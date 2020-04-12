var email=null;
$(function () {
    //layui初始化
    layui.use('form', function(){
        var form = layui.form;
        // 登录事件
        $("#loginBtn").click(function () {
            form.on('submit(login)', function(data){
                login();
                return false;
            });
        });
        //按enter登录
        document.onkeydown= function (e) {
            var theEvent = window.event || e;
            var code = theEvent.keyCode || theEvent.which;
            if (code == 13) {
                $("#loginBtn").click();
            }
        }

        form.on('submit(code)', function(data){
            if(validate()){
                if(email!=null||email!=""){
                    sendEmail(email);
                }
            }
            return false;
        });
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
                window.setTimeout(function () {
                    if(window.sessionStorage.getItem('gotoUrl')!==null){
                        var url=window.sessionStorage.getItem('gotoUrl');
                        //登陆后更改地址栏userId参数
                        var newurl=changeURLArg(url,'userId',resultData.data.userId);
                        window.sessionStorage.setItem('userId',resultData.data.userId);
                        window.location.href=newurl;
                        sessionStorage.removeItem('gotoUrl');//gotoUrl session用完后要释放掉
                    }else{
                        window.location.href = resultData.data.url;
                    }
                },1000);
            }
        }
    })
}
//修改密码
function changePassword(){
    layer.open({
        type:1
        ,title:"修改密码"
        ,area:['450px','350px']
        ,content:$("#forgetPassword"),
        shade:0,
        btn: ['确定修改', '取消']
        ,btn1: function(index, layero){
            if(verifyCode(email)){
                var userName=$("#userName1").val();
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
                            //$("#forgetPassword").hide();
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
function getEmailByName(userName) {
    $.ajax({
        type: "post",
        url: "/user/getEmailByName",
        data: {
            "userName":userName
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                email=resultData.data.email;
            }
            else{
                layer.alert('用户不存在！',{
                    icon:5
                });
            }
        },
        error:function(){
        }
    });
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
    var name=$("#userName1").val();
    if(name!=null||name!=""){
        getEmailByName(name);
    }
}
//定义替换url指定参数的方法
function changeURLArg(url,arg,arg_val){
    var pattern=arg+'=([^&]*)';
    var replaceText=arg+'='+arg_val;
    if(url.match(pattern)){
        var tmp='/('+ arg+'=)([^&]*)/gi';
        tmp=url.replace(eval(tmp),replaceText);
        return tmp;
    }else{
        if(url.match('[\?]')){
            return url+'&'+replaceText;
        }else{
            return url+'?'+replaceText;
        }
    }
    return url+'\n'+arg+'\n'+arg_val;
}

