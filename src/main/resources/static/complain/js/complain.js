//用户举报页面
var userId=getQueryVariable('userId'); //用户ID
var taskId=getQueryVariable('taskId'); // 任务ID
var type=getQueryVariable('type');//任务类型
// $.Lemoji({
//     emojiInput: '#L_content',
//     emojiBtn: '#btn',
//     position: 'LEFTBOTTOM',
//     length: 8
//   });
$(function () {
    //layui初始化
    layui.use(['layer','form'],function () {
        var layer = layui.layer;
        var form = layui.form;
    })
    $("#submitBtn").click(function () {
        //提交举报信息
        var message="举报理由："+$("#L_title").val()+"   详情描述："+$("#L_content").val();
        var type=$("#type").val();
        $.ajax({
            url: '/message/createMessage',
            type: "GET",
            data: {
                "userId": userId,
                "taskId": taskId,
                "Message":message,
                "type":type
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
});
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


