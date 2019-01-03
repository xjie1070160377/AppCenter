
define(function(){
	return{
		token : "b39b072fb626c84e512bab4536cb43ebf5cdce06",
		url : {
			index : "/m-articlePage.htx?cateId=52&attrType=0&hasPic=0&pageNumber=1&pageSize=20&token=b39b072fb626c84e512bab4536cb43ebf5cdce06"+this.token,
			zhuanti : "/m-articlePage.htx?cateId=76&attrType=0&hasPic=0&pageNumber=1&pageSize=20&token="+this.token,
			zonghe : "/m-articlePage.htx?cateId=77&attrType=0&hasPic=0&pageNumber=1&pageSize=20&token="+this.token,
			junxiangyang : "/m-articlePage.htx?cateId=89&attrType=0&hasPic=0&pageNumber=1&pageSize=20&token="+this.token,
			xuanshiting : "/m-articlePage.htx?cateId=11&attrType=0&hasPic=0&pageNumber=1&pageSize=20&token="+this.token
		},
		template : {
			index : this.templateToStr(function(){/*
			<% _.each(datas, function (info) { %>
			<li class="clearfix">
			 	<% if(info.getSmallImage()){ %>
			 	<a href="${info.url}" target="_blank" class="wrap-img">
					<img src="${info.smallImage()}" onerror="this.src='_files/img/noPic2.png'" width="148" height="110">
				</a>
			 	<% } %>
			 	<div>
					<p class="info-top">
						<span class="author">${info.author}</span><em>.</em>
						<span class="publishTime"> ${info.publishDate} </span>
					</p>
					<h4 class="title">
	                	<a href="${info.url}" title="${info.title}" target="_blank">${info.title}</a></h4>
					<p class="info-bottom">
						<span class="views">阅读：${info.views}</span>
						<span class="comments">评论数：${info.comments}</span></p>
				</div>
			</li>
			<% }); %>
			*/})
		},
		templateToStr:function(fn){
			return fn.toString().split("\n").slice(1,-1).join('\n') + '\n';
		}
	}
});

