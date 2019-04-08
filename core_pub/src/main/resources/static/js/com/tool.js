(function($,doc,win){
	function getInitParent(parent){
		if(parent=parent.parent){
			return parent;
		}else{
			return getInitParent(parent.parent)
		}
	}
	
	function doAjax(option){
		var obj={
				data:{},
				type:'GET',
				async:true,
				dataType:"text",	
		}
		obj=$.extend(obj,option)
		if(obj.loadding){
			obj.beforeSend=function(){
				if(option.beforeSend){
					option.beforeSend();
				}
				$.loading(obj.loadtarget).show()
			}
			obj.complete=function(){
				if(option.complete){
					option.complete();
				}
				$.loading(obj.loadtarget).close()
			}
		}
		var url;
		if(obj.url.indexOf("://")<0){
			try {
				url=API.config.baseUrl+obj.url
			} catch (e) {
				url=obj.url
			}
		}else{
			url=obj.url
		}
		$.ajax({
			url:url,
			data:obj.data,
			type:obj.type,
			async: obj.async,
			contentType:obj.contentType,
			processData:obj.processData,
//			timeout: 10000,
			dataType:obj.dataType,
			beforeSend:function(){
				if(obj.btn){
					var btns=$(obj.btn);
					if(btns.length>0){
						btns.addClass('disabled');
						btns.prop('disabled', true);
					}
				}
				if(obj.beforeSend){
					obj.beforeSend()
				}
			},
			success:function(data,textStatus){
				if(data==null || data.code==undefined || data.code=="0"|| data.code=="-1" || obj.ignore==true || data.success){
						if(obj.success){
								obj.success(data,textStatus)
						}
				}else if(data.code=="-2"){
					getInitParent(parent).location.href=basePath+"/login";
				}else{
					if(obj.alert){
						$.diaLog({con: obj.alert,closed:true})
					}else{
						$.diaLog({con:obj.url+'>>>'+data.message,closed:true})
					}
				}
			},
			complete:function(){
				if(obj.complete){
					obj.complete()
				}
				if(obj.btn){
					var btns=$(obj.btn);
					if(btns.length>0){
						btns.removeClass('disabled');
						btns.prop('disabled', false);
					}
				}
			},
			error:function(e){
				if(obj.error){
					obj.error()
				}else{
					console.log(url)
					if(e.responseJSON){
						if(e.responseJSON.message){
							$.diaLog({con: e.responseJSON.message,closed:true})
						}
					}
				}
			}
		})
	}


	/**上传文件aJAX*/
	function doUpdateFileAjax(obj){
		obj.dataType='json';
		obj.async=false;
		obj.type="POST";
		obj.contentType=false;
		obj.processData= false;
		var formData = new FormData();
		for(var key in obj.data){
			formData.append(key, obj.data[key]);
		}
		if(obj.ele){
			var files=$(obj.ele).prop("files");
			if(files.length==0){
				$.diaLog({con:"请选择文件"})
				return false;
			}
			for(var i=0;i<files.length;i++){
				formData.append($(obj.ele).attr("name"),files[i])
			}
		}
		obj.data=formData
		doAjax(obj)
	}


	function doJsonAjax(obj){
		obj.dataType='json'
			doAjax(obj)
	}
	function doPostJsonAjax(obj){
		obj.type="POST";
		obj.contentType='application/json'
		if(typeof(obj.data)!="string"){
			obj.data=JSON.stringify(obj.data)
		}
		doJsonAjax(obj)
	}
	function jTable(tar,obj){
		var param ={
				method: 'get',
			    url: "",
				cache: false,
				striped: true,
				dataField: "list",
				pagination: true,
				pageNumber: 1,
				sidePagination: 'server',
				queryParams :function(param){
					if(obj.param){
						param=$.extend(param,obj.param())
					}
					param.current=param.offset;
					param.pageSize=param.limit;
					return param;
				},
				responseHandler:function(res){
					if(res.success && res.data){
						return res.data;
					}else{
						$.alert({type:"warn",content:"请求数据错误,错误编码："+res.code+" 信息："+res.message})
					}
				},
				pageSize: 10,
				pageList: [10, 25, 50, 100, 200],
				search: false,
				showColumns: false,
				showRefresh: false,
				minimumCountColumns: 2,
				clickToSelect: true,
				onLoadError:function(){
					if(obj.error){
						obj.error()
					}else{
						$.alert({type:"warn",content:"请求数据失败"})
					}
				}
				}
		param=$.extend(param,obj)
		$(tar).bootstrapTable(param)
	}
	var cartPage=function(param){
		if(param){
			this.getCartPage(param);
		}
	}
	cartPage.prototype={
		getCartPage:function(obj){
			var back_name=obj.back?obj.back:'返回';
			$(obj.parent).fadeOut();
			this.parent=$(obj.parent).parents("body");
			this.cart_panel=this.parent.find(".cart-panel")
			var _this=this;
			if(this.cart_panel.length==0){
				this.cart_panel=$('<div class="cart-panel panel panel-default fade in pos_relative">'+
							'<a class="cart-back"><span class="glyphicon glyphicon-chevron-left"></span><span>'+back_name+'</span></a>'+
							'<iframe frameborder="no" border="0" scrolling="no" id="cart-panle"></iframe>'+
						'</div>')
				this.parent.append(this.cart_panel);
				this.parent.data("cart-page",obj);
				$(this.cart_panel).css({"width":"100%","height":"100%","margin-bottom": "0"})
				$(this.cart_panel).find(".cart-back").css({"height":"30px","width":"100%"})
				$(this.cart_panel).find("iframe").css({"height":"calc(100% - 30px)","width":"100%"})
//				$(this.cart_panel).find(".cart-back").css({"height":"30px","width":"100%","position": "absolute","top": "0px","z-index": 2})
//				$(this.cart_panel).find("iframe").css({"height":"100%","width":"100%","position": "absolute","top": "0px"})
				$(this.cart_panel).find(".cart-back").on('click',function(){
					$(_this.cart_panel).fadeOut();
					$(obj.parent).fadeIn();
				})
			}
			this.cart_panel.find("iframe").attr("src",obj.src)
			this.cart_panel.fadeIn();
		},
		showPanel:function(){
			var cart_panel=$(".cart-panel");
			cart_panel.find(".cart-back").trigger("click")
		},
		back:function(func){
			var cart_panel=$(".cart-panel", window.parent.document);
			if(!cart_panel.is(':hidden')){
				top.cartBack();
				if(func){
					func()
				}
			}
		}
	}
	
	
	$.fn.extend({
		jTable:function(param){
			return new jTable($(this),param)
		},
		jmTable:function(method,param){
			return $(this).bootstrapTable(method,param)
		},
	})
	$.extend({
		cartPage:function(param){
			return new cartPage(param);
		},
		doAjax:doAjax,
		doJsonAjax:doJsonAjax,
		doPostJsonAjax:doPostJsonAjax,
		doUpdateFileAjax:doUpdateFileAjax
	})
	
})(jQuery,document,window)

$(function(){
	initPageEvent();
})

/** 页面跳转监听事件 */
function initPageEvent(){
	/**页面卸载 ，开始跳转*/
	 window.onunload = function() {
		 var load=$.loading();
		 if(load){
			 load.close()
		 }
	};
	/**页面准备跳转*/
	window.onbeforeunload = function(event) {
		 $.loading().show();
	};
}

function cartBack(){
	$.cartPage().showPanel()
}