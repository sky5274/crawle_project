
/** 流程画板初始化*/
function initFlowCanvas(flowId){
	//初始化按钮事件
	initBtnvent();
	var flowCavasPath="/svg/"+flowId+".svg";

	$("#save-flow-btn").on("click",function(){
		$.diaLog({
			con:"是否保存绘制的流程",
			func:function(type){
				if(type=='sure'){
					saveFlowCanvas(flowCavasPath)
				}
			}
		})
	})
	
	initFlowCanvasFromData(flowId)
	
}

function getInitDefFlowCanvas(flowId,ele,func){
	var flowCavasPath="/svg/"+flowId+".svg";
	if($($("#"+ele+" svg")).length<=0){
		canvas=$draw.initCanvas(ele);
	}
	$.doAjax({
		url:"/file"+flowCavasPath,
		loadding:true,
		success:function(html){
			if(html.indexOf("</svg>")>-1){
				$("#"+ele).find("svg").html($(html).html())
				//初始化节点事件
				initNodeEvent(canvas.select(".node-group").members);
			}else{
				initFlowCanvasFromData(flowId)
			}
			if(func){
				func()
			}
		},
		error:function(){
			initFlowCanvasFromData(flowId)
			if(func){
				func()
			}
		}
	})
}

/**保存流程画板*/
function saveFlowCanvas(flowCavasPath){
	var html=$("#panel-canvas").html()
	var file=createTxt(flowId+".svg",html)
	$.doUpdateFileAjax({
		url:"/file/replace",
		data:{file:file,path:flowCavasPath},
		loadding:true,
		success:function(res){
			console.log(res)
			$.diaLog({con:"流程绘制保存成功！",closed:true})
		}
	})
}

function initNodeEvent(nodes){
	$.each(nodes,function(i,node){
		$draw.drageGroup(node)
		for(var evt in node_event){
			node.on(evt,node_event[evt])
		}
	})
	
}

/**根据流程交节点数据生成流程节点*/
function initFlowCanvasFromData(flowId){
	$.doJsonAjax({
		url:"/flow/query/id?flowId="+flowId,
		loadding:true,
		success:function(res){
			flow=res.data
			$.session.put("flow_"+flowId,flow);
			$(".panel-title").html(flow.name)
			drawNode(flow)
			
			//画中垂线
//			$draw.drawLine([[0,ph/2],[pw,ph/2]])
		}
	})
}

function createTxt(name,content){
	return  new Blob([content], {name:name,type: "text/plain;charset=utf-8"})
}

function drawNode(flow){
	var linknode=getNodeLinks(flow);
	//console.log(linknode)
	var links=linknode.node
	var nodes=initFlowNode(flow);
//	$.each(flow.nodes,function(i,n){
//		$draw.loadNode({
//			node:n,
//			success:function(node){
////				console.log(node)
//			},
//			event:{
//				click:function(){
//					console.log(this)
//				}
//		
//			}
//		})
//	})
	var nodekeys=[]
	var nodeLen=getLinkLevel(links)+2;   //减去收尾两层
	drawNodeLevel(links,nodes,0,nodekeys,0,nodeLen,true)
	
	//画连接线
	$draw.drawLinks({links:linknode.link});
	
}

function drawNodeLevel(links,nodes ,index,keylist,size,nodeLen,flag){
	//不是第一层，整行家1
	if(!flag){
		index++;
	}
	var i=0
	for(var link in links){
		if(typeof(link) !='string'){
			continue;
		}
		if($.inArray(link,keylist)>-1){
			continue;
		}
		keylist.push(link)
		var keys=getNodeKeys(links[link])
		var n=nodes[link];
		if(n==undefined){
			continue;
		}
		//当前节点添加，当前行，索引，行内尺寸
		drowNodeGroup(n,(index+1)/nodeLen,i++,size)
		if(keys.length>0){
			//子节点，添加到整行内，索引位，添加行内尺寸
			drawNodeLevel(links[link],nodes,index,keylist,keys.length,nodeLen,false)
		}
		//是第一行的层次加一
		if(flag){
			index++;
		}
	}
}

function getLinkLevel(links){
	var size=0;
	for(var link in links){
		size+=getLinkLen(links[link],1);
	}
	return size;
}

function getLinkLen(link,lvl){
	var size=0;
	for(var key in link ){
		var s=getLinkLen(link[key],lvl+1)
		if(s>size){
			size=s;
		}
	}
	if(size==0){
		size+=lvl
	}
	return size;
}

function getNodeKeys(node){
	var keys=[]
	for(var key in node){
		keys.push(key);
	}
	return keys;
}

