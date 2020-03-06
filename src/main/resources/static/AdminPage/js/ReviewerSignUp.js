$(function () {
    console.log('ReviewerSignUp')
    layui.use('table', function() {
        var table = layui.table;
        table.render({
            elem: '#userList'
            , height: '779'
            , url: '/user/getReviewerSignUp/' //数据接口
            , page: true //开启分页
            , limits: [17,30,50,100]
            , limit: 17
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
                , {field: 'ext1', title: '信息', align:'center',width: '10%'}
                , {title: '操作', align:'center',toolbar: '#barHandle'}
            ]]

        });
    });
    //监听工具条
    table.on('tool(monitorToolbar)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        if(layEvent === 'detail'){ //查看
            //do somehing
        } else if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'edit'){ //编辑
            //do something

            //同步更新缓存对应的值
            obj.update({
                username: '123'
                ,title: 'xxx'
            });
        } else if(layEvent === 'LAYTABLE_TIPS'){
            layer.alert('Hi，头部工具栏扩展的右侧图标。');
        }
    });
})