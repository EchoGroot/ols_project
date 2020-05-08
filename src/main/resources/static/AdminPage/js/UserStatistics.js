var option1; //echar使用
var men=[]; //男
var women=[]; //女
var allSex=[]; //总数量
var myChart1=''; //饼状图
//入口函数:在 html 所有标签(DOM)都加载之后，就会去执行。
$(function () {
    // 基于准备好的dom，初始化echarts实例
    myChart1 = echarts.init(document.getElementById('main1'));
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
});
// 加载图表数据
function loadData(role) {
    $.ajax({
        type: "GET",
        url:"/user/getSex",
        data:{
            "role":role
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
}
// 渲染图表
function rendTable() {// 指定图表的配置项和数据
    option1 = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        color:['#9FE6B8','#32C5E9'],
        legend: {
            orient: 'vertical',
            left: 10,
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
}