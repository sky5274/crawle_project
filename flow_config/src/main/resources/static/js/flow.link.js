(function($,doc){
	var flow=function(tar,params){
		this.target=tar;
		if($("style#flow_css_content").length==0){
			$("body").append('<style id="flow_csss_content" type="text/css">'+
			'.flow_time{font-size: 11px; float:left}'+
			'.flow_head,.flow_content{clear: both; width:100%}'+
			'.flow_title{margin-left:5px}'+
			'.flow_content{min-height:20px;}'+
			'.flow_node{width: 100%; position: relative; padding: 3px 10px 3px 26px; clear: both;}'+
			'.flow_node .flow_line{height: 50%; width: 2px;  border-left: 2px #00FFFF solid; position: absolute; top: 0%; left: 11px;}'+
			'.flow_node .flow_line{top:6.5px}'+
			'.flow_node .flow_line:last-child{top: calc(50% + 6.5px)}'+
			'.flow_node:first-child .flow_line:last-child{display: block;}'+
			'.flow_node:last-child .flow_line{display: none;}'+
			'.flow_lazy .flow_line{ border-left: 2px #4682B4 dotted; height: calc(50% - 2px);}'+
			'.flow_pointer{ position:absolute; left: 5px; width: 14px; height: 14px;  z-index: 1;  background: white; border-radius: 100%; border: 1px solid #DCDCDC;}'+
			'.flow_pointer:after{content:""; position:  absolute; left: 2px; top: 2px;  width: 8px; height: 8px; background: #DCDCDC; border-radius: 100%; border: 1px #DCDCDC solid;}'+
			'.flow_active .flow_pointer{border-color: #87CEEB !important; }'+
			'.flow_active .flow_pointer:after{border-color: #87CEEB !important;top: 2px; background-color: #87CEEB;}'+
			'.flow-point-mid .flow_pointer{top:calc(50% - 6.5px); }'+
			'.flow-point-mid .flow_node:first-child .flow_line:nth-last-child(2) {display: none;}'+
			'.flow-point-mid .flow_node .flow_line:first{top: 0px;}'+
			'.flow-point-mid .flow_node:last-child .flow_line:nth-last-child(2){display: block;}'+
			'</style>')
		}
		this.settings={
			method:{},
			node_key:{time:'setTime',title:'setTitle',content:"setContent",active:"setActive"}
		},
		console.log("flow init ")
		if(params.extend !=undefined){
			this.extend(params.extend)
		}
		params.extend=undefined
		this.settings.param={
			align:'top',
			'title-align':'right',
		}
		this.settings.param=$.extend(this.settings.param,params)
		//加载数据
		this.load(this.settings.param.data)
		this.regist()
	}
	flow.prototype={
		regist:function(){
			console.log("regist flow")
			$(this.target).data("flow_data",this)
		},
		clear:function(){
			$(this.target).data("flow_data",undefined)
		},
		load:function(data){
			var _this=this;
			this.target.html("")
			this.target.addClass("flow_panel flow-point-"+this.settings.param.align)
			$.each(data,function(i,d){
				_this.addNode(d,i)
			})
			this.target.find(".flow_node").each(function(i,n){
				if($(this).attr("class").indexOf("flow_lazy")>-1){
					if(i>0){
						var cond=_this.settings.param.align=='mid'?':last-child':''
						$(this).parent().find(".flow_node").eq(i-1)
						.find(".flow_line"+cond)
						.css({
							'border-left':'2px #4682B4 dotted',
							"height":"calc(50% - 2px)"
						})
					}
				}
			})
			
			if(this.settings.param.init){
				this.settings.param.init(this.target)
			}
			
		},
		addNode:function(node,index){
			var temp=this.get("convert",node);
			if(temp){
				node=temp;
			}
			if(node.time==undefined && node.content==undefined){
				return;
			}
			var html=this.getNodeContent(node,index)
			if(html){
				this.target.append(html)
				if(this.settings.param.event){
					for(var key in this.settings.param.event){
						$(html).unbind(key)
						$(html).bind(key,this.settings.param.event[key])
					}
				}
			}
		},
		getNodeContent:function(node,index){
			if(node.time==undefined){
				return;
			}
			if(node.time instanceof Object){
				node.time=node.time.toLocaleString()
			}if (typeof(node.time)=='number'){
				node.time=new Date(node.time).toLocaleString()
			}
			node=this.extendNodeData(node,index);
			return this.getNodeDataContentHtml(node,index)
		},
		/**获取节点内容的html*/
		getNodeDataContentHtml:function(node,index){
			var title_align=this.settings.param['title-align'];
			var ele_n=$('<div class="flow_node"></div>')
				ele_n.addClass(node.active==undefined || node.active?"flow_active":"flow_lazy")
				ele_n.append('<div class="flow_head"><span class="flow_time">'+node.time+'</span>'+(node.title?'<span class="flow_title pull-'+title_align+'">'+node.title+'</span>':'')+'<span class="flow_pointer"></span><div class="flow_line"></div><div class="flow_line"></div></div>')
				if(node.content){
					var con=$('<div class="flow_content"></div>')
					con.append(node.content);
					ele_n.append(con)
				}
				ele_n.data("node",node)
			return ele_n;
		},
		/**如果接到中的data数据不为null,对节点默认数据进行拓展*/
		extendNodeData:function(node,index){
			if(node.data!=undefined){
				for(var key in this.settings.node_key){
					if(node[key]==undefined){
						node[key]=this.get(this.settings.node_key[key],node.data,index)
					}
				}
			}
			return node;
		},
		/**注册flow的方法*/
		extend:function(method){
			this.settings.method=$.extend(this.settings.method,method)
		},
		/**注册事件与调用*/
		on:function(key,...param){
			if(param  instanceof Function){
				this.settings.method[key]=param
				this.regist()
			}else{
				var func=this.settings.method[key]
				if(func){
					return func.apply(null,param==undefined ?this:param)
				}
			}
		},
		/**调用事件,使用参数的数组形式，调用方法的apply；无对象+参数*/
		get:function(key,...param){
			var func=this.settings.method[key]
			if(func){
				//数组展开传参
				return func.apply(null,param)
			}
			func=this[key]
			if(func){
				return func.apply(null,param)
			}
		}
	}
	
	$.fn.extend({
		flow:function(param){
			var flow_panel=$(this).data("flow_data");
			if(flow_panel==undefined || param!=undefined){
				flow_panel=new flow($(this),param)
			}
			return flow_panel;
		}
	})
})(jQuery,document)