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
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery.validation_zh_CN.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/JPlaceHolder.js"></script>

<style type="text/css">
label.error {color:red;font-style:italic;}
em.required {color:red;font-style:normal;}
.recru-table{width:100%;margin:20px 0;border-collapse:collapse;}
.recru-table td{padding:15px 5px; border:#ccc 1px solid;}
.info_box{padding:0;border:1px solid #ddd;}
.frm_controls{display:inline-block;}
.info_box .frm_input_box, .info_box .frm_msg, .info_box .frm_tips {
    width: 390px;
}
.frm_label{
	width:7em;
	color: #333;
    font-weight: 400;
    font-size:15px;
}
.frm_controls{
	margin:0;
}
.frm_textarea{
	width:390px;
    max-width: 390px;
}
.frm_control_group.step2{
	display:none;
}
.frm_control_group{
	padding-bottom:60px;
}
.frm_input_box{
	height:40px;
}
.frm_input {
    height: 32px;
}
.step.prev{
	background-image:none;
}
.col-lg-5.login{
	padding-top:50px;
}
</style>
</head>

<body>

    
	<!-- Header Start -->
	[#include "inc_header_publicPlatform.ftl"/]
	<!-- Header End -->

	<!-- Main Start -->
	<div class="main">
	<div class="position"><span>当前位置：<i></i></span><a href="/">首页 </a>> <a href="/mobile/pp/create.htx">服务号</a>  > 服务号申请</div>
		<div class="info_box">
		
		[#if !appService??]
		<div id="stepItems">
			<ul class="processor_bar grid_line">
			    <li class="step grid_item size1of3 current" id="processor1">
			        <h4>1 基本信息</h4>
			    </li>
			    <li class="step grid_item size1of3 next" id="processor2">
			        <h4>2 信息登记</h4>
			    </li>
			    <li class="step grid_item size1of3 nnext" id="processor3">
			        <h4>3 公众号信息</h4>
			    </li>
			</ul>
		</div>
		
			<div class="form_wrp clearfix">
			
				<div class="col-lg-7 register">
					<form id="validForm" action="/mobile/pp/update.htx" method="post" class="form">
						<input type="hidden" name="serviceId" value="${appService.serviceId}"/>
						<fieldset class="frm_fieldset">
							<div class="frm_control_group">
								<label for="" class="frm_label" style="width:auto"><i class="fa fa-exclamation-circle" style="color:#0D88C5"></i>请填写真实信息</label>
							</div>
							<div class="frm_control_group step1">
								<label for="" class="frm_label">所属组织</label>
								<div class="frm_controls">
									<span class="frm_input_box">
										<select name="orgId" class="frm_input">
											[#list orgList as org]
											<option value="${org.id}"[#if appService?? && appServiceorg.id == appService.orgId] selected="selected"[/#if]>${org.name}</option>
											[/#list] 
										</select>
									</span>
									<p class="frm_tips">(请填写未被服务号平台注册的服务号简称)</p>
								</div>
							</div>
							<div class="frm_control_group step1">
								<label for="" class="frm_label">服务号类型</label>
								<div class="frm_controls">
									<span class="frm_input_box">
										<select name="servicetype.serviceTypeid" class="frm_input"> 
											[#list typeList as type]
											<option value="${type.serviceTypeid}"[#if (appService.appServiceType)?? && type.serviceTypeid == appService.appServiceType.serviceTypeid] selected="selected"[/#if]>${type.serviceTypeName}</option>
											[/#list] 
										</select>
									</span>
									<p class="frm_tips">(请填写未被服务号平台注册的服务号简称)</p>
									
								</div>
							</div>
							<div class="frm_control_group step1">
								<label for="" class="frm_label"><em class="required">*</em>服务号帐号</label>
								<div class="frm_controls">
									<span class="frm_input_box">
										<input name="serviceName" maxlength:"50" id="serviceName" type="text" placeholder="请填写未被服务号平台注册的服务号帐号" class="frm_input required" value="${appService.serviceName}">
									</span>
								</div>
							</div>
							<div class="frm_control_group step1">
								<label for="" class="frm_label"><em class="required">*</em>服务号全称</label>
								<div class="frm_controls">
									<span class="frm_input_box">
										<input name="serviceFullname" maxlength:"50" id="serviceFullname" type="text" placeholder="请填写未被服务号平台注册的服务号全称" class="frm_input required" value="${appService.serviceFullname}">
									</span>
								</div>
							</div>
							
							<div class="frm_control_group step1">
								<label for="" class="frm_label"><em class="required">*</em>负责人</label>
								<div class="frm_controls">
									<span class="frm_input_box">
										<input name="charger" id="charger" type="text" placeholder="请填写负责人名称" class="frm_input required" value="${appService.charger}">
									</span>
								</div>
							</div>
							<div class="frm_control_group step1">
			                    <a class="btn btn-success" id="next-step" />下一步</a>
			                 </div>
							<div class="frm_control_group step2">
								<label for="" class="frm_label">办公电话</label>
								<div class="frm_controls">
									<span class="frm_input_box">
										<input name="telephone" id="telephone" type="text" placeholder="请填写办公电话" class="frm_input" value="${appService.telephone}">
									</span>
								</div>
							</div>
							<div class="frm_control_group step2">
								<label for="" class="frm_label">移动电话</label>
								<div class="frm_controls">
									<span class="frm_input_box">
										<input name="mobile" id="mobile" type="text" placeholder="请填写移动电话" class="frm_input" value="${appService.mobile}" maxlength=11>
									</span>
								</div>
							</div>
							<div class="frm_control_group step2">
								<label for="" class="frm_label">电子邮件</label>
								<div class="frm_controls">
									<span class="frm_input_box">
										<input name="email"  id="email" type="text" placeholder="请填写电子邮件" class="frm_input" value="${appService.email}">
									</span>
								</div>
							</div>
							<div class="frm_control_group step2">
								<label for="" class="frm_label">简介</label>
								<div class="frm_controls">
									<textarea rows="15" name="intro" id="intro" type="text" placeholder="请填写服务号简介" max-length="1000" class="frm_textarea">${appService.intro}</textarea>
								</div>
							</div>
							[#if (appService.checkNote)?? ] 
							<div class="frm_control_group">
								<label for="" class="frm_label">审核意见</label>
								<div class="frm_controls">
									${appService.checkNote}
								</div>
							</div>
							[/#if]
							<div class="frm_control_group step2">
			                     <a class="btn btn-success" id="prev-step" />上一步</a>
			                     <input style="margin-left:20px;" class="btn btn-success" id="commit" type="submit" value="提交" />
			                 </div>
									
						</fieldset>
					</form>
				</div>
				<div class="col-lg-5 login">
					已有红客公众账号？
					<a href="/login.htx">立即登录</a>
				</div>
			</div>
			[#else]
			<div id="stepItems">
				<ul class="processor_bar grid_line">
				    <li class="step grid_item size1of3 prev" id="processor1">
				        <h4>1 基本信息</h4>
				    </li>
				    <li class="step grid_item size1of3 nnext" id="processor2">
				        <h4>2 信息登记</h4>
				    </li>
				    <li class="step grid_item size1of3 current" id="processor3">
				        <h4>3 公众号信息</h4>
				    </li>
				     <li class="step grid_item next">
				     	<h4>&nbsp;</h4>
				    </li>
				</ul>
			</div>
				<!-- 步骤3 -->
				<div class="row preview clearfix">
					<div class="clo-lg-12">
						<div class="form_wrp">
							<form action="/mobile/pp/update.htx" method="post" class="form">
								<input type="hidden" name="serviceId" value="${appService.serviceId}"/>
								<fieldset class="frm_fieldset">
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success col col-lg-2">所属组织</label>
										<div class="frm_controls col col-lg-10">
												${orgName}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">服务号类型</label>
										<div class="frm_controls">
												${appService.servicetype.serviceTypeName}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">服务号简称</label>
										<div class="frm_controls">
												${appService.serviceName}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">服务号全称</label>
										<div class="frm_controls">
												${appService.serviceFullname}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">负责人</label>
										<div class="frm_controls">
										
												${appService.charger}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">办公电话</label>
										<div class="frm_controls">
												${appService.telephone}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">移动电话</label>
										<div class="frm_controls">
												${appService.mobile}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">电子邮件</label>
										<div class="frm_controls">
												${appService.email}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">简介</label>
										<div class="frm_controls">
												${appService.intro}
										</div>
									</div>
									<div class="frm_control_group clearfix">
										<label for="" class="alert alert-success">审核状态</label>
										<div class="frm_controls">
												[#if appService.isChecked?? && appService.isChecked==1]<span style="padding:8px 12px;" class="label label-success">已审核</span>[#else]<span style="padding:8px 12px;" class="label label-warning">未审核</span>[/#if]
										</div>
									</div>
									
											
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			[/#if]
		</div>
	
		
			
		
	</div>
	<!-- Main End -->
	<!-- Footer Start -->
	[#include "inc_footer.ftl"/]
	<!-- Footer End -->
	
	<script type="text/javascript">
		//IE placeholder兼容
		jQuery(function(){
		    JPlaceHolder.init();    
		});
		
		//下一步
		$("#next-step").click(function(){
			$(".frm_control_group.step2").show();
			$(".frm_control_group.step1").hide();
			$("#processor1").removeClass("current").addClass("prev");
			$("#processor2").removeClass("next").addClass("current");
			$("#processor3").removeClass("nnext").addClass("next");
		});
		//上一步
		$("#prev-step").click(function(){
			$(".frm_control_group.step1").show();
			$(".frm_control_group.step2").hide();
			$("#processor1").removeClass("prev").addClass("current");
			$("#processor2").removeClass("current").addClass("next");
			$("#processor3").removeClass("next").addClass("nnext");
		});

		//表单验证
	    var Validator = $("#validForm").validate({
	    	onfocusout:function(){
	    		this.form();
	    	},
	    	rules:{
	    		serviceName:{
	    			required:true,
	    			maxlength:50,
	    			minlength:2
	    		},
	    		serviceFullname:{
	    			required:true,
	    			maxlength:50,
	    			minlength:2
	    		},
	    		charger:{
	    			required:true,
	    			maxlength:4,
	    			minlength:2
	    		},
	    		telephone:{
	    			digits:true,
	    			maxlength:8,
	    			minlength:6
	    		},
	    		mobile:{
	    			isMobile:true
	    		},
	    		email:{
	    			email:true
	    		}
	    	}
	    	
	    	
    	});
    	
    	jQuery.validator.addMethod("isMobile", function(value, element) {   
		    var tel = /^[1][0-9]{10}$/;
		    return this.optional(element) || (tel.test(value));
		}, "请填写正确的手机号");
		
		
	</script>
    
    
</body>
</html>
[/#escape]