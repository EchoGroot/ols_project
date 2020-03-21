var userId=getQueryVariable('userId');
var page=getQueryVariable('page');
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    if(page==='notCheck'){
        $("#notCheckTag").attr('class','layui-nav-item layui-this');
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
    layui.use('element', function(){
        var element = layui.element;
    });

    $("a").click(function (e) {
        e.preventDefault();
        $("#iframeMain").attr("src",$(this)
            .attr("href")
            +"&userId="+userId
        );
    });
});
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}