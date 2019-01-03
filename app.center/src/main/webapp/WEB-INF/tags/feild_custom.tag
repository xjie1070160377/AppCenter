<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ attribute name="field" type="java.lang.Object" required="true" rtexprvalue="true"%>
<%@ attribute name="bean" type="java.lang.Object" required="true" rtexprvalue="true"%>
<%@ attribute name="customs" type="java.lang.Object" required="true" rtexprvalue="true"%>
<!-- <font color="red">${field.name }${field.type }<p></font> -->
  <c:choose>
    <c:when test="${field.innerType==3}">
      <c:set var="c_name" value="${field.name}"/>
    </c:when>
    <c:otherwise>
    	<c:set var="c_name"><c:out value="${(field.clob) ? ('clobs_') : ('customs_')}${field.name}"/></c:set>
    </c:otherwise>
  </c:choose>
  <c:choose>
    <c:when test="${field.innerType==3}">
      <c:set var="c_value" value="${bean[field.name]}"/>
    </c:when>
    <c:when test="${field.clob}">
      <c:set var="c_value" value="${bean.clobs[field.name]}"/>
    </c:when>
    <c:otherwise>
      <c:set var="c_value" value="${customs[field.name]}"/>
    </c:otherwise>
  </c:choose>
	<%--<c:set var="c_value"><c:out value="${(field.clob) ? (bean.clobs[field.name]) : (bean.customs[field.name])}"/></c:set>--%>
	<c:if test="${oprt=='create' && empty c_value}"><c:set var="c_value" value="${field.defValue}"/></c:if>
	<c:set var="style_width"><c:if test="${!empty field.customs['width']}">width:${field.customs['width']}px;</c:if></c:set>
	<c:set var="style_height"><c:if test="${!empty field.customs['height']}">height:${field.customs['height']}px;</c:if></c:set>
	<c:set var="attr_maxlength"><c:if test="${!empty field.customs['maxLength']}">maxlength="${field.customs['maxLength']}"</c:if></c:set>
	<c:set var="attr_class"><c:choose><c:when test="${!empty field.customs['validation']}">class="${field.customs['validation']}"</c:when><c:otherwise><c:if test="${field.required}">class="required"</c:if></c:otherwise></c:choose></c:set>
	<c:choose>
 		<c:when test="${field.type==1}">
 			<input type="text" name="${c_name}" value="${c_value}" class="form-control" placeholder="${field.label}" ${attr_maxlength} />
 		</c:when>
 		<c:when test="${field.type==2}">
 			<%-- <c:if test="${oprt=='create' && c_value=='now'}"><c:set var="c_value"><fmt:formatDate value="${fnx:now()}" pattern="${field.customs['datePattern']}"/></c:set></c:if>
 			<input type="text" name="${c_name}" value="${c_value}" onclick="WdatePicker({dateFmt:'${field.customs['datePattern']}'});" ${attr_class} ${attr_maxlength} style="${style_width}"/> --%>
 		</c:when>
 		<c:when test="${field.type==3}">
      <c:set var="c_value" value="${fnx:split_sc(c_value,',')}"/>
 			<c:forEach var="option" items="${fnx:invoke(field.options,'entrySet')}">
 				<label><input type="checkbox" name="${c_name}" value="${option.key}"<c:if test="${fnx:contains_oxo(c_value,option.key)}"> checked="checked"</c:if>/>${option.value}</label>
 			</c:forEach>
 		</c:when>
    <c:when test="${field.type==4 || field.type==100}">
      <c:forEach var="option" items="${fnx:invoke(field.options,'entrySet')}">
        <label><input type="radio" name="${c_name}" value="${option.key}"<c:if test="${c_value eq option.key}"> checked="checked"</c:if>/>${option.value}</label>
      </c:forEach>
    </c:when>
 		<c:when test="${field.type==5 || field.type==101}">
		    <select class="input-sm form-control input-s-sm inline" name="${c_name}">
	      		<c:forEach var="option" items="${fnx:invoke(field.options,'entrySet')}">
	 				<option value="${option.key}"<c:if test="${c_value eq option.key}"> selected="selected"</c:if>>${option.value}</option>
	 			</c:forEach>
 			</select>
 		</c:when>
 		<c:when test="${field.type==6}">
 			<textarea name="${c_name}" class="form-control" placeholder="${field.label}" ${attr_maxlength} style="${style_width}${style_height}">${c_value}</textarea>
 		</c:when>
 		<c:when test="${field.type==7}">
 			<div id="${c_name}Div" data-with="${field.customs['imageWidth']}" data-height="${field.customs['imageHeight']}" data-value="${c_value}" data-watermark="${field.customs['imageWatermark']}" data-scale="${field.customs['imageScale']}" data-stretch="${field.customs['imageExact']}"></div>
 			<script type="text/javascript">

			    $(document).ready(function () {
			        loadImageUploadDiv("${c_name}Div", "${c_name}");
			    });
			    
			    function loadImageUploadDiv(id, name) {
			    	var data_with = $('#' + id).attr('data-with');
			    	var data_height = $('#' + id).attr('data-height');
			    	var data_value = $('#' + id).attr('data-value');
			    	var data_watermark = $('#' + id).attr('data-watermark');
			    	var data_scale = $('#' + id).attr('data-scale');
			    	var data_stretch = $('#' + id).attr('data-stretch');
			    	$('#' + id).load('cms/selector/imgUploadTag?id='+name+'&width='+data_with+'&height='+data_height+'&value='+data_value+'&scale='+data_scale+'&watermark='+data_watermark+'&stretch='+data_stretch);
			    }
			</script>
 		</c:when>  	
 		<c:when test="${field.type==8}">
 			<c:set var="c_valueName">${field.name}Name</c:set>
 			<c:set var="c_valueName"><c:out value="${(field.clob) ? (bean.clobs[c_valueName]) : (customs[c_valueName])}"/></c:set>
	      	<c:set var="c_valueLength">${field.name}Length</c:set>
	      	<c:set var="c_valueLength"><c:out value="${(field.clob) ? (bean.clobs[c_valueLength]) : (customs[c_valueLength])}"/></c:set>
	      	<c:set var="c_valueTime">${field.name}Time</c:set>
	      	<c:set var="c_valueTime"><c:out value="${(field.clob) ? (bean.clobs[c_valueTime]) : (customs[c_valueTime])}"/></c:set>
	 		<div id="${c_name}Div" data-fileNameId="${c_name}Name" data-fileName="${c_valueName}" data-fileUrlId="${c_name}" data-fileUrl="${c_value}" data-fileSizeId="${c_name}Length" data-fileSize="${c_valueLength}" data-fileTimeId="${c_name}Time" data-fileTime="${c_valueTime}"></div>
	 		<script type="text/javascript">

			    $(document).ready(function () {
			    	loadVideoUploadDiv("${c_name}");
			    });
			    
			    function loadVideoUploadDiv(id) {
			    	var divId = id + "Div";
			    	var data_fileNameId = $('#' + divId).attr('data-fileNameId');
			    	var data_fileName = $('#' + divId).attr('data-fileName');
			    	var data_fileUrlId = $('#' + divId).attr('data-fileUrlId');
			    	var data_fileUrl = $('#' + divId).attr('data-fileUrl');
			    	var data_fileSizeId = $('#' + divId).attr('data-fileSizeId');
			    	var data_fileSize = $('#' + divId).attr('data-fileSize');
			    	var data_fileTimeId = $('#' + divId).attr('data-fileTimeId');
			    	var data_fileTime = $('#' + divId).attr('data-fileTime');
			    	
			    	$('#' + divId).load('cms/selector/videoUploadTag?id='+id+'&fileNameId='+data_fileNameId+'&fileName='+data_fileName+'&fileUrlId='+data_fileUrlId+'&fileUrl='+data_fileUrl+'&fileSizeId='+data_fileSizeId+'&fileSize='+data_fileSize+'&fileTimeId='+data_fileTimeId+'&fileTime='+data_fileTime);
			    }
			</script>
		</c:when>
 		<c:when test="${field.type==9}">
	 		<c:set var="c_valueName">${field.name}Name</c:set>
	      	<c:set var="c_valueName"><c:out value="${(field.clob) ? (bean.clobs[c_valueName]) : (customs[c_valueName])}"/></c:set>
	      	<c:set var="c_valueLength">${field.name}Length</c:set>
	      	<c:set var="c_valueLength"><c:out value="${(field.clob) ? (bean.clobs[c_valueLength]) : (customs[c_valueLength])}"/></c:set>
	 		<div id="${c_name}Div" data-fileNameId="${c_name}Name" data-fileName="${c_valueName}" data-fileUrlId="${c_name}" data-fileUrl="${c_value}" data-fileSizeId="${c_name}Length" data-fileSize="${c_valueLength}"></div>
	 		<script type="text/javascript">

			    $(document).ready(function () {
			    	loadFileUploadDiv("${c_name}");
			    });
			    
			    function loadFileUploadDiv(id) {
			    	var divId = id + "Div";
			    	var data_fileNameId = $('#' + divId).attr('data-fileNameId');
			    	var data_fileName = $('#' + divId).attr('data-fileName');
			    	var data_fileUrlId = $('#' + divId).attr('data-fileUrlId');
			    	var data_fileUrl = $('#' + divId).attr('data-fileUrl');
			    	var data_fileSizeId = $('#' + divId).attr('data-fileSizeId');
			    	var data_fileSize = $('#' + divId).attr('data-fileSize');
			    	
			    	$('#' + divId).load('cms/selector/fileUploadTag?id='+id+'&fileNameId='+data_fileNameId+'&fileName='+data_fileName+'&fileUrlId='+data_fileUrlId+'&fileUrl='+data_fileUrl+'&fileSizeId='+data_fileSizeId+'&fileSize='+data_fileSize);
			    }
			</script>
   	</c:when>
   	<c:when test="${field.type==11}">
 			<c:set var="c_valueName">${field.name}Name</c:set>
 			<c:set var="c_valueName"><c:out value="${(field.clob) ? (bean.clobs[c_valueName]) : (customs[c_valueName])}"/></c:set>
	      	<c:set var="c_valueLength">${field.name}Length</c:set>
	      	<c:set var="c_valueLength"><c:out value="${(field.clob) ? (bean.clobs[c_valueLength]) : (customs[c_valueLength])}"/></c:set>
	      	<c:set var="c_valueTime">${field.name}Time</c:set>
	      	<c:set var="c_valueTime"><c:out value="${(field.clob) ? (bean.clobs[c_valueTime]) : (customs[c_valueTime])}"/></c:set>
	 		<div id="${c_name}Div" data-fileNameId="${c_name}Name" data-fileName="${c_valueName}" data-fileUrlId="${c_name}" data-fileUrl="${c_value}" data-fileSizeId="${c_name}Length" data-fileSize="${c_valueLength}" data-fileTimeId="${c_name}Time" data-fileTime="${c_valueTime}"></div>
	 		<script type="text/javascript">

			    $(document).ready(function () {
			    	loadVideoUploadDiv("${c_name}");
			    });
			    
			    function loadVideoUploadDiv(id) {
			    	var divId = id + "Div";
			    	var data_fileNameId = $('#' + divId).attr('data-fileNameId');
			    	var data_fileName = $('#' + divId).attr('data-fileName');
			    	var data_fileUrlId = $('#' + divId).attr('data-fileUrlId');
			    	var data_fileUrl = $('#' + divId).attr('data-fileUrl');
			    	var data_fileSizeId = $('#' + divId).attr('data-fileSizeId');
			    	var data_fileSize = $('#' + divId).attr('data-fileSize');
			    	var data_fileTimeId = $('#' + divId).attr('data-fileTimeId');
			    	var data_fileTime = $('#' + divId).attr('data-fileTime');
			    	
			    	$('#' + divId).load('cms/selector/audioUploadTag?id='+id+'&fileNameId='+data_fileNameId+'&fileName='+data_fileName+'&fileUrlId='+data_fileUrlId+'&fileUrl='+data_fileUrl+'&fileSizeId='+data_fileSizeId+'&fileSize='+data_fileSize+'&fileTimeId='+data_fileTimeId+'&fileTime='+data_fileTime);
			    }
			</script>
		</c:when>
 		<c:when test="${field.type==50}">
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
 			<textarea id="${c_name}" name="${c_name}">${c_value}</textarea>
			<script type="text/javascript">
	    $(function() {
	      var editor_${c_name} = UE.getEditor('${c_name}',{
	    	  <c:if test="${!empty field.customs['toolbar']}">toolbars: window.UEDITOR_CONFIG.toolbars_${field.customs['toolbar']},</c:if>
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
	        localDomain: ['${!empty GLOBAL.uploadsDomain ? GLOBAL.uploadsDomain : ""}']
	      });
	    });
			</script>  			
 		</c:when>
  	<c:otherwise>
  		Unknown Filed Type: ${field.type}
  	</c:otherwise>
	</c:choose>
	<c:if test="${!empty field.prompt}"><span class="in-prompt" title="<c:out value='${field.prompt}'/>">&nbsp;</span></c:if>