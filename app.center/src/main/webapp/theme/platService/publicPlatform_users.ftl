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
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/comm.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/index.js"></script>
<style>
.info_box .left{
	margin:0;
}
.info_box .right{
}
.content .right h3 i{
	background:url(_files/images/list.png) no-repeat 0 0 ;
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
.menu-box a.users{
    background: #e74c3c;
    color: #fff;
}
</style>
</head>
<body>
[#include "inc_header_publicPlatform.ftl"/]
<div class="main body">
  <div class="position"><span>当前位置：<i></i></span><a href="/">首页 </a>> <a href="/mobile/pp/create.htx">服务号</a>  > 已发布文章</div>
  <div class="content info_box clearfix">
    [#include "inc_left_publicPlatform.ftl"/]
    <div class="right">
    	<div class="right-box">
	      <h3><i></i><span>已关注用户</span></h3>
	      [#assign ind = 1]
	      [@ServUsersPage id=appService.serviceId pageSize=20;pagedList]
	      <table border="0" cellpadding="0" cellspacing="0" class="l-table1">
	        <tbody class="t-title">
	          <tr>
	            <td class="xh">序号</td>
	            <td style="text-align:center;">名称</td>
	          </tr>
	        </tbody>
	        <tbody class="t-content">
	          [#list pagedList.content as info]
	          <tr>
	            <td>${ind}</td>
	            <td><a href="">[#if info.realname??&&info.realname!=""]${info.realname!}[#else]${info.username!}[/#if]</a></td>
	            
	          </tr>
	          [#assign ind=ind+1 /] 
	          [/#list]
	        </tbody>
	      </table>
	      [#if ind > 1]
	      [#include "inc_page.ftl"/]
	      [/#if]
		  [/@ServUsersPage]
	      [#if ind = 1]
	      <div style="margin:10px">尚无资料</div>
	      [/#if]
	    </div>  
    </div>
  </div>
</div>
[#include "inc_footer.ftl"/]
</body>
</html>
[/#escape]