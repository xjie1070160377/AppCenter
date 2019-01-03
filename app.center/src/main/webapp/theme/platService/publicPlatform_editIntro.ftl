[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>红客移动客户端</title>
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery-2.1.1.js"></script>

<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/publicPlatform_editIntro.css" />

<script type="text/javascript">

</script>
</head>
<body>
	<!-- Main Start -->
	<form action="saveIntro.htx" method="post" class="form">
						<input type="hidden" name="serviceId" value="${appService.serviceId}"/>
	<div class="main">
		
		<div class="processor_bar_wrp">
			<ul class="processor_bar grid_line">
			    <li class=" step grid_item size1of2 current process_step1">
			        <h4>1 修改功能介绍</h4>
			    </li>
			    <li class="no_extra step grid_item size1of2 next process_step2">
			        <h4>2 确定修改</h4>
			    </li>
			</ul>
		</div>
		<div class="setting_dialog_content">
		   <div class="modify_content">
		     <label class="frm_label">请输入功能介绍</label>
		    <div class="frm_controls">
                    <span class="frm_textarea_box" style="width:500px;"><textarea class="frm_textarea" id="modifyInput" name="intro">${appService.intro}</textarea></span>
                    <!-- -->
                    <p class="frm_msg" style="display: none;">
                       ●功能介绍长度为4-120个字
                    </p>
                    <p class="frm_tips">提交通过后，你可以使用新的功能介绍。                    
<!--                     <a href="#" target="blank">点击了解更多</a> -->
                    </p>
                     
                </div>
		   </div>
		   <div class="ensure_content" style="display:none;">
		     <label class="frm_label">请确认是否修改功能介绍如下：</label>
		     <div class="frm_controls">
                    <div class="disabled_box" id="ensure_input">该减肥日记</div>
                    <p class="frm_tips">请确认介绍内容不含国家相关法律法规禁止内</br>容，否则将追究责任，               
<!--                     <a href="#" target="blank">了解更多</a> -->
                    </p>
                </div>
		   </div>
		</div>
		
	</div>
	<!-- Main End -->
	
	<div class="dialog_ft">
        <span class="btn" id="span_pre" style="display: inline-block;"><button type="button" class="js_btn" id="btn_next" data-index="0">下一步</button></span>
        <span class="btn1" id="span_next" style="display: none;"><button type="button" class="js_btn" id="btn_pre" data-index="1">上一步</button></span>
        <span class="btn" id="span_ok" style="display: none;"><button type="button" class="js_btn" id="btn_ok" data-index="2">确定</button></span>
        
		                    <input id="commit" style="display:none;" type="submit" value="提交" /> 
	</div>
	</form>
	<script type="text/javascript">
		$("#btn_next").click(function(){
			$("#span_pre").css("display", "none");
			$("#span_next").css("display", "inline-block");
			$("#span_ok").css("display", "inline-block");
			$(".process_step1").addClass("prev").removeClass("current");
			$(".process_step2").addClass("current").removeClass("next");
			$(".ensure_content").css("display", "");
			$(".modify_content").css("display", "none");
			var modifyInput = $("#modifyInput").val();
			$("#ensure_input").html(modifyInput);
		});
		$("#btn_pre").click(function(){
			$("#span_pre").css("display", "inline-block");
			$("#span_next").css("display", "none");
			$("#span_ok").css("display", "none");
			$(".process_step1").addClass("current").removeClass("prev");
			$(".process_step2").addClass("next").removeClass("current");
			$(".ensure_content").css("display", "none");
			$(".modify_content").css("display", "");
		});
		$("#btn_ok").click(function(){
			$("#commit").click();
			setTimeout(function(){window.close()},100);
		});
	</script>
</body>
</html>
[/#escape]