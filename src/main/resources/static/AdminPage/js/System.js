var page=getIframeQueryVariable('page'); //页面名称
$(function () {
    // layui初始化
    layui.use(['layer', 'form', 'table'], function () {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        // 表格渲染
        // 表格渲染
        tableIns = table.render({
            elem: '#sysList'
            , height: '700'
            , url: '/system/getAllSystem'
            , type: "GET"
            , page: true //开启分页
            , limits: [15, 30, 50, 100]
            , limit: 15
            , parseData: function (res) { //res 即为原始返回的数据
                console.log(res)
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.sysList //解析数据列表
                }
            }
            , cols: [[ //表头
                {field: 'accept_user_id', title: '用户ID', align: 'center', width: '20%', fixed: 'left', sort: true}
                , {field: 'create_time', title: '发布时间', align: 'center', width: '20%', sort: true}
                , {field: 'message', title: '消息', align: 'center', width: '50%'}
            ]]
        });
        table.on('tool(monitorToolbar)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        });
    });
});
// 获取URL里的参数
function getIframeQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
