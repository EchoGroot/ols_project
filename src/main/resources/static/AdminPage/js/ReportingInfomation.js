var adminUserId=getQueryVariable('userId'); //获取URL参数里的用户ID
$(function () {
    // layui初始化
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;

        // 表格渲染
        var tableIns=table.render({
            elem: '#messageList'
            , height: '700'
            , url: '/message/getAllMessage/' //数据接口
            , page: true //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , parseData: function(res) { //res 即为原始返回的数据
                console.log(res)
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.messageList //解析数据列表
                }
            }
            , cols: [[ //表头
                {field: 'id', title: '消息ID', align:'center',width: '10%',fixed: 'left', sort: true}
                , {field: 'user_id', title: '举报者编号', align:'center',width: '10%', sort: true}
                , {field: 'task_id', title: '任务编号', align:'center',width: '10%', sort: true}
                , {field: 'message', title: '举报信息', align:'center',width: '15%'}
                , {field: 'ishandled', title: '是否处理', align:'center',width: '15%',sort: true}
                , {field: 'isfirst', title: '是否第一次查看',align:'center', width: '20%',sort: true}
               // , {field: 'response', title: '是否回复',align:'center', width: '10%',sort: true}
                , {field: 'create_time', title: '发布时间',align:'center', width: '10%', sort: true}
            ]]
        });
        //监听工具条
        table.on('tool(monitorToolbar)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        });


    });
});
// 获取URL参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}