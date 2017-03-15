var borderColor = 'rgba(255,255,255,.2)';

//饼图分类
var classifyChart = function () {
    var myChart = echarts.init(document.getElementById('classifyChart'));
    var option = {
            //color: ['#2e75b6', '#73de2b', '#f2f354', '#ea5d7f'],
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: 60,
                max: 700,
                inRange: {
                    colorLightness: [0.3, 1]
                },
                color: ['#a1dafc', '#3095e6', '#32a5f6', '49b9fd']
            },
            series: [
                {
                    name: '能耗分类',
                    type: 'pie',
                    radius: '85%',
                    center: ['50%', '50%'],
                    data: [
                        {
                            value: 150,
                            name: '空调用电'
                            },
                        {
                            value: 110,
                            name: '特殊用电'
                            },
                        {
                            value: 80,
                            name: '照明插座'
                            },
                        {
                            value: 230,
                            name: '动力用电'
                            }
                        ].sort(function (a, b) {
                        return a.value - b.value
                    }),
                    roseType: 'angle',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(0, 0, 0, 0.9)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(0,0,0,.2)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#a1dafc',
                            shadowColor: 'rgba(0, 0, 0, 0.5)',
                            shadowBlur: 10
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
        }
    ]
        }
        // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
};

//码表统计
var gaugeChart = function () {
    var myChart = echarts.init(document.getElementById('gaugeChart'));
    var option = {
        tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
        },
        series: [
            {
                name: '业务指标',
                type: 'gauge',
                radius: '90%',
                //仪表盘轴线配置
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: [[0.2, '#55dc42'], [0.8, '#1697ea'], [1, '#ff9000']],
                        width: 12
                    }
                },
                //分割线配置
                splitLine: {
                    show: true,
                    length: 12
                },
                //指针配置
                pointer: {
                    show: true,
                    lenght: '50%',
                    width: 3
                },
                //仪表盘标题
                title: {
                    show: true,
                    offsetCenter: [0, '-40%'],
                    textStyle: {
                        color: '#666',
                        fontSize: 10
                    }
                },
                //仪表盘详细数据            
                detail: {
                    formatter: '{value}%',
                    offsetCenter: [0, '80%'],
                    textStyle: {
                        fontSize: 18
                    }
                },
                data: [{
                    value: 90,
                    name: 'Kwh'
                }]
        }
    ]
    };
    myChart.setOption(option);
}

//周统计图表
var weekChart = function () {
    var week = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    var myChart = echarts.init(document.getElementById('weekChart'));
    var option = {
        grid: {
            show: true,
            top: '30',
            left: '0',
            right: '0',
            bottom: '0',
            containLabel: true,
            borderColor: borderColor,
            borderWidth: 1
        },
        tooltip: {
            show:true,
            trigger: 'axis',
            borderColor:'#f00',
            backgroundColor:'rgba(0,0,0,.3)',
            axisPointer:{
                lineStyle:{
                    color:'#fff'
                }
            }
        },
        xAxis: [
            {
                type: 'category',
                data: week,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: borderColor // x轴底线颜色
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: 'none'
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'rgba(255,255,255,1)' //x轴文字颜色
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: borderColor //x轴分割线颜色
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '（千瓦时）',
                nameTextStyle: {
                    color: 'rgba(255,255,255,.9)'
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: 'rgba(255,255,255,.1)' //y轴底线颜色
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'rgba(255,255,255,.9)' //y轴文字颜色
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: 'none'
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: borderColor //y轴分割线颜色
                    }
                },
                scale: true
            }
        ],
        series: [
            {
                symbolSize: 0,
                name: '2016',
                type: 'line',
                stack: '总量',
                smooth: true,
                itemStyle: {
                    normal: {
                        width:1,
                        color: 'rgba(255, 255, 255, .4)'
                    }
                },
                //图像区域颜色
                areaStyle: {
                    normal: {
                        color: 'rgba(255, 255, 255, .3)'
                    }
                },
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: '2017',
                type: 'line',
                stack: '总量',
                symbolSize: 0,
                smooth: true,
                itemStyle: {
                    normal: {
                        color: 'rgb(255, 255, 255)',
                    }
                },
                lineStyle: {
                    normal: {
                        width: 1,
                        color: '#fff'
                    }
                },
                //图像区域颜色
                areaStyle: {
                    //                    normal: {
                    //                        color: 'rgba(255, 255, 255,.5)'
                    //                    }
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 1,
                            color: 'rgba(255, 255, 255, 0)'
                        }, {
                            offset: 0,
                            color: 'rgb(255, 255, 255)'
                        }])
                    }

                },
                data: [150, 232, 201, 154, 190, 330, 117]
            }
        ]
    };
    myChart.setOption(option);
};

//页面初始化
$(function () {
    classifyChart();
    gaugeChart();
    weekChart();
})