var userId=getQueryVariable('userId'); //获取URL参数里的用户ID
var state1=[];var state2=[];var state3=[];var state4=[];var state5=[];var state6=[];var state7=[];
var free;var used;
$(function () {
    var year=2020;
    $("#Header").append("图片任务后台管理可视化分析");
    /*$("#GoBack").click(function () {
        parent.$("#iframeMain").attr("src",$(this).attr("href")+'userId='+userId);
    })*/
    $.ajax({
        url: '/task/getAdminImgChartData',
        type: "GET",
        data: {
            year:year
        },
        success: function (resultData) {
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                state1=resultData.data.releaseList[0];
                state2=resultData.data.releaseList[1];
                state3=resultData.data.releaseList[2];
                state4=resultData.data.releaseList[3];
                state5=resultData.data.releaseList[4];
                state6=resultData.data.releaseList[5];
                state7=resultData.data.releaseList[6];
                GenerateChart();
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    })

    $.ajax({
        url: '/task/getDiskInfo',
        type: "GET",
        success: function (resultData) {
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                free = resultData.data.free;
                used = resultData.data.used;
                GenerateChart1();
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    })
    $("#selectYear").change(function () {
        switch ($("#selectYear").val()) {
            case '0':
                AjaxA1(2020);
                break;
            case '1':
                AjaxA1(2020);
                break;
            case '2':
                AjaxA1(2019);
                break;
        }
    })
})

function AjaxA1(year) {
    $.ajax({
        url: '/task/getAdminImgChartData',
        type: "GET",
        data: {
            year:year
        },
        success: function (resultData) {
            resultData=JSON.parse(resultData);
            if(resultData.meta.status === "200"){
                state1=resultData.data.releaseList[0];
                state2=resultData.data.releaseList[1];
                state3=resultData.data.releaseList[2];
                state4=resultData.data.releaseList[3];
                state5=resultData.data.releaseList[4];
                state6=resultData.data.releaseList[5];
                state7=resultData.data.releaseList[6];
                GenerateChart();
            }else{
                layer.msg('操作失败，请刷新页面', {
                    icon: 5, //红色不开心
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        }
    })
}

function GenerateChart() {
    var ImgChart = echarts.init(document.getElementById('ChartMain'));
    var option = {
        title: {
            text: '全站图片任务堆叠图'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data: ['已完成', '已删除', '已失效', '审核中', '已发布','未过审','未标注示例'] //全部已完成-其他所有=
        },
        toolbox: {
            feature: {
                magicType: {show: true, type: ['line']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
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
                //boundaryGap: false,
                data: [ '1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '已完成',
                type: 'bar',
                stack: '总量',
                areaStyle: {},
                data: state1
            },
            {
                name: '已删除',
                type: 'bar',
                stack: '总量',
                areaStyle: {},
                data: state2
            },
            {
                name: '已失效',
                type: 'bar',
                stack: '总量',
                areaStyle: {},
                data: state3
            },
            {
                name: '审核中',
                type: 'bar',
                stack: '总量',
                areaStyle: {},
                data: state4
            },
            {
                name: '未过审',
                type: 'bar',
                stack: '总量',
                areaStyle: {},
                data: state6
            },
            {
                name: '未标注示例',
                type: 'bar',
                stack: '总量',
                areaStyle: {},
                data: state7
            },
            {
                name: '已发布',
                type: 'bar',
                stack: '总量',

                areaStyle: {},
                data: state5
            },{
                name:'总量',
                type: 'line',
                stack: '总计',
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        textStyle: { color: '#000' },
                    }
                },
                itemStyle: {
                    normal: {
                        color: 'rgba(128, 128, 128, 0)',
                        borderWidth: 1,
                        borderColor: '#1FBCD2'
                    }
                },
                areaStyle: {},
                data: [0,0,0,0,0,0,0,0,0,0,0,0]
            }
        ]
    };
    var series = option["series"];
    var m=0;
    var fun = function (params) {
        var data3=0;
        for(var i=0,l=series.length-1;i<l;i++){
            data3 += series[i].data[params.dataIndex]
        }
        if(m<12){
            series[series.length-1].data[params.dataIndex]=data3;m++;
        }
        return data3
    }
    //加载页面时候替换最后一个series的formatter
    series[series.length-1]["label"]["normal"]["formatter"] = fun

    ImgChart.on("legendselectchanged", function(obj) {
        var b = obj.selected
            , d = [];
        //alert(JSON.stringify(b))
        for(var key in b){
            if(b[key]){
                //alert(key)
                for(var i=0,l=series.length;i<l;i++){
                    var changename = series[i]["name"];
                    if(changename == key){
                        d.push(i);//得到状态是true的legend对应的series的下标
                    }
                }
            }
        }
        m=0;
        var fun1 = function (params) {
            var data3 =0;
            for(var i=0,l=d.length;i<l;i++){
                for(var j=0,h=series.length;j<h;j++){
                    if(d[i] == j){
                        data3 += series[j].data[params.dataIndex] //重新计算总和
                    }
                }
            }
            if(m<12){
                series[series.length-1].data[params.dataIndex]=data3;m++;
            }
            return data3
        }
        series[series.length-1]["label"]["normal"]["formatter"] = fun1
        ImgChart.setOption(option);
        ImgChart.setOption(option);
    })
    ImgChart.setOption(option);
    ImgChart.setOption(option);
}

function GenerateChart1() {
    var ImgChart = echarts.init(document.getElementById('ChartMain1'));
    var option = {
        title: {
            text: '服务器容量',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c}'+"GB"+' ({d}%)'
        },
        series: [
            {
                name: '服务器容量',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: used, name: '已用空间',itemStyle: {
                            color: '#26a0da'
                        }},
                    {value: free, name: '可用空间',itemStyle: {
                            color: '#acacac'
                        }},
                ],
                itemStyle:{
                    normal: {
                        borderWidth: 5,
                        borderColor: '#fff',
                    }
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 10,
                        shadowColor: 'rgba(222, 222, 222, 0.5)'
                    }
                }
            }
        ]
    };
    ImgChart.setOption(option);
}

function GenerateChart2() {
    var ImgChart = echarts.init(document.getElementById('ChartMain2'));
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