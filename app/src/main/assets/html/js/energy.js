var borderColor = 'rgba(255,255,255,.2)';
var op = 'init',	//previous上一页  next下一页  init最近七天
    fEnergytype = '01000',	//能源类型
    byType = 'AD0'+Datacenterid,	//all build  floor  circuit =>全部  建筑物  楼层 回路
    byId = Datacenterid,	//byId =>  建筑物ID fBdId   fDatalevelid  回路ID fCcId
    fScheme = 'd',	//d : 天  m：月  y：年  o：自定义
    theTimeS = '',	//当fScheme=o,theTimeS 是开始时间
    theTimeE = '',	//当fScheme=o,theTimeE是结束时间
    theTime = '';
var dateX = [],
    chartData = [],
    xAxisData = [];
var firstData = '',
    lastData = '';
var unit = '千瓦时';
var energyType = '电';
var fEnergytypeAll = '01000';

var mintime = new Date();
var year = mintime.getFullYear();
var month = mintime.getMonth() + 1;
var day = mintime.getDate() + 1;
var minDate = '';

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
        legend: {
            show: true,
            data: xAxisData
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
                name: energyType,
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

//判断数组是否为空
function isEmpty(value) {
    return (Array.isArray(value) && value.length === 0) || (Object.prototype.isPrototypeOf(value) && Object.keys(value).length === 0);
}

//获取能换类型
function getEnergylist() {
    var data = {"fDatacenterid": Datacenterid};
    console.log(data);
    $.ajax({
        type: "post",
        url: api + '/monitor/api/consume/analy/energylist ',
        data: JSON.stringify(data),
        contentType: 'application/json',
        timeout: 10000,
        async: true,
        beforeSend: function () {
        },
        success: function (res) {
            energyPicker(res);
        },
        error: function () {
            //alert('ajax error')
        }
    });
}

//能源类型选择
function energyPicker(res) {
    var val = [];
    var dispalyVal = [];
    var html = '';
    var picker = $('#picker');
    for (var i = 0; i < res.length; i++) {
        val.push(res[i].fEnergyname);
        dispalyVal.push(res[i].fEnergytype);
        html += '<li class="item-content close-popup">' +
            '<div class="item-inner">' +
            '<div class="item-title" data-val=' + res[i].fEnergytype + ' data-tag=' + res[i].fEnergyitemunit + '>' + res[i].fEnergyname + '</div>' +
            '</div>' +
            '</li>';
    }
    ;

    if( isEmpty(res) == true){
        $('#energyType').parent(".list-block").html("<p style='text-align:center'><span class='icon icon-browser'></span> 暂无更多</p>");
    }else{
        $('#energyType').html(html);
    }
    picker.click(function () {
        $.popup('.popup-energy');
    })
    $(document).on('click', '#energyType li', function () {
        var txt = $(this).find('.item-title').html();
        var val = $(this).find('.item-title').attr('data-val');
        var tag = $(this).find('.item-title').attr('data-tag');
        fEnergytypeAll = val;
        console.log(txt, val, tag)
        unit = tag;
        energyType = txt;
        picker.val(txt)
    });

}

function curveFn() {
    var curveData = {
        "op": op,
        "fEnergytype": fEnergytypeAll,
        "byType": byType,
        "byId": byId,
        "fScheme": fScheme,
        "theTimeS": theTimeS,
        "theTimeE": theTimeE,
        "theTime": theTime
    };
    console.log(curveData)
    $.ajax({
        type: "post",
        url: api + '/monitor/api/consume/analy/curve',
        data: JSON.stringify(curveData),
        contentType: 'application/json',
        timeout: 4000,
        async: true,
        beforeSend: function () {
            $.showIndicator();
        },
        success: function (res) {
            console.log(res)
            $('#network').hide();
            $.hideIndicator();
//			console.log($.isEmptyObject( res ));

            dateX.splice(0, dateX.length);//清空数组
            chartData.splice(0, chartData.length);//清空数组

            //对象插入对应的数组
            $.each(res, function (key, val) {
                var Nval =  Number(val).toFixed(2);
                var valFn = (Nval == 0.00) ? Nval = '':Nval;
                dateX.push(key);
                chartData.push(valFn);
            });

            xAxisData.splice(0, xAxisData.length);//清空数组
            if (fScheme == 'y') {
                $.each(dateX, function (index) {
                    xAxisData.push(this.substring(0, 4))
                })
            } else {
                //截取数组
                $.each(dateX, function (index) {
                    xAxisData.push(this.substring(5, 10))
                })
            }
            //判断返回图表数据是否为空
            if ($.isEmptyObject(res)) {
                $('#Chart').html('<p style="text-align:center;line-height:10rem;color:#fff;">暂无数据</p>')
            } else {
                //获取数组都一个时间和最后一个时间
                firstData = dateX[0].toString();
                lastData = dateX.pop().toString();
                $('#contentDate').html(firstData + ' — ' + lastData)

                //日期对比
                var start = new Date(getDate.replace("-", "/").replace("-", "/"));
                var end = new Date(lastData.replace("-", "/").replace("-", "/"));
                if (end < start) {
                    $('#nextChart').removeAttr('disabled');
                } else if (end >= start) {
                    $('#nextChart').attr('disabled', 'disabled');
                }

                Chart();
            }

        },
        error: function () {
            $.hideIndicator();
            //alert('ajax error')
        },
        complete: function (xhr, status) {
            $.hideIndicator();
            if (status == 'abort' || status == 'timeout') {
                console.log('网络错误');
                $('#network').show();
            }
        }
    });
}

$('#resetNetwork').click(function () {
    curveFn()
})

//上一数据
$('#prevChart').bind('click', function () {
    op = 'previous';
    console.log(firstData)
    theTime = firstData;

    curveFn()
});
//下一数据
$('#nextChart').bind('click', function () {
    op = 'next';
    //var getdata =lastData;
    theTime = lastData
    curveFn()
});

//获取时间维度
$("#timeHorizon li").click(function () {
    var checked = $(this).find('input[name=dateOption]').val();
    fScheme = checked;
    op = 'init';
    theTime = '';
    $('#checkbox').prop("checked", false);
    $('#diyTime').hide();
    $('#prevChart').show();
    $('#nextChart').show();
    $('#startTime').val('');
    $('#endTime').val('');
    console.log(fScheme);
})

//重置时间维度加载到图表
$("#timeReset").click(function () {
    var timeS = $('#startTime').val();
    var timeE = $('#endTime').val();
    var start = new Date(timeS.replace("-", "/").replace("-", "/"));
    var end = new Date(timeE.replace("-", "/").replace("-", "/"));
//	console.log(start, end);
//	console.log(timeS, timeE);
    if (timeS == '' && timeE == '') {
        curveFn()
    } else if (timeS !== '' && timeE !== '') {
        curveFn()
        $('#contentDate').html(timeS + ' — ' + timeE)
    }

    if (start > end) {
        alert('开始时间不能小于结束时间');
        return false;
    }


})

//时间选择控件
$("#startTime").calendar({
    value: [Datefn],
    maxDate: Datefn,
    onOpen: function () {
        //alert(typeof Datefn);
        console.log(typeof Datefn)
    },
    onClose: function () {
        theTimeS = $('#startTime').val();
        mintime = new Date(theTimeS);
        minyear = mintime.getFullYear();
        minmonth = mintime.getMonth() + 1;
        minday = mintime.getDate() + 1;
        minDate = minyear + '/' + minmonth + '/' + minday;
        console.log(theTimeS);
    }
});

$("#endTime").calendar({
    value: [Datefn],
    maxDate: Datefn,
    onClose: function () {
        theTimeE = $('#endTime').val();
        console.log(theTimeE)
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
        $("#timeHorizon li input").prop("checked", false);
        fScheme = 'o';
        theTimeS = $('#startTime').val();
        theTimeE = $('#endTime').val();
        $('#prevChart').hide();
        $('#nextChart').hide();
    } else {
        time.hide();
        $('#startTime').val('');
        $('#endTime').val('');
    }
});

//建筑选择
$(document).on('click', '.open-build', function () {
    console.log('当前能源类型： ' + fEnergytypeAll)
    var fEnergytype = fEnergytypeAll
    getTree(fEnergytype);
    $.popup('.popup-build');
});

//时间维度选择
$(document).on('click', '.open-time', function () {
    $.popup('.popup-time');
});

function zTreeOnClick(event, treeId, treeNode) {
    console.log(treeNode.id + ", " + treeNode.name);
    byType = treeNode.id;
    $('#buildVal').val(treeNode.name)

};

//树结构菜单数据
function getTree(fEnergytype) {
    console.log('请求中的数据类型： ' + fEnergytype)
    var data = {"fDatacenterid": Datacenterid, "fEnergytype": fEnergytype};
    $.ajax({
        type: "post",
        url: api + '/monitor/api/device/buildlist',
        data: JSON.stringify(data),
        contentType: 'application/json',
        timeout: 10000,
        async: true,
        beforeSend: function () {
            $.showIndicator();
        },
        success: function (res) {
            var res = res.data;
            console.log('获取数据成功');
            $('#buildVal').val('');
            var setting = {
                view: {
                    dblClickExpand: false,//双击节点时，是否自动展开父节点的标识
                    showLine: false,//是否显示节点之间的连线
                    selectedMulti: false //设置是否允许同时选中多个节点
                },
                key: {
                    name: "name",
                    title:'name'
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "pid",
                        // rootPId: "0"
                    }
                },
                callback: {
                    onClick: zTreeOnClick
                }
            };
            jQuery.fn.zTree.init(jQuery("#treeDemo"), setting, res);
            var treeObj = jQuery.fn.zTree.getZTreeObj("treeDemo");

            $.hideIndicator();
        },
        error: function () {
            $.hideIndicator();
            //console.log('ajax error')
        }
    })
}



