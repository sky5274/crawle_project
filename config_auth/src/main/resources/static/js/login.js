$(function(){
    		$("#submit").on("click",function(){
    			var obj={}
    			$("#login-table").find("input").each(function(i,ele){
    				obj[$(this).attr("name")]=$(this).val();
    			})
    			$.doJsonAjax({
    				url:"/auth/login",
    				type:"post",
    				data:obj,
    				loadding:true,
    				success:function(res){
    					console.log(res)
    					if(res.success){
    						window.location.href=getRedirect_url()+"&access_token="+res.data.token.value
    					}else{
    						$.alert({ type:"warn",content:res.message})
    					}
    				}
    			})
    		})
    	})
    	
    	function getRedirect_url(){
    		var url=$("#redirect_url").attr("href");
    		$(".authorize-param input").each(function(i,inp){
    			if($(this).attr("name") !='redirect_uri'){
    				url+=i>0?"&":"?";
					url+=$(this).attr("name")+"="+$(this).val()
    			}
    		})
    		return url;
    	}