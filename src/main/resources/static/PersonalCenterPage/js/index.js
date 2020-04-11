var userId=getQueryVariable('userId'); //用户ID
var page=getQueryVariable('page'); //页面名称

//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 添加a=1参数是为了正确匹配iframe的URL参数
    $("#personalInfo a,#acceptFinishTask a,#acceptNotFinishTask a,#releaseFinishTask a,#releaseNotFinishTask a").click(function (e) {
        // 阻止a标签的默认行为
        e.preventDefault();
        // 给iframe的src属性赋值，显示对应的html
        $("#iframeMain").attr("src",$(this)
                .attr("href")
            +"&userId="+userId
        );
    });
    // 个人信息
    if(page==='personalInfo'){
        // 添加选中效果
        $("#personalInfo").attr('class','layui-nav-item layui-this');
        $("#iframeMain").attr("src",$("#personalInfo a")
                .attr("href")
            +'&userId='+userId
        );
    // 已接受未完成的任务
    }else if(page ==='acceptFinishTask'){
        // 打开抽屉菜单
        $("#acceptTask").attr('class','layui-nav-item layui-nav-itemed');
        $("#acceptFinishTask").attr('class','layui-this');
        $("#iframeMain").attr("src",$("#acceptFinishTask a")
                .attr("href")
            +'&userId='+userId
        );
    // 已接受未完成
    }else if(page ==='acceptNotFinishTask'){
        $("#acceptTask").attr('class','layui-nav-item layui-nav-itemed');
        $("#acceptNotFinishTask").attr('class','layui-this');
        $("#iframeMain").attr("src",$("#acceptNotFinishTask a")
                .attr("href")
            +'&userId='+userId
        );
    // 已发布已完成
    }else if(page ==='releaseFinishTask'){
        $("#releaseTask").attr('class','layui-nav-item layui-nav-itemed');
        $("#releaseFinishTask").attr('class','layui-this');
        $("#iframeMain").attr("src",$("#releaseFinishTask a")
                .attr("href")
            +'&userId='+userId
        );
    // 已发布未完成
    }else if(page ==='releaseNotFinishTask'){
        $("#releaseTask").attr('class','layui-nav-item layui-nav-itemed')
        $("#releaseNotFinishTask").attr('class','layui-this');
        $("#iframeMain").attr("src",$("#releaseNotFinishTask a")
                .attr("href")
            +'&userId='+userId
        );
    }
    // layui初始化
    layui.use('element', function(){
        var element = layui.element;
    });
    judgeLogin();
});
// 获取URL里的参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
};
//判断是否登录
function judgeLogin() {
    if(userId!=null){
        $.ajax({
            url: '/user/judgeLogin',
            type: "GET",
            data: {
                "userId": userId
            },
            success: function (resultData) {
                resultData = JSON.parse(resultData);
                if(resultData.meta.status === "200"){
                    var name=null;
                    $.ajax({
                        url: "/user/getUserInfo",
                        type: "get",
                        data: {
                            userId: userId
                        },
                        success: function (resultData) {
                            resultData = JSON.parse(resultData);
                            if (resultData.meta.status === "200") {
                                name = resultData.data.userInfo.name;
                                var a=document.getElementById("userName");
                                a.innerText=name;
                            }
                        }
                    });

                }else{
                    window.location.href='/Home/Home.html';
                }

            }
        })
    }
}
function goBackFunc() {
    window.location.href = "/Home/Home.html"+
        "?userId="+userId
    ;
}
