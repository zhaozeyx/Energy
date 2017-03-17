var borderColor = 'rgba(255,255,255,.2)';

//周统计图表
var Chart = function () {
    var week = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    var myChart = echarts.init(document.getElementById('Chart'));
    var option = {
        grid: {
            show: true,
            top: '30',
           	left: '3%',
			right: '3.5%',
            bottom: '0',
            containLabel: true,
            borderColor: borderColor,
            borderWidth: 1
        },
        tooltip: {
            show: true,
            trigger: 'axis',
            borderColor: '#f00',
            backgroundColor: 'rgba(0,0,0,.3)',
            axisPointer: {
                lineStyle: {
                    color: '#fff'
                }
            }
        },
        xAxis: [
            {
            boundaryGap: false,
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
    Chart();

    $(document).on("pageInit", function () {
        $("#picker").picker({
            toolbarTemplate: '<header class="bar bar-nav">\
                              <button class="button button-link pull-left close-picker">取消</button>\
                              <button class="button button-link pull-right close-picker">确定</button>\
                              <h1 class="title">请选择能源类型</h1>\
                              </header>',
            cols: [
                {
                    textAlign: 'center',
                    values: ['电', '水', '暖气', '天然气']
        }
      ]
        });
    });

    //建筑选择
    $(document).on('click', '.open-build', function () {
        $.popup('.popup-build');
    });
    //时间维度选择
    $(document).on('click', '.open-time', function () {
        $.popup('.popup-time');
    });
    //自定义时间选择    
    $(document).on('click', '#checkBtn', function () {
        var bol = true;
        var isCheck = $('#checkbox').is(':checked');
        var time = $('#diyTime');
        if (isCheck !== bol) {
            time.show();
        } else {
            time.hide();
        }
    });

    var myDate = new Date();
    var Datefn = myDate.toLocaleDateString();
    var startTime;

    //时间选择控件
    $("#startTime").calendar({
        value: [Datefn],
        maxDate: Datefn,
        onClose: function () {
            startTime = $('#startTime').val();
            var time = new Date(startTime);
            var year =time.getFullYear();
            var month = time.getMonth()+1;
            var day = time.getDate()+1;        
            var minData = year+'/'+month+'/'+day;            
            //console.log(minData);
            $("#endTime").calendar({
                value: [minData],
                minDate:minData,
                maxDate: Datefn
            });        
        }

    });







    $.init();
})