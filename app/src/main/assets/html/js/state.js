var api = 'http://192.168.4.14:8080';
var data = { "fDatacenterid1": "1", "pageNum": 1 };

//建筑选择
$(document).on('click', '.open-build', function() {
	$.popup('.popup-build');
});
//获取数据
var getData = function() {
	$.ajax({
		type: "post",
		url: api + '/monitor/api/device/meterlist ',
		data: JSON.stringify(data),
		processData: true,
		async: true,
		contentType: 'application/json',
		timeout: 10000,
		success: function(res) {
			var res = res.data;
			deployData(res);
			//alert(JSON.stringify(res))
		}
	});
}
//获取采集器设备列表
function getDevice(){
	var data = {"fDatacenterid1":"1","pageNum":1};
	$.ajax({
		type: "post",
		url: api + '/monitor/api/device/list',
		data: JSON.stringify(data),
		processData: true,
		async: true,
		contentType: 'application/json',
		timeout: 10000,
		success: function(res) {
			var res = res.data;
			//alert(JSON.stringify(res))
			var device = '';			
			for(var i = 0; i < res.length; i++){
				var toggle = (res[i].fClDes == '完成')?'success':'warning';
				device +='<li class="item-content">'+
						'<div class="item-media"><i class="icon icon-collector"></i></div>'+
						'<div class="item-inner">'+
						'<div class="item-title">'+res[i].fCollectionname+'</div>'+
						'<div class="item-after"><i class="icon icon-state-'+toggle+'"></i></div>'+
						'</div>'+
						'</li>';
						}			
			$('#device-list').html(device);
		}
	});
}


//部署数据到dom
function deployData(res){
	//电表信息
	var html = '';
	for(var i = 0; i< res.length; i++){
		html += '<li>'+
					'<a href="state-detail.html?fmeid='+res[i].fMeId+'" class="item-link item-content" external>'+
						'<div class="item-media"><i class="icon icon-ammeter"></i></div>'+
						'<div class="item-inner">'+
							'<div class="item-title">'+res[i].fMetername+'</div>'+
						'</div>'+
					'</a>'+
				'</li>';
	}
	$('#ammeter-list').html(html);
};

getData();
getDevice();