var node_event={
		click:function(){
			canvas.select(".ele_active").removeClass("ele_active")
			this.addClass("ele_active")
			var node=JSON.parse(this.attr("node"))
			showNodeContent(node)
		}
}


function drowNodeGroup(n,len,index,widthsize){
	$draw.loadNode({
		node:n,
		panel:{
			len:len,	//行
			size:widthsize,  //行内节点数
			index:index,   //节点索引
		},
		success:function(node){
//			console.log(node)
		},
		event:node_event
	})
}



function getNowSelectNode(){
	return canvas.select(".ele_active").members[0]
}

/**显示节点信息*/
function showNodeContent(node){
	$(".panel-detail-show").html("")
	var panel=$('<div class="panel-group" id="panel-node-show"></div>')
	//显示节点名称，类型，key
	panel.append(setPanelItem({type:"content",title:"节点内容",
		content:
			'<input type="hidden" name="id" value="'+node.id+'"/>'+
			'<input type="hidden" name="flowId" value="'+node.flowId+'"/>'+
			'<input type="hidden" name="containerId" value="'+node.containerId+'"/>'+
			'<div class="input-group col-sm-12"><span class="input-group-addon input-tab-lable">名称：</span><input type="text" class="form-control"  placeholder="名称" name="name" value="'+getNotNullText(node.name)+'"></div>'+
			'<div class="input-group col-sm-12"><span class="input-group-addon input-tab-lable">key：</span><input type="text" class="form-control"  placeholder="key" name="name" value="'+getNotNullText(node.key)+'"></div>'+
			'<div class="input-group col-sm-12"><span class="input-group-addon input-tab-lable">类型：</span><input type="text" class="form-control"  placeholder="类型" name="name" value="'+getNotNullText(node.type)+'"></div>'+
			'<div class="input-group col-sm-12"><span class="input-group-addon input-tab-lable">创建人：</span><input type="text" class="form-control"  placeholder="创建人" name="name" value="'+getNotNullText(node.createId)+'"></div>'+
			'<div class="input-group col-sm-12"><span class="input-group-addon input-tab-lable">创建时间：</span><input type="text" class="form-control"  placeholder="创建时间" name="name" value="'+new Date(node.ts).toLocaleString()+'"></div>'
		}))
	//显示节点属性
	panel.append(setPanelItem({type:"attr",title:"属性",content:getNodeAttrContent(node)}))
	//显示节点链接
	panel.append(setPanelItem({type:"link",title:"链接",content:getNodeLinkContent(node)}))
	//显示节点事件
	panel.append(setPanelItem({type:"event",title:"事件",content:getNodeEventContent(node)}))
	$(".panel-detail-show").append(panel)
	
	//删除容器属性、链接、事件操作标识
	$(".node-obj .glyphicon").each(function(i,ele){
			var fID=$(this).parents('.node-obj').attr("flowId")
			if(fID!=node.flowId){
				$(this).remove()
			}
	})
	
	//鼠标事件
	$(".node-obj").hover(function(){
		$(this).addClass("item-active")
	},function(){
		$(this).removeClass("item-active")
	})
	$(".node-obj").off("click")
	$(".node-obj").on("click",".glyphicon",function(){
		var i=1
		if($(this).attr("class").indexOf("glyphicon-minus")>-1){
			i=0
		}
		
		//节点操作
		activeNodeComponent($(this).parents(".node-obj"),i)
	})
}

function setPanelItem(obj){
	var it=$('<div class="panel panel-default">'+
		        '<div class="panel-heading">'+
		            '<h4 class="panel-title node-item" n_type="'+obj.type+'">'+
		                '<a data-toggle="collapse" href="#panel-node-'+obj.type+'" onClick="tragglePanel(this)">'+obj.title+'</a><i class="glyphicon '+(obj.type!='content'?'glyphicon-chevron-right':'glyphicon-chevron-down')+' pull-right"></i>'+
		            '</h4>'+
		        '</div>'+
		        '<div id="panel-node-'+obj.type+'" class="panel-collapse node-item collapse '+(obj.type!='content'?'':'in')+'" n_type="'+obj.type+'">'+
		            '<div class="panel-body"></div>'+
		        '</div>'+
		    '</div>')
	it.find(".panel-body").append(obj.content);
	return it;
}

function getNodeAttrContent(node){
	if(node.attrs && node.attrs.length>0){
		var str='';
		$.each(node.attrs,function(i,a){
			str+=getAttrCon(a)
		})
		return str;
	}else{
		return '';
	}
	
}
function getAttrCon(a){
	return '<div n_type="attr" value=\''+JSON.stringify(a)+'\' flowId="'+a.flowId+'" class="col-sm-12 node-item node-obj"><span>'+a.key+':'+a.value+'</span><i class="glyphicon glyphicon_act glyphicon-minus"></i><i class="glyphicon glyphicon_act glyphicon-pencil"></i></div>'
}

