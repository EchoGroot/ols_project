$(function () {
    var PhRdColor = ["red", "orange", "orange", "green", "green", "cyan", "blue", "black", "black", "blue"];
    var FxDATA=[];
    console.log(FxDATA);
    $.ajax({
        type: "get",
        url:"/task/getClickNum",
        success:function(resultData){
            resultData=JSON.parse(resultData);
            FxDATA=resultData;
            if (FxDATA != null) {//浏览量总排行榜
                var str = "<blockquote class='layui-elem-quote layui-quote-nm'>";//排行榜的载体
                for (var i = 0; i < FxDATA.length; i++) {
                    if (i != 0) {
                        str = str + "<br>";
                    }
                    //分别表示排行榜的热度颜色 排行榜的点击数量 当前热度排行的名称
                    str = str + "<span class='layui-badge-dot layui-bg-" + PhRdColor[i] + "'></span> <span class='layui-badge-rim'>" + FxDATA[i].ext3 + "</span> <a id='rankBtn"+i+"' name='"+FxDATA[i].id+"' >" + FxDATA[i].name + "</a>";
                }
                str = str + "</blockquote> ";
                $("#ViewPhbInfo").append(str);
                //创建动态btnID点击事件
                for(i = 0;i<FxDATA.length;i++){  //这个i用来选中btn
                    $("#rankBtn"+i).click(function (e){
                        $.ajax({
                            type: "post",
                            url:"/task/clickNumPlus",
                            data:{
                                taskId:e.target.name
                            }
                        })
                        checkFunc(e.target.name); //选中name   name用来存taskId了
                    })
                }
            }
        }
    })
})