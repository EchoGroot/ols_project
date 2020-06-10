var taskId=getQueryVariable('taskId'); //用户ID

$(function () {
    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#acceptTaskTable'
            ,height: 312
            ,url: '/accept/getAcceptTaskByTaskId/' //数据接口
            ,page: true //开启分页
            ,where: {taskId: taskId}
            ,cols: [[ //表头
                {field: 'id', title: '接受任务编号', width:150, sort: true, fixed: 'left'}
                ,{field: 'name', title: '完成任务的用户名', width:150}
                ,{field: 'task_id', title: '任务号', width:150, sort: true}
                ,{field: 'accept_time', title: '接受时间', width:150}
                ,{field: 'finish_time', title: '完成时间', width: 150}
            ]]
            ,parseData: function(res) { //res 即为原始返回的数据
                console.log(res)
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.message, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.acceptTaskList//解析数据列表
                };
            }
        });

    });
})

// 获取URL里的参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
};

// www.baidu.com/index.html?userid=1235

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
            parent.$("#iframeMain").attr("src","./Task.html?a=1&page=releaseTask&query=releasenotfinish&taskType=doc"+'userId='+userId);
        }
    })
}
