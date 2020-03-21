var userId=getQueryVariable('userId'); //审核者id
var option; //echar使用
var yes=[]; //审核通过
var no=[]; //审核未通过
var yesAndNo=[]; //总数量

$(function () {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    layui.use('form', function() {
        var form = layui.form;
        // 监听下拉选择框
        form.on('select(chooseSelect)', function (data) {
            // 加载图表数据
            loadData(data.value,myChart);
        });
    });
    // 加载图表数据
    loadData(2019,myChart);
});
// 获取iframe的URL参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var t=window.parent.document.getElementById("iframeMain").contentWindow.location;
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
// 加载图表数据
function loadData(year,myChart) {
    $.ajax({
        type: "GET",
        url:"/judge/getHistoryByUserId",
        data:{
            "year":year,
            "userId":userId
        },
        success:function(resultData){
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                yes=resultData.data.historyList[0];
                no=resultData.data.historyList[1];
                yesAndNo=resultData.data.historyList[2];
                // 指定图表的配置项和数据
                option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: {
                        data: [ '审核通过', '审核未通过']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '审核通过',
                            type: 'bar',
                            stack: '审核',
                            itemStyle:{
                                normal:{
                                    color:'#9FE6B8'
                                }
                            },
                            data: yes
                        },
                        {
                            name: '审核未通过',
                            type: 'bar',
                            stack: '审核',
                            itemStyle:{
                                normal:{
                                    color:'#32C5E9'
                                }
                            },
                            data: no
                        },
                        {
                            name: '审核总量',
                            type: 'bar',
                            stack: '审核',
                            label: {
                                normal: {
                                    offset:['50', '80'],
                                    show: true,
                                    position: 'insideBottom',
                                    formatter:'{c}',
                                    textStyle:{ color:'#000' }
                                }
                            },
                            itemStyle:{
                                normal:{
                                    color:'rgba(128, 128, 128, 0)'
                                }
                            },
                            data: yesAndNo
                        }
                    ]
                };
                myChart.setOption(option,true);
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    });
}