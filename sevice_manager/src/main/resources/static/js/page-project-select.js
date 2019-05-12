function getProjects(data){
	var result=[]
	$.doJsonAjax({
		url:"/project/querylike",
		data:data,
		async:false,
		success:function(res){
			result= res.data;
		}
	})
	return result;
}
var projects;
$(function(){
	projects=getProjects();
})

/**本系统含有的项目快速选取*/
function inputSelectInit(obj){
	$(obj.ele).inputSelect({
		data:projects,
		target:'serviceName',
		getOption:function(opt){
			var list=[]
			$.each(opt.versions,function(i,e){
				var temp=$.extend(true,{}, opt);
				temp.name=opt.serviceName+'-'+opt.profile+"-"+e;
				temp.value=opt.serviceName;
				temp.version=e;
				list.push(temp)
			})
			return list;
		},
		change:function(ele,data){
			if(obj.change){
				obj.change(ele,data)
			}
			$(ele).parents(obj.parent).find("input").each(function(){
				if(obj.changeInput){
					obj.changeInput($(this),data)
				}
				var val=data[obj.match[$(this).attr("name")]]
				if(val){
					$(this).val(val)
				}
			})
		}
	})
}

/**项目关联url快速选取*/
function inputProjectUrlSelectInit(target,data){
	var url="/"+data.serviceName+(data.profile?'/'+data.profile+(data.version?'/'+data.version:''):'')
	$.doPostJsonAjax({
		url:'/project/info'+url,
		async:false,
		ignore:true,
		success:function(res){
			if(res.success ){
				if(res.data.length>0){
					var proj=res.data[0]
					if(proj.urls.length>0){
						var urlcol=[]
						$.each(proj.urls,function(i,u){
							for( var j in u.paths){
								urlcol.push({name:u.paths[j],types:u.types})
							}
						})
						$(target).inputSelect({
							data:urlcol,
						})
					}
				}
			}
		}
	}) 
}