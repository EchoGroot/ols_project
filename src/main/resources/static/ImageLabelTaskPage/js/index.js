var imageUrl="http://yuyy.info/image/ols/"; //图片URL的前缀
var userId=getQueryVariable('userId'); //用户ID
var acceptId=getQueryVariable('acceptId'); //接受任务ID
var pageType=getQueryVariable('pageType');  //页面类型
var pageFrom=URLencode(getQueryVariable('pageFrom')); //从那个页面跳转来的（返回时使用）
var taskId=getQueryVariable('taskId'); // 任务ID

var imageExampleList=[]; //标注示例
var imageNotFinishlist=[]; //待标注
var imageFinishlist=[]; //已标注

//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 加载layui组件
    layui.use(['layer', 'form','element'], function(){
    var element = layui.element
        ,layer = layui.layer
        ,form = layui.form
    });
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
    switch (pageType){
        case 'personalAcceptNotFinishPage':
            //显示提交任务按钮
            $("#submit").show();
            // 获取接受任务的数据
            getAccepteImageList();
            break;
        case 'personalAcceptFinishPage':
            // 获取接受任务的数据
            getAccepteImageList();
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
            getImageList();
            break;
        case 'personalReleasePage':
            // 获取任务数据
            getImageList();
            break;
    }
});
// 获取接受任务数据
function getAccepteImageList () {
    $.ajax({
        url: '/task/getAcceptImageListByAcceptId',
        data: {
            "userId": userId,
            "acceptId": acceptId
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                if(pageType.indexOf('Accept')!==-1){
                    var acceptImageList = JSON.parse(resultData.data.taskImage.ols_accept_url);
                }else{
                    var acceptImageList = JSON.parse(resultData.data.taskImage.ols_task_url);
                }
                // 将标签名存入本地session
                window.sessionStorage.setItem(
                    acceptId + 'labelName',
                    JSON.stringify(acceptImageList.labelName)
                );
                // 将标注信息存入对应的数组
                for (var i = 0; i < acceptImageList.taskImage.length; i++) {
                    if (acceptImageList.taskImage[i].isExample) {
                        $("#exampleDivContainer").show();
                        imageExampleList.push(acceptImageList.taskImage[i]);
                    } else if (acceptImageList.taskImage[i].isLabeled) {
                        $("#finishDivContainer").show();
                        imageFinishlist.push(acceptImageList.taskImage[i])
                    } else {
                        $("#notFinishDivContainer").show();
                        imageNotFinishlist.push(acceptImageList.taskImage[i])
                    }
                    // 标注信息存本地session
                    if(pageType==='personalAcceptNotFinishPage'
                        || pageType==='personalAcceptFinishPage'
                    ){
                        window.sessionStorage.setItem(
                            acceptId+imageUrl + acceptImageList.taskImage[i].originalImage,
                            JSON.stringify(acceptImageList.taskImage[i].labeledInfo)
                        )
                    }
                }
                taskId=resultData.data.taskImage.ols_task_id;
                // 显示任务详情
                $("#taskName").val(resultData.data.taskImage.name);
                $("#taskInfo").val(resultData.data.taskImage.information);
                $("#acceptTime").val(resultData.data.taskImage.accept_time);
                $("#releaseTime").val(resultData.data.taskImage.release_time);
                $("#taskPoints").val(resultData.data.taskImage.points);
                // 将数组里的图片数据加载到对应的显示区域
                loadImageList("exampleDiv",imageExampleList);
                loadImageList("finishDiv",imageFinishlist);
                loadImageList("notFinishDiv",imageNotFinishlist);
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
function loadImageList(domId,imageList) {
    var shtml = "";
    switch (domId) {
        // 标注示例
        case 'exampleDiv':
            for (var i = 0; i < imageList.length; i++){
                shtml +=
                    '<div class="imgContainer">\n'
                    +'    <img class="imgStyle" src="'+imageUrl+imageList[i].originalImage+'" alt="">\n'
                    +'    <div class="lookAndLabel">\n'
                    +'            <a href="/ImageLabelPage/index.html'
                    +'?imageUrl='+imageUrl+imageList[i].originalImage
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
                    +'    </div>\n'
                    +'</div>\n'
                ;
            }
            break;
            // 待标注
        case "notFinishDiv":
            for (var i = 0; i < imageList.length; i++){
                shtml +=
                    '<div class="imgContainer">\n'
                    +'    <img class="imgStyle" src="'+imageUrl+imageList[i].originalImage+'" alt="">\n'
                    +'    <div class="lookAndLabel">\n'
                    +'            <a href="/ImageLabelPage/index.html'
                    +'?imageUrl='+imageUrl+imageList[i].originalImage
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
                    +'            <a class="labelATag" href="/ImageLabelPage/index.html'
                    +'?imageUrl='+imageUrl+imageList[i].originalImage
                    +'&userId='+userId
                    +'&acceptId='+acceptId
                    +'&pageType='+pageType
                    +'&pageFrom='+pageFrom
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
            for (var i = 0; i < imageList.length; i++){
                shtml +=
                    '<div class="imgContainer">\n'
                    +'    <img class="imgStyle" src="'+imageUrl+imageList[i].originalImage+'" alt="">\n'
                    +'    <div class="lookAndLabel">\n'
                    +'            <a href="/ImageLabelPage/index.html'
                    +'?imageUrl='+imageUrl+imageList[i].originalImage
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
                    +'            <a class="labelAgain" href="/ImageLabelPage/index.html'
                    +'?imageUrl='+imageUrl+imageList[i].originalImage
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
    if (r != null) return unescape(r[2]);
    return null;
}
// 获取任务信息
function getImageList() {
    $.ajax({
        url: '/task/getTaskInfoByTaskId',
        data: {
            "userId": userId,
            "taskId": taskId
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                var imageList = JSON.parse(resultData.data.taskInfo.url);
                // 将标签名存入本地session
                window.sessionStorage.setItem(
                    taskId + 'labelName',
                    JSON.stringify(imageList.labelName)
                );
                for (var i = 0; i < imageList.taskImage.length; i++) {
                    console.log(imageList.taskImage[i])
                    if (imageList.taskImage[i].isExample) {
                        $("#exampleDivContainer").show();
                        imageExampleList.push(imageList.taskImage[i]);
                        // 标注信息存本地session
                        window.sessionStorage.setItem(
                            taskId+imageUrl + imageList.taskImage[i].originalImage,
                            JSON.stringify(imageList.taskImage[i].labeledInfo)
                        )
                    } else {
                        $("#notFinishDivContainer").show();
                        imageNotFinishlist.push(imageList.taskImage[i])
                    }

                }
                $("#taskName").val(resultData.data.taskInfo.name);
                $("#taskInfo").val(resultData.data.taskInfo.information);
                $("#acceptTime").val(resultData.data.taskInfo.accept_time);
                $("#releaseTime").val(resultData.data.taskInfo.release_time);
                $("#taskPoints").val(resultData.data.taskInfo.points);
                loadImageList("exampleDiv",imageExampleList);
                loadImageList("notFinishDiv",imageNotFinishlist);
                $(".labelATag").hide();
            } else {
                layer.msg('获取任务数据失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });

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

//举报
function reportFunc() {}
// 返回
function goBackFunc() {
    var t=reverseURLencode(pageFrom);
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
    if (imageNotFinishlist.length>0){
        layer.msg('提交任务失败，有任务未标注', {
            icon: 5, //红色不开心
            time: 2000 //2秒关闭（如果不配置，默认是3秒）
        });
        return;
    }
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