var borderColor = 'rgba(255,255,255,.2)';
var op = 'init',	//previous上一页  next下一页  init最近七天
	fEnergytype = '01000',	//能源类型
	byType = 'all',	//all build  floor  circuit =>全部  建筑物  楼层 回路
	byId = '',	//byId =>  建筑物ID fBdId   fDatalevelid  回路ID fCcId
	fScheme ='d',	//d : 天  m：月  y：年  o：自定义
	theTimeS ='',	//当fScheme=o,theTimeS 是开始时间
	theTimeE = '',	//当fScheme=o,theTimeE是结束时间
	theTime ='';
var dateX = [],
	chartData = [],
	xAxisData = [];
var firstData = '',
	lastData = '';
var unit = '千瓦时';
var fEnergytypeAll = '01000'
var myDate = new Date();
var Datefn = myDate.toLocaleDateString();	
var newDate = new Date();
var getYear = newDate.getFullYear(), //当前年
	prevYear = getYear - 1, //上一年
	getDay = newDate.getDate(), //当天
	getMonth = newDate.getMonth() + 1; //当月
var getDate = getYear + "-" + ((getMonth) < 10 ? "0" : "") + (getMonth) + "-" + (getDay < 10 ? "0" : "") + getDay;

//统计图表
var Chart = function () {
    //var week = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
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
                data: xAxisData,
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
            }
        ],
        series: [           
            {
                //name: '2017',
                type: 'line',
                //stack: '总量',
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
                data: chartData
            }
        ]
    };
    myChart.setOption(option);
};

//获取一级建筑数据
function getSetpOne(){
	var data = {"fDatacenterid1":"1"};
	$.ajax({
		type:"post",
		url:api+'/monitor/api/device/buildlist',
		data: JSON.stringify(data),
		contentType: 'application/json',
		timeout: 10000,
		async: true,
		beforeSend: function() {
//			$.showPreloader();
		},
		success: function(res) {
			var res = res.data;
			var list = '';
			for(var i = 0; i<res.length; i++){
				list += '<li><a href="javascript:void(0)" data-fbdid="'+res[i].fBdId+'" onclick="tapSetpOne(this)">'+res[i].fBuildname+'</a></li>'
			}			
			$('#accordion').html(list);			
		},
		error:function(){
			//alert('ajax error')
		}
	});
}

function tapSetpOne(tags){
	var fbdid = $(tags).attr('data-fbdid');	
	console.log("【点击了】 ==> "+$(tags).html() +' | 当前fbdid：'+fbdid);
	var data = {"fBdId":fbdid};
	$.ajax({
		type:"post",
		url:api+'/monitor/api/consume/analy/bdfloorlist',
		data: JSON.stringify(data),
		contentType: 'application/json',
		timeout: 10000,
		async: true,
		beforeSend: function() {
			//$.showPreloader();
		},
		success: function(res) {
			$.hidePreloader();
			var list = '';
			for(var i = 0; i<res.length; i++){
				list += '<li><a href="#">'+res[i].fDatalevelname+'</a></li>'
			}
			$(tags).after('<ul class="menu">'+list+'</ul>')
		},
		error:function(){
			//alert('ajax error')
		}
	});

}

//获取能换类型
function getEnergylist(){
	var data = {"fDatalevelid":"1"};
	$.ajax({
		type:"post",
		url:api+'/monitor/api/consume/analy/energylist ',
		data: JSON.stringify(data),
		contentType: 'application/json',
		timeout: 10000,
		async: true,
		beforeSend: function() {
		},
		success: function(res) {
			energyPicker(res);
		},
		error:function(){
			//alert('ajax error')
		}
	});	
}

