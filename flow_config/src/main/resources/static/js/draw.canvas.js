var $draw={
		_setting:{},
		_event:{}
};
$draw.extend=function(param){
	$draw._setting=$.extend(true,$draw._setting,param)
}
$draw.get=function(key){
	return $draw._setting[key]
}
$draw.fn={
		extend:function(params){
			for(var key in params){
				$draw[key]=params[key]
			}
		}
}
$draw.on=function(key,event){
	$draw._event[key]=event
}
;(function($,$$){
	var canvs
	//线段的起始、中间、结束marker
	var line_start,line_mid,line_end;
	var pw,ph,mph,mpw;  //canvas,panel_width,panel_height
	var flow={};
//	var canvas_layout='Horizontal'   //  Vertical 
		var canvas_layout='Vertical'   //  Vertical 
	
	
	function initContent(content){
		return $$.extend({"content":content})
	}
	function setContent(key,value){
		var content=getContent();
		content[key]=value;
		initContent(content);
	}
	function getContent(){
		return $$.get("content")
	}
	function getCanvas(){
		if(canvs==undefined){
			canvs=$$.get("canvas")
		}
		return canvs
	}
	
	function getNode(id){
		return getCanvas().select(".node-"+id).members[0]
	}
	function getNodeData(id){
		var node
		$.each(flow.nodes,function(i,n){
			if(n.id==id){
				node=n;
				breakl
			}
		})
		return node;
	}

	var node_ids=[]
	function pushNodeId(id){
		node_ids.push(id)
	}
	function getNodeIndex(id){
		return $.inArray(id,node_ids);
	}
	function hasNodeId(id){
		return $.inArray(id,node_ids)>0
	}

	function loadNode(param){
		var node=param.node;
		if(hasNodeId(node.id)){
			return false;
		}
		pushNodeId(node.id)
		addNodeGroup(param)
	}

	//添加节点组
	function addNodeGroup(param){
		//文字与图表结合
		var group=getNodeGroupByType(param)
		var offset=getGroupNodeOffset({group:group,panel:param.panel});
		//设置图组位置
		move(group,[offset.x,offset.y])
		if(param.success){
			param.success(group)
		}
		return group;
	}

	function getGroupNodeOffset(param){
		if(canvas_layout==undefined || canvas_layout==''){
			canvas_layout='Horizontal'
		}
		return canvas_layout=='Horizontal'?getGroupNodeHOffset(param):getGroupNodeVOffset(param)
		
	}
	/**水平布局*/
	function getGroupNodeHOffset(param){
		var group =param.group
		//决定图组位置
		var ww=pw-group.attr("width")
		var wh=ph-group.attr("height")
		
		var type=group.attr("nodetype")
		var wy
		if(panel){
			if(panel.size>1){
				panel.index+=1;
				wy=wh*((panel.index*2-1)/(panel.size*2))
			}else{
				wy=wh*0.5 
			}
		}else{
			wy= isSingleNode(type) ?wh*0.5 :wh*0.25*(1+Math.random())
		}
	
		var wx=(panel!=undefined && panel.len>0?panel.len:Math.random())*ww
		
		if(type=='end' ){
			wx=ww-10
		}
		if(type=='start'){
			wx=0+10;
		}
		if(type=='select'){
			wy+=parseFloat(group.attr("height"))/3;
		}
		return {x:wx,y:wy}
	}
	/**垂直布局*/
	function getGroupNodeVOffset(param){
//		console.log(param)
		var group =param.group
		var panel=param.panel;
		//决定图组位置
		var ww=pw-group.attr("width")
		var wh=ph-group.attr("height")
		var type=group.attr("nodetype")
		var wx
		if(panel){
			if(panel.size>1){
				panel.index+=1;
				wx=ww*((panel.index*2-1)/(panel.size*2))
			}else{
				wx=ww*0.5 
			}
		}else{
			wx=isSingleNode(type) ?ww*0.5 :ww*0.25*(1+Math.random())
		}
	
		var wy=(panel!=undefined && panel.len>0?panel.len:Math.random())*wh
		if(type=='end' ){
			wy=wh-10
			wx+=group.attr("width")/4
		}
		if(type=='start'){
			wy=0+10;
			wx+=parseInt(group.attr("width")/4)
		}
		return {x:wx,y:wy}
	}

	/**当前图组类型是否最多只有1个*/
	function isSingleNode(type){
		return getCanvas().select("g[nodeType="+type+"").members.length<=1;
	}

	/**根据节点类型，设置流程节点的外形*/
	function getNodeByType(n){
		switch (n.type.trim()) {
		case 'container_select':
		case 'select':
			n.length=n.length<8?8:n.length;
			var h=30*2,w=n.length*5*2;
			var path=getCanvas().path("M"+w/2+",0 "+" L"+w+","+h/2+" L"+w/2+","+h +" L0,"+h/2+" z").attr("fill", '#f06')
			return path
			break;
		case 'container_container':
		case 'container':
			return getCanvas().rect(n.length*5,30).attr("fill", '#f06');
			break;
		case 'container_choose':
		case 'choose':
			return getCanvas().rect(n.length*5,30).rx(6).ry(6).attr("fill", '#f06');
			break;
		case 'container_start':
		case 'start':
			return getCanvas().circle(35).attr("fill", 'none').stroke({ width: 2 ,color:"#f06"});;
			break;
		case 'container_end':
		case 'end':
			return getCanvas().circle(35).attr("fill", '#f06');
			break;

		default:
			return getCanvas().rect(n.length*5,30).attr("fill", '#f06');
			break;
		}
	}

	/**生成节点构型*/
	function getNodeGroupByType(param){
		var node=param.node;
		var nameLength=node.name.length+2
		if(nameLength<5){
			nameLength=5
		}
		var group=getCanvas().group();
		group.attr("nodetype",(node.isContainer?"container_":"")+node.type)
		group.addClass("node-group node-"+node.id);
		//生成节点构型
		var n_ele=getNodeByType({type:node.type,length:nameLength,id:node.id})
		group.add(n_ele)
		//生成图组的文本
		var n_txt=group.text(node.name).x(2)
		
		var fontSize=parseInt($(n_txt.node).css("font-size"));
		if(fontSize==undefined || fontSize==null || fontSize<14){
			fontSize=14;
		}
		var eh=0
		//元素样式（宽高调整）
		var ew=nameLength*fontSize
		var e_type=n_ele.node.nodeName
		if(e_type=="circle"){
			nameLength=5
			if(node.name.length>2){
				n_txt.plain(node.name.substring(0,2)+"..")
			}
			eh=parseFloat(n_ele.attr("r"))*2
			//调整文件才圆中的高度
			n_txt.y((28-eh/2))
			group.attr("gw",eh)
			group.attr("gh",eh)
		}else if(e_type=="rect"){
			//调整文件在矩形的横向位置
			n_txt.x((nameLength-node.name.length)*7)
			eh=n_ele.attr("height");
			group.attr("gw",ew)
			group.attr("gh",eh)
		}else if(node.type=='select'){
			n_txt.x((nameLength-node.name.length)*7).y(fontSize*((node.name.length+1)/3))
			group.attr("gw",n_ele.width())
			group.attr("gh",n_ele.height())
		}
		
		n_ele.attr("width",ew)
		group.attr("width",ew+10)
		group.attr("height",(eh>1?eh:ew)+10)
		group.attr("node",JSON.stringify(node))
		group.style('cursor', 'pointer')
		
		//初始化事件
		initNodeGroupEvent(group,param.event)
		return group;
	}

	/*图组事件*/
	function initNodeGroupEvent(group,event){
		if(event){
			for(var key in event){
				group.on(key,event[key])
			}
		}
		drageGroup(group);
	}
	
	
	function getNodeGroupOffset(group){
		var transformStr=group.attr("transform").trim();
		//	console.log(transformStr)
		var transform=transformStr.substring(transformStr.indexOf('(')+1,transformStr.length-1).split(",")
		return {x:parseFloat(transform[transform.length-2]),y:parseFloat(transform[transform.length-1])}
	
	}

	/**元素拖拽*/
	function drageGroup(group){
		group.on('mousedown',function(e){
			e.preventDefault();
			if(getContent() && getContent().drage){
				group.style('cursor', 'move')
				this.node.setAttribute("drageable",true)
				var offset=getNodeGroupOffset(group);
				initNodeGroupLinkOffset(group);
//				console.log(offset)
				getCanvas().off("mousemove")
				getCanvas().on("mousemove",function(e1){
					var flag=group.node.getAttribute("drageable")
					if(flag){
					var scl=getPanleScal();
						var x=e1.pageX-e.pageX;
						var y=e1.pageY-e.pageY;
//						console.log({x:x,y:y})
						group.move(offset.x+x*scl.x,offset.y+y*scl.y)
						moveGroupLink(group,[x,y]);
					}
				})
			}
		})	
		group.on('mouseup',function(e){
			var _this=this;
			group.style('cursor', 'pointer')
			_this.node.setAttribute("drageable",false)
		})
	}
	function drage(group){
		group.on('mousedown',function(e){
			if(getContent() && getContent().drage){
				this.node.setAttribute("drageable",true)
				var offset={x:parseFloat(this.node.getAttribute("x")),y:parseFloat(this.node.getAttribute("y"))}
				getCanvas().off("mousemove");
				getCanvas().on("mousemove",function(e1){
					var flag=group.node.getAttribute("drageable")
					if(flag){
						var x=e1.pageX-e.pageX;
						var y=e1.pageY-e.pageY;
						move(group,[offset.x+x,offset.y+y])
					}
				})
			}
		})	
		group.on('mouseout',function(e){
			this.node.setAttribute("drageable",false)
			getCanvas().off("mousemove")
		})
	}
	
	/**初始化节点对应的连线起始结束位置*/
	function initNodeGroupLinkOffset(group){
		var s_link_id=group.attr("s_link_id")
		var e_link_id=group.attr("e_link_id")
		if(s_link_id !=undefined && s_link_id !='' && s_link_id.length>0){
			$.each(s_link_id.split(","),function(i,id){
				var ling=getCanvas().select("#"+id).members[0];
				if(ling){
					initLineOffset(ling,false)
				}
			})
		}
		if(e_link_id !=undefined && e_link_id !='' && e_link_id.length>0){
			$.each(e_link_id.split(","),function(i,id){
				var ling=getCanvas().select("#"+id).members[0];
				if(ling){
					initLineOffset(ling,true)
				}
			})
		}
	}
	
	/**设置折线的起始位置的offset记录*/
	function initLineOffset(linegroup,isend){
		var line=linegroup.select("polyline").members[0]
		var list=line.array();
		setLineLinkSEOffest(linegroup,list)
	}
	
	function moveGroupLink(node,offset){
		offset=getPanelOffset(offset);
		var s_link_id=node.attr("s_link_id")
		var e_link_id=node.attr("e_link_id")
		if(s_link_id !=undefined && s_link_id !='' && s_link_id.length>0){
			$.each(s_link_id.split(","),function(i,id){
				moveAutoLine(getCanvas().select("#"+id).members[0],false,offset)
			})
		}
		if(e_link_id !=undefined && e_link_id !='' && e_link_id.length>0){
			$.each(e_link_id.split(","),function(i,id){
				moveAutoLine(getCanvas().select("#"+id).members[0],true,offset)
			})
		}
	}
	
	/**根据鼠标按下时的线位置调整线的节点*/
	function moveLine(linegroup,isend, offset){
		var line=linegroup.select("polyline").members[0]
		var list=line.array();
		var i=0;
		if(isend){
			i=list.value.length-1
		}
		var p=linegroup.attr("link-p-"+(isend?"e":"s")).split(",")
		list.value[i]=[parseFloat(p[0])+offset[0],parseFloat(p[1])+offset[1]];
		line.plot(list);
	}
	
	function setLineLinkSEOffest(linegroup,list){
		linegroup.attr("link-p-s",list.value[0].join(","))
		var last=list.value.length-1;
		linegroup.attr("link-p-e",list.value[last].join(","))
	}
	
	
	/**根据鼠标按下的线位置调整节点连线的路径（智能路径）*/
	function moveAutoLine(linegroup,isend, offset){
		var line=linegroup.select("polyline").members[0]
		var list=line.array();
		var i=0;
		if(isend){
			i=list.value.length-1
		}
		var p=linegroup.attr("link-p-"+(isend?"e":"s")).split(",")
		list.value[i]=[parseFloat(p[0])+offset[0],parseFloat(p[1])+offset[1]];
		var s_node=getNode(linegroup.attr("s_node_id"))
		var e_node=getNode(linegroup.attr("e_node_id"))
		var nodes=getCanvas().select(".node-group").members;
		var s_of={x:list.value[0][0],y:list.value[0][1]}
		var e_of={x:list.value[list.value.length-1][0],y:list.value[list.value.length-1][1]}
		list.value=initLinkPath(s_node,e_node,s_of,e_of,nodes)
		line.plot(list);
	}
	
	function move(node,offset){
		var p=getPanelOffset(offset)
		node.move(offset[0],offset[1]);
	}


	/**设置元素位置*/
	function setGroupOffset(ele,e){
		var flag=ele.getAttribute("drageable");
		if(flag=="true"){
			ele.setAttribute("transform","translate("+(e.offsetX-ele.getAttribute('width')/2)+","+(e.offsetY-ele.getAttribute('height')/2)+")")
		}
	}
	/**设置元素位置*/
	function setNodeOffset(ele,e){
		var flag=ele.getAttribute("drageable");
		if(flag=="true"){
			ele.setAttribute("x",e.offsetX-ele.getAttribute('width')/2)
			ele.setAttribute("y",e.offsetY-ele.getAttribute('height')/2)
		}
	}

	/**画连接线*/
	function drawLinks(param){
		var links=param.links
		var scl=getPanleScal();
		var nodes=getCanvas().select(".node-group").members;
		$.each(links,function(start,ends){
			//起始节点
			var startNode=getNode(start);
			var s_offset=getNodeGroupMidOffset(startNode,1,scl);
			//canvas.circle(10).cx(s_offset.x).cy(s_offset.y)
			
			for(var i in ends){
				var lk=ends[i];
				//console.log(lk)
				var endNode=getNode(lk.downNodeId)
				if(endNode){
					var se_offset=getNodeGroupMidOffset(endNode,0,scl);
					//canvas.circle(10).fill("none").stroke({width:1}).cx(se_offset.x).cy(se_offset.y)
					drawLinkIntoGroup(startNode,endNode,s_offset,se_offset,lk,nodes)
				}
			}
		})
	}
	
	function drawLinkIntoGroup(start,end,s_os,e_os,link,nodes){
		//确认连接线路径
		var path=	initLinkPath(start,end,s_os,e_os,nodes)
		var line =drawLine({
			path:path,
			link:link,
			event:{
				click:function(){
					console.log(this.attr("link"))
				}
			}
		})
		registNodeLink(start,line,true)
		registNodeLink(end,line,false)
	}
	
	function registNodeLink(node,line,flag){
		var id=line.attr("id")
		if(flag){
			line.attr("s_node_id",node.attr("id"))
		}else{
			line.attr("e_node_id",node.attr("id"))
		}
		var key="s_link_id";
		if(!flag){
			key="e_link_id"
		}
		var linkids=node.attr(key)
		if(linkids==undefined || linkids==''){
			linkids=id;
		}else{
			linkids+=','+id
		}
		node.attr(key,linkids)
	}
	
	//根据起始结束节点确认连接线路径
	function initLinkPath(start,end,s_os,e_os,nodes){
		var path=[[s_os.x,s_os.y],[e_os.x,e_os.y]]
		if(canvas_layout=='Horizontal'){
			//水平
		}else{
			var isLeft=path[1][0]>path[0][0]
			//垂直
			if(path[0][0]==path[1][0]){
				var cn=isCossNode(path,nodes)
				if(cn){
					path=getOutCossPathByNode(path,cn)
				}
			}else {
				var p1=[path[0][0],path[0][1]+10]
				var p2=[path[1][0],path[0][1]+10]
				var flag=path[0][1]>path[1][1]
				var cn=isCossNode([p2,path[1]],nodes)
				//console.log(isCossNode([p2,path[1]],nodes))
				if(cn){
					path=getOutCossPathByNode([path[0],p1,p2,path[1]],cn)
					if(flag){
						//console.log(path)
						//console.log(cn)
					}
				}else{
					path=[path[0],p1,p2,path[1]]
				}
//				if(flag){
//					console.log(path)
//					console.log(cn)
//				}
			}
		}
		return path;
	}
	
	function getOutCossPathByNode(path,node){
		if(canvas_layout=='Horizontal'){
			//水平
		}else{
			var flag=path[0][1]>path[path.length-1][1]
			if(flag){
				//console.log(path)
			}
			var fst=path[0][1]>path[1][1]?10:-10
			if(path.length==2){
				var p1=[path[0][0],path[0][1]+10]
				var p2=[path[0][0]+node.attr("gw"),path[0][1]+10]
				var p3=[path[0][0]+node.attr("gw"),path[1][1]+fst]
				var p4=[path[1][0],path[1][1]+fst]
				path=[path[0],p1,p2,p3,p4,path[1]]
			}else if(path.length==3 || path.length==4){
				var last=path.length-1
				var p1=[path[1][0]+node.attr("gw"),path[1][1]]
				var p2=[path[1][0]+node.attr("gw"),path[last][1]+fst]
				var p3=[path[last][0],path[last][1]+fst]
				path=[path[0],path[1],p1,p2,p3,path[last]]
			}
		}
		return path
		
	}
	
	/**(水平/垂直)两个点的连线是否进过流程节点*/
	function isCossNode(path,nodes){
//		console.log(path)
		var is_h=path[0][0]==path[1][0];
		var is_v=path[0][1]==path[1][1];
		var flag=path[0][1]>=path[1][1]
		//路径排序
		if(is_h){
			//水平
			if(path[0][1]>path[1][1]){
				path=[path[1],path[0]]
			}
		}
		if(is_v){
			//垂直
			if(path[0][0]>path[1][0]){
				path=[path[1],path[0]]
			}
		}
		if(is_h || is_v){
			var node;
			//console.log(path)
			$.each(nodes,function(i,n){
				var n_offset=getNodeGroupOffset(n)
				//console.log( n_offset.x+"-"+path[0][0] )
				//console.log(n_offset.x+n.attr("gw") +"-"+ path[0][0])
				if(is_h && n_offset.x<=path[0][0] && n_offset.x+n.attr("gw")>=path[0][0]){
					var max_l=(path[0][1]>path[1][1]?path[0][1]:path[1][1])
					var min_l=(path[0][1]<=path[1][1]?path[0][1]:path[1][1])
//					console.log(path)
//					console.log("max:"+max_l)
//					console.log("min:"+min_l)
//					console.log("y:"+n_offset.y)
//					console.log("y+h:"+( n_offset.y+n.attr("gh")))
					if(n_offset.y>=min_l && n_offset.y+n.attr("gh")<=max_l){
						node=n;
						return false;
					}
				}
				if(is_v && n_offset.y<path[0][1] && n_offset.y+n.attr("gh")>path[1][1]){
					var max_l=path[0][0]>path[1][0]?path[0][0]:path[1][0]
					var min_l=path[0][0]<=path[1][0]?path[0][0]:path[1][0]
					if(n_offset.x >= min_l && n_offset.x <= max_l){
//						console.log("max:"+max_l)
//						console.log("min:"+min_l)
//						console.log("y:"+n_offset.y)
						node=n;
						return false;
					}
				}
			})
			return node;
		}
	}
	
	/** site:0,in-mid;1,out-mid*/
	function getNodeGroupMidOffset(group,site,scl){
		//node group start offset
		var st_offset=getNodeGroupOffset(group)
		var gw=group.attr("gw");
		var gh=group.attr("gh");
//		console.log(gw+"-"+gh)
		if(canvas_layout=='Horizontal'){
			//水平
		}else{
			//垂直
			if(site==1){
				st_offset.y+=gh/scl.y
			}
			st_offset.x+=gw/(2*scl.x)
		}
		
		return st_offset ;
	}
	

	/**坐标是否在给定元素的范围内*/
	function isInNode(node, e){
		if(node){
			var scl=getPanleScal();
//			console.log(scl)
			var x1=node.x()
			var y1=node.y()
			var x2=x1+node.width();
			var y2=y1+node.height();
			
//			console.log(x1+"-"+x2+"   "+y1+"-"+y2)
//			console.log(e.offsetX+"-"+e.offsetY)
			return (e.offsetX*scl.x>=x1 && e.offsetX*scl.x<=x2) &&(e.offsetY*scl.y>=y1 && e.offsetY*scl.y<=y2)
		}
	}

	/**线段结束位置变更*/
	function changeLineEnd(linegroup,p1){
		p1=getPanelOffset(p1)
		var line=linegroup.select("polyline").members[0]
		var list=line.array();
		list.value[1]=p1;
		line.plot(list);
	}

	/**多点线段，点位置移动*/
	function changeLinePointer(linegroup,id,p){
		var line=linegroup.select("polyline").members[0]
		var point=linegroup.select("#"+id).members[0]
		var list=line.array().value;
		//指定位置替换
		list[parseInt(point.attr("line_index"))]=p;
		line.plot(list);
		point.cx(p[0]).cy(p[1])
	}

	/**线段内加点*/
	function addLinePointer(linegroup,p1){
		var line=linegroup.select("polyline").members[0]
		var list=line.array().value;
		var i=0
		for(let j in list){
			if(p1[0]>list[j][0] || p1[1]>list[j][1]){
				i=parseInt(j);
			}else{
				break
			}
		}
		if(i<1){
			i=1;
		}
		//在指定索引添加位置
		list.splice(i, 0, p1);
		addLinePoniterAction(linegroup,i,p1)
		line.plot(list);
	}

	function addLinePoniterAction(linegroup,i,p){
		var point=linegroup.circle(10).cx(p[0]).cy(p[1])
		point.attr("line_index",i);
	}


	/**划线*/
	function drawLine(param){
		var path=param.path
		var link=param.link
//		path[0]=getPanelOffset(path[0])
//		path[1]=getPanelOffset(path[1])
		var linegroup=getCanvas().group().attr("class","link-group line-"+(link==undefined?linendex:"link-"+link.id));
		if(link.upNodeId){
			linegroup.attr("link_from",link.upNodeId+'-'+link.downNodeId)
		}
		var line=linegroup.polyline(path).fill('none').stroke({ width: 2 });
		
		if(path.lenght>2){
			for(var i=1;i<path.lenght;i++){
				addLinePoniterAction(linegroup,i,path[i])
			}
		}
		line.marker("start",line_start)
		
		if(link){
			linegroup.attr("link",JSON.stringify(link))
			if(link.condition!=undefined && link.condition!=''){
				line.marker("end", 45,link.condition.length*6, function(l) {
					  l.text(link.condition).fill("red").style({"font-size":"6px"}).transform({ rotation: 270 }).x(2)
				})
			}
		}else{
			line.marker("end",line_end)
		}
		line.style("cursor", "pointer")
		if(param.event){
			for( var key in param.event){
				linegroup.on(key,param.event[key])
			}
		}
		line.on("mousedown",function(e){
			if(getContent() && getContent().line){
				addLinePointer(linegroup,getPanelOffset([e.offsetX,e.offsetY]))
			}
		})
		
		var line_group_id=linegroup.attr("id");
		$("#"+line_group_id).on("mousedown","circle",function(e){
			if(getContent() && getContent().pointer){
				var _this=this
				getCanvas().off("mousemove");
				getCanvas().on("mousemove",function(e1){
					if(getContent().pointer){
						var dr=e1.offsetX-e.offsetX>0
						changeLinePointer(linegroup,$(_this).attr("id"),getPanelOffset([e1.offsetX,e1.offsetY]))
					}
				})
			}
		})
		$("#"+line_group_id).on("mouseup","circle",function(e){
			getCanvas().off("mousemove");
		})
		linegroup.on("mouseup",function(){
			getCanvas().off("mousemove");
		})
		setLineLinkSEOffest(linegroup,line.array())
		return linegroup;
	}
	
	

	/**初始化画板*/
	function initCanvas(ele){
		pw=$("#"+ele).width()
		ph=$("#"+ele).height()
		var canv=SVG(ele).size('100%','100%');
		canv.attr("viewBox","0 0 "+pw+" "+ph) 
		//延时设置canvas的viewbox( 该处涉及视图的缩放移动，以及鼠标拖拽准确性问题，必须保持 )
		window.setTimeout(function(){ 
			pw=$("#"+ele).width()
			ph=$("#"+ele).height()
			canv.attr("viewBox","0 0 "+pw+" "+ph) 
		},500)
		line_start=canv.marker(10,10,function(n){
			n.attr("refX",(5+2))
			n.circle(4).cx(5).cy(5).fill("none")
			.stroke({ width: 0.5 ,color:"green"});
		})
		line_mid=canv.marker(10,10,function(n){
			n.path("M0,0 L0,10 L10,8 z");
		})
		line_end=canv.marker(8,6,function(n){
			n.attr("refX",7)
			n.path("M0,0 L8,3 L0,6").stroke({ width: 0.5 ,color:"green"}).fill("none");
		})
		$$.extend({canvas:canv})
		$("#"+ele).resize(function(){
			canv.attr("viewBox","0 0 "+$(this).width()+" "+$(this).height())
			$$.extend({canvas:canvs})
		})
		return canv;
	}

	function setViewBox(viewBox){
		return getCanvas().attr("viewBox",viewBox)
	}
	function getViewBox(){
		return getCanvas().attr("viewBox").split(" ");
	}
	function zoom(offset){
		var viewBox=getViewBox();
		viewBox[0]=offset[0];
		viewBox[1]=offset[1];
		getCanvas().attr("viewBox",viewBox.join(" "))
	}
	function getZoomOffset(){
		var viewBox=getViewBox();
		return [viewBox[0],viewBox[1]]
	}
	function getZoomSize(){
		var viewBox=getViewBox();
		return [viewBox[2],viewBox[3]]
	}
	function getCanvasParent(){
		return getCanvas().parent();
	}
	function getPanleScal(){
		var p=$(getCanvasParent());
		var vsize=getZoomSize();
		return {x:vsize[0]/p.width(),y:vsize[1]/p.height()}
	}
	function getPanelOffset(p){
		var scl=getPanleScal();
		p[0]=p[0]*scl.x
		p[1]=p[1]*scl.y
		return p;
	}

	function scale(scl){
		var p=$(getCanvasParent());
		var viewBox=getViewBox();
		var v=[]
		v[0]=viewBox[0]
		v[1]=viewBox[1]
		v[2]=p.width()*scl;
		v[3]=p.height()*scl;
		getCanvas().attr("viewBox",v.join(" "))
	}
	
	function select(cls){
		return getCanvas().select(cls)
	}
	
	
	$$.fn.extend({
		select:select,
		getCanvas:getCanvas,
		move:move,
		drageGroup:drageGroup,
		zoom:zoom,
		scale:scale,
		getZoomOffset:getZoomOffset,
		getViewBox:getViewBox,
		setViewBox:setViewBox,
		initCanvas:initCanvas,
		getPanelOffset:getPanelOffset,
		getNodeGroupByType:getNodeGroupByType,
		changeLineEnd:changeLineEnd,
		isInNode:isInNode,
		initContent:initContent,
		setContent:setContent,
		getContent:getContent,
		loadNode:loadNode,
		drawLinks:drawLinks,
		drawLine:drawLine
	})
//	console.log($$)
})(jQuery,$draw)