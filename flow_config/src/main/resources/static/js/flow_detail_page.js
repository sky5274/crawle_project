$(function(){
	var taskId=$("#taskId").val().trim()
	if(taskId==''){
		$.diaLog({con:"流程任务id为空"})
	}else{
		initLoadTask(taskId)
		initEvent()
	}
})

function initLoadTask(taskId,flag){
	$.doJsonAjax({
		url:"/task/query/info?taskId="+taskId,
		loadding:true,
		success:function(res){
			initTaskData(res.data,flag)
		}
	})
	
}

function removeTr(tr){
	$(tr).parent().remove();
}

function initEvent(){
	$(document).on("click","#param-add-icon",function(){
		$(this).parents('.task-node-param').find('.task-node-param-content').append('<div class="node-param-item"><input type="text" name="key" placeholder="key"/>:<input type="text" name="value" placeholder="value"/><i class="glyphicon glyphicon-minus icon-minus" onclick="removeTr(this)"></i></div>')
	})
	$(document).on("click",".node-select",function(){
		var node=JSON.parse($(this).attr("value"))
		var data=getNodeData();
		if(data.name){
			node.name=data.name
		}
		node.param=$.extend(node.param,data.param,true)
		data=$.extend(data,node,true)
		addTaskNodeDetail(data,this)
	})
	$(document).on("click",".node-submit",function(){
		var data=getNodeData(true);
		addTaskNodeDetail(data,this,true)
	})
	$("#task-call-back").on('click',function(){
		var taskid=$("#taskId").val()
		$.doPostJsonAjax({
		url:'/task/callBack',
		data:{taskId:taskid},
		loadding:true,
		loadtarget:"#task-flow-link",
		success:function(res){
			$.diaLog({con:"任务回滚成功"})
			initLoadTask(taskid,false)
		}
	})
	})
	
}

function addTaskNodeDetail(node,btn,flag){
	node.taskId=$("#taskId").val()
	var task={nowNode:node}
	if(flag){
		var t={
				id:node.taskId,
				message:node.message,
				params:node.param
		}
		task={task:t,nowNode:node}
	}
	
	$.doPostJsonAjax({
		url:'/task/addNode',
		data:task,
		async:false,
		btn:$(btn),
		loadding:true,
		loadtarget:"#task-flow-link",
		success:function(res){
			$.diaLog({con:"任务更新成功"})
			initLoadTask(node.taskId,false)
		}
	})
}

function getNodeData(flag){
	var node={}
	$('.task-node-detail input').each(function(i,t){
		var val=$(this).val();
		if(val!=''){
			node[$(this).attr("name")]=val;
		}
	})
	node.param={}
	$(".task-node-param-content .node-param-item").each(function(i,it){
		var key=$(this).find("input[name=key]").val()
		var value=$(this).find("input[name=value]").val()
		if(key!='' && value!=''){
			node.param[key]=value;
		}
	})
	$(".task-node-param-tool input").each(function(i,it){
		node.param[$(this).attr("name")]=$(this).prop("checked")
	})
	if(flag){
		$("#node-param-panel input,select").each(function(i,it){
			var val=$(this).val();
			if(val!=''){
				node.param[$(this).attr("name")]=val;
			}
		})
	}
	return node;
}

function initTaskData(task,flag){
	$(".task-item").each(function(i,e){
		var val=task[$(this).attr("param")];
		if( val !=''){
			$(this).html(val)
		}
	})
	if(flag==undefined || flag==true){
		loadFlowCanvas(task.flowId,function(){
			loadFlowTimeLink(task)
		})
	}else{
		loadFlowTimeLink(task)
	}
	
}

/**加载任务流程时间轴*/
function loadFlowTimeLink(task){
	var nodes=task.details;
	if(task.node){
		var n=nodes[nodes.length-1]
		n.nodeData=task.node
		nodes[nodes.length-1]=n
	}
	$("#task-flow-link").flow({
		//align:"mid",
		extend:{
			convert:function(item,index){
				return {time:item.ts,title:item.name?item.name:item.nodeName,data:item}
			},
			setContent:function(data){
				var tips=getContentTips(data);
				var node_con=''
				if(data.nodeData){
					node_con=transformNodeData(data.nodeData)
				}
				return '<div class="task-node-content" '+tips+'>'+
					(data.content?'<div class="node-con">'+data.content+'</div>':'')+
					(data.desc?'<div class="node-desc">'+data.desc+'</div>':'')+
				'</div>'+node_con;
			},
			setActive:function(data,index){
				return !(task.status!=3 && index==task.details.length-1)
			}
		},
		event:{
			"mouseenter":function(){
				$(this).addClass("node-hover")
				var node=$(this).data("node")
				if(node.data){
					showNodeInPanel(node.data)
				}
			},
			mouseleave:function(){
				$(this).removeClass("node-hover")
				$(".node_select").removeClass("node_select")
				$(".link_select").removeClass("link_select")
				$(".link_select_callback").removeClass("link_select_callback")
				$(".link-group").css("opacity",1)
				$(".node-group").css("opacity",1)
			}
		},
		init:function(panel){
			//$('[data-toggle="tooltip"]').tooltip({html : true })
			initTaskNodeIntoCanvas(task)
		},
		data:nodes
	})
}