//能源类型选择
function energyPicker(res){
	var val = [];
	var dispalyVal = [];
	var html = '';
	var picker = $('#picker');
	for(var i = 0; i<res.length; i++){
		val.push(res[i].fEnergyname);		
		dispalyVal.push(res[i].fEnergytype);
		html += '<li class="item-content close-popup">'+
			        '<div class="item-inner">'+
			          '<div class="item-title" data-val='+res[i].fEnergytype+' data-tag='+res[i].fEnergyitemunit+'>'+res[i].fEnergyname+'</div>'+
			        '</div>'+
			    '</li>';
	};
	$('#energyType').html(html);
	picker.click(function(){
		$.popup('.popup-energy');
	})	
	$(document).on('click', '#energyType li', function () {
 		var txt = $(this).find('.item-title').html();
 		var val = $(this).find('.item-title').attr('data-val');
 		var tag = $(this).find('.item-title').attr('data-tag');
 		fEnergytypeAll = val;
 		console.log(txt, val, tag)
 		unit = tag;
 		picker.val(txt)
    });
	
}

function curveFn(){
	var curveData = {"op":op,"fEnergytype":fEnergytype,"byType":byType,"byId":byId,"fScheme":fScheme,"theTimeS":theTimeS,"theTimeE":theTimeE,"theTime":theTime};
	console.log(curveData)
	$.ajax({
		type:"post",
		url:api+'/monitor/api/consume/analy/curve',
		data: JSON.stringify(curveData),
		contentType: 'application/json',
		timeout: 4000,
		async: true,
		beforeSend: function() {
		},
		success: function(res) {
			$('#network').hide();
//			console.log($.isEmptyObject( res ));
			
			dateX.splice(0,dateX.length);//清空数组 	
			chartData.splice(0,chartData.length);//清空数组 	
			
			//对象插入对应的数组
			$.each(res, function(key,val) {
				dateX.push(key);
				chartData.push(val);
			});			
			
			xAxisData.splice(0,xAxisData.length);//清空数组 							
			//截取数组
			$.each(dateX, function(index){
				xAxisData.push(this.substring(5,10))
			})
						
//			console.log(xAxisData)					
			//判断返回图表数据是否为空
			if($.isEmptyObject( res )){
				$('#Chart').html('<p style="text-align:center;line-height:10rem;color:#fff;">暂无数据</p>')
			}else{
				//获取数组都一个时间和最后一个时间
				firstData = dateX[0].toString();
				lastData = dateX.pop().toString();
				$('#contentDate').html(firstData+' — '+lastData)
				console.log(firstData, lastData)

				//日期对比	
				var start=new Date(getDate.replace("-", "/").replace("-", "/"));  
				var end=new Date(lastData.replace("-", "/").replace("-", "/"));  
				console.log(start,end)
				if(end<start){
					$('#nextChart').removeAttr('disabled');
				}else if(end>=start){
					$('#nextChart').attr('disabled','disabled');
				}

				Chart();
			}
			
		},
		error:function(){
			//alert('ajax error')
		},
		complete:function(xhr, status){
			console.log(xhr, status)
			if(status == 'abort'|| status== 'timeout'){
				console.log('网络错误');
				$('#network').show();				
			}
		}
	});	
}

$('#resetNetwork').click(function(){
	curveFn()
})

//上一数据
$('#prevChart').bind('click', function() {
	op = 'previous';
	console.log(firstData)
	theTime = firstData;

	curveFn()
});
//下一数据
$('#nextChart').bind('click', function() {
	op = 'next';
	//var getdata =lastData;
	theTime = lastData
	curveFn()
});

//获取时间维度
$("#timeHorizon li").click(function(){
	var checked = $(this).find('input[name=dateOption]').val();    	
	fScheme = checked;
	op = 'init';
	theTime = '';
	$('#checkbox').prop("checked",false);
	$('#diyTime').hide();
	$('#prevChart').show();
    $('#nextChart').show();
	console.log(fScheme);
})

//重置时间维度加载到图表
$("#timeReset").click(function(){
	curveFn()
})

