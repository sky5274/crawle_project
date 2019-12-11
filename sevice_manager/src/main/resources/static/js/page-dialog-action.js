	/*属性窗口操作*/
function windowAction(obj){
	$.diaLog({
		title:obj.title,
		type:"confrim",
		con:getTableContent(obj.columns),
		func:function(type,content){
			var tabledata=getTableData(content);
			var flag=tabledata.isOk;
			if(flag){
				if(type=='sure'){
					var func=obj.sureFunction;
					var param={url:obj.actionUrl,data:tabledata.data,table:obj.table};
					if(func){
						func(param);
					}else{
						updatOrAddPostRequest(param);
					}
					
				}
			}
			return flag;
		}
	})
}

function getColums(col,row){
	var collist=[]
	$.each(col,function(i,e){
		if(e.name=="isGrobal"){
			e.value=row[e.name]	
			if(row.isGrobal==1){
				e.attr='checked'
			}
		}else{
			e.value=row[e.name]	
		}
		collist.push(e.name)
	})
	for( var key in row){
		if(!($.inArray(key,collist) >= 0)){
			col.push({name:key,value:row[key],hide:true})
		}
	}
	return col
}

function getSelctRow(table,alert){
	var row=$(table).bootstrapTable('getSelections')[0];
	if(row==undefined){
		$.diaLog({con:alert?alert:"请选择一条数据"})
	}
	return row;
}

function updatOrAddPostRequest(obj){
	$.doPostJsonAjax({
		url:obj.url,
		data:obj.data,
		async:false,
		success:function(res){
			$.diaLog({
				con:"操作成功"
			})
			if(obj.table){
				$(obj.table).jmTable("refresh")	
			}
		}
	})
}