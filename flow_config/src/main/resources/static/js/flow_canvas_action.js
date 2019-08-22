$(function(){
	//初始化页面右键菜单
	initContextMenu()
})

var item= {
		 	updateNodeContent: {name: "修改节点", callback: function(key, opt){
		 		doNodeAction(now_ele,1)
		 	}},
		 	addAttr: {name: "新增属性", callback: function(key, opt){
		 		doNodeAction(now_ele,2)
		 	}},
		 	updateAttr: {name: "修改属性", callback: function(key, opt,ele,e){
		 		doNodeAction(now_ele,1)
		 	}},
		 	delAttr: {name: "删除属性", callback: function(key, opt){
		 		doNodeAction(now_ele,0)
		 	}},
		 	addLink: {name: "新增链接", callback: function(key, opt){
		 		doNodeAction(now_ele,2)
		 	}},
		 	updateLink: {name: "修改链接", callback: function(key, opt){
		 		doNodeAction(now_ele,1)
		 	}},
		 	delLink: {name: "删除链接", callback: function(key, opt){
		 		doNodeAction(now_ele,0)
		 	}},
		 	addEvent: {name: "新增事件", callback: function(key, opt){
		 		doNodeAction(now_ele,2)
		 	}},
		 	updateEvent: {name: "修改事件", callback: function(key, opt){
		 		doNodeAction(now_ele,1)
		 	}},
		 	delEvent: {name: "删除事件", callback: function(key, opt){
		 		doNodeAction(now_ele,0)
		 	}},
		 }

var now_ele={}
function initContextMenu(){
	$.contextMenu({
		 selector: ".panel-detail-show div.node-item",
		 build:function($trigger, e){
			 var type=$trigger.attr("n_type")
			 var value=$trigger.attr("value")
			 var tabmenu={}
			 if(type){
				 if(type=='content'){
					 type='node'
				 }
				 now_ele={}
				 now_ele.target=$trigger
				 if($($trigger).children(".panel-body").length>0){
					 now_ele.target=$($trigger).children(".panel-body")
				 }
				 now_ele.type=type
				 if(value!=undefined && value!=''){
					 now_ele.value=JSON.parse(value.replace(new RegExp('&t;','g'),"'"))
				 }
				 for(var key in item){
					 if(key.toLowerCase().indexOf(type)>=0 &&(key)){
						if(key.indexOf("update")>-1 && now_ele.flowId==flowId && now_ele.value!=undefined){
							tabmenu[key]=item[key];
						}else if(key.indexOf("update")<0){
							if(now_ele.value==undefined && key.indexOf("del")==0){
								continue;
							}
							tabmenu[key]=item[key];
						}
					 }
				 }
			 }else{
				 tabmenu=item;
			 }
			 return {items:tabmenu};
		 }
	});
}

/*节点操作事件：opt:0:删除，1:修改，2:新增*/
function doNodeAction(param,opt){
	//console.log(param)
	if(param.target==undefined){
		$.diaLog({title:"警告",con:"菜单选择位置未确定"})
		return;
	}
	if(param.type ==undefined){
		$.diaLog({title:"警告",con:"菜单选择定位未确定操作类型"})
		return;
	}
	param.opt=opt;
	switch (param.type) {
	case 'link':
		//节点链接操作
		doActionLinkContent(param)
		break;
	case 'attr':
		//节点属性操作
		doActionAttrContent(param)
		break;
	case 'event':
		//节点事件操作
		doActionEventContent(param)
		break;

	default:
		//默认节点更新操作
		 doActionNodeContent(param)
		break;
	}
}

/*操作节点内容*/
function doActionNodeContent(param){
	if(param.opt!=1){
		$.diaLog({title:"警告",con:"节点内容目前只允许修改"})
		return;
	}
	$(param.target).find("input").removeAttr("disable")
	$(document).on("keydown",function(e){
		if(e.keyCode=='13'){
			var obj={}
			$(param.target).find("input").each(function(i,inp){
				if($(this).val()!=''){
					obj[$(this).attr("name")]=$(this).val();
				}
			})
			console.log(obj)
		}
	})
}

/*操作节点属性*/
function doActionAttrContent(param){
	param.extendNewComp=function(obj){
		//拓展新的节点属性
		return obj;
	}
	param.modContent=getAttrModContent
	nodeComponentAction(param)
}


