
function addFlowNode(flowId,nodeId,containerId){
	$.diaLog({
		title:   nodeId==undefined?"添加流程节点":"修改流程节点",
		width:"80%",
		type:"confrim",
		height:"400px",
		src:"/page/flow/nodePage?flowId="+flowId+(nodeId==undefined?"":"&nodeId="+nodeId)+(containerId==undefined?"":"&containerId="+containerId),
		func:function(type,content){
			var node=this.callFrame("getNode")
			if(type=="sure"){
				return updatOrAddPostRequest({url:nodeId==undefined?'/flow/node/add':'/flow/node/update',data:node,loadding:true})
			}
		}
	})
	
}

function initEvent(){
	var col=[
		{title:"名称",name:'name'},
		{title:"group", name:"groupId"}
	]
	
	$("#btn_flow_add").on("click",function(){
		windowAction({
			title:"新建流程配置",
			table:"#flow-table",
			isPost:false,
			columns:col,
			actionUrl:'/flow/info/add',
		})
	})
	$("#btn_flow_update").on("click",function(){
		var row=getSelctRow("#flow-table");
		if(row){
			var cols=getColums(col,row)
			windowAction({
				title:"修改流程配置",
				table:"#flow-table",
				isPost:false,
				columns:cols,
				actionUrl:'/flow/info/update',
			})
		}
	})
	
	$.contextMenu({
		 selector: "#flow-table>tbody>tr[class !=detail-view]",
		 // define the elements of the menu
		 items: {
		 	updateFlow: {name: "更新流程", callback: function(key, opt){ $("#btn_flow_update").click()}},
		 	updateFlow: {name: "绘图", callback: function(key, opt){ 
		 		var row=getSelctRow("#flow-table");
		 		if(row){
		 			//window.location.href="/page/flow/canvas"
		 			//window.open(API.config.baseUrl+"/page/flow/canvas");
			 		$.cartPage({parent:"#page-panel",src:'/page/flow/canvas?flowId='+row.id})
		 		}
		 	}},
		 	addNode: {name: "新增节点", callback: function(key, opt){
		 			var row=getSelctRow("#flow-table");
		 			if(row){
		 				addFlowNode(row.id)
		 			}
		 		},
		 	}
		 }
	});

}

function initMenuContext(pid){
	$.contextMenu({
		 selector: pid+">tbody>tr[class !=detail-view]",
		 items: {
		 	updateNode: {name: "修改节点", callback: function(key, opt){
		 		//console.log(pid)
		 		var row=getSelctRow(pid);
	 			if(row){
	 				addFlowNode(row.flowId,row.id,row.containerId)
	 			}
		 	}},
		 },
		 events:{
			 preShow:function(e,menu){
				 //console.log(menu)
				 //menu.data.items.addNode.disabled=false
			 }
		 }
	});
}

function getContent(id){
	return '<div id="node-page-contain_'+id+'" class="panel-body">'+
					'<ul class="nav nav-tabs">'+
						'<li class="active"><a href="#f_node-data_'+id+'" data-toggle="tab">数据</a> </li>'+
						'<li ><a href="#f_node-attr_'+id+'" data-toggle="tab">属性</a></li>'+
						'<li><a href="#f_node-link_'+id+'" data-toggle="tab">链接</a></li>'+
						'<li><a href="#f_node-event_'+id+'" data-toggle="tab">事件</a></li>'+
					'</ul>'+
					'<div class="tab-content">'+
						'<div class="tab-pane fade in active" id="f_node-data_'+id+'">'+
							'<input type="hidden" name="flowId"/>'+
							'<input type="hidden" name="nodeId"/>'+
							'<div class="form-group clearfix"><lable class="col-sm-2 control-label">key:</lable><div class="col-sm-8 form-item" name="key"></div></div>'+
							'<div class="form-group clearfix"><lable class="col-sm-2 control-label">name:</lable><div class="col-sm-8 form-item" name="name"></div></div>'+
							'<div class="form-group clearfix"><lable class="col-sm-2 control-label">type:</lable><div class="col-sm-8 form-item" name="type"></div></div>'+
						'</div>'+
						'<div class="tab-pane fade " id="f_node-attr_'+id+'">'+
							'<div id="f_nodeattr-content pane-content">'+
								'<div class="attr-title"><div class="col-sm-4">key</div><div class="col-sm-4">name</div><div class="col-sm-4">value</div></div>'+
								'<div class="input-con-demo hide">'+
									'<div class="col-sm-4" name="key"></div>'+
									'<div class="col-sm-4" name="name"></div>'+
									'<div class="col-sm-4" name="value"></div>'+
								'</div>'+
								'<div class="content-list"></div>'+
							'</div>'+
						'</div>'+
						'<div class="tab-pane fade " id="f_node-link_'+id+'">'+
							'<div id="f_nodeattr-content pane-content">'+
								'<div class="attr-title"><div class="col-sm-4">key</div><div class="col-sm-4">condition</div><div class="col-sm-4">downnode</div></div>'+
								'<div class="input-con-demo hide">'+
									'<div class="col-sm-4" name="key"></div>'+
									'<div class="col-sm-4" name="condition"></div>'+
									'<div class="col-sm-4" name="downNodeId"></div>'+
								'</div>'+
								'<div class="content-list"></div>'+
							'</div>'+
						'</div>'+
						'<div class="tab-pane fade " id="f_node-event_'+id+'">'+
							'<div id="f_nodeattr-content pane-content">'+
								'<div class="attr-title"><div class="col-sm-2">key</div><div class="col-sm-2">name</div><div class="col-sm-2">type</div><div class="col-sm-2">eventType</div><div class="col-sm-4">content</div></div>'+
								'<div class="input-con-demo hide">'+
									'<div class="col-sm-2" name="key"></div>'+
									'<div class="col-sm-2" name="name"></div>'+
									'<div class="col-sm-2" name="type"></div>'+
									'<div class="col-sm-2" name="eventType"></div>'+
									'<div class="col-sm-4" name="content"></div>'+
								'</div>'+
								'<div class="content-list"></div>'+
							'</div>'+
						'</div>'+
					'</div>'+
				'</div>';
}

