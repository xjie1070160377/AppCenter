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
					  <c:if test="${!fnx:contains_co(names,'parent')}">
					  <tr>
					    <td>&nbsp;</td>
					    <td align="center">parent
					      <input type="hidden" name="name" value="parent"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.parent'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'name')}">
					  <tr>
					    <td>&nbsp;</td>
					    <td align="center">name
					      <input type="hidden" name="name" value="name"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2,"required":true}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.name'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'number')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">number
					      <input type="hidden" name="name" value="number"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.number'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'link')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">link
					      <input type="hidden" name="name" value="link"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.link'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'newWindow')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">newWindow
					      <input type="hidden" name="name" value="newWindow"/>
					      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.newWindow'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'metaKeywords')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">metaKeywords
					      <input type="hidden" name="name" value="metaKeywords"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.metaKeywords'/>"/></td>
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
					      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.metaDescription'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'workflow')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">workflow
					      <input type="hidden" name="name" value="workflow"/>
					      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.workflow'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <%-- <c:if test="${!fnx:contains_co(names,'infoPerms')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">infoPerms
					      <input type="hidden" name="name" value="infoPerms"/>
					      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.infoPerms'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'nodePerms')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">nodePerms
					      <input type="hidden" name="name" value="nodePerms"/>
					      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.nodePerms'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'viewGroups')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">viewGroups
					      <input type="hidden" name="name" value="viewGroups"/>
					      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.viewGroups'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'contriGroups')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">contriGroups
					      <input type="hidden" name="name" value="contriGroups"/>
					      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.contriGroups'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'commentGroups')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">commentGroups
					      <input type="hidden" name="name" value="commentGroups"/>
					      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.commentGroups'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if> --%>
					  <c:if test="${!fnx:contains_co(names,'nodeModel')}">
					  <tr>
					    <td>&nbsp;</td>
					    <td align="center">nodeModel
					      <input type="hidden" name="name" value="nodeModel"/>
					      <input type='hidden' name='property' value='{"type":5,"innerType":2,"required":true}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.nodeModel'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'infoModel')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">infoModel
					      <input type="hidden" name="name" value="infoModel"/>
					      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.infoModel'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'nodeTemplate')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">nodeTemplate
					      <input type="hidden" name="name" value="nodeTemplate"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.nodeTemplate'/>"/></td>
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
					    <td align="center"><input type="text" name="label" value="<s:message code='node.infoTemplate'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <%-- <c:if test="${!fnx:contains_co(names,'generateNode')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">generateNode
					      <input type="hidden" name="name" value="generateNode"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.generateNode'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'generateInfo')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">generateInfo
					      <input type="hidden" name="name" value="generateInfo"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.generateInfo'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'staticMethod')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">staticMethod
					      <input type="hidden" name="name" value="staticMethod"/>
					      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.staticMethod'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'staticPage')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">staticPage
					      <input type="hidden" name="name" value="staticPage"/>
					      <input type='hidden' name='property' value='{"type":1,"innerType":2,"defValue":1,"required":false}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.staticPage'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" checked="checked" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="true">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if> --%>
					  <c:if test="${!fnx:contains_co(names,'smallImage')}">
					  <tr>
					    <td align="center"><input type="checkbox" name="control" checked="checked"/></td>
					    <td align="center">smallImage
					      <input type="hidden" name="name" value="smallImage"/>
					      <input type='hidden' name='property' value='{"type":7,"innerType":2,"required":false}'/>
					      <input type='hidden' name='custom' value='{"imageWidth":"180","imageHeight":"120","imageScale":"true","exact":"false","imageWatermark":"false"}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.smallImage'/>"/></td>
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
					    <td align="center"><input type="text" name="label" value="<s:message code='node.largeImage'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'p1')}">
					  <tr>
					    <td><input type="checkbox" name="control"/></td>
					    <td align="center">p1
					      <input type="hidden" name="name" value="p1"/>
					      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.p1'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'p2')}">
					  <tr>
					    <td><input type="checkbox" name="control"/></td>
					    <td align="center">p2
					      <input type="hidden" name="name" value="p2"/>
					      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.p2'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'p3')}">
					  <tr>
					    <td><input type="checkbox" name="control"/></td>
					    <td align="center">p3
					      <input type="hidden" name="name" value="p3"/>
					      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.p3'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'p4')}">
					  <tr>
					    <td><input type="checkbox" name="control"/></td>
					    <td align="center">p4
					      <input type="hidden" name="name" value="p4"/>
					      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.p4'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'p5')}">
					  <tr>
					    <td><input type="checkbox" name="control"/></td>
					    <td align="center">p5
					      <input type="hidden" name="name" value="p5"/>
					      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.p5'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'p6')}">
					  <tr>
					    <td><input type="checkbox" name="control"/></td>
					    <td align="center">p6
					      <input type="hidden" name="name" value="p6"/>
					      <input type='hidden' name='property' value='{"type":101,"innerType":3}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.p6'/>"/></td>
					    <td align="center">
					    	<input type="checkbox" onclick="$('#_chk_dblColumn${ckIndex}').val(this.checked);"><input type="hidden" id="_chk_dblColumn${ckIndex}" name="dblColumn" value="false">
	            			<c:set var="ckIndex" value="${ckIndex+1}"></c:set>
					    </td>
					  </tr>
					  </c:if>
					  <c:if test="${!fnx:contains_co(names,'text')}">
					  <tr>
					    <td><input type="checkbox" name="control"/></td>
					    <td align="center">text
					      <input type="hidden" name="name" value="text"/>
					      <input type='hidden' name='property' value='{"type":50,"innerType":2}'/>
					      <input type='hidden' name='custom' value='{}'/>
					    </td>
					    <td align="center"><input type="text" name="label" value="<s:message code='node.text'/>"/></td>
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