/**节点属性、链接、事件操作通用模型*/
function nodeComponentAction(param){
	console.log(param)
	switch (param.opt) {
	case 0:
		var tips
		switch (param.type) {
		case "attr":
			tips='节点属性'
			break;
		case "link":
			tips='链接'
				break;
		case "event":
			tips='节点事件'
				break;

		default:
			tips='数据'
			break;
		}
		//删除
		$.diaLog({
			type:"confrim",
			con:"是否删除"+tips,
			func:function(type){
				if(type=='sure'){
					//删除节点操作
					param.form=function(d){console.log(d);$(param.target).remove();deleteNodeData(d,param.type+'s')}
					doNodeComponentAction(param)
				}
			}
		})
		break;
	case 1:
		//修改
		var parent=$(param.target);
		var ht=parent.html()
		parent.html("")
		parent.removeAttr("value")
		parent.append('<div class="node-mod-temp" value=\''+JSON.stringify(param.value)+'\' style="display: none">'+ht+'</div>')
		if(param.modContent){
			parent.append(param.modContent(param.type,param.value))
		}
		nodeModInputEvent({target:param.target,
			form:function(obj){
				param.value=$.extend(param.value,obj,true);
				//更新节点操作
				param.form=function(d){updateNodeData(d,param.type+'s')}
				doNodeComponentAction(param)
			},
			leave:function(tar){
				var temp=parent.find(".node-mod-temp");
				parent.attr("value",temp.attr("value"))
				parent.html(temp.html())
			}
		})
		break;
	case 2:
		//新建
		var parent=$(param.target)
		if($(param.target).attr("value")){
			parent=parent.parent()
		}
		if(param.modContent){
			var con=$(param.modContent(param.type,param.value));
			con.addClass("node-mod-temp")
			parent.append(con)
		}
		//节点属性编辑事件
		nodeModInputEvent({target:param.target,
			form:function(obj){
				if(param.extendNewComp){
					param.value=param.extendNewComp(obj,getNodeData(getNowSelectNode()))
				}
				//添加节点操作
				param.form=function(d){addNodeData(d,param.type+'s');parent.find(".node-mod-temp").remove();}
				doNodeComponentAction(param)
			},
			leave:function(tar){
				parent.find(".node-mod-temp").remove();
			}
		})
		break;

	default:
		$.diaLog({title:"警告",con:"无效操作"})
		break;
	}
}

var option_name=['del','update','add']
function doNodeComponentAction(obj){
	$[obj.opt!=0?'doPostJsonAjax':'doJsonAjax']({
		url:'/flow/node/'+obj.type+'/'+option_name[obj.opt],
		data:obj.value,
		success:function(res){
			
			$(document).unbind("keydown")
			var d=res.data
			if(d.flowId==undefined){
				d=obj.value
			}
			if(obj.form){
				obj.form(d)
			}
			if(obj.opt==0){
				if(obj.success){
					obj.success(d,obj.target)
				}
				return ;
			}
			var con
			switch (obj.type) {
			case "attr":
				con=getAttrCon(d)
				break;
			case "link":
				con=getLinkCon(d)
				break;
			case "event":
				con=getEventCon(d)
				break;

			default:
				console.log("def")
				break;
			}
			if(con){
				if(obj.opt==1){
					//请求成功后变更页面数据，以及加载数据处理事件
					$(obj.target).html($(con).html())
					$(obj.target).attr("value",JSON.stringify(d))
				}else{
					//新增数据
					$(obj.target).append($(con))
				}
				
				if(obj.success){
					obj.success(d,obj.target)
				}
			}
		}
	})
}

function getNodeData(node){
	return JSON.parse(node.attr("node"))
}

function setNodeData(node,data){
	node.attr("node",JSON.stringify(data))
}

/**删除页面节点部分数据*/
function deleteNodeData(obj,key){
	var node=getNowSelectNode()
	var nodeValue=getNodeData(node)
	for(var i in nodeValue[key]){
		if(nodeValue[key][i].id=obj.id){
			//删除数据
			nodeValue[key].splice(i,1)
		}
	}
	setNodeData(node,nodeValue)
	
}

/**更新页面节点部分数据*/
function updateNodeData(obj,key){
	var node=getNowSelectNode()
	var nodeValue=getNodeData(node)
	for(var i in nodeValue[key]){
		if(nodeValue[key][i].id=obj.id){
			nodeValue[key][i]=obj;
		}
	}
	setNodeData(node,nodeValue)
}

/**新增部分页面节点数据*/
function addNodeData(obj,key){
	var node=getNowSelectNode()
	var nodeValue=getNodeData(node)
	if(nodeValue[key]==undefined){
		nodeValue[key]=[]
	}
	nodeValue[key].push(obj)
	setNodeData(node,nodeValue)
}

