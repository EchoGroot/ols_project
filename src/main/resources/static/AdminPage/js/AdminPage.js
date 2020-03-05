//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    console.log('hello');
    layui.use('element', function(){
        var element = layui.element;
    });
    $("a").click(function (e) {
        e.preventDefault();
        $("#iframeMain").attr("src",$(this).attr("href"));
    });
})
