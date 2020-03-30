var userId=getQueryVariable('userId'); //用户ID
//var page=getQueryVariable('page'); //页面名称
var page = 'releaseNotFinishTask';
var query=getQueryVariable('query'); //查询参数  needlable和adopted两种
//var query="needlable"; //默认查询参数  主页面点击已采纳后 变更
/************点击进入查看已采纳任务列表后，page变更，进入只能查看界面 有无userId均可********************/
var url='/task/getAllTask'
$(function () {
    // layui初始化
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        // 发布的任务
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
        // 渲染表格 *****由于默认界面是tableIns，所以选择先渲染tableIns2再渲染tableIns覆盖。
        var tableIns2=table.render({
            elem: '#taskList'
            , height: 700
            , url: url //数据接口
            , page: true //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , method: 'get'
            , autoSort: false
            , where:{
                query:'adopted',
                queryInfo:'',
                searchInfo:'',
                field:'',
                order:''
            }, page: {
                curr: 1 //重新从第 1 页开始
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
            , cols:[[ //表头
                {field: 'id', title: '任务编号', align:'center',width: '10%',fixed: 'left'}
                , {field: 'name', title: '任务名称', align:'center',width: '10%'}
                , {field: 'points', title: '任务分值', align:'center',width: '10%', sort: true}
                , {field: 'type', title: '文件类型', align:'center',width: '10%', sort: true}
                , {field: 'finish_time', title: '完成时间',align:'center', width: '15%', sort: true}
                , {title: '操作', align:'center',toolbar: '#barHandle'}
            ]]
            ,done: function(res, curr, count){
                if(page==='acceptTask'){
                    $(".adoptClass").hide();
                }
            }
        });
        var tableIns=table.render({
            elem: '#taskList'
            , height: 700
            , url: url //数据接口
            , page: true //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , method: 'get'
            , autoSort: false
            , where:{
                query:'needlable',
                queryInfo:'',
                searchInfo:'',
                field:'',
                order:''
            }, page: {
                curr: 1 //重新从第 1 页开始
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
            // 工具条的点击事件 点击量+1 并跳转详情页面
            $.ajax({
                type: "post",
                url:"/task/clickNumPlus",
                data:{
                    taskId:data.id
                }
            })
            checkFunc(data.id);
        });
        //监听表头 用来排序
        table.on('sort(monitorToolbar)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            //监听排序
            console.log(obj.field); //当前排序的字段名
            console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
            console.log(this);//当前排序的 th 对象*/;
            console.log(page);
            if(page != ''){
                tableIns.reload({
                    initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
                    ,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                        field: obj.field,
                        order: obj.type
                    },page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }else {
                tableIns2.reload({
                    initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
                    ,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                        field: obj.field,
                        order: obj.type
                    },page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        });
        $("#queryReleasedTask").click(function () {
            page = 'releaseNotFinishTask';
            $("#searchInput").val('');
            $("#queryReleasedTask").attr('class',"layui-this");
            $("#queryImgTask").attr('class',"");
            $("#queryTxtTask").attr('class',"");
            $("#queryAdoptedTask").attr('class',"");
            tableIns.reload({
                where:{
                    query:'needlable',
                    queryInfo:'',
                    searchInfo:'',
                    field:'',
                    order:''
                }
            });
        })
        $("#queryImgTask").click(function () {
            $("#searchInput").val('');
            page = 'releaseNotFinishTask';
            $("#queryReleasedTask").attr('class',"");
            $("#queryImgTask").attr('class',"layui-this");
            $("#queryTxtTask").attr('class',"");
            $("#queryAdoptedTask").attr('class',"");
            tableIns.reload({
                where:{
                    query:'needlable',
                    queryInfo:'img',
                    searchInfo:'',
                    field:'',
                    order:''
                }
            });
        })
        $("#queryTxtTask").click(function () {
            $("#searchInput").val('');
            page = 'releaseNotFinishTask';
            $("#queryReleasedTask").attr('class',"");
            $("#queryImgTask").attr('class',"");
            $("#queryTxtTask").attr('class',"layui-this");
            $("#queryAdoptedTask").attr('class',"");
            tableIns.reload({
                where:{
                    query:'needlable',
                    queryInfo:'doc',
                    searchInfo:'',
                    field:'',
                    order:''
                }
            });
        })

        $("#queryAdoptedTask").click(function () {
            page = '';//************--------------------****************/
            $("#searchInput").val('');
            $("#queryReleasedTask").attr('class', "");
            $("#queryImgTask").attr('class', "");
            $("#queryTxtTask").attr('class', "");
            $("#queryAdoptedTask").attr('class', "layui-this");
            tableIns2.reload();
        })
        //搜索按钮点击事件
        $("#searchButton").click(function () {
            if(page != ''){
                tableIns.reload({
                    where:{
                        searchInfo:$("#searchInput").val(),
                        field:'',
                        order:''
                    },
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
            else {
                tableIns2.reload({
                    where:{
                        searchInfo:$("#searchInput").val(),
                        field:'',
                        order:''
                    },
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        })
    });
    judgeLogin();
});
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);//一个界面
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}

// 查看任务
function checkFunc(taskId) {
    //判断传递userId为空时，接受任务按钮转登录，登录完后返回本页面并在地址栏添加userId参数..丢给杨哥做.这里可以为空
    if(userId == ""){

    }
    if(query == 'needlable'){

    }
    else if(query =='adopted'){

    }
    top.location.href="/ImageLabelTaskPage/index.html?" +
        "userId="+userId+
        "&pageType="+'otherReleasePage'+
        "&"+'taskId'+"="+taskId+
        "&pageFrom="+URLencode('/Home/Home.html')
        +"%3FuserId%3D"+userId
        +"%26page%3D"+'releaseNotFinishTask';
}
function URLencode(sStr) {
    return sStr.replace(/\%/g,"%25")
        .replace(/\+/g, '%2B')
        .replace(/\"/g,'%22')
        .replace(/\#/g,'%23')
        .replace(/\'/g, '%27')
        .replace(/\&/g, '%26')
        .replace(/\?/g, '%3F')
        .replace(/\=/g, '%3D')
        .replace(/\//g,'%2F');
}

//判断是否登录
function judgeLogin() {
    if(userId!=null){
        $.ajax({
            url: '/user/judgeLogin',
            type: "GET",
            data: {
                "userId": userId
            },
            success: function (resultData) {
                resultData = JSON.parse(resultData);
                if(resultData.meta.status === "200"){
                    var name=null;
                    $.ajax({
                        url: "/user/getUserInfo",
                        type: "get",
                        data: {
                            userId: userId
                        },
                        success: function (resultData) {
                            resultData = JSON.parse(resultData);
                            if (resultData.meta.status === "200") {
                                name = resultData.data.userInfo.name;
                                var div2=document.getElementById("logoff");
                                div2.style.display="none";
                                var div1=document.getElementById("login");
                                div1.style.display="block";
                                var div3=document.getElementById("logindiv");
                                div3.style.display="block";
                                var li1=document.getElementById("acceptli");
                                li1.style.display="block";
                                var li2=document.getElementById("releaseli");
                                li2.style.display="block";
                                var a=document.getElementById("userName");
                                a.innerText=name;
                                a.href="/PersonalCenterPage/index.html?userId="+userId;

                            }
                        }
                    });

                }else{
                    //window.location.href='/Home/Home.html';
                }

            }
        })
    }
}
//注销
function cancel() {
    sessionStorage.clear();   //清除所有session值
    window.location.href='/Home/Home.html';
}
