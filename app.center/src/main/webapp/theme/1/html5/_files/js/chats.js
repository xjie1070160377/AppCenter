require(['jquery'], function($) {
	
	$("#new-chat-link").click(function(e){
		if ( e && e.stopPropagation ){
		   e.stopPropagation(); 
	   }else{
		   window.event.cancelBubble = true;
	   }
	   $("#followers").toggle();
	})
	
	$(".btn-link").click(function(e){
		if ( e && e.stopPropagation ){
		   e.stopPropagation(); 
	   }else{
		   window.event.cancelBubble = true;
	   }
	   $(this).siblings().filter(".dropdown-menu").toggle();
	})
	
	$('body').click(function(e){
	   if ( e && e.stopPropagation ){
		   e.stopPropagation(); 
	   }else{
		   window.event.cancelBubble = true;
	   }
	   if($("#followers").is(':visible')){
		   $("#followers").hide();
	   }
	   if($(".dropdown-menu").is(':visible')){
		   $(".dropdown-menu").hide();
	   }
   })
	
	$(".dropdown-submenu").hover(function(){
		
	});
	
	
	
	
});

	
	