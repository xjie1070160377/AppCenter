<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<link href="${ctxPath}/static/base/css/plugins/jsTree/style.min.css" rel="stylesheet">
<link href="${ctxPath}/static/base/css/form.css" rel="stylesheet">

<style type="text/css">
* html{overflow-y: scroll;}
input[name='node_name'],input[name='specialNames']{padding-right:50px;}
.form-control {padding: 0 12px;font-weight:400;}
.nav-tabs>li{margin:0;position:relative;bottom:-2px;}
/* .nav-tabs li a:link,.nav-tabs li a:focus{border:none;outline:none;} */
#nodeSelectDiv,#specialSelectDiv{position: absolute; top: 7px; right: 7px;height:30px;}
.nav-tabs{border-bottom:none;}
a, a:hover, a:focus,a:link{outline:none;}
.in-tb td.in-lab{background:#fff;font-weight:700;color: #1AB394;}
.in-tb label{font-weight:400;}
#sourceUrl_x,#sourceother{margin:10px 0;}
#datepicker{margin-bottom:0;}
.forwardLink{position:absolute;right:15px;top:10px;}
#imagesUploadDiv .fileinput-button,#fileUploadDiv .fileinput-button{margin-left: 20px; margin-top: 20px;}
#imagesUploadDiv .img_prev_div{padding-left:5px;}
#imagesUploadDiv .text-left {padding-left:0;}
#images_div > .col-lg-12.form-group{margin-left:0;}
.control-label.text-center{text-align:center;}
.info-content-box{height:auto;border:none;}
.info-content-box #edui1{max-width:100%;}
.edui-default .edui-editor-toolbarboxouter{background:none;}
.edui-default .edui-editor-toolbarbox{box-shadow:none;}
.edui-default .edui-box{padding:3px;}
@-moz-document url-prefix(){
	.edui-default .edui-editor-toolbarbox{ position:relative!important;left:0!important;top:0!important;}
}

.overlay{
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(255,255,255,.8);
	color: #000;
	text-align: center;
	padding-top: 20%;
	font-size:16px;
	z-index:99999;
}
.overlay  img{
	display:inline-block;
	margin-right:6px;
	width:24px;
	height:24px;
}
legend{
	padding-bottom: 5px;
	margin-bottom:10px;
}
#jsTree{margin-top:16px;}
.jstree-default>.jstree-container-ul>.jstree-node > i{background:none;}
.c-fieldset .item{display:none;}
.c-fieldset .item:first-child{display:none;}
.col-sm-6{margin-bottom:16px;}
.form-horizontal .control-label{margin-bottom:8px;}
.form-horizontal .form-group{padding: 16px 32px;border-bottom: 1px solid #ddd;position:relative;margin-bottom:30px;margin-top:16px;}
.form-control{border-radius:5px;color:#888;}
.ibox-content{padding:20px 36px;}
.simpleFileUpload-box .form-group{border:none;margin:0;}
.uploadContainer .form-group{padding:0;}
.uploadContainer .form-group.text-left{padding: 0;width: auto;margin-right: 16px;}
.uploadContainer .form-group > .form-group.col-sm-4{display:none;}
.uploadContainer .image-preview .col-sm-7{padding: 0;position: relative; top: -22px;width:50%;}
.form-group .u-tt{position: absolute;top: -12px; background: #fff; padding: 0;color: #1c84c6;font-weight: 700;}
</style>
<div class="col-sm-12">
	
	<div class="col-sm-2">
               <select class="input-sm form-control input-s-sm inline" onchange="changeType();" name="type">
               		<c:forEach items="${type_list }"  var="typels">
                   <option value="${typels.type}" <c:if test="${typels.type==type }">selected</c:if>>${typels.name}</option>
                   </c:forEach>
               </select>
	               
		<div id="jsTree"></div>
	</div>
	
	<div class=" col-sm-10 container">
			<div class="ibox-title  text-left">
          <a href="javascript:void(0);" onclick="addservice();" title='新增服务' class="btn btn-primary btn-sm">新增服务</a>
          <a href="javascript:void(0);" onclick="addapp();" title='新增应用' class="btn btn-primary btn-sm">新增应用</a>
          </div>
      </div>
	<div class="col-sm-10 container" id="all_div">
			
		<div class="ibox-title">
			<h5>${menuInfo.name }设置</h5>
		</div>
		<div class="ibox-title">
			<button class="btn btn-info " type="button" id="top_btn"><i class="fa fa-mail-reply-all"></i> 保存</button>
		</div>
		<div class="ibox float-e-margins">
	        <div class="ibox-content">
	            <form id="postForm" method="post" class="form-horizontal" >
	            	<c:forEach items="${info }" var="data" varStatus="data_stat">
	            		<fieldset class="c-fieldset">
	            			<!-- <legend>服务</legend> -->
	            			<div class="form-group ${data.key } item">
	            				<div class="row">
							    	<div class="col-sm-6">
						    			<label class="control-label">服务名称</label>
						    			<input type="text" class="form-control"  name="fumc" value="${data.key }"/>
							    	</div>
							    	
							    	<div class="col-sm-6">
							    		<label class="control-label">排序</label>
							    		<input type="text" class="form-control"  name="fwpx" value="${data.value['px'] }"/>
							    	</div>
								</div>
							</div>
							
							<c:forEach items="${info[data.key]['items'] }" var="item" varStatus="stat">
								<div class="form-group app_item item ${item.key}">
								    <div class="row">
								    	<div class="col-sm-6">
							    			<label class="control-label">应用名称</label>
								    		<input name="name" type="text" class="form-control" value="${item.key}" style=";" maxlength="50" />
											<input type="hidden" name="index" value="${data_stat.count}_${stat.count }"/>
								    	</div>
								    	
								    	<div class="col-sm-6">
								    		<label class="control-label">链接url</label>
								    		<input name="url" placeholder="*站内栏目或文章直接输入栏目代号或文章代号" type="text" class="form-control"  value="${item.value['url']}"  maxlength="200" />
								    	</div>
									</div>
									
									<div class="row">
								    	<div class="col-sm-6">
							    			<label class="control-label">链接方式</label>
							    			<select class="form-control"  name="linktype">
												<option value="">...请选择...</option>
												<option value="1" <c:if test="${item.value['linktype']=='1' }">selected</c:if>>站内栏目</option>
												<option value="2" <c:if test="${item.value['linktype']=='2' }">selected</c:if>>站内文章</option>
												<option value="3" <c:if test="${item.value['linktype']=='3' }">selected</c:if>>站外文章</option>
												<option value="4" <c:if test="${item.value['linktype']=='4' }">selected</c:if>>跳蚤市场</option>
												<option value="6" <c:if test="${item.value['linktype']=='6' }">selected</c:if>>随手拍</option>
												<option value="5" <c:if test="${item.value['linktype']=='5' }">selected</c:if>>电子书</option>
												<option value="7" <c:if test="${item.value['linktype']=='7' }">selected</c:if>>专题</option>
												<option value="8" <c:if test="${item.value['linktype']=='8' }">selected</c:if>>电台</option>
												<option value="9" <c:if test="${item.value['linktype']=='9' }">selected</c:if>>多栏目展示</option>
											</select>
								    	</div>
								    	
								    	<div class="col-sm-6">
								    		<label class="control-label">是否启用</label>
								    		<select class="form-control"  name="enabled">
												<option value="1" <c:if test="${item.value['enabled']=='1' }">selected</c:if>>是</option>
												<option value="0" <c:if test="${item.value['enabled']=='0' }">selected</c:if>>否</option>
											</select>
								    	</div>
									</div>
									<div class="row">
								    	<div class="col-sm-6">
							    			<label class="control-label">可见范围</label>
							    			<select class="form-control"  name="range">
												<option value="1" <c:if test="${item.value['range']=='1' }">selected</c:if>>校内可见</option>
												<option value="2" <c:if test="${item.value['range']=='2' }">selected</c:if>>校外可见，不可分享</option>
												<option value="3" <c:if test="${item.value['range']=='3' }">selected</c:if>>校外可见，可分享</option>
											</select>
								    	</div>
								    	
								    	<div class="col-sm-6">
								    		<label class="control-label">排序</label>
								    		<input type="text" class="form-control"  name="px" value="${item.value['px']}" maxlength="50" />
								    	</div>
									</div>
									
									<div class="row">
								    	<div class="col-sm-12">
							    			<label class="control-label">图标</label>
								    	</div>
								    	<div class="col-sm-12 uploadContainer">
								    		<div id="div_${data_stat.count}_${stat.count}" ></div>
											<script type="text/javascript">
												$(function(){
												    	$('#div_${data_stat.count}_${stat.count}').load('cms/selector/imgUploadTag?id=image_${data_stat.count}_${stat.count}&value=${item.value["image"]}');
												});
											</script>
								    	</div>
									</div>
									
									</div>
								</c:forEach>
							</fieldset>
	            	</c:forEach>
	            	<div class="form-group" style="border:none;">
		                <div class="col-sm-12 text-right">
					      		<button title='保存' class="btn btn-primary" id="bottom_btn" type="button">保存</button>
		                </div>
		            </div>
	            </form>
	        </div>
	    </div>
	</div>
	
	
	<div class="col-sm-10 container" id="service_div" style="display:none">
		<div class="ibox-title">
			<h5>服务新增</h5>
		</div>
		<div class="ibox float-e-margins">
	        <div class="ibox-content">
	            <form method="post" id="serviceForm" class="form-horizontal" action="cms/discover/addService">
	            	<div class="form-group item ">
	            	<div class="row">
				    	<div class="col-sm-6">
			    			<label class="control-label">服务名称</label>
			    			<input type="hidden" name="type" value="${type }"/>
			    			<input type="text" class="form-control"  name="fwmc"/>
				    	</div>
				    	
				    	<div class="col-sm-6">
				    		<label class="control-label">排序</label>
				    		<input type="text" class="form-control"  name="fwpx"/>
				    	</div>
					</div>
					<div class="form-group" style="border:none;">
		                <div class="col-sm-12 text-right">
					      		<button title='保存' class="btn btn-primary" onclick="submitForm(1);" type="button">保存</button>
		                </div>
		            </div>
		            </div>
	            </form>
	        </div>
	    </div>
	</div>
	
	<div class="col-sm-10 container" id="app_div" style="display:none">
		<div class="ibox-title">
			<h5>应用新增</h5>
		</div>
		<div class="ibox float-e-margins">
	        <div class="ibox-content">
	            <form id="appForm" method="post" class="form-horizontal" action="cms/discover/addApp">
	            	<div class="form-group item ">
	            	<div class="row">
				    	<div class="col-sm-6">
			    			<label class="control-label">服务名称</label>
			    			<input type="hidden" name="type" value="${type }"/>
			    			
			    			<select class="form-control"  name="fwmc">
			    				<c:forEach items="${serviceInfos }" var="serinfo">
			    					<option value="${serinfo }">${serinfo }</option>
			    				</c:forEach>
							</select>
				    	</div>
					</div>
					<div class="row">
				    	<div class="col-sm-6">
			    			<label class="control-label">应用名称</label>
				    		<input name="name" type="text" class="form-control" style=";" maxlength="50" />
				    	</div>
				    	
				    	<div class="col-sm-6">
				    		<label class="control-label">链接url</label>
				    		<input name="url" placeholder="*站内栏目或文章直接输入栏目代号或文章代号" type="text" class="form-control" maxlength="200" />
				    	</div>
					</div>
					
					<div class="row">
				    	<div class="col-sm-6">
			    			<label class="control-label">链接方式</label>
			    			<select class="form-control"  name="linktype">
								<option value="">...请选择...</option>
												<option value="1">站内栏目</option>
												<option value="2" >站内文章</option>
												<option value="3" >站外文章</option>
												<option value="4" >跳蚤市场</option>
												<option value="6" >随手拍</option>
												<option value="7" >专题</option>
												<option value="8" >电台</option>
												<option value="9" >多栏目展示</option>
							</select>
				    	</div>
				    	
				    	<div class="col-sm-6">
				    		<label class="control-label">是否启用</label>
				    		<select class="form-control"  name="enabled">
								<option value="1" >是</option>
								<option value="0" >否</option>
							</select>
				    	</div>
					</div>
					<div class="row">
				    	<div class="col-sm-6">
			    			<label class="control-label">可见范围</label>
			    			<select class="form-control"  name="range">
								<option value="1" >校内可见</option>
								<option value="2" >校外可见，不可分享</option>
								<option value="3" >校外可见，可分享</option>
							</select>
				    	</div>
				    	
				    	<div class="col-sm-6">
				    		<label class="control-label">排序</label>
				    		<input type="text" class="form-control"  name="px" maxlength="50" />
				    	</div>
					</div>
					
					<div class="row">
				    	<div class="col-sm-12">
			    			<label class="control-label">图标</label>
				    	</div>
				    	<div class="col-sm-12 uploadContainer">
				    		<div id="div_0_0" ></div>
							<script type="text/javascript">
								$(function(){
								    	$('#div_0_0').load('cms/selector/imgUploadTag?id=image_0_0&value=/uploads/p/20160815/png/d84ccfc2-34d3-44f7-876d-ba0b0ea4db99.png');
								});
							</script>
				    	</div>
					</div>
					<div class="form-group" style="border:none;">
		                <div class="col-sm-12 text-right">
					      		<button title='保存' class="btn btn-primary" onclick="submitForm(2);" type="button">保存</button>
		                </div>
		            </div>
		            </div>
	            </form>
	        </div>
	    </div>
	</div>

	
</div>
<script type="text/javascript" src="${ctxPath}/static/base/js/HashMap.js"></script>
<script type="text/javascript">
	function changeType(){
		var type = $("select[name='type']").val();
		loadFrameContent('cms/discover/view?type=' +type);
	}
	
		
	//$.get("/uploads/servicejson/discover_json.txt",  function(data){
		var data = eval('(${jsonText})');
		//if(typeof data !== "object"){
		//	return;
		//}
		//console.log(data);
		var jstree_json = jsonToArr(data);
		init_jsTree(jstree_json);
		
		function jsonToArr(json){
			var arr = [{
				id : 0,
				text : "${menuInfo.name}",
				state:{"opened":true},
				children: []
			}];
			for(var key in json){
				var item = {
					id: key,
					text: key,
					state:{"opened":true},
					children:[]
				};
				for(var key1 in json[key].items){
					item.children.push({
						id: key1,
						text: key1,
						state:{},
						children:[]
					});
				}
				arr[0].children.push(item);
			}
			return arr;
		}
		
		function addservice(){
			$("#all_div").css("display", "none");
			$("#service_div").css("display", "");
			$("#app_div").css("display", "none");
		}
		
		function addapp(){
			$("#all_div").css("display", "none");
			$("#service_div").css("display", "none");
			$("#app_div").css("display", "");
		}
		
		function init_jsTree(data){
			//console.log(data);
			//jstree
			$("#jsTree").jstree({
	            "core" : { 'data' : data }, 
	            "themes": { "theme": "default", "dots": false, "icons": true },
	            "plugins": ["themes", "ui"]
	        }).
	        bind("select_node.jstree",  function(e,data){
	        	$("#all_div").css("display", "");
				$("#service_div").css("display", "none");
				$("#app_div").css("display", "none");
	        	var node = data.node.id,
	        		children = data.node.children_d;
	        	$(".c-fieldset .item").hide();
	        	$(("." + node)).show();
	        	for(var i = 0;i<children.length;i++){
	        		$(("." + children[i])).show();
	        	}
	        }).
	        on("ready.jstree", function(){
	        	$('#jsTree').jstree('select_node', '.jstree-node[aria-level="2"]:first-child');
	        });
		}
		
	//}, "json");
	

	$(function(){
		
		$("#top_btn").click(saveSetting);
		$("#bottom_btn").click(saveSetting);
		
		 $("#serviceForm").validate({
		        rules: {
		        	fwmc: {
		                required: true,
		                minlength: 1
		            },
		            fwpx: {
		                required: true,
		                number: true
		            }
		        }
		    });
		 
		 $("#appForm").validate({
			 rules: {
		        	name: {
		                required: true,
		                minlength: 1
		            },
		            fwmc: {
		                required: true,
		                minlength: 1
		            }
		        }
		 });
	});
	
	
	function submitForm(flag){
		if(flag == 1){
			if($("#serviceForm").valid()==false){
				return;
			}
			var postData = $("#serviceForm").serializeArray();
			$.post($("#serviceForm").attr("action"), postData, function (json) {
		        if (json.resultStatus) {
		        	loadFrameContent('cms/discover/view?type=${type}');
		        } else {
		        	layer.alert("失败了："+ json.resultMsg, {icon: 2});
		        }
		    }, "json");
		}else{
			if($("#appForm").valid()==false){
				return;
			}
			var postData = $("#appForm").serializeArray();
			$.post($("#appForm").attr("action"), postData, function (json) {
		        if (json.resultStatus) {
		        	loadFrameContent('cms/discover/view?type=${type}');
		        } else {
		        	layer.alert("失败了："+ json.resultMsg, {icon: 2});
		        }
		    }, "json");
		}
		
		
	}
	
	function saveSetting(){
		var fieldsets = $(document).find("fieldset");
		var params = new HashMap();
		for(var i = 0; i < fieldsets.length; i++){
			var fieldse = $(fieldsets[i]);
			var servicename = fieldse.find("[name='fumc']").val();
			if(!servicename){
				continue;
			}
			var fwpx = fieldse.find("[name='fwpx']").val();
			var service = new HashMap();
			service.put("px", fwpx);
			
			var tables = fieldse.find(".app_item");
			var items = new HashMap();
			for(var k = 0; k < tables.length; k++){
				var table = $(tables[k]);
				var name = table.find("[name='name']").val();
				if(!name){
					continue;
				}
				var index = table.find("[name='index']").val();
				var linktype = table.find("[name='linktype']").val();
				var url = table.find("[name='url']").val();
				var image = table.find("[name='image_"+index+"']").val();
				var range = table.find("[name='range']").val();
				var px = table.find("[name='px']").val();
				var enabled = table.find("[name='enabled']").val();
				var gc = new HashMap();
				gc.put("linktype", linktype);
				gc.put("enabled", enabled);
				gc.put("image", image);
				gc.put("range", range);
				gc.put("px", px);
				gc.put("url", url);
				items.put(name, gc);
			}
			service.put("items", items);
			params.put(servicename, service);
		}
		var data = params.tojson();
		$.post("cms/discover/update", {text : data,type:'${type}'}, function (json) {
	            if (json.resultStatus) {
	                //成功
	            	layer.msg("设置成功");
	            	loadFrameContent('cms/discover/view?type=${type}');
	            } else {
	            	//
	            	layer.alert("失败了："+ json.resultMsg, {icon: 2});
	            }
	        }, "json");
	}
</script>