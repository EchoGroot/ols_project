var imageUrl=getQueryVariable("imageUrl"); // 图片URL
var userId=getQueryVariable('userId'); //用户ID
var acceptId=getQueryVariable('acceptId'); //接受任务ID
var pageType=getQueryVariable('pageType');  //当前页面类型
var pageFrom=URLencode(getQueryVariable('pageFrom')); //从那个页面跳转来的（返回时使用）
var operation=getQueryVariable('operation'); //read，write
var taskId=getQueryVariable('taskId'); //任务ID

var imageWidth=0;   //原始图片宽度
var imageHeight=0;  //原始图片高度
var labelName='';   //标签名
var labelInfoArray=[];  //标注信息
var firstLabelName='';  //当前展示的标签名对应的标注信息

$(function () {
    //layui组件
    layui.use(['element','layer'], function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
        var layer = layui.layer;
    });
    //添加图片src属性
    $("#imgDOM").attr("src",imageUrl);
    //加载图片时执行
    imageRender();
    // 从本地session中取出该图片的标注信息
    labelNameRender();
    if(operation ==='write'){
        // 允许在图层上画框
        drawPen();
        // 显示xy坐标线
        xyLine();
    }else{
        // 隐藏撤销按钮
        $("#revoke").hide();
        // 隐藏标注完成按钮
        $("#finish").hide();
    }
});

