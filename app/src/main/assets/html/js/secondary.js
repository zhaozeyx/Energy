var	path;
var fEnergytype = window.location.href.split('=')[1]; //页面类型参数
var week = [],
	weekKey = [],
	weekVal = [];
var prevYearData = [],
	currentYearData = [];	
var firstData,
	lastData;
var borderColor = 'rgba(255,255,255,.2)';

var newDate = new Date();
var getYear = newDate.getFullYear(), //当前年
	prevYear = getYear - 1, //上一年
	getDay = newDate.getDate(), //当天
	getMonth = newDate.getMonth() + 1; //当月
var getDate = getYear + "-" + ((getMonth) < 10 ? "0" : "") + (getMonth) + "-" + (getDay < 10 ? "0" : "") + getDay;

//判断当前页面能耗类型
switch (fEnergytype) 
{ 
  case '01000': 
  $('#yearPK').hide();
  path = '/monitor/api/electricityIndex';
  postData(path);
  unit = '千瓦时';
  console.log('电');
  break; 
  case '02000':  
  $('#energyType').hide();
  path = '/monitor/api/waterIndex';
  postData(path);
  unit = '吨';
  console.log('水');
  break; 
  case '07000': 
  $('#energyType').hide();
  path = '/monitor/api/coalIndex';
  postData(path);
  unit = 'MJ';
  console.log('煤');
  break; 
  case '03000':
  $('#energyType').hide();
  path = '/monitor/api/gasIndex';
  postData(path);
  unit = '立方米';
  console.log('天然气');
  break; 

}

//年度曲线
var yearPower = function() {
	var Month = [1,2,3,4,5,6,7,8,9,10,11,12];
	var yearChart = echarts.init(document.getElementById('yearPower'));
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
			right: '1.9%',
			bottom: '0',
			top: '35',
			borderColor: '#f5f5f5',
			borderWidth: 1,
			containLabel: true
		},
		legend: {
			show: true,
			data: [prevYear + '年', getYear + '年']
		},
		xAxis: [{
		 	boundaryGap : false,
			type: 'category',
			data: Month,
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
				symbolSize: 0,
				name: prevYear + '年',
				type: 'line',
				stack: '总量1',
				smooth: true,
				itemStyle: {
					normal: {
						width: 1,
						color: 'rgba(150, 225, 217, .7)'
					}
				},
				//图像区域颜色
				areaStyle: {
					normal: {
						color: 'rgba(214,236,247, .5)'
					}
				},
				data: prevYearData
			},
			{
				name: getYear + '年',
				type: 'line',
				stack: '总量2',
				smooth: true,
				symbolSize: 0,
				itemStyle: {
					normal: {
						width: 1,
						color: 'rgba(248, 129, 129, .9)'
					}
				},
				//图像区域颜色
				areaStyle: {
					normal: {
						color: 'rgba(225, 200, 200, .3)'
					}
				},
				data: currentYearData
			}
		]
	};
	// 使用刚指定的配置项和数据显示图表。
	yearChart.setOption(option);
};

