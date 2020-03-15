$(function () {
    if(getQueryVariable('isChecked') === '0'){
        $("#queryForm").hide();
    }
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;

        var tableIns=table.render({
            elem: '#taskList'
            , height: '750'
            , url: '/task/getNotCheckedTask/' //数据接口
            , page: false //开启分页
            , method: 'post'
            , where:{userId:getQueryVariable('userId')}
            , parseData: function(res) { //res 即为原始返回的数据
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.taskList //解析数据列表
                }
            }
            , cols: [[ //表头
                {field: 'id', title: '任务编号', align:'center',width: '10%',fixed: 'left', sort: true}
                , {field: 'name', title: '任务名称', align:'center',width: '10%', sort: true}
                , {field: 'points', title: '任务分值', align:'center',width: '10%', sort: true}
                , {field: 'state', title: '状态', align:'center',width: '10%'}
                , {field: 'type', title: '文件类型', align:'center',width: '10%', sort: true}
                , {field: 'release_time', title: '发布时间',align:'center', width: '15%', sort: true}
                , {field: 'release_user_id', title: '发布者编号',align:'center', width: '10%', sort: true}
                , {title: '操作', align:'center',toolbar: '#barHandle'}
            ]]

        });


        //监听工具条
        table.on('tool(monitorToolbar)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if(layEvent === 'yes'){
                yesReviewerSignUp(data.id,'yes',tableIns)
            } else if(layEvent === 'no'){
                yesReviewerSignUp(data.id,'no',tableIns)
            }
        });

        //筛选按钮点击时间
        $("#searchButton").click(function (e) {
            e.preventDefault();
            console.log($("#chooseSelect").val())
            switch ($("#chooseSelect").val()) {
                //只执行搜索
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
                //待处理
                case '1':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'notHandled',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 通过
                case '2':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'passed',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 不通过
                case '3':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'notPassed',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 性别男
                case '4':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'man',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 性别女
                case '5':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'wuman',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 时间升序
                case '6':
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
                case '7':
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

})
function yesReviewerSignUp(userId,operation,tableIns) {
    $.ajax({
        type: "POST",
        url:"/user/yesReviewerSignUp",
        contentType: "application/json",
        data:JSON.stringify({
            "userId":userId,
            "operation":operation
        }),
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
function chooseFunc(userId,operation,tableIns) {
    $.ajax({
        type: "POST",
        url:"/user/yesReviewerSignUp",
        contentType: "application/json",
        data:JSON.stringify({
            "userId":userId,
            "operation":operation
        }),
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
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
