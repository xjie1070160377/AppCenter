

define(function () {
   
	function ispc()
	{  
	   var userAgentInfo = navigator.userAgent;  
	   var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
	   var flag = true;  
	   for (var v = 0; v < Agents.length; v++) {  
	       if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }  
	   }  
	   return flag;  
	}  

    return {
    	/**
		 * @property :{url} ajax请求地址
		 * @property :{template} 数据填充模板
		 * @property :{vars} 参数
		 * */
    	option : {
			url : null,
			template : null,
			pageNumber : 1,
			pageSize : 5,
		},
        init : function(options){
        	var _this = this;
        	if(!ispc()){
        		require(['mui.pullToRefresh', 'mui.pullToRefresh.material','../loadmore'],function(pullToRefresh, material,loadmore){
        			mui.init();
        			/*(function($) {
        				//阻尼系数
        				var deceleration = mui.os.ios?0.003:0.0009;
        				var scrollApis = $('.mui-scroll-wrapper').scroll({
        					bounce: false,
        					indicators: true, //是否显示滚动条
        					deceleration:deceleration
        				});
        				$.ready(function() {
        					//循环初始化所有下拉刷新，上拉加载。
        					$.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, pullRefreshEl) {
        						if(this.querySelector('.mui-table-view').children.length >= 0){
        							var option = $.extend(_this.option, options[index]);
        							var muiEl = $(pullRefreshEl).pullToRefresh({
        								down: {
        									callback: function() {
        										var self = this;
        										setTimeout(function() {
        											location.reload();
    											}, 500);
    										}
        								}, 
        								up: {
        									contentrefresh: '<span class="i-loading"><i></i></span>正在加载……',
        									offset: 0,
        									url: option.url,
        									pageNumber:option.pageNumber,
        									pageSize: option.pageSize,
        									template: option.template,
        									callback: function() {
        										var self = this;
        										setTimeout(function() {
        											loadmore.load(self);
        										}, 500);
        									}
        								}//up
        							});
        							
        						}//if
        						setTimeout(function() {
        							loadmore.load(muiEl);//加载其他tab数据
								}, 500);
        					});//each
        				});//ready
        			})(mui);*/
        		});
        	}
        }
    }
});






