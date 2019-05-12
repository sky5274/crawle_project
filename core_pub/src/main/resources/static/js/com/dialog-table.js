(function($,doc,win){
	var Exact_like_switch="Exact_like_switch_clazz"
	$.fn.extend({
		pageQueryTool:function(param){
			var _this=this;
			
			//type:like/Exact 
			var obj={
					clear:true,queryLike:true,type:'like'
			}
			if(param.columns==undefined || !Array.isArray(param.columns)){
				return;
			}
			var isChange=false
			$.each(param.columns,function(i,cl){
				if(cl.Exact){
					isChange=true;
					cl.attr=(cl.attr?cl.attr:'')+'exact="'+cl.Exact+'"'
				}
			})
			param=$.extend(true,obj,param)
			if(isChange){
				param.columns.push({on:'精准',off:"模糊",clazz:Exact_like_switch,checked:false,type:"switch",change:function(ele,data){
					
				}})
			}
			if(param.clear){
				param.columns.push({on:"清空",type:"switch",change:function(ele,data){
					if(!data.value){
						$(_this).find("input").val('')
						$(_this).find(".switch").bootstrapSwitch('setState', false)
						$(_this).find("input:checkbox").prop('checked',false)
					}
				}})
			}
			addFromItem($(this),param.columns)
		},
		pageQuery:function(){
			var data={}
			var isExcat=false;
			var el_switch=$(this).find("."+Exact_like_switch)
			if(el_switch.length>0){
				isExcat=$(el_switch).data("value")
			}
			$(this).find("input").each(function(){
				var nameKey=isExcat?'exact':'name'
				var key=$(this).attr(nameKey);
				if(key && key == '' ){
					key=$(this).attr("name")
				}
				if(key && key !='' && key !='undefined'){
					if($(this).attr("type")=='checkbox' || $(this).attr("type")=='radio'){
						data[key]=$(this).prop("checked")
					}else if($(this).val()!=''){
						data[key]=$(this).val()
					}
				}
			})
			return data;
		},
	})
	
})(jQuery,document,window)

/**pc from封装*/
function getTableContent(list){
	var content=$('<div class="item-content clearfix"></div>');
	addFromTable(content,list)
	return content;
}

/**form item 封装1*/
function addFromTable(content,list){
	$.each(list,function(i,e){
		if(e.hide){
			content.append('<input type="hidden" name="'+getNotNullText(e.name)+'" value="'+getNotNullText(e.value)+'"/>')
		}else{
			var size=e.size?e.size:10
			var item=$('<div class="item-lable form-group clearfix"><lable class="col-sm-2 control-label">'+getNotNullText(e.title)+':</lable><div class="col-sm-'+size+' form-item"></div></div>')
			$(item).find("div.form-item").append(getElement(e));;
			content.append(item)
		}
	})
}
function getNotNullText(txt){
	return txt?txt:''
}

/**form item 封装2*/
function addFromItem(content,list){
	$.each(list,function(i,e){
		if(e.hide){
			content.append('<input type="hidden" name="'+e.name+'" value="'+e.value+'"/>')
		}else if(e.ele && e.ele=='button'){
			var btn=$('<button class="btn btn-primary '+(e.clazz?e.clazz:'')+'" '+(e.id?'id="'+e.id+'"':'')+'>'+getNotNullText(e.title)+'</button>')
			initEleEvent(e,btn)
			content.append(btn)
		}else{
			var size=e.size?e.size:3
			if(e.title){
				var item=$('<div class="col-sm-'+size+' input-group"><span class="input-group-addon">'+getNotNullText(e.title)+'</span></div>')
				$(item).append(getElement(e));;
				content.append(item)
			}else{
				content.append(getElement(e))
			}
		}
	})
}