function getNodeLinkContent(node){
	var str='';
	$.each(node.links,function(i,l){
		str+=getLinkCon(l)
	})
	return str;
}

function getLinkCon(l){
	var n=$(".node-"+l.downNodeId).attr("node")
	if(n){
		n=JSON.parse(n);
		return '<div n_type="link" value=\''+JSON.stringify(l).replace(/'/g, "&t;")+'\' flowId="'+l.flowId+'" class="col-sm-12 node-item node-obj"><span>'+l.condition+':'+n.name+'</span><i class="glyphicon glyphicon_act glyphicon-minus"></i><i class="glyphicon glyphicon_act glyphicon-pencil"></i></div>';
	}
	
}

function getNodeEventContent(node){
	var str='';
	$.each(node.events,function(i,e){
		str+=getEventCon(e);
	})
	return str;
}

function getEventCon(e){
	return '<div n_type="event" value=\''+JSON.stringify(e)+'\' flowId="'+e.flowId+'" class="col-sm-12 node-item node-item node-obj"><div class="col-sm-10"><p>key:'+e.key+'</p><p>name:'+e.name+'</p><p>事件执行类型： '+e.eventType+'</p><p>事件执行方式： '+e.type+'</p><p>事件执行内容： '+e.content+'</p></div><div class="col-sm-2"><i class="glyphicon glyphicon_act glyphicon-minus"></i><i class="glyphicon glyphicon_act glyphicon-pencil"></i></div></div>';
}

function tragglePanel(ele){
	var icon=$(ele).parent().find("i");
	if(icon.attr("class").indexOf("glyphicon-chevron-down")>-1){
		icon.removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-right")
	}else{
		icon.removeClass("glyphicon-chevron-right").addClass("glyphicon-chevron-down")
	}
	
}


function initFlowNode(flow){
	var nodes={};
	$.each(flow.nodes,function(i,n){
		nodes[n.id]=n;
		getContainerNode(n,nodes)
	})
	return nodes;
}

function getContainerNode(n,nodes){
	if(n.type=='container'){
		$.each(n.container,function(k,nc){
			nc.isContainer=true;
			nodes[nc.id]=nc;
			nodes=getContainerNode(nc,nodes)
		})
	}
	return nodes
}

function getNodeLinks(flow){
	var nodes=flow.nodes
	var links=[];
	var startId;
	if(flow.startId!=null && flow.startId!='' && flow.startId!=undefined){
		startId=flow.startId
	}
	var linkNode={}
	var link={}
	$.each(nodes,function(i,n){
		getContainerLink(n,startId,linkNode,links,link)
		$.each(n.links,function(j,l){
			if(startId == undefined && l.type=='start'){
				startId=l.upNodeId
			}
			var ls=linkNode[l.upNodeId];
			var lsl=link[l.upNodeId];
			if(ls==undefined){
				ls=[]
				lsl=[]
			}
			ls.push(l.downNodeId)
			lsl.push(l)
			linkNode[l.upNodeId]=ls
			links.push(l)
			link[l.upNodeId]=lsl
		})
	})
	if(startId==undefined && links.length>0){
		startId=links[0].upNodeId
	}
	return {node:analysisNodeLink(linkNode,startId),link:link} 
}

function getContainerLink(n,startId,linkNode,links,link){
	if(n.type=='container'){
		$.each(n.container,function(k,nc){
			$.each(nc.links,function(j,l){
				if(startId == undefined && l.type=='start'){
					startId=l.upNodeId
				}
				var ls=linkNode[l.upNodeId];
				var lsl=link[l.upNodeId];
				if(ls==undefined){
					ls=[]
					lsl=[]
				}
				ls.push(l.downNodeId)
				lsl.push(l)
				linkNode[l.upNodeId]=ls
				links.push(l)
				link[l.upNodeId]=lsl
			})
			getContainerLink(nc,startId,linkNode,links,link)
		})
	}
}


function analysisNodeLink(linkNode,startId){
	linkNode[startId]=getNextNodeLink(linkNode,startId)
	for(var key in linkNode){
		var l=linkNode[key];
		if(l.length>0){
			linkNode[key]=getNextNodeLink(linkNode,key)
		}
	}
	return linkNode
}
function getNextNodeLink(linkNode,key){
	var ls=linkNode[key];
	var lsv={}
	$.each(ls,function(i,l){
		var lk=linkNode[l]
		delete linkNode[l]
		var lkv={}
		$.each(lk,function(j,k){
			lkv[k]=getNextNodeLink(linkNode,k)
		})
		lsv[l]=lkv
	})
	delete linkNode[key]
	return lsv;
}

