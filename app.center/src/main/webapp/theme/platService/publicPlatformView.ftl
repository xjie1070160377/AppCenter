[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${site.fullNameOrName}</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/vendor/bootstrap/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/vendor/bootstrap/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="_files/css/comm.css" />
<link type="text/css" rel="stylesheet" href="_files/css/index.css" />
<link type="text/css" rel="stylesheet" href="_files/css/nav.css" />
<link type="text/css" rel="stylesheet" href="_files/css/wechat.css" />

<script type="text/javascript" src="${ctx}/static/vendor/bootstrap/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/static/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="_files/js/comm.js"></script>
<script type="text/javascript" src="_files/js/index.js"></script>
<script type="text/javascript" src="_files/js/i_slider.js"></script>
<script type="text/javascript" src="_files/js/JPlaceHolder.js"></script>

<style>
.frm_label {
    float: left;
    width: 5em;
    text-align: right;
    padding-right: 2em;
}
.info_box .frm_input_box, .info_box .frm_msg, .info_box .frm_tips{width:540px;background:#f5f5f5;color:#555;}
</style>

</head>

<body>

    
	<!-- Header Start -->
	[#include "inc_header.html"/]
	<!-- Header End -->

	<!-- Main Start -->
	<div class="main">
	<div class="position"><span>当前位置：<i></i></span><a href="/">首页 </a>> <a href="/mobile/pp/create.htx">服务号</a>  > 服务号查看</div>
		<div class="info_box">
			<div class="form_wrp">
				<form action="/mobile/pp/update.htx" method="post" class="form">
					<input type="hidden" name="serviceId" value="${appService.serviceId}"/>
					<fieldset class="frm_fieldset">
						<div class="frm_control_group">
							<label for="" class="frm_label">所属组织</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.cmsOrg.name}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">服务号类型</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.appServiceType.serviceTypeName}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">服务号帐号</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.serviceName}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">服务号全称</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.serviceFullname}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">负责人</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.charger}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">办公电话</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.telephone}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">移动电话</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.mobile}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">电子邮件</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.email}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">简介</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.intro}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">备注</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									${appService.note}
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">审核状态</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									[#if appService.isChecked && appService.isChecked==true ??]已审核[#else]未审核[/#if]
								</span>
							</div>
						</div>
						
								
					</fieldset>
				</form>
			</div>
		</div>
	
		
			
		
	</div>
	<!-- Main End -->
	<!-- Footer Start -->
	[#include "inc_footer.html"/]
	<!-- Footer End -->
	</script>
    
    
</body>
</html>
[/#escape]