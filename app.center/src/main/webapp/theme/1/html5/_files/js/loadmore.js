define(['jquery','underscore'], function($, _){
	
	return{
		load: function(mui){
			console.log(mui.options.up.pageNumber);
			var option = mui.options.up;
			var container = mui.element.querySelector('.mui-table-view');
			var pageSize = option.pageSize;
			var template = option.template;
			var url = option.url;
			if(!option.url){
				console.log("url不能为空");
				return;
			}
			if(!(container instanceof jQuery)){ 
				container = $(container);
			}
			$.ajax({
				url:url,
				data:{pageNumber: mui.options.up.pageNumber++,pageSize:pageSize},
				success:function(res){
					var data = eval('('+res+')');
					if(data.msg_code=='0'){
						var list = data.result.data;
						if(list.length==0){
							mui.endPullUpToRefresh(true);
							return;
						}
						_.templateSettings = {  
						    evaluate : /@\*([\s\S]+?)\*@/g,  
						    interpolate : /\@{([\s\S]+?)\}/g,  
						    escape : /\<%-([\s\S]+?)%\>/g  
						}
						window.loadMoreDatas = list;
						console.log(window.loadMoreDatas);
						var htmlstr = _.template(template, loadMoreDatas);
						console.log(htmlstr);
						container.append(htmlstr);
						mui.endPullUpToRefresh();
					}else{
						console.log("加载出错");
					}
				}
			});
		}
		
	}
	
});