function showNodeInPanel(node){
	$(".node_select").removeClass("node_select")
	$(".link_select").removeClass("link_select")
	$(".link_select_callback").removeClass("link_select_callback")
	$(".node-"+node.nodeId).addClass("node_select")
	var links=node.param.TASK_RECORD_LINKS;
	$(".link-group").css("opacity",1)
	$(".node-group").css("opacity",1)
	if(links){
		links=links.split(",");
		showLinkFromCanvas(links)
	}
}

function getParamKey(con){
	return con.match(/\{(.+?)\}/g);
}

function addSet(list,temp){
	for( var i in temp){
		if(list.indexOf(temp[i])<0){
			list.push(temp[i])
		}
	}
	return list;
}

/**根据流程节点显示下一任务节点内容选择*/
function transformNodeData(node){
	var con=''
	if(node && node.type!='end'){
		/*
		 *   流程节点确定的两种方式
		 *   1：根据用户选择的流程节点（即指定任务节点） 
		 *   2：根据用户用户上传的节点参数进行匹配（当前任务几点的下级链接条件匹配） 
		 */
		var obj=analysisFlowNode(node);
		con=getActiveNodeContent(obj)
	}
	
	return con;
}

function getActiveNodeContent(obj){
	var con='';
	if(obj ){
		con+='<div class="task-node-add-con">'
			con+='<div class="task-node-detail">'+
					'<div class="input-group"><span class="input-group-addon">name</span><input type="text" name="name"/></div>'+
					'<div class="input-group"><span class="input-group-addon">content</span><input type="text" name="content"/></div>'+
					'<div class="input-group"><span class="input-group-addon">desc</span><input type="text" name="desc"/></div>'+
				'</div>';
		var flag=false;
		if(obj.param){
			for(var key in obj.param){
				flag=true;
				break;
			}
		}
		//指向或自定义，使用后台判断
		if(obj.btn){
			if(flag){
				con+='<ul class="nav navbar-nav">'+
						'<li class="active"><a data-toggle="tab" href="#node-acture-btn">指定</a></li>'+
						'<li><a data-toggle="tab" href="#node-param-panel">自定义</a></li>'+
					  '</ul>';
			}
			con+='<div class="tab-content">';
			con+='<div class="task-node-param clearboth">'+
					'<div class="task-node-param-tool">'
						+'<i id="param-add-icon" class="glyphicon glyphicon-plus icon-plus"></i>'
						+'<span>是否自动执行：<input type="radio" name="TASK_RECORD_CONTROLLER"/></span>'
						+'<span>是否不校验权限：<input type="radio" name="TASK_RECORD_AUTO" checked="checked"/></span>'
					+'</div>'+
					'<div class="task-node-param-content"></div>'+
				  '</div>'
			con+='<div id="node-acture-btn" class=" tab-pane fade in active task-node-to-next-btn clearboth">';
			$.each(obj.btn,function(i,btn){
				con+='<button class="btn btn-primary node-select" value=\''+JSON.stringify(btn)+'\'>'+btn.name+'</button>'
			})
			con+='</div>';
			if(flag){
				con+='<div id="node-param-panel" class="tab-pane fade task-node-detail-param clearboth">';
				$.each(obj.param,function(i,p){
					con+='<div class="input-group node-param-item"><span class="input-group-addon">'+p.name+'</span>'
					if(p.type=='select'){
						con+='<select name="'+p.name+'">'
						for(var i in p.data){
							con+='<option value="'+p.data[i]+'">'+p.data[i]+' </option>'
						}
						con+='</select>'
					}else if(p.type=='input'){
						con+='<input type="text" name="'+p.name+'"/>'
					}
					con+='</div>';
				})
				con+='<div><button class="btn btn-primary node-submit">提交</button></div>'
				con+='</div>'
			}
			con+='</div>';
		}
		
		con+='</div>'
	}
	return con;
}

