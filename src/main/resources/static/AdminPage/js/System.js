var page=getIframeQueryVariable('page'); //页面名称
$(function () {
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        loadPage(1,10)
        function loadPage(page1,limit1){
            $.ajax({
                url: '/system/getAllSystem',
                type: "GET",
                data: {
                   // acceptUID: 0,
                    page:page1,
                    limit:limit1,
                },
                success: function (resultData) {
                    resultData = JSON.parse(resultData);
                    if(resultData.data.total>0){
                        $('ul').empty();
                        for(var i=0;i<resultData.data.sysList.length;i++){
                            var str="<li class='layui-timeline-item'><i class='layui-icon layui-timeline-axis'></i><div class='layui-timeline-content layui-text'><h3 class='layui-timeline-title'>用户"+resultData.data.sysList[i].accept_user_id+"  "+resultData.data.sysList[i].create_time+"</h3><p>"+resultData.data.sysList[i].message+"</p></div></li>";
                            $("#systemInfos").append(str);
                        }
                        laypage.render({
                            elem: 'pagingModule'
                            ,theme: '#1E9FFF'
                            ,curr:page1
                            ,limit:limit1
                            ,count: resultData.data.total
                            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                            ,jump: function(obj,first){
                                console.log(obj)
                                if(!first) {
                                    loadPage(obj.curr,obj.limit);
                                }
                            }
                        });
                    }else {
                        var str="<li class='layui-timeline-item'><i class='layui-icon layui-timeline-axis'></i><div class='layui-timeline-content layui-text'><h3 class='layui-timeline-title'>暂无系统消息</h3><p></p></div></li>";
                        $("#systemInfos").append(str);
                    }
                }
            })
        }
    })

})
// 获取URL里的参数
function getIframeQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
