/**加载链路信息*/
function loadTraceLink(parent,data){
	parent.html("");
	var time_s=data[0].time
	var time_e=data[0].time+data[0].cost
	$.each(data,function(i,trace){
		var t_s=trace.time,t_e=trace.cost
		var link_con=$("<div class='tracelink'></div>")
		link_con.append("<p class='trace-group-url'>"+trace.url+"</p>")
		if(trace.traces.length>0){
			link_con.append("<span class='trace-tab glyphicon glyphicon-chevron-down'></span>")
			link_con.append(loadTraces(trace.traces))
		}
		if(t_s < time_s){
			time_s=t_s;
		}
		if(t_e>time_e){
			time_e=t_e;
		}
		parent.append(link_con)
		parent.data("data","trace")
		parent.data("time_s",time_s)
		parent.data("time_e",time_e)
		loadTraceCanvas(parent);
	})
}

function loadTraces(traces){
	var traceslink=$("<div class='trace-group-links'></div>")
	$.each(traces,function(j,t){
		var url_name= t.url.substr(t.url.lastIndexOf("/"));
		var link=$("<div class='trace-group-link trace-group-down'></div>")
		link.append("<span class='pull-left trace-group-link-url' data-toggle='tooltip' title='"+t.url+"'>"+url_name+"</span>")
		link.append("<span class='pull-left trace-group-link-item'>"+t.status+"</span>")
		link.append("<span class='pull-left trace-group-link-item'>"+t.type+"</span>")
		link.append("<span class='pull-left trace-group-link-item'>"+t.cost+"</span>")
		link.append(loadProgress(t))
		link.data("trace",t)
		traceslink.append(link)
	})
	return traceslink;
}

function loadProgress(trace){
	return '<div class="progress pull-left"> <div class="progress-bar progress-bar-info trace-show-canv" start="'+trace.time+'" len="'+trace.cost+'" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" ></div></div>'
}

function showTraceDetail(ele,trace){
	var panel=ele.find(".trace-detail-panel");
	console.log(panel)
	if(panel.length<1){
		var contain=$("<div class='trace-show-panel' style='float: left; width: calc(90% - 50px);'><span class='close' aria-hidden='true'>&times;</span></div>")
		panel=$("<div class='trace-detail-panel'></div>");
		contain.append(panel)
		contain.find('.close').on("click",function(){
			$(this).parent().remove();
			ele.find(".trace-group-links").css('width','')
			ele.find(".progress,.trace-group-link-item").fadeIn()
		});
		ele.append(contain)
	}
	panel.html("")
	showTracePanel(panel,trace)
	console.log(trace)
}

function showTracePanel(panel,trace){
	panel.append('<ul id="trace-tab" class="nav nav-tabs">'+
					'<li class="active"><a href="#trace-'+trace.traceId+'-request" data-toggle="tab">request</a></li>'+
					'<li><a href="#trace-'+trace.traceId+'-response" data-toggle="tab">response</a></li>'+
					'<li><a href="#trace-'+trace.traceId+'" data-toggle="tab">trace</a></li>'+
				'</ul>')
	var contain=$('<div id="tab-'+trace.traceId+'" class="tab-content"></div>')
	contain.append(addTraceRequestPanel(trace))
	contain.append(addTraceResponsePanel(trace))
	contain.append(addTraceTracePanel(trace))
	panel.append(contain)
}

function addTraceRequestPanel(trace){
	var id_def='trace-'+trace.traceId+'-request';
	var panel_id=id_def+'-panel'
	var panel=$('<div class="tab-pane fade in active" id="'+id_def+'"><div class="panel-group" id="'+panel_id+'"></div></div>')
	panel.children('.panel-group').append('<div class="panel panel-default">'+
			        '<div class="panel-heading">'+
			            '<h4 class="panel-title">'+
			               ' <a data-toggle="collapse" data-parent="#'+panel_id+'" href="#'+id_def+'-gen-body">Greneral</a>'+
			            '</h4>'+
			        '</div>'+
			        '<div id="'+id_def+'-gen-body" class="panel-collapse collapse">'+
			            '<div class="panel-body">'+
			            	'<div class="trace-detail-item"><span class="trace-detail-item-title">Request Url:</span><span class="trace-detail-item-txt">'+trace.url+'</span></div>'+
			            	'<div class="trace-detail-item"><span class="trace-detail-item-title">Request Method:</span><span class="trace-detail-item-txt">'+trace.type+'</span></div>'+
			            	'<div class="trace-detail-item"><span class="trace-detail-item-title">Status Code:</span><span class="trace-detail-item-txt">'+trace.status+'</span></div>'+
			            '</div>'+
			       ' </div>'+
			    '</div>')
			    
	var headers=JSON.parse(trace.headers);
	var headerDiv='';
	for(var key in headers){
		headerDiv+='<div class="trace-detail-item"><span class="trace-detail-item-title">'+key+':</span><span class="trace-detail-item-txt">'+headers[key]+'</span></div>'
	}
	panel.children('.panel-group').append('<div class="panel panel-default">'+
    				'<div class="panel-heading">'+
		    			'<h4 class="panel-title">'+
			    		' <a data-toggle="collapse" data-parent="#'+panel_id+'" href="#'+id_def+'-REQ-Headers-body">Request Headers</a>'+
			    		'</h4>'+
			    		'</div>'+
			    		'<div id="'+id_def+'-REQ-Headers-body" class="panel-collapse collapse">'+
			    			'<div class="panel-body">'+headerDiv+'</div>'+
			    		' </div>'+
			    '</div>')
   panel.children('.panel-group').append('<div class="panel panel-default">'+
		    		'<div class="panel-heading">'+
			    		'<h4 class="panel-title">'+
			    		' <a data-toggle="collapse" data-parent="#'+panel_id+'" href="#'+id_def+'-REQ-body">Request Body</a>'+
			    		'</h4>'+
			    		'</div>'+
			    		'<div id="'+id_def+'-REQ-body" class="panel-collapse collapse">'+
			    			'<div class="panel-body">'+trace.requestBody+'</div>'+
			    		' </div>'+
			    '</div>')
	return panel;
}

function addTraceResponsePanel(trace){
	var id_def='trace-'+trace.traceId+'-response';
	var panel_id=id_def+'-panel'
	var panel=$('<div class="tab-pane fade in" id="'+id_def+'">'+trace.responseBody+'</div>')
	return panel;
}
function addTraceTracePanel(trace){
	var id_def='trace-'+trace.traceId+'-trace';
	var panel_id=id_def+'-panel'
	var panel=$('<div class="tab-pane fade in" id="'+id_def+'">'+trace.cost+'</div>')
	return panel;
}

function loadTraceCanvas(parent){
	var time_s=parent.data("time_s")
	var time_e=parent.data("time_e")
	var len=time_e-time_s
	$(".trace-show-canv").each(function(i,c){
		var left=($(c).attr('start')-time_s)/len;
		var width=$(c).attr('len')/len;
		$(c).css({
			'width':width*100+"%",
			'margin-left':left*100+"%"
		})
	})
}