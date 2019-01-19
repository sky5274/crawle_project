/**按照页面模型获取配置信息*/
function getConfigData(obj){
	var data={}
	var isValid=true;
	console.log(obj)
	$(obj.table_ele).find("input,select,textarea").each(function(){
		if($(this).attr("require")=="true"){
			if($(this).val()==""){
				isValid=false;
				$.alert({type:"warn",content:$(this).parent().text()+"不能为空"})
			}
		}
		var type=$(this).attr("type")
		if(type=="checkbox" || type=="radio"){
			if($(this).prop("checked")){
				data[$(this).attr("name")]=$(this).val()
			}
		}else{
			data[$(this).attr("name")]=$(this).val();	
		}
	})
	$(obj.attr_ele).each(function(){
		var attr={}
		$(this).find("input,select").each(function(){
			var type=$(this).attr("type")
			if(type=="checkbox" || type=="radio"){
				if($(this).prop("checked")){
					attr[$(this).attr("name")]=$(this).val()
				}
			}else{
				var value=$(this).val();
				attr[$(this).attr("name")]=value;
			}
		})
		var field=$(this).parent().attr("field")
		var col=data[field];
		if(col==undefined){
			col=[]
		}
		col.push(attr)
		data[field]=col;
	})
	if(isValid){
		return data;
	}	
}
var loop 
function listenLog(data,node){
	loop=window.setInterval(function(){
		listenLogContent(data,node)
	},1000)
}
/**动态查询爬虫临时日志*/
function listenLogContent(data,node){
	$.doJsonAjax({
		url:"/crawle/config/log/defined",
		data:{id:data.id},
		ignore:true,
		success:function(res){
			if(res.success){
				if(res.data.status==4){
					clearBtnStatus(node)
				}
				console.log(res)
			}else{
				clearBtnStatus(node)
			}
		},
		error:function(){
			clearBtnStatus(node)
		}
	})
}

/**清除按钮状态与循环查询日志*/
function clearBtnStatus(node){
	clearInterval(loop)
	$(node).text("提交")
	$(node).data("status",0)
}
/**执行爬虫 配置事件*/
function btnExcete(param){
	var node=param.btn_ele;
	$(node).on("click",function(){
		var status=$(this).data("status")
		if(status != 1){
			var data=getConfigData(param)
			if(data){
				$.doPostJsonAjax({
					url:"/crawle/config/excute",
					data:JSON.stringify(data),
					type:"post",
					success:function(res){
						if(res.success){
							listenLog(res.data,node)
							$(node).data("status",1)
							$(node).text("放弃")
						}else{
							$.alert({type:"warn",content:res.message})
						}
					},
					beforeSend:function(){
						$(node).attr("disabled","disabled").addClass("disabled")
					},
					complete:function(){
						$(node).removeAttr("disabled").removeClass("disabled")	
					}
				})
			}
		}else{
			clearBtnStatus(node)
		}
		
	})
}