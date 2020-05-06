var userId=getQueryVariable('userId');  //用户ID
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // layui初始化
    layui.use(['layer', 'form', 'table'], function () {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var url = '';
        // 渲染表格
        var tableIns = table.render({
            elem: '#punishmentList'
            , height: 700
            , url: '/information/getPInformationById/'//数据接口
            , page: true //开启分页
            , limits: [15, 30, 50, 100]
            , limit: 15
            , method: 'get'
            , where: {
                userId: userId,
            }
            // 渲染表格结束后的回调函数
            , parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.messageList //解析数据列表
                }
            }
            , cols: [[ //表头
                {field: 'id', title: '消息ID', align: 'center', width: '10%', fixed: 'left', sort: true}
                , {field: 'information', title: '惩罚原因', align: 'center', width: '20%'}
                // , {field: 'deductPoints', title: '奖励积分', align: 'center', width: '12%', sort: true}
                , {field: 'create_time', title: '发布时间', align: 'center', width: '18%', sort: true}
            ]]
        });
        //监听工具条
        table.on('tool(monitorToolbar)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        })
    })
})
// 获取URL参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null && unescape(r[2]) !== 'null') return unescape(r[2]);
    if (null !== window.sessionStorage.getItem(name)) {
        return window.sessionStorage.getItem(name);
    }
    return null;
}