var userId;
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    userId=getQueryVariable('userId');
    layui.use('element', function(){
        var element = layui.element;
    });
    $("#iframeMain").attr("src",'./Check.html?isChecked=0&userId='+userId);
    $("a").click(function (e) {
        e.preventDefault();
        $("#iframeMain").attr("src",$(this).attr("href")+'&userId='+userId);
    });
})
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}