[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>红客移动客户端</title>
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/font-awesome/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/comm.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/list.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/wechat.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/publicPlatform_infolist.css" />
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/masonry.pkgd.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/layer-v2.1/layer/layer.js"></script>

<style>
.info_box .left{
	margin:0;
}
.content .right h3 i{
	background:url(${ctxPath}/theme/platService/_files/images/list.png) no-repeat 0 0 ;
	margin: 7px 5px 5px 5px;
	width:16px;
	height:16px;
}
.l-table1 tbody.t-content tr td:nth-child(2){
	text-align:center;
}
.l-table1 tbody.t-title tr td{
	box-shadow: 0 0 0px 1px #999;
}
.info_box{width:100%;}
.info_box .right .right-box{
	padding:0 10px;
}
.menu-box a.draftMsgList{
    background: #e74c3c;
    color: #fff;
}
</style>
</head>
<body>

<div class="main body" style="padding-bottom: 50px;">
  
	      [#assign ind = 1]
	      [@ServMsgsPage id=appService.serviceId state=1 pageSize=5;pagedList]
	      <div class="grid">
	          [#list pagedList.content as info]
	          	<div class="msglistbox" data-id="${info.msgid}">
	          	<div>${info.pushTime}</div>
	           	[#list info.detail as msg]
	           		[#if msg.index == 0]
	           		<div class="msglist msglistpriheight msglistcurrent" data-id="1" data-index="0">
						<div class="primdiv imgdiv" style="background-image:url('${msg.headImage}')">
							<div class="backgrounddiv">
								
							</div>
							<div class="currenttitlediv">
							<h4 class="currenttitle titlediv" >${msg.title}</h4>
							</div>
						</div>		
					</div>
					[/#if]
	           	[/#list]
	           	[#list info.detail as msg]
	           		[#if msg.index != 0]
	           		<div class="msglist" data-id="2" data-index="${msg.index}">
						<div class="seconddiv">
							<div class="backgrounddiv imgdiv" style="background-image:url('${msg.headImage}')">
							</div>
							<div>
							<h4 class="secondtitle titlediv">${msg.title}</h4>
							</div>
						</div>
					</div>
					[/#if]
	           	[/#list]
	           	
	          	<div class="appmsg_mask" style="display:none;"></div>
	          	<i class="icon_card_selected">已选择</i>
	          	</div>
	          [#assign ind=ind+1 /] 
	          [/#list]
	          </div>
	      [#if ind > 1]
	      [#include "inc_page.ftl"/]
	      [/#if]
	      
		  [/@ServMsgsPage]
	      [#if ind = 1]
	      <div style="margin:10px">尚无资料</div>
	      [/#if]
   	      </div>
   	      
   	      <div class="dialog_ft">
			
            <span class="btn btn_primary btn_input js_btn_p"><button type="button" class="btn btn-primary" id="btn-chooseMsg-ok">确定</button></span>
	        
            <span class="btn btn_default btn_input js_btn_p"><button type="button" class="btn btn-default" id="btn-chooseMsg-cancel">取消</button></span>
	        
		</div>
		
   	      <script type="text/javascript">
   	      $(window).load(function(){
   	    	$('.grid').masonry({
   	   		  // options
   	   		  itemSelector: '.msglistbox'
   	   		});
   	      });
   	      $(".js_del").click(function(){
   	    	  var dataid = $(this).attr("data-id");
	   	    	layer.confirm('确定删除此消息？', {
	   	    	  btn: ['确定','取消'] //按钮
	   	    	}, function(){
	   	    		$.post("/mobile/pp/delMsg.htx",  {
	   	    	    	msgid : dataid
	   	    	    },  function(e) {
	   	    	    	var result = eval('('+e+')');
	   	    	    	layer.msg(result.reason, {icon: 1});
	   	    	    	window.location.href = '/mobile/pp/draftMsgList.htx';
	   	    	    });
	   	    	}, function(){
	   	    		//layer.msg('点了取消', {icon: 1});
	   	    	});
   	      });
   	      $(".msglistbox").mouseover(function(){
   	    	  $(this).find(".appmsg_mask").css("display", "");
   	      });
   	   	  $(".msglistbox").mouseout(function(){
   	   		  if($(this).find(".icon_card_selected").css("display") == "none")
	    	  $(this).find(".appmsg_mask").css("display", "none");
	      });
   	   	  $(".msglistbox").click(function(){
	    	  $(".appmsg_mask").css("display", "none");
	    	  $(".icon_card_selected").css("display", "none");
	    	  
	    	  $(this).find(".appmsg_mask").css("display", "block");
	    	  $(this).find(".icon_card_selected").css("display", "block");
	      });
   	   $("#btn-chooseMsg-ok").click(function(){
   		    var flag = 0;
   		    var id;
   			$(".icon_card_selected").each(function(){
   				if($(this).css("display")=="block"){
   					flag++;
   					id = $(this).parents(".msglistbox").attr("data-id");
   				}
   			});
   			if(flag == 0){
   				layer.msg("请选择素材",{
   				    time: 1000, //20s后自动关闭
   				});
   				return;
   			}
   			if(flag > 1){
   				layer.msg("只能选择一个素材",{
   				    time: 1000, //20s后自动关闭
   				});
   				return;
   			}
   			$("#msgid",window.parent.document).val(id).change();
   			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	   	 	parent.layer.close(index);
   	   });
   	   	  $("#btn-chooseMsg-cancel").click(function(){
	   	   	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	   	 	parent.layer.close(index);
   	   	  });
   	      </script>
  
</body>
</html>
[/#escape]