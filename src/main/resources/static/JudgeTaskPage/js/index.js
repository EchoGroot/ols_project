var userId=getQueryVariable('userId'); //用户ID
var page=getQueryVariable('page'); //页面名称
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    if(page==='notCheck'){
        // 添加选中效果
        $("#notCheckTag").attr('class','layui-nav-item layui-this');
        // 设置iframe的src属性
        $("#iframeMain").attr("src",$("#notCheckTag a")
            .attr("href")
            +'&userId='+userId
        );
    }else if(page ==='finishCheck'){
        $("#finishCheckTag").attr('class','layui-nav-item layui-this');
        $("#iframeMain").attr("src",$("#finishCheckTag a")
            .attr("href")
            +'&userId='+userId
        );
    }else if(page === "message"){
        $("#messageTag").attr('class','layui-nav-item layui-this');
        $("#iframeMain").attr("src",$("#messageTag a")
            .attr("href")
            +'&userId='+userId
        );
    }else if(page === "dataAnalysis"){
        $("#dataAnalysisTag").attr('class','layui-nav-item layui-this');
        $("#iframeMain").attr("src",$("#dataAnalysisTag a")
            .attr("href")
            +'&userId='+userId
        );
    }
    // layui初始化
    layui.use('element', function(){
        var element = layui.element;
    });
    // 所有a标签添加点击事件
    $("a").click(function (e) {
        // 阻止a标签的默认行为
        e.preventDefault();
        $("#iframeMain").attr("src",$(this)
            .attr("href")
            +"&userId="+userId
        );
    });
});
// 获取URL里的参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}