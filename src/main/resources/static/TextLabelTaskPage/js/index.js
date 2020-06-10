var docUrl="D://docTask//"; //文件URL的前缀
var userId=getQueryVariable('userId'); //用户ID
var acceptId=getQueryVariable('acceptId'); //接受任务ID
var pageType=getQueryVariable('pageType');  //页面类型
var pageFrom=URLencode(getQueryVariable('pageFrom')); //从那个页面跳转来的（返回时使用）
var taskId=getQueryVariable('taskId'); // 任务ID

var docExampleList=[]; //标注示例
var docNotFinishlist=[]; //待标注
var docFinishlist=[]; //已标注

//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 加载layui组件
    layui.use(['layer', 'form','element'], function(){
        var element = layui.element
            ,layer = layui.layer
            ,form = layui.form
    });
    myHide();
    switch (pageType){
        case 'labelExamplePage':
            // 获取任务数据
            getDocList();
            //显示提交示例按钮
            $("#submitExample").show();
            break;
        case 'personalAcceptNotFinishPage':
            //显示提交任务按钮
            $("#submit").show();
            // 获取接受任务的数据
            getAccepteDocList();
            break;
        case 'personalAcceptFinishPage':
            // 获取接受任务的数据
            getAccepteDocList();
            break;
        case 'otherReleasePage':
            // 不是从审核页面，个人发布页面进来的
            if(pageFrom.indexOf('JudgeTaskPage')===-1
                &&pageFrom.indexOf('PersonalCenterPag')===-1
            ){
                // 显示举报按钮
                $("#report").show();
                // 显示接受任务按钮
                $("#accept").show();
            }
            // 获取任务数据
            getDocList();
            break;
        case 'personalReleasePage':
            // 获取任务数据
            getDocList();
            break;
    }
});
// 获取接受任务数据
function getAccepteDocList () {
    $.ajax({
        url: '/task/getAcceptDocListByAcceptId',
        data: {
            "userId": userId,
            "acceptId": acceptId
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                if(pageType.indexOf('Accept')!==-1){
                    var acceptDocList = JSON.parse(resultData.data.taskDoc.ols_accept_url);
                }else{
                    var acceptDocList = JSON.parse(resultData.data.taskDoc.ols_task_url);
                }
                // 将标签名存入本地session
                window.sessionStorage.setItem(
                    acceptId + 'labelName',
                    JSON.stringify(acceptDocList.labelName)
                );
                // 将标注信息存入对应的数组
                for (var i = 0; i < acceptDocList.taskDoc.length; i++) {
                    if (acceptDocList.taskDoc[i].isExample) {
                        $("#exampleDivContainer").show();
                        docExampleList.push(acceptDocList.taskDoc[i]);
                    } else if (acceptDocList.taskDoc[i].isLabeled) {
                        $("#finishDivContainer").show();
                        docFinishlist.push(acceptDocList.taskDoc[i])
                    } else {
                        $("#notFinishDivContainer").show();
                        docNotFinishlist.push(acceptDocList.taskDoc[i])
                    }
                    // 标注信息存本地session
                    if(pageType==='personalAcceptNotFinishPage'
                        || pageType==='personalAcceptFinishPage'
                    ){
                        window.sessionStorage.setItem(
                            acceptId+docUrl + acceptDocList.taskDoc[i].originalDoc,
                            JSON.stringify(acceptDocList.taskDoc[i].labeledInfo)
                        )
                    }
                }
                taskId=resultData.data.taskDoc.ols_task_id;
                // 显示任务详情
                $("#taskName").val(resultData.data.taskDoc.name);
                $("#taskId").val(resultData.data.taskDoc.ols_accept_id);
                $("#acceptNum").val(resultData.data.taskDoc.accept_num);
                $("#taskType").val(resultData.data.taskDoc.type);
                $("#acceptTaskState").val(resultData.data.taskDoc.ols_accept_state);
                $("#taskState").val(resultData.data.taskDoc.ols_task_state);
                $("#releaseUserName").val(resultData.data.taskDoc.release_user_name);
                $("#taskInfo").val(resultData.data.taskDoc.information);
                $("#acceptTime").val(resultData.data.taskDoc.accept_time);
                $("#releaseTime").val(resultData.data.taskDoc.release_time);
                $("#taskPoints").val(resultData.data.taskDoc.points);
                $(".onlyTask").hide();
                // 将标签名存入本地session
                window.sessionStorage.setItem(
                    acceptId + 'docNotFinishlist',
                    JSON.stringify(docNotFinishlist)
                );
                // 将数组里的图片数据加载到对应的显示区域
                loadDocList("exampleDiv",docExampleList);
                loadDocList("finishDiv",docFinishlist);
                loadDocList("notFinishDiv",docNotFinishlist);
                if(pageType === "personalAcceptFinishPage"){
                    // 隐藏重新标注按钮
                    $(".labelAgain").hide();
                }
            } else {
                layer.msg('获取任务数据失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    })
}
// 加载图片数据到指定元素
function loadDocList(domId,docList) {
    var path = "image/word.jpg";
    var shtml = "";
    switch (domId) {
        //标注示例
        case 'exampleDiv':
            for (var i = 0; i < docList.length; i++){
                shtml +=
                    '<div class="imgContainer">\n'
                    +'    <img class="imgStyle" src="'+path+'" alt="">\n'
                    +'    <div class="lookAndLabel">\n'
                    +'            <a href="/TextLablePage/index.html'
                    +'?docUrl='+docUrl+docList[i].originalDoc
                    +'&labelInfo='+docUrl+docList[i].labeledInfo
                    +'&userId='+userId
                    +'&acceptId='+acceptId
                    +'&pageType='+pageType
                    +'&pageFrom='+pageFrom
                    +'&taskId='+taskId
                    +'&operation=read'
                    +'">'
                    +'        <button type="button" class="layui-btn layui-btn-radius">'
                    +'查看'
                    +'        </button>\n'
                    +'</a>\n';
                if(pageType =='labelExamplePage'){
                    shtml+='            <a class="labelAgain" href="/TextLablePage/index.html'
                        +'?docUrl='+docUrl+docList[i].originalDoc
                        +'&labelInfo='+docUrl+docList[i].labeledInfo
                        +'&userId='+userId
                        +'&acceptId='+acceptId
                        +'&taskId='+taskId
                        +'&pageType='+pageType
                        +'&pageFrom='+pageFrom
                        +'&operation=write'
                        +'">'
                        +'        <button type="button" class="layui-btn layui-btn-normal layui-btn-radius">'
                        +'标注示例'
                        +'        </button>\n'
                        +'</a>\n'
                }
                shtml+='    </div>\n'
                    +'</div>\n';
            }
            break;
        //待标注
        case "notFinishDiv":
            for (var i = 0; i < docList.length; i++){
                shtml +=
                    '<div class="imgContainer">\n'
                    +'    <img class="imgStyle" src="'+path+'" alt="">\n'
                    +'    <div class="lookAndLabel">\n'
                    +'            <a href="/TextLablePage/index.html'
                    +'?docUrl='+docUrl+docList[i].originalDoc
                    +'&labelInfo='+docUrl+docList[i].labeledInfo
                    +'&userId='+userId
                    +'&acceptId='+acceptId
                    +'&pageType='+pageType
                    +'&pageFrom='+pageFrom
                    +'&taskId='+taskId
                    +'&operation=read'
                    +'">'
                    +'        <button type="button" class="layui-btn layui-btn-radius">'
                    +'查看'
                    +'        </button>\n'
                    +'</a>\n'
                    +'            <a class="labelATag" href="/TextLablePage/index.html'
                    +'?docUrl='+docUrl+docList[i].originalDoc
                    +'&labelInfo='+docUrl+docList[i].labeledInfo
                    +'&userId='+userId
                    +'&acceptId='+acceptId
                    +'&pageType='+pageType
                    +'&pageFrom='+pageFrom
                    +'&taskId='+taskId
                    +'&operation=write'
                    +'">'
                    +'        <button type="button" class="layui-btn layui-btn-normal layui-btn-radius">'
                    +'标注'
                    +'        </button>\n'
                    +'</a>\n'
                    +'    </div>\n'
                    +'</div>\n'
                ;
            }
            break;
        // 已标注
        case "finishDiv":
            for (var i = 0; i < docList.length; i++){
                shtml +=
                    '<div class="imgContainer">\n'
                    +'    <img class="imgStyle" src="'+path+'" alt="">\n'
                    +'    <div class="lookAndLabel">\n'
                    +'            <a href="/TextLablePage/index.html'
                    +'?docUrl='+docUrl+docList[i].originalDoc
                    +'&labelInfo='+docUrl+docList[i].labeledInfo
                    +'&userId='+userId
                    +'&acceptId='+acceptId
                    +'&pageType='+pageType
                    +'&pageFrom='+pageFrom
                    +'&operation=read'
                    +'">'
                    +'        <button type="button" class="layui-btn layui-btn-radius">'
                    +'查看'
                    +'        </button>\n'
                    +'</a>\n'
                    +'            <a class="labelAgain" href="/TextLablePage/index.html'
                    +'?docUrl='+docUrl+docList[i].originalDoc
                    +'&labelInfo='+docUrl+docList[i].labeledInfo
                    +'&userId='+userId
                    +'&acceptId='+acceptId
                    +'&pageType='+pageType
                    +'&pageFrom='+pageFrom
                    +'&operation=write'
                    +'">'
                    +'        <button type="button" class="layui-btn layui-btn-normal layui-btn-radius">'
                    +'重新标注'
                    +'        </button>\n'
                    +'</a>\n'
                    +'    </div>\n'
                    +'</div>\n'
                ;
            }
            break;
    }
    // 渲染到指定的DOM
    $("#"+domId).html(shtml);
}
// 从URL中获取参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}
// 获取任务信息
function getDocList() {
    $.ajax({
        url: '/task/getTaskInfoByTaskId',
        data: {
            "userId": userId,
            "taskId": taskId
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            console.log(resultData);
            if (resultData.meta.status === "200") {
                var docList = JSON.parse(resultData.data.taskInfo.url);
                // 将标签名存入本地session
                window.sessionStorage.setItem(
                    taskId + 'labelName',
                    JSON.stringify(docList.labelName)
                );
                for (var i = 0; i < docList.taskDoc.length; i++) {
                    console.log(docList.taskDoc[i])
                    if (docList.taskDoc[i].isExample) {
                        $("#exampleDivContainer").show();
                        docExampleList.push(docList.taskDoc[i]);
                        // 标注信息存本地session
                        window.sessionStorage.setItem(
                            taskId+docUrl + docList.taskDoc[i].originalDoc,
                            JSON.stringify(docList.taskDoc[i].labeledInfo)
                        )
                    } else {
                        $("#notFinishDivContainer").show();
                        docNotFinishlist.push(docList.taskDoc[i])
                    }

                }
                $("#taskName").val(resultData.data.taskInfo.name);
                $("#taskId").val(resultData.data.taskInfo.id);
                $("#ext3").val(resultData.data.taskInfo.ext3);
                $("#adoptAcceptId").val(resultData.data.taskInfo.adopt_accept_id);
                $("#acceptNum").val(resultData.data.taskInfo.accept_num);
                $("#taskType").val(resultData.data.taskInfo.type);
                $("#taskState").val(resultData.data.taskInfo.state);
                $("#taskInfo").val(resultData.data.taskInfo.information);
                $("#acceptTime").val(resultData.data.taskInfo.accept_time);
                $("#releaseTime").val(resultData.data.taskInfo.release_time);
                $("#taskPoints").val(resultData.data.taskInfo.points);
                $(".onlyAccept").hide();
                window.sessionStorage.setItem(
                    taskId + 'docNotFinishlist',
                    JSON.stringify(docNotFinishlist)
                );
                loadDocList("exampleDiv",docExampleList);
                loadDocList("notFinishDiv",docNotFinishlist);
                if(pageType!='labelExamplePage'){
                    $(".labelATag").hide();
                }
            } else {
                layer.msg('获取任务数据失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });

            }
        }
    })
}
//判断是否登录
function judgeLogin(func) {
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
                        /*resultData = JSON.parse(resultData);
                        if (resultData.meta.status === "200") {
                            name = resultData.data.userInfo.name;
                            var a=document.getElementById("userName");
                            a.innerText=name;
                        }*/
                    }
                });
                if(func==='acceptFunc'){
                    acceptFunc();
                }else if(func==='reportFunc'){
                    reportFunc();
                }
            }
            if (resultData.meta.status === "201") {
                layer.msg('请先登录', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                // 将当前网址存入本地session
                window.sessionStorage.setItem(
                    'gotoUrl',window.location.href
                );
                // 跳转到登录页面
                window.setTimeout(function () {
                    window.location.href='/Home/login.html';
                }, 1000);
            }
        }
    })
}
//接受任务
function acceptFunc() {
    $.ajax({
        url: '/task/acceptTask',
        type: "POST",
        data: {
            "userId": userId,
            "taskId": taskId
        },
        success: function (resultData) {
            console.log(resultData);
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                layer.msg('接受任务成功', {
                    icon: 1, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            } else if(resultData.meta.status === "201"){
                layer.msg('您已接受该任务', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            } else{
                layer.msg('接受任务失败，请刷新再试', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    })
}
// 返回
function goBackFunc() {
    window.location.href=reverseURLencode(pageFrom);
}
//转义URL里的特殊字符
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
//逆向转义
function  reverseURLencode(sStr) {
    return sStr.replace(/%25/g,"%")
        .replace(/%2B/g, '+')
        .replace(/%22/g,'"')
        .replace(/%23/g,'#')
        .replace(/%27/g, '\'')
        .replace(/%26/g, '&')
        .replace(/%3F/g, '?')
        .replace(/%3D/g, '=')
        .replace(/%2F/g,'/');
}
// 提交任务
function submitFunc() {
    if (docNotFinishlist.length>0){
        layer.msg('提交任务失败，有任务未标注', {
            icon: 5, //红色不开心
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        return;
    }
    alert(taskId);
    $.ajax({
        url: '/task/submitAcceptTask',
        type: "POST",
        data: {
            "userId": userId,
            "taskId": taskId,
            "acceptId":acceptId
        },
        success: function (resultData) {
            console.log(resultData);
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                layer.msg('提交任务成功', {
                    icon: 1, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            } else{
                layer.msg('提交任务失败，请刷新页面再试。', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    })
}
// 提交标注示例
function submitExampleFunc() {
    if (docExampleList.length===0){
        layer.msg('提交标注示例失败，未标注任何示例。', {
            icon: 5, //红色不开心
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        return;
    }
    $.ajax({
        url: '/task/setTaskStateByTaskId',
        type: "POST",
        data: {
            "userId": userId,
            "taskId": taskId
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                layer.msg('提交标注示例成功，任务审核中。', {
                    icon: 1, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                window.setTimeout(function(){
                    goBackFunc();
                },2000)
            } else{
                layer.msg('提交标注示例失败，请刷新页面再试。', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    })
}
//举报任务
function reportFunc(){
    window.location.href='/complain/html/complain.html?userId='+userId+'&taskId='+taskId
}
// 初始化时隐藏元素
function myHide() {
    // 隐藏示例标注区域
    $("#exampleDivContainer").hide();
    // 隐藏待标注区域
    $("#notFinishDivContainer").hide();
    // 隐藏已标注区域
    $("#finishDivContainer").hide();
    // 隐藏举报按钮
    $("#report").hide();
    // 隐藏接受任务按钮
    $("#accept").hide();
    // 隐藏提交任务按钮
    $("#submit").hide();
    // 隐藏提交示例按钮
    $("#submitExample").hide();
}