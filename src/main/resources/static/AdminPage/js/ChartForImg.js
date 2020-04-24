var userId=getQueryVariable('userId'); //获取URL参数里的用户ID
$(function () {
    $("#Header").append("图片任务后台管理可视化分析");


    $("#GoBack").click(function () {
        parent.$("#iframeMain").attr("src",$(this).attr("href")+'userId='+userId);
    })
})

function GenerateChart() {
    var ImgChart = echarts.init(document.getElementById('ChartMain'));
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

    ImgChart.on('updateAxisPointer', function (event) {
        var xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
            var dimension = xAxisInfo.value + 1;
            ImgChart.setOption({
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
    ImgChart.setOption(option);
}
// 获取URL参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}