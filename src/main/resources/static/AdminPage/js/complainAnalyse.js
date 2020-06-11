var userId=getQueryVariable('userId');  //用户ID
var yes=[];
var no=[];var state0=[];var state1=[];var state5=[];var state6=[];
var yesAndNo=[];
$(function () {
    $("#ChartMain3").hide();
    $("#Header").append("举报信息可视化分析");
    $.ajax({
        url: '/message/getmessage',
        type: "GET",
        data: {
            year:2020
        },
        success: function (resultData) {
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                yes=resultData.data.complainList[0];
                no=resultData.data.complainList[1];
                yesAndNo=resultData.data.complainList[2];
                GenerateChart2();
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    })

})
//生成图表
function GenerateChart2() {
    var ImgChart = echarts.init(document.getElementById('ChartMain'));
    var ImgChart2 = echarts.init(document.getElementById('ChartMain2'));
    var option1 = {
        title: {
            text: '举报信息数据统计与分析' ,
            x:'center',
            y:'top'

        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                saveAsImage: {}
            }
        },
        legend: {
            orient: 'vertical',
            left: 10,
            data: ['图片', '文档']
        },
        series: [{
            name: '举报信息',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                show: false,
                position: 'center'
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: '30',
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            itemStyle: {
                normal: {
                    shadowBlur: 200,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            data: [
                {value:yes.reduce(getSum),name:'文档'},
                {value:no.reduce(getSum),name:'图片'}
            ]
        }]
    };
    var option2 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
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
            data:['文档','图片']
        },
        yAxis: {
            type: 'category',
            data: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
        },
        xAxis: {
            type: 'value'
        },
        series: [{
            name: '文档',
            type: 'bar',
            stack: '总量',
            label: {
                show: true,
                position: 'insideRight'
            },
            data: yes
        },{
            name: '图片',
            type: 'bar',
            stack: '总量',
            label: {
                show: true,
                position: 'insideRight'
            },
            data: no
        },]
    };
    ImgChart.setOption(option1);
    ImgChart2.setOption(option2);
}
function getSum(total, num) {
    return total + num;
}
// 获取URL参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null && unescape(r[2]) !== 'null') return unescape(r[2]);
    if (null !== window.sessionStorage.getItem(name)) {
        return window.sessionStorage.getItem(name);
    }
    return null;
}