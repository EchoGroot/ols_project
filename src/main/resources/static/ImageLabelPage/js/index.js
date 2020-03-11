var layer=0;
var imageUrl='';
$(function () {
    drawPen();
    imageUrl=getQueryVariable("imageUrl");
    // 从本地session中取出该图片的标注信息
    var labelName = JSON.parse(window.sessionStorage.getItem(imageUrl))
});
CanvasExt = {
    drawRect:function(canvasId,penColor,strokeWidth){
        var that=this;
        that.penColor=penColor;
        that.penWidth=strokeWidth;
        var canvas=document.getElementById(canvasId);
        //canvas 的矩形框
        var canvasRect = canvas.getBoundingClientRect();
        //矩形框的左上角坐标
        var canvasLeft=canvasRect.left;
        var canvasTop=canvasRect.top;
        var layerIndex=layer;
        var layerName="layer";
        var x=0;
        var y=0;
        //鼠标点击按下事件，画图准备
        canvas.onmousedown=function(e){
            //设置画笔颜色和宽度
            var color=that.penColor;
            var penWidth=that.penWidth;
            layerIndex++;
            layer++;
            layerName+=layerIndex;
            x = e.clientX-canvasLeft;
            y = e.clientY-canvasTop;
            $("#"+canvasId).addLayer({
                type: 'rectangle',
                strokeStyle: color,
                strokeWidth: penWidth,
                name:layerName,
                fromCenter: false,
                x: x, y: y,
                width: 1,
                height: 1
            });
            $("#"+canvasId).drawLayers();
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
            console.log(x,y,width, height)
            $("#"+canvasId).drawLayers();
            $("#"+canvasId).saveCanvas();
            console.log($("#"+canvasId))
        }
    }
};

function drawPen(){
    var color = "red";
    var width = 1;
    CanvasExt.drawRect("canvas",color,width);
}

function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}