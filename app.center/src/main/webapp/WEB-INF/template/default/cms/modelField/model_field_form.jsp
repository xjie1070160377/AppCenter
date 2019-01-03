<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<div class="col-sm-12">
	<div class="ibox-title">
		<h5>模型管理 - <s:message code="model.type.${model.type}"/> - ${model.name } - ${oprt == 'create' ? '新增' : '编辑'}</h5>
	</div>
	<div class="ibox-title">
		<button class="btn btn-info" type="button" id="btn_back"><i class="fa fa-mail-reply-all"></i> 返回</button>
	</div>
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <form id="postForm" method="post" class="form-horizontal" action="cms/modelField/modelField${bean.id == null ? 'Save' : 'Update'}">
            	<input type="hidden" name="modelId" value="${model.id }"/>
            	<input type="hidden" name="oid" value="${bean.id}"/>
				<div class="form-group">
					<table border="0" cellpadding="0" cellspacing="0" class="in-tb">
					  <tr>
					  	<td class="in-lab" width="15%"><em class="req">*</em>类型:</td>
					    <td class="in-ctt" colspan="3">
					      <c:choose>
					      <c:when test="${oprt=='create'||bean.custom}">
					      <select id="input_type" name="type" class="input-sm form-control input-s-sm inline">
					        <option value="1"<c:if test="${bean.type==1}"> selected="selected"</c:if>><s:message code="modelField.type.1"/></option>
					        <%-- <option value="2"<c:if test="${bean.type==2}"> selected="selected"</c:if>><s:message code="modelField.type.2"/></option> --%>
					        <option value="3"<c:if test="${bean.type==3}"> selected="selected"</c:if>><s:message code="modelField.type.3"/></option>
					        <option value="4"<c:if test="${bean.type==4}"> selected="selected"</c:if>><s:message code="modelField.type.4"/></option>
					        <option value="5"<c:if test="${bean.type==5}"> selected="selected"</c:if>><s:message code="modelField.type.5"/></option>
					        <option value="6"<c:if test="${bean.type==6}"> selected="selected"</c:if>><s:message code="modelField.type.6"/></option>
					        <%-- <option value="50"<c:if test="${bean.type==50}"> selected="selected"</c:if>><s:message code="modelField.type.50"/></option> --%>
					        <option value="7"<c:if test="${bean.type==7}"> selected="selected"</c:if>><s:message code="modelField.type.7"/></option>
					        <option value="8"<c:if test="${bean.type==8}"> selected="selected"</c:if>><s:message code="modelField.type.8"/></option>
					        <option value="9"<c:if test="${bean.type==9}"> selected="selected"</c:if>><s:message code="modelField.type.9"/></option>
					      </select> &nbsp;
					      <label><input type="checkbox" name="clob" <c:if test="${bean.clob}"> checked="checked"</c:if>/>大字段</label>
					      </c:when>
					      <c:when test="${bean.innerType==3}">
					      <select id="input_type" name="type" class="input-sm form-control input-s-sm inline">
					        <option value="">请选择</option>
					        <option value="100"<c:if test="${bean.type==100}"> selected="selected"</c:if>><s:message code="modelField.type.100"/></option>
					        <option value="101"<c:if test="${bean.type==101}"> selected="selected"</c:if>><s:message code="modelField.type.101"/></option>
					      </select>
					      </c:when>
					      <c:otherwise><s:message code="modelField.type.${bean.type}"/></c:otherwise>
					      </c:choose>
					    </td>
					  </tr>
					  <tr>
					    <td class="in-lab" width="15%"><em class="req">*</em>字段名称:</td>
					    <td class="in-ctt" width="35%">
				  			<input type="text" class="form-control" placeholder="字段名称" maxlength="50" id="label" name="label" value="${bean.label}"/>
				    	</td>
					    <td class="in-lab" width="15%"><em class="req">*</em>字段代码:</td>
					    <td class="in-ctt" width="35%">
					    	<input type="text" class="form-control" placeholder="字段代码" maxlength="50" id="name" name="name" value="${bean.name}" <c:if test="${bean.predefined}"> readonly="readonly"  class="disable"</c:if>/>
				    	</td>
					  </tr>
					  <tr>
					    <td class="in-lab" width="15%"><em class="req">*</em>必填字段:</td>
					    <td class="in-ctt" width="35%">
					      <label><input type="radio" name="required" value="true"<c:if test="${bean.required}"> checked="checked"</c:if>/>是</label> &nbsp;
					      <label><input type="radio" name="required" value="false"<c:if test="${empty bean.required || !bean.required}"> checked="checked"</c:if>/>否</label>
					    </td>
					    <td class="in-lab" width="15%"><em class="req">*</em>一行两列:</td>
					    <td class="in-ctt" width="35%">
					      <label><input type="radio" name="dblColumn" value="true"<c:if test="${bean.dblColumn}"> checked="checked"</c:if>/>是</label> &nbsp;
					      <label><input type="radio" name="dblColumn" value="false"<c:if test="${empty bean.dblColumn || !bean.dblColumn}"> checked="checked"</c:if>/>否</label>
					    </td>
					  </tr>
					  <tr>
					    <td class="in-lab" width="15%">默认值:</td>
					    <td class="in-ctt" colspan="3">
				  			<input type="text" class="form-control" placeholder="默认值" maxlength="255" id="defValue" name="defValue" value="${bean.defValue}"/>
					    </td>
					  </tr>
					  <tbody id="input_type_body">
					  <tr class="input_type_1" style="display:none;">
					    <td class="in-lab" width="15%">最大长度:</td>
					    <td class="in-ctt" colspan="3"><input type="text" placeholder="最大长度" class="form-control" name="customs_maxLength" value="<c:out value='${bean.customs["maxLength"]}'/>" maxlength="10"/></td>
					  </tr>
					  <tr class="input_type_2" style="display:none;">
					    <td class="in-lab" width="15%"><em class="req">*</em>日期格式:</td>
					    <td class="in-ctt" colspan="3">
					      <select name="customs_datePattern" class="input-sm form-control input-s-sm inline">
					        <option<c:if test="${bean.customs['datePattern'] eq 'yyyy-MM-dd HH:mm:ss'}"> selected="selected"</c:if>>yyyy-MM-dd HH:mm:ss</option>
					        <option<c:if test="${bean.customs['datePattern'] eq 'yyyy-MM-dd'}"> selected="selected"</c:if>>yyyy-MM-dd</option>
					        <option<c:if test="${bean.customs['datePattern'] eq 'MM-dd HH:mm'}"> selected="selected"</c:if>>MM-dd HH:mm</option>
					        <option<c:if test="${bean.customs['datePattern'] eq 'MM-dd'}"> selected="selected"</c:if>>MM-dd</option>
					        <option<c:if test="${bean.customs['datePattern'] eq 'yyyy-MM'}"> selected="selected"</c:if>>yyyy-MM</option>
					        <option<c:if test="${bean.customs['datePattern'] eq 'yyyy'}"> selected="selected"</c:if>>yyyy</option>
					      </select>
					    </td>
					  </tr>  
					  <tr class="input_type_3 input_type_4 input_type_5 input_type_100 input_type_101" style="display:none;">
					    <td class="in-lab" width="15%"><em class="req">*</em>可选项:</td>
					    <td class="in-ctt" colspan="3"><textarea name="customs_options" class="form-control" maxlength="2000" style="height:150px;"><c:out value='${bean.customs["options"]}'/></textarea>一行一个选项，格式value或key|value。</td>
					  </tr>
					  <tr class="input_type_6" style="display:none;">
					    <td class="in-lab" width="15%">最大长度:</td>
					    <td class="in-ctt" colspan="3"><input type="text" placeholder="最大长度" name="customs_maxLength" class="form-control" value="<c:out value='${bean.customs["maxLength"]}'/>" maxlength="5"/></td>
					  </tr>
					  <tr class="input_type_7 input_type_51" style="display:none;">
					    <td class="in-lab" width="15%">图片压缩:</td>
					    <td class="in-ctt" width="85%" colspan="3">
				    		<input type="checkbox" value="true" class="js-switch" name="customs_imageScale" value="true" <c:if test="${bean.customs['imageScale']}"> checked="checked"</c:if>/>
						      宽: <input type="text" name="customs_imageWidth" value="${bean.customs['imageWidth']}" maxlength="5" style="width:120px;"/>px &nbsp; &nbsp;
						      高: <input type="text" name="customs_imageHeight" value="${bean.customs['imageHeight']}" maxlength="5" style="width:120px;"/>px &nbsp; &nbsp;
					    </td>
					  </tr>
					  <tr class="input_type_7 input_type_51" style="display:none;">
					    <td class="in-lab" width="15%">图片拉伸:</td>
					    <td class="in-ctt" width="85%" colspan="3">
				    		<input type="checkbox" value="true" class="js-switch" name="customs_imageExact" value="true" <c:if test="${bean.customs['imageExact']}"> checked="checked"</c:if>"/>
					    </td>
					  </tr>
					  <tr class="input_type_7 input_type_51" style="display:none;">
					    <td class="in-lab" width="15%">图片水印:</td>
					    <td class="in-ctt" width="85%" colspan="3">
				    		<input type="checkbox" value="true" class="js-switch" name="customs_imageWatermark" value="true" <c:if test="${bean.customs['imageWatermark']}"> checked="checked"</c:if>"/>
					    </td>
					  </tr>
					  <tr class="input_type_50" style="display:none;">
					    <td class="in-lab" width="15%"><em class="req">*</em>编辑器工具条:</td>
					    <td class="in-ctt" colspan="3">
					      <select name="customs_toolbar" class="input-sm form-control input-s-sm inline">
					        <option value="Cms"<c:if test="${bean.customs['toolbar'] eq 'Cms'}"> selected="selected"</c:if>>紧凑工具条</option>
					        <option value="Basic"<c:if test="${bean.customs['toolbar'] eq 'Basic'}"> selected="selected"</c:if>>简洁工具条</option>
					      </select>
					    </td>
					  </tr>
					  </tbody>
					  <tr>
					    <td colspan="4" class="in-opt">
			                <div class="col-sm-12 text-right" style="margin-top:5px">
			                    <button onclick="submitForm();" class="btn btn-primary" type="button">保存并返回</button>
				      			<c:if test="${oprt=='create'}">
			                    	<button onclick="submitAndCreate();" class="btn btn-primary" type="button">保存并新增</button>
				      			</c:if>
			                </div>
					    </td>
					  </tr>
					</table>
				</div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