//饼图分类
var classifyChart = function(data) {
	var myChart = echarts.init(document.getElementById('classifyChart'));
	console.log(data);
	var option = {
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},

		visualMap: {
			show: false,
			min: 60,
			max: 700,
 			inRange: {color: ['#89d9fa','#77cefb','#51b8fc', '#2ca3fd','#1999fe']}
		},
		series: [{
			name: '能耗分类',
			type: 'pie',
			radius: '85%',
			center: ['50%', '50%'],
			data: [{
					value: Number(data.airConditioningElec).toFixed(2),
					name: '空调用电'
				},
				{
					value: Number(data.specialElec).toFixed(2),
					name: '特殊用电'
				},
				{
					value: Number(data.lightingElec).toFixed(2),
					name: '照明插座'
				},
				{
					value: Number(data.powerElec).toFixed(2),
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
var gaugeChart = function(min, max, total, Mtotal ,unit) {
	var myChart = echarts.init(document.getElementById('gaugeChart'));
	var option = {
		tooltip: {
			formatter: "{a} <br/>{b} : {c}%"
		},
		series: [{
			//name: '业务指标',
			type: 'gauge',
			radius: '95%',
			min:min,
			max:max,
			//仪表盘轴线配置
			axisLine: {
				show: true,
				lineStyle: {
					color: [
						[0.2, '#55dc42'],
						[0.8, '#1697ea'],
						[1, '#ff9000']
					],
					width: 10
				}
			},
			//分割线配置
			splitLine: {
				show: true,
				length: 10
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
				formatter: total,
				offsetCenter: [0, '80%'],
				textStyle: {
					fontSize: 18
				}
			},
			data: [{
				value: Mtotal,
				name:unit
			}]
		}]
	};
	myChart.setOption(option);
}

//周统计图表
var weekChart = function() {

	var myChart = echarts.init(document.getElementById('weekChart'));
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
		xAxis: [{
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
		}],
		yAxis: [{
			type: 'value',
			name: unit,
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
			data: weekVal
		}]
	};
	myChart.setOption(option);
};

//数据请求
function postData(path) {
	$.ajax({
		type: 'POST',
		url: api + path,
		data: JSON.stringify({ "fDatacenterid": Datacenterid }),
		contentType: 'application/json',
		timeout: 10000,
		beforeSend:function(){
			 $.showIndicator();
		},		
		success: function(res) {
			$.hideIndicator();
			var data = res.data;
			deployData(data);
			classifyChart(data);

		},
		error: function(xhr, type) {
			$.hideIndicator();
			//alert('Ajax error!')
		}
	})
}

//最近七天数据
function curveByWeek(fEnergytype,getdata,op) {	
	var weekData = { "fDatacenterid": Datacenterid, "fEnergytype": fEnergytype, "theTime": getdata, "op": op };	
	console.log(weekData)
	$.ajax({
		type: 'POST',
		url: api + '/monitor/api/curveByWeek',
		data: JSON.stringify(weekData),
		contentType: 'application/json',
		timeout: 10000,
		beforeSend:function(){
			$.showIndicator();
		},
		success: function(res) {
			$.hideIndicator();
			var res = res.data;
			if($.isEmptyObject( res )){
				$.toast("查询数据不存在");
			}
			weekKey.splice(0,weekKey.length);//清空数组 
			weekVal.splice(0,weekVal.length);//清空数组 
			week.splice(0,week.length);//清空数组 
			$.each(res, function(key, val) {
				var valFn = Number(val).toFixed(2);
				weekKey.push(key);
				weekVal.push(valFn);
			});
		
			$.each(weekKey, function(index) {
				week.push(this.substring(5, 100))
			})
			
			if($.isEmptyObject( res )){
				$('#weekChart').html('<p style="text-align:center;line-height:10rem;color:#fff;">暂无数据</p>');
				$('#prevWeek').hide();
				$('#nextWeek').hide();
			}else{
				$('#prevWeek').show();
				$('#nextWeek').show();
				var weekKeyFirst = weekKey[0].toString();
				var weekKeyLast = weekKey.pop().toString();
				firstData = weekKeyFirst;
				lastData = weekKeyLast;

				weekChart();
				//最近7天数据索引显示
				$('#contentWeek').text(weekKeyFirst + ' -- ' + weekKeyLast);
				//日期对比	
				var start=new Date(getDate.replace("-", "/").replace("-", "/"));  
				var end=new Date(lastData.replace("-", "/").replace("-", "/"));  
				//console.log(start,end)
				if(end<start){
					$('#nextWeek').removeAttr('disabled');
				}else if(end>=start){
					$('#nextWeek').attr('disabled','disabled');
				}
				
			}
			//console.log(getDate, lastData)
			
		},
		error: function(xhr, type) {
			$.hideIndicator();
			console.log('ajax error');
		}
	})
}

//上一周
$('#prevWeek').bind('click', function() {
	var op = 'previous';
	var getdata = firstData;
	//var lastData = lastData;
	curveByWeek(fEnergytype,getdata,op);
});
//下一周
$('#nextWeek').bind('click', function() {
	var op = 'next';
	var getdata =lastData;
	curveByWeek(fEnergytype,getdata,op)
});

//数据部署
function deployData(data) {
	var electricityType = '<ul>' +
		'<li class="item-content">' +
		'<div class="item-media">' +
		'<i class="icon icon-app"></i>' +
		'</div>' +
		'<div class="item-inner">' +
		'<div class="item-title">照明插座用电</div>' +
		'<div class="item-after">' + Number(data.lightingElec).toFixed(2) + ' 千瓦时</div>' +
		'</div>' +
		'</li>' +
		'<li class="item-content">' +
		'<div class="item-media">' +
		'<i class="icon icon-app"></i>' +
		'</div>' +
		'<div class="item-inner">' +
		'<div class="item-title">空调用电</div>' +
		'<div class="item-after">' + Number(data.airConditioningElec).toFixed(2) + ' 千瓦时</div>' +
		'</div>' +
		'</li>' +
		'<li class="item-content">' +
		'<div class="item-media">' +
		'<i class="icon icon-app"></i>' +
		'</div>' +
		'<div class="item-inner">' +
		'<div class="item-title">动力用电</div>' +
		'<div class="item-after">' + Number(data.powerElec).toFixed(2) + ' 千瓦时</div>' +
		'</div>' +
		'</li>' +
		'<li class="item-content">' +
		'<div class="item-media">' +
		'<i class="icon icon-app"></i>' +
		'</div>' +
		'<div class="item-inner">' +
		'<div class="item-title">特殊用电</div>' +
		'<div class="item-after">' + Number(data.specialElec).toFixed(2) + ' 千瓦时</div>' +
		'</div>' +
		'</li>' +
		'</ul>';
	$('#electricityType').html(electricityType);
	
	//月度数据图表信息
	//	同比
	$('#yoy').html(function() {
		var d = data.yearOnYear * 100;
		var str = d.toString();
		if(d > 0) {
			var sub = str.substring(0, 2);
			return sub + '%' + '<i class="icon-t"></i>'
		}else if(d == 0){
			return '—'
		}  else {
			var sub = str.substring(1, 3);
			return sub + '%' + '<i class="icon-b"></i>'
		}
	});
	//月能耗图表
	var total = data.total;
	var unit = '';
	var Mtotal = Number(data.monthOnStd.total);
	// var Mtotal = 500.00;
		total = Number(total).toFixed(0);
	var max = Number(data.monthOnStd.max);
	// var max = 500;
	var min = Number(data.monthOnStd.step1);
	console.info(max.toFixed(0).toString().length)
	switch (min.toFixed(0).toString().length){
		case 4:
			min = Number(min/1000).toFixed(0)
			break;
		case 5:
			min = Number(min/10000).toFixed(0)
			break;
		case 6:
			min = Number(min/100000).toFixed(0)
			break;
		case 7:
			min = Number(min/1000000).toFixed(0)
			break;
		default:
			min = Number(min).toFixed(0)
	}

	if(max == '0'){
		max = data.monthOnStd.total*1.2;
		//max = 24622*1.2;
		switch (Mtotal.toFixed(0).toString().length){
			case 4:
				max = Number(max/1000).toFixed(0)
				break;
			case 5:
				max = Number(max/10000).toFixed(0)
				break;
			case 6:
				max = Number(max/100000).toFixed(0)
				break;
			case 7:
				max = Number(max/1000000).toFixed(0)
				break;
			default:
				max = Number(max).toFixed(0)
		}
	}else {
		switch (max.toFixed(0).toString().length){
			case 4:
				max = Number(max/1000).toFixed(0)
				break;
			case 5:
				max = Number(max/10000).toFixed(0)
				break;
			case 6:
				max = Number(max/100000).toFixed(0)
				break;
			case 7:
				max = Number(max/1000000).toFixed(0)
				break;
			default:
				max = Number(max).toFixed(0)
		}
	}

	switch (Mtotal.toFixed(0).toString().length){
		case 4:
			console.log('千',Mtotal.toFixed(0));
			Mtotal = Mtotal/1000;
			unit = 'x1千';
			break;
		case 5:
			console.log('万',Mtotal.toFixed(0));
			Mtotal = Mtotal/10000;
			unit = 'x1万';
			break;
		case 6:
			console.log('十万',Mtotal.toFixed(0));
			Mtotal = Mtotal/100000;
			unit = 'x10万';
			break;
		case 7:
			console.log('百万',Mtotal.toFixed(0));
			Mtotal = Mtotal/100000;
			unit = 'x1百万';
			break;
		default:
			console.log(Mtotal.toFixed(0));
			Mtotal = Mtotal.toFixed(0)
	}

	console.log(min, max, total, Mtotal, unit)

	gaugeChart(min, max, total, Mtotal, unit);
	//	环比
	$('#mom').html(function() {
		var d = data.monthOnMonth * 100;
		var str = d.toString();
		if(d > 0) {
			var sub = str.substring(0, 2);
			return sub + '%' + '<i class="icon-t"></i>'
		}else if(d == 0){
			var sub = '—'
			return sub
		}else {
			var sub = str.substring(1, 3);
			return sub + '%' + '<i class="icon-b"></i>'
		}
	});
	//单位面积能耗
	$('#byArea').text(Number(data.byArea).toFixed(2));
	//人均能耗
	$('#byPerson').text(Number(data.byPerson).toFixed(2));

	//年度曲线对比
	if(data.curveByYear){
		var powerCurveData0 = data.curveByYear[prevYear];
		var powerCurveData1 = data.curveByYear[getYear];
		$.each(powerCurveData0, function(key, value) {
			var Nvalue =  Number(value).toFixed(2);
			var valueFn = (Nvalue == 0.00) ? Nvalue = '':Nvalue;
			prevYearData.push(valueFn);
		});
		$.each(powerCurveData1, function(key, value) {
			var Nvalue =  Number(value).toFixed(2);
			var valueFn = (Nvalue == 0.00) ? Nvalue = '':Nvalue;
			currentYearData.push(valueFn);
		});
		yearPower();
	}
	//取消加载中
//	$.hidePreloader();
}

//页面初始化
$(function() {
	var fEnergytype = window.location.href.split('=')[1]; //页面类型参数
	var	getdata = getDate;
	var	op = 'init';	
	curveByWeek(fEnergytype,getdata,op);	
})