
//备份数据库
 function backupFunc(){
     $.ajax({
        url: '/data/backup',
        type: "GET",
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                layer.msg('备份成功', {
                    icon: 1, //红色不开心
                     time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            } else {
                layer.msg('备份失败，请刷新页面重试', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });

            }
        }
    })
 }
//还原
function importDatabase(){
    $.ajax({
        url: '/data/importDatabase',
        type: "GET",
        data: {
            "importFilePath":importFilePath,
            "fileName": fileName
        },
        success: function (resultData) {
            resultData = JSON.parse(resultData);
            if (resultData.meta.status === "200") {
                layer.msg('还原数据库成功', {
                    icon: 1, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            } else {
                layer.msg('还原数据库失败，请刷新页面重试', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });

            }
        }
    })
}



function restoreFunc1(){
    // 打开弹窗
    layer.open({
        title: '还原数据库',
        area: ['400px', '250px'],
        btnAlign: 'c',
        closeBtn:'1',//右上角的关闭
        content: '<div> <div>请选择数据库： <textarea class="layui-layer-input" name="txt_remark" id="filepath"  ' +
            'style="width:100%;height:30%;line-height:20px;padding:6px 10px;"></textarea></div>',
        btn:['确认','取消'],
        yes: function (index, layero) {
            backup($("#filepath").val());
            layer.close(index);//可执行确定按钮事件并把备注信息（即多行文本框值）存入需要的地方
        },
        btn2:function(index, layero)
        {
            layer.close(index);
        }
    });
}

