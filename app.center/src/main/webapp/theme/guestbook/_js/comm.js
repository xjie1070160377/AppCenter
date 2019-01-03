// JavaScript Document
$(function(){
	var font="站内全文搜索..."
	$("#serch").focus(function() {
		if($(this).val()===font){
			$(this).val("");
		}
		$("#serch").css("color","#333");
    });
	$("#serch").blur(function(){
		if($(this).val().length<1){
			$(this).val(font);
			$("#serch").css("color","#999");
		}
	});
	var thisIcon=$(".nav ul.body i").position().left; //原始位置
	$(".nav ul.body li").hover(function(){
		var thisLi=$(this).position().left;
		$(this).parent().children("i").stop().animate({left:thisLi+($(this).width()/2)-5});
		if($(this).find("div.min-div,div.m-d-content").length>0){
			$(this).addClass("this-li").find("div.min-div,div.m-d-content").css({"display":"block"});
		}
	},function(){
		$(this).parent().children("i").stop().animate({left:thisIcon});
		if($(this).find("div.min-div,div.m-d-content").length>0){
		  $(this).removeClass("this-li").find("div.min-div,div.m-d-content").css({"display":"none"});
		}
	});
	$(".nav ul.body div.min-div ul.left li.min-nav").hover(function(){
		$(this).addClass("thisbg").find("ul.right").stop().animate({width:"127px"},300);
	},function(){
		$(this).removeClass("thisbg").find("ul.right").stop().width(0)
	});
	

	 if (navigator.userAgent.toLowerCase().indexOf("chrome") >= 0) {
	 $(window).load(function(){
		 $('input:-webkit-autofill').each(function(){
			 var text = $(this).val();
			 var name = $(this).attr('name');
			 $(this).after(this.outerHTML).remove();
			 $('input[name=' + name + ']').val(text);
			 });
		});
	}
	$(".header .top li.add-thisbg").hover(function(){
		$(this).addClass("thisbg");
	},function(){
		$(this).removeClass("thisbg");
	});
	
	$(".header .nav ul.body  li.hg").hover(function(){
		$(this).addClass("thisbg").children("ul").stop().animate({height:($(this).find("li").height()+1)*$(this).find("li").length});
	},function(){
		
		$(this).removeClass("thisbg").children("ul").stop().animate({height:0});
	});
})