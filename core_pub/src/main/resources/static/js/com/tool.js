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
				dataType:"json",	
		}
		obj=$.extend(obj,option)
		if(obj.prepare){
			//前置准备函数
			obj.prepare(obj.data,function(d){
				if(d){
					obj.data$.extend(obj.data,d);
				}
				ajaxFunc(obj,option)
			})
		}else{
			ajaxFunc(obj,option)
		}
	}
	
	/*解析url 前缀*/
	function parseUrl(url){
		var protocol=window.location.protocol
		if(url && url.indexOf("://")<0){
			try {
				url=API.config.baseUrl+url
				if(url.indexOf("http:")==0 && protocol !='http:'){
					url=protocol+url.substr(url.indexOf('://')+1);
				}
			} catch (e) {
			}
		}
		if(url && url.indexOf('://')==0){
			url=protocol+url.substr(1);
		}
		return url
	}
	
	/*ajax 函数*/
	function ajaxFunc(obj,option){
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
		var url=parseUrl(obj.url);
		if(url==undefined){
			return;
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
				if(data==null || data.code==undefined || data.code=="0"|| obj.ignore==true || data.success){
					if(obj.success || obj.ignore==true){
						obj.success(data,textStatus)
					}
				}else if(data.code=="-2"){
					getInitParent(parent).location.href=basePath+"/login";
				}else{
					if(obj.alert){
						$.diaLog({con: obj.alert,closed:true})
					}else{
						if(API.config.level>2){
							console.log(obj.url+'>>>'+data.message)
							$.diaLog({con:data.message,closed:true})
						}else{
							if(data.exception){
								$.diaLog({
									con:obj.url+'>>>'+data.message,
									init:function(dialog){
										$(dialog).find(".modal-footer").prepend("<button class='down-exception btn btn-default' >下载</button>")
										$(dialog).find(".down-exception").on("click",function(){
											download("log.txt",data.exception)
										})
									}
								})
							}else{
								$.diaLog({con:obj.url+'>>>'+data.message,closed:true})
							}
							
						}
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
					if(e.responseJSON){
						if(e.responseJSON.message){
							$.diaLog({con: e.responseJSON.message,closed:true})
						}
					}
				}
				this.complete()
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
		if(obj.type !=undefined){
			if("POST,post,PUT,put,Detelet,detelet".indexOf(obj.type)<0){
				obj.type="POST";
			}
		}else{
			obj.type="POST";
		}
		obj.contentType='application/json'
		if(typeof(obj.data)!="string"){
			obj.data=JSON.stringify(obj.data)
		}
		doJsonAjax(obj)
	}
	
	function jTable(tar,obj){
		var param ={
				method: 'get',
				cache: false,
				striped: true,
				dataField: "list",
				pagination: true,
				pageNumber: 1,
				sidePagination: 'server',
				queryParams :function(param){
					if(obj.param){
						param=$.extend(param,obj.param)
					}
					if(obj.getParam){
						param=$.extend(param,obj.getParam())
					}
					var params={}
					if(obj.ispost && (obj.method=='post'|| obj.method=='POST')){
						params.data=param;
					}else{
						params=param;
					}
					params.current=(param.offset/param.limit)+1;
					params.pageSize=param.limit;
					return params;
				},
				responseHandler:function(res){
					if(res.total !=undefined){
						return res;
					}
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
				clickToSelect: true,
				onLoadError:function(){
					if(obj.error){
						obj.error()
					}else{
						$.alert({type:"warn",content:"请求数据失败"})
					}
				}
				}
		if(obj.ispost && (obj.method=='post'|| obj.method=='POST')){
			param.contentType='application/json';
		}
		param=$.extend(param,obj)
		
		//解析url
		param.url=parseUrl(param.url)
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
				if(obj.showback==false){
					$(this.cart_panel).find(".cart-back").hide();
				}
				$(this.cart_panel).find(".cart-back").on('click',function(){
					$(_this.cart_panel).fadeOut();
					$(obj.parent).fadeIn();
				})
			
			this.cart_panel.find("iframe").attr("src",parseUrl(obj.src))
			this.cart_panel.fadeIn();
		},
		showPanel:function(){
			var cart_panel=$(".cart-panel");
			cart_panel.find(".cart-back").trigger("click")
		},
		back:function(func){
			var cart_panel=$(".cart-panel", window.parent.document);
			if(!cart_panel.is(':hidden')){
				//获取上级回调方法
				window.parent.cartBack();
				if(func){
					func()
				}
			}
		}
	}
	
	function getOptionEle(save_key,d){
		var opt=$('<p class="hidden" style="margin: 0px;padding: 2px 10px;text-align: center;"></p>')
		opt.data(save_key,d)
		opt.html(d.name)
		opt.attr('value',d.value)
		return opt
	}
	
	/*input  输入选择*/
	function inputSelect(ele,obj){
		$(".input-tip").remove();
		var save_key="_value"
		var zindex=$(ele).css('z-index')
		if(zindex==undefined || zindex=='' || zindex=='auto'){
			zindex=0;
		}
		var sel_id='sel_'+parseInt(Math.random()*1000);
		var targetKey=obj.target?obj.target:'name'
		var sel=$('<div id="'+sel_id+'" class="input-tip form-control hidden" style="border: 1px solid gray; display: inline-table;position: absolute;z-index: 999;"></div>')
		window.setTimeout(function(){
				if($(ele).length==0){
					$.alert({type:"warn",content:"input select  has not found the target input-element"})
				}
				var left=0
				var h=$(ele).outerHeight()
				var w=$(ele).outerWidth()
				sel.css({"left":left+'px',top:h+'px','width':w+"px"})
				if(obj.init){
					obj.init(ele,sel)
				}
				$(ele).parent().css("position",'relative')
				$(ele).parent().append(sel)
				if($("#input-tip-style").length==0){
					$(ele).parent().append('<style id="input-tip-style">.opt_hover{background-color: #E6E6E6; border: 1px solid #BDBDBD; box-shadow: 0 0 5px #A9E2F3;  border-radius: 5px}</style>')
				}
			},500)
			
		var initSelectBody=function(data){
			sel.html("")
			$.each(data,function(i,d){
				
				if(obj.getOption){
					d=obj.getOption(d)
				}
				if(Array.isArray(d)){
					$.each(d,function(i,el){
						sel.append(getOptionEle(save_key,el))
					})
				}else{
					sel.append(getOptionEle(save_key,d))
				}
			})
			if($(sel).find(".hidden").length>0){
				$(sel).removeClass("hidden")
				$(sel).find(".hidden").removeClass("hidden")
			}
		}
		if(obj.ajax){
			//异步加载
			$(ele).bind('input propertychange',function(){
				var val=$(this).val();
				var ajax=obj.ajax;
				if(obj.getData){
					ajax.data=obj.getData(val)
				}
				ajax.success=function(res){
					var data=obj.success?obj.success(res):res.data
					initSelectBody(data)
				}
				if(obj.type=='postjson'){
					doPostJsonAjax(ajax)
				}else{
					doAjax(ajax)
				}
				
			})
		}else if(obj.data){
			initSelectBody(obj.data)
			var isSelect=false;
			if(obj.dataType !='server'){
				$(ele).bind('input propertychange',function(){
					var val=$(this).val();
					var flag=false;
					var select=$(ele).parent().find("div#"+sel_id)
					select.children().each(function(){
						var optd=$(this).data(save_key);
						var targetVal=optd[targetKey];
						try{
							isSelect=val==targetVal
							if(targetVal.indexOf(val)<0){
								$(this).addClass("hidden")
							}else{
								$(this).removeClass("hidden")
								flag=true;
							}
						}catch (e) {
							$(this).removeClass("hidden")
						}
					})
					if(flag || isSelect){
						$(select).css({	position: 'absolute','cursor': 'pointer','height':'auto','max-height':'150px','overflow-y': 'auto','top': $(ele).parent().height()+"px",'width': $(ele).parent().width()+'px','z-index': zindex+1})
						$(select).removeClass('hidden')
					}else{
						$(select).addClass('hidden')
					}
				})
			}
		}
		
		//键盘上下+确认键事件
		var keyDownFunc=function(kcode){
			var nopt=$(sel).find(".opt_hover")
			var isSelect=nopt.length>0
			if(!isSelect){
				nopt=$(sel).children().eq(0);
				nopt.addClass("opt_hover")
			}
			if(kcode==40){
				//向下
				if(isSelect){
					nopt.removeClass("opt_hover")
					var nxopt=nopt.next()
					if(nxopt.length==0){
						nxopt=$(sel).children().eq(0);
					}
					nxopt.addClass("opt_hover")
				}
			}else if(kcode==38){
				//向上
				nopt.removeClass("opt_hover")
				var preopt=nopt.prev()
				if(preopt.length==0){
					preopt=$(sel).children().eq($(sel).children().length-1);
				}
				preopt.addClass("opt_hover")
			}else if(kcode==13){
				//确认
				nopt.click()
			}
		}
		
		$(ele).on('focus',function(){
			if($(sel).find('p').length>0){
				$(sel).removeClass("hidden")
			}
			$(ele).on('keydown',function(e){
				keyDownFunc(e.keyCode);
			})
		})
		$(ele).on('keydown',function(e){
			keyDownFunc(e.keyCode);
		})
		
		$(ele).on('blur',function(){
			window.setTimeout(function(){
				$(ele).off('keydown')
				$(sel).addClass('hidden')
			},300)
		})
		$(sel).on('click','p',function(){
			var opt=$(this);
			var optd=opt.data(save_key)
			$(ele).val(optd[targetKey])
			$(ele).data(targetKey,optd[targetKey])
			if(obj.change){
				obj.change(ele,optd)
			}
			$(this).parent().addClass('hidden')
			window.setTimeout(function(){
				$("#"+sel_id).remove()
			},500)
		})
		$(sel).children().hover(function(){
			$(sel).find(".opt_hover").removeClass("opt_hover")
			$(this).addClass("opt_hover")
		},function(){
			$(this).removeClass('opt_hover')
		})
	}
	
	function parseEle(tar,obj){
		$(tar).find("input,textArea").each(function(i,e){
			var n=$(this).attr("name")
			if(n!=undefined && n !=''){
				var v=obj[n];
				if(v !=undefined){
					$(this).val(v);
				}
			}
		})
		$(tar).find("select").each(function(i,e){
			var n=$(this).attr("name")
			if(n!=undefined && n !=''){
				var v=obj[n];
				if(v){
					$(this).children().prop("selected", false);
					$(this).children('[value="'+v+'"]').prop("selected", true);
				}
			}
		})
	}
	function parseObj(tar){
		var obj={}
		$(tar).find("input,textArea,select").each(function(i,e){
			var n=$(this).attr("name")
			if(n!=undefined && n !=''){
				var v=$(this).val()
				if(v !=''){
					obj[n]=v
				}
			}
		})
		return obj;
	}
	
	
	$.fn.extend({
		jTable:function(param){
			return new jTable($(this),param)
		},
		jmTable:function(method,param){
			return $(this).bootstrapTable(method,param)
		},
		inputSelect:function(param){
			return new inputSelect($(this),param)
		},
		parseEle:function(param){
			return new parseEle($(this),param)
		},
		parseObj:function(){
			return new parseObj($(this))
		},
		
	})
	$.extend({
		cartPage:function(param){
			return new cartPage(param);
		},
		doAjax:doAjax,
		doJsonAjax:doJsonAjax,
		doPostJsonAjax:doPostJsonAjax,
		doUpdateFileAjax:doUpdateFileAjax,
	})
	$.storage={
		put:function(key,value){
			this.init();
			if(value instanceof  Object){
				value=JSON.stringify(value)
			}
			localStorage.setItem(key,value);
		},
		get:function(key){
			this.init();
			var value=localStorage.getItem(key)
			try {
				value=JSON.parse(value)
			} catch (e) {}
			return value;
		},
		remove:function(key){
			this.init();
			localStorage.removeItem(key);
		},
		clear:function(){
			this.init();
			localStorage.clear();
		},
		init:function(){
			if(typeof(Storage)==="undefined"){
				return false;
			}
		}
	};
	$.session={
			put:function(key,value){
				this.init();
				if(value instanceof  Object){
					value=JSON.stringify(value)
				}
				sessionStorage.setItem(key,value);
			},
			get:function(key){
				this.init();
				var value=sessionStorage.getItem(key)
				try {
					value=JSON.parse(value)
				} catch (e) {}
				return value;
			},
			remove:function(key){
				this.init();
				sessionStorage.removeItem(key);
			},
			clear:function(){
				this.init();
				sessionStorage.clear();
			},
			init:function(){
				if(typeof(Storage)==="undefined"){
					return false;
				}
			}
	};
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

Date.prototype.toLocaleString = function() {
    return this.getFullYear() + "-" + getDoubleNumString((this.getMonth() + 1)) + "-" + getDoubleNumString(this.getDate()) + " " + getDoubleNumString(this.getHours()) + ":" + getDoubleNumString(this.getMinutes()) + ":" + getDoubleNumString(this.getSeconds());
};

function getDoubleNumString(num){
	return parseInt(num)<10?'0'+num:num;
}

/** 
 * 时间戳格式化函数 
 * @param  {string} format    格式 
 * @param  {int}    timestamp 要格式化的时间 默认为当前时间 
 * @return {string}           格式化的时间字符串 
 */
function date(format, timestamp){  
    var a, jsdate=((timestamp) ? new Date(timestamp*1000) : new Date()); 
    var pad = function(n, c){ 
        if((n = n + "").length < c){ 
            return new Array(++c - n.length).join("0") + n; 
        } else { 
            return n; 
        } 
    }; 
    var txt_weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]; 
    var txt_ordin = {1:"st", 2:"nd", 3:"rd", 21:"st", 22:"nd", 23:"rd", 31:"st"}; 
    var txt_months = ["", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];  
    var f = { 
        // Day 
        d: function(){return pad(f.j(), 2)}, 
        D: function(){return f.l().substr(0,3)}, 
        j: function(){return jsdate.getDate()}, 
        l: function(){return txt_weekdays[f.w()]}, 
        N: function(){return f.w() + 1}, 
        S: function(){return txt_ordin[f.j()] ? txt_ordin[f.j()] : 'th'}, 
        w: function(){return jsdate.getDay()}, 
        z: function(){return (jsdate - new Date(jsdate.getFullYear() + "/1/1")) / 864e5 >> 0}, 
        
        // Week 
        W: function(){ 
            var a = f.z(), b = 364 + f.L() - a; 
            var nd2, nd = (new Date(jsdate.getFullYear() + "/1/1").getDay() || 7) - 1; 
            if(b <= 2 && ((jsdate.getDay() || 7) - 1) <= 2 - b){ 
                return 1; 
            } else{ 
                if(a <= 2 && nd >= 4 && a >= (6 - nd)){ 
                    nd2 = new Date(jsdate.getFullYear() - 1 + "/12/31"); 
                    return date("W", Math.round(nd2.getTime()/1000)); 
                } else{ 
                    return (1 + (nd <= 3 ? ((a + nd) / 7) : (a - (7 - nd)) / 7) >> 0); 
                } 
            } 
        }, 
        
        // Month 
        F: function(){return txt_months[f.n()]}, 
        m: function(){return pad(f.n(), 2)}, 
        M: function(){return f.F().substr(0,3)}, 
        n: function(){return jsdate.getMonth() + 1}, 
        t: function(){ 
            var n; 
            if( (n = jsdate.getMonth() + 1) == 2 ){ 
                return 28 + f.L(); 
            } else{ 
                if( n & 1 && n < 8 || !(n & 1) && n > 7 ){ 
                    return 31; 
                } else{ 
                    return 30; 
                } 
            } 
        }, 
        
        // Year 
        L: function(){var y = f.Y();return (!(y & 3) && (y % 1e2 || !(y % 4e2))) ? 1 : 0}, 
        //o not supported yet 
        Y: function(){return jsdate.getFullYear()}, 
        y: function(){return (jsdate.getFullYear() + "").slice(2)}, 
        
        // Time 
        a: function(){return jsdate.getHours() > 11 ? "pm" : "am"}, 
        A: function(){return f.a().toUpperCase()}, 
        B: function(){ 
            // peter paul koch: 
            var off = (jsdate.getTimezoneOffset() + 60)*60; 
            var theSeconds = (jsdate.getHours() * 3600) + (jsdate.getMinutes() * 60) + jsdate.getSeconds() + off; 
            var beat = Math.floor(theSeconds/86.4); 
            if (beat > 1000) beat -= 1000; 
            if (beat < 0) beat += 1000; 
            if ((String(beat)).length == 1) beat = "00"+beat; 
            if ((String(beat)).length == 2) beat = "0"+beat; 
            return beat; 
        }, 
        g: function(){return jsdate.getHours() % 12 || 12}, 
        G: function(){return jsdate.getHours()}, 
        h: function(){return pad(f.g(), 2)}, 
        H: function(){return pad(jsdate.getHours(), 2)}, 
        i: function(){return pad(jsdate.getMinutes(), 2)}, 
        s: function(){return pad(jsdate.getSeconds(), 2)}, 
        //u not supported yet 
        
        // Timezone 
        //e not supported yet 
        //I not supported yet 
        O: function(){ 
            var t = pad(Math.abs(jsdate.getTimezoneOffset()/60*100), 4); 
            if (jsdate.getTimezoneOffset() > 0) t = "-" + t; else t = "+" + t; 
            return t; 
        }, 
        P: function(){var O = f.O();return (O.substr(0, 3) + ":" + O.substr(3, 2))}, 
        //T not supported yet 
        //Z not supported yet 
        
        // Full Date/Time 
        c: function(){return f.Y() + "-" + f.m() + "-" + f.d() + "T" + f.h() + ":" + f.i() + ":" + f.s() + f.P()}, 
        //r not supported yet 
        U: function(){return Math.round(jsdate.getTime()/1000)} 
    }; 
        console.log(format)
    return format.replace(new RegExp('/[\]?([a-zA-Z])/g'), function(t, s){ 
        if( t!=s ){ 
            // escaped 
            ret = s; 
        } else if( f[s] ){ 
            // a date function exists 
            ret = f[s](); 
        } else{ 
            // nothing special 
            ret = s; 
        } 
        return ret; 
    }); 
}

function sprintf(str) {
    var args = arguments,
        flag = true,
        i = 1;

    str = str.replace(/%s/g, function () {
        var arg = args[i++];

        if (typeof arg === 'undefined') {
            flag = false;
            return '';
        }
        return arg;
    });
    return flag ? str : '';
};

/**
 * 创建文件
 * @param filename
 * @param text
 * @returns
 */
function download(filename, text) {
	  var element = document.createElement('a');
	  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
	  element.setAttribute('download', filename);
	  
	  element.style.display = 'none';
	  document.body.appendChild(element);
	  element.click();
	  document.body.removeChild(element);
	}

/*元素全局显示*/
$(function(){
	listenFullScreenEvent();
})
/*全屏事件监听*/
function listenFullScreenEvent(){
	document.addEventListener('fullscreenchange', 		function() { FullScreenChangeEvent(document.fullscreen)},false);
	document.addEventListener('mozfullscreenchange',	function() { FullScreenChangeEvent(document.mozFullScreen)},false);
	document.addEventListener('webkitfullscreenchange',	function() { FullScreenChangeEvent(document.webkitIsFullScreen)},false);
	document.addEventListener('msfullscreenchange',		function() { FullScreenChangeEvent(document.msFullscreenElement)},false);
}

function FullScreenChangeEvent(flag){
	if(FULLFUC){FULLFUC(flag)}
	if(!flag){
		FULLELE=undefined;
	}
}

var FULLELE;
var FULLFUC;
/*加载全屏元素，以及全局过程事件*/
function launchIntoFullscreen(element,func) {
	FULLELE=element;
	FULLFUC=func;
    if(element.requestFullscreen){
        element.requestFullscreen();
    } else if(element.mozRequestFullScreen) {
        element.mozRequestFullScreen();
    } else if(element.webkitRequestFullscreen) {
        element.webkitRequestFullscreen();
    }else if(element.msRequestFullscreen) {
        element.msRequestFullscreen();
    }
}
/*退出全屏*/
function exitFullscreen() {
    if(document.exitFullscreen) {
        document.exitFullscreen();
    } else if(document.mozCancelFullScreen) {
        document.mozCancelFullScreen();
    } else if(document.webkitExitFullscreen) {
        document.webkitExitFullscreen();
    }
}
/*全屏元素*/
function FullScreenEle(){
	return FULLELE;
}

<<<<<<< HEAD
=======
/*执行iframe 内函数方法*/
function invokeFrameEvent(frame,key,...param){
	var callfunc=frame.eq(0)[0].contentWindow[key];
	if(callfunc){
		return callfunc.apply(null,param);
	}
}

>>>>>>> refs/remotes/origin/dev_base
/**json fromate (json 格式化)*/
var formatJson = function (json, options) {
    var reg = null,
        formatted = '',
        pad = 0,
        PADDING = '    ';
    options = options || {};
    options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
    options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
    if (typeof json !== 'string') {
        json = JSON.stringify(json);
    } else {
        json = JSON.parse(json);
        json = JSON.stringify(json);
    }
    reg = /([\{\}])/g;
    json = json.replace(reg, '\r\n$1\r\n');
    reg = /([\[\]])/g;
    json = json.replace(reg, '\r\n$1\r\n');
    reg = /(\,)/g;
    json = json.replace(reg, '$1\r\n');
    reg = /(\r\n\r\n)/g;
    json = json.replace(reg, '\r\n');
    reg = /\r\n\,/g;
    json = json.replace(reg, ',');
    if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
        reg = /\:\r\n\{/g;
        json = json.replace(reg, ':{');
        reg = /\:\r\n\[/g;
        json = json.replace(reg, ':[');
    }
    if (options.spaceAfterColon) {
        reg = /\:/g;
        json = json.replace(reg, ':');
    }
    (json.split('\r\n')).forEach(function (node, index) {
            var i = 0,
                indent = 0,
                padding = '';
            if (node.match(/\{$/) || node.match(/\[$/)) {
                indent = 1;
            } else if (node.match(/\}/) || node.match(/\]/)) {
                if (pad !== 0) {
                    pad -= 1;
                }
            } else {
                indent = 0;
            }
            for (i = 0; i < pad; i++) {
                padding += PADDING;
            }
            if(node != "") {
                formatted += padding + node + '\r\n';
            }
            pad += indent;
        }
    );
    return formatted;
};
