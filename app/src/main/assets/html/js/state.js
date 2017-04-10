
//建筑选择
$(document).on('click', '.open-build', function () {	
	//console.log('当前能源类型： '+fEnergytypeAll)
	//var fEnergytype = fEnergytypeAll
	getTree();
    $.popup('.popup-build');
});

//获取电表信息数据
var getData = function(val) {
	var data = { "fDatacenterid1": "1", "pageNum":"1","fBdId":val };
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
			if($.isEmptyObject( res )){
				deployNull();
			}else{
				deployData(res);
			}			
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
		timeout: 4000,
		success: function(res) {
			$('#network').hide();
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
		},
		error:function(){
			
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
	getData();
	getDevice();
})

function deployNull(){
	var html = '<p style="text-align:center;background-color:#efeff4;color:#9a9a9a;margin-top: 2rem;">暂无数据</p>';
	$('#ammeter-list').html(html);
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
	$('#ammeter-list').html('<ul>'+html+'</ul>');
};

//建筑物信息列表
function buildlist(res){
	var html = '';
	for(var i = 0; i<res.length; i++){
		html += '<li class="item-content close-popup">'+
			        '<div class="item-inner">'+
			          '<div class="item-title" data-val='+res[i].fBdId+'>'+res[i].fBuildname+'</div>'+
			        '</div>'+
			    '</li>';
	};
	$('#buildlist').html(html);
	$('#buildlist').click(function(){
		$.popup('.popup-energy');
	})
	$(document).on('click','#buildlist li',function(){
		var txt = $(this).find('.item-title').html();
 		var val = $(this).find('.item-title').attr('data-val');
 		console.log(txt, val)
 		$('#buildName').val(txt);
 		getData(val)
	})
}
//树结构菜单数据
function getTree(){
	var data = {"fDatalevelid":"1"};
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
			console.log(res)
			buildlist(res);
		},
		error:function(){
			console.log('ajax error')
		}
	})	
}

getData();
getDevice();
