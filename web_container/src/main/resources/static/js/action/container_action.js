
/**容器事件*/
function doActionContainer(obj){
	var col=[
		{title:"服务环境",name:"imageName"},
		{title:"版本",name:"imageVersion"},
		{title:"jar文件",type:"file",name:"file"},
		{title:"开放端口",name:"runPort"},
	]
	$.diaLog({
		title:obj.title,
		type:"confrim",
		con:getTableContent(obj.data?getColums(col,obj.data):col),
		func:function(type,content){
			var tabledata=getTableData(content);
			var jar=$(content).find("input[name=file]");
			if(jar.prop("files").length==0 && obj.needFile){
				$.diaLog({con:"请选择上传文件"})
				return false;
			}
			var flag=tabledata.isOk;
			if((flag && obj.needFile) || jar.prop("files").length>0){
				if(type=='sure'){
					$.loading().show()
					$.doUpdateFileAjax({
						url:"/uploadFile",
						ele:jar,
						async:false,
						success:function(res){
							if(res.success){
								tabledata.data.jarPath="/file"+res.data
								if(obj.success){
									obj.success(tabledata.data)
								}
							}else{
								$.diaLog({con:"文件上传错误"})
								flag=false;
							}
						}
					})
					$.loading().close();
				}
			}else if(flag){
				if(obj.success){
					obj.success(tabledata.data)
				}
			}
			return flag;
		}
	})
}
