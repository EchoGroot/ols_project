$(function () {
    //layui初始化
    layui.use('form', function(){
        var form = layui.form;
    });
    layui.use(['layer'],function () {
        var layer = layui.layer;
    })
    // 页面初始化生成验证码
    window.onload = createCode("#codeCard");
    // 验证码切换
    $("#codeCard").click(function () {
        createCode("#codeCard");
    });
    // 登录事件
    $("#loginBtn").click(function () {
        login();
    });
});
// 生成验证码
function createCode(codeID) {
    var code = "";
    // 验证码长度
    var codeLength = 4;
    // 验证码dom元素
    var checkCode = $(codeID);
    // 验证码随机数
    var random = [0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
        'S','T','U','V','W','X','Y','Z'];
    for (var i = 0;i < codeLength; i++){
        // 随机数索引
        var index = Math.floor(Math.random()*36);
        code += random[index];
    }
    // 将生成的随机验证码赋值
    checkCode.val(code);
}

// 登录流程
function login() {
    var inputCode = $("#code").val().toUpperCase();
    var cardCode = $("#codeCard").val();
    var userName = $("#userName").val();
    var passWord = $("#userPassword").val();
    if ($.trim(userName) == '' || $.trim(userName).length<=0){
        layer.alert('用户名不能为空');
        return false;
    }
    if ($.trim(passWord) == '' || $.trim(passWord).length<=0){
        layer.msg("密码不能为空", {
            icon: 5, //红色不开心
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    if (inputCode.length<=0){
        layer.msg("验证码不能为空", {
            icon: 5, //红色不开心
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    if (inputCode != cardCode){
        layer.msg("请输入正确验证码", {
            icon: 5, //红色不开心
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    $.ajax({
        type: "get",
        url:"/user/login",
        data:{
            "userName":userName,
            "passWord":passWord
        },
        success:function(resultData){
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "205"){
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }else{
                layer.msg('登录成功');
                var page=null;
                switch ((resultData.meta.status)) {
                    case "200":page="PersonalCenterPage";
                            break;
                    case "201":page="AdminPage";
                            break;
                    case "202":page="JudgeTaskPage";
                }
                window.location.href = "http://127.0.0.1:8080/"+page+"/index.html?userId=" +resultData.data.userId;
            }
        }
    })
}
