//var adminUserId=getQueryVariable('userId'); //获取URL参数里的用户ID
var userId=getQueryVariable('userId'); //用户ID
$(function () {
    // layui初始化
    layui.use(['layer', 'form', 'table'], function () {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;

        // 表格渲染
        var tableIns = table.render({
            elem: '#messageList'
            , height: '700'
            , url: '/information/getAllMessage/' //数据接口
            , page: true //开启分页
            , limits: [15, 30, 50, 100]
            , limit: 15
            , where: {queryInfo: 'timeDown', searchInfo: ''}
            , parseData: function (res) { //res 即为原始返回的数据
                console.log(res)
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.messageList //解析数据列表
                }
            }
            , cols: [[ //表头
                {field: 'id', title: '消息ID', align: 'center', width: '15%', fixed: 'left', sort: true}
                , {field: 'user_id', title: '用户编号', align: 'center', width: '15%', sort: true}
                , {field: 'information', title: '奖惩信息', align: 'center', width: '50%'}
                , {field: 'create_time', title: '发布时间', align: 'center', width: '20%', sort: true}
            ]]
        });
        //监听工具条
        table.on('tool(monitorToolbar)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）


            if(layEvent === 'yes'){
                punishmentFunc(userId,'yes',tableIns,'扣除用户（'+userId+'）的积分。')
            }

        });


    });
});

// 发布奖惩信息
function createMessage(userId,information,deductPoints,type) {
    $.ajax({
        type: "POST",
        url:"/information/createMessage",
        data:{
            "userId":userId,
            "information":information,
            "deductPoints":deductPoints,
            "type":type
        },
        success:function(resultData){
            resultData=JSON.parse(resultData)
            if(resultData.meta.status === "200"){
                layer.msg('操作成功', {
                    icon: 1, //绿勾
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                //这里以搜索为例
                tableIns.reload({});
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    });
}
// 发布惩罚信息
function punishmentFunc() {
        // 打开弹窗
        layer.open({
            title: '惩罚',
            area: ['400px', '250px'],
            btnAlign: 'c',
            closeBtn:'1',//右上角的关闭
            content: '<div><div>请输入用户ID：  <textarea class="layui-layer-input" name="txt_remark" id="toID"style="width:50%;height:20%;line-height:20px;padding:6px 10px;"></textarea></div>' +
                '<div>请输入惩罚理由： <textarea class="layui-layer-input" name="txt_remark" id="information"  style="width:100%;height:30%;line-height:20px;padding:6px 10px;"></textarea></div>' +
                '<div>扣除积分：<textarea class="layui-layer-input" name="txt_remark" id="remark" style="width:50%;height:20%;line-height:20px;padding:6px 10px;"></textarea></div></div>',
            btn:['确认','取消'],
            yes: function (index, layero) {
                createMessage($("#toID").val(),$("#information").val(),$('#remark').val(),0);
                layer.close(index);//可执行确定按钮事件并把备注信息（即多行文本框值）存入需要的地方
            },
            btn2:function(index, layero)
            {
                layer.close(index);
            }
        });
    }

// 发布奖励信息
function rewardFunc() {
    // 打开弹窗
    layer.open({
        title: '奖励',
        area: ['400px', '250px'],
        btnAlign: 'c',
        closeBtn:'1',//右上角的关闭
        content: '<div><div>请输入用户ID：  <textarea class="layui-layer-input" name="txt_remark" id="toID"style="width:50%;height:20%;line-height:20px;padding:6px 10px;"></textarea></div>' +
            '<div>请输入奖励理由： <textarea class="layui-layer-input" name="txt_remark" id="information"  style="width:100%;height:30%;line-height:20px;padding:6px 10px;"></textarea></div>' +
            '<div>奖励积分：<textarea class="layui-layer-input" name="txt_remark" id="remark" style="width:50%;height:20%;line-height:20px;padding:6px 10px;"></textarea></div></div>',
        btn:['确认','取消'],
        yes: function (index, layero) {
            createMessage($("#toID").val(),$("#information").val(),$('#remark').val(),1);
            layer.close(index);//可执行确定按钮事件并把备注信息（即多行文本框值）存入需要的地方
        },
        btn2:function(index, layero)
        {
            layer.close(index);
        }
    });
}

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