//时间选择控件
$("#startTime").calendar({
    value: [Datefn],
    maxDate: Datefn,
    onClose: function () {
        theTimeS = $('#startTime').val();
        var time = new Date(theTimeS);
        var year =time.getFullYear();
        var month = time.getMonth()+1;
        var day = time.getDate()+1;        
        var minData = year+'/'+month+'/'+day;            
        console.log(theTimeS)
        $("#endTime").calendar({
            value: [minData],
            minDate:minData,
            maxDate: Datefn,
            onClose:function(){
            	theTimeE = $('#endTime').val();
            	console.log(theTimeE)
            }
        });        
    }
});

//自定义时间选择    
$(document).on('click', '#checkBtn', function () {
    var bol = true;
    var isCheck = $('#checkbox').is(':checked');
	
    var time = $('#diyTime');
    if (!isCheck) {
    	console.log(!isCheck)
        time.show();
        $("#timeHorizon li input").prop("checked",false); 
        fScheme = 'o';
        theTimeS = $('#startTime').val();
        theTimeE = $('#endTime').val();
        $('#prevChart').hide();
        $('#nextChart').hide();
    } else {
        time.hide();           
    }
});

//建筑选择
$(document).on('click', '.open-build', function () {	
	console.log('当前能源类型： '+fEnergytypeAll)
	var fEnergytype = fEnergytypeAll
	getTree(fEnergytype);
    $.popup('.popup-build');
});

//时间维度选择
$(document).on('click', '.open-time', function () {
    $.popup('.popup-time');
});

//树结构菜单数据
function getTree(fEnergytype){
	console.log('请求中的数据类型： '+fEnergytype)
	var data = {"fDatacenterid":"1","fEnergytype":fEnergytype};
	$.ajax({
		type:"post",
		url:api+'/monitor/api/device/buildlist',
		data: JSON.stringify(data),
		contentType: 'application/json',
		timeout: 10000,
		async: true,
		beforeSend: function() {
		},
		success: function(res) {
			var res = res.data;	
			console.log('获取数据成功');
			$("#tree").treeview({
				showcheck: false,
				theme: "bbit-tree-arrows", //bbit-tree-lines ,bbit-tree-no-lines,bbit-tree-arrows
				    data: [createNode(res)]
			});
		},
		error:function(){
			//console.log('ajax error')
		}
	})	
}

//树结构菜单
 function createNode(res) { 
	var root = {
		"id": '',
		"text": '全部',
		"value": 'all',
		"showcheck": true,
		"complete": true,
		"isexpand": true,
		"checkstate": 0,
		"hasChildren": true
	};
 	var two = [];
 	for(var i = 0; i < res.length; i++){
 		var three = [];
 		var resThree = res[i].bdFloorList; 		
 		for(var x = 0; x < resThree.length; x++ ){
 			var four = [];
 			var resFour = resThree[x].meterList;
 			for(var a = 0; a < resFour.length; a++){
 				four.push({
			 		"id": resFour[a].fCcId,
					"text": resFour[a].fCircuitname,
					"value": 'circuit',
					"showcheck": true,
					"complete": true,
					"isexpand": true,
					"checkstate": 0,
					"hasChildren": false
 				})
 			} 			
 			three.push({
		 		"id": resThree[x].fDlId,
				"text": resThree[x].fDatalevelname,
				"value": 'floor',
				"showcheck": true,
				"complete": true,
				"isexpand": false,
				"checkstate": 0,
				"hasChildren": true,
				"ChildNodes": four
 			})
 		}
 		two.push({
	 		"id": res[i].fBdId,
			"text": res[i].fBuildname,
			"value": 'build',
			"showcheck": true,
			"complete": true,
			"isexpand": true,
			"checkstate": 0,
			"hasChildren": true,
			"ChildNodes": three
 		})
 	}
    root["ChildNodes"] = two;
    return root;
}

 $("#buildReset").click(function(e){
    var s = $("#tree").getCurrentNode();
    if(s !=null){
    	$('#buildVal').val(s.text)
        console.log(s.id,s.text,s.value);
        byType = s.value;
        byId = s.id;
        
    }
    else{
        alert("NULL");
    }
 });


$('#submit').click(function(){
	curveFn()
})

//页面初始化
$(function () {    	
    getEnergylist()
	curveFn();	
    $.init();
})