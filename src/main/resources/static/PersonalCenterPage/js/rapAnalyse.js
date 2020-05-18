var userId=getQueryVariable('userId');  //用户ID
var yes=[];
var no=[];var state0=[];var state1=[];var state5=[];var state6=[];
var yesAndNo=[];
$(function () {
    $("#ChartMain3").hide();
    $("#Header").append("个人奖惩信息可视化分析");
    $.ajax({
            url: '/information/getInformationByUserId',
            type: "GET",
            data: {
                userId: userId,
                year:2020
            },
            success: function (resultData) {
                resultData=JSON.parse(resultData);
                if(resultData.meta.status === "200"){
                    yes=resultData.data.rapList[0];
                    no=resultData.data.rapList[1];
                    yesAndNo=resultData.data.rapList[2];
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
            text: '奖惩信息数据统计与分析'
        },
        tooltip: {},
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        series: [{
            name: '奖惩信息',
            type: 'pie',
            data: [
                {value:yes.reduce(getSum),name:'奖励'},
                {value:no.reduce(getSum),name:'惩罚'}
            ]
        }]
    };
    var option2 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#93c36f'
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
                name: '奖励',
                // 强制设置图形为圆。
                icon: 'circle',
            },{
                name: '惩罚',
                // 强制设置图形为圆。
                icon: 'circle',
            },{
                name: '奖惩总量',
                // 强制设置图形为圆。
                icon: 'circle',
            }]
        },
        xAxis: {
            data: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
        },
        yAxis: {},
        series: [{
            name: '奖励',
            type: 'bar',
            data: yes
        },{
            name: '惩罚',
            type: 'bar',
            data: no
        },{
            name: '奖惩总量',
            type: 'bar',
            data: yesAndNo
        }]
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