var userId=getQueryVariable('userId'); //用户ID
var page=getQueryVariable('page'); //页面名称
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    /*if(page==='notCheck'){
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
    }*/
    //初始化iframe
    $("#iframeMain").attr("src",'./Check.html?a=1&isChecked=0&page=notCheck&userId='+userId);
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
            +"?userId="+userId
        );
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
}

//判断是否登录
function judgeLogin() {
    if(userId==null){
        layer.msg('请先登录', {
            icon: 5, //红色不开心
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });

        // 跳转到登录页面
        window.location.href='/Home/login.html';
    }
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
                            var a=document.getElementById("reviewerName");
                            a.innerText=name;
                        }
                    }
                });

            }
            if (resultData.meta.status === "201") {
                layer.msg('请先登录', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                // 跳转到登录页面
                window.location.href='/Home/login.html';
            }
        }
    })
}
//注销
function cancel() {
    $.ajax({
        url:"/user/cancel",
        type:"get",
        success:function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                sessionStorage.clear();   //清除所有session值
                window.location.href='/Home/login.html';
            }else{
                layer.msg('操作失败!', {
                    icon: 5, //红色不开心
                });
            }
        }
    })

}