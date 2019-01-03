<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<link href="${ctxPath}/static/base/css/plugins/jsTree/style.min.css" rel="stylesheet">
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<style>
	.info-content-box{height:auto;border:none;}
	.info-content-box #edui1{max-width:100%;width:100%!important;}
	.edui-default .edui-editor-toolbarboxouter{background:none;}
	.edui-default .edui-editor-toolbarbox{box-shadow:none;}
	.edui-default .edui-box{padding:3px;}
	@-moz-document url-prefix(){
		.edui-default .edui-editor-toolbarbox{ position:relative!important;left:0!important;top:0!important;}
	}
	.layui-layer-content{height: calc(100% - 100px)!important;}
	
</style>

<div class="col-sm-12">
	<div class="ibox-title">
		<h5>栏目信息修改</h5>
	</div>
	<div class="ibox-title">
		<button class="btn btn-info " onclick="returnList();" type="button" id="btn_back"><i class="fa fa-mail-reply-all"></i> 返回</button>
	</div>
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <form id="postForm" method="post" class="form-horizontal" action="cms/node/node${bean.id == null ? 'Save' : 'Update'}">
			   	<input type="hidden" name="cid" value="${cid}" />
			   	<input type="hidden" name="oid" value="${bean.id}" />
			   	<input type="hidden" name="queryParentId" value="${queryParentId}" />
			   	<input type="hidden" name="showDescendants" value="${showDescendants}" />
				<div class="form-group">
				<table border="0" cellpadding="0" cellspacing="0" class="in-tb">

				  <c:set var="colCount" value="${0}"/>
				  <c:forEach var="field" items="${model.normalFields}">
				  <c:if test="${colCount%2==0||!field.dblColumn}"><tr></c:if>
				  <td class="in-lab" width="15%"><c:if test="${field.required}"><em class="req">*</em></c:if><c:out value="${field.label}"/>:</td>
				  <td<c:if test="${field.type!=50}"> class="in-ctt"</c:if><c:choose><c:when test="${field.dblColumn}"> width="35%"</c:when><c:otherwise> width="85%" colspan="3"</c:otherwise></c:choose>>
				  <c:choose>
				  <c:when test="${field.custom || field.innerType == 3}">
				  	<tags:feild_custom bean="${bean}" customs="${customs }" field="${field}"/>
				  </c:when>
				  <c:otherwise>
				  <c:choose>
				  <c:when test="${field.name eq 'parent'}">
				  	<input type="hidden" id="parentId" name="parentId" value="${parent.id}" />
			  	  	<input type="text" readonly="readonly" id="parentName" class="form-control" name="parentName" value="${parent.name}"/>
			  	  	
			  	  	<div id="parentSelectDiv"></div>
			  	  	
				  </c:when>
				  		  
				  <c:when test="${field.name eq 'name'}">
				  	<input type="text" class="form-control" placeholder="${field.label}" maxlength="40" id="name" name="name" value="${bean.name}"/>
				  </c:when>
				  <c:when test="${field.name eq 'number'}">
				  	<input type="text" class="form-control" placeholder="${field.label}" maxlength="40" id="number" name="number" value="${bean.number}"/>
				  </c:when>
				  <c:when test="${field.name eq 'link'}">
				  	<input type="text" class="form-control" placeholder="${field.label}" maxlength="255" id="link" name="link" value="${bean.link}"/>
				  </c:when>
				  <c:when test="${field.name eq 'metaKeywords'}">
				  	<input type="text" class="form-control" placeholder="${field.label}" maxlength="150" id="metaKeywords" name="metaKeywords" value="${bean.metaKeywords}"/>
				  </c:when>
				  
				  <c:when test="${field.name eq 'metaDescription'}">
				  	<textarea class="form-control" placeholder="${field.label}" maxlength="150" name="metaDescription">${bean.metaDescription}</textarea>
				  </c:when>
				  <c:when test="${field.name eq 'workflow'}">
				    <select class="input-sm form-control input-s-sm inline" name="workflowId">
				    	<option value="">--请选择--</option>
				    	<c:forEach var="item" items="${workflowList}">
				    		<option value="${item.id}" <c:if test="${(bean.workflow!=null && item.id==bean.workflow.id) || (bean.id==null && item.id==field.defValue)}">selected="selected"</c:if>>${item.name}</option>
				    	</c:forEach>
				    </select>
				  </c:when>

				    <c:when test="${field.name eq 'nodeModel'}">
				    <select class="input-sm form-control input-s-sm inline" name="nodeModelId" onchange="changeModel('cms/node/${bean.id == null ? 'nodeAdd' : 'nodeEdit'}.do?id=${bean.id}&parentId=${parent.id}&queryParentId=${queryParentId}&showDescendants=${showDescendants}&modelId='+$(this).val());">
				    	<c:forEach var="item" items="${nodeModelList}">
				    		<option value="${item.id}" <c:if test="${item.id==model.id}">selected="selected"</c:if>>${item.name}</option>
				    	</c:forEach>
				    </select>
				  </c:when>
				  <c:when test="${field.name eq 'infoModel'}">
				    <select name="infoModelId" class="input-sm form-control input-s-sm inline">
				    	<c:forEach var="item" items="${infoModelList}">
				    		<option value="${item.id}" <c:if test="${item.id==infoModel.id}">selected="selected"</c:if>>${item.name}</option>
				    	</c:forEach>
				    </select>
				  </c:when>
				  <c:when test="${field.name eq 'nodeTemplate'}">
             			<input type="text" class="form-control" readonly="readonly" id="nodeTemplate" name="nodeTemplate" value="${bean.nodeTemplate}"> 
             			<div id="nodeTemplateSelectDiv" class="SelectDiv"></div>
				  </c:when>
				  <c:when test="${field.name eq 'infoTemplate'}">
             			<input type="text" class="form-control" readonly="readonly" id="infoTemplate" name="infoTemplate" value="${bean.infoTemplate}"> 
             			<div id="infoTemplateSelectDiv" class="SelectDiv"></div>
				  </c:when>
				  <c:when test="${field.name eq 'smallImage'}">
				  	<div id="smallImageUploadDiv" data-with="${field.customs['imageWidth']}" data-height="${field.customs['imageHeight']}" data-value="${bean.smallImage}" data-watermark="${field.customs['imageWatermark']}" data-scale="${field.customs['imageScale']}" data-stretch="${field.customs['imageExact']}"></div>
				  </c:when>
				  <c:when test="${field.name eq 'largeImage'}">
				  	<div id="largeImageUploadDiv" data-with="${field.customs['imageWidth']}" data-height="${field.customs['imageHeight']}" data-value="${bean.largeImage}" data-watermark="${field.customs['imageWatermark']}" data-scale="${field.customs['imageScale']}" data-stretch="${field.customs['imageExact']}"></div>
				  </c:when>
				  <c:otherwise>
				    System field not found: '${field.name}'
				  </c:otherwise>
				  </c:choose>
				  </c:otherwise>
				  </c:choose>
				  </td><c:if test="${colCount%2==1||!field.dblColumn}"></tr></c:if>
				  <c:if test="${field.dblColumn}"><c:set var="colCount" value="${colCount+1}"/></c:if>
				  </c:forEach>
				  
				  <tr>
				      <td class="in-lab" width="15%">
				                  栏目显示类型：
				      </td>
				      <td class="in-ctt" width="35%">
				        <select id="showType" name="show_type_id" class="input-sm form-control input-s-sm inline">
				            <option value="0">两列样式(图片宽高比1:1)</option>
				    		<option value="1">三列样式(图片宽高比4:3)</option>
				    		<option value="2">四列样式(图片宽高比3:4)</option>
				    		<option value="3">滑动样式(图片宽高比4:3)</option>
				    		<option value="4">四列样式(图片宽高比1:1)</option>
				    		<option value="5">左图右标题(图片宽高比4:3)</option>
				    		<option value="6">左标题右作者(无图片)</option>
				    		<option value="7">左图右简介-个人之星(图片宽高比3:4)</option>
				    		<option value="8">左右左三图片(图片宽高比9:5)</option>
				    		<option value="9">音频样式(图片宽高比7:2)</option>
				    		<option value="10">小图轮播样式(图片宽高比9:2)</option>
				    		<option value="11">普通轮播样式(图片宽高比16:9)</option>
				    		<option value="12">两边被遮挡轮播样式(图片宽高比16:9)</option>
				    		<option value="13">专题样式(图片宽高比12:5)</option>
				    		<option value="14">二行四列样式(图片宽高比1:1)</option>
				    		<option value="15">左标题右图片(图片宽高比4:3)</option>
				    		<option value="16">可左右切换视频样式(图片宽高比16:9)</option>
				    		<option value="17">强军两列样式(图片宽高比16:9)</option>
				        </select>
				      </td>
				      <td class="in-lab" width="15%">
				          是否显示标题：
				      </td>
				      <td  class="in-ctt" width="35%">
				        <select id="showTitle" name="isShowTitle" class="input-sm form-control input-s-sm inline">
				    		<option value="1">是</option>
				    		<option value="0">否</option>
				        </select>
				      </td>
				  </tr>
				  
				  
				</table>
				<c:forEach var="field" items="${model.editorFields}">
				  <div style="padding:5px 3px;">
				    <c:if test="${field.required}"><em class="req">*</em></c:if><c:out value="${field.label}"/>:
				  </div>
				  <div>
				  <c:choose>
				  <c:when test="${field.name eq 'text'}">
				    <textarea class="form-control info-content-box" placeholder="${field.label}" id="clobs_text" name="clobs_text">${bean.text}</textarea>
				    <script type="text/javascript">
				    $(function() {
				      var editor_clobs_text = UE.getEditor('clobs_text',{
				          <c:if test="${!empty field.customs['toolbar']}">toolbars: window.UEDITOR_CONFIG.toolbars_${field.customs['toolbar']}Page,</c:if>
				          <c:if test="${!empty field.customs['width']}">initialFrameWidth:${field.customs['width']},</c:if>
				          <c:if test="${!empty field.customs['height']}">initialFrameHeight:${field.customs['height']},</c:if>
					      imageUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_image",
					      wordImageUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_image",
					      fileUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_file",
					      videoUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_file",
					      audioUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_file",
					      catcherUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_file",
					      imageManagerUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_file",
					      getMovieUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_file",
				          localDomain:['${!empty GLOBAL.uploadsDomain ? GLOBAL.uploadsDomain : ""}']
				        });
				    });
				    </script>
				  </c:when>
				  <c:otherwise>
				   <tags:feild_custom bean="${bean}" customs="${customs }" field="${field}"/>
				  </c:otherwise>
				  </c:choose>
				  </div>
				</c:forEach>
				</div>
	            <div class="form-group">
	                <div class="col-sm-12 text-right">
	                    <button onclick="submitForm();" title='保存并返回' class="btn btn-primary" type="button">保存并返回</button>
	                </div>
	            </div>
	        </form>
        </div>
    </div>
