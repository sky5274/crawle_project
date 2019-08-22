
var nodeTypes=[
	{name:"开始",value:"start"},
	{name:"基本",value:"base"},
	{name:"条件",value:"select"},
	{name:"容器",value:"container"},
	{name:"选择",value:"choose"},
	{name:"结束",value:"end"},
]
//content status 
var content={
	pointer:true,
	drage:false,
	choose:false,
	node:false,
	line:false,
}

function setContent(key,value){
	content[key]=value;
	$draw.initContent(content)
}
function initBtnvent(){
	//初始化环境变量
	$draw.initContent(content)
	//控制按钮状态以及样式
	$(".tool-item").on("click",function(){
		var key=$(this).attr("active")
		var keyt=$(".item-choose").attr("active");
		if(key!=keyt){
			setContent(keyt,false);
		}
		$(".item-choose").removeClass("item-choose");
		if(!content[key]){
			$(this).addClass("item-choose")
		}
		setContent(key,!content[key])
	})
	
	$("#tool-move").on("click",function(){
		var key=$(this).attr("active")
		var flag=content[key];
		console.log(flag)
	})
	$("#tool-node").on("click",function(){
		var key=$(this).attr("active")
		var flag=content[key];
		if(flag){
			$.diaLog({
				title:"选择节点类型",
				type:"confrim",
				con:getTableContent([{title:"节点类型",name:"type",ele:"select",data:nodeTypes}]),
				func:function(type,content){
					var tabledata=getTableData(content);
					var flag=tabledata.isOk;
					if(flag){
						if(type=='sure'){
							preforeNewNode({type:tabledata.data.type})
						}
					}
					$("#tool-node").click()
					return flag;
				}
			})
		}
	})
	$("#tool-link").on("click",function(){
		var key=$(this).attr("active")
		var flag=content[key];
		if(flag){
			drawLinkLine()
		}
	})
	
	$("#panel-canvas").bind("mouserout",function(){
		$(this).unbind("mouseenter");
		mousemove
		$("."+$(this).attr("target")).remove()
	})
	$("#panel-canvas").bind("click",function(){
		$(this).unbind("mouseenter");
		canvas.off("mousemove")
		var targetCls=$("#panel-canvas").attr("target");
		if(targetCls){
			window.setTimeout(function(){
				$("."+$("#panel-canvas").attr("target")).remove()
				$("#panel-canvas").removeAttr("target")
			},500)
		}
	})
	$("#panel-canvas").bind("dblclick",function(){
		$(this).unbind("mouseenter");
		$("#panel-canvas").removeAttr("target")
		var group=$(this).data("shap")
		if(group){
			group.style('cursor', 'pointer')
		}
		nodeIndex++;
		canvas.off("mousemove")
	})
}

var nodeIndex=0;
/**添加流程节点事件*/
function preforeNewNode(param){
	//当鼠标移入画板，将节点添加进入，并根据鼠标移动，调整节点位置，双击画板放置节点
	$("#panel-canvas").bind("mouseenter",function(e){
		var t=$(this).attr("target")
		if(t==undefined ){
			var key="node-"+param.type+"-"+nodeIndex;
			var node={id:nodeIndex,key:key,name:key,type:param.type};
			var group=$draw.getNodeGroupByType({node:node});
			if(param.event){
				var keys=[]
				for(var key in param.event){
					keys.push(key)
					group.on("key",param.event[key])
				}
				group.attr("eventkey",JSON.stringify(keys))
			}
			$draw.move(group,[e.offsetX,e.offsetY])
			var cls=group.attr("class")
			$(this).data("shap",group);
			$(this).attr("target",cls.replace("node-group","").trim());
		}
		canvas.off("mousemove")
		canvas.on("mousemove",function(e1){
			e1.preventDefault();
			var group=$(this.parent()).data("shap")
			group.style('cursor', 'move')
			if(param.type=='select'){
				$draw.move(group,[e1.offsetX-group.width()/3,e1.offsetY-group.height()/10])
			}else{
				$draw.move(group,[e1.offsetX-group.width()/8,e1.offsetY-group.height()/3]);
			}
		})
	})
	
	
	
}

function addNewNode(type){
	
}

/*画连接线前置事件*/
function drawLinkLine(func){
	//canvas.select(".node-group").off("mousedown")
	canvas.select(".node-group").on("mousedown",function(e){
		e.preventDefault();
		//划线必须开启
		if(!content.line){
			return false;
		}
		var _this=this;
		//根据线段两点划线
		var line=$draw.drawLine({path:[$draw.getPanelOffset([e.offsetX,e.offsetY]),$draw.getPanelOffset([e.offsetX,e.offsetY])]})
		var nodestr=$(this.node).attr("node");
		if(nodestr ==undefined){
			return false;
		}
		var linedata={}
		try {
			var n=JSON.parse(nodestr);
			linedata.upNodeId=n.id
			linedata.flowId=n.flowId
		} catch (e) {
			console.log(e)
			return false;
		}
		//移动鼠标，决定划线结束位置
		canvas.off("mousemove")
		canvas.on("mousemove",function(e1){
			$draw.changeLineEnd(line,[e1.offsetX,e1.offsetY])
		})
		canvas.off("mouseup")
		var node=undefined;
		canvas.on("mouseup",function(e2){
			e2.preventDefault();
			window.setTimeout(function(){
				//300ms,后判断鼠标按上时，是否在最近的移进节点范围内
				if(node==undefined  || node==_this || !$draw.isInNode(node, e2)){
					$(line.node).remove();
				}else{
					//node  不为当前节点，必须有，且离开位置在节点范围内
					var nnode=node.attr("node");
					linedata.downNodeId=JSON.parse(nnode).id
					//设置线属性以及可能事件
					addLineDataIndex(line,linedata,func);
				}
				node==undefined;
			},300)
			
			canvas.off("mousemove")
			canvas.off("mouseup")
		})
		//鼠标移动到的最新节点
		canvas.select(".node-group").on("mouseenter",function(){
			node=this;
		})
	})
}

var linendex=1;
/**线段添加，赋予线段属性*/
function addLineDataIndex(line,data,func){
	line.attr("node",JSON.stringify(data))
	if(func){
		func(data,line)
	}
	linendex++;
}
