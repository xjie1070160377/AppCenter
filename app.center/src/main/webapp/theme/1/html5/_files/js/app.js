
require.config({
	baseUrl: "/theme/1/html5/_files/js/plugins",
    paths: {
        //个人中心url请求配置
//        jquery: "http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min",
//        jquery: "http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min",
//        "jquery.cookie" : "http://apps.bdimg.com/libs/jquery.cookie/1.4.1/jquery.cookie",
        jquery: "/theme/1/html5/_files/js/jquery-2.1.4.min",
        jquery: "/theme/1/html5/_files/js/jquery-2.1.4.min",
        "jquery.cookie" : "/theme/1/html5/_files/js/jquery-1.4.1.cookie",
        mui : "mui.min",
        underscore : "underscore-min",
        "mui.pullToRefresh" : "mui.pullToRefresh",
        "mui.pullToRefresh.material" : "mui.pullToRefresh.material",
        chats:"../chats",
        "jquery.sinaEmotion": "jquery.sinaEmotion"
    },
    priority:['jquery','zepto'],
    shim: {
    	//依赖配置
    		"chats": {
	            deps: [ "jquery" ]
	       },
    		"jquery.jplayer": {
	            deps: [ "jquery" ]
	       },
	       "jquery.cookie" : {
	       		deps: ["jquery"]
	       },
	       "mui": {
	            exports: 'mui',
	        },
           "mui.pullToRefresh": {
	            deps: [ "mui" ]
	        },
           "mui.pullToRefresh.material": {
	            deps: [ "mui" ]
	        },
	        "underscore": {
	            exports: "_"
	        },
	        "jquery.sinaEmotion": {
	        	deps: [ "jquery" ]
	        }

    }
});

//首页
require(['jquery'], function($) {
	
	//菜单hover
   $(".nav-side a").mouseenter(function(){
	   if($(this).hasClass("downapp")){
		   $(".dropdown-downapp").toggle();
	   }else{
		   var offsetTop =  this.offsetTop ;	  
	   	   var html = '<div class="dropdown" style="top:'+ (offsetTop+4)+"px" +'"><i class="iconfont">&#xe606;</i>'+ $(this).find("span").text() +'</div>'
		   $(this).append(html);
	   }
	    
   });
   $(".nav-side a").mouseleave(function(){
	   if($(this).hasClass("downapp")){
		   $(".dropdown-downapp").toggle(); 
	   }else{
		   $(this).find(".dropdown").remove();
	   }
   });
   
   //首页搜索  
   window.submitSearchForm = function(){
		$("#searchForm").submit();
   }
 
   //用户头像
   $(".wrap-btn .user i ,.editor-article .user i").click(function(e){
	   if ( e && e.stopPropagation ){
		   e.stopPropagation(); 
	   }else{
		   window.event.cancelBubble = true;
	   }
	   $(".user-dropdown").toggle();
   });
   $('body').click(function(e){
	   if ( e && e.stopPropagation ){
		   e.stopPropagation(); 
	   }else{
		   window.event.cancelBubble = true;
	   }
	   if($(".user-dropdown").is(':visible')){
		   $(".user-dropdown").hide();
	   }
   })
   
    $(".btn-group").click(function(e){
	   if(e && e.stopPropagation){
		   e.stopPropagation();
	   }else{
		   window.event.cancelBubble = true;
	   }
	   $(".dropdown-menu").toggle();
   });
   
   $('body').click(function(e){
	   if ( e && e.stopPropagation ){
		   e.stopPropagation(); 
	   }else{
		   window.event.cancelBubble = true;
	   }
	   if($(".dropdown-menu").is(':visible')){
		   $(".dropdown-menu").hide();
	   }
   })
   
   //设置
	$(".setting .nav-tabs li").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
		var href=$(this).find("a").attr("data-href");
		$(href).addClass("active").siblings().removeClass("active");
	})
	
	//热门与推荐
	$(".tab-pane .sort-nav li").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
		var href=$(this).find("a").attr("data-href");
		$(href).addClass("active").siblings().removeClass("active");
	})
	
	$(".page-title .navigation li").click(function(){
		if($(this).hasClass("t-search")){
			return;
		}
		$(this).addClass("active").siblings().removeClass("active");
		var href=$(this).find("a").attr("data-href");
		$(href).addClass("active").siblings().removeClass("active");
	})
	//
	$("#bindPhoneNumber").click(function(){
		$(".sms-bind-modal").show();
	});
    $(".sms-bind-modal .close").click(function(){
    	$(".sms-bind-modal").hide();
    })
   
    /**
     * 手机菜单弹出
     */
    $(".nav-shrink").on("tap click", function(e){
    	e.stopPropagation(); 
    	$(".nav-side").toggle();
    });
    
    $("body").on("tap click", function(e){
    	var $target = $(e.target);
    	if(!$target.hasClass("nav-side") && $target.parents(".nav-side").length == 0 && $(".nav-shrink").is(':visible')){
    		$(".nav-side").hide();
    	}
    });
    
    
   

});


//红友圈
/*require(['mui','mui.pullToRefresh', 'mui.pullToRefresh.material'], function($) {
	
});*/

