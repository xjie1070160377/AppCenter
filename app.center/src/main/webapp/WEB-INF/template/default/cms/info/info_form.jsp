<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<link href="${ctxPath}/static/base/css/plugins/jsTree/style.min.css"
	rel="stylesheet">
<link href="${ctxPath}/static/base/css/form.css" rel="stylesheet">

<script type="text/javascript">
	window.UEDITOR_HOME_URL = "${ctxPath}/static/base/js/plugins/ueditor/";

</script>

<script type="text/javascript"
	src="${ctxPath}/static/base/js/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript"
	src="${ctxPath}/static/base/js/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"
	src="${ctxPath}/static/base/js/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<!--
	<script type="text/javascript" charset="utf-8" src="http://hgs.xiumi.us/uedit/ueditor.config.js"></script> 
   <script type="text/javascript" charset="utf-8" src="http://hgs.xiumi.us/uedit/ueditor.all.min.js"></script>
   <script type="text/javascript" src="http://hgs.xiumi.us/uedit/lang/zh-cn/zh-cn.js"></script>
-->	

<style type="text/css">
* html {
	overflow-y: scroll;
}

input[name='node_name'], input[name='specialNames'] {
	padding-right: 50px;
}

.form-control {
	padding: 0 12px;
	font-weight: 400;
}

.nav-tabs>li {
	margin: 0;
	position: relative;
	bottom: -2px;
}
/* .nav-tabs li a:link,.nav-tabs li a:focus{border:none;outline:none;} */
#nodeSelectDiv,#node2SelectDiv, #specialSelectDiv {
	position: absolute;
	top: 7px;
	right: 7px;
	height: 30px;
}

.nav-tabs {
	border-bottom: none;
}

a, a:hover, a:focus, a:link {
	outline: none;
}

.in-tb td.in-lab {
	background: #fff;
	font-weight: 700;
	color: #1AB394;
}

.in-tb label {
	font-weight: 400;
}

#sourceUrl_x, #sourceother {
	margin: 10px 0;
}

#datepicker {
	margin-bottom: 0;
}

.forwardLink {
	position: absolute;
	right: 15px;
	top: 10px;
}

#imagesUploadDiv .fileinput-button, #fileUploadDiv .fileinput-button {
	margin-left: 20px;
	margin-top: 20px;
}

#imagesUploadDiv .img_prev_div {
	padding-left: 5px;
}

#imagesUploadDiv .text-left {
	padding-left: 0;
}

#images_div>.col-lg-12.form-group {
	margin-left: 0;
}

.control-label.text-center {
	text-align: center;
}

.info-content-box {
	height: auto;
	border: none;
}

.info-content-box #edui1 {
	max-width: 100%;
}

.edui-default .edui-editor-toolbarboxouter {
	background: none;
}

.edui-default .edui-editor-toolbarbox {
	box-shadow: none;
}

.edui-default .edui-box {
	padding: 3px;
}

@
-moz-document url-prefix (){ .edui-default .edui-editor-toolbarbox {
	position: relative !important;
	left: 0 !important;
	top: 0 !important;
}

}
.overlay {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(255, 255, 255, .8);
	color: #000;
	text-align: center;
	padding-top: 20%;
	font-size: 16px;
	z-index: 99999;
}

.overlay  img {
	display: inline-block;
	margin-right: 6px;
	width: 24px;
	height: 24px;
}
</style>

