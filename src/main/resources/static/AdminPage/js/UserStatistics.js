var option1; //echar使用
var men=[]; //男
var women=[]; //女
var allSex=[]; //总数量
var myChart1=''; //饼状图
var user=[];//普通用户
var admin=[];//管理员
var reviewer=[];//审核者
var myChart2=''; //折线图
var myChart3=''; //日历图
var userday=[];
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 基于准备好的dom，初始化echarts实例
    myChart1 = echarts.init(document.getElementById('main1'));
    myChart2 = echarts.init(document.getElementById('main2'));
    myChart3 = echarts.init(document.getElementById('main3'));
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
    loadData(0);
    loadData(2018);
});
// 加载图表数据
function loadData(value) {
    if(value==0||value==1||value==2){
        $.ajax({
            type: "GET",
            url:"/user/getSex",
            data:{
                "role":value
            },
            success:function(resultData){
                resultData=JSON.parse(resultData);
                if(resultData.meta.status === "200"){
                    men=resultData.data.sexList[0];
                    women=resultData.data.sexList[1];
                    allSex=resultData.data.sexList[2];
                    rendTable();
                }else{
                    layer.msg('操作失败，请刷新页面', {
                        icon: 5, //红色不开心
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                }
            }
        });
    }else{
        $.ajax({
            type: "GET",
            url:"/user/getRegister",
            data:{
                "year":value
            },
            success:function(resultData){
                resultData=JSON.parse(resultData);
                if(resultData.meta.status === "200"){
                    user=resultData.data.userList[0];
                    admin=resultData.data.userList[1];
                    reviewer=resultData.data.userList[2];
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
    $.ajax({
        type: "GET",
        url:"/user/getRegisterday",
        data:{
            "year":"2020",
        },
        success:function(resultData){
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                userday=resultData.data;
                //alert(userday);
                //rendTable();
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
    option1 = {
        title: {
            text: '用户性别饼状图',
            x:'center',
            y:'top'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        color:['#9FE6B8','#32C5E9'],
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['男', '女']
        },
        series: [
            {
                name: '性别',
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
                    {value: men, name: '男'},
                    {value: women, name: '女'}
                ]
            }
        ]
    };
    myChart1.setOption(option1,true);

    option2 = {
        title: {
            text: '用户注册数折线图',
            x:'center',
            y:'top'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['普通用户', '审核者']
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [
            {
                name: '普通用户',
                type: 'line',
                data: user,
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
                name: '审核者',
                type: 'line',
                data: reviewer,
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
    myChart2.setOption(option2,true);

    option3 = {
        title: {
            x:'center',
            y:'top',
            text: '2020年用户活跃度分析'
        },
        tooltip: {},
        visualMap: {
            min: 0,
            max: 100,
            type: 'piecewise',
            orient: 'horizontal',
            left: 'center',
            top: 65,
            textStyle: {
                color: '#000'
            }
        },
        calendar: {
            top: 120,
            left: 30,
            right: 30,
            cellSize: ['auto', 13],
            range: '2020',
            itemStyle: {
                borderWidth: 0.5
            },
            yearLabel: {show: false}
        },
        series: {
            type: 'heatmap',
            coordinateSystem: 'calendar',
            data: [['2020-04-12', 42], ['2020-04-13',8], ['2020-04-14',3], ['2020-04-17',15], ['2020-04-18',40],
                ['2020-04-19' , 12], ['2020-05-29', 10], ['2020-04-20' , 36], ['2020-05-30' , 26], ['2020-04-21' , 73],
                ['2020-05-31' , 11], ['2020-06-01' , 46], ['2020-04-22' , 16], ['2020-06-02' , 3], ['2020-04-23' , 21],
                ['2020-06-03' , 22], ['2020-04-24' , 14], ['2020-04-25' , 5], ['2020-04-26' , 9], ['2020-04-27' , 13],
                ['2020-04-28' , 10], ['2020-04-29' , 39], ['2020-04-30' , 21], ['2020-05-01' , 6], ['2020-05-02' , 13],
                ['2020-05-03' , 27], ['2020-05-05' , 8], ['2020-05-06' , 32], ['2020-05-07' , 33], ['2020-05-08' , 27],
                ['2020-05-09' , 1], ['2020-05-10' , 19], ['2020-05-11' , 6], ['2020-05-12' , 12], ['2020-05-13' , 24],
                ['2020-05-14' , 12], ['2020-05-15' , 9], ['2020-05-16' , 17], ['2020-05-18' , 13], ['2020-05-19' , 2],
                ['2020-05-21' , 3], ['2020-05-24' , 9], ['2020-05-25' , 11], ['2020-05-26' , 18], ['2020-05-27' , 8],
                ['2020-05-29' , 10], ['2020-05-30' , 26], ['2020-05-31' , 11], ['2020-06-01' , 46], ['2020-06-02' , 3],
                ['2020-06-03' , 22]]
        }
    };
    myChart3.setOption(option3,true);

}