[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>红客移动客户端</title>
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/font-awesome/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/comm.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/index.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/nav.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/wechat.css" />
<!-- <script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/jquery.min.js"></script> -->
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/comm.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/index.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/i_slider.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/layer-v2.1/layer/layer.js"></script>
<style type="text/css">
	.frm_label{width:6em;margin-right:8em;float:left;font-weight:400;color:#777;line-height: 40px;}
	.info_box .frm_input_box, .info_box .frm_msg, .info_box .frm_tips{width:350px;}
	.frm_controls a{color: #459ae9;font-size: 14px;font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","\9ED1\4F53",Arial,sans-serif;}
	.frm_input_box{border:none;font-family: "Microsoft Yahei";overflow:hidden;white-space:nowrap;text-overflow:ellipsis;}
	.frm_textare_box{width:450px;border:none;font-family: "Microsoft Yahei";height:auto;line-height: 20px;
	display: inline-block;
    position: relative;
    vertical-align: middle;
    font-size: 14px;
    padding: 0 10px;
    box-shadow: none;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
    border-radius: 0;
    -moz-border-radius: 0;
    -webkit-border-radius: 0;
    background-color: #fff;
	}
	.frm_control_group{border-bottom: 1px solid #eee;padding:6px 0;}
	
.menu-box a.settings{
    background: #e74c3c;
    color: #fff;
}
</style>
</head>

<body>
    
	<!-- Header Start -->
	[#include "inc_header_publicPlatform.ftl"/]
	<!-- Header End -->

	<!-- Main Start -->
	<div class="main">
	<div class="position"><span>当前位置：<i></i></span><a href="/">首页 </a>> <a href="/mobile/pp/create.htx">服务号</a>  > 服务号设置</div>
		<div class="info_box clearfix">
			<div class="form_wrp right">
				<div class="right-box">
				<form action="/mobile/pp/update.htx" method="post" class="form">
					<input type="hidden" name="serviceId" value="${appService.serviceId}"/>
					<fieldset class="frm_fieldset">
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">所属组织</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.cmsOrg.name}
								</span>
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">服务号类型</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.appServiceType.serviceTypeName}
								</span>
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">头像</label>
							<div class="frm_controls">
								<span class="frm_input_box" style="height:80px;">
									<input type="hidden" id="smallImageUrl" value="sss" onchange="javascript:changeHeadImg();"/>
									<img src="${appService.headImg}"  onerror="this.src='${ctxPath}/theme/platService/_files/images/getheadimg.jpg'" id="smallImage" width="80px" height="80px" alt="" />
								</span>
								<a onclick="javascript:editHeadimg();">修改头像</a>
								<script>
								function changeHeadImg(){
									var smallImageUrl = $("#smallImageUrl").val();
									$("#smallImage").attr('src', smallImageUrl);
									
								}
								function editHeadimg(){
									layer.open({
									    type: 2, //page层
									    area: ['960px', '620px'],
									    title: '修改头像',
									    shade: 0.6, //遮罩透明度
									    moveType: 1, //拖拽风格，0是默认，1是传统拖动
									    shift: -1, //0-6的动画形式，-1不开启
									    content: 'editHeadImg.htx?serviceId=${appService.serviceId}'
									});   
								}
								</script>
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">服务号帐号</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.serviceName}
								</span>
<!-- 								<a href="javascript:;">修改</a> -->
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">服务号全称</label>
							<div class="frm_controls">
								<span class="frm_input_box lable label-primary">
									${appService.serviceFullname}
								</span>
<!-- 								<a href="javascript:;">修改</a> -->
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">负责人</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.charger}
								</span>
<!-- 								<a href="javascript:;">修改</a> -->
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">办公电话</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.telephone}
								</span>
<!-- 								<a href="javascript:;">修改</a> -->
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">移动电话</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.mobile}
								</span>
<!-- 								<a href="javascript:;">修改</a> -->
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">电子邮件</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.email}
								</span>
<!-- 								<a href="javascript:;">修改</a> -->
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">简介</label>
							<div class="frm_controls">
								<span class="frm_textare_box">
									${appService.intro}
								</span>
								<script>
								function editIntro(){
									layer.open({
									    type: 2, //page层
									    area: ['960px', '620px'],
									    title: '修改服务号简介',
									    shade: 0.6, //遮罩透明度
									    moveType: 1, //拖拽风格，0是默认，1是传统拖动
									    shift: -1, //0-6的动画形式，-1不开启
									    content: 'editIntro.htx?serviceId=${appService.serviceId}'
									});
								}
								</script>
								<a onclick="javascript:editIntro();">修改</a>
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label">备注</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.note}
								</span>
<!-- 								<a href="javascript:;">修改</a> -->
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label clearfix">审核状态</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									[#if appService.isChecked?? && appService.isChecked==1]已审核[#else]未审核[/#if]
								</span>
							</div>
						</div>
						
								
					</fieldset>
				</form>
			</div>
			</div>
			[#include "inc_left_publicPlatform.ftl"]
			
		</div>
	</div>
	<!-- Main End -->
	<!-- Footer Start -->
	[#include "inc_footer.ftl"/]
	<!-- Footer End -->
	</script>
    
    
</body>
</html>
[/#escape]