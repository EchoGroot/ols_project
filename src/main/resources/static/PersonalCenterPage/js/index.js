var userId=getQueryVariable('userId');
var page=getQueryVariable('page');
var query=getQueryVariable('query');

//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 添加a=1参数是为了正确匹配iframe的URL参数
    $("#personalInfo a,#acceptFinishTask a,#acceptNotFinishTask a,#releaseFinishTask a,#releaseNotFinishTask a").click(function (e) {
        e.preventDefault();
        $("#iframeMain").attr("src",$(this)
                .attr("href")
            +"&userId="+userId
        );
    });
    if(page==='personalInfo'){
        $("#personalInfo").attr('class','layui-nav-item layui-this');
        $("#iframeMain").attr("src",$("#personalInfo a")
                .attr("href")
            +'&userId='+userId
        );
    }else if(page ==='acceptFinishTask'){
        $("#acceptTask").attr('class','layui-nav-item layui-nav-itemed');
        $("#acceptFinishTask").attr('class','layui-this');
        $("#iframeMain").attr("src",$("#acceptFinishTask a")
                .attr("href")
            +'&userId='+userId
        );
    }else if(page ==='acceptNotFinishTask'){
        $("#acceptTask").attr('class','layui-nav-item layui-nav-itemed');
        $("#acceptNotFinishTask").attr('class','layui-this');
        $("#iframeMain").attr("src",$("#acceptNotFinishTask a")
                .attr("href")
            +'&userId='+userId
        );
    }else if(page ==='releaseFinishTask'){
        $("#releaseTask").attr('class','layui-nav-item layui-nav-itemed');
        $("#releaseFinishTask").attr('class','layui-this');
        $("#iframeMain").attr("src",$("#releaseFinishTask a")
                .attr("href")
            +'&userId='+userId
        );
    }else if(page ==='releaseNotFinishTask'){
        $("#releaseTask").attr('class','layui-nav-item layui-nav-itemed')
        $("#releaseNotFinishTask").attr('class','layui-this');
        $("#iframeMain").attr("src",$("#releaseNotFinishTask a")
                .attr("href")
            +'&userId='+userId
        );
    }
    layui.use('element', function(){
        var element = layui.element;
    });

})
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};

