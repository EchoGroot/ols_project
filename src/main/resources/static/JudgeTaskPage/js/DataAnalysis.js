var userId=getQueryVariable('userId'); //审核者id
var option; //echar使用
var yes=[]; //审核通过
var no=[]; //审核未通过
var yesAndNo=[]; //总数量
var myChart =''; //柱状图
var myChart1 =''; //折现图
var myChart2 =''; //饼状图
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById('main'));
    myChart1 = echarts.init(document.getElementById('main1'));
    myChart2 = echarts.init(document.getElementById('main2'));
    // layui初始化
    layui.use('form', function() {
        var form = layui.form;
        // 监听下拉选择框
        form.on('select(chooseSelect)', function (data) {
            // 加载图表数据
            loadData(data.value);
        });
    });
    // 加载图表数据
    loadData(2019);
});
// 获取iframe的URL参数
function getQueryVariable(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    // var r = window.location.search.substr(1).match(reg);
    var r = window.parent.document.getElementById("iframeMain").contentWindow.location.search.match(reg);
    if (r != null&&unescape(r[2])!=='null') return unescape(r[2]);
    if(null!==window.sessionStorage.getItem(name)){
        return window.sessionStorage.getItem(name);
    }
    return null;
}
// 加载图表数据
function loadData(year) {
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
                rendTable();
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    });
}
// 渲染图表
function rendTable() {// 指定图表的配置项和数据
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
    option1 = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['审核通过', '审核未通过']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul','Aug','Sept','Oct','Nov','Dec']
        },
        yAxis: {
            type: 'value',
                axisLabel: {
                formatter: '{value}'
            }
        },
        series: [
            {
                name: '审核通过',
                type: 'line',
                data: yes,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'},
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'max'
                        }, {
                            symbol: 'circle',
                            label: {
                                position: 'start',
                                formatter: '最大值'
                            },
                            type: 'max',
                            name: '最高点'
                        }],
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'min'
                        }, {
                            symbol: 'circle',
                            label: {
                                position: 'start',
                                formatter: '最小值'
                            },
                            type: 'min',
                            name: '最低点'
                        }]
                    ]
                }
            },
            {
                name: '审核未通过',
                type: 'line',
                data: no,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'},
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'max'
                        }, {
                            symbol: 'circle',
                            label: {
                                position: 'start',
                                formatter: '最大值'
                            },
                            type: 'max',
                            name: '最高点'
                        }],
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'min'
                        }, {
                            symbol: 'circle',
                            label: {
                                position: 'start',
                                formatter: '最小值'
                            },
                            type: 'min',
                            name: '最低点'
                        }]
                    ]
                }
            }
        ]
    };
    myChart1.setOption(option1,true);
    var yesNum=0;
    var noNum=0;
    for(var i=0;i<12;i++){
        yesNum+=yes[i];
        noNum+=no[i];
    }
    option2 = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        color:['#f6da22','#bbe2e8'],
        legend: {
            orient: 'vertical',
            left: 10,
            data: ['审核通过', '审核未通过']
        },
        series: [
            {
                name: '审核情况',
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
                data: [
                    {value: yesNum, name: '审核通过'},
                    {value: noNum, name: '审核未通过'}
                ]
            }
        ]
    };
    myChart2.setOption(option2,true);

}