//树结构菜单
function createNode(res) {
    var root = {
        "id": Datacenterid,
        "text": '全部',
        "value": 'all',
        "showcheck": true,
        "complete": true,
        "isexpand": true,
        "checkstate": 0,
        "hasChildren": true
    };

    var currentAD = 'AD'+Datacenterid;
    var two = [],
        three = [],
        four = [];
    var currentCB = [],     //当前建筑
        currentDF = '';     //当前楼层
    var currentCB1 = [];
    for(var i = 0; i <res.length; i++){
        var AD = res[i].PID;    //数据中心
        var CB = res[i].PID;    //建筑物
        var FC = res[i].PID;    //支路
        var DF = res[i].PID;    //楼层
        //获取建筑物列表
        if(AD == currentAD){
            currentCB1.push(i);
            two.push({
                "id": res[i].ID,
                "text": res[i].NAME,
                "value": 'build',
                "showcheck": true,
                "complete": true,
                "isexpand": true,
                "checkstate": 0,
                "hasChildren": true,
              "ChildNos": three
            });
        }
    }
    //获取二级列表
    $.each(currentCB1,function(key,value){
        currentCB.push(res[value].ID)
    })

    for(var i = 0; i <res.length; i++){
        $.each(currentCB1,function(key,value){
            if(res[i].PID == res[value].ID){
                three.push({
                    "id": res[i].ID,
                    "text": res[i].NAME,
                    "value": 'build',
                    "showcheck": true,
                    "complete": true,
                    "isexpand": true,
                    "checkstate": 0,
                    "hasChildren": true,
                    // "ChildNos": three
                });
            }

        })
    }


    console.log(currentCB1);
    console.log(currentCB)
    root["ChildNodes"] = two;
    return root;
}


$("#buildReset").click(function (e) {
    if($('#buildVal').val() == ''){
        byType = 'AD0'+Datacenterid;
        $('#buildVal').val('全部')
        
    }
});
$("#buildCancel").click(function (e) {
    if($('#buildVal').val() == ''){
        byType = 'AD0'+Datacenterid;
        $('#buildVal').val('全部')

    }
});


$('#submit').click(function () {
    curveFn()
})

//页面初始化
$(function () {
    getEnergylist()
    curveFn();
    $.init();
})