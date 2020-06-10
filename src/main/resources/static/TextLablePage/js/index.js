var docUrl=getQueryVariable("docUrl"); // 文本URL
var userId=getQueryVariable('userId'); //用户ID
var acceptId=getQueryVariable('acceptId'); //接受任务ID
var pageType=getQueryVariable('pageType');  //当前页面类型
var pageFrom=URLencode(getQueryVariable('pageFrom')); //从那个页面跳转来的（返回时使用）
var operation=getQueryVariable('operation'); //read，write
var taskId=getQueryVariable('taskId'); //任务ID
var labelInfo=getQueryVariable('labelInfo');
var docUrlPre="D://docTask//";
var originaldoc;
var docNotFinishlist=[];
var colorArray=[ //颜色数组
    '#33A02B',
    '#F9001B',
    '#96017E',
    '#FFB200',
    '#0F3193',
    '#99CF17',
    '#FE4D01',
    '#3E007D',
    '#FFD900',
    '#0950A0',
    '#FF7F00',
    '#00978B'
];
$(function () {
    layui.use(['element','layer'], function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
        var layer = layui.layer;
    });
    labelNameRender();
    if(operation==="read"){
        if(labelInfo==null){
            loadDoc(docUrl);
        }else {
            loadDoc(labelInfo);
        }

        editor.isReadOnly = true;
    }else{
        loadDoc(docUrl);
    }

});
function labelNameClick(name,color) {
    var rgb=hexToRgb(color);
    change(rgb);
    editor.editing.view.focus();
    boldSelection(name);

}

//加载标签
function labelNameRender() {
    if(pageType==='personalAcceptNotFinishPage'
        || pageType==='personalAcceptFinishPage'
    ){
        labelName = JSON.parse(window.sessionStorage.getItem(acceptId + 'labelName'));
    }else if(pageType==='otherReleasePage'
        || pageType==='personalReleasePage'
        || pageType==='labelExamplePage'
    ){
        labelName = JSON.parse(window.sessionStorage.getItem(taskId + 'labelName'))
    }
    if(labelName !== null && labelName!== ''){
        firstLabelName=labelName[0];
        if(operation==='write'){
            var shtml ='<li class="layui-nav-item layui-this" onclick="labelNameClick('+'\''+labelName[0]+'\''+','+'\''+colorArray[0]+'\''+')"><a href="javascript:;">'+ labelName[0]+'</a><div class="colorDiv" style="background-color: '+colorArray[0]+'"></div></li>';
            //labelInfoArray.push({labelName:labelName[0],labelInfo:[]});
            for (var i = 1; i < labelName.length; i++){
                shtml +='<li class="layui-nav-item" onclick="labelNameClick('+'\''+labelName[i]+'\''+','+'\''+colorArray[i]+'\''+')"><a href="javascript:;">'+ labelName[i]+'</a><div class="colorDiv" style="background-color: '+colorArray[i]+'"></div></li>';
                //labelInfoArray.push({labelName:labelName[i],labelInfo:[]});
            }
        }else{
            var shtml ='<li class="layui-nav-item layui-this" onclick="showAll()"><a href="javascript:;">查看全部</a></li>';
            for (var i = 0; i < labelName.length; i++){
                shtml +='<li class="layui-nav-item" onclick="labelNameClick('+'\''+labelName[i]+'\''+','+'\''+colorArray[i]+'\''+')"><a href="javascript:;">'+ labelName[i]+'</a><div class="colorDiv" style="background-color: '+colorArray[i]+'"></div></li>';
                //labelInfoArray.push({labelName:labelName[i],labelInfo:[]});
            }
        }

        $("#labelNameContainer").html(shtml);
    }
}
//加载文本
function loadDoc(url) {
    if(operation === 'read'){
        // 隐藏撤销按钮
        $("#revoke").hide();
        // 隐藏标注完成按钮
        $("#finish").hide();
    }else if(operation ==='write') {
        if (pageType === 'labelExamplePage') {
            docNotFinishlist = JSON.parse(
                window.sessionStorage.getItem(taskId + 'docNotFinishlist'));
        } else {
            docNotFinishlist = JSON.parse(
                window.sessionStorage.getItem(acceptId + 'docNotFinishlist'));
        }
    }
    $.ajax({
        type: "POST",
        url: '/task/loadDoc',
        data: {
            "path": url,
        },
        success: function (resultData) {
            originaldoc = resultData;
            editor.setData(resultData);
        }
    })

}
//加载已标注文本
function loadLabelDoc() {
    if(operation === 'read'){
        // 隐藏撤销按钮
        $("#revoke").hide();
        // 隐藏标注完成按钮
        $("#finish").hide();
    }else if(operation ==='write') {
        if (pageType === 'labelExamplePage') {
            docNotFinishlist = JSON.parse(
                window.sessionStorage.getItem(taskId + 'docNotFinishlist'));
        } else {
            docNotFinishlist = JSON.parse(
                window.sessionStorage.getItem(acceptId + 'docNotFinishlist'));
        }
    }
    $.ajax({
        type: "POST",
        url: '/task/loadLabelDoc',
        data: {
            "path": docUrl,
        },
        success: function (resultData) {
            originaldoc = resultData;
            editor.setData(resultData);
        }
    })

}
//文本标注
function boldSelection(label) {
    if (document.selection) { // 老IE
        var selecter = document.selection.createRange();
        selecter.select();
        var selectStr = selecter.text; //获取选中文本
        selecter.pasteHTML(text); //替换为HTML元素，替换完会失去选取，如果选择的是textarea里的内容这里会报错
    } else { // 非老IE
        var selecter;
        if (window.getSelection()) {
            selecter = window.getSelection();
        } else {
            selecter = document.getSelection();
        }
        selecter = document.getSelection();
        var selectStr = selecter.toString();
        if (selectStr.trim() != "") {
            var rang = selecter.getRangeAt(0);
            // temp成为选中内容的父节点，达到加粗的效果
            var text="#"+selectStr+"<"+label+">#";
            // 先删除再插入达到替换的效果，
            rang.deleteContents(); // 删除选中内容
            rang.insertNode(document.createTextNode(text)); //在选中内容的起始位置插入一个节点
            // chrome中的bug，如果选中的是textarea中的内容，就会在textarea前面插入节点
        }
    }
}
//获取url参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}
//16进制颜色转rgb
function hexToRgb(hex) {
    return 'rgb(' + parseInt('0x' + hex.slice(1, 3)) + ',' + parseInt('0x' + hex.slice(3, 5))
        + ',' + parseInt('0x' + hex.slice(5, 7)) + ')';
}

