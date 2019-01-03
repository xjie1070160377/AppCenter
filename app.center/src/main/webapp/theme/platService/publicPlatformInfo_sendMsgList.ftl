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
.menu-box a.sendMsgList{
    background: #e74c3c;
    color: #fff;
}
</style>
</head>
<body>
[#include "inc_header_publicPlatform.ftl"/]
<div class="main body">
  <div class="content info_box clearfix">
    [#include "inc_left_publicPlatform.ftl"]
    <div class="right">
    	<div class="right-box">
	      <h3><i></i><span>已发送消息</span></h3>
	      [#assign ind = 1]
	      [@ServMsgsPage id=appService.serviceId state=2 pageSize=5;pagedList]
	      <div class="grid">
	          [#list pagedList.content as info]
	          	<div class="msglistbox">
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
   	      <script type="text/javascript">
   	      $(window).load(function(){
   	    	$('.grid').masonry({
   	   		  // options
   	   		  itemSelector: '.msglistbox'
   	   		});
   	      });
   	      
   	      
   	      
   	   
   	      </script>
    </div>
  </div>
</div>
[#include "inc_footer.ftl"/]
</body>
</html>
[/#escape]