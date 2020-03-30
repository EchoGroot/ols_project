var userId=getQueryVariable('userId');
$(function () {
    //alert(userId);
    loadUserInfo();
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
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
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
