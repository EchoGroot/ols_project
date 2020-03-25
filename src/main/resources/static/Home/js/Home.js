var query="needlable"; //查询参数
$(function () {
    // layui初始化
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var url='';
        // 发布的任务
        $("#queryReleasedTask").click(function () {
            url='/task/getAllTask';
        })
        cols=[[ //表头
            {field: 'id', title: '任务编号', align:'center',width: '10%',fixed: 'left'}
            , {field: 'name', title: '任务名称', align:'center',width: '10%'}
            , {field: 'points', title: '任务分值', align:'center',width: '10%', sort: true}
            , {field: 'type', title: '文件类型', align:'center',width: '10%', sort: true}
            , {field: 'release_time', title: '发布时间',align:'center', width: '15%', sort: true}
            , {field: 'accept_num', title: '接受者数量',align:'center', width: '10%', sort: true}
            , {field: 'ext1', title: '完成任务数量',align:'center', width: '10%', sort: true}
            , {title: '操作', align:'center',toolbar: '#barHandle'}
        ]];

        // 渲染表格
        var tableIns=table.render({
            elem: '#taskList'
            ,height: 700
            , url: url //数据接口
            , page: true //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , method: 'get'
            , where:{
                query:query,
                queryInfo:'timeDown',
                searchInfo:''
            }
            // 渲染表格结束后的回调函数
            , parseData: function(res) { //res 即为原始返回的数据
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.taskList //解析数据列表
                }
            }
            , cols: cols
            ,done: function(res, curr, count){
                if(page==='acceptTask'){
                    $(".adoptClass").hide();
                }
            }

        });
        //监听工具条
        table.on('tool(monitorToolbar)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            // 工具条的点击事件
            if(layEvent === 'check'){
                if(page==='releaseTask'){
                    // 查看
                    checkFunc(data.id)
                }else if(page==='acceptTask'){
                    checkFunc(data.acceptId)
                }
            } else if(layEvent === 'adopt'){
                // 采纳
            }
        });
        //筛选按钮点击时间
        $("#searchButton").click(function (e) {
            e.preventDefault();
            chooesAndSearch(tableIns);
        })
    });

});

// 筛选个搜索
function chooesAndSearch(tableIns) {
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
        // 文档类型
    }
}
// 查看任务
function checkFunc(taskId) {
    var page1;
    var pageType;
    var idType;
    if(page==='releaseTask' ){
        idType='taskId';
        pageType='personalReleasePage';
        if(query==='releasefinish'){
            page1 ='releaseFinishTask';
        }else {
            page1 ='releaseNotFinishTask';
        }
    }else{
        idType='acceptId';
        if(query==='acceptfinish'){
            page1 ='acceptFinishTask';
            pageType='personalAcceptFinishPage';
        }else {
            page1 ='acceptNotFinishTask';
            pageType='personalAcceptNotFinishPage';
        }
    }
    top.location.href="/ImageLabelTaskPage/index.html?" +
        "userId="+userId+
        "&pageType="+pageType+
        "&"+idType+"="+taskId+
        "&pageFrom=%2FPersonalCenterPage%2Findex.html"
        +"%3FuserId%3D"+userId
        +"%26page%3D"+page1;
}