var initIndex = 0;
function typeChange(index) {
	$("#input_type_body tr").hide();
	$("#input_type_body input,#input_type_body select,#input_type_body textarea").prop("disabled",true);
	if(index!="") {
		$(".input_type_"+index).show();
		$(".input_type_"+index+" input,.input_type_"+index+" select,.input_type_"+index+" textarea").prop("disabled",false);
		if(index == 7) {
			initPhotoCk();
		}
	}	
}

function initPhotoCk(){
	if(initIndex == 0) {
		var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
		elems.forEach(function(html) {
		  var switchery = new Switchery(html, { color: '#1AB394' });
		});
	}
	initIndex++;
}

$(function() {
	$("input[name='label']").focus();
	$("#input_type").change(function() {
		var index = $(this).val();
		typeChange(index);
	});
	typeChange("${empty bean.type ? 1 : bean.type}");

    $("#postForm").validate({
        rules: {
        	label: {
                required: true
            },
            name: {
                required: true
            },
            input_type: {
                required: true
            }
        }
    });
    $("#btn_back").click(function(){
    	loadFrameContent('cms/modelField/modelFieldList?modelId=${modelId}');
    });
});

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

function submitAndCreate(){
	if($("#postForm").valid()==false){
		return;
	}
	var postData = $("#postForm").serializeArray();
	$.post($("#postForm").attr("action"), postData, function (json) {
        //var data = $.parseJSON(json);
        if (json.resultStatus) {
            //成功
			layer.msg('新增成功.');
        	loadFrameContent('cms/modelField/modelFieldAdd?modelId=${modelId}');
        } else {
        	//
        	layer.alert("失败了："+ json.resultMsg, {icon: 2});
        }
    }, "json");
}
</script> 