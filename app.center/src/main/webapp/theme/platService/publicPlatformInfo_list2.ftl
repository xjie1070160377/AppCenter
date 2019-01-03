[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${node.name} -- ${site.fullNameOrName}</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/vendor/bootstrap/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/vendor/bootstrap/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="_files/css/comm.css" />
<link type="text/css" rel="stylesheet" href="_files/css/list.css" />
<link type="text/css" rel="stylesheet" href="_files/css/wechat.css" />
<script type="text/javascript" src="${ctx}/static/vendor/bootstrap/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/static/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="_files/js/comm.js"></script>
<script type="text/javascript" src="_files/js/index.js"></script>

<style>
.info_box .left{
	margin:0;
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
.info_box{width:100%;}
.info_box .right .right-box{
	padding:0 10px;
}
.menu-box a.platformList{
    background: #e74c3c;
    color: #fff;
}
</style>
</head>
<body>
[#include "inc_header_publicPlatform.html"/]
<div class="main body">
  <div class="position"><span>当前位置：<i></i></span><a href="/">首页 </a>> <a href="/mobile/pp/create.htx">服务号</a>  > 已发布文章</div>
  <div class="content info_box clearfix">
    [#include "inc_left_publicPlatform.html"]
    <div class="right">
    	<div class="right-box">
	      <h3><i></i><span>已发布文章</span></h3>
	      [#assign ind = 1]
	      [@ServInfoPage id=appService.serviceId pageSize=20;pagedList]
	      <table border="0" cellpadding="0" cellspacing="0" class="l-table1">
	        <tbody class="t-title">
	          <tr>
	            <td class="xh">序号</td>
	            <td>标题</td>
	            <td class="fbr">发布人</td>
	          </tr>
	        </tbody>
	        <tbody class="t-content">
	          [#list pagedList.content as info]
	          <tr>
	            <td>${ind}</td>
	            <td><a href="">${substring(info.detail.FFullTitle,20,'...')}</a></td>
	            <td>${info.detail.FAuthor}</td>
	          </tr>
	          [#assign ind=ind+1 /] 
	          [/#list]
	        </tbody>
	      </table>
	      [#if ind > 1]
	      [#include "inc_page.html"/]
	      [/#if]
		  [/@ServInfoPage]
	      [#if ind = 1]
	      <div style="margin:10px">尚无资料</div>
	      [/#if]
   	      </div>
    </div>
  </div>
</div>
[#include "inc_footer.html"/]
</body>
</html>
[/#escape]