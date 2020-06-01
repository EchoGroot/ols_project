var userId=getQueryVariable('userId');
$(function () {
    //alert(userId);
    loadUserInfo();
    getEmail();
    layui.use('form', function(){
        var form = layui.form;
        form.on('submit(code)', function(data){
            if(validate()){
                if(email!=null||email!=""){
                    sendEmail(email);
                }
            }
            return false;
        });
    });
});
function loadUserInfo(){
    $.ajax({
        type: "GET",
        url:"/user/getUserInfo",
        data:{
            "userId":userId
        },
        success:function(resultData){
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                $("#userName").val(resultData.data.userInfo.name);
                $("#sex").val(resultData.data.userInfo.sex);
                $("#birthday").val(resultData.data.userInfo.birthday);
                $("#email").val(resultData.data.userInfo.email);
                switch (resultData.data.userInfo.role) {
                    case 0:$("#role").val("普通用户");
                        break;
                    case 1:$("#role").val("系统管理员");
                        break;
                    case 2:$("#role").val("审核者");
                        break;
                }

                $("#points").val(resultData.data.userInfo.points);
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    });
}
// 获取iframe的URL参数
function getQueryVariable(name) {
    /*var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }*/
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null){
        return unescape(r[2]);
    }
    return null;
}
//修改邮箱
function changeEmail(){
    layer.open({
        type:1
        ,title:"修改邮箱"
        ,area:['400px','180px']
        ,content:$("#emailInfo"),
        shade:0,
        btn: ['确定修改', '取消']
        ,btn1: function(index, layero){
            var email=$("#email2").val();
            $.ajax({
                type : "post",
                url : "/user/changeEmail",
                dataType : "json",
                data : {
                    "userId":userId,
                    "email":email
                },
                //请求成功
                success : function(result) {
                    if(result){
                        layer.closeAll();
                        $("#emailInfo").hide();
                        loadUserInfo();
                    }else{
                    }
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                }
            });

            //alert(email);
        },
        btn2: function(index, layero){
            layer.closeAll();
            $("#emailInfo").hide();
        },
        cancel:function (layero,index) {
            layer.closeAll();
            $("#emailInfo").hide();
        }
    });
}
//修改密码
function changePassword(){
    layer.open({
        type:1
        ,title:"修改密码"
        ,area:['480px','300px']
        ,content:$("#forgetPassword"),
        shade:0,
        btn: ['确定修改', '取消']
        ,btn1: function(index, layero){
            if(verifyCode(email)){
                var password=$("#userPassword1").val();
                $.ajax({
                    type : "post",
                    url : "/user/changePasswordByName",
                    dataType : "json",
                    data : {
                        "userName":userName,
                        "password":password
                    },
                    //请求成功
                    success : function(resultData) {
                        if(resultData.meta.status === "200"){
                            layer.msg('修改密码成功！',{
                                icon:1
                            });
                            //cancel();
                            $("#forgetPassword").hide();
                            //layer.closeAll();
                        }else{
                            layer.alert('修改密码失败！',{
                                icon:5
                            });
                        }
                    },
                    //请求失败，包含具体的错误信息
                    error : function(e){
                        layer.alert('修改密码失败！',{
                            icon:5
                        });
                    }
                });
            }else{

            }
        },
        btn2: function(index, layero){
            layer.closeAll();
            $("#forgetPassword").hide();
        },
        cancel:function (layero,index) {
            layer.closeAll();
            $("#forgetPassword").hide();
        }
    });
}
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
//验证码匹配
function verifyCode(email) {
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
//倒计时60s
var countdown = 60;
function setTime() {
    if (countdown == 0) {
        $("#emailCode").attr("disabled",false);
        $("#emailCode").val("获取验证码");
        countdown = 60;//60秒过后button上的文字初始化,计时器初始化;
        return;
    } else {
        $("#emailCode").attr("disabled",true);
        $("#emailCode").val("重新发送("+countdown+"s)");
        countdown--;
    }
    setTimeout(function() { setTime() },1000) //每1000毫秒执行一次
}
function sendEmail(email){
    $.ajax({
        type: "post",
        url: "/user/sendEmail",
        data: {
            "email":email
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                layer.alert('验证码发送成功！',{
                    icon:1
                });
                setTime();
            }
        },
        error:function(){
            layer.alert('验证码发送失败',{
                icon:5
            });
        }
    });
}
//获取邮箱
function getEmail(){
    $.ajax({
        type: "GET",
        url:"/user/getUserInfo",
        data:{
            "userId":userId
        },
        success:function(resultData){
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                email=resultData.data.userInfo.email;
                userName=resultData.data.userInfo.name;
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    });
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