</div>


<script type="text/javascript">

    $(document).ready(function () {

    	$("input[name='name']").focus();
    	$("#showType").val(${bean.show_type_id});
    	$("#showTitle").val(${bean.isShowTitle});
        //表单验证
        $("#postForm").validate({
            rules: {
            	name: {
                    required: true,
                    minlength: 2
                }
		        <c:forEach var="field" items="${model.normalFields}">
		        ,${field.name}: {
		        	<c:if test="${field.required}">
                    required: true,
                    </c:if>
                }
		        </c:forEach>
            }
        });
        
        $('#parentSelectDiv').load('cms/selector/nodeTag?id=nodeSelect&callback=nodeSelectCallBack&selectedId=${parent.id}');
       
        $('#nodeTemplateSelectDiv').load('cms/selector/templateTag?id=nodeTemplateSelect&callback=nodeTemplateSelectCallBack&selectedId=${bean.nodeTemplate}');
        $('#infoTemplateSelectDiv').load('cms/selector/templateTag?id=infoTemplateSelect&callback=infoTemplateSelectCallBack&selectedId=${bean.infoTemplate}');
        loadImageUploadDiv("smallImageUploadDiv", "smallImage");
        loadImageUploadDiv("largeImageUploadDiv", "largeImage");
    });
    
    function loadImageUploadDiv(id, name) {
    	var data_with = $('#' + id).attr('data-with');
    	var data_height = $('#' + id).attr('data-height');
    	var data_value = $('#' + id).attr('data-value');
    	var data_watermark = $('#' + id).attr('data-watermark');
    	var data_scale = $('#' + id).attr('data-scale');
    	var data_stretch = $('#' + id).attr('data-stretch');
    	if(data_stretch == 'undefined' || typeof(data_stretch) == 'undefined') {
    		data_stretch = '';
    	}
    	
    	$('#' + id).load('cms/selector/imgUploadTag?id='+name+'&width='+data_with+'&height='+data_height+'&value='+data_value+'&scale='+data_scale+'&watermark='+data_watermark+'&stretch='+data_stretch);
    }
	
    function changeModel(url){
    	loadFrameContent(url);
    }
	
    function returnList(){
    	loadFrameContent('cms/node/nodeList?queryParentId=${queryParentId}&showDescendants=${showDescendants}');
    }
    
    function nodeSelectCallBack(id, text){
    	$('#parentId').val(id);
    	$('#parentName').val(text);
	}
    
    function nodeTemplateSelectCallBack(text){
    	$('#nodeTemplate').val(text);
	}

    function infoTemplateSelectCallBack(text){
    	$('#infoTemplate').val(text);
	}
    
    function submitForm(){
    	if($("#postForm").valid()==false){
    		return;
    	}
    	var postData = $("#postForm").serializeArray();
		$.post($("#postForm").attr("action"), postData, function (json) {
            //var data = $.parseJSON(json);
            if (json.resultStatus) {
                //成功
            	loadFrameContent('cms/node/nodeList?queryParentId=${queryParentId}&showDescendants=${showDescendants}');
            } else {
            	//
            	layer.alert("失败了："+ json.resultMsg, {icon: 2});
            }
        }, "json");
    	
    }
</script>