[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>红客移动客户端</title>
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/font-awesome/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/comm.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/list.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/wechat.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/publicPlatform_selfmenu.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/publicPlatform_infolist.css" />
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/masonry.pkgd.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/layer-v2.1/layer/layer.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/jquery-ui/jquery-ui.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/js/jquery-ui/jquery-ui.css" />


<style>
.info_box .left {
	margin: 0;
}


.content .right h3 i {
	background: url(${ctxPath}/theme/platService/_files/images/list.png) no-repeat 0 0;
	margin: 7px 5px 5px 5px;
	width: 16px;
	height: 16px;
}

.l-table1 tbody.t-content tr td:nth-child(2) {
	text-align: center;
}

.l-table1 tbody.t-title tr td {
	box-shadow: 0 0 0px 1px #999;
}

.info_box {
	width: 100%;
}

.info_box .right .right-box {
	padding: 0 10px;
}

.menu-box a.selfmenu {
	background: #e74c3c;
	color: #fff;
}
</style>
</head>
<body>
	[#include "inc_header_publicPlatform.ftl"/]
	<div class="main body">
		<div class="content info_box clearfix">
			[#include "inc_left_publicPlatform.ftl"]
			<div class="right">
				<div class="right-box">
					<h3>
						<i></i><span>自定义菜单</span>
					</h3>

					<div class="menu_setting_area">

						<div class="menu_preview_area">
							<div class="mobile_menu_preview">
								<div class="mobile_hd">${appService.serviceFullname}</div>
								<div class="mobile_bd">
									<!-- -->
									<ul class="pre_menu_list grid_line no_menu ui-sortable ui-sortable-disabled" id="menuList">
										<li class="js_addMenuBox pre_menu_item grid_item no_extra size1of1">
											<a href="javascript:void(0);" class="pre_menu_link js_addL1Btn" title="最多添加3个一级菜单" draggable="false"> 
												<i class="icon14_menu_add"></i> 
												<span class="js_addMenuTips">添加菜单</span>
											</a>
										</li>
									</ul>

								</div>
							</div>
							
						</div>
						<div class="menu_form_area">
							<div id="js_rightBox" class="portable_editor to_left" style="display: none;">
								<div class="editor_inner">
									<div class="global_mod float_layout menu_form_hd js_second_title_bar">
										<h4 class="global_info">菜单名称</h4>
										<div class="global_extra">
											<a href="javascript:void(0);" id="jsDelBt">删除菜单</a>
										</div>
									</div>
									<div id="js_innerNone" style="display: none;" class="msg_sender_tips tips_global">已添加子菜单，仅可设置菜单名称。</div>
									<div class="menu_form_area_form">
									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label for="menuname" class="col-sm-3 control-label" style="font-weight:100;" id="menunamelabel">菜单名称</label>
											
											<div class="col-sm-9">
												<input type="text" class="form-control" id="menuname"
													placeholder="请输入名字">
											</div>
											<p class="tishi">字数不超过4个汉字或8个字母</p>
										</div>
										
										<div class="form-group" id="js_innerMenuContext">
											<label for="menuTypeRadio" class="col-sm-3 control-label" style="font-weight:100; ">菜单内容</label>
											<div class="col-sm-9 radio">
												<label class="frm_radio_label" data-editing='0'> 
												   <input type="radio" name="menuTypeRadio" id="menuTypeRadio1" class="frm_radio" value="1" checked="checked">
												   <span class="lbl_content">发送消息</span> 
												</label> 
												<label class="frm_radio_label" data-editing='0'>
													<input type="radio" name="menuTypeRadio" id="menuTypeRadio0" class="frm_radio" value="0">
													<span class="lbl_content">跳转网页</span>
												</label>
											</div>
										</div>
									</form>
                                    <div class="menu_content" id="menuType1">
                                      <div class="menu_content_tit">
                                        <i class="fa fa-th-large"></i>
                                        <span>图文消息</span>
                                      </div>
                                      <div class="msg_send">
                                        <input type="hidden" id="msgid" onchange="javascript:msgidchange();"/>
                                        <div class="media_msg">
                                        	
                                        </div>
                                        <div class="media_cover">
                                          <a href="javascript:;" onclick="javascript:chooseServMsg();">
                                            <i></i>
                                            <p>从素材库中选择</p>
                                          </a>
                                          <script type="text/javascript">
                                          function chooseServMsg(){
	          									layer.open({
	          									    type: 2, //page层
	          									    area: ['960px', '620px'],
	          									    title: '选择素材',
	          									    shade: 0.6, //遮罩透明度
	          									    moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          									    shift: -1, //0-6的动画形式，-1不开启
	          									    content: 'chooseServMsg.htx?serviceId=${appService.serviceId}'
	          									});   
	          								}
                                          </script>
                                        </div>
                                        <div class="media_cover mar_l">
                                          <a href="/mobile/pp/infoForm.htx" target="_blank">
                                            <i></i>
                                            <p>新建图文消息</p>
                                          </a>
                                        </div>
                                      </div>
                                    </div>
                                    <div class="menu_content" id="menuType0" style="display:none;">
                                       <p class="tips_global">订阅者点击该子菜单会跳到以下链接</p>
                                       <p>
                                          <div  class="frm_label">页面地址</div>
										  <input type="text" class="form-control" id="menuvalueurl" placeholder="请输入页面地址">
                                       </p>
                                       <p class="tishi2">&nbsp;
<!--                                        <a>从公众号图文消息中选择</a> -->
                                       </p>
                                    </div>


								</div>
								</div>
							</div>

						</div>
					</div>
					<form action="/mobile/pp/saveSelfmenu.htx" method="post" class="form">
						<input type="hidden" name="serviceId" value="${appService.serviceId}"/>
						<input type="hidden" id="details" name="details" />
						
							<p style="text-align: center; margin-right:6px; margin-bottom:10px;padding-left: 5.5em;padding-top:5.5em;">
							<input type="button" class="btn btn-default" style="float:left;display:none;" id="btn_sort_start" value="菜单排序" onclick="sortMenuStart();" />
							<input type="button" class="btn btn-default" style="float:left;display:none;" id="btn_sort_end" value="排序完成" onclick="sortMenuEnd();" />
							
						 	<input type="button" class="btn btn-primary" value="保存并发布" id="btn_save_submit" onclick="formSubmit()" />
		                    <input id="commit" style="display:none;" type="submit" value="提交" />
<!-- 		                    <input type="button" class="btn btn-primary" value="预览" onclick="mobileShow()" /> -->
		                </p>
		            </form>
					<script type="text/javascript">
		        	var menu1_id = 1;
		        	var menu2_id = 1;
		        	var menuList = {};
		        	function formSubmit(){
		        		
		        		$("#details").val(JSON.stringify(menuList));
		        		$("#commit").click();
		        	}
	        $(document).ready(function(){
	        	//添加新的一级菜单
				$(".js_addL1Btn").click(function(){
					var pbox = $(this).parents(".js_addMenuBox");
					var pboxchild = $("#menuList").children();
					var className = "size1of3";
					var menuLength = Object.getOwnPropertyNames(menuList).length;//获取一级菜单个数
					if(menuLength == 0){
						className = "size1of2";
					}
					if(menuLength > 0){
						className = "size1of3";
					}
					if(menuLength == 2){
						pbox.css("display", "none")
					}
					pboxchild.removeClass("size1of1 size1of2 size1of3 selected current").addClass(className);
					
					pboxchild.find('.sub_pre_menu_box').css("display", "none");
					$("#menuList").removeClass("no_menu");
	        		//清除所有二级菜单的选中状态
	        		$(".sub_pre_menu_list").children().removeClass('selected').removeClass('current');
					var menuId = "menu1_" + menu1_id;
					var jsMenuObj = {};
					jsMenuObj.id = menuId;
					jsMenuObj.menutype = 1;
					jsMenuObj.menuname = "菜单名称";
					jsMenuObj.index = getMenuIndex();
					jsMenuObj.menuvalue = "";
					jsMenuObj.childMenu = {};
					menuList[menuId] = jsMenuObj;
					menu1_id++;
					var str = '<li class="jsMenu pre_menu_item grid_item jslevel1 ui-sortable ui-sortable-disabled '+className+' selected current" id="'+menuId+'">'+
								'<a href="javascript:void(0);" class="pre_menu_link" draggable="false">'+
			                    '        <i class="icon_menu_dot js_icon_menu_dot dn" style="display: none;"></i>'+
			                    '        <i class="icon20_common sort_gray"></i>'+
			                    '        <span class="js_l1Title">菜单名称</span>'+
			                     '   </a>'+
			                     '   <div class="sub_pre_menu_box js_l2TitleBox" style="">'+
			                     '       <ul class="sub_pre_menu_list">'+
			                     '           <li class="js_addMenuBox"><a href="javascript:void(0);" class="jsSubView js_addL2Btn" title="最多添加5个子菜单" draggable="false"><span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="icon14_menu_add icon14_menu_add_3"></i></span></a></li>'+
			                     '       </ul>'+
			                     '       <i class="arrow arrow_out"></i>'+
			                     '       <i class="arrow arrow_in"></i>'+
			                     '   </div>'+
			                    '</li>';
					pbox.before(str);
					pbox.find('.js_addMenuTips').remove();
					pbox.find(".icon14_menu_add").addClass("icon14_menu_add_2");

					initClickEnvent();
					initRightBox(1);
					$("#btn_sort_start").show();
	        	});
				initClickEnvent();
	        });//end ready
	        //初始化点击事件
	        function initClickEnvent(){
	        	//先移除click事件，否则会多次触发
	        	$(".jsMenu .pre_menu_link").unbind("click"); //移除click
	        	$(".js_addL2Btn").unbind("click"); //移除click
	        	$(".jslevel2").unbind("click"); //移除click
	        	//一级菜单点击事件
	        	$(".jsMenu .pre_menu_link").click(function(){
	        		$("#menuList").children().removeClass('selected').removeClass('current').find(".sub_pre_menu_box").css("display","none");
	        		$(this).parents(".jsMenu").addClass('selected').addClass('current').find(".sub_pre_menu_box").css("display","");
	        		//如果是在排序的情况下，没有子菜单，隐藏子菜单框
	        		if($("#menuList").is(".sorting"))
	        		if(!$(this).parents(".jsMenu").find(".sub_pre_menu_list").children().is(".jslevel2")){
	        			$(this).parents(".jsMenu").find(".sub_pre_menu_box").css("display","none");
	        		}
	        		$(".sub_pre_menu_list").children().removeClass('selected').removeClass('current');
	        		initRightBox(1);
	        	});
	        	//添加二级菜单点击事件
	        	$(".js_addL2Btn").click(function(){
	        		//清除一级菜单的选中状态
	        		$(this).parents(".pre_menu_item").removeClass('selected').removeClass('current');
	        		//清除所有二级菜单的选中状态
	        		$(".sub_pre_menu_list").children().removeClass('selected').removeClass('current');
	        		var parentId = $(this).parents(".pre_menu_item").attr("id");
	        		
	        		var menuId = "menu2_" + menu2_id;
	        		menu2_id++;
	        		
	        		var childMenu = {};
	        		childMenu.id = menuId;
	        		childMenu.parentId = parentId;
	        		childMenu.menutype = 1;
	        		childMenu.menuname = "子菜单名称";
	        		childMenu.menuvalue = "";
	        		childMenu.index = getMenuIndex(parentId);
	        		menuList[parentId].childMenu[menuId] = childMenu;
	        		
	        		
	        		var str = '<li id="'+menuId+'" class="jslevel2 selected current"><a href="javascript:void(0);" class="jsSubView" draggable="false"><span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="icon20_common sort_gray"></i><span class="js_l2Title">子菜单名称</span></span></a></li>';
	        		$(this).parents(".js_addMenuBox").before(str);
	        		initClickEnvent();
	        		initRightBox(2);
	        	});
	        	$(".jslevel2").click(function(){
	        		//清除一级菜单的选中状态
	        		$(this).parents(".pre_menu_item").removeClass('selected').removeClass('current');
	        		//清除所有二级菜单的选中状态
	        		$(".sub_pre_menu_list").children().removeClass('selected').removeClass('current');
	        		$(this).addClass('selected').addClass('current');
	        		initRightBox(2);
	        	});
	        };//end initClickEnvent
	        //初始化右边框中的内容
	        function initRightBox(level){
	        	$("#js_rightBox").css("display", "");
	        	var jsMenu = $(".jsMenu.selected.current");
	        	var menuId,menutype,menuname,menuvalue;
	        	if(jsMenu.length>0){
	        		menuId = jsMenu.attr("id");
	        		menutype = menuList[menuId].menutype;
	        		menuname = menuList[menuId].menuname;
	        		menuvalue = menuList[menuId].menuvalue;
	        		
	        		$(".global_info").html("菜单名称");
	        		$("#menunamelabel").html("菜单名称");
	        		$("#jsDelBt").html("删除菜单");
	        	}
	        		
	        	var jslevel2 = $(".jslevel2.selected.current");
	        	if(jslevel2.length>0){
	        		var parentId = jslevel2.parents(".pre_menu_item").attr("id");
	        		menuId = jslevel2.attr("id");
	        		var childMenu = menuList[parentId].childMenu[menuId];
	        		menutype = childMenu.menutype;
	        		menuname = childMenu.menuname;
	        		menuvalue = childMenu.menuvalue;
	        		$(".global_info").html("子菜单名称");
	        		$("#menunamelabel").html("子菜单名称");
	        		$("#jsDelBt").html("删除子菜单");

        			$("#js_innerMenuContext").css("display", "");
	        	}
	        	$("#menuname").val(menuname);
	        	if(menutype == 1){
	        		$('input[name="menuTypeRadio"][value=1]').prop("checked", "checked");
	        		$("#menuvalueurl").val("");
	        		$("#msgid").val(menuvalue);
	        		msgidchange();
	        	}
	        	if(menutype == 0){
	        		$('input[name="menuTypeRadio"][value=0]').prop("checked", "checked");
	        		$("#msgid").val("");
	        		$("#menuvalueurl").val(menuvalue);
	        	}
	        	if(menutype == 0){
	        		$("#menuType1").css("display", "none");
	        		$("#menuType0").css("display", "");
	        	}else{
	        		$("#menuType1").css("display", "");
	        		$("#menuType0").css("display", "none");
	        	}
	        	
	        	if(jsMenu.length>0){
	        		var childMenu = menuList[menuId].childMenu;
	        		var childLength = Object.getOwnPropertyNames(childMenu).length;//获取子菜单个数
	        		//如果有子菜单，一级菜单只允许设置菜单名称
	        		if(childLength == 0){
	        			$("#js_innerNone").css("display", "none");
	        			$("#js_innerMenuContext").css("display", "");
	        		}else{
	        			$("#js_innerNone").css("display", "");
	        			$("#js_innerMenuContext").css("display", "none");
		        		$("#menuType1").css("display", "none");
		        		$("#menuType0").css("display", "none");
	        		}
	        	}
	        };//end initRightBox
	        //菜单名称编辑事件
	        $("#menuname").keyup(function(){
	        	var menuname = $("#menuname").val();
	        	var jsMenu = $(".jsMenu.selected.current");
	        	if(jsMenu.length>0){
	        		jsMenu.find(".js_l1Title").html(menuname);
	        		var menuId = jsMenu.attr("id");
	        		menuList[menuId].menuname = menuname;
	        	}
	        		
	        	var jslevel2 = $(".jslevel2.selected.current");
	        	if(jslevel2.length>0){
	        		var parentId = jslevel2.parents(".pre_menu_item").attr("id");
	        		var menuId = jslevel2.attr("id");
	        		menuList[parentId].childMenu[menuId].menuname = menuname;
	        		jslevel2.find(".js_l2Title").html(menuname);
	        	}
	        });
	        //菜单内容，页面地址编辑事件
	        $("#menuvalueurl").keyup(function(){
	        	var menuvalue = $("#menuvalueurl").val();
	        	var menutype = $('input[name="menuTypeRadio"]:checked').val();
	        	if(menutype == 0){//页面地址
	        		//一级菜单内容
	        		var jsMenu = $(".jsMenu.selected.current");
		        	if(jsMenu.length>0){
		        		var menuId = jsMenu.attr("id");
		        		menuList[menuId].menuvalue = menuvalue;
		        	}
		        	//二级菜单内容
		        	var jslevel2 = $(".jslevel2.selected.current");
		        	if(jslevel2.length>0){
		        		var parentId = jslevel2.parents(".pre_menu_item").attr("id");
		        		var menuId = jslevel2.attr("id");
		        		menuList[parentId].childMenu[menuId].menuvalue = menuvalue;
		        	}
	        	}else{//消息回复
	        		
	        	}
	        });
	        //菜单内容单选按钮组事件
	        $('input[name="menuTypeRadio"]').click(function(){
	        	var menutype = $('input[name="menuTypeRadio"]:checked').val();
	        	if(menutype == 0){
	        		$("#menuType1").css("display", "none");
	        		$("#menuType0").css("display", "");
	        	}else{
	        		$("#menuType1").css("display", "");
	        		$("#menuType0").css("display", "none");
	        	}
	        	
	        	var jsMenu = $(".jsMenu.selected.current");
	        	if(jsMenu.length>0){
	        		var menuId = jsMenu.attr("id");
	        		menuList[menuId].menutype = menutype;
	        	}
	        		
	        	var jslevel2 = $(".jslevel2.selected.current");
	        	if(jslevel2.length>0){
	        		var parentId = jslevel2.parents(".pre_menu_item").attr("id");
	        		var menuId = jslevel2.attr("id");
	        		menuList[parentId].childMenu[menuId].menutype = menutype;
	        	}
	        });
	        //选择图文消息后，进行显示
	        function msgidchange(){
	        	var menuvalue = $("#msgid").val();
	        	$(".msg_send .media_cover").css("display", "");
	        	var jsMenu = $(".jsMenu.selected.current");
	        	if(jsMenu.length>0){
	        		var menuId = jsMenu.attr("id");
	        		menuList[menuId].menuvalue = menuvalue;
	        	}
	        		
	        	var jslevel2 = $(".jslevel2.selected.current");
	        	if(jslevel2.length>0){
	        		var parentId = jslevel2.parents(".pre_menu_item").attr("id");
	        		var menuId = jslevel2.attr("id");
	        		menuList[parentId].childMenu[menuId].menuvalue = menuvalue;
	        	}
	        	$(".media_msg").html("");
	        	if(menuvalue)
	        	$.post("/mobile/pp/selectedMsgById-"+$("#msgid").val()+".htx", {},function(json){
	        		
	        		var msg = json.resultObject;
	        		
	        		var html = '<div class="msglistbox">' +
	        					'<div>'+msg.pushTime+'</div>';
			        var detail = msg.detail;
			        for(var i = 0, len = detail.length; i<len;i++){
			        	var msgDetail = detail[i];
			        	if(msgDetail.index == 0){
			        		html += '<div class="msglist msglistpriheight msglistcurrent" data-id="1" data-index="0">'+
				        			'<div class="primdiv imgdiv" style="background-image:url(\''+msgDetail.headImage+'\')">'+
				        				'<div class="backgrounddiv"></div>'+
				        				'<div class="currenttitlediv">'+
				        					'<h4 class="currenttitle titlediv" >'+msgDetail.title+'</h4>'+
				        				'</div>'+
				        			'</div>'+
				        		'</div>';
			        	}
			        }
			        for(var i = 0, len = detail.length; i<len;i++){
			        	var msgDetail = detail[i];
			        	if(msgDetail.index != 0){
			        		html += '<div class="msglist" data-id="2" data-index="'+msgDetail.index+'">'+
				        			'<div class="seconddiv">'+
				        				'<div class="backgrounddiv imgdiv" style="background-image:url(\''+msgDetail.headImage+'\')"></div>'+
				        				'<div><h4 class="secondtitle titlediv">'+msgDetail.title+'</h4></div>'+
				        			'</div></div>';
			        	}
			        }
			        html += '<div style="float:right"><a onclick="javascript:js_delSelectedMsg();">删除</a></div>'
			        html += '</div>';
			        $(".media_msg").html(html);
			        $(".msg_send .media_cover").css("display", "none");
	        	},'json')
	        };//end msgidchange
	        //删除已经选择的图文消息
	        function js_delSelectedMsg(){
	        	$("#msgid").val('');
	        	$(".media_msg").html("");
	        	$(".msg_send .media_cover").css("display", "");
	        }
	        //获取菜单排序
	        function getMenuIndex(menuid){
	        	if(menuid){
	        		var childLength = Object.getOwnPropertyNames(menuList[menuid].childMenu).length;//获取二级菜单个数
	        		return childLength;
	        	}else{
	        		var menuLength = Object.getOwnPropertyNames(menuList).length;
	        		return menuLength;
	        	}
	        }
	        //重置菜单排序
	        function resetIndex(index, menuid){
	        	if(menuid){
	        		var childMenu = menuList[menuid].childMenu;
	        		for(var menu in childMenu){
	        			var obj = childMenu[menu];
	        			var menuIndex = obj.index;
	        			if(menuIndex > index){
	        				obj.index = menuIndex -1;
	        			}
	        		}
	        	}else{
	        		for(var menu in menuList){
	        			var obj = menuList[menu];
	        			var menuIndex = obj.index;
	        			if(menuIndex > index){
	        				obj.index = menuIndex -1;
	        			}
	        		}
	        	}
	        }
	        $("#jsDelBt").click(function(){
	        	var jsMenu = $(".jsMenu.selected.current");
	        	var menuname = $("#menuname").val();
	        	if(jsMenu.length>0){//删除一级菜单
	        		layer.confirm('删除后”'+menuname+'“菜单下设置的内容将被删除？', {
	  	   	    	  btn: ['确定','取消'] //按钮
	  	   	    	}, function(){
		  	   	    	var menuId = jsMenu.attr("id");
		  	   	    	var index = menuList[menuId].index;
		        		delete menuList[menuId];
		        		resetIndex(index);
		        		$("#"+menuId).remove();
		        		
		        		var menuLength = Object.getOwnPropertyNames(menuList).length;//获取一级菜单个数
		        		if(menuLength == 0){
		        			$("#menuList").addClass("no_menu");
		        			$(".js_addMenuBox").removeClass("size1of2").addClass("size1of1");
		        			$(".icon14_menu_add").removeClass("icon14_menu_add_2").after('<span class="js_addMenuTips">添加菜单</span>');
		        			$("#js_rightBox").css("display", "none");
		        			$("#btn_sort_start").hide();
		        		}
		        		if(menuLength == 1){
							var pboxchild = $("#menuList").children().removeClass("size1of3").addClass("size1of2");
							$("#menuList").children().find('.sub_pre_menu_box').css("display", "");
							$("#menuList").find('.jsMenu').addClass('selected current');
							initRightBox(1);
		        		}
		        		if(menuLength == 2){
		        			$("#menuList").find('.jsMenu:last').addClass('selected current');
		        			$("#menuList").find('.jsMenu:last').find('.sub_pre_menu_box').css("display", "")
		        			$(".js_addMenuBox").css("display", "")
							initRightBox(1);
		        		}
	  	   	    		layer.msg('删除成功', {icon: 1});
	  	   	    	}, function(){
	  	   	    		//layer.msg('点了取消', {icon: 1});
	  	   	    	});
	        	}
	        		
	        	var jslevel2 = $(".jslevel2.selected.current");
	        	if(jslevel2.length>0){//删除二级菜单
	        		layer.confirm('删除后”'+menuname+'“菜单下设置的内容将被删除？', {
	  	   	    	  btn: ['确定','取消'] //按钮
	  	   	    	}, function(){
		  	   	    	var parentId = jslevel2.parents(".pre_menu_item").attr("id");
		        		var menuId = jslevel2.attr("id");
		        		var index = menuList[parentId].childMenu[menuId].index;
		        		delete menuList[parentId].childMenu[menuId];
		        		resetIndex(index, parentId);
		        		
		        		$("#"+menuId).remove();
		        		
		        		var childLength = Object.getOwnPropertyNames(menuList[parentId].childMenu).length;//获取二级菜单个数
		        		if(childLength > 0){
		        			var obj = $("li[id='"+parentId+"']");
		        			var childrenLi = obj.find('.jslevel2:first').addClass('selected current');
							initRightBox(2);
		        		}
		        		layer.msg('删除成功', {icon: 1});
	  	   	    	}, function(){
	  	   	    		//layer.msg('点了取消', {icon: 1});
	  	   	    	});
	        	}
	        });// end $("#jsDelBt").click
	        //初始化菜单内容
	        function initSelfmenuList(){
	        	[#if selfmenuList ??]
				[#list selfmenuList as selfmenu]
	        		var menuId = "menu1_" + menu1_id;
	        		initParentMenu(menuId, '${selfmenu.name}','${selfmenu.type}','${selfmenu.value}','${selfmenu.order}');
	        		[#list selfmenu.child as selfmenuchild]
	        			initCHildMenu(menuId, '${selfmenuchild.name}','${selfmenuchild.type}','${selfmenuchild.value}','${selfmenuchild.order}');
	        		[/#list]
	        		menu1_id++;	
				[/#list]
				 initParentMenuStyle();
				[/#if]
	        };//end initSelfmenuList
	        function initParentMenu(menuId, menuname, menutype,menuvalue,menuindex){
	        	var jsMenu = $("#menuList").children(".js_addMenuBox");
	        	//var menuId = "menu1_" + menu1_id;
				var jsMenuObj = {};
				jsMenuObj.id = menuId;
				if(menutype){
					jsMenuObj.menutype = menutype;
				}else{
					jsMenuObj.menutype = 1;
				}
				
				jsMenuObj.menuname = menuname;
				jsMenuObj.index = menuindex;
				jsMenuObj.menuvalue = menuvalue;
				jsMenuObj.childMenu = {};
				menuList[menuId] = jsMenuObj;
				//menu1_id++;
				var str = '<li class="jsMenu pre_menu_item grid_item jslevel1 ui-sortable ui-sortable-disabled " id="'+menuId+'">'+
							'<a href="javascript:void(0);" class="pre_menu_link" draggable="false">'+
		                    '        <i class="icon_menu_dot js_icon_menu_dot dn" style="display: none;"></i>'+
		                    '        <i class="icon20_common sort_gray"></i>'+
		                    '        <span class="js_l1Title">'+menuname+'</span>'+
		                     '   </a>'+
		                     '   <div class="sub_pre_menu_box js_l2TitleBox" style="">'+
		                     '       <ul class="sub_pre_menu_list">'+
		                     '           <li class="js_addMenuBox"><a href="javascript:void(0);" class="jsSubView js_addL2Btn" title="最多添加5个子菜单" draggable="false"><span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="icon14_menu_add icon14_menu_add_3"></i></span></a></li>'+
		                     '       </ul>'+
		                     '       <i class="arrow arrow_out"></i>'+
		                     '       <i class="arrow arrow_in"></i>'+
		                     '   </div>'+
		                    '</li>';
		        jsMenu.before(str);
	        };
	        function initCHildMenu(parentId, menuname, menutype,menuvalue,menuindex){
	        	var menuId = "menu2_" + menu2_id;
        		menu2_id++;
        		var childMenu = {};
        		childMenu.id = menuId;
        		childMenu.parentId = parentId;
				if(menutype){
					childMenu.menutype = menutype;
				}else{
					childMenu.menutype = 1;
				}
        		childMenu.menuname = menuname;
        		childMenu.menuvalue = menuvalue;
        		childMenu.index = menuindex;
        		menuList[parentId].childMenu[menuId] = childMenu;
	        	var str = '<li id="'+menuId+'" class="jslevel2 selected current"><a href="javascript:void(0);" class="jsSubView" draggable="false"><span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="icon20_common sort_gray"></i><span class="js_l2Title">'+menuname+'</span></span></a></li>';
	        	$("#"+parentId).find(".sub_pre_menu_list").find(".js_addMenuBox").before(str);
	        }
	        function initParentMenuStyle(){
	        	var menuLength = Object.getOwnPropertyNames(menuList).length;//获取一级菜单个数
	        	if(menuLength > 0){
	        		$("#menuList").removeClass("no_menu");
	        		$("#menuList").find(".sub_pre_menu_box").css("display", "none");
	        		$("#menuList").children(".js_addMenuBox").find('.js_addMenuTips').remove();
        			$("#menuList").children(".js_addMenuBox").find(".icon14_menu_add").addClass("icon14_menu_add_2");
        			$("#btn_sort_start").show();
	        	}
        		if(menuLength == 3){
        			$("#menuList").children(".pre_menu_item").addClass("size1of3");
        			$("#menuList").children(".js_addMenuBox").css("display", "none");
        		}
        		if(menuLength == 2){
        			$("#menuList").children(".pre_menu_item").addClass("size1of3");
        			$("#menuList").children(".js_addMenuBox").removeClass("size1of1");
        		}
        		if(menuLength == 1){
        			$("#menuList").children(".pre_menu_item").addClass("size1of2");
        			$("#menuList").children(".js_addMenuBox").removeClass("size1of1");
        		}
	        }
	        initSelfmenuList();
	        
	        
    		function sortMenuStart(){
    			var menuLength = Object.getOwnPropertyNames(menuList).length;//获取一级菜单个数
    			$("#menuList").removeClass("ui-sortable-disabled").addClass("sorting");
    			if(menuLength == 2){
    				$("#menuList").children(".pre_menu_item").removeClass("size1of3").addClass("size1of2");
    				$("#menuList").children(".js_addMenuBox").css("display", "none");
    			}
    			if(menuLength == 1){
    				$("#menuList").children(".pre_menu_item").removeClass("size1of2").addClass("size1of1");
    				$("#menuList").children(".js_addMenuBox").css("display", "none");
    			}
    			$(".sub_pre_menu_list .js_addMenuBox").css("display", "none");
    			$(".sub_pre_menu_box").css("display","none");
    			//$(".sub_pre_menu_list").each(function(){
    			//	if($(this).has(".jslevel2")){
    			//	$(".sub_pre_menu_list").parent().hide();
    			//}
    			//})
    			
    			//一级菜单排序
    			$( "#menuList" ).sortable({
    				//placeholder: "ui-state-highlight",
    	            delay: 300,
    	            distance: 15
    	            //update: function( event, ui ) {
    	            //	alert(ui)
    	            //}
    	        });
        		$( "#menuList" ).disableSelection();
        		
        		//二级菜单排序
        		$(".sub_pre_menu_list").sortable({
    				//placeholder: "ui-state-highlight",
    	            delay: 300,
    	            distance: 15
    	        });
        		$(".sub_pre_menu_list").disableSelection();
        		$("#btn_sort_start").hide();
        		$("#btn_sort_end").show();
        		$("#btn_save_submit").hide();
    			//$("#menuList").children(".pre_menu_item").addClass("size1of3");
    		}
    		function sortMenuEnd(){
    			$( "#menuList" ).sortable( "destroy" );
    			$(".sub_pre_menu_list").sortable( "destroy" );
    			sortMenu();
    			$("#btn_sort_start").show();
        		$("#btn_sort_end").hide();
        		$("#btn_save_submit").show();
        		var menuLength = Object.getOwnPropertyNames(menuList).length;//获取一级菜单个数
        		$("#menuList").addClass("ui-sortable-disabled").removeClass("sorting");
        		if(menuLength == 2){
    				$("#menuList").children(".pre_menu_item").addClass("size1of3").removeClass("size1of2");
    				$("#menuList").children(".js_addMenuBox").css("display", "");
    			}
    			if(menuLength == 1){
    				$("#menuList").children(".pre_menu_item").addClass("size1of2").removeClass("size1of1");
    				$("#menuList").children(".js_addMenuBox").css("display", "");
    			}
    			$(".sub_pre_menu_list .js_addMenuBox").css("display", "");
    			//if(!$(".sub_pre_menu_list").is(".jslevel2")){
    			//	$(".sub_pre_menu_list").parent().show();
    			//}
    		}
    		function sortMenu(){
    			var jsMenus = $("#menuList").children(".jsMenu");
    			var parentIndex = 0;
    			jsMenus.each(function(){
    				var id = $(this).attr("id");
    				//alert(id);
    				//alert(menuList[id].index);
    				menuList[id].index = parentIndex;
    				parentIndex++;
    				//alert(menuList[id].index);
    				var jslevel2s = $(this).find(".sub_pre_menu_list").children(".jslevel2");
    				var childIndex = 0;
    				jslevel2s.each(function(){
    					var childId = $(this).attr("id");
    					//alert(childId)
    					//alert(menuList[id].childMenu[childId].index)
    					menuList[id].childMenu[childId].index = childIndex;
    					//alert(menuList[id].childMenu[childId].index)
    					childIndex++;
    				});
    			});
    		}
	        </script>
				</div>
			</div>
		</div>
	</div>
	[#include "inc_footer.ftl"/]
</body>
</html>
[/#escape]
