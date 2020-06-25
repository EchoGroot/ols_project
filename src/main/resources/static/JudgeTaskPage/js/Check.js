var userId=getQueryVariable('userId'); //用户ID
var page=getQueryVariable('page'); //页面名称
var isChecked=getQueryVariable('isChecked'); //是否审核完成
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    if(getQueryVariable('isChecked') === '0'){
        // 隐藏下拉选择框和搜索
        $("#queryForm").hide();
    }
    // layui初始化
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;

        var url='';
        var pageFlag=false;
        var method='';
        var height=0;
        var where={};
        var cols=null;
        if(isChecked==='0'){
            url='/task/getNotCheckedTask/';
            pageFlag=false;
            method ='post';
            height =740;
            where={
                userId:userId
            };
            cols=[[ //表头
                {field: 'id', title: '任务编号', align:'center',width: '10%',fixed: 'left', sort: true}
                , {field: 'name', title: '任务名称', align:'center',width: '15%', sort: true}
                , {field: 'points', title: '任务分值', align:'center',width: '10%', sort: true}
                , {field: 'state', title: '状态', align:'center',width: '10%'}
                , {field: 'type', title: '文件类型', align:'center',width: '10%', sort: true}
                , {field: 'release_time', title: '发布时间',align:'center', width: '15%', sort: true}
                , {field: 'release_user_id', title: '发布者编号',align:'center', width: '10%', sort: true}
                , {title: '操作', align:'center',toolbar: '#barHandle'}
            ]];
        }else{
            url='/task/getFinishCheckTaskByUserId';
            pageFlag=true;
            method ='get';
            height=700;
            where={
                userId:userId,
                queryInfo:'timeDown',
                searchInfo:''
            };
            cols=[[ //表头
                {field: 'judgeId', title: '审核编号', align:'center',width: '10%',fixed: 'left'}
                , {field: 'taskName', title: '任务名称', align:'center',width: '12%'}
                , {field: 'taskId', title: '任务编号', align:'center',width: '10%'}
                , {field: 'releaseTime', title: '任务发布时间', align:'center',width: '10%', sort: true}
                , {field: 'releaseUserName', title: '任务发布者', align:'center',width: '8%'}
                , {field: 'type', title: '文件类型', align:'center',width: '7%'}
                , {field: 'isPassed', title: '通过审核',align:'center', width: '7%'}
                , {field: 'message', title: '审核答复信息',align:'center', width: '15%'}
                , {field: 'judgeTime', title: '审核时间',align:'center', width: '10%', sort: true}
                , {title: '操作', align:'center',toolbar: '#barHandle'}
            ]];
        }
        // 渲染表格
        var tableIns=table.render({
            elem: '#taskList'
            , height: height
            , url: url //数据接口
            , page: pageFlag //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , method: method
            , where:where
            , parseData: function(res) { //res 即为原始返回的数据
                return {
                    "code": res.meta.status, //解析接口状态
                    "msg": res.meta.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.taskList //解析数据列表
                }
            }
            , cols: cols
            // 渲染完成的回调函数
            ,done: function(res, curr, count){
                if(page==='finishCheck'){
                    $(".layui-btn-normal").hide();
                    $(".layui-btn-danger").hide();
                }
            }

        });

        //监听工具条
        table.on('tool(monitorToolbar)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            // 工具条的点击事件
            // 通过审核
            if(layEvent === 'yes'){
                taskPassOrNotPassAuditsFunc(data.id,'yes',tableIns,'恭喜您！您发布的任务（编号'+data.id+'）已通过审核。')
            // 未通过审核
            } else if(layEvent === 'no'){
                noFunc(data.id,tableIns);
            // 查看
            }else if(layEvent === 'check'){
                if(page==='finishCheck'){
                    checkFunc(data.taskId,data.type)
                }else{
                    checkFunc(data.id,data.type)
                }
            }
        });
        // 监听下拉选择框
        form.on('select(chooseSelectFilter)', function (data) {
            chooseAndSearch(tableIns);
        });
        //筛选按钮点击时间
        $("#searchButton").click(function (e) {
            e.preventDefault();
            chooseAndSearch(tableIns);
        })
    });

});
// 提交是否通过审核
function taskPassOrNotPassAuditsFunc(taskId,operation,tableIns,message) {
    console.log(taskId+operation);
    $.ajax({
        type: "POST",
        url:"/task/taskPassOrNotPassAudits",
        data:{
            "userId":userId,
            "taskId":taskId,
            "message":message,
            "operation":operation
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
// 获取iframe的URL参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}
// 查看
function checkFunc(taskId,type) {
    if(type=="图片"){
        top.location.href="/ImageLabelTaskPage/index.html?" +
            "userId="+userId+
            "&pageType="+'otherReleasePage'+
            "&"+'taskId'+"="+taskId+
            "&pageFrom=%2FJudgeTaskPage%2Findex.html"
            +"%3FuserId%3D"+userId
            +"%26page%3D"+page;
    }
    else {
        top.location.href="/TextLabelTaskPage/TextLabelTask.html?" +
            "userId="+userId+
            "&pageType="+'otherReleasePage'+
            "&"+'taskId'+"="+taskId+
            "&pageFrom=%2FJudgeTaskPage%2Findex.html"
            +"%3FuserId%3D"+userId
            +"%26page%3D"+page;
    }
}
// 未通过审核
function noFunc(taskId,tableIns) {
    // 打开弹窗
    layer.open({
//formType: 2,//这里依然指定类型是多行文本框，但是在下面content中也可绑定多行文本框
        title: '请输入驳回理由',
        area: ['300px', '240px'],
        btnAlign: 'c',
        closeBtn:'1',//右上角的关闭
        content: '<div><textarea class="layui-layer-input" name="txt_remark" id="remark" style="width:100%;height:100%;line-height:20px;padding:6px 10px;"></textarea></div>',
        btn:['确认','取消'],
        yes: function (index, layero) {
            taskPassOrNotPassAuditsFunc(taskId,"no",tableIns,'很抱歉！您发布的任务（编号'+taskId+'）未能通过审核，理由：'+$('#remark').val());
            layer.close(index);//可执行确定按钮事件并把备注信息（即多行文本框值）存入需要的地方
        },
        btn2:function(index, layero)
        {
            layer.close(index);
        }
    });
}
// 筛选和搜索
function chooseAndSearch(tableIns) {
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
        // 通过
        case '1':
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
        case '2':
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
}