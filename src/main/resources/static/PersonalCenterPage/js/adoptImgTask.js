var userId=getIframeQueryVariable('userId'); //用户ID
var taskId=getIframeQueryVariable('taskId');
$(function () {
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        // 渲染表格
        var tableIns=table.render({
            elem: '#acceptList'
            , height: 700
            , url: '/accept/getAcceptListByTaskId' //数据接口
            , page: true //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , method: 'get'
            , where:{
                taskId:taskId
            }
            // 渲染表格结束后的回调函数
            , parseData: function(res) { //res 即为原始返回的数据
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.acceptList //解析数据列表
                }
            }
            , cols: [[ //表头
                {field: 'acceptId', title: '接受任务编号', align:'center',width: '15%',fixed: 'left'}
                , {field: 'acceptTime', title: '接受时间', align:'center',width: '20%'}
                , {field: 'finishTime', title: '提交时间', align:'center',width: '20%'}
                , {field: 'acceptUserName', title: '接收任务者用户名', align:'center',width: '20%'}
                , {field: 'state', title: '完成状态', align:'center',width: '15%'}
                , {title: '操作', align:'center',toolbar: '#barHandle'}
            ]]
            ,done: function(res, curr, count){
/*                if(page==='acceptTask'){
                    $(".adoptClass").hide();
                };
                if(query==='releasefinish' && taskType==='img'){
                    $(".checkClass").hide();
                }*/
            }
        });
        //监听工具条
        table.on('tool(monitorToolbar)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent==='adopt'){
                adoptFunc(data.acceptId);
            }else if(layEvent==='check'){
                checkFunc(data.acceptId)
            }
        });
        $("#GoBack").click(function () {
            parent.$("#iframeMain").attr("src",$(this).attr("href")+'userId='+userId);
        })
    });
})
// 获取URL里的参数
function getIframeQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
function checkFunc(acceptId) {
//查看标注详情
}
function adoptFunc(acceptId) {
    //选择采纳
    $.ajax({
        url: '/accept/adoptByAcceptId',
        type: "POST",
        data: {
            "acceptId": acceptId,
            "taskId":taskId
        },
        success: function (resultData) {
            alert("采纳成功！");
            parent.$("#iframeMain").attr("src","./Task.html?a=1&page=releaseTask&query=releasenotfinish&taskType=img&"+'userId='+userId);
        }
    })
}