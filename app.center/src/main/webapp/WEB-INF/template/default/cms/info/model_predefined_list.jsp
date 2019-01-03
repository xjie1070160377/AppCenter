<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.csmooc.core.domain.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<div class="col-sm-12 m-model-predefined-list">
	<div class="ibox-title">
		<h5>模型管理 - <s:message code="model.type.${model.type}"/> - ${model.name } - ${oprt == 'create' ? '新增' : '编辑'}</h5>
	</div>
	<div class="ibox-title">
		<button class="btn btn-info" type="button" id="btn_back"><i class="fa fa-mail-reply-all"></i> 返回</button>
	</div>
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <form id="postForm" method="post" class="form-horizontal" action="cms/modelField/batchSave">
            	<input type="hidden" name="modelId" value="${model.id }"/>
            	<c:set var="ckIndex" value="0"></c:set>
				<table border="0" cellpadding="0" cellspacing="0" class="in-tb">
				  <thead>
				  <tr class="ls_table_th">
				    <th align="center" width="25"><input type="checkbox" checked="checked" onclick="checkControl('control',this.checked);"/></th>
				    <th><s:message code="modelField.name"/></th>
				    <th><s:message code="modelField.label"/></th>
				    <th><s:message code="modelField.dblColumn"/></th>
				  </tr>
				  </thead>
				  <tbody>
				  <c:set var="names" value="${model.predefinedNames}"/>
				  <c:if test="${!fnx:contains_co(names,'node')}">
				  <tr>
				    <td>&nbsp;</td>
				    <td align="center">node
				      <input type="hidden" name="name" value="node"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2,"required":true}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.node'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'specials')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">specials
				      <input type="hidden" name="name" value="specials"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.specials'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'title')}">
				  <tr>
				    <td>&nbsp;</td>
				    <td align="center">title
				      <input type="hidden" name="name" value="title"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2,"required":true}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.title'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'subtitle')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">subtitle
				      <input type="hidden" name="name" value="subtitle"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.subtitle'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'fullTitle')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">fullTitle
				      <input type="hidden" name="name" value="fullTitle"/>
				      <input type='hidden' name='property' value='{"type":6,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.fullTitle'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'tagKeywords')}">
			  	  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">tagKeywords
				      <input type="hidden" name="name" value="tagKeywords"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.tagKeywords'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'metaDescription')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">metaDescription
				      <input type="hidden" name="name" value="metaDescription"/>
				      <input type='hidden' name='property' value='{"type":6,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.metaDescription'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'infoPath')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">infoPath
				      <input type="hidden" name="name" value="infoPath"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.infoPath'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'infoTemplate')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">infoTemplate
				      <input type="hidden" name="name" value="infoTemplate"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.infoTemplate'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'priority')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">priority
				      <input type="hidden" name="name" value="priority"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.priority'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'publishDate')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">publishDate
				      <input type="hidden" name="name" value="publishDate"/>
				      <input type='hidden' name='property' value='{"type":2,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.publishDate'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'source')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">source
				      <input type="hidden" name="name" value="source"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.source'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'author')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">author
				      <input type="hidden" name="name" value="author"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.author'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'allowComment')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">allowComment
				      <input type="hidden" name="name" value="allowComment"/>
				      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.allowComment'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'attributes')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">attributes
				      <input type="hidden" name="name" value="attributes"/>
				      <input type='hidden' name='property' value='{"type":1,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.attributes'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'smallImage')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">smallImage
				      <input type="hidden" name="name" value="smallImage"/>
				      <input type='hidden' name='property' value='{"type":7,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{"imageWidth":"180","imageHeight":"120","imageScale":"true","exact":"false","imageWatermark":"false"}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.smallImage'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'largeImage')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">largeImage
				      <input type="hidden" name="name" value="largeImage"/>
				      <input type='hidden' name='property' value='{"type":7,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{"imageWidth":"480","imageHeight":"480","imageScale":"true","exact":"false","imageWatermark":"true"}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.largeImage'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'file')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">file
				      <input type="hidden" name="name" value="file"/>
				      <input type='hidden' name='property' value='{"type":9,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.file'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <%-- <c:if test="${!fnx:contains_co(names,'files')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">files
				      <input type="hidden" name="name" value="files"/>
				      <input type='hidden' name='property' value='{"type":52,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.files'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if> --%>
				  <c:if test="${!fnx:contains_co(names,'video')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">video
				      <input type="hidden" name="name" value="video"/>
				      <input type='hidden' name='property' value='{"type":8,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.video'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'audio')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">audio
				      <input type="hidden" name="name" value="audio"/>
				      <input type='hidden' name='property' value='{"type":11,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.audio'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <%-- <c:if test="${!fnx:contains_co(names,'doc')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">doc
				      <input type="hidden" name="name" value="doc"/>
				      <input type='hidden' name='property' value='{"type":10,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.doc'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if> --%>
				  <c:if test="${!fnx:contains_co(names,'images')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">images
				      <input type="hidden" name="name" value="images"/>
				      <input type='hidden' name='property' value='{"type":51,"innerType":2,"required":false}'/>
				      <input type='hidden' name='custom' value='{"imageWidth":"1500","thumbnail":"true","thumbnailWidth":"116","thumbnailHeight":"77","imageScale":"true","exact":"false","imageWatermark":"true"}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.images'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'p1')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control"/></td>
				    <td align="center">p1
				      <input type="hidden" name="name" value="p1"/>
				      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.p1'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'p2')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control"/></td>
				    <td align="center">p2
				      <input type="hidden" name="name" value="p2"/>
				      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.p2'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'p3')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control"/></td>
				    <td align="center">p3
				      <input type="hidden" name="name" value="p3"/>
				      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.p3'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'p4')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control"/></td>
				    <td align="center">p4
				      <input type="hidden" name="name" value="p4"/>
				      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.p4'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'p5')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control"/></td>
				    <td align="center">p5
				      <input type="hidden" name="name" value="p5"/>
				      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.p5'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'p6')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control"/></td>
				    <td align="center">p6
				      <input type="hidden" name="name" value="p6"/>
				      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.p6'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  <c:if test="${!fnx:contains_co(names,'text')}">
				  <tr>
				    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
				    <td align="center">text
				      <input type="hidden" name="name" value="text"/>
				      <input type='hidden' name='property' value='{"type":50,"innerType":2}'/>
				      <input type='hidden' name='custom' value='{}'/>
				    </td>
				    <td align="center"><input type="text" name="label" value="<s:message code='info.text'/>"/></td>
				    <td align="center">
				    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
				    </td>
				  </tr>
				  </c:if>
				  </tbody>
				  <tr>
				    <td colspan="4" class="in-opt">
		                <div class="col-sm-12 text-right" style="margin-top:5px">
		                    <button onclick="submitForm();" class="btn btn-primary" type="button">保存并返回</button>
		                </div>
				    </td>
				  </tr>
				</table>
			</form>
        </div>
    </div>
</div>
<script type="text/javascript">
$(function() {
	$("input[name=control][checked!=checked]").each(function(){
		$(this).parent().parent().find("input,select").not(this).attr("disabled","disabled").addClass("disabled");
	});
	$("input[name='control']").change(function(){
		if(this.checked) {
			$(this).parent().parent().find("input,select").not(this).removeAttr("disabled").removeClass("disabled");
		} else {
			$(this).parent().parent().find("input,select").not(this).attr("disabled","disabled").addClass("disabled");
		}
	});
    $("#btn_back").click(function(){
    	loadFrameContent('cms/modelField/modelFieldList?modelId=${modelId}');
    });
});
function checkControl(name,checked) {
	$("input[name='"+name+"']").each(function() {
		$(this).prop("checked",checked).change();
	});
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
        	loadFrameContent('cms/modelField/modelFieldList?modelId=${modelId}');
        } else {
        	//
        	layer.alert("失败了："+ json.resultMsg, {icon: 2});
        }
    }, "json");
}
</script>