/**form item 输入元素*/
function getElement(e){
	var attr= e.attr == undefined ?"":e.attr
		attr+=e.require?"require=true":""
	var ele
	if(e.ele == 'select'){
		ele=$('<select class="pull-left  form-control" '+attr+' name="'+e.name+'" value="'+(e.value==undefined || e.value==null?'':e.value)+'"></select>"=')
		if(e.data){
			$.each(e.data,function(i,d){
				ele.append('<option data="'+JSON.stringfiy(d)+'" value="'+d.value+'">'+d.name+'</option>')
			})
		}
	}else {
		if(e.type!=undefined && e.type=='switch'){
			if(e.on){attr+='data-on-label="'+e.on+'"'}
			if(e.off){attr+='data-off-label="'+e.off+'"'}
			ele=$('<div class="switch '+(e.clazz?e.clazz:'')+'" '+attr+'> <input type="checkbox" name="'+getNotNullText(e.name)+'" /></div>')
			ele.bootstrapSwitch()
			$(ele).on('switch-change', function (ev, data) {
			    $(this).data('value',data.value)
			    $(this).data('data',data)
			    if(e.change){
			    	e.change(ele,data)
			    }
			});
		}else{
			ele= $('<input class="pull-left  form-control" type="'+(e.type?e.type:'text')+'" '+attr+' placeholder="'+(e.placeholder?e.placeholder:'')+'"  name="'+e.name+'" value="'+(e.value==undefined || e.value==null?'':e.value)+'"/>')
		}
		
	}
	if(e.id){
		ele.attr(id,e.id)
	}
	if(e.type=='checkbox' || e.type=='radio'){
		ele.css('max-width', '20px')
		var check=(e.chceked==undefined?'':(e.checked?'checked':''));
		if(check=='' && e.isChecked){
			check=e.isChecked(e)?'checked':''
		}
		if(check!=''){
			ele.attr("checked",check)
		}
	}
	initEleEvent(e,ele)
	return ele
}

/*元素事件*/
function initEleEvent(e,ele){
	if(e.event){
		for(var key in e.event){
			$(ele).on(key,function(){
				e.event[key](this)
			})
		}
	}
	if(e.init){
		e.init(ele)
	}
}

/**form 封装对象输入结果封装*/
function getTableData(content,func){
	$(content).find('input').parent().css("border",'')
	var data={}
	var flag=true;
	var code=$(content).find("input,select").each(function(i,ele){
		var val=$(this).val();
		if(func){
			data=func(ele,data);
		}
		if(val !=''){
			data[$(this).attr("name")]=val;
		}else if($(this).attr("requrie")=='true'){
			$.alert({content:$(this).parents('.form-group').text()+"不能为空",type:"warn"})
			$(this).parent().css('border',"1px solid red")
			flag=false;
		}
	})
	return {data:data,isOk:flag}
}

/**window 选择*/
function selectWindow(param){
	if(param.table==undefined){
		return;
	}
	var contxt=$('<div class="win-page-table"></div>')
	if(param.tabletool){
		var tool=$('<div class="page-tool" style="width:100%"></div>')
		param.table.toolbar=tool;
		param.table.getParam=function(){return $(tool).pageQuery()}
		contxt.append(tool)
		tool.pageQueryTool({clear:false,columns:param.tabletool})
	}
	var table=$('<table id="query-table_'+parseInt(Math.random()*1000)+'"></table>')
	contxt.append(table)
	
	if(param.table.isPage==false){
		param.table.responseHandler=function(res){
			if(res.success && res.data){
				return {list:res.data};
			}else{
				$.alert({type:"warn",content:"请求数据错误,错误编码："+res.code+" 信息："+res.message})
			}
		}
	}
	
	//首列添加
	var checkType=param.type?param.type:'radio'
	var colums=[]
	var ffiled={}
	ffiled.field=checkType;
	ffiled[checkType]=true
	colums.push(ffiled);
	$.each(param.table.columns,function(i,c){
		colums.push(c)
	})
	param.table.columns=colums
	$.diaLog({
		title:param.title?param.title:'',
		type:"confrim",
		width:'1000px',
		con:contxt,
		init:function(content){
			$(content).find("#"+table.attr("id")).jTable(param.table)
		},
		func:function(type,content){
			var flag=true;
			var rows=$(table).bootstrapTable('getSelections');
			if(rows.length==0 && type=='sure'){
				flag=false;
				$.alert({type:"warn",content:"您未选择任何数据"})
			}
			if(flag && type=='sure'){
				if(param.success){
					param.success(checkType=='radio'?rows[0]:rows)
				}
			}
			return flag;
		}
	})
}