var adminUserId=getQueryVariable('userId'); //获取URL参数里的用户ID
$(function () {
    // layui初始化
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;

        // 表格渲染
        var tableIns=table.render({
            elem: '#userList'
            , height: '700'
            , url: '/user/getUserSignUp/' //数据接口
            , page: true //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , where:{queryInfo:'timeDown',searchInfo:'',userId:adminUserId}
            , parseData: function(res) { //res 即为原始返回的数据
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.userList //解析数据列表
                }
            }
            , cols: [[ //表头
                {field: 'id', title: '用户ID', align:'center',width: '10%',fixed: 'left', sort: true}
                , {field: 'name', title: '用户名', align:'center',width: '10%', sort: true}
                , {field: 'sex', title: '性别', align:'center',width: '10%', sort: true}
                , {field: 'birthday', title: '出生日期', align:'center',width: '15%', sort: true}
                , {field: 'email', title: '邮箱', align:'center',width: '15%'}
                , {field: 'role', title: '角色',align:'center', width: '10%'}
                , {field: 'signUpTime', title: '注册时间',align:'center', width: '10%', sort: true}
                , {field: 'points', title: '积分', align:'center',width: '10%', sort: true}
                , {title: '操作日志', align:'center',toolbar: '#barHandle'}
            ]]

        });

        //监听工具条
        table.on('tool(monitorToolbar)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

            // 工具条的点击事件
            if(layEvent === 'log'){
                // 查看用户操作日志
                // yesReviewerSignUp(data.id,'yes',tableIns)
            } else if(layEvent === 'no'){
                // 拒绝审核者账号注册
                //yesReviewerSignUp(data.id,'no',tableIns)
            }
        });

        //筛选按钮点击事件
        $("#searchButton").click(function (e) {
            // 阻止a标签的默认行为
            e.preventDefault();
            // 根据下拉选择框的值来判断
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
                //普通用户
                case '1':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'user',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 审核者
                case '2':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'reviewer',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 管理者
                case '3':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            queryInfo: 'admin',
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
                            queryInfo: 'woman',
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

});
// 允许或者拒绝审核者账号注册
function yesReviewerSignUp(userId,operation,tableIns) {
    $.ajax({
        type: "POST",
        url:"/user/yesReviewerSignUp",
        data:{
            "userId":userId,
            "operation":operation,
            "adminUserId":adminUserId
        },
        success:function(resultData){
            resultData=JSON.parse(resultData)
            if(resultData.meta.status === "200"){
                layer.msg('操作成功', {
                    icon: 1, //绿勾
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                // 表格重载
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
// 获取URL参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}