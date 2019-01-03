// JavaScript Document
$(function(){
	$(".viod .comm div.v-img").hover(function(){
	  $(this).find("a.v-icon").toggle(100);	
	},function(){
	  $(this).find("a.v-icon").toggle(100);	
	});
	/*$(".viod .comm div.v-img ul li").hover(function(){
		$(this).parents("ul").find("img:not(:eq("+$(this).index()+"))").parent().children("p").css("display","block");
	},function(){
		$(this).parents("ul").find("img:not(:eq("+$(this).index()+"))").parent().children("p").css("display","none");
	});*/
	
})