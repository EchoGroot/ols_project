var imageUrl="http://yuyy.info/image/ols/"
var userId=getQueryVariable('userId');
var accepteId=getQueryVariable('accepteId');
var imageExampleList=[];
var imageNotFinishlist=[];
var imageFinishlist=[];

//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    $("#exampleDivContainer").hide();
    $("#notFinishDivContainer").hide();
    $("#finishDivContainer").hide();
    layui.use(['layer', 'form','element'], function(){
        var element = layui.element
            ,layer = layui.layer
            ,form = layui.form
    });
    getAccepteImageList();

});
function loadImageList(domId,imageList) {
    console.log(imageList);
    var shtml = "";
    for (var i = 0; i < imageList.length; i++){
        shtml +=
            '<div class="imgContainer">\n'
            +'    <img class="imgStyle" src="'+imageUrl+imageList[i].originalImage+'" alt="">\n'
            +'    <div class="lookAndLabel">\n'
            +'        <button type="button" class="layui-btn layui-btn-radius">默认按钮</button>\n'
            +'        <button type="button" class="layui-btn layui-btn-normal layui-btn-radius">百搭按钮</button>\n'
            +'    </div>\n'
            +'</div>\n'
        ;
    };
    $("#"+domId).html(shtml);
}
function getAccepteImageList () {
    $.ajax({
        url: '/task/getAccepteImageListByAccepteId',
        data: {
            "userId": userId,
            "accepteId": accepteId
        },
        success: function (resultData) {
            console.log(resultData);
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                var accepteImageList = JSON.parse(resultData.data.taskImage.ols_accepte_url)
                window.sessionStorage.setItem(
                    accepteId + 'labelName',
                    JSON.stringify(accepteImageList.labelName)
                );
                for (var i = 0; i < accepteImageList.taskImage.length; i++) {
                    console.log(accepteImageList.taskImage[i])
                    if (accepteImageList.taskImage[i].isExample) {
                        $("#exampleDivContainer").show();
                        imageExampleList.push(accepteImageList.taskImage[i]);

                    } else if (accepteImageList.taskImage[i].isLabeled) {
                        $("#finishDivContainer").show();
                        imageFinishlist.push(accepteImageList.taskImage[i])
                    } else {
                        $("#notFinishDivContainer").show();
                        imageNotFinishlist.push(accepteImageList.taskImage[i])
                    }
                    // 标注信息存本地session
                    window.sessionStorage.setItem(
                        imageUrl + accepteImageList.taskImage[i].originalImage,
                        JSON.stringify(accepteImageList.taskImage[i].labeledInfo)
                    )
                }
                $("#taskName").val(resultData.data.taskImage.name);
                $("#taskInfo").val(resultData.data.taskImage.information);
                $("#acceptTime").val(resultData.data.taskImage.accept_time);
                $("#releaseTime").val(resultData.data.taskImage.release_time);
                $("#taskPoints").val(resultData.data.taskImage.points);
                loadImageList("exampleDiv",imageExampleList);
                loadImageList("finishDiv",imageFinishlist);
                loadImageList("notFinishDiv",imageNotFinishlist);
            } else {
                layer.msg('获取任务数据失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });

            }
        }
    })
}
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
