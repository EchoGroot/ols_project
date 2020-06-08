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
            , where:{
                queryInfo:'timeDown',
                searchInfo:''
            }
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
                , {field: 'task_id', title: '任务编号', align:'center',width: '11%', sort: true}
                , {field: 'type', title: '任务类型',align:'center', width: '11%',sort: true}
                , {field: 'message', title: '举报信息', align:'center',width: '11%'}
                , {field: 'ishandled', title: '是否处理', align:'center',width: '11%',sort: true}
                , {field: 'isfirst', title: '是否查看',align:'center', width: '11%',sort: true}
               // , {field: 'response', title: '是否回复',align:'center', width: '10%',sort: true}
                , {field: 'create_time', title: '发布时间',align:'center', width: '15%', sort: true}
                , {title: '操作', align: 'center', toolbar: '#barHandle', width: '10%'}
            ]]
        });
        //监听工具条
        table.on('tool(monitorToolbar)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent==="handle"){
                replyFunc(data)
            }
        });
        //筛选按钮点击事件
        //监听下拉框change事件 layui不支持jQuery的change事件 用form.on('select(test)', function(data){})监听
        form.on('select(chooseSelect)', function(data){
            $("#searchInput").val("");
            switch (data.value) {
                //默认所有举报信息
                case '0':
                    //表格重载
                    tableIns.reload({
                        where:{
                            queryInfo:'timeDown',
                            searchInfo:$("#searchInput").val()
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                //已处理
                case '1':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'isHandled',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 未处理
                case '2':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'noHandled',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 时间升序
                case '3':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'timeUp',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 时间降序
                case '4':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'timeDown',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
            }
        })
    });

    //监听搜索按钮点击事件
    $("#searchButton").click(function () {
        tableIns.reload({
            where: {
                queryInfo:'user_id',
                searchInfo: $("#searchInput").val(),
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })

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

// 回复举报信息
function replyFunc(data) {


    // 打开弹窗
    layer.open({
        title: '回复举报信息',
        area: ['500px', '300px'],
        btnAlign: 'c',
        closeBtn:'1',//右上角的关闭
        content: '<div>' +
            '<div >举报者ID： <textarea class="layui-layer-input"  name="txt_remark" id="user_id"  style="width:50%;height:10%;line-height:20px;padding:10px 10px;">'+data.user_id+'</textarea></div>'+
            '<div >请输入回复信息： <textarea class="layui-layer-input" placeholder="请输入回复信息" name="txt_remark" id="message"  style="width:100%;height:70%;line-height:20px;padding:10px 10px;"></textarea></div>' +
            '</div>',
        btn:['确认','取消'],
        yes: function (index, layero) {
            replyMessage($("#message").val());
            layer.msg('回复成功', {
                icon: 1, //绿勾
                time: 2000 //2秒关闭（如果不配置，默认是3秒）
            });
            layer.close(index);//可执行确定按钮事件并把备注信息（即多行文本框值）存入需要的地方
        },
        btn2:function(index, layero)
        {
            layer.msg('取消回复', {
                icon: 5, //红色不开心
                time: 2000 //2秒关闭（如果不配置，默认是3秒）
            });
            layer.close(index);
        }
    });
}
// 回复举报信息
function replyMessage(message) {
    $.ajax({
        type: "GET",
        url:"/message/replyMessage",
        data:{
            "Message":message,
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