//更改文本样式
function change(rgb) {

    editor.execute( 'bold' );
    editor.execute( 'fontColor', { value: rgb } );
}
//完成任务
function finishFunc(){
    var docUrlParam = docUrl.substring(13, docUrl.length);
    var labelInfo = editor.getData();
    if(labelInfo===originaldoc){
        layer.msg('请先标注', {
            icon: 5, //红色不开心
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        return;
    }
    var data ={
        "pageType":pageType,
        "userId":userId,
        "acceptId":acceptId,
        "taskId":taskId,
        "docUrlParam":docUrlParam,
        "labelInfo":labelInfo
    };
    // 提交标注信息
    $.ajax({
        type: "POST",
        url:"/task/storeDocLabelInfo",
        data:data,
        success:function(resultData){
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                layer.msg('操作成功', {
                    icon: 1, //绿勾
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                });
                window.setTimeout(function () {
                    if(docNotFinishlist!==null&&docNotFinishlist.length >0){
                        var docName=docNotFinishlist.pop().originalDoc;
                        if(pageType==='labelExamplePage'){
                            window.sessionStorage.setItem(
                                taskId + 'docNotFinishlist',
                                JSON.stringify(docNotFinishlist)
                            );
                        }else{
                            window.sessionStorage.setItem(
                                acceptId + 'docNotFinishlist',
                                JSON.stringify(docNotFinishlist)
                            );
                        }
                        window.location.href='/TextLablePage/index.html'
                            +'?docUrl='+docUrlPre+docName
                            +'&userId='+userId
                            +'&acceptId='+acceptId
                            +'&pageType='+pageType
                            +'&pageFrom='+pageFrom
                            +'&taskId='+taskId
                            +'&operation=write';
                    }else{
                        goBackFunc();
                    }
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
//返回
function goBackFunc(){
    window.location.href="/TextLabelTaskPage/TextLabelTask.html" +
        "?userId="+userId+
        "&acceptId="+acceptId+
        "&pageType="+pageType+
        "&taskId="+taskId+
        "&pageFrom="+pageFrom;
}
//转义
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