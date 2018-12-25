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
				$.loading().show()
			}
			obj.complete=function(){
				if(option.complete){
					option.complete();
				}
				$.loading().close()
			}
		}
		var url;
		if(obj.url.indexOf("://")<0){
			url=API.config.baseUrl+obj.url
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
				if(obj.beforeSend){
					obj.beforeSend()
				}
			},
			success:function(data,textStatus){
				if(data==null || data.code==undefined || data.code==0|| data.code==-1 || obj.ignore==true){
						if(obj.success){
								obj.success(data,textStatus)
						}
				}else if(data.code==-2){
					getInitParent(parent).location.href=basePath+"/login";
				}else{
					if(obj.alert){
						$.diaLog({con: obj.alert,closed:true})
					}else{
						$.diaLog({con:obj.url+'>>>'+data.msg,closed:true})
					}
				}
			},
			complete:function(){
				if(obj.complete){
					obj.complete()
				}
				
			},
			error:function(){
				if(obj.error){
					obj.error()
				}else{
					console.log(url)
				}
			}
		})
	}

	/**上传文件*/
	function updateFile(obj){
		var forms={}
		var list=obj.files
		for(var i in list){
			forms[i]=list[i]
		}
		forms.mk=obj.type
		doUpdateFileAjax({
			data:forms,
			success:function(data){
				if(obj.success){
					obj.success(data)
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
		obj.url="/file/fileUp";
		var formData = new FormData();
		for(var key in obj.data){
			formData.append(key, obj.data[key]);
		}
		obj.data=formData
		doAjax(obj)
	}


	function doJsonAjax(obj){
		obj.dataType='json'
			doAjax(obj)
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
						$.alert({type:"warn",content:"请求数据返回错误"})
					}
				},
				pageSize: 10,
				pageList: [10, 25, 50, 100, 200],
				search: false,
				showColumns: true,
				showRefresh: true,
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
	$.fn.extend({
		jTable:function(param){
			return new jTable($(this),param)
		},
		jmTable:function(method,param){
			return $(this).bootstrapTable(method,param)
		},
	})
	$.extend({
		doAjax:doAjax,
		doJsonAjax:doJsonAjax,
		updateFile:updateFile,
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
