var powerChart = function () {
    var month = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    var powerChart = echarts.init(document.getElementById('powerChart'));
    var option = {
        title: {
            text: '(功率因数)',
            textStyle: {
                color: 'rgba(0,0,0,.5)',
                fontSize: 14,
                fontWeight: 'none'
            }
        },
         tooltip: {
            show: true,
            trigger: 'axis',
            borderColor: '#f00',
            backgroundColor: 'rgba(0,0,0,.3)',
            axisPointer: {
                lineStyle: {
                    color: '#a5c5eb'
                }
            }
        },
         grid: {
            show: true,
            left: '0',
            right: '0',
            bottom: '0',
            top: '35',
            borderColor: '#f5f5f5',
            borderWidth: 1,
            containLabel: true
        },
        legend: {
            show: true,
            data: ['2017']
        },
        xAxis: [
            {
                type: 'category',
                data: month,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#f5f5f5' // x轴底线颜色
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: 'none'
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#f5f5f5'
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'rgba(0,0,0,.5)' //x轴文字颜色
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                //name:'千瓦时',
                nameTextStyle: {
                    color: 'rgba(0,0,0,.5)'
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#f5f5f5' //y轴底线颜色
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'rgba(0,0,0,.5)' //y轴文字颜色
                    }
                },
                 splitLine: {
                    show: false
                },
            }
        ],
        series: [
            {
                symbolSize: 0,
                name: '2017',
                type: 'line',
                stack: '总量',
                smooth: true,
                itemStyle: {
                    normal: {
                        width: 1,
                        color: 'rgb(187, 228, 249)'
                    }
                },
                //图像区域颜色
                areaStyle: {
                    normal: {
                        color: 'rgb(214,236,247)'
                    }
                },
                data: [120, 132, 101, 134, 90, 230, 210, 220, 182, 191, 234, 290]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    powerChart.setOption(option);

};

var costChart = function () {
    var month = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    var costChart = echarts.init(document.getElementById('costChart'));
    var option = {
        title: {
            text: '(电费)',
            textStyle: {
                color: 'rgba(0,0,0,.5)',
                fontSize: 14,
                fontWeight: 'none'
            }
        },
         tooltip: {
            show: true,
            trigger: 'axis',
            borderColor: '#f00',
            backgroundColor: 'rgba(0,0,0,.3)',
            axisPointer: {
                lineStyle: {
                    color: '#a5c5eb'
                }
            }
        },
         grid: {
            show: true,
            left: '0',
            right: '0',
            bottom: '0',
            top: '35',
            borderColor: '#f5f5f5',
            borderWidth: 1,
            containLabel: true
        },
        legend: {
            show: true,
            data: ['2017']
        },
        xAxis: [
            {
                type: 'category',
                data: month,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#f5f5f5' // x轴底线颜色
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: 'none'
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#f5f5f5'
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'rgba(0,0,0,.5)' //x轴文字颜色
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                //name:'千瓦时',
                nameTextStyle: {
                    color: 'rgba(0,0,0,.5)'
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#f5f5f5' //y轴底线颜色
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'rgba(0,0,0,.5)' //y轴文字颜色
                    }
                },
                 splitLine: {
                    show: false
                },
            }
        ],
        series: [
            {
                symbolSize: 0,
                name: '2017',
                type: 'line',
                stack: '总量',
                smooth: true,
                itemStyle: {
                    normal: {
                        width: 1,
                        color: 'rgb(187, 228, 249)'
                    }
                },
                //图像区域颜色
                areaStyle: {
                    normal: {
                        color: 'rgb(214,236,247)'
                    }
                },
                data: [150, 232, 201, 154, 190, 330, 410, 934, 844, 330, 132, 665]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    costChart.setOption(option);

};

$(function(){
	powerChart();
	costChart();
})
