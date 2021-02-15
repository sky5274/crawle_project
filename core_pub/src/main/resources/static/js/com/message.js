;(function($,win,doc){
	var Message=function(cond,parent){
		this.parent=parent!=undefined ?$(parent):getFullParent();
		this.settings={
				src:'',
				type:"alert",
				title:"提示",
				enSure:'确认',
				cancel:'取消',
				hidden:true,
				drag:false,
				closed:false,
				top:30,
				con:"",
				init:function(){},  //页面初始化事件
				func:function(){}
		}
		if(cond!=null || cons!=""){
			$.extend(this.settings,cond)
		}
		var id="Modal_"+Math.round(Math.random()*100);
		this.settings.eleId=id;
		switch (this.settings.type) {
		case "alert":
			var _this=this;
			var dialog=$(this.getModal(id));
			this.initModalEvent(dialog,id)
			if(this.settings.closed){
				window.setTimeout(function(){
					$(dialog).modal('hide')
				},3000)
			}
			break;
		case "confrim":
			var dialog=this.getModal(id,1);
			this.initModalEvent(dialog,id)
			break;
		default:
			break;
		}
		this.initEVENT()
	}
	Message.prototype={
			initModalEvent:function(dialog,id){
				var _this=this;
				$(dialog).find(".modal-footer button[type=button]").click(function(){
					var flag
					try {
						var win=dialog
						if(_this.settings.src.length>0){
							win=dialog.find("#iframe_"+id).contents().find("body")
							if(win.length==0){
								win=dialog
							}
						}
						flag=_this.settings.func($(this).data("type"),win)
					} catch (e) {
						console.error(e)
						throw 'dialog err'
					}
					flag= flag==undefined?true:flag
					if(flag){
						$(dialog).modal('toggle')
					}
				})
				$(dialog).modal({backdrop:false,show:true});
				$(dialog).on('hide.bs.modal',function(){
					$(this).remove()
				})
				if(this.settings.drag){
					$(dialog).draggable();//为模态对话框添加拖拽
					$(dialog).css("overflow", "hidden");//禁止模态对话框的半透明背景滚动
				}
			},
			initModalEvn:function(dialog,id){
				//页面初始化事件
				var _this=this;
				this.parent.find("#"+id).modal('toggle')
				var H=$(_this.parent).height()
				window.setTimeout(function(){
					if(self!=top){
						$(_this.parent).children(".modal-backdrop").remove();
					}
					var h=$(dialog).find(".modal-body").height()
					var con_w=$(dialog).find(".modal-content").width();
					$(dialog).find(".modal-content").css("margin-top",(H-h)/4+"px")
				},500)
				if(this.settings.width){
					//根据百分比适配宽度
					if((this.settings.width+"").indexOf('%')>-1){
						window.setTimeout(function(){
							$(dialog).find(".modal-dialog").css("width", "100%")
							$(dialog).find(".modal-content").css({"width":_this.settings.width,"margin-left":((100-parseInt(_this.settings.width))/2)+"%"})
						},300)
					}else{
						$(dialog).find(".modal-content").css({"width":parseInt(this.settings.width)+"px","margin-left":(600-parseInt(this.settings.width))/2+"px"})
					}
				}
				$(dialog).find(".modal-body").css({"max-height":"500px","overflow-y": "auto"})
				$(dialog).find(".modal-body").on('resize',function(){
					var H=$(_this.parent).height()
					var h=$(this).height()
					$(dialog).find(".modal-content").css("margin-top",(H-h)/4+"px")
				})
				this.settings.init(dialog);
			},
			stop:function(){
				this.settings.flag=false;
			},
			getModal:function(id,i){
				var con='<div class="dialog modal fade" id="'+id+'" tabindex="-1" role="dialog" aria-hidden="'+this.settings.hidden+'" >'+
				'<div class="modal-backdrop" style="opacity:0.5;z-index:-1"></div>'+
				'<div class="modal-dialog">'+
				' <div class="modal-content">'+
				' <div class="modal-header">'+
				'     <button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
				'    <h4 class="modal-title" id="myModalLabel">'+this.settings.title+'</h4>'+
				' </div>'+
				' <div id="modal_body_content" class="modal-body"></div>'+
					'<div class="modal-footer">';
					if(i==1){
						con+='<button type="button" data-type="cancle" class="btn btn-default" data-dismiss="modal">'+this.settings.cancel+'</button>'
					}
					con+='   <button type="button" data-type="sure" class="btn btn-primary">'+this.settings.enSure+'</button>'+
					'</div>'+
				' </div>'+
				'</div>'+
				'</div>'
				var dialog=$(con)
				this.settings.content=dialog
				dialog.find("#modal_body_content").append(this.settings.con)
				var _this=this;
				this.parent.append(dialog);
				if(this.parent !=$("body")){
					setTimeout(function(){if(self!=top){$("body .modal-backdrop").remove()}},180)
				}
					if(this.settings.src.length>0){
//						$.loading().show()
						dialog.find(".modal-body").css("height",this.settings.height?this.settings.height:"500px")
						dialog.find(".modal-body").append("<iframe id='iframe_"+id+"' frameborder='no' border='0'  style='width:100%;height:98%'></iframe>")
						var iframe=dialog.find(".modal-body iframe")
						if(this.settings.src.indexOf("://")<0){
							try {
								this.settings.src=API.config.baseUrl+this.settings.src
							} catch (e) {
							}
						}
						iframe.attr("src",this.settings.src)
						//快捷调用
						this.settings.callFrame=function(key,...param){
<<<<<<< HEAD
							var callfunc=dialog.find("iframe").eq(0)[0].contentWindow[key];
							if(callfunc){
								return callfunc.apply(null,param);
							}
=======
							return callFrameEvent(dialog.find("iframe"),key,param);
>>>>>>> refs/remotes/origin/dev_base
						}
						_this.initModalEvn(dialog,id);
//						$(iframe).load(function() { 
//							console.log("page load")
//							$.loading().close();
//							if($(this).find("body").html().length>0){
//								_this.initModalEvn(dialog,id);
//							}else{
//								$(dialog).find(".close").click();
//								$.diaLog({con:"dialog load page error! src:"+_this.settings.src})
//							}
//			            });
//						dialog.find(".modal-body").load(this.settings.src,"",function(){
//							$.loading().close();
//							if($(dialog).find(".modal-body").html().length>0){
//								_this.initModalEvn($(dialog),id);
//							}else{
//								$(dialog).find(".close").click();
//								$.diaLog({con:"dialog load page error! src:"+_this.settings.src})
//							}
//						})
					}else{
						this.initModalEvn(dialog,id)
					}
				return dialog;
			},
			start:function(){
				this.settings.flag=true;
			},
			getDom:function(){
				return $('#'+this.settings.eleId)
			},
			getContent:function(){
				return this.content;
			},
			callFrame:function(key,...param){
<<<<<<< HEAD
				var callfunc=$(this.content).find("iframe")[0].contentWindow[key];
				if(callfunc){
					return callfunc.apply(null,param);
				}
=======
				return callFrameEvent($(this.content).find("iframe"),key,param)
>>>>>>> refs/remotes/origin/dev_base
			},
			initEVENT:function(){
				/**默认事件*/
				this.settings.getDom=function(){
					return $('#'+this.eleId)
				}
				this.settings.getContent=function(){
					return this.content;
				}
			}
	}
	var loading=function(tar){
		var is_body=$(tar)[0].nodeName=='BODY'
		this.parent=$(tar)
		if(this.parent.find(".loader").length>0){
			return this.parent.data("load");
		}
		this.load=$('<div id="loading" class="loader">'+
				'<div class="lay"></div>'+
		        '<div class="loader-inner line-spin-fade-loader">'+
		          '<div></div>'+'<div></div>'+'<div></div>'+
		          '<div></div>'+'<div></div>'+'<div></div>'+
		          '<div></div>'+'<div></div>'+
		        '</div>'+
		      '</div>');
		
		if(!is_body){
			//非body下，lay去除，切load颜色为black
			this.load.find(".lay").remove()
			this.parent.css({position:'relative'})
			this.load.find(".line-spin-fade-loader").children().css({
				'background-color':'black',
				'opacity':'0.8'
			})
		}else{
			this.load.find(".lay").css({
				'display':'block',
				'z-index':-1,
			    'background-color': 'black',
			    'opacity': '0.3',
			    'width': '100%',
			    'height': '100%',
			    'position': is_body ?'fixed':'absolute',
			    'top': is_body ? 0 :'-50%',
			    'left': is_body ? 0 :'-50%',
			})
		}
		this.parent.append(this.load)
		this.parent.data("load",this)
		this.load.css({
			position:'absolute',
			'top':'50%',
			'left':'50%',
			'z-index':'10000',
		})
	}
	loading.prototype={
			show:function(){
				this.parent.find("#loading").show()
			},
			close:function(){
				this.parent.find("#loading").remove();
			},
			remove:function(){
				this.parent.find("#loading").remove();
			},
			getParent:function(){
				if(self!=top){
					this.parent=$("body",window.parent.top.document)
					
				}else{
					this.parent=$("body")
				}
			}
	}
	
	var Load=function(param){
		this.settings={
				src:'',
				type:"alert",
				hidden:true,
				drag:false,
				closed:false,
				top:30,
				con:"",
				init:function(){},  //页面初始化事件
				func:function(){}
		}
		if(param!=null || cons!=""){
			$.extend(this.settings,param)
		}
		this.parent=getFullParent();
		var id=param.type+"_"+Math.round(Math.random()*100);
		var con='<div class="dialog modal fade" id="'+id+'" tabindex="-1" role="dialog" aria-hidden="'+this.settings.hidden+'" >'+
			'<div class="modal-backdrop" style="opacity:0.5;z-index:-1"></div>'+
			'<div class="modal-dialog">'+
			' <div class="modal-content">'+
			' <div class="modal-header">'+
			'     <button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
			' </div>'+
			' <div id="modal_body_content" class="modal-body">'+'</div>'+
			'</div>'+
		'</div>'
		this.eleId=id;
		this.ele=$(con)
		this.ele.attr("id",id)
		this.showLoad();
	}
	Load.prototype={
			initParam:function(param){
				if(param!=null ){
					$.extend(this.settings,param)
				}
			},
			showLoad:function(){
				if(this.settings.src.length>0){
					this.ele.find("#modal_body_content").html("<iframe id='iframe_"+this.eleId+"' frameborder='no' border='0'  style='width:100%;height:98%'></iframe>")
					var iframe=this.ele.find("#modal_body_content iframe")
					if(this.settings.src.indexOf("://")<0){
						try {
							this.settings.src=API.config.baseUrl+this.settings.src
						} catch (e) {
						}
					}
					iframe.attr("src",this.settings.src)
					//快捷调用
					this.settings.callFrame=function(key,...param){
<<<<<<< HEAD
						var callfunc=iframe.eq(0)[0].contentWindow[key];
						if(callfunc){
							return callfunc.apply(null,param);
						}
					}
				}else{
					this.ele.find("#modal_body_content").html(this.settings.con)
				}
				this.parent.append(this.ele)
				this.initModalEvn(this.ele,this.eleId);
				this.initModalEvent(this.ele,this.eleId);
				$(this.ele).modal({backdrop:false,show:true});
			},
			hide:function(){
				$(this.ele).modal({backdrop:false,show:false});
			},
			initModalEvent:function(dialog,id){
				var _this=this;
				$(dialog).modal({backdrop:false,show:true});
				$(dialog).on('hide.bs.modal',function(){
					$(this).remove()
				})
				if(this.settings.drag){
					$(dialog).draggable();//为模态对话框添加拖拽
					$(dialog).css("overflow", "hidden");//禁止模态对话框的半透明背景滚动
				}
//				if(this.settings.init){
//					window.setTimeout(function(){
//						_this.settings.init(_this.ele)
//					},500)
//				}
			},
			initModalEvn:function(dialog,id){
				//页面初始化事件
				var _this=this;
				this.parent.find("#"+id).modal('toggle')
				var H=$(_this.parent).height()
				window.setTimeout(function(){
					if(self!=top){
						$(_this.parent).children(".modal-backdrop").remove();
					}
					var h=$(dialog).find(".modal-body").height()
					var con_w=$(dialog).find(".modal-content").width();
					//$(dialog).find(".modal-content").css("margin-top",(H-h)/4+"px")
				},500)
				if(this.settings.width){
					//根据百分比适配宽度
					if((this.settings.width+"").indexOf('%')>-1){
						window.setTimeout(function(){
							$(dialog).find(".modal-dialog").css("width", parseInt(_this.settings.width)+"%")
						},300)
					}else{
						$(dialog).find(".modal-dialog").css({"width":parseInt(this.settings.width)+"px"})
					}
				}
				if(this.settings.height){
					if((this.settings.height+"").indexOf('%')>-1){
						$(dialog).find(".modal-dialog").css({"height":parseInt(this.settings.height)+'%',"overflow-y": "auto"})
					}else{
						$(dialog).find(".modal-dialog").css({"height":parseInt(this.settings.height)+"px","overflow-y": "auto"})
						
					}
				}else{
					$(dialog).find(".modal-dialog").css({"max-height":"500px","overflow-y": "auto"})
				}
				$(dialog).find(".modal-content").css({"height":'100%'})
				$(dialog).find(".modal-body").css({"height":'calc( 100% - 33px)'})
				
				if(this.settings.height && (this.settings.height+"").indexOf('100%')<0){
					$(dialog).find(".modal-content").resize(function(){
						var H=$(_this.parent).height()
						var h=$(this).height()
						$(dialog).css("margin-top",(H-h)/4+"px")
					})
				}else{
					$(dialog).find('.modal-dialog').css('margin','0px')
					window.setTimeout(function(){
						$(dialog).css('padding','0px !important')
						$(dialog).css('padding-left','0px !important')
						$(dialog).css('margin','0px !important')
					},600)
				}
				
				this.settings.init(dialog);
			},
			stop:function(){
				this.settings.flag=false;
			},
=======
						return callFrameEvent(iframe,key,param)
					}
				}else{
					this.ele.find("#modal_body_content").html(this.settings.con)
				}
				this.parent.append(this.ele)
				this.initModalEvn(this.ele,this.eleId);
				this.initModalEvent(this.ele,this.eleId);
				$(this.ele).modal({backdrop:false,show:true});
			},
			hide:function(){
				$(this.ele).modal({backdrop:false,show:false});
			},
			initModalEvent:function(dialog,id){
				var _this=this;
				$(dialog).modal({backdrop:false,show:true});
				$(dialog).on('hide.bs.modal',function(){
					$(this).remove()
				})
				if(this.settings.drag){
					$(dialog).draggable();//为模态对话框添加拖拽
					$(dialog).css("overflow", "hidden");//禁止模态对话框的半透明背景滚动
				}
//				if(this.settings.init){
//					window.setTimeout(function(){
//						_this.settings.init(_this.ele)
//					},500)
//				}
			},
			initModalEvn:function(dialog,id){
				//页面初始化事件
				var _this=this;
				this.parent.find("#"+id).modal('toggle')
				var H=$(_this.parent).height()
				window.setTimeout(function(){
					if(self!=top){
						$(_this.parent).children(".modal-backdrop").remove();
					}
					var h=$(dialog).find(".modal-body").height()
					var con_w=$(dialog).find(".modal-content").width();
					//$(dialog).find(".modal-content").css("margin-top",(H-h)/4+"px")
				},500)
				if(this.settings.width){
					//根据百分比适配宽度
					if((this.settings.width+"").indexOf('%')>-1){
						window.setTimeout(function(){
							$(dialog).find(".modal-dialog").css("width", parseInt(_this.settings.width)+"%")
						},300)
					}else{
						$(dialog).find(".modal-dialog").css({"width":parseInt(this.settings.width)+"px"})
					}
				}
				if(this.settings.height){
					if((this.settings.height+"").indexOf('%')>-1){
						$(dialog).find(".modal-dialog").css({"height":parseInt(this.settings.height)+'%',"overflow-y": "auto"})
					}else{
						$(dialog).find(".modal-dialog").css({"height":parseInt(this.settings.height)+"px","overflow-y": "auto"})
						
					}
				}else{
					$(dialog).find(".modal-dialog").css({"max-height":"500px","overflow-y": "auto"})
				}
				$(dialog).find(".modal-content").css({"height":'100%'})
				$(dialog).find(".modal-body").css({"height":'calc( 100% - 33px)'})
				
				if(this.settings.height && (this.settings.height+"").indexOf('100%')<0){
					$(dialog).find(".modal-content").resize(function(){
						var H=$(_this.parent).height()
						var h=$(this).height()
						$(dialog).css("margin-top",(H-h)/4+"px")
					})
				}else{
					$(dialog).find('.modal-dialog').css('margin','0px')
					window.setTimeout(function(){
						$(dialog).css('padding','0px !important')
						$(dialog).css('padding-left','0px !important')
						$(dialog).css('margin','0px !important')
					},600)
				}
				
				this.settings.init(dialog);
			},
			stop:function(){
				this.settings.flag=false;
			},
	}
	
	function callFrameEvent(frame,key,param){
		var callfunc=frame.eq(0)[0].contentWindow[key];
		if(callfunc){
			return callfunc.apply(null,param);
		}
>>>>>>> refs/remotes/origin/dev_base
	}

	var panel=function(param){
		var option={
				width:'800',
				height:500,
				bg_color:"white",
				con:'',
				hidden:false,
				type:"panel"
		}
		if(param!=null || cons!=""){
			$.extend(option,param)
		}
		$(".modal_panel").remove();
		this.panel=new Load(option);
		var _this=this;
		$(".modal_panel .close").click(function(){
			_this.hide();
		})
	}

	panel.prototype={
			show:function(){
				this.panel.showLoad()
			},
			hide:function(){
				this.panel.hide();
			},
			reload:function(param){
				$(this.panel.parent).find("#"+this.panel.eleId).html(param.con).fadeIn()
				this.panel.initParam(param)
				this.panel.parent=getFullParent();
				this.show();
			}
	}
	var showImg=function(img){
		var con='<div class="img-show-load"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><img src="'+img+'"></div>';
		var option={
				width:800,
				height:500,
				hidden:false,
				bg_color:"white",
				con:con,
				type:"img_show"
		}
		$(".modal_img_show").remove();
		this.panel=new Load(option);
		this.initEvn();
	}

	showImg.prototype={
			initEvn:function(){
				//初始化显示图片环境
				var p=this.getPanel();
				var close=p.find(".close");
				$(p).find(".img-show-load").css({
					"width":"100%",
					"height":"100%",
					position:"relative"
				})
				$(close).css({
					position:"absolute",
					"top":"5%",
					"right":"5%",
					"z-index":3
				})
				$(p).find("img").css({
					position:"absolute",
					"top":0,
					"left":"0",
					"width":"100%",
					"height":"100%"
				})
				this.initEvent();
			},
			initEvent:function(){
				//初始化图片显示事件
				var _this=this;
				this.getPanel().find(".close").click(function(){
					_this.hide();
				})
			},
			show:function(){
				this.panel.showLoad()
			},
			hide:function(){
				this.panel.hide();
			},
			getPanel:function(){
				return $(this.panel.LOAD);
			},
			reload:function(img){
				this.getPanel().find("img").attr("src",img)
				this.show();
			}
	}
	var Alert=function(param,parent){
		this.parent=parent!=undefined ?$(parent):$("body",window.top.document)
		this.settings={
				type:"success",
				title:undefined,
				content:"",
				timeout:undefined,
				closed:true,
				init:function(ele){},
				action:function(){}
		}
		this.type={
			warn:{title:"警告!",type:"alert-warning",timeout:3000},	
			success:{title:"成功",type:"alert-success",timeout:2000},	
		}
		this.settings=$.extend(this.settings,param)
		var t_p=this.type[this.settings.type];
		if(t_p==undefined){
			new Message({con:"未找到系统定义参数：type="+this.settings.type})
		}else{
			var flag=this.settings.content.indexOf('<')>-1
			var con=flag?this.settings.content:('<strong>'+(this.settings.title==undefined?t_p.title:this.settings.title)+'</strong>'+this.settings.content)
			var html=$('<div class="alert-item alert '+t_p.type+'" style="margin-bottom:0px"><a href="#" class="close" data-dismiss="alert">&times;</a>'+con+'</div>');
			var group=this.parent.find("div.alert-group")
			if(group.length==0){
				group=$("<div class='alert-group'></div>")
				this.parent.append(group)
				group.css({"position":"absolute","width":"100%","top":"0px","left":'1px',"z-index":100000})
			}
			group.append(html);
			this.settings.init(html);
			var _this=this;
			html.bind("close.bs.alert",function(){_this.settings.action();})
			if(this.settings.closed){
				window.setTimeout(function(){
					html.remove();
				},this.settings.timeout==undefined?t_p.timeout:this.settings.timeout)
			}
		}
	}
	
	$.fn.extend({  
		diaLog:function(cond){  
			if(cond!=null || cond!=""){
				$.extend(cond,{con:$(this).html()})
				$(this).hide()
				return new Message(cond);
			}
		}
	});  

	/*获取最顶层的iframe parent*/
	function getInitParent(parent){
		if(parent==undefined){
			parent=window.parent;
		}
		if(parent=parent.parent){
			return parent;
		}else{
			return getInitParent(parent.parent)
		}
	}
	
	/*考虑全屏上级元素*/
	function getFullParent(){
		var ele;
		try {
			ele=eval('FullScreenEle()')
		} catch (e) {
		}
		return ele?$(ele):$(getInitParent().document.body)
	}
	
	
	$.extend({
		diaLog:function(cond){
			return new Message(cond)
		},
		alert:function(con){
			return new Alert(con)
		},
		valid:function(param){
			var obj={}
			var flag=true;
			$(param.ele).find(".need_inp").removeClass("need_inp")
			$(param.ele).find(param.tar==undefined?"input":param.tar).each(function(i,ele){
				if($(ele).val()!="" || (param.ext && $.inArray(param.ext,$(ele).attr("name"))>-1)){
					obj[$(ele).attr("name")]=$(ele).val()
				}else{
					$(ele).parent().addClass("need_inp")
					$.alert({content:$(ele).parent().text()+"不能为空",type:"warn"})
					flag=false
				}
			})
			return flag?obj:undefined
		},
		loading:function(tar){
			var parent;
			var body= tar==undefined || $(tar).length==0 ?'body':$(tar)
			if(tar==undefined || self!=top || eval('FullScreenEle()') !=undefined){
				parent=$(body,window.top.document)
			}else{
				parent=$(body)
			}
			if(parent!=undefined){
				var load=parent.find("#loading").data("load")
				if(load==undefined){
					load=new loading($(parent));
				}
				return load;
			}else{
				console.error("could not find parent")
			}
		},
		panel:function(param){
			var p;
			var body=$("body",window.top.document)
			if($(body).data("panel")==undefined){
				p=new panel(param)
				$(body).data("panel",p)
			}else{
				p=$(body).data("panel")
				p.reload(param)
			}
			return p;
		},
		showImg:function(src){
			var img
			var body=$("body",window.top.document)
			if($(body).data("showImg")==undefined){
				img=new showImg(src)
				$(body).data("showImg",img)
			}else{
				img=$(body).data("showImg");
				img.reload(src)
			}
			return img;
		}

	})
})(jQuery,window,document);

