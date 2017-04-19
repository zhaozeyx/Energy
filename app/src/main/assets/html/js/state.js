var DeviceTotal;
var DataTotal;
var DeviceNum = 1;
var DataNum = 1;
var lastIndex1, lastIndex2;
//多个标签页下的无限滚动
var loading = false;
// 每次加载添加多少条目
var itemsPerLoad = 20;

$(function() {
	//建筑选择
	$(document).on('click', '.open-build', function() {
		//console.log('当前能源类型： '+fEnergytypeAll)
		//var fEnergytype = fEnergytypeAll
		getTree();
		$.popup('.popup-build');
	});

	//获取电表信息数据
	function getData(val) {
		var data = { "fDatacenterid": Datacenterid, "pageNum": DataNum, "fBdId": val };
		console.log(data)
		$.ajax({
			type: "post",
			url: api + '/monitor/api/device/meterlist ',
			data: JSON.stringify(data),
			processData: true,
			async: true,
			contentType: 'application/json',
			timeout: 10000,
			success: function(data) {
				console.log(data)				
				var res = data.data.list;
				DataTotal = data.data.total;
				if($.isEmptyObject(res)) {
					DataNum = 1;
					deployNull();
				} else {
					$('#noneData').remove();
					DataNum++;
					deployData(res);
				}
			},
			complete: function() {
				console.log("数据加载完毕")
			}
		});
	}
	//获取采集器设备列表
	function getDevice() {
		var data = { "fDatacenterid": Datacenterid, "pageNum": DeviceNum };
		$.ajax({
			type: "post",
			url: api + '/monitor/api/device/list',
			data: JSON.stringify(data),
			processData: true,
			async: true,
			contentType: 'application/json',
			timeout: 4000,
			beforeSend: function() {
				$.showIndicator();
			},
			success: function(data) {
				DeviceNum++;
				$('#network').hide();
				var res = data.data.list;
				DeviceTotal = data.data.total;

				if(DeviceTotal <= 9) {
					$('#tab1 .infinite-scroll-preloader').hide();
				}
				//alert(JSON.stringify(res))
				var device = '';
				for(var i = 0; i < res.length; i++) {
					var toggle = (res[i].fState == '1') ? 'success' : 'warning';
					device += '<li class="item-content">' +
						'<div class="item-media"><i class="icon icon-collector"></i></div>' +
						'<div class="item-inner">' +
						'<div class="item-title">' + res[i].fCollectionname + '</div>' +
						'<div class="item-after"><i class="icon icon-state-' + toggle + '"></i></div>' +
						'</div>' +
						'</li>';
				}
				$('#device-list').append(device);
				lastIndex1 = $('#device-list li').length;
				sessionStorage.setItem("lastIndex1", lastIndex1);
				$.hideIndicator();
			},
			error: function() {
				$.hideIndicator();

			},
			complete: function(xhr, status) {
				console.log(xhr, status)
				if(status == 'abort' || status == 'timeout') {
					console.log('网络错误');
					$('#network').show();
				}
			}
		});
	}
	$('#resetNetwork').click(function() {
		getData();
		getDevice();
	})

	function deployNull() {
		$('#tab2 .infinite-scroll-preloader').hide();
		var html = '<li id="noneData" style="text-align:center;background-color:#efeff4;color:#9a9a9a;margin-top: 2rem;">暂无数据</li>';
		$('#ammeter-list').html(html);
	}

	//部署数据到dom
	function deployData(res) {	
		$('#tab2 .infinite-scroll-preloader').show();
		//电表信息
		var html = '';
		for(var i = 0; i < res.length; i++) {
			html += '<li>' +
				'<a href="state-detail.html?fmeid=' + res[i].fMeId + '" class="item-link item-content" external>' +
				'<div class="item-media"><i class="icon icon-ammeter"></i></div>' +
				'<div class="item-inner">' +
				'<div class="item-title">' + res[i].fMetername + '</div>' +
				'</div>' +
				'</a>' +
				'</li>';
		}
		$('#ammeter-list').append( html );
		lastIndex2 = $('#ammeter-list li').length;
		sessionStorage.setItem("lastIndex2", lastIndex2);
	};

	//建筑物信息列表
	function buildlist(res) {
		var html = '';
		for(var i = 0; i < res.length; i++) {
			html += '<li class="item-content close-popup">' +
				'<div class="item-inner">' +
				'<div class="item-title" data-val=' + res[i].fBdId + '>' + res[i].fBuildname + '</div>' +
				'</div>' +
				'</li>';
		};
		$('#buildlist').html(html);
		$('#buildlist').click(function() {
			$.popup('.popup-energy');
		})
		
	}
	//选择建筑物，重新获取信息
	$(document).on('click', '#buildlist li', function() {
			DataNum = 1;
			var txt = $(this).find('.item-title').html();
			var val = $(this).find('.item-title').attr('data-val');
			$('#buildName').val(txt);
			getData(val)
		})
	//树结构菜单数据
	function getTree() {
		var data = { "fDatalevelid": Datacenterid };
		$.ajax({
			type: "post",
			url: api + '/monitor/api/device/buildlist',
			data: JSON.stringify(data),
			contentType: 'application/json',
			timeout: 10000,
			async: true,
			beforeSend: function() {
				$.showIndicator();
			},
			success: function(res) {
				var res = res.data;
				buildlist(res);
				$.hideIndicator();
			},
			error: function() {
				$.hideIndicator();
				console.log('ajax error')
			}
		})
	}

	// 注册'infinite'事件处理函数
	$(document).on('infinite', function() {
		// 如果正在加载，则退出
		if(loading) return;

		// 设置flag
		loading = true;

		var tabIndex = 0;
		if($(this).find('.infinite-scroll.active').attr('id') == "tab1") {
			tabIndex = 0;
		}
		if($(this).find('.infinite-scroll.active').attr('id') == "tab2") {
			tabIndex = 1;
		}
		//lastIndex = $('.list-container').eq(tabIndex).find('li').length;

		switch(tabIndex) {
			case 0:

				// 模拟1s的加载过程
				setTimeout(function() {
					// 重置加载flag
					loading = false;
					var lastIndex1 = sessionStorage.getItem("lastIndex1");

					if(parseInt(lastIndex1) >= parseInt(DeviceTotal)) {
						// 加载完毕，则注销无限加载事件，以防不必要的加载
						//$.detachInfiniteScroll($('.infinite-scroll').eq(tabIndex));
						// 删除加载提示符
						$('#tab1 .infinite-scroll-preloader').eq(tabIndex).hide();
						//$('#tab1 .infinite-scroll-preloader .preloader').remove();
						//$('#tab1 .infinite-scroll-preloader').append('<p style="color:#8e8e8e;">暂无更多</p>');
						return;
					}

					// 添加新条目
					getDevice();
					// 更新最后加载的序号
					//lastIndex = $('#warn-list li').length;
					//容器发生改变,如果是js滚动，需要刷新滚动
					$.refreshScroller();
				}, 1000);
				break;
			case 1:

//				$.attachInfiniteScroll($('.infinite-scroll').eq(tabIndex));
				setTimeout(function() {
					// 重置加载flag
					loading = false;
					var lastIndex2 = sessionStorage.getItem("lastIndex2");
					if(parseInt(lastIndex2) >= parseInt(DataTotal)) {
						// 加载完毕，则注销无限加载事件，以防不必要的加载
						//$.detachInfiniteScroll($('.infinite-scroll').eq(tabIndex));
						// 删除加载提示符
						$('#tab2 .infinite-scroll-preloader').eq(tabIndex).hide();
						//$('#tab2 .infinite-scroll-preloader .preloader').remove();
						//$('#tab2 .infinite-scroll-preloader').append('<p style="color:#8e8e8e;">暂无更多</p>');
						return;
					}

					// 添加新条目
					getData();
					// 更新最后加载的序号
					//lastIndex = $('#warn-list li').length;
					//容器发生改变,如果是js滚动，需要刷新滚动
					$.refreshScroller();
				}, 500);
				break;
		}

	});

	$.init();
	getData();
	getDevice();
})