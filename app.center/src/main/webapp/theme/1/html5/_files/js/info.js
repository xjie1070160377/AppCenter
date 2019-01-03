
define(function(require, exports, module) {
	'use strict';
	var $ = require("jquery")

	//分享
	$(".share").click(function(e){
		   if ( e && e.stopPropagation ){
			   e.stopPropagation(); 
		   }else{
			   window.event.cancelBubble = true;
		   }
		   $(".share-dropdown").toggle();
	});
	
	//收藏
    var is = true;
	$(".colect").on("click",function(){
		if(is){
			colect('.colect i','&#xe62a;','#ff5500',false)
		}else{
			colect('.colect i','&#xe62a;','#a7a7a7',true)
		}
		
	});
	function colect(Elements,str,color,Is){
			$(Elements).html(str).css('color',color)
			is = Is;
	}
	
	//添加关注btn-success
//	$(".btn-success").on("click",function(){
//		$(this).hide(0);
//		
//	})
})