(function ($, window, undefined) {
    var elems = $([]),
        jq_resize = $.resize = $.extend($.resize, {}),
        timeout_id,
        str_setTimeout = 'setTimeout',
        str_resize = 'resize',
        str_data = str_resize + '-special-event',
        str_delay = 'delay',
        str_throttle = 'throttleWindow';
    jq_resize[str_delay] = 250;
    jq_resize[str_throttle] = true;
    $.event.special[str_resize] = {
        setup: function () {
            if (!jq_resize[str_throttle] && this[str_setTimeout]) {
                return false;
            }
            var elem = $(this);
            elems = elems.add(elem);
            $.data(this, str_data, {
                w: elem.width(),
                h: elem.height()
            });
            if (elems.length === 1) {
                loopy();
            }
        },
        teardown: function () {
            if (!jq_resize[str_throttle] && this[str_setTimeout]) {
                return false;
            }
            var elem = $(this);
            elems = elems.not(elem);
            elem.removeData(str_data);
            if (!elems.length) {
                clearTimeout(timeout_id);
            }
        },
        add: function (handleObj) {
            if (!jq_resize[str_throttle] && this[str_setTimeout]) {
                return false;
            }
            var old_handler;

            function new_handler(e, w, h) {
                var elem = $(this),
                    data = $.data(this, str_data);
                data.w = w !== undefined ? w : elem.width();
                data.h = h !== undefined ? h : elem.height();
                old_handler.apply(this, arguments);
            }

            if ($.isFunction(handleObj)) {
                old_handler = handleObj;
                return new_handler;
            } else {
                old_handler = handleObj.handler;
                handleObj.handler = new_handler;
            }
        }
    };

    function loopy() {
        timeout_id = window[str_setTimeout](function () {
            elems.each(function () {
                var elem = $(this),
                    width = elem.width(),
                    height = elem.height(),
                    data = $.data(this, str_data);
                if (width !== data.w || height !== data.h) {
                    elem.trigger(str_resize, [data.w = width, data.h = height]);
                }
            });
            loopy();
        }, jq_resize[str_delay]);
    }
})(jQuery, this);