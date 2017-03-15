var api = 'http://192.168.4.14:8080';
var weekKey = [],
	weekVal = [];
var borderColor = 'rgba(255,255,255,.2)';
var newDate = new Date();
var getYear = newDate.getFullYear(), //当前年
 	getDay = newDate.getDate(), //当天
	getMonth = newDate.getMonth() + 1; //当月
var getDate = getYear+"-"+((getMonth)<10?"0":"")+(getMonth)+"-"+(getDay<10?"0":"")+getDay;
var weekData = { "fDatacenterid": "1", "fEnergytype": "01000", "theTime": getDate, "op": "init" };


//饼图分类
var classifyChart = function(data) {
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
		series: [{
			name: '能耗分类',
			type: 'pie',
			radius: '85%',
			center: ['50%', '50%'],
			data: [{
					value: data.airConditioningElec,
					name: '空调用电'
				},
				{
					value: data.specialElec,
					name: '特殊用电'
				},
				{
					value: data.lightingElec,
					name: '照明插座'
				},
				{
					value: data.powerElec,
					name: '动力用电'
				}
			].sort(function(a, b) {
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
			animationDelay: function(idx) {
				return Math.random() * 200;
			}
		}]
	}
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
};

//码表统计
var gaugeChart = function() {
	var myChart = echarts.init(document.getElementById('gaugeChart'));
	var option = {
		tooltip: {
			formatter: "{a} <br/>{b} : {c}%"
		},
		series: [{
			name: '业务指标',
			type: 'gauge',
			radius: '90%',
			//仪表盘轴线配置
			axisLine: {
				show: true,
				lineStyle: {
					color: [
						[0.2, '#55dc42'],
						[0.8, '#1697ea'],
						[1, '#ff9000']
					],
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
		}]
	};
	myChart.setOption(option);
}

//周统计图表
var weekChart = function() {
	var week = []
	$.each(weekKey,function(index){

		week.push(this.substring(5,10))
//		alert(this.substring(5,9))
	})
	
	
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
		xAxis: [{
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
		}],
		yAxis: [{
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
		}],
		series: [{
			name: getYear,
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
			data:weekVal
		}]
	};
	myChart.setOption(option);
};

//数据请求
function postData() {
	$.ajax({
		type: 'POST',
		url: api + '/monitor/api/electricityIndex',
		data: JSON.stringify({ "fDatacenterid": "1" }),
		contentType: 'application/json',
		timeout: 10000,
		success: function(res) {
			var data = res.data;
			deployData(data);
			classifyChart(data);

		},
		error: function(xhr, type) {
			//alert('Ajax error!')
		}
	})
}

//最近七天数据
function curveByWeek() {
	$.ajax({
		type: 'POST',
		url: api + '/monitor/api/curveByWeek',
		data: JSON.stringify(weekData),
		contentType: 'application/json',
		timeout: 10000,
		success: function(res) {
			//alert(JSON.stringify(res));
			var res = res.data;
			var yearData = res[getYear];
			$.each(yearData, function(key,val) {
				weekKey.push(key);
				weekVal.push(val);
			});
			weekChart();
		},
		error: function(xhr, type) {
			console.log('ajax error');
		}
	})
}

//数据部署
function deployData(data) {
	var electricityType = '<ul>' +
		'<li class="item-content">' +
		'<div class="item-media">' +
		'<i class="icon icon-app"></i>' +
		'</div>' +
		'<div class="item-inner">' +
		'<div class="item-title">照明插座用电</div>' +
		'<div class="item-after">' + data.lightingElec + '千瓦时</div>' +
		'</div>' +
		'</li>' +
		'<li class="item-content">' +
		'<div class="item-media">' +
		'<i class="icon icon-app"></i>' +
		'</div>' +
		'<div class="item-inner">' +
		'<div class="item-title">空调用电</div>' +
		'<div class="item-after">' + data.airConditioningElec + '千瓦时</div>' +
		'</div>' +
		'</li>' +
		'<li class="item-content">' +
		'<div class="item-media">' +
		'<i class="icon icon-app"></i>' +
		'</div>' +
		'<div class="item-inner">' +
		'<div class="item-title">动力用电</div>' +
		'<div class="item-after">' + data.powerElec + '千瓦时</div>' +
		'</div>' +
		'</li>' +
		'<li class="item-content">' +
		'<div class="item-media">' +
		'<i class="icon icon-app"></i>' +
		'</div>' +
		'<div class="item-inner">' +
		'<div class="item-title">特殊用电</div>' +
		'<div class="item-after">' + data.specialElec + '千瓦时</div>' +
		'</div>' +
		'</li>' +
		'</ul>';
	$('#electricityType').html(electricityType);

}

//页面初始化
$(function() {
	postData();
	curveByWeek();
	gaugeChart();

})