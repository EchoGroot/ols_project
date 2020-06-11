var userId=getIframeQueryVariable('userId'); //用户ID
var query=getIframeQueryVariable('query'); //查询参数
var taskType="img";//文件类型
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 已发布已完成
    if(query==='releasefinish'){
        // 给下拉选择框赋值
        $("#chooseSelect").html(
            '<option value="10">选择完成状态</option>'+
            '<option value="11">已完成</option>'+
            '<option value="12">已失效</option>'+
            '<option value="13">已删除</option>'
        )
        // 已发布未完成
    }else if(query==='releasenotfinish'){
        $("#chooseSelect").html(
            '<option value="20">选择完成状态</option>'+
            '<option value="21">审核中</option>'+
            '<option value="22">已发布</option>'+
            '<option value="23">未通过审核</option>'+
            '<option value="24">未标注示例</option>'
        )
        // 已接受未完成
    }else if(query === "acceptnotfinish"){
        $("#chooseSelect").html(
            '<option value="30">未完成</option>'
        )
        // 已接受已完成
    }else if(query === "acceptfinish"){
        $("#chooseSelect").html(
            '<option value="40">选择接受状态</option>'+
            '<option value="41">已提交</option>'+
            '<option value="42">已采纳</option>'+
            '<option value="43">未采纳</option>'+
            '<option value="44">已失效</option>'
        )
    }
    // layui初始化
    layui.use(['layer', 'form','table'], function() {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var url='';
        // 发布的任务
        if(query==='releasefinish' || query==='releasenotfinish'){
            url='/task/getReleaseImgTaskByUserId/';
            cols=[[ //表头
                {field: 'id', title: '任务编号', align:'center',width: '10%',fixed: 'left'}
                , {field: 'name', title: '任务名称', align:'center',width: '10%'}
                , {field: 'points', title: '任务分值', align:'center',width: '10%', sort: true}
                , {field: 'state', title: '状态', align:'center',width: '10%', sort: true}
                , {field: 'type', title: '文件类型', align:'center',width: '10%'}
                , {field: 'release_time', title: '发布时间',align:'center', width: '15%', sort: true}
                , {field: 'accept_num', title: '接受者数量',align:'center', width: '10%', sort: true}
                , {field: 'ext1', title: '完成任务数量',align:'center', width: '10%', sort: true}
                , {title: '操作', align:'center',toolbar: '#barHandle'}
            ]];
        }else{
            // 接受的任务
            url='/task/getAcceptImgTaskByUserId/';
            cols=[[ //表头
                {field: 'acceptId', title: '任务编号', align:'center',width: '7%',fixed: 'left'}
                , {field: 'taskName', title: '任务名称', align:'center',width: '7%'}
                , {field: 'points', title: '任务分值', align:'center',width: '7%', sort: true}
                , {field: 'taskState', title: '任务状态', align:'center',width: '7%'}
                , {field: 'type', title: '文件类型', align:'center',width: '7%'}
                , {field: 'releaseTime', title: '发布时间',align:'center', width: '9%', sort: true}
                , {field: 'releaseName', title: '发布者',align:'center', width: '7%'}
                , {field: 'acceptNum', title: '任务接受者数量',align:'center', width: '10%', sort: true}
                , {field: 'acceptTime', title: '接受时间',align:'center', width: '9%', sort: true}
                , {field: 'acceptState', title: '接受状态',align:'center', width: '7%', sort: true}
                , {field: 'finishTime', title: '提交时间',align:'center', width: '9%', sort: true}
                , {title: '操作', align:'center',toolbar: '#barHandle'}
            ]];
        }
        // 渲染表格
        var tableIns=table.render({
            elem: '#taskList'
            , height: 700
            , url: url //数据接口
            , page: true //开启分页
            , limits: [15,30,50,100]
            , limit: 15
            , method: 'get'
            , where:{
                userId:userId,
                query:query,
                queryInfo:'',
                searchInfo:'',
                field:'',
                order:''
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
                if(query==='acceptfinish' || query==='acceptnotfinish'){ // acceptnotfinish releasefinish releasenotfinish
                    $(".adoptClass").hide();
                };
                if(query==='acceptnotfinish'){
                    $(".checkClass").html("标注");;
                };
                if(query==='releasefinish'){
                    $(".checkClass").hide();
                    $(".adoptClass").hide();
                    $(".downloadClass").show();
                };
            }
        });
        //监听表头 用来排序
        table.on('sort(monitorToolbar)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            //监听排序
            console.log(obj.field)
            console.log(obj.type)
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
        // 监听下拉选择框
        form.on('select(chooseSelectFilter)', function (data) {
            switch (data.value) {
                //只执行搜索
                case '10':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '11':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'finish',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '12':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'disable',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                case '13':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'delete',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '20':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '21':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'check',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '22':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'passed',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '23':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'notPassed',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '24':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'notlabelexm',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;

                    /***********************************************************/
                case '30':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '40':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'',
                            fiedl:'',
                            order:'',
                            searchInfo:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '41':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'finish',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '42':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'adopted',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '43':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'notAdopted',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case '44':
                    //表格重载
                    tableIns.reload({
                        where:{
                            query:query,
                            queryInfo:'invalid',
                            searchInfo:'',
                            field:'',
                            order:''
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
            }
        });
        //监听工具条
        table.on('tool(monitorToolbar)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            // 工具条的点击事件
            if(layEvent === 'check'){
                if(query==='acceptfinish' || query==='acceptnotfinish'){
                    checkFunc(data.acceptId)
                }else{
                    checkFunc(data.id)
                }
            } else if(layEvent === 'adopt'){
                // 采纳
                chooseAdoptFunc(data.id);
            }else if(layEvent === 'download'){
                // 下载
                downloadFunc(data.id);
            }
        });
        //查询按钮点击时间
        $("#searchButton").click(function (e) {
            e.preventDefault();
            tableIns.reload({
                where:{
                    searchInfo:$("#searchInfo").val()
                },
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        })
    });

});
// 获取URL里的参数
function getIframeQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
// 查看任务
function checkFunc(taskId) {
    var page1;
    var pageType;
    var idType;
    if(query==='releasefinish'){
        idType='taskId';
        pageType='personalReleasePage';
        page1='releaseFinishImgTask';
    }else if(query==='releasenotfinish'){
        idType='taskId';
        pageType='personalReleasePage';
        page1='releaseNotFinishImgTask';
    }
    else {
        idType='acceptId';
        if(query==='acceptfinish'){
            pageType='personalAcceptFinishPage';
            page1='acceptFinishImgTask';
        }else {
            pageType='personalAcceptNotFinishPage';
            page1='acceptNotFinishImgTask';
        }
    }
    //url = window.location.href
    top.location.href="/ImageLabelTaskPage/index.html?" +
        "userId="+userId+
        "&pageType="+pageType+
        "&"+idType+"="+taskId+
        "&pageFrom= %2FPersonalCenterPage%2Findex.html"
        +"%3FuserId%3D"+userId
        +"%26page%3D"+page1;
}

function chooseAdoptFunc(taskId) {
    parent.$("#iframeMain").attr("src","./adoptImgTask.html?a=1&"+'taskId='+taskId+'&userId='+userId);
}
function downloadFunc(taskId) {
    //window.open("http://localhost:8080/task/downloadFinishedTask?taskId="+taskId);
    window.open("http://yuyy.info:8081/task/downloadFinishedTask?taskId="+taskId);
}