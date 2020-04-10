	/*属性窗口操作*/
function windowAction(obj){
	$.diaLog({
		title:obj.title,
		type:"confrim",
		width:obj.width,
		con:getTableContent(obj.columns),
		func:function(type,content){
			var tabledata=getTableData(content,obj.parseForm);
			var flag=tabledata.isOk;
			if(flag){
				if(type=='sure'){
					if(obj.extendData){
						var d=obj.extendData(tabledata.data)
						if(d){
							tabledata.data=d;
						}
					}
					updatOrAddPostRequest({url:obj.actionUrl,data:tabledata.data,table:obj.table,btn:obj.btn,loadding:obj.loadding,success:obj.success,isPost:obj.isPost})
				}
			}
			return flag;
		}
	})
}

/**默认弹框from 函数*/
function defActiveWindowFun(obj){
	for(var i in obj.columns){
		var c=obj.columns[i];
		var val=obj.data[c.name]
		if(val !=undefined){
			c.value=val
		}
		obj.columns[i]=c
	}
	windowAction({
		title:obj.title,
		width:obj.width?obj.width:700,
		table:obj.table,
		success:obj.success,
		columns:obj.columns,
		actionUrl:obj.url,
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

/*获取表格已选择的数据*/
function getSelctRows(table,alert){
	var rows=$(table).bootstrapTable('getSelections');
	if(rows==undefined || rows.length==0){
		$.diaLog({con:alert?alert:"请选择一条数据"})
	}
	return rows;
}
function getSelctRow(table,alert){
	var rows=getSelctRows(table,alert);
	if(rows!=undefined && rows.length>0){
		return rows[0];
	}
}

function updatOrAddPostRequest(obj){
	var flag=false;
	var param={
			url:obj.url,
			data:obj.data,
			async:false,
			btn:obj.btn,
			loadding:obj.loadding?obj.loadding:true,
			success:function(res){
				$.diaLog({con:"操作成功"})
				if(obj.table){
					$(obj.table).jmTable("refresh")	
				}
				if(obj.success){
					obj.success(obj.data)
				}
				flag=true;
			}
		}
	if(obj.isPost ==undefined||obj.isPost){
		$.doPostJsonAjax(param)
	}else{
		$.doJsonAjax(param)
	}
	return flag;
	
}