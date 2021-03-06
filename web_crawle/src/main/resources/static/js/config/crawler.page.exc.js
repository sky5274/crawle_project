/**按照页面模型获取配置信息*/
function getConfigData(obj){
	var data={}
	var isValid=true;
	$(obj.table_ele).find("input:visible,select:visible,textarea:visible").each(function(){
		if($(this).val()!=""){
			var type=$(this).attr("type")
			if(type=="checkbox" || type=="radio"){
				if($(this).prop("checked")){
					data[$(this).attr("name")]=$(this).val()
				}
			}else{
				data[$(this).attr("name")]=$(this).val();	
			}
		}else{
			if($(this).attr("require")=="true"){
				isValid=false;
				$.alert({type:"warn",content:$(this).parent().text()+"不能为空"})
			}
		}
		
	})
	$(obj.attr_ele).each(function(){
		var attr={}
		$(this).find("input,select").each(function(){
			var type=$(this).attr("type")
			if(type=="checkbox" || type=="radio"){
				try {
					if($(this).prop("checked")){
						attr[$(this).attr("name")]=$(this).val()
					}
				} catch (e) {
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
var loop =true;
function listenLog(data,node,obj){
	$(".log-content").html("")
	loop=true;
	listenLogContent(data,node,obj)
}
/**
 * 1:动态查询爬虫临时日志
 * 2:使用递归形式查询临时日志，减少过度频繁的调度与时序错乱
 * */
function listenLogContent(data,node,obj){
	if(loop){
		window.setTimeout(function(){
			$.doJsonAjax({
				url:"/crawle/config/log/defined",
				data:{id:data.id},
				ignore:true,
				loadding:true,
				loadtarget:$(".tab-content").find(".log-content").parents(".tab-pane"),
				success:function(res){
					if(res.success){
						if(res.data.status==4){
							clearBtnStatus(node)
						}else{
							listenLogContent(data,node,obj)
						}
						if(res.data){
							showCrawlerLog(res.data,obj)
						}
					}else{
						clearBtnStatus(node)
					}
				},
				error:function(){
					clearBtnStatus(node)
				}
			})
		},5000)
	}
}

function showCrawlerLog(data,obj){
	var log_title=$(obj.panel).find(".nav-tabs").children("li#crawler_log_title");
	if(log_title.length==0){
		log_title=addCrawlerLogPanel(data,obj)
	}
	var panle_id=$(log_title).find("a").attr("href")
//	var con=data.context.replace(/</g,"&lt;").replace(/>/g,"&gt;");
	$(panle_id).find(".log-content").append(data.context)
	targgleTab(log_title);
}
function targgleTab(node){
	$(node).parent().find(".active").removeClass("active")
	$(node).addClass("active")
	var tab_content=$($(node).children("a").attr("href"))
	tab_content.parent().find(".active").removeClass("active")
	tab_content.addClass("active")
}

/**添加爬虫日志面板*/
function addCrawlerLogPanel(data,obj){
	var panel_id="crawler-log-content"
	var li=$('<li id="crawler_log_title" class="pos-relative"><a href="#'+panel_id+'" data-toggle="tab">爬虫日志</a><span style=" position: absolute; top: 0; right: 5px; cursor: pointer;" onClick="closeLogPanel(\''+obj.btn_ele+'\',this)">&times;</span></li>')
	$(obj.panel).find(".nav-tabs").append(li)
	$(obj.panel).find(".tab-content").append('<div id="'+panel_id+'" class="tab-pane fade in"><div style="max-height: 300px;overflow-y: auto; overflow-x: auto;"><div class="log-content"></div></div></div>')
	return li;
}

function closeLogPanel(btn,node){
	targgleTab($(node).parents("ul").children().eq(0));
	$($(node).parent().find("a").attr("href")).remove();
	$(node).parents("li").remove()
	clearBtnStatus(btn);
}

/**清除按钮状态与循环查询日志*/
function clearBtnStatus(node){
	loop=false;
	$(node).text("执行")
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
					data:data,
					loadding:true,
					success:function(res){
						if(res.success){
							listenLog(res.data,node,param)
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