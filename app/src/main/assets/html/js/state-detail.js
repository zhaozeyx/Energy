var fMeId = window.location.href.split('=')[1]; //页面类型参数
var data = { "fMeId": fMeId };
var curveByWeekX =[],
	curveByWeekData = [];
var oneData = [],
	twoData = [],
	threeData = []
	curveByDayX = [];

//三相电流曲线
var electricityChart = function() {
	var electricityChart = echarts.init(document.getElementById('electricity'));
	var option = {
		title: {
			text: '(年度)',
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
			right: '3.5%',
			bottom: '0',
			top: '35',
			borderColor: '#f5f5f5',
			borderWidth: 1,
			containLabel: true
		},
		legend: {
			show: true,
			//data: ['2016', '2017']
		},		
		xAxis: {
			boundaryGap: false,
			type: 'category',
			//nameLocation:'middle',
			//interval:10,
			data: curveByDayX,
			splitNumber:12,
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
				},
				interval : 2,
                formatter : function(params){
                    var newParamsName = "";
                    var paramsNameNumber = params.length;
                    var provideNumber = 3;
                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                    if (paramsNameNumber > provideNumber) {
                        for (var p = 0; p < rowNumber; p++) {
                            var tempStr = "";
                            var start = p * provideNumber;
                            var end = start + provideNumber;
                            if (p == rowNumber - 1) {
                                tempStr = params.substring(start, paramsNameNumber);
                            } else {
                                tempStr = params.substring(start, end) + "\n";
                            }
                            newParamsName += tempStr;
                        }

                    } else {
                        newParamsName = params;
                    }
                    return newParamsName
                }
			}
		},
		yAxis: [{

			type: 'value',
			//name:'千瓦时',
			nameTextStyle: {
				color: '#646464'
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
		}],
		series: [{
			name: 'A',
			type: 'line',
			stack: '总量',
			smooth: true,
			symbolSize: 5,
			itemStyle: {
				normal: {
					width: 1,
					color: '#96ffd9'
				}
			},

			data: oneData
		}, {
			name: 'B',
			type: 'line',
			stack: '总量',
			smooth: true,
			symbolSize: 5,
			itemStyle: {
				normal: {
					width: 1,
					color: '#ff9696'
				}
			},

			data: twoData
		}, {
			name: 'C',
			type: 'line',
			stack: '总量',
			smooth: true,
			symbolSize: 5,
			itemStyle: {
				normal: {
					width: 1,
					color: '#96c7ff'
				}
			},

			data: threeData
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	//electricityChart.hideLoading();
	electricityChart.setOption(option);
}

//最近7天用电曲线
var weekChart = function() {
//	var month = ['1-1', '1-2', '1-3', '1-4', '1-5', '1-6', '1-7'];
	var yearChart = echarts.init(document.getElementById('weekChart'));
	var option = {
		title: {
			//text: '(年度)',
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
			right: '3.5%',
			bottom: '0',
			top: '35',
			borderColor: '#f5f5f5',
			borderWidth: 1,
			containLabel: true
		},
		legend: {
			show: true,
			//data: ['2016', '2017']
		},
		xAxis: [{
			boundaryGap: false,
			type: 'category',
			data: curveByWeekX,
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
		}],
		yAxis: [{

			type: 'value',
			//name:'千瓦时',
			nameTextStyle: {
				color: '#646464'
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
		}],
		series: [{
			name: '用电',
			type: 'line',
			//stack: '总量',
			smooth: true,
			symbolSize: 0,
			itemStyle: {
				normal: {
					width: 1,
					color: 'rgb(165, 197, 235)'
				}
			},
			//图像区域颜色
			areaStyle: {
				normal: {
					color: 'rgb(212, 232, 255)'
				}
			},
			data: curveByWeekData
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	yearChart.setOption(option);
}
//获取数据
var getData = function() {
	$.ajax({
		type: "post",
		url: api + '/monitor/api/device/meterview',
		data: JSON.stringify(data),
		contentType: 'application/json',
		timeout: 10000,
		async: true,
		beforeSend: function() {
			$.showIndicator();
		},
		success: function(res) {
			$.hideIndicator();
			var res = res.data;
			deployData(res);
		},
	});
}
//部署数据到dom
function deployData(res) {
	//最近七天数据
	var curveByWeek = res.curveByWeek;
//	alert(JSON.stringify(curveByWeek))
	$.each(curveByWeek, function(key,val) {
		curveByWeekX.push(key.substring(5, 100));
		curveByWeekData.push(val);	
	});
	
	if($.isEmptyObject( res.curveByWeek )){
		$('#weekChart').html('<p style="text-align:center;line-height:10rem;color:#a9a9a9;">暂无数据</p>')
	}else{
		weekChart();
	}
	
	//三相电流数据
	var curveByDay = res.curveByDay;
	var curveByDayA = curveByDay.A,
		curveByDayB = curveByDay.B,
		curveByDayC = curveByDay.C;
	$.each(curveByDayA, function(key,val) {
		curveByDayX.push(key);
		oneData.push(val)
	});
	$.each(curveByDayB,function(key,val){
		twoData.push(val)
	});
	$.each(curveByDayC,function(key,val){
		threeData.push(val)
	});
	console.log(oneData);
	console.log(twoData);
	console.log(threeData);
	console.log(curveByDayX);
	if(curveByDayA == ''&& curveByDayB == '' && curveByDayC ==''){
		$('#electricity').html('<p style="text-align:center;line-height:10rem;color:#a9a9a9;">暂无数据</p>')
	}else{
		electricityChart();
	}
	
	
	//列表详细
	var html = '<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">表计编码</div>' +
		'										<div class="item-after">'+res.info.fMeterid+'</div>' +
		'									</div>' +
		'								</li>' +
		'								<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">表计名称</div>' +
		'										<div class="item-after">'+res.info.fMetername+'</div>' +
		'									</div>' +
		'								</li>' +
		'								<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">建筑物ID</div>' +
		'										<div class="item-after">'+res.info.fBdId+'</div>' +
		'									</div>' +
		'								</li>' +
		'								<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">采集器ID</div>' +
		'										<div class="item-after">'+res.info.fClId+'</div>' +
		'									</div>' +
		'								</li>' +
//		'								<li class="item-content">' +
//		'									<div class="item-inner">' +
//		'										<div class="item-title">表计类型</div>' +
//		'										<div class="item-after">威思顿三相多功能表</div>' +
//		'									</div>' +
//		'								</li>' +
		'								<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">变比</div>' +
		'										<div class="item-after">'+res.info.fRate+'</div>' +
		'									</div>' +
		'								</li>' +
		'								<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">比特率</div>' +
		'										<div class="item-after">'+res.info.fBitnum+'</div>' +
		'									</div>' +
		'								</li>' +
		'								<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">通信违约类型</div>' +
		'										<div class="item-after">'+res.info.fCommtype+'</div>' +
		'									</div>' +
		'								</li>' +
		'								<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">通信地址</div>' +
		'										<div class="item-after">'+res.info.fCommaddress+'</div>' +
		'									</div>' +
		'								</li>' +
		'								<li class="item-content">' +
		'									<div class="item-inner">' +
		'										<div class="item-title">正反向接反</div>' +
		'										<div class="item-after">'+res.info.fReverse+'</div>' +
		'									</div>' +
		'								</li>';

	$('#ammeter-list').html(html);
//	$.hidePreloader();
}

//页面初始化
$(function() {
	var fMeId = window.location.href.split('=')[1]; //页面类型参数	
	getData();
	console.log(fMeId)
	
})