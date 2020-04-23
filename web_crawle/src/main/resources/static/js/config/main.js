function addModel(ele,tar){
	var tab_url=$(ele).attr("url")
	if(tab_url!="#" && tab_url !=""){
		var code=$(ele).attr("code")
		var tabId="tab_"+code;
		var tab=$(tar.tab).find("#"+tabId).parent()
		if(tab.length==0){
			tab=$('<li class="tab-item"><a href="#con_'+code+'" data-toggle="tab" id="'+tabId+'">'+$(ele).text()+'</a><i onClick="delTab(this)" class="tab-close" data-toggle="tooltip" title="closed">&times;</i> </li>');
			$(tar.tab).append(tab);
			var tab_con=$('<div class="tab-pane fade in" id="con_'+code+'"><iframe frameborder="no" border="0" src="'+$(ele).attr("url")+'" id="iframe_'+code+'"></iframe></div>')
			$(tar.content).append(tab_con);
		}
		var tab_act=$(tar.tab).find(".active")
		if(tab_act.find("#"+tabId).length==0){
			tab_act.removeClass("active")
			$(tar.content).find(".active").removeClass("active")
			$(tar.content).find("#con_"+code).addClass("active")
		}
		tab.addClass("active");
	}
}

function delTab(ele){
	var p=$(ele).parents(".tab-item");
	var parent=p.parent()
	var p_tar=$(p).find("a");
	$($(p_tar).attr("href")).remove();
	$(p).remove();
	if(parent.find("li.active").length==0){
		var item=parent.find("li").eq(0);
		if(item.length>0){
			item.addClass("active")
			var tab_con=$(item.find("a").attr("href"))
			$(tab_con).parent().find(".active").removeClass("active")
			$(tab_con).addClass("active")
			item.find("a").addClass("active")
			activeMenuTiem($("#menu-group-list a[code='"+item.find("a").attr("href").replace("#con_","")+"']").parent())
		}
	}
}

function activeMenuTiem(tar){
	$("#menu-group-list").find(".active").removeClass("active")
	$(tar).addClass("active")
}

function initMneuPage(){
	$("#menu-group-list").on("click","li",function(ele){
		activeMenuTiem(this)
		var pre=$(this).parents("li").find(".glyphicon-chevron-down");
		if(pre.length>0){
			return false;
		}
		var tap=$(this).children("a");
		var tab_url=$(tap).attr("url")
		if(tab_url=="#" || tab_url ==""){
			var tab_flag=$(tap).children("span.glyphicon");
			var list_g=$(this).children(".list-group")
			if(tab_flag.length>0 && list_g.length>0){
				var isClose=tab_flag.attr("class").indexOf("glyphicon-chevron-right")>-1;
				if(isClose){
					list_g.removeClass("hidden").fadeIn()
					$(tap).children("i.glyphicon").addClass("glyphicon-minus").removeClass("glyphicon-plus")
					tab_flag.addClass("glyphicon-chevron-down").removeClass("glyphicon-chevron-right")
				}else{
					$(this).find(".list-group").fadeOut().addClass("hidden")
					$(this).find("i.glyphicon").addClass("glyphicon-plus").removeClass("glyphicon-minus")
					$(this).find("span.glyphicon").addClass("glyphicon-chevron-right").removeClass("glyphicon-chevron-down")
				}
			}
		}
	})
}