function initNodeContent($detail,node){
	console.log(node)
	var id=node.id;
	$detail.html(getContent(id))
	$detail.find("#f_node-data_"+id).find("input").each(function(){
		$(this).val(node[$(this).attr("name")])
	})
	$detail.find("#f_node-data_"+id).find("div.form-item").each(function(){
		$(this).html(node[$(this).attr("name")])
	})
	initNodeRelativeCon($detail.find("#f_node-attr_"+id),node.attrs)
	initNodeRelativeCon($detail.find("#f_node-link_"+id),node.links)
	initNodeRelativeCon($detail.find("#f_node-event_"+id),node.events)
}

function initNodeRelativeCon(ele,list){
	if(list.length>0){
		$.each(list,function(i,d){
			var li=$(ele).find('.input-con-demo').clone()
			li.removeClass("hide input-con-demo").addClass("input-con")
			li.find("div").each(function(j,it){
				var val=d[$(this).attr("name")]
				$(this).html(val?val:'')
			})
			$(ele).find('.content-list').append(li)
		})
	}
}

function extendTable(index, row, $detail){
	var cur_table = $detail.html('<table></table>').find('table');
	cur_table.attr("id","flow-node-table-"+row.id)
	$(cur_table).jTable({
		url:"/flow/node/query?flowId="+  row.id,
		uniqueId:'id',
		method:"post",
		detailView: true,//父子表
		pagination:false,
		responseHandler:function(res){
			if(res.success ){
				return {list:res.data,total:res.data.length};
			}else{
				$.alert({type:"warn",content:"请求数据错误,错误编码："+res.code+" 信息："+res.message})
			}
		},
		columns :[
			{field: 'radio', radio: true}, 
			{
				field: 'index', title: '序号',align: 'center', valign: 'middle', sortable: false,
				formatter: function (value,row,index){return index+1; }
			},
			{ field : "id", title : "id"},
			{ field : "type", "value":"node", visible: false},
			{ field : "key", title : "key"},
			{ field : "name", title : "名称"},
			{ field : "type1", title : "type",formatter:function(value,row){return row.type}},
			{ field : "status", title : "状态",formatter:function(value,row){return value==0?'正常':'作废'}},
			{ field : "version", title : "版本号", visible: false},
			{ field : "ts", title : "时间",formatter:function(value,row){return value !=null?new Date(value).toLocaleString():'-'}},
		],
		onExpandRow: function (index, row, $detail) {
			if(row.type!='container'){
				initNodeContent($detail,row)
			}else{
				extendTableContainer( row, $detail)
			}
		}
	})
	initMenuContext("#"+cur_table.attr("id"))
}
function extendTableContainer(row, $detail){
	var cur_table = $detail.html('<table></table>').find('table');
	cur_table.attr("id","flow-node-table-"+row.id)
	$(cur_table).jTable({
		data:row.container,
		uniqueId:'id',
		sidePagination:"client",
		detailView: true,//父子表
		pagination:false,
		columns :[
			{field: 'radio', radio: true}, 
			{
				field: 'index', title: '序号',align: 'center', valign: 'middle', sortable: false,
				formatter: function (value,row,index){return index+1; }
			},
			{ field : "id", title : "id"},
			{ field : "type", "value":"node", visible: false},
			{ field : "key", title : "key"},
			{ field : "name", title : "名称"},
			{ field : "type1", title : "type",formatter:function(value,row){return row.type}},
			{ field : "status", title : "状态",formatter:function(value,row){return value==0?'正常':'作废'}},
			{ field : "version", title : "版本号", visible: false},
			{ field : "ts", title : "时间",formatter:function(value,row){return value !=null?new Date(value).toLocaleString():'-'}},
			],
			onExpandRow: function (index, row, $detail) {
				if(row.type!='container'){
					initNodeContent($detail,row)
				}else{
					extendTableContainer( row.container, $detail)
				}
			}
	})
	initMenuContext("#"+cur_table.attr("id"))
}