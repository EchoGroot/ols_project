//用户举报页面
var userId=getQueryVariable('userId'); //用户ID
var taskId=getQueryVariable('taskId'); // 任务ID

$(function () {
    //layui初始化
    layui.use(['layer','form'],function () {
        var layer = layui.layer;
        var form = layui.form;
    })
    $("#submitBtn").click(function () {
        //
        var message="举报理由："+$("#Complain").val()+"/n详情描述："+$("#L_content").val();
        $.ajax({
            url: '/message/createMessage',
            type: "GET",
            data: {
                "userId": userId,
                "taskId": taskId,
                "Message":message
            },
            success: function (resultData) {
                resultData = JSON.parse(resultData);
                if (resultData.meta.status === "200") {
                    layer.msg('提交成功', {
                        icon: 1, //红色不开心
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                } else {
                    layer.msg('提交举报信息失败，请刷新页面', {
                        icon: 5, //红色不开心
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });

                }
            }
        })
    });


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

// 从URL中获取参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}
//举报任务