var input_focus='input_focus'
/*节点属性编辑事件*/
function nodeModInputEvent(obj){
	var target=obj.target
	$(target).find("input,select").css("height", "25px")
	$(target).find("input,select").focus(function(){
		$(this).addClass(input_focus)
		$(this).removeClass("input-null")
	})
	$(target).find("input,select").blur(function(){
		$(this).removeClass(input_focus)
		var _this=this;
		window.setTimeout(function(){
			if($(target).find("."+input_focus).length==0){
				if(obj.leave){
					obj.leave(_this);
				}
				$(_this).parent().remove()
			}
		},500)
	})
	$(document).unbind("mousedown")
	$(document).bind("mousedown",function(){
		window.setTimeout(function(){
			if($(target).find("."+input_focus).length==0){
				if(obj.leave){
					obj.leave($(target).find("input,select"));
				}
				$(target).find("input,select").parent().remove()
			}
			$(document).unbind("mousedown")
		},500)
	})
	$(document).unbind("keydown")
	$(document).bind("keydown",function(e){
		if(e.keyCode=="13"){
			var param={}
			var errkey=[]
			$(target).find("input,select").each(function(){
				$(this).addClass(input_focus)
				if($(this).val().trim()!=''){
					param[$(this).attr("name")]=$(this).val();
				}else{
					errkey.push($(this).attr("name"))
					$(this).addClass("input-null")
				}
			})
			if(errkey.length>0){
				for(var i in errkey){
					$.alert({type:"warn",content:errkey[i]+"为空！"})
				}
			}
			if(obj.form){
				obj.form(param)
			}
		}
	})
	
}

function getLinkModContent(type,val){
	var nodes=canvas.select(".node-group").members
	var con='<div n_type="'+type+'" class="col-sm-12 clearfix node-item-add">'+
				'<input class="form-control input-tab-lable pull-left" type="text" name="condition" placeholder="condition" value="'+(val?val.condition:"")+'"/><span class="pull-left">:</span>'+
				'<select class="form-control input-tab-lable pull-left" type="text" name="downNodeId" >';
	con+='<option></option>'
	$.each(nodes,function(i,n){
		var d=getNodeData(n);
		var sel=''
		if(val){
			sel=val.downNodeId==d.id?'selected = "selected"':''
		}
		con+='<option value="'+d.id+'" '+sel+'>'+d.name+'</option>'
	})
	con+='</select></div>';
	return con;
			
}
function getAttrModContent(type,val){
	return '<div n_type="'+type+'" class="col-sm-12 clearfix node-item-add"><input class="form-control input-tab-lable pull-left" type="text" name="key" placeholder="key" value="'+(val?val.key:"")+'"/><span class="pull-left">:</span><input class="form-control input-tab-lable pull-left" type="text" name="value" placeholder="value" value="'+(val?val.value:"")+'"/></div>';
}
function getEventModContent(type,val){
	return '<div n_type="'+type+'" class="col-sm-12 clearfix node-item-add">'+
				'<input class="form-control input-tab-lable pull-left" type="text" name="key" placeholder="key" value="'+(val?val.key:"")+'"/><span class="pull-left">:</span>'+
				'<input class="form-control input-tab-lable pull-left" type="text" name="name" placeholder="name" value="'+(val?val.name:"")+'"/><span class="pull-left">:</span>'+
				'<input class="form-control input-tab-lable pull-left" type="text" name="eventType" placeholder="eventType" value="'+(val?val.eventType:"")+'"/><span class="pull-left">:</span>'+
				'<input class="form-control input-tab-lable pull-left" type="text" name="type" placeholder="type" value="'+(val?val.type:"")+'"/><span class="pull-left">:</span>'+
				'<input class="form-control input-tab-lable pull-left" type="text" name="content" placeholder="content" value="'+(val?val.content:"")+'"/>'+
			'</div>';
}


/*操作节点链接*/
function doActionLinkContent(param){
	//链接数据补齐
	param.extendNewComp=function(obj,node){
		//拓展新的链接
		obj.flowId=node.flowId
		obj.upNodeId=node.id
		return obj;
	}
	param.modContent=getLinkModContent,
	param.success=function(data){
		$(".line-link-"+data.id).remove();
		if(param.opt!=0){
			//画新线
			var p={}
			p[data.upNodeId]=[data]
			$draw.drawLinks({links:p});
		}
	}
	nodeComponentAction(param)
}

/*操作节点事件*/
function doActionEventContent(param){
	//事件数据补齐
	param.extendNewComp=function(obj,node){
		//拓展新的节点事件
		obj.flowId=node.flowId
		obj.nodeId=node.id
		return obj;
	}
	param.modContent=getEventModContent
	nodeComponentAction(param)
}

/*节点控件操作：0:删除，1:修改，2:新增*/
function activeNodeComponent(ele,act){
	var val=$(ele).attr("value");
	if(val !=undefined && val !=''){
		val=JSON.parse(val.replace(new RegExp('&t;','g'),"'"))
	}
	var param={target:ele,type:$(ele).attr("n_type"),value:val}
	doNodeAction(param,act)
}

function ajaxPost(obj){
	$.doPostJsonAjax({
		url:obj.url,
		data:obj.data,
		loadding:true,
		succss:obj.success
	})
}