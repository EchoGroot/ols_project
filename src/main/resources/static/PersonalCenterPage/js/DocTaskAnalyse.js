var page = getIframeQueryVariable("page");
var userId = getIframeQueryVariable("userId");
var yes=[];
var no=[];var state0=[];var state1=[];var state5=[];var state6=[];
var yesAndNo=[];
$(function () {
    if(page=='releaseTask'){
        $("#Header").append("个人发布任务可视化分析");
        $("#ChartMain").hide();
        $("#ChartMain2").hide();
        $.ajax({
            url: '/task/getAllReleaseDocById',
            type: "GET",
            data: {
                userId: userId,
                year:2020
            },
            success: function (resultData) {
                resultData=JSON.parse(resultData);
                if(resultData.meta.status === "200"){
                    state0=resultData.data.releaseList[0];state0.unshift('所有已发布任务数量');
                    state1=resultData.data.releaseList[1];state1.unshift('已完成');
                    state5=resultData.data.releaseList[2];state5.unshift('已发布未完成');
                    //state6=resultData.data.releaseList[3];state6.unshift('未通过审核');
                    GenerateChart1();
                }else{
                    layer.msg('操作失败，请刷新页面', {
                        icon: 5, //红色不开心
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                }
            }
        })
    }else if(page == 'acceptTask'){
        $("#ChartMain3").hide();
        $("#Header").append("个人接受任务可视化分析");
        $.ajax({
            url: '/accept/getPersonalAcceptDocByUserId',
            type: "GET",
            data: {
                userId: userId,
                year:2020
            },
            success: function (resultData) {
                resultData=JSON.parse(resultData);
                if(resultData.meta.status === "200"){
                    yes=resultData.data.acceptList[0];
                    no=resultData.data.acceptList[1];
                    yesAndNo=resultData.data.acceptList[2];
                    GenerateChart2();
                }else{
                    layer.msg('操作失败，请刷新页面', {
                        icon: 5, //红色不开心
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                }
            }
        })
    }
})
function GenerateChart1() {
    var DocChart = echarts.init(document.getElementById('ChartMain3'));
    var option = {
        legend: {},
        tooltip: {
            trigger: 'axis',
            showContent: false
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        dataset: {
            source: [
                ['product', '1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                state0,state1,state5,state6
            ]
        },
        xAxis: {type: 'category'},
        yAxis: {gridIndex: 0},
        grid: {top: '55%'},
        series: [
            {type: 'line', smooth: true, seriesLayoutBy: 'row'},
            {type: 'line', smooth: true, seriesLayoutBy: 'row'},
            {type: 'line', smooth: true, seriesLayoutBy: 'row'},
            {type: 'line', smooth: true, seriesLayoutBy: 'row'},
            {
                type: 'pie',
                id: 'pie',
                radius: '30%',
                center: ['50%', '25%'],
                label: {
                    formatter: '{b}: {@2012} ({d}%)'
                },
                encode: {
                    itemName: 'product',
                    value: '2012',
                    tooltip: '2012'
                }
            }
        ]
    };

    DocChart.on('updateAxisPointer', function (event) {
        var xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
            var dimension = xAxisInfo.value + 1;
            DocChart.setOption({
                series: {
                    id: 'pie',
                    label: {
                        formatter: '{b}: {@[' + dimension + ']} ({d}%)'
                    },
                    encode: {
                        value: dimension,
                        tooltip: dimension
                    }
                }
            });
        }
    });
    DocChart.setOption(option);
}
//生成图表
function GenerateChart2() {
    var DocChart = echarts.init(document.getElementById('ChartMain'));
    var DocChart2 = echarts.init(document.getElementById('ChartMain2'));
    var option1 = {
        title: {
            text: '已接受文本任务数据统计与分析'
        },
        tooltip: {},
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        series: [{
            name: '接受的文本任务',
            type: 'pie',
            data: [
                {value:yes.reduce(getSum),name:'已完成'},
                {value:no.reduce(getSum),name:'未完成'}
            ]
        }]
    };
    var option2 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        legend: {
            data:[{
                name: '已完成',
                // 强制设置图形为圆。
                icon: 'circle',
            },{
                name: '未完成',
                // 强制设置图形为圆。
                icon: 'circle',
            },{
                name: '接受任务总量',
                // 强制设置图形为圆。
                icon: 'circle',
            }]
        },
        xAxis: {
            data: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
        },
        yAxis: {},
        series: [{
            name: '已完成',
            type: 'bar',
            data: yes
        },{
            name: '未完成',
            type: 'bar',
            data: no
        },{
            name: '接受任务总量',
            type: 'bar',
            data: yesAndNo
        }]
    };
    DocChart.setOption(option1);
    DocChart2.setOption(option2);
}
function getSum(total, num) {
    return total + num;
}
// 获取URL里的参数
function getIframeQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}