/**
 * 图片上传数量及其大小等控制
 * 点击开始上传按钮(uploadImgs)，执行上传
 *
 */
var success=0;
var fail=0;
var imgurls="";
var imgsName = new Array();

$(function (){
    layui.use(['upload','layer'],function() {
        var upload = layui.upload;
        var layer=layui.layer;

        upload.render({
            elem: '#selectImgs',
            url: '/task/uploadImgs',
            multiple: true,
            auto:false,
//			上传的单个图片大小
            size:10240,
//			最多上传的数量
            number:20,
//			MultipartFile file 对应，layui默认就是file,要改动则相应改动
            field:'file',
            bindAction: '#uploadImgs',
            before: function(obj) {
                //预读本地文件示例
                obj.preview(function(index, file, result) {
                    $('#previewImgs').append('<img src="' + result
                        + '" alt="' + file.name
                        +'"height="92px" width="92px" class="layui-upload-img uploadImgPreView">')
                });
            },
            done: function(res, index, upload) {
                //每个图片上传结束的回调，成功的话，就把新图片的名字保存起来，作为数据提交
                console.log(res);
                if(res.meta.status=="1"){
                    fail++;
                }else{
                    success++;
                    //imgurls=imgurls+""+res.data.src+",";
                    imgsName.length++;
                    imgsName[imgsName.length-1]=res.data.imgName;
                    console.log(imgsName);
                    //$('#imgUrls').val(imgurls);
                }
            },
            allDone:function(obj){
                layer.msg("总共要上传图片总数为："+(fail+success)+"\n"
                    +"其中上传成功图片数为："+success+"\n"
                    +"其中上传失败图片数为："+fail
                )
            }
        });

    });

    //清空预览图片
    cleanImgsPreview();
    //保存商品
    releaseTask();
});

/**
 * 清空预览的图片及其对应的成功失败数
 * 原因：如果已经存在预览的图片的话，再次点击上选择图片时，预览图片会不断累加
 * 表面上做上传成功的个数清0，实际后台已经上传成功保存了的，只是没有使用，以最终商品添加的提交的为准
  */
function cleanImgsPreview(){
    $("#cleanImgs").click(function(){
        success=0;
        fail=0;
        imgsName.length=0;
        $('#previewImgs').html("");
        $('#imgUrls').val("");
    });
}

/**
 * 发布任务
 */
function releaseTask() {
    $('#btnSubmit').click(function () {
        var tname = $("#taskName").val();
        var tdesc = $("#taskDesc").val();
        var rpoints = $("#rewardPoints").val();
        //var lname = $("#lableName").val();
        var lname = new Array("漏水","裂缝");

        console.log(lname.toString());
        $.ajax({
            type: "POST",
            url: "/task/creatTaskUrl",
            data: {
                lableName: lname.toString(),
                originalImage: imgsName.toString(),
            },
            success: function (resultData) {
                console.log(resultData.toString());
                //url成功获取 提交表单进行数据交互
                $.ajax({
                    type: "POST",
                    url: "/task/createTask",
                    data: {
                        taskName: tname,
                        taskInfo: tdesc,
                        rewardPoints:rpoints,
                        type:1,
                        taskUrl: resultData.toString(),
                        //releaseUserId: rId,  //发布者ID
                        releaseUserId: 12567,
                    },
                    success: function (msg) {
                        alert(msg.meta.msg);
                        if (msg.meta.status == "1") {
                            //成功跳转界面
                            top.location.href="/ImageLabelTaskPage/index.html?" +
                                "userId="+userId+
                                "&pageType="+'otherReleasePage'+
                                "&"+'taskId'+"="+taskId+
                                "&pageFrom="+URLencode('/Home/Home.html')
                                +"%3FuserId%3D"+userId
                                +"%26page%3D"+'releaseNotFinishTask';
                        } else {
                        }
                    }
                });
            }
        })
    });
}