// 图层上画框功能
CanvasExt = {
    drawRect:function(canvasId,penColor,strokeWidth){
        var that=this;
        // 画笔颜色
        that.penColor=penColor;
        // 画笔宽度
        that.penWidth=strokeWidth;
        // 获取指定canvas图层
        var canvas=document.getElementById(canvasId);
        //矩形框的左上角坐标
        var canvasTop=0;
        var canvasLeft=0;
        var layerIndex=0;
        // 标注框的名字
        var layerName="layer";
        var x=0;
        var y=0;
        //鼠标点击按下事件，画图准备
        canvas.onmousedown=function(e){
            canvasTop=$("#"+canvasId).offset().top;
            canvasLeft=$("#"+canvasId).offset().left-0.5;
            //设置画笔颜色和宽度
            var color=that.penColor;
            var penWidth=that.penWidth;
            layerIndex++;
            layerName='layer';
            layerName+=layerIndex;
            x = e.clientX-canvasLeft;
            y = e.clientY-canvasTop;
            // 向canvas图层添加标注框
            $("#"+canvasId).addLayer({
                // 绘画的图形为矩形
                type: 'rectangle',
                // 画笔颜色
                strokeStyle: color,
                // 画笔宽度
                strokeWidth: penWidth,
                name:layerName,
                fromCenter: false,
                x: x, y: y,
                width: 1,
                height: 1
            });
            // 画图
            $("#"+canvasId).drawLayers();
            // 保存新增的标注框
            $("#"+canvasId).saveCanvas();
            //鼠标移动事件，画图
            canvas.onmousemove=function(e){
                width = e.clientX-canvasLeft-x;
                height = e.clientY-canvasTop-y;
                $("#"+canvasId).removeLayer(layerName);
                $("#"+canvasId).addLayer({
                    type: 'rectangle',
                    strokeStyle: color,
                    strokeWidth: penWidth,
                    name:layerName,
                    fromCenter: false,
                    x: x, y: y,
                    width: width,
                    height: height
                });
                $("#"+canvasId).drawLayers();
            }
        };
        // 鼠标松开后执行
        canvas.onmouseup=function(e){
            var color=that.penColor;
            var penWidth=that.penWidth;
            canvas.onmousemove=null;
            width = e.clientX-canvasLeft-x;
            height = e.clientY-canvasTop-y;
            $("#"+canvasId).removeLayer(layerName);
            $("#"+canvasId).addLayer({
                type: 'rectangle',
                strokeStyle: color,
                strokeWidth: penWidth,
                name:layerName,
                fromCenter: false,
                x: x, y: y,
                width: width,
                height: height
            });
            for (var i = 0; i <labelInfoArray.length;i++ ){
                // 找到对应的标签名
                if(labelInfoArray[i].labelName===firstLabelName){
                    // 添加到该标签名对应的数组
                    labelInfoArray[i].labelInfo.push(
                        {
                            layerName:layerName,
                            startX : x/1600*imageWidth,
                            startY : y/800*imageHeight,
                            endX : (x+width)/1600*imageWidth,
                            endY : (y+height)/800*imageHeight
                        }
                    );
                    break;
                }
            }
            $("#"+canvasId).drawLayers();
            $("#"+canvasId).saveCanvas();
        }
    }
};
// 主动画图
CanvasExt1 = {
    drawRect:function(canvasId,penColor,strokeWidth){
        var that=this;
        that.penColor=penColor;
        that.penWidth=strokeWidth;
        var layerIndex=0;
        var layerName="layer";
        var color=that.penColor;
        var penWidth=that.penWidth;
        // 从本地session获取标注信息
        if(pageType.indexOf('Release')!==-1){
            var labelInfos=JSON.parse(window.sessionStorage.getItem(taskId+imageUrl));
        }else if(pageType.indexOf('Accept')!==-1){
            var labelInfos=JSON.parse(window.sessionStorage.getItem(acceptId+imageUrl));
        }
        for (var i = 0; i < labelInfos.length;i++){
            layerIndex++;
            layerName='layer';
            layerName+=layerIndex;
            for (var j = 0; j < labelInfoArray.length;j++){
                if(labelInfoArray[j].labelName ===labelInfos[i].name){
                    // 将标注信息根据标签名添加到对应的数组
                    labelInfoArray[j].labelInfo.push({
                        layerName:layerName,
                        startX : labelInfos[i].x/imageWidth*1600,
                        startY : labelInfos[i].y/imageHeight*800,
                        endX : labelInfos[i].ex/imageWidth*1600,
                        endY : labelInfos[i].ey/imageHeight*800
                    });
                    break;
                }
            }
            // 添加到canvas图层
            $("#"+canvasId).addLayer({
                type: 'rectangle',
                strokeStyle: color,
                strokeWidth: penWidth,
                name:layerName,
                fromCenter: false,
                x: labelInfos[i].x/imageWidth*1600,
                y: labelInfos[i].y/imageHeight*800,
                width: (labelInfos[i].ex-labelInfos[i].x)/imageWidth*1600,
                height: (labelInfos[i].ey-labelInfos[i].y)/imageHeight*800
            });
            // 画图
            $("#"+canvasId).drawLayers();
            // 保存图层
            $("#"+canvasId).saveCanvas();
        }
        // 只显示第一个标签名对应的标注信息
        labelNameClick(labelInfoArray[0].labelName)
    }
};
// 默认画图准备
function drawPen(){
    var color = "green";
    var width = 2;
    CanvasExt.drawRect("canvas",color,width);
}
// 主动画图准备
function drawPen1(){
    var color = "green";
    var width = 2;
    CanvasExt1.drawRect("canvas",color,width);
}
// 从URL中获取参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}
// 加载图片
function imageRender() {
    var img=new Image();
    img.src =imageUrl;
    img.onload = function() {
        imageWidth=img.width;
        imageHeight = img.height;
        if(operation === 'read'){
            drawPen1();
        }
    };
}
// 加载标签名
function labelNameRender() {
    if(pageType==='personalAcceptNotFinishPage'
        || pageType==='personalAcceptFinishPage'
    ){
        labelName = JSON.parse(window.sessionStorage.getItem(acceptId + 'labelName'));
    }else if(pageType==='otherReleasePage'
        || pageType==='personalReleasePage'
    ){
        labelName = JSON.parse(window.sessionStorage.getItem(taskId + 'labelName'))
    }
    if(labelName !== null && labelName!== ''){
        firstLabelName=labelName[0];
        var shtml ='<li class="layui-nav-item layui-this" onclick="labelNameClick('+'\''+labelName[0]+'\''+')"><a href="javascript:;">'+ labelName[0]+'</a></li>';
        labelInfoArray.push({labelName:labelName[0],labelInfo:[]});
        for (var i = 1; i < labelName.length; i++){
            shtml +='<li class="layui-nav-item" onclick="labelNameClick('+'\''+labelName[i]+'\''+')"><a href="javascript:;">'+ labelName[i]+'</a></li>';
            labelInfoArray.push({labelName:labelName[i],labelInfo:[]});
        }
        $("#labelNameContainer").html(shtml);
    }
}
// 只显示某个标签名对应的标注信息
function labelNameClick(bt) {
    firstLabelName=bt;
    for (var i = 0; i < labelInfoArray.length; i++) {
        if(labelInfoArray[i].labelName!==bt){
            for (var j = 0; j <labelInfoArray[i].labelInfo.length;j++){
                // 隐藏图层
                $('#canvas').setLayer(labelInfoArray[i].labelInfo[j].layerName, {
                    visible: false // set to true instead to show the layer again
                }).drawLayers();
            }
        }else{
            for (var j = 0; j <labelInfoArray[i].labelInfo.length;j++){
                // 显示图层
                $('#canvas').setLayer(labelInfoArray[i].labelInfo[j].layerName, {
                    visible: true // set to true instead to show the layer again
                }).drawLayers();
            }
        }
    }
}
// 显示xy坐标线
function xyLine(){
    var ox = document.createElement('div');
    var oy = document.createElement('div');
    ox.style.width = '100%';
    ox.style.height = '1px';
    ox.style.backgroundColor = 'red';
    ox.style.position = 'fixed';
    ox.style.left = 0;
    document.body.appendChild(ox);
    oy.style.height = '100%';
    oy.style.width = '1px';
    oy.style.backgroundColor = 'red';
    oy.style.position = 'fixed';
    oy.style.top = 0;
    document.body.appendChild(oy);
    // 根据周围元素的Z轴高度决定的，目的是坐标线只在某个范围内显示
    ox.style.zIndex =998;
    oy.style.zIndex =998;
    ox.style.pointerEvents='none';
    oy.style.pointerEvents='none';
    document.onmousemove = function(e) {
        var e = e || event;
        var x = e.pageX;
        var y = e.pageY;
        // 鼠标超出范围时隐藏坐标线
        if(x<($("#canvas").offset().left-0.5)
            ||x>($("#canvas").offset().left-0.5+1600)
            ||y<($("#canvas").offset().top)
            ||y>($("#canvas").offset().top+800)
        ){
            ox.style.display='none';
            oy.style.display='none';
        }else{
            ox.style.display='inline';
            oy.style.display='inline';
        }
        ox.style.top = y + 'px';
        oy.style.left = x + 'px';
    }
}
// 标注完成
function finishFunc(){
    // 27是http://yuyy.info/image/ols/的长度
    var imageUrlParam = imageUrl.substring(27, imageUrl.length);
    var labelInfo=[];
    for (var i = 0; i < labelInfoArray.length;i++){
        for (var j = 0; j < labelInfoArray[i].labelInfo.length;j++){
            labelInfo.push({
                name:labelInfoArray[i].labelName,
                x:labelInfoArray[i].labelInfo[j].startX,
                y:labelInfoArray[i].labelInfo[j].startY,
                ex:labelInfoArray[i].labelInfo[j].endX,
                ey:labelInfoArray[i].labelInfo[j].endY
            })
        }
    }
    var data ={
            "userId":userId,
            "acceptId":acceptId,
            "imageUrlParam":imageUrlParam,
            "labelInfo":JSON.stringify(labelInfo)
        };
    // 提交标注信息
    $.ajax({
        type: "POST",
        url:"/task/storeImageLabelInfo",
        data:data,
        success:function(resultData){
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                layer.msg('操作成功', {
                    icon: 1, //绿勾
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                });
                window.setTimeout(function () {
                    goBackFunc();
                }, 1000);
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });

            }
        }
    });
}
// 返回按钮点击事件
function goBackFunc(){
    window.location.href="/ImageLabelTaskPage/index.html" +
            "?userId="+userId+
            "&acceptId="+acceptId+
            "&pageType="+pageType+
            "&taskId="+taskId+
            "&pageFrom="+pageFrom;
}

//撤销按钮的点击事件
function revokeFunc(){
    for (var i = 0; i < labelInfoArray.length; i++) {
        // 找到此时的标签名
        if(labelInfoArray[i].labelName===firstLabelName){
            // 在该标签名对应的数组中移除最后个标注信息，并移除对应的canvas图层
            $('#canvas').removeLayer(labelInfoArray[i].labelInfo.pop().layerName).drawLayers();
        }
    }
}
//转义URL里的特殊字符
function URLencode(sStr) {
    return sStr.replace(/\%/g,"%25")
        .replace(/\+/g, '%2B')
        .replace(/\"/g,'%22')
        .replace(/\#/g,'%23')
        .replace(/\'/g, '%27')
        .replace(/\&/g, '%26')
        .replace(/\?/g, '%3F')
        .replace(/\=/g, '%3D')
        .replace(/\//g,'%2F');
}