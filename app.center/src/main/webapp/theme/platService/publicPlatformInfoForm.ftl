[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>红客移动客户端</title>
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/font-awesome/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/comm.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/index.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/nav.css" /> 
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/wechat.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/publicPlatform_infoform.css" />

<style>
	.frm_label{width:5em;}
	.frm_controls{display:inline-block;}
	.info_box .frm_input_box, .info_box .frm_msg, .info_box .frm_tips{width:616px;}
	.msglist{position:relative;overflow:hidden;}
	.msglist .bott{display:block;position:absolute;bottom:-35px;width:100%;height:35px;line-height:35px;background:rgba(50,50,50,0.7);cursor:pointer;}
	.msglist .bott i{width:24px;height:24px;display:inline-block;margin:0 10px;background:url(${ctxPath}/theme/platService/_files/images/icons-platformInfoForm.png) no-repeat;position:absolute;top:5px;}
	.msglist .bott i.i-up{left:10px;background-position:0 0;}
	.msglist .bott i.i-down{left:50px;background-position:0 -24px;}
	.msglist .bott i.i-delete{right:15px;background-position:0 -48px;}
	.msglist[data-index="0"] .bott i.i-up ,.msglist[data-index="0"] .bott i.i-delete{display:none;}
	.msglist[data-index="0"] .bott i.i-down{left:20px;}
	.msglist[data-index="6"] .bott i.i-down{display:none;}
</style>
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/JPlaceHolder.js"></script>
<!-- 配置文件 -->
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/ueditor/ueditor.all.js"></script>

<script type="text/javascript">
function formSaveSubmit(){
	saveInfoTemp();
	$("#state").val(1);
	$("#details").val(JSON.stringify(infoList));
	$("#commit").click();
	
	
}
function formSubmit(){
	saveInfoTemp();
	$("#state").val(2);
	$("#details").val(JSON.stringify(infoList));
	$("#commit").click();
}
//jquery ajax图片上传
function saveImage(o) {
    document.getElementById("over").style.display = "block";
    document.getElementById("layout").style.display = "block";
	   var $file=jQuery(o);
	   var fileName= $file.val();
	   var num = fileName.lastIndexOf(".");
	   var formartName = fileName.substring(num+1,fileName.length);
	   formartName = formartName.toLowerCase();
	   if(!(formartName == 'jpg' || formartName == 'jpeg' || formartName == 'png')){
			alert("图片只能上传jpg、jpeg、png类型文件！");
	   		return ;
	   }
       var size = 0;
       if ($.browser && $.browser.msie) {//ie旧版浏览器
           var fileMgr = new ActiveXObject("Scripting.FileSystemObject");
           var fileObj = fileMgr.getFile(filePath);
           size = fileObj.size; //byte
           size = size / 1024;//kb
           size = size / 1024;//mb
       } else {//其它浏览器
           size = $file[0].files[0].size;//byte
           size = size / 1024;//kb
           size = size / 1024;//mb
       }
       if(size > 10) {
			alert("请上传小于10M的图片！");
            document.getElementById("over").style.display = "none";
            document.getElementById("layout").style.display = "none";
			return ;
       }
	   var f1= "<form name='saveImg' action='${ctxPath}/upload_image.htx' method='post' enctype='multipart/form-data'>";
	   f1 += "<input type='hidden' name='scale' value='false' />";
	   f1 += "<input type='hidden' name='width' value='100' />";
	   f1 += "<input type='hidden' name='height' value='100' />";
	   f1 += "</form>";
       var hideForm = jQuery(f1);  
       hideForm.append($file);  
       hideForm.ajaxSubmit({
    	   dataType:'json',
             success: function (result, status) {
                 document.getElementById("over").style.display = "none";
                 document.getElementById("layout").style.display = "none";
            	 $("#img0").show();
                 $("#img0").attr('src', result.url);
                 $("#image").val(result.url);
                 setPreImg(result.url);
                 $("#imgfileP").html('<input id="imageFile0" name="imageFile" type="file" accept="image/*" style="display:none;" onchange="saveImage(this)" />');
             }
         });
}
</script>

</head>
<body>
	<!-- Header Start -->
	[#include "inc_header_publicPlatform.ftl"/]
	<!-- Header End -->

	<!-- Main Start -->
	<div class="main">
		<div class="position"><span>当前位置：<i></i></span><a href="/">首页 </a>> <a href="/mobile/pp/create.htx">服务号</a>  > 文章发布</div>
			<div class="page_nav">
				<a href="/mobile/pp/notice.htx" class="icon_goback">返回上一层</a>
				<span class="gap">新建图文消息</span>
			</div>
		<div class="info_box clearfix">
			<div class="form_wrp right">
				<form action="/mobile/pp/saveMsg.htx" method="post" class="form">
					<input type="hidden" name="msgid" value="${servMsg.msgid}"/>
					<input type="hidden" name="serviceId" value="${appService.serviceId}"/>
					<input type="hidden" name="orgId" value="${appService.cmsOrg.id}"/>
					<input type="hidden" name="appService.serviceId" value="${appService.serviceId}"/>
					<input type="hidden" id="htmlInput" name="FHtml" />
					<input type="hidden" id="details" name="details" />
					<input type="hidden" id="state" name="state" />
					<fieldset class="frm_fieldset">
						<div class="frm_control_group">
							<label for="" class="frm_label">标题</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									<input name="FFullTitle" id="title" type="text" placeholder="请填写文章标题" class="frm_input" value="" onkeyup="javascript:setPreTitle(this);" >
									<script type="text/javascript">
										function changeTitle(obj){
											var title = $("#title").val();
											$("#prmTitle0").html(title);
										}
									</script>
								</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label">作者</label>
							<div class="frm_controls">
								<span class="frm_input_box">
									<input name="FAuthor" id="author" type="text" placeholder="请填写文章作者" class="frm_input" value="">
								</span>
							</div>
						</div>
						<div class="frm_control_group clearfix">
							<label for="" class="frm_label" style="float:left">正文</label>
							<div class="frm_controls" style="float:left">
								<!-- 加载编辑器的容器 -->
    							<script id="container" name="content" type="text/plain"></script>
							    <!-- 实例化编辑器 -->
							    <script type="text/javascript">
							        var ue = UE.getEditor('container',{
							        	toolbars: window.UEDITOR_CONFIG.toolbars_BasicPage,
										initialFrameWidth:620,
										initialFrameHeight:300,
										imageUrl:"${ctxPath}/upload_image.htx?ueditor=true",
										wordImageUrl:"${ctxPath}/upload_image.htx?ueditor=true",
										fileUrl:"${ctxPath}/upload_file.htx?ueditor=true",
										videoUrl:"${ctxPath}/upload_file.htx?ueditor=true",
										catcherUrl:"${ctxPath}/upload_file.htx?ueditor=true",
										imageManagerUrl:"${ctxPath}/upload_file.htx",
										getMovieUrl:"${ctxPath}/upload_file.htx"
									    });
							    </script>
							</div>
						</div>
						
						<div class="frm_control_group">
							<label for="" class="frm_label" style="padding: 20px 0 0 0;">上传封面</label>
							<input type="button" class="btn btn-default" value="选择图片" onclick='javascript:$("#imageFile0").click();'></input>
							<img id="img0" src="${ksbm.zpcflj}" width="100px" height="100px" style="display:none;"/>
							<input type="hidden" id="image" name="image" /> 
							<p id="imgfileP">
			                <input id="imageFile0" name="imageFile" type="file" accept="image/*" style="display:none;" onchange="saveImage(this)" />
			                </p>
		                
<!-- 							<input id="imageFile0" name="imageFile" type="file" onchange="saveImage(this)" style="display:none;"/> -->
							<div id="over" class="over" style="display:none;"></div>
							<div id="layout" class="layout" style="display:none;">
							<img src="" alt="" /></div>
						</div>
						<div class="frm_control_group">
							<label for="" class="frm_label" style="line-height: 80px;vertical-align: top;">摘要</label>
							<div class="frm_controls">
								<textarea  name="FMetaDescription" id="infoDesc" type="text" placeholder="选填，如果不填写会默认抓取正文前54个字" class="frm_textarea"></textarea>
							</div>
						</div>
						 <p style="text-align: left; margin-right:6px; margin-bottom:10px;padding-left: 5.5em;">
						 	<input type="button" class="btn btn-primary" value="保存" onclick="formSaveSubmit()" />
						 	<input type="button" class="btn btn-primary" value="发送" onclick="formSubmit()" />
		                    <input id="commit" style="display:none;" type="submit" value="提交" />
		                    <input type="button" class="btn btn-primary" value="预览" onclick="mobileShow()" />
		                    <script>
      			function mobileShow(){
      				var title = $("#title").val();
      				var author = $("#author").val();
      				var mydate = new Date();
      				var myyear = mydate.getFullYear(); //获取完整的年份(4位,1970-????)
      				var mymonth = mydate.getMonth()+1; //获取当前月份(0-11,0代表1月)
      				var mydate = mydate.getDate(); //获取当前日(1-31)
      				var mydateStr = myyear + '-' + mymonth + '-' + mydate;
      				var ue = UE.getEditor('container');
      				//获取html内容，返回: <p>hello</p>
      				var html = ue.getContent();
      				
				  	//if($(body2).height() > 800){
				  	var html = ' <div id="mobile-preview" style="background:rgba(0,0,0,0.8);position:fixed;width:100%;height:100%;left:0;top:0;z-index:9999;">' +
				  		 '<div class="close" style="width:24px;height:24px;background:url(${ctxPath}/theme/platService/_files/images/close.png);position:absolute;top:30px;right:30px;cursor:pointer;" onclick="(function(){$(&apos;#mobile-preview&apos;).remove();})();"></div>' +
								  	  '<div class="iphone" style="background:url(${ctxPath}/theme/platService/_files/images/iphone6p.png) no-repeat;background-size:cover;width:348px;height:700px;margin:5% auto 0;    background-color: #fff;border-radius: 45px;">'+
								  	  '<div style="top: 13%;position: relative;left: 10%;overflow: auto;height: 500px;width: 280px;">'+
								  	'<div style="font-size: x-large;display: inline-block;background-color: #000;width: 100%;text-align: center;"><font style="color: #FFF;">${appService.serviceFullname}</font></div>'+
								  	  '<div style="font-size: x-large;display: inline-block;">'+title+'</div>'+
								  	'<div style="font-size: large;display: inline-block;padding-top: 10px;color: slategrey;">'+mydateStr+'    ${appService.serviceFullname}</div>'+
								  	'<div style="font-size: large;display: inline-block;padding-top: 10px;">'+html+'</div>'+
								  	  '</div>'+
									'</div>'+
								'</div>';
						
				  	$("body").append(html);
      			}
			  </script>
		                </p>
								
					</fieldset>
				</form>
			</div>
			<div class="tips_show left" style="padding-top:0px;">
				<div style="height: auto; margin-bottom: 0px; margin-right: 0px; ">
					<div style="text-align: left;margin-left: 10px;margin-bottom: 10px;">
						<h4>图文列表</h4>
					</div>
					<div>
<!-- 						第一篇图文 -->
					<div class="msglist msglistpriheight msglistcurrent" data-id="1" data-index="0">
						<div class="primdiv imgdiv">
							<div class="backgrounddiv">
								<i class="icon_appmsg_thumb smallimg">缩略图</i>
							</div>
							<div class="currenttitlediv">
							<h4 class="currenttitle titlediv" >标题</h4>
							</div>
						</div>		
						<div class="bott"><i class="i-up"></i><i class="i-down"></i><i class="i-delete"></i></div>
					</div>
<!-- 						第二篇图文 -->
					<div class="msglist nodisplay" data-id="2" data-index="1">
						<div class="seconddiv">
							<div class="backgrounddiv imgdiv">
							<i class="icon_appmsg_thumb_small smallimg">缩略图</i>
							</div>
							<div>
							<h4 class="secondtitle titlediv">标题</h4>
							</div>
							<!-- 
							<div style="background:rgba(0,0,0,0.5)!important;position: relative;">
							<a>向上</a>
							<a>向下</a>
							<a>删除</a>
							</div>
							 -->		
						</div>
						<div class="bott"><i class="i-up"></i><i class="i-down"></i><i class="i-delete"></i></div>
					</div>
<!-- 						第三篇图文 -->
					<div class="msglist nodisplay" data-id="3" data-index="2">
						<div class="seconddiv">
							<div class="backgrounddiv imgdiv">
							<i class="icon_appmsg_thumb_small smallimg">缩略图</i>
							</div>
							<div>
							<h4 class="secondtitle titlediv">标题</h4>
							</div>
						</div>	
						<div class="bott"><i class="i-up"></i><i class="i-down"></i><i class="i-delete"></i></div>	
					</div>
<!-- 						第四篇图文 -->
					<div class="msglist nodisplay" data-id="4" data-index="3">
						<div class="seconddiv">
							<div class="backgrounddiv imgdiv">
							<i class="icon_appmsg_thumb_small smallimg">缩略图</i>
							</div>
							<div>
							<h4 class="secondtitle titlediv">标题</h4>
							</div>
						</div>		
						<div class="bott"><i class="i-up"></i><i class="i-down"></i><i class="i-delete"></i></div>
					</div>
<!-- 						第五篇图文 -->
					<div class="msglist nodisplay" data-id="5" data-index="4">
						<div class="seconddiv">
							<div class="backgrounddiv imgdiv">
							<i class="icon_appmsg_thumb_small smallimg">缩略图</i>
							</div>
							<div>
							<h4 class="secondtitle titlediv">标题</h4>
							</div>
						</div>	
						<div class="bott"><i class="i-up"></i><i class="i-down"></i><i class="i-delete"></i></div>	
					</div>
<!-- 						第六篇图文 -->
					<div class="msglist nodisplay" data-id="6" data-index="5">
						<div class="seconddiv">
							<div class="backgrounddiv imgdiv">
							<i class="icon_appmsg_thumb_small smallimg">缩略图</i>
							</div>
							<div>
							<h4 class="secondtitle titlediv">标题</h4>
							</div>
						</div>	
						<div class="bott"><i class="i-up"></i><i class="i-down"></i><i class="i-delete"></i></div>	
					</div>
<!-- 						第七篇图文 -->
					<div class="msglist nodisplay" data-id="7" data-index="6">
						<div class="seconddiv">
							<div class="backgrounddiv imgdiv">
							<i class="icon_appmsg_thumb_small smallimg">缩略图</i>
							</div>
							<div>
							<h4 class="secondtitle titlediv">标题</h4>
							</div>
						</div>	
						<div class="bott"><i class="i-up"></i><i class="i-down"></i><i class="i-delete"></i></div>	
					</div>

					</div>
					<a class="addMsgPreA" style="text-align:center;padding:20px;" onclick="javascript:addMsgList();"><i>增加一条</i></a>
				</div>
			 	
			</div>
		</div>
		<script type="text/javascript">
			var index = 0;
			var currentIndex = 0;
			var infoList = {};
			var ue = UE.getEditor('container');
			function addMsgList(){
				index++;
				saveInfoTemp();
				currentIndex = index;
				if(index == 6){
					$("a.addMsgPreA").addClass("nodisplay");
				}
				$("#title").val("");
				$("#author").val("");
				$("#infoDesc").val("");
				ue.execCommand('cleardoc');
				$("#img0").hide();
				$("#img0").attr('src', "");
				$("#image").val("");
				$(".msglistcurrent").removeClass("msglistcurrent");
				var obj = $("div[data-index='"+index+"']");
				obj.removeClass("nodisplay");
				obj.addClass("msglistcurrent");
				setPreImg("");
				setPreTitle();
				var bott = $('<div class="bott"><i class="i-up"></i><i class="i-down"></i><i class="i-delete"></i></div>');
			}
			
			$(".msglist").click(function(e){
				var obj = $(e.currentTarget);
				var id = obj.attr("data-id");
				var index2 = obj.attr("data-index");
				if(currentIndex != index2){
					saveInfoTemp();
					currentIndex = index2;
					var info = infoList["id-"+id];
					$("#title").val(info.title);
					$("#author").val(info.author);
					$("#infoDesc").val(info.infoDesc);
					ue.execCommand('cleardoc');
					ue.setContent(info.html);
					//ue.execCommand('inserthtml',htmlDecode(info.html));
					$("#img0").show();
					$("#img0").attr('src', info.img);
					$("#image").val(info.img);
					$(".msglistcurrent").removeClass("msglistcurrent");
					obj.addClass("msglistcurrent");
				}
			});
			
			function saveInfoTemp(){
				var id = $("div[data-index='"+currentIndex+"']").attr("data-id");
				var info = infoList["id-"+id];
				if(!info){
					info = {};
				}
				info.title = $("#title").val();
				info.author = $("#author").val();
				info.img = $("#image").val();
				info.index = currentIndex;
			    var html = ue.getContent();
				//info.html = htmlEncode(html);
			    info.html = html;
			    var infoDesc = $.trim($("#infoDesc").val());
			    if(infoDesc==''){
				    var txt = ue.getContentTxt();
			    	$("#infoDesc").val(txt.substring(0,54))
			    }
				info.infoDesc = $("#infoDesc").val();
			    
				infoList["id-"+id] = info;
			}
			function setPreImg(url){
				//$("#preImg0").css("background-image","url("+result.fileUrl+")");
				$(".msglistcurrent .imgdiv").css("background-image","url("+url+")");
				$(".msglistcurrent .smallimg").addClass("nodisplay");
			}
			function setPreTitle(){
				var title = $("#title").val();
				$(".msglistcurrent .titlediv").html(title);
			}
			function initMsg(){
				var ue = UE.getEditor('container');
				//对编辑器的操作最好在编辑器ready之后再做
				ue.ready(function() {
					[#if servMsgList ??]
					[#list servMsgList as being]
						//var html = "[#noescape]${being.html}[/#noescape]";${((topicCase.name)!'')?xhtml}
						initDiv("${being.title}", "${being.author}","${being.img}","${being.html}","${being.index}","${being.intro}", "${being.infoid}");	
					[/#list]
					[/#if]
				});
				
			}
			//Html编码获取Html转义实体
			function htmlEncode(value){
			  return $('<div/>').text(value).html();
			}
			//Html解码获取Html实体
			function htmlDecode(value){
			  return $('<div/>').html(value).text();
			}
			initMsg();
			function initDiv(title, author, img, html, index2, intro, infoid){
				//html = [#noescape]html[/#noescape];
				var id = $("div[data-index='"+index2+"']").attr("data-id");
				var info = {};
				info.title = title;
				info.author = author;
				info.img = img;
				info.index = index2;
				
				
				info.html = htmlDecode(html);
				
				info.infoDesc = intro;
				info.infoid = infoid;
			    
				infoList["id-"+id] = info;
				if(index < index2){
					index = index2;
					currentIndex = index;
				}
				clickMsgListDiv($("div[data-index='"+index2+"']"));
			}
			function clickMsgListDiv(obj){
				var id = obj.attr("data-id");
				var index2 = obj.attr("data-index");
				
					currentIndex = index2;
					var info = infoList["id-"+id];
					$("#title").val(htmlDecode(info.title));
					$("#author").val(info.author);
					$("#infoDesc").val(info.infoDesc);
					ue.execCommand('cleardoc');
					ue.setContent(info.html);
					$("#img0").show();
					$("#img0").attr('src', info.img);
					$("#image").val(info.img);
					$(".msglistcurrent").removeClass("msglistcurrent");
					obj.addClass("msglistcurrent");
					obj.removeClass("nodisplay");
					setPreImg(info.img);
					setPreTitle();
				
			}
			
			//缩略图排序移动
			$(document).ready(function(){
				//鼠标移动
				$(".msglist").mouseenter(function(){
					if(index > 0){
						var dataIndex = $(this).attr("data-index");
						if(dataIndex == index){
							$(this).find(".bott .i-down").addClass("nodisplay");
						}else{
							$(this).find(".bott .i-down").removeClass("nodisplay");
						}
						$(this).find(".bott").stop().animate({bottom:"0px"}, 100);
					}
				});
				//鼠标移开
				$(".msglist").mouseleave(function(){
					$(this).find(".bott").stop().animate({bottom:"-35px"}, 100);
				});
				//上移按钮
				$(".bott .i-up").click(function(){
					var msglist = $(this).parents(".msglist");
					var index = parseInt(msglist.attr("data-index"));
					//var prev = msglist.siblings('.msglist[data-index='+'"'+(index-1)+'"'+']');
					//msglist.attr("data-index", index-1);
					//prev.attr("data-index", index);
					//prev.before(msglist);
					saveInfoTemp();
					moveMsgList(index, 1);
				});
				//下移按钮
				$(".bott .i-down").click(function(){
					var msglist = $(this).parents(".msglist");
					var index = parseInt(msglist.attr("data-index"));
					//var next = msglist.siblings('.msglist[data-index='+'"'+(index+1)+'"'+']');
					//msglist.attr("data-index", index+1);
					//next.attr("data-index", index);
					//next.after(msglist);
					saveInfoTemp();
					moveMsgList(index, 2);
				});
				//删除按钮
				$(".bott .i-delete").click(function(){
					var msglist = $(this).parents(".msglist");
					var i = parseInt(msglist.attr("data-index"));
					var id = msglist.attr("data-id");
					infoList["id-"+id] = {};
					
					console.log("index="+i);
					msglist.addClass("nodisplay").removeClass("msglistcurrent");
					$(".msglist").each(function(){
						var ind = parseInt($(this).attr("data-index"));
						console.log("ind="+ind);
						if(ind > i){
							$(this).attr("data-index", ind-1);
						}else if(ind == i){
							$(this).attr("data-index", 6);
						}
					});
					$(".msglist[data-index='5']").after(msglist);
					index--;
					$("a.addMsgPreA").removeClass("nodisplay");
					
				});
			});
			function moveMsgList(dataIndex, opflag){
				var preDataIndex = dataIndex;
				if(opflag == 1){//上移
					preDataIndex = dataIndex - 1;
				}else{
					preDataIndex = dataIndex + 1;
				}
				var obj = $("div[data-index='"+dataIndex+"']");
				var objid = obj.attr("data-id");
				var preObj = $("div[data-index='"+preDataIndex+"']");
				var preObjid = preObj.attr("data-id");
				var objInfo = infoList["id-"+objid];
				var preObjInfo = infoList["id-"+preObjid];
				objInfo.index = preDataIndex;
				preObjInfo.index = dataIndex;
				infoList["id-"+objid] = preObjInfo;
				infoList["id-"+preObjid] = objInfo;
				
				clickMsgListDiv(obj);
				clickMsgListDiv(preObj);
				
			}
		</script>
		
			
		
	</div>
	<!-- Main End -->
	<!-- Footer Start -->
	<!-- Footer End -->
	</script>
    
    
</body>
</html>
[/#escape]