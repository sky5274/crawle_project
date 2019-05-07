function getTableContent(list){
	var content='<div class="item-content clearfix">';
	$.each(list,function(i,e){
		if(e.hide){
			content+='<input type="hidden" name="'+e.name+'" value="'+e.value+'"/>'
		}else{
			var attr= e.attr == undefined ?"":e.attr
			content+='<div class="item-lable form-group clearfix"><lable class="col-sm-2 control-label">'+e.title+':</lable><div class="col-sm-10"><input class="pull-left  form-control" type="text" '+attr+'  name="'+e.name+'" value="'+(e.value==undefined || e.value==null?'':e.value)+'"/></div></div>'
		}
	})
	content+='</div>'
	return content;
}