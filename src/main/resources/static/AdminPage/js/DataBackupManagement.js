$(function(){
    $.ajax({
        url: "/data/getAllBackUpName",
        type: "get",
        dataType:"Json",
        success: function (backUpNames) {
            for (var i = 0; i < backUpNames.length; i++) {
                $("#selectBox").append('<option>' + backUpNames[i] + '</option>');
            }
        }
    });
    $("#restore").click(function () {
        restoreFunc($("#selectBox").val());
    })
})

//还原数据库
function restoreFunc(fileName){

    $.ajax({
        url: '/data/importDatabase',
        type: "GET",
        data:{
            "fileName":fileName
        },
        success: function(resultData){
            if(resultData=="1"){
                alert("还原成功!");
            }
            else {
                alert("还原失败");
            }
        }
    })
}
//备份数据库
function backupFunc(){
    $.ajax({
        url: '/data/backup',
        type: "GET",
        success: function (resultData) {
            if(resultData=="1"){
                alert("备份成功!");
            }
            else {
                alert("备份失败");
            }
        }
    })
}





