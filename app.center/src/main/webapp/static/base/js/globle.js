;(function($){
	
	/**
	 * 返回顶部
	 */
	$(function(){
		$("body").append('<div class="toTop"><i class="fa fa-angle-up"></i></div>');
		
		$("body").on("click", ".toTop", function(){
			var self = this;
			$('html, body').animate({scrollTop:0}, 300, 'swing', function(){
				$(self).hide();
			});
		});
		$(document).on("scroll", function(){
			var self =this,
			scrollTop =$(self).scrollTop();//滚动高度  
			if(scrollTop > 300){
				$(".toTop").show();
			}
			if(scrollTop < 100){
				$(".toTop").hide();
			}
		});
	})
		
		
		
	
	
	
	
	
	
})(jQuery)