var userId=getQueryVariable('userId'); //获取URL参数里的用户ID
var queryInfo= getQueryVariable('queryInfo');
$(function () {
    // layui初始化
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;

        // 表格渲染
        var tableIns=table.render({
            elem: '#taskList'
            , height: '700'
            , url: '/task/getAllTask' //数据接口
            , page: true //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , autoSort: false
            , where:{
                query: '',
                queryInfo: queryInfo,
                searchType:'',
                searchInfo: '',
                field: '',
                order: ''
            }
            , parseData: function(res) { //res 即为原始返回的数据
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.taskList //解析数据列表
                }
            }
            , cols: [[ //表头
                {field: 'id', title: '任务编号', align: 'center', width: '7%', fixed: 'left'}
                , {field: 'name', title: '任务名称', align: 'center', width: '7%'}
                , {field: 'points', title: '任务分值', align: 'center', width: '7%', sort: true}
                , {field: 'type', title: '文件类型', align: 'center', width: '7%', sort: true}
                , {field: 'release_time', title: '发布时间', align: 'center', width: '11%', sort: true}
                , {field: 'finish_time', title: '完成时间', align: 'center', width: '11%', sort: true}
                , {field: 'release_user_id', title: '发布者id', align: 'center', width: '11%', sort: true}
                , {field: 'accept_num', title: '接受者数量', align: 'center', width: '5%', sort: true}
                , {field: 'adopt_accept_id', title: '采纳接受任务编号', align: 'center', width: '5%', sort: true}
                , {field: 'ext1', title: '完成任务数量', align: 'center', width: '5%', sort: true}
                , {field: 'ext2', title: '所属审核者编号', align: 'center', width: '7%', sort: true}
                , {field: 'ext3', title: '点击量', align: 'center', width: '5%', sort: true}
                , {title: '操作', align: 'center', toolbar: '#barHandle'}
            ]]

        });
        //监听工具条
        table.on('tool(monitorToolbar)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

            // 工具条的点击事件
            if(layEvent === 'taskInfo'){
                alert(1)
                // 查看任务详情
                // yesReviewerSignUp(data.id,'yes',tableIns)
            } else if(layEvent === 'labelInfo'){
                alert(2)
                // 拒绝审核者账号注册
                //yesReviewerSignUp(data.id,'no',tableIns)
            }
        });
        //监听表头 用来排序
        table.on('sort(monitorToolbar)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            //监听排序
            console.log(obj.field); //当前排序的字段名
            console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
            console.log(this);//当前排序的 th 对象*/;
            tableIns.reload({
                initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
                , where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                    field: obj.field,
                    order: obj.type
                }, page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        });
        //监听下拉框change事件 layui不支持jQuery的change事件 用form.on('select(test)', function(data){})监听
        form.on('select(chooseSelect)', function(data){
            $("#searchInput").val("");
            switch (data.value) {
                //默认所有任务信息
                case '0':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query: '',
                            searchInfo:$("#searchInput").val()
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                //所有任务信息
                case '1':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            query: '',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 审核中
                case '2':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            query: 'underreview',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 已发布
                case '3':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            query: 'needlabel',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 未通过审核
                case '4':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            query: 'failedreview',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 已完成
                case '5':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            query: 'adopted',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                // 已失效
                case '6':
                    //表格重载
                    tableIns.reload({
                        where: { //设定异步数据接口的额外参数,可覆盖原有参数
                            query: 'expired',
                            searchInfo:$("#searchInput").val()
                        }
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
            }
        });
        //监听搜索类型
        var searchType='';
        form.on('select(searchType)', function(data){
            switch (data.value) {
                //默认任务名搜索
                case '0':
                    searchType='';
                    break;
                //任务名称
                case '1':
                    searchType='name';
                    break;
                // 任务编号
                case '2':
                    searchType='id';
                    break;
                //发布者id
                case '3':
                    searchType='release_user_id';
                    break;
                //所属审核者编号
                case '4':
                    searchType='ext2';
                    break;
            }
        });
        //监听搜索按钮点击事件
        $("#searchButton").click(function () {
            tableIns.reload({
                where: {
                    searchType:searchType,
                    searchInfo: $("#searchInput").val(),
                    field: '',
                    order: ''
                },
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        })
        $("#ChartBtn").click(function () {
            parent.$("#iframeMain").attr("src",$(this).attr("href")+'userId='+userId);
        })
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
