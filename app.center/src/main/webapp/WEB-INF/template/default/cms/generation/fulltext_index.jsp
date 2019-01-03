<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<style type="text/css">
* html{overflow-y: scroll;}
input[name='node_name'],input[name='specialNames']{padding-right:50px;}
.form-control {padding: 0 12px;font-weight:400;}
/* .nav-tabs li a:link,.nav-tabs li a:focus{border:none;outline:none;} */
#nodeSelectDiv,#specialSelectDiv{position: absolute; top: 7px; right: 7px;height:30px;}
.in-tb td.in-lab{background:#fff;font-weight:700;color: #1AB394;}
.in-tb label{font-weight:400;}

</style>
<div class="col-sm-12">
	<div class="ibox-title">
		<h5>生成全文索引</h5>
	</div>
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <form id="postForm" method="post" class="form-horizontal" action="cms/generation/fulltext_submit">
				<div class="form-group">
					<table border="0" cellpadding="0" cellspacing="0" class="in-tb">
					  <tr>
					  	<td class="in-lab" width="15%">栏目:</td>
					    <td class="in-ctt" >
					    	<input type="hidden" name="nodeId" id="nodeId">
					    	<input type="text" readonly="readonly" id="node_name" class="form-control" name="node_name" value="">
					    	<div id="nodeSelectDiv"></div>
					    </td>
					  </tr>
					  
					  </tr>
					  </tbody>
					  <tr>
					    <td colspan="4" class="in-opt">
			                <div class="col-sm-12 text-right" style="margin-top:5px">
			                    <button onclick="submitxForm();" class="btn btn-primary" type="button">提交</button>
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
	$(function(){
		$('#nodeSelectDiv').load('cms/selector/nodeTag?id=nodeSelector&callback=nodeSelectCallBack');
	});
	function nodeSelectCallBack(id, text){
    	$('#nodeId').val(id);
    	$('#node_name').val(text);
	}
	function submitxForm(){
		var postData = $("#postForm").serializeArray();
		$.post($("#postForm").attr("action"), postData, function (json) {
	        //var data = $.parseJSON(json);
	        if (json.resultStatus) {
	        	layer.alert("正在生成全文索引！", {icon: 1});
	            //成功
	        	//loadFrameContent('cms/modelField/modelFieldList?modelId=${modelId}');
	        } else {
	        	//
	        	layer.alert("失败了："+ json.resultMsg, {icon: 2});
	        }
	    }, "json");
	}
</script> 