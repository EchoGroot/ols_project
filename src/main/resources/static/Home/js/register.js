$(function () {
    //layui初始化
    layui.use('form', function () {
        var form = layui.form;
        form.verify({
            //验证用户名是否存在
            username:function(value){
                var code;
                $.ajax({
                    type: "post",
                    async:false,
                    url: "/user/checkUserName",
                    data: {
                        "userName": value
                    },
                    success: function (resultData) {
                        resultData = JSON.parse(resultData);
                        code=resultData.meta.status;
                    }
                });
                if(code==="201"){
                    return '用户名已存在！';
                }
            }
        });
        form.on('submit(code)', function(data){
            if(validate()){
                sendEmail();
            }
            return false;
        });
        form.on('submit(registerbtn)', function(data){
            if(validate()){
                if(verifyCode()){
                    userRegister()

                }
            }
            return false;
        });
    });
    layui.use(['layer'], function () {
        var layer = layui.layer;
    });
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#birthday' //指定元素
        });
    });


});
function validate() {
    //验证两次输入的密码是否相同
    var passWord1=$("#userPassword1").val();
    var passWord2=$("#userPassword2").val();
    if(passWord1==null||passWord1==""){
        return false;
    }
    if(passWord1!=passWord2){
        layer.alert('两次输入的密码不相同！', {
            icon: 5 //红色不开心
        });
        return false;
    }
    return true;
}
//倒计时60s
var countdown = 60;
function setTime() {
    if (countdown == 0) {
        $("#codeCard").attr("disabled",false);
        $("#codeCard").val("获取验证码");
        countdown = 60;//60秒过后button上的文字初始化,计时器初始化;
        return;
    } else {
        $("#codeCard").attr("disabled",true);
        $("#codeCard").val("重新发送("+countdown+"s)");
        countdown--;
    }
    setTimeout(function() { setTime() },1000) //每1000毫秒执行一次
}
//验证码匹配
function verifyCode() {
    var email=$("#email").val();
    var inputcode=$("#inputcode").val();
    if(inputcode===""||inputcode===null){
        layer.msg('请输入验证码！',{
            icon:5
        });
        return false;
    }else{
        $.ajax({
            type:"post",
            url:"/user/verifyCode",
            data:{
                "email":email,
                "inputcode":inputcode
            },
            success: function (resultData) {
                resultData = JSON.parse(resultData);
                if (resultData.meta.status === "200") {
                    return true;
                }else{
                    layer.alert('验证码错误！',{
                        icon:5
                    });
                    return false;
                }
            },
            error:function(){
                return false;
            }
        })
    }
    return true;
}
function sendEmail() {
    var email=$("#email").val();
    $.ajax({
        type: "post",
        url: "/user/sendEmail",
        data: {
            "email":email
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {

            }
        },
        error:function(){
            layer.alert('验证码发送失败',{
                icon:5
            });
        }
    });
    setTime();
}
function userRegister() {
    var name=$("#userName").val();
    var birthday=$("#birthday").val();
    var password=$("#userPassword2").val();
    var sex=$('input[name="sex"]:checked').val();
    var email=$("#email").val();
    var role=$("#role").val();
    $.ajax({
        type: "post",
        url: "/user/userRegister",
        data: {
            "name":name,
            "birthday":birthday,
            "password":password,
            "sex":sex,
            "role":role,
            "email":email
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                if(role=="0"){
                    layer.alert('注册成功！立即登录',{
                        icon:1
                    })
                }else{
                    layer.alert('等待管理员审核！',{
                        icon:1
                    })
                }

            }else{
                layer.alert('注册失败！请重试',{
                    icon:5
                })
            }
        },
        error:function(){
            layer.alert('注册失败！请重试',{
                icon:5
            });
        }
    });
}
