var userId=getQueryVariable('userId'); //用户ID
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 加载用户个人信息
    loadUserInfo();
});
// 加载个人用户信息
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
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var t=window.parent.document.getElementById("iframeMain").contentWindow.location;
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}