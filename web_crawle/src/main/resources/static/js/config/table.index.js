$(function(){
		$("#sql-table").jTable({
			url:"/config/table/page",
			uniqueId:'id',
			height:"300",
			showColumns: true,
			showRefresh: true,
			columns :[
				{field: 'radio', radio: true}, 
				{
					field: 'index', title: '序号',align: 'center', valign: 'middle', sortable: false,
					formatter: function (value,row,index){return index+1; }
				},
				{ field : "id", title : "id"},
				{ field : "tableCode", title : "编码"},
				{ field : "name", title : "名称"},
				{ field : "groupName", title : "分组"},
				{ field : "remark", title : "描述"},
				{ field : "columns", visible: false},
				{ field:'id', title: '操作', width: 120,align: 'center',valign: 'middle',formatter: actionFormatter}
			],
			onClickRow:function(row,ele){
				$("#sql-detail-table").jmTable("load",row.columns)
			},
			onDblClickRow:function(row,ele){
				$.cartPage({parent:"#page-panel",src:'/config/table/cart/page?id='+row.id})
			}
		})
		$("#sql-detail-table").jTable({
			sidePagination: "client",
			height:"300",
			columns :[
				{ field : "id", title : "id"},
				{ field : "tableId", title : "表id", visible: false},
				{ field : "attr", title : "列名"},
				{ field : "type", title : "类型"},
				{ field : "isNull", title : "是否为空"},
				{ field : "isPrimary", title : "是否是主键"},
				{ field : "remark", title : "描述"},
			]
		})
		
		$("#btn_tab_add").on("click",function(){
			$.cartPage({parent:"#page-panel",src:'/config/table/mod/page'})
		})
		
		$("#sql-table").on("click",".tab-index",function(e){
			var row=$("#sql-table").bootstrapTable("getRowByUniqueId",$(this).attr("index"))
			if($(this).attr("type")=="mod"){
				$.cartPage({parent:"#page-panel",src:'/config/table/mod/page?type=1&id='+row.id});
			}else if($(this).attr("type")=="del"){
				$.diaLog({con:"是否删除菜单",func:function(type){
					if(type=="sure"){
						console.log(row)
					}
				}})
			}
		})
		
	})
	function actionFormatter(value, row, index, field){
		return [
			'<a class="tab-index" type="mod" index="'+value+'">修改</a>',
			'<a class="tab-index" type="del" index="'+value+'">删除</a>',
		].join("")
	}