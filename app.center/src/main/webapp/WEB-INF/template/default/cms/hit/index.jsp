<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<link href="${ctxPath}/static/base/css/form.css" rel="stylesheet">
<style>
legend{
	font-size:14px;
	padding-bottom: 5px;
	margin-bottom:10px;
}
form{
	border:1px solid #e5e5e5;
	padding: 10px;
	margin: 10px;
}
</style>
<div class="col-sm-12">
	<div class="ibox-title">
		<h5>点击数设置</h5>
	</div>
	
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <form id="setting1Form" onsubmit="return confirm('确认提交输入的设置，提交后不能回滚，确认设置？');" action="cms/hit/setting1">
				<fieldset  class="c-fieldset">
					<legend>按倍数设置</legend>
					将点击数小于&nbsp;<input type="text" name="js" size="7"/>&nbsp;的文章翻&nbsp;<input type="text" name="bs" size="7"/>&nbsp;倍。<input type="button" onclick="submitForm(1);" value="设置"/>&nbsp;&nbsp;<font style="color:#F00E44;">点击数条件设置为0时，对所有文章生效。</font>
				</fieldset>
			</form>
			<form id="setting2Form" onsubmit="return confirm('确认提交输入的设置，提交后不能回滚，确认设置？');" action="cms/hit/setting2">
				<fieldset  class="c-fieldset">
					<legend>按范围设置</legend>
					将点击数小于&nbsp;<input type="text" name="js" size="7"/>&nbsp;的设置为大于&nbsp;<input type="text" name="xhits" size="7"/>&nbsp;小于&nbsp;<input type="text" name="dhits" size="7"/>&nbsp;的随机数。<input type="submit" onclick="submitForm(2);" value="设置"/><font style="color:#F00E44;">&nbsp;&nbsp;点击数条件设置为0时，对所有文章生效。</font>
				</fieldset>
			</form>
			<form id="setting3Form" onsubmit="return confirm('确认提交输入的设置，提交后不能回滚，确认设置？');" action="cms/hit/setting3">
				<fieldset  class="c-fieldset">
					<legend>按文章设置</legend>
					将文章代号为&nbsp;<input type="text" name="infoId" size="7"/>&nbsp;的点击数设置为&nbsp;<input type="text" name="hits" size="7"/>。<input type="submit" onclick="submitForm(3);" value="设置"/>
				</fieldset>
			</form>
        </div>
    </div>
</div>


<script type="text/javascript">

    $(document).ready(function () {
        //表单验证className
        $("#setting1Form").validate({
            rules: {
            	js: {
                    required: true,
                    digits: true
                },
                bs: {
                    required: true,
                    digits: true
                }
            }
        });
        $("#setting2Form").validate({
            rules: {
            	js: {
                    required: true,
                    digits: true
                },
                xhits: {
                    required: true,
                    digits: true
                },
                dhits: {
                    required: true,
                    digits: true
                }
            }
        });
        $("#setting3Form").validate({
            rules: {
            	infoId: {
                    required: true,
                    digits: true
                },
                bs: {
                    required: true,
                    digits: true
                }
            }
        });
    });
    
    function submitForm(flag){
    	if($("#setting"+flag+"Form").valid()==false){
    		return;
    	}
   		if(confirm("确认提交输入的设置，提交后不能回滚，确认设置？")){
   			var postData = $("#setting"+flag+"Form").serializeArray();
   			$.post($("#setting"+flag+"Form").attr("action"), postData, function (json) {
   	            //var data = $.parseJSON(json);
   	            if (json.resultStatus) {
   	                //成功
   	            	layer.msg("设置成功");
   	            	loadFrameContent('cms/hit/view');
   	            } else {
   	            	//
   	            	layer.alert("失败了："+ json.resultMsg, {icon: 2});
   	            }
   	        }, "json");
   		}
    }
</script>