/**分析流程节点：确定可供选择的节点以及，自由匹配时的参数条件*/
function analysisFlowNode(node){
	var linkdata=getNodeNextLinkData(node)
	//console.log(linkdata)
	/*
	 * 将节点属性与节点链接条件进行汇总梳理，链接条件包含不确定数据时，使用input，其他情况使用select进行条件汇总
	 * */
	var nextCon='<div>';
	var paramMap={}
	//按钮节点（代表用户可供选择的流程节点）
	var btnNode=[]
	for(var i in node.attrs){
		var attr=node.attrs[i]
		paramMap[attr.value]={type:'input',name:attr.name}
	}
	for( var key in linkdata){
		var d=linkdata[key];
		var condition=d.link.condition
		if(condition !=undefined && condition !=''){
			condition=condition.replace(/and|or|\(|\)/g,',').split(",")
		}else{
			condition=[]
		}
		var paramValue={}
		if(condition.length>0){
			for(var i in condition){
				//
				var params=getParamKey(condition[i]);
				//只有一个参数且是等于关系
				if(params.length==1 &&(condition[i].indexOf("=")>-1 && condition[i].indexOf('!=')<0 )){
					var key=params[0].replace(/\{|\}/g,'');
					var v=condition[i].replace(params[0],'').replace(/=|\$|#|'/g,'');
					var vals=paramMap[key]
					if(vals==undefined){
						vals={type:"select",data:[],name:key}
					}
					if(vals.type!='input'){
						vals.data.push(v)
						paramMap[key]=vals;
						paramValue[key]=v
					}
				}else{
					for(var j in params){
						var key=params[j].replace(/\{|\}/g,'');
						paramMap[key]={type:'input',name:key}
					}
				}
			}
		}
		var fnode=d.node
		if(fnode){
			btnNode.push({nodeId:fnode.id,nodeKey:fnode.key,nodeName:fnode.name,name:fnode.name,flowId:fnode.flowId,param:paramValue})
		}
		
		nextCon+=''
	}
	return {
		btn:btnNode,
		param:paramMap
	}
	
}

function getNodeNextLinkData(node){
	var param={}
	var nexMap={}
	$.each(node.next,function(i,n){
		nexMap[n.id]=n
	})
	$.each(node.links,function(i,l){
		param[l.downNodeId]={
				link:l,
				node:nexMap[l.downNodeId]
		}
	})
	return param;
}


function initTaskNodeIntoCanvas(task){
	
}

function getContentTips(data){
//	var con=''
//	for(var key in data.param){
//		if(key.indexOf("TASK_RECORD")==0){
//			continue;
//		}
//		con+='<br/><span>'+key+':'+data.param[key]+'</span>';
//	}
//	return con==''?'':'data-toggle="tooltip" title="<div><span>参数：</span>'+con+'</div>"'
	var param={}
	var flag=false
	for(var key in data.param){
		if(key.indexOf("TASK_RECORD")==0){
			continue;
		}
		flag=true;
		param[key]=data.param[key]
	}
	return !flag?'':'data-toggle="tooltip" title=\'参数：'+JSON.stringify(param)+'\''
}

function showLinkFromCanvas(links){
	$(".link-group").css("opacity",0.2)
	$(".node-group").css("opacity",0.2)
	$.each(links,function(i,l){
		$(".node-"+l).css("opacity",1)
		if(i!=0){
			$(".link-group[link_from="+links[i-1]+"-"+l+"]").addClass("link_select")
			$(".link-group[link_from="+l+"-"+links[i-1]+"]").addClass("link_select_callback")
		}
	})
}

/**加载流程结构图与设置流程连线事件*/
function loadFlowCanvas(flowId,func){
	//加载流程结构图
	getInitDefFlowCanvas(flowId,"task-flow-canvas",func)
	window.setTimeout(function(){
		//节点链接线事件
		$.each($draw.select(".link-group").members,function(i,link){
			//console.log(i)
			link.on("mouseenter",function(){
				link.select("polyline").members[0].stroke({'color':'red'}).style('z-index',1)
				$(".link-group[id!="+link.attr('id')+"]").css("opacity",0.2)
				$(link).css("opacity",1)
			})
			link.on("mouseleave",function(){
				link.select("polyline").members[0].stroke({'color':'#000'}).style('z-index',0)
				$(".link-group").css("opacity",1)
			})
		})
	},1000)
}