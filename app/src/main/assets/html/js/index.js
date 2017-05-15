console.log(api)
var prevYearData = [];
var currentYearData = [];
var prevYearMonth = [];
var currentYearMonth = [];
var mydate = new Date();
var currentYear = mydate.getFullYear(); //当前年
var prevYear = currentYear - 1; //上一年

var yearPower = function() {
	// console.log(prevYearData)
	$.each(prevYearMonth, function(index, item) {
		//item[index]+'月'
		//console.log(this+'月');
		currentYearMonth.push(this + '月')
	});
	var yearChart = echarts.init(document.getElementById('yearPower'));
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
			right: '1.9%',
			bottom: '0',
			top: '35',
			borderColor: '#f5f5f5',
			borderWidth: 1,
			containLabel: true
		},
		legend: {
			show: true,
			data: [prevYear + '年', currentYear + '年']
		},
		xAxis: [{
		 	boundaryGap : false,
			type: 'category',
			data: prevYearMonth,
			boundaryGap: false,
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
			name:'千瓦时',
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
				name: currentYear + '年',
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

var monthStat = function(data) {
	// console.log(data);
	var myChart = echarts.init(document.getElementById('monthStat'));
	var option = {
		color: ['#2e75b6', '#ea5d7f', '#f2f354', '#73de2b'],
		backgroundColor: '#fff', //背景色
		series: [{
			radius: ['80%', '88%'],
			type: 'pie',
			selectedOffset: 6,
			labelLine: {
				normal: {
					show: false
				}
			},
			data: [{
					value: data.lightElec,
					name: ''
				},
				{
					value: data.airElec,
					name: ''
				},
				{
					value: data.powerElec,
					name: ''
				},
				{
					value: data.specialElec,
					name: ''
				},
			]
		}]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

//数据请求
function indexPost() {
	$.ajax({
		type: 'POST',
		url: api + '/monitor/api/index',
		data: JSON.stringify({ "fDatacenterid": Datacenterid }),
		contentType: 'application/json',
		timeout: 4000,
		beforeSend:function(){
			 $.showIndicator();
		},
		success: function(data) {
			$.hideIndicator();
			$('#network').hide();
			var data = data.data;
			var powerCurveData0 = data.powerCurve[prevYear];
			var powerCurveData1 = data.powerCurve[currentYear];
			$.each(powerCurveData0, function(key, value) {
				var Nvalue =  Number(value).toFixed(2);
				var valueFn = (Nvalue == 0.00) ? Nvalue = '':Nvalue;
				// console.log(valueFn);
				prevYearData.push(valueFn);
				prevYearMonth.push(key);
			});
			$.each(powerCurveData1, function(key, value) {
				var Nvalue =  Number(value).toFixed(2);
				var valueFn = (Nvalue == 0.00) ? Nvalue = '':Nvalue;
				currentYearData.push(valueFn);
			});
			//年度图表数据
			yearPower();
			//月度用电占比数据
			monthStat(data);
			//其他数据
			deployData(data);
			//关闭加载中		
        	//$.hidePreloader();

		},
		error: function(xhr, errorType, error) {
			$.hideIndicator();
			//alert('Ajax error!')
			console.log(xhr, errorType, error)
		},
		complete:function(xhr, status){
			console.log(xhr, status)
			if(status == 'abort'|| status== 'timeout'){
				console.log('网络错误');
				$('#network').show();				
			}
		}
	})
}

$('#resetNetwork').click(function(){
	indexPost()
})

//判断数组是否为空
function isEmpty(value) {
    return (Array.isArray(value) && value.length === 0) || (Object.prototype.isPrototypeOf(value) && Object.keys(value).length === 0);
}

//数据部署
function deployData(data) {
	//	同比
	$('#yoy').html(function() {
		var d = data.yearOnYear * 100;
		var str = d.toString();
		if(d > 0) {
			var sub = str.substring(0, 2);
			return '同比 ' + sub + '%' + '<i class="icon-t"></i>'
		}else if(d == 0){
			return '同比 '+ '—'
		} else {
			var sub = str.substring(1, 3);
			return '同比' + sub + '%' + '<i class="icon-b"></i>'
		}
	});
	//月用电总量
	$('#totalM').html(Number(data.totalElectricity).toFixed(0));
	//	环比
	$('#mom').html(function() {
		var d = data.monthOnMonth * 100;
		var str = d.toString();
		if(d > 0) {
			var sub = str.substring(0, 2);
			return '环比 ' + sub + '%' + '<i class="icon-t"></i>'
		}else if(d == 0){
			return '环比 '+ '—'
		}else {
			var sub = str.substring(1, 3);
			return '环比' + sub + '%' + '<i class="icon-b"></i>'
		}
	});
	//用电分类
	var electricityType = '<div class="row">' +
		'<div class="col-25">' +
		'<div class="item">' +
		'<div class="icon-zm"></div>' +
		'<small>照明</small>' +
		'<p>' + Number(data.lightElec).toFixed(2)+ '</p>' +
		'</div>' +
		'</div>' +
		'<div class="col-25">' +
		'<div class="item">' +
		'<div class="icon-kt"></div>' +
		'<small>空调</small>' +
		'<p>' + Number(data.airElec).toFixed(2)+ '</p>' +
		'</div>' +
		'</div>' +
		'<div class="col-25">' +
		'<div class="item">' +
		'<div class="icon-dl"></div>' +
		'<small>动力</small>' +
		'<p>' + Number(data.powerElec).toFixed(2)+ '</p>' +
		'</div>' +
		'</div>' +
		'<div class="col-25">' +
		'<div class="item">' +
		'<div class="icon-ts"></div>' +
		'<small>特殊</small>' +
		'<p>' + Number(data.specialElec).toFixed(2)+ '</p>' +
		'</div>' +
		'</div>' +
		'</div>';
	$('#electricityType').html(electricityType);
	// 能耗分析
    var energyList = '',
		data = data.energyList;
    for(var i = 0; i < data.length; i++){
        energyList +=
            '<li>' +
            '<a href="secondary.html?typeid='+data[i].fEnergytype+'" class="item-link item-content" external>' +
            '<div class="item-media"><i class="icon icon-f7"></i></div>' +
            '<div class="item-inner">' +
            '<div class="item-title">'+data[i].fEnergyname+'</div>' +
            '<div class="item-after">'+Number(data[i].totalAmount).toFixed(2)+' '+data[i].fEnergyitemunit+'</div>' +
            '</div>' +
            '</a>' +
            '</li>';
    }
    var energyMain = '<div class="content-block-title">能耗总览</div>'+
        '<div class="list-block"><ul>'+energyList+'</ul></div>';
    if(isEmpty(data) == true){
        $('#energyTotal').html('<div class="list-block"></div>');
    }else{
        $('#energyTotal').html(energyMain);
    }
}

//初始化图表
$(function() {
	indexPost()
});