<div class="col-sm-12">
	<div class="ibox-title">
		<h5>文档信息修改</h5>
	</div>
	<div class="ibox-title">
		<button class="btn btn-info " onclick="returnList();" type="button"
			id="btn_back">
			<i class="fa fa-mail-reply-all"></i> 返回
		</button>
		<c:if test="${bean.id == null}"></c:if>
		<button onclick="submitForm();" title='保存并返回' class="btn btn-info"
			id="btn_saveback" type="button">保存并返回</button>

		<c:if test="${istoend==true }">
			<button class="btn btn-info " type="button" id="btn_finalPass">
				保存并发稿</button>
		</c:if>
		<c:if test="${bean.id != null}">
			<button class="btn btn-info " type="button" id="btn_auditPass">
				<i class="fa fa-mail-reply-all"></i> 保存并上呈
			</button>
			<!-- <button class="btn btn-info " type="button" id="btn_auditReject"><i class="fa fa-mail-reply-all"></i> 撤销审核</button> -->
			<button class="btn btn-info " type="button" id="btn_auditReturn">
				<i class="fa fa-mail-reply-all"></i> 退稿
			</button>
			<!--<button class="btn btn-info " type="button" id="btn_adriodpush"><i class="fa fa-mail-reply-all"></i> 安卓推送</button>
		<button class="btn btn-info " type="button" id="btn_iospush"><i class="fa fa-mail-reply-all"></i> IOS推送</button>-->
			<button class="btn btn-info " type="button" id="btn_iosadriodpush">
				<i class="fa fa-mail-reply-all"></i> 一键推送
			</button>
		</c:if>
	</div>
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<form id="postForm" method="post" class="form-horizontal"
				action="cms/info/info${bean.id == null ? 'Save' : 'Update'}">
				<input type="hidden" name="cid" value="${cid}" /> <input
					type="hidden" name="oid" value="${bean.id}" /> <input
					type="hidden" name="queryNodeId" value="${queryNodeId}" /> <input
					type="hidden" name="showDescendants" value="${showDescendants}" />
				<input type="hidden" name="queryStatus" id="queryStatus"
					value="${queryStatus}" /> <input type="hidden"
					name="imageListJson" value="" /> <input type="hidden"
					name="hasContent" id="hasContent" value="0" />
				<ul id="tabs" class="nav nav-tabs" style="display: none;">
					<li class="active" id="li_fields"><a
						href="javascript:void(0);">文档标题及属性</a></li>
					<li id="li_content"><a href="javascript:void(0);">文档正文</a></li>
				</ul>
				<div class="form-group">
					<table id="info_fields" border="0" cellpadding="0" cellspacing="0"
						class="in-tb">
						<c:set var="colCount" value="${0}" />
						<c:forEach var="field" items="${model.normalFields}">
							<c:if test="${colCount%2==0||!field.dblColumn}">
								<tr>
							</c:if>
							<td class="in-lab" width="15%"><c:if
									test="${field.required}">
									<em class="req">*</em>
								</c:if> <c:out value="${field.label}" />:</td>
							<td <c:if test="${field.type!=50}"> class="in-ctt"</c:if>
								<c:choose><c:when test="${field.dblColumn}"> width="35%"</c:when><c:otherwise> width="85%" colspan="3"</c:otherwise></c:choose>>
								<c:choose>
									<c:when test="${field.custom || field.innerType == 3}">
										<tags:feild_custom bean="${bean}" customs="${customs }"
											field="${field}" />
									</c:when>
									<c:otherwise>
										<c:choose>

											<c:when test="${field.name eq 'node'}">
												<input type="hidden" id="nodeId" name="nodeId"
													value="${node.id}" />
												<input type="text" readonly="readonly" id="node_name"
													class="form-control" name="node_name" value="${node.name}">
												<div id="nodeSelectDiv"></div>
											</c:when>
											<c:when test="${field.name eq 'p1'}">
												<input type="hidden" id="node2Id" name="p1"
													value="${node2.id}" />
												<input type="text" readonly="readonly" id="node2_name"
													class="form-control" name="node2_name" value="${node2.name}">
												<div >
												<div id="node2SelectDiv"></div>
												</div>
											</c:when>

											<c:when test="${field.name eq 'specials'}">
												<input type="hidden" id="specialIds" name="specialIds"
													value="${specialIds}" />
												<input type="text" readonly="readonly" id="specialNames"
													class="form-control" name="specialNames"
													value="${specialNames}">
												<div id="specialSelectDiv"></div>
											</c:when>
											<c:when test="${field.name eq 'title'}">
												<div>
													<input type="text" class="form-control"
														placeholder="${field.label}" maxlength="150" id="title"
														name="title" value="${bean.title}" /> <label
														class="forwardLink"><input type="checkbox"
														onclick="changeLink();"
														<c:if test="${bean.linked}"> checked="checked"</c:if> />转向链接</label>
													<script type="text/javascript">
							    function changeLink(){
							    	$("#linkDiv").toggle(this.checked);
							    	if(!this.checked){
							    		$("input[name='link']").val('');
							    	}
							    }
						    </script>
												</div>
												<div id="linkDiv"
													style="padding-top:2px;<c:if test="${!bean.linked}">display:none;</c:if>">
													<input type="text" class="form-control" placeholder="超链接"
														maxlength="255" id="link" name="link" value="${bean.link}" />
												</div>
											</c:when>
											<c:when test="${field.name eq 'subtitle'}">
												<input type="text" class="form-control"
													placeholder="${field.label}" maxlength="150" id="subtitle"
													name="subtitle" value="${bean.subtitle}" />
											</c:when>
											<c:when test="${field.name eq 'fullTitle'}">
												<input type="text" class="form-control"
													placeholder="${field.label}" maxlength="150" id="fullTitle"
													name="fullTitle" value="${bean.fullTitle}" />
											</c:when>
											<c:when test="${field.name eq 'metaDescription'}">
												<input type="hidden" name="remainDescription" value="true" />
												<textarea class="form-control" placeholder="${field.label}"
													maxlength="255" name="metaDescription">${bean.metaDescription}</textarea>
											</c:when>
											<c:when test="${field.name eq 'priority'}">
												<select name="priority" style="width: 50px;">
													<c:forEach var="i" begin="0" end="9">
														<option
															<c:if test="${i==bean.priority}"> selected="selected"</c:if>>${i}</option>
													</c:forEach>
												</select>
											</c:when>
											<c:when test="${field.name eq 'publishDate'}">
												<div class="form-group input-daterange" id="datepicker">
													<div class="col-sm-5">
														<input type="text" id="publishDate" name="publishDate"
															class="form-control"
															value="<c:if test="${oprt=='edit'}"><fmt:formatDate value="${bean.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>"
															style="text-align: left" />
													</div>
													<label class="col-sm-1 control-label" for="offDate">至</label>
													<div class="col-sm-5">
														<input type="text" id="offDate" name="offDate"
															class="form-control"
															value="<c:if test="${oprt=='edit'}"><fmt:formatDate value="${bean.offDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>"
															style="text-align: left" />
													</div>
												</div>
											</c:when>
											<c:when test="${field.name eq 'infoTemplate'}">
												<input type="text" class="form-control" readonly="readonly"
													id="infoTemplate" name="infoTemplate"
													value="${bean.infoTemplate}">
												<div id="infoTemplateSelectDiv" class="SelectDiv"></div>
											</c:when>
											<c:when test="${field.name eq 'infoPath'}">
												<input type="text" class="form-control"
													placeholder="${field.label}" maxlength="255" id="infoPath"
													name="infoPath" value="${bean.infoPath}" />
											</c:when>
											<c:when test="${field.name eq 'source'}">
					  	来源：
					  	<select name="sourceId" onchange="changeSource(this);">
													<option value="" iurl="" iname="">...请选择...</option>
													<c:forEach items="${sourceList }" var="sitem">
														<option iname="${sitem.name }" iurl="${sitem.url }"
															value="${sitem.id }"
															<c:if test="${!empty bean.infoSource && sitem.id==bean.infoSource.id }">selected</c:if>>${sitem.name }</option>
													</c:forEach>
												</select>
					    	名称：
				  			<input type="text" style="width: 150px;" class="form-control"
													placeholder="${field.label}" maxlength="255"
													id="sourceother" name="source" value="${bean.source}" />
					    	地址:
					    	<input type="text" style="width: 200px;" class="form-control"
													placeholder="${field.label}" maxlength="255"
													id="sourceUrl_x" name="sourceUrl" value="${bean.sourceUrl}" />
												<input type="checkbox" name=sourceFlag value="1"
													<c:if test="${bean.sourceFlag=='1'}"> checked="checked"</c:if> />前端显示
					    <script type="text/javascript">
					    	function changeSource(obj){
					    		var opt = $(obj).find("option:selected");
					    		$("#sourceother").val(opt.attr("iname"));
					    		$("#sourceUrl_x").val(opt.attr("iurl"));
					    	}
					    </script>
											</c:when>
											<c:when test="${field.name eq 'author'}">
												<input type="text" class="form-control"
													placeholder="${field.label}" maxlength="50" id="author"
													name="author" value="${bean.author}" />
											</c:when>
											<c:when test="${field.name eq 'allowComment'}">
												<select name="allowComment">
													<option value="">--请选择--</option>
													<option value="true"
														<c:if test="${bean.allowComment!=null && bean.allowComment}">selected="selected"</c:if>>是</option>
													<option value="false"
														<c:if test="${bean.allowComment!=null && !bean.allowComment}">selected="selected"</c:if>>否</option>
												</select>
											</c:when>
											<c:when test="${field.name eq 'attributes'}">
												<c:set var="attrs" value="${bean.attrs}" />
												<c:forEach var="attr" items="${attrList}">
													<label><input type="checkbox" name="attrIds"
														value="${attr.id}"
														onclick="$('#attr_img_${attr.id}').toggle(this.checked);"
														<c:if test="${fnx:contains_co(attrs,attr)}"> checked="checked"</c:if> />
														<c:out value="${attr.name}" />(<c:out
															value="${attr.number}" />)</label> &nbsp;
					  	</c:forEach>
												<c:forEach var="attr" items="${attrList}">
													<c:if test="${attr.withImage}">
							</td>
							</tr>
							<tr id="attr_img_${attr.id}"
								<c:if test="${!fnx:contains_co(attrs,attr)}"> style="display:none;"</c:if>>
								<td class="in-lab" width="15%"><em class="req">*</em>${attr.name}
								</td>
								<td class="in-ctt" width="85%" colspan="3">
									<div id="attrImages_${attr.id}Div"
										data-with="${attr.imageWidth}"
										data-height="${attr.imageHeight}"
										data-value="${fnx:invoke1(bean,'getInfoAttr',attr).image}"
										data-watermark="${attr.watermark}" data-scale="${attr.scale}"
										data-exact="${attr.exact}"></div> </c:if>
						</c:forEach>
						</c:when>
						<c:when test="${field.name eq 'smallImage'}">
							<div id="smallImageUploadDiv"
								data-with="${field.customs['imageWidth']}"
								data-height="${field.customs['imageHeight']}"
								data-value="${bean.smallImage}"
								data-watermark="${field.customs['imageWatermark']}"
								data-scale="${field.customs['imageScale']}"
								data-stretch="${field.customs['imageExact']}"></div>
						</c:when>

						<c:when test="${field.name eq 'largeImage'}">
							<div id="largeImageUploadDiv"
								data-with="${field.customs['imageWidth']}"
								data-height="${field.customs['imageHeight']}"
								data-value="${bean.largeImage}"
								data-watermark="${field.customs['imageWatermark']}"
								data-scale="${field.customs['imageScale']}"
								data-stretch="${field.customs['imageExact']}"></div>
						</c:when>
						<c:when test="${field.name eq 'file'}">
							<div id="fileUploadDiv" data-fileNameId="fileName"
								data-fileName="${bean.fileName}" data-fileUrlId="file"
								data-fileUrl="${bean.file}" data-fileSizeId="fileLength"
								data-fileSize="${bean.fileLength}"></div>
						</c:when>
						<c:when test="${field.name eq 'video'}">
							<div id="videoUploadDiv" data-fileNameId="videoName"
								data-fileName="${bean.videoName}" data-fileUrlId="video"
								data-fileUrl="${bean.video}" data-fileSizeId="videoLength"
								data-fileSize="${bean.videoLength}" data-fileTimeId="videoTime"
								data-fileTime="${bean.videoTime}"></div>
						</c:when>
						<c:when test="${field.name eq 'audio'}">
							<div id="audioUploadDiv" data-fileNameId="audioName"
								data-fileName="${bean.audioName}" data-fileUrlId="audio"
								data-fileUrl="${bean.audio}" data-fileSizeId="audioLength"
								data-fileSize="${bean.audioLength}" data-fileTimeId="audioTime"
								data-fileTime="${bean.audioTime}"></div>
						</c:when>
						<c:when test="${field.name eq 'images'}">
							<div id="imagesUploadDiv"
								data-exact="${field.customs['imageExact']}"
								data-watermark="${field.customs['imageWatermark']}"
								data-scale="${field.customs['imageScale']}"
								data-with="${field.customs['imageWidth']}"
								data-height="${field.customs['imageHeight']}"
								data-imgListJson="${imgListJson}"></div>
						</c:when>
						<c:when test="${field.name eq 'tagKeywords'}">
							<input type="text" class="form-control"
								placeholder="${field.label}" maxlength="150" id="tagKeywords"
								name="tagKeywords" value="${bean.tagKeywords}" />
							<%-- <input type="button" value="<s:message code='info.getTagKeywords'/>" onclick="var button=this;$(button).prop('disabled',true);$.get('get_keywords.do',{title:$('input[name=title]').val()},function(data){$('input[name=tagKeywords]').val(data);$(button).prop('disabled',false);})"/> --%>
						</c:when>
						<c:otherwise>
					    System field not found: '${field.name}'
					  </c:otherwise>
						</c:choose>
						</c:otherwise>
						</c:choose>
						</td>
						<c:if test="${colCount%2==1||!field.dblColumn}">
							</tr>
						</c:if>
						<c:if test="${field.dblColumn}">
							<c:set var="colCount" value="${colCount+1}" />
						</c:if>
						</c:forEach>
					</table>
					<div id="info_content">
						<div style="clear: both;"></div>
						<c:forEach var="field" items="${model.editorFields}">
							<%-- <div style="padding:5px 3px;">
						  <c:if test="${field.required}"><em class="req">*</em></c:if><c:out value="${field.label}"/>:
						</div> --%>
							<div>
								<c:choose>
									<c:when test="${field.name eq 'text'}">
										<textarea class="form-control info-content-box"
											placeholder="${field.label}" id="clobs_text"
											name="clobs_text">${bean.text}</textarea>
										<script type="text/javascript">
						  $(document).ready(function(){
							  var initialFrameHeight = document.body.scrollHeight - 640;
								if(initialFrameHeight < 0) {
									initialFrameHeight = 400;
								}
								<c:if test="${!empty field.customs['height']}"> initialFrameHeight = ${field.customs['height']};</c:if>
							    var editor_clobs_text = UE.getEditor('clobs_text',{
							    	<c:if test="${!empty field.customs['toolbar']}">toolbars: window.UEDITOR_CONFIG.toolbars_${field.customs['toolbar']}Page,</c:if>
						        <c:if test="${!empty field.customs['width']}">initialFrameWidth:${field.customs['width']},</c:if>
						        initialFrameHeight:initialFrameHeight,
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
										<tags:feild_custom bean="${bean}" customs="${customs }"
											field="${field}" />
									</c:otherwise>
								</c:choose>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-12 text-right">
						<c:if test="${oprt=='create'}">
							<input type="hidden" id="draft" name="draft" value="false" />
							<button onclick="draftForm();" title='保存草稿'
								class="btn btn-primary" type="button">保存草稿</button>
						</c:if>
						<button onclick="submitForm();" title='保存并返回'
							class="btn btn-primary" type="button">保存并返回</button>
						<c:if test="${bean.status=='B'}">
							<input type="hidden" id="pass" name="pass" value="false" />
							<button onclick="vForm();" title='审核并返回' class="btn btn-primary"
								type="button">保存并上呈</button>
						</c:if>
						<c:if test="${bean.status=='X'}">
							<input type="hidden" id="recall" name="recall" value="false" />
							<button onclick="recallForm();" title='召回并返回'
								class="btn btn-primary" type="button">召回并返回</button>
						</c:if>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>


<script type="text/javascript">

    $(document).ready(function () {

    	$("input[name='title']").focus();
        
        $('#nodeSelectDiv').load('cms/selector/nodeTag?id=nodeSelector&callback=nodeSelectCallBack&selectedId=${node.id}');
        $('#node2SelectDiv').load('cms/selector/nodeTag2?id=node2Selector&callback=node2SelectCallBack&selectedId=${node.id}');

        $('#specialSelectDiv').load('cms/selector/specialSelector?id=specialSelector&callback=specialSelectCallBack&infoId=${bean.id}');
        $('#infoTemplateSelectDiv').load('cms/selector/templateTag?id=infoTemplateSelect&callback=infoTemplateSelectCallBack&selectedId=${bean.infoTemplate}');
        loadImageUploadDiv("smallImageUploadDiv", "smallImage");
        loadImageUploadDiv("largeImageUploadDiv", "largeImage");
        loadFileUploadDiv("file");
        loadVideoUploadDiv("video");
        loadAudioUploadDiv("audio");
        loadImagesUploadDiv("images");
        
        
        //表单验证className
        $("#postForm").validate({
            rules: {
            	title: {
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

        <c:forEach var="field" items="${model.editorFields}">
        	<c:choose>
        	<c:when test="${field.name eq 'text'}">	
	    		$('#tabs').show();
	    		$('#info_content').hide();
	    		$('#hasContent').val(1);
	    	</c:when>
	    	<c:otherwise>
	    	</c:otherwise>
        	</c:choose>
        </c:forEach>

	  	<c:forEach var="attr" items="${attrList}">
	  	<c:if test="${attr.withImage}">
        loadImageUploadDiv("attrImages_${attr.id}Div", "attrImages_${attr.id}");
	  	</c:if>
	  	</c:forEach>
	  	
    	$("#tabs li").click(function() {
    		var idStr = this.id;
    		var contentStr = idStr.replace('li','info');
    		$("#tabs li").removeClass("active");
    		$("#"+idStr).addClass("active");
    		$("#info_fields").hide();
    		$("#info_content").hide();
    		$("#"+contentStr).show();
    	})
    	
    	$('#publishDate').datetimepicker({
            language:  'zh-CN',
            pickTime:false,
            format:'yyyy-mm-dd hh:ii:ss',
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1
        });
    	
    	$('#offDate').datetimepicker({
            language:  'zh-CN',
            pickTime:false,
            format:'yyyy-mm-dd hh:ii:ss',
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1
        });
    });
    
    function loadImageUploadDiv(id, name) {
    	var data_with = $('#' + id).attr('data-with');
    	var data_height = $('#' + id).attr('data-height');
    	var data_value = $('#' + id).attr('data-value');
    	var data_watermark = $('#' + id).attr('data-watermark');
    	var data_scale = $('#' + id).attr('data-scale');
    	//alert("id:"+id+";data_scale:"+data_scale);
    	var data_stretch = $('#' + id).attr('data-stretch');
    	if(!data_stretch){
    		data_stretch=false;
    	}
    	$('#' + id).load('cms/selector/imgUploadTag?id='+name+'&width='+data_with+'&height='+data_height+'&value='+data_value+'&scale='+data_scale+'&watermark='+data_watermark+'&stretch='+data_stretch);
    }
    
    function loadFileUploadDiv(id) {
    	var divId = id + "UploadDiv";
    	var data_fileNameId = $('#' + divId).attr('data-fileNameId');
    	var data_fileName = $('#' + divId).attr('data-fileName');
    	var data_fileUrlId = $('#' + divId).attr('data-fileUrlId');
    	var data_fileUrl = $('#' + divId).attr('data-fileUrl');
    	var data_fileSizeId = $('#' + divId).attr('data-fileSizeId');
    	var data_fileSize = $('#' + divId).attr('data-fileSize');
    	
    	$('#' + divId).load('cms/selector/fileUploadTag?id='+id+'&fileNameId='+data_fileNameId+'&fileName='+encodeURI(encodeURI(data_fileName))+'&fileUrlId='+data_fileUrlId+'&fileUrl='+data_fileUrl+'&fileSizeId='+data_fileSizeId+'&fileSize='+data_fileSize);
    }
    
    function loadVideoUploadDiv(id) {
    	var divId = id + "UploadDiv";
    	var data_fileNameId = $('#' + divId).attr('data-fileNameId');
    	var data_fileName = $('#' + divId).attr('data-fileName');
    	var data_fileUrlId = $('#' + divId).attr('data-fileUrlId');
    	var data_fileUrl = $('#' + divId).attr('data-fileUrl');
    	var data_fileSizeId = $('#' + divId).attr('data-fileSizeId');
    	var data_fileSize = $('#' + divId).attr('data-fileSize');
    	var data_fileTimeId = $('#' + divId).attr('data-fileTimeId');
    	var data_fileTime = $('#' + divId).attr('data-fileTime');
    	$('#' + divId).load('cms/selector/videoUploadTag?id='+id+'&fileNameId='+data_fileNameId+'&fileName='+encodeURI(encodeURI(data_fileName))+'&fileUrlId='+data_fileUrlId+'&fileUrl='+data_fileUrl+'&fileSizeId='+data_fileSizeId+'&fileSize='+data_fileSize+'&fileTimeId='+data_fileTimeId+'&fileTime='+data_fileTime);
    }
    
    function loadAudioUploadDiv(id) {
    	var divId = id + "UploadDiv";
    	var data_fileNameId = $('#' + divId).attr('data-fileNameId');
    	var data_fileName = $('#' + divId).attr('data-fileName');
    	var data_fileUrlId = $('#' + divId).attr('data-fileUrlId');
    	var data_fileUrl = $('#' + divId).attr('data-fileUrl');
    	var data_fileSizeId = $('#' + divId).attr('data-fileSizeId');
    	var data_fileSize = $('#' + divId).attr('data-fileSize');
    	var data_fileTimeId = $('#' + divId).attr('data-fileTimeId');
    	var data_fileTime = $('#' + divId).attr('data-fileTime');
    	$('#' + divId).load('cms/selector/audioUploadTag?id='+id+'&fileNameId='+data_fileNameId+'&fileName='+encodeURI(encodeURI(data_fileName))+'&fileUrlId='+data_fileUrlId+'&fileUrl='+data_fileUrl+'&fileSizeId='+data_fileSizeId+'&fileSize='+data_fileSize+'&fileTimeId='+data_fileTimeId+'&fileTime='+data_fileTime);
    }
    
    function loadImagesUploadDiv(id) {
    	var divId = id + "UploadDiv";
    	var data_with = $('#' + divId).attr('data-with');
    	var data_height = $('#' + divId).attr('data-height');
    	var data_imgListJson = $('#' + divId).attr('data-imgListJson');
    	var data_stretch = $('#' + divId).attr('data-stretch');
    	var data_watermark = $('#' + divId).attr('data-watermark');
    	var data_scale = $('#' + divId).attr('data-scale');
    	var url = "sys/uploadImage";
    	$('#' + divId).load('cms/selector/imagesUploadTag?id='+id+'&width='+data_with+'&height='+data_height+'&stretch='+data_stretch
    			+'&watermark='+data_watermark+'&scale='+data_scale+'&url='+url, {imgListJson:data_imgListJson});
    }
    
    function changeModel(url){
    	loadFrameContent(url);
    }
	
    function returnList(){
    	loadFrameContent('cms/info/infoList?queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&queryStatus=${queryStatus}&start=${start}&rnd='+ Math.random());
    }
    
    function nodeSelectCallBack(id, text){
    	
    	$.post("cms/info/hasToEnd", {
			nodeId : id
			}, function(json) {
				if (json.resultStatus == true) {//成功
					addButton();
					$('#nodeId').val(id);
			    	$('#node_name').val(text);
				} else {//失败
					delButton();
					$('#nodeId').val(id);
			    	$('#node_name').val(text);
				}
			}, 'json');
	}
function node2SelectCallBack(id, text){
    	
    	$.post("cms/info/hasToEnd", {
			nodeId : id
			}, function(json) {
				if (json.resultStatus == true) {//成功
					addButton();
					$('#node2Id').val(id);
			    	$('#node2_name').val(text);
				} else {//失败
					delButton();
					$('#node2Id').val(id);
			    	$('#node2_name').val(text);
				}
			}, 'json');
	}
    
    function addButton(){
    	if($("#btn_finalPass").length == 0){
	    	$("#btn_saveback").after('&nbsp;<button class="btn btn-info " type="button" id="btn_finalPass"> 保存并发稿</button>');
	    	$("#btn_finalPass").click(saveAndSend);
    	}
    }
    
    function delButton(){
    	if($("#btn_finalPass").length > 0){
    		$("#btn_finalPass").remove();
    	}
    }
    
    function specialSelectCallBack(idlist){
    	var specialIds = '';
    	var specialNames = '';
    	$.each(idlist, function(){
    		specialIds += (specialIds == ''?'':',')+this.id;
    		specialNames += (specialNames == ''?'':',')+this.text;
    	})
    	$('#specialIds').val(specialIds);
    	$('#specialNames').val(specialNames);
    }
    
    function draftForm(){
    	$('#draft').val('true');
    	//overlay();
    	submitForm();
    }
    
    function vForm() {
    	$('#pass').val('true');
    	$('#queryStatus').val('A');
    	submitForm();
    }
    
    function recallForm() {
    	$('#recall').val('true');
    	$('#queryStatus').val('A');
    	submitForm();
    }
    
    function submitForm(){
    	if($("#postForm").valid()==false){
    		return;
    	}
    	if(document.getElementById('imagesUploadDiv')){
        	if(getImgList()){
        		$("input[name='imageListJson']").val(getImgList());
        	}
    	}
    	/* var hasContent = $('#hasContent').val();
    	if(hasContent == 1) {
    		var content = UE.getEditor('clobs_text').getContent();
    		if(content == null || content == '') {
        		$("#tabs li").removeClass("active");
        		$("#li_content").addClass("active");
        		$("#info_fields").hide();
        		$("#info_content").show();
    			layer.alert("请录入文档正文！", {icon: 2});
    			return false;
    		}
    	} */
    	var queryStatus = $('#queryStatus').val();
    	var postData = $("#postForm").serializeArray();
    	overlay();
		$.post($("#postForm").attr("action"), postData, function (json) {
            //var data = $.parseJSON(json);
            if (json.resultStatus) {
                //成功
                $(".overlay").remove();
            	loadFrameContent('cms/info/infoList?queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&start=${start}&queryStatus=' + queryStatus);
            } else {
            	//
            	$(".overlay").remove();
            	layer.alert("失败了："+ json.resultMsg, {icon: 2});
            }
        }, "json");
    	
    }
    
    $("#btn_finalPass").click(saveAndSend);
    function saveAndSend(){
    	if(confirm("确认直接发稿吗？")){
	    	if($("#postForm").valid()==false){
	    		return;
	    	}
	    	if(document.getElementById('imagesUploadDiv')){
	        	if(getImgList()){
	        		$("input[name='imageListJson']").val(getImgList());
	        	}
	    	}
	    	var postData = $("#postForm").serializeArray();
	    	overlay();
			$.post($("#postForm").attr("action"), postData, function (json) {
	            if (json.resultStatus) {
	                //成功
	                $(".overlay").remove();
	                loadFrameContent('cms/info/toend?id='+json.resultMsg+'&queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&queryStatus=${queryStatus}&start=${start}&rnd='+ Math.random());
	            } else {
	            	//
	            	$(".overlay").remove();
	            	layer.alert("失败了："+ json.resultMsg, {icon: 2});
	            }
	        }, "json");
    	}
    }
    $("#btn_auditPass").click(function(){
    	if('${bean.status}'=='A'){
    		layer.alert("文章已发布，不需要审核", {icon: 2});
    	}
    	$.post("cms/info/hasWorkflow", {
			id : '${bean.id}'
		}, function(json) {
			if (json.resultStatus == true) {//成功
				loadFrameContent('cms/info/audit_pass?ids=${bean.id}&queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&queryStatus=${queryStatus}&start=${start}&rnd='+ Math.random());
			} else {//失败
				if(confirm("文章所在栏目没有绑定工作流，此操作将会直接发布文章，确认执行此操作吗？")){
					loadFrameContent('cms/info/audit_pass?ids=${bean.id}&queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&queryStatus=${queryStatus}&start=${start}&rnd='+ Math.random());
				}
			}
		}, 'json');
    });
    $("#btn_auditReject").click(function(){
    	loadFrameContent('cms/info/audit_reject?ids=${bean.id}&queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&queryStatus=${queryStatus}&start=${start}&rnd='+ Math.random());
    });
    $("#btn_auditReturn").click(function(){
    	layer.prompt({
  		  formType: 2,
  		  value: '',
  		  title: '请输入退搞原因'
	  	}, function(value, index, elem){
		    	loadFrameContent('cms/info/audit_return?ids=${bean.id}&queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&queryStatus=${queryStatus}&start=${start}&opinion='+value+'&rnd='+ Math.random());
	  		  layer.close(index);
	  	});
    });
    
    $("#btn_adriodpush").click(function(){
    	pushInfo(1);
    });
    
    $("#btn_iospush").click(function(){
    	pushInfo(2);
    });
    
    $("#btn_iosadriodpush").click(function(){
    	pushInfo(3);
    });
    
    function pushInfo(flag){
    	$.post("cms/info/pushInfo", {id :'${bean.id}', flag:flag}, function(json) {
    		alert("推送成功！");
		}, 'text');
	}
    
    //ueditor全屏问题
    $("#edui3_body").bind("click", function(){
    	var $edui1 = $("#edui1");
    	if($edui1.length > 0){
    		if($edui1.data("fullscreen") == "true"){
    			$edui1.css("max-width","100%");
    			$edui1.data("fullscreen", "false");
    		}else{
    			var bodyWidth = document.body.clientWidth;
        		$edui1.css({"max-width": bodyWidth + "px", "z-index":"9999"});
        		$edui1.data("fullscreen", "true");
    		}
    	}
    });
    
    //遮罩层
    var overlay = function(){
    	var html = '<div class="overlay"><img src="${ctxPath}/static/base/images/loading.gif"/>保存中……</div>';
    	$("body").append(html);
    }
    
</script>