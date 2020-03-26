var userId=getQueryVariable('userId'); //获取URL参数里的用户ID
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    //layui初始化
    layui.use('element', function(){
        var element = layui.element;
    });
    //初始化iframe
    $("#iframeMain").attr("src",'./TaskManage.html?userId='+userId);
    //给所有的a标签添加点击事件
    $("a").click(function (e) {
        //阻止a标签的默认行为
        e.preventDefault();
        // 给iframe的src属性赋值
        $("#iframeMain").attr("src",$(this).attr("href")+'?userId='+userId);
    });
});

// 获取URL里的参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
