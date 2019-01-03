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

<script type="text/javascript">
	window.UEDITOR_HOME_URL = "${ctxPath}/static/base/js/h5editor/";
	var BASEURL = "${ctxPath}";
	
	function clean_helper(){
		console.log("clean_helper");
	}
	
</script>
 	
<script type="text/javascript" src="${ctxPath}/static/base/js/h5editor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/h5editor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/h5editor/lang/zh-cn/zh-cn.js"></script>
<!--  
   <script type="text/javascript" charset="utf-8" src="http://hgs.xiumi.us/uedit/ueditor.config.js"></script> 
   <script type="text/javascript" charset="utf-8" src="http://hgs.xiumi.us/uedit/ueditor.all.min.js"></script>
   <script type="text/javascript" src="http://hgs.xiumi.us/uedit/lang/zh-cn/zh-cn.js"></script>
-->
<script type="text/javascript">
	$(function(){
		
		//$('.ui-portlet-list > li').unbind("click");
		$('.ui-portlet-list > li').on('click',function(){
			var ret = false;
			var id = $(this).data('id');
			var rules = null;
			if( $(this).data('rules') ) {
				rules = eval('('+$(this).data('rules')+');');
			}
			ret = insertHtml("<section  data-id=\""+id+"\" class=\"_h5editor\">" + $(this).html() + "</section>",rules); 
		
		});
		
		
	});
	
	function insertHtml(t,e){t=current_editor.parseInsertPasteSetHtml(t);var i=$.trim(getSelectionHtml(!0));if(""!=i){if(e){var a=$("<div>"+i+"</div>");a.find("*").each(function(){$(this).removeAttr("style"),$(this).removeAttr("class"),$(this).removeAttr("placeholder")});for(var r in e.replace)a.find(r).each(function(){e.replace[r]&&""!=e.replace[r]?$(this).replaceWith("<"+e.replace[r]+">"+$(this).html()+"</"+e.replace[r]+">"):$(this).replaceWith($(this).html())});for(var r in e.attributes)a.find(r).attr(e.attributes[r]);for(var r in e.style)a.find(r).attr("style",e.style[r]);for(var r in e["class"])a.find(r).attr("class",e["class"][r]);for(var r in e.css)a.find(r).css(e.css[r]);return t=a.html(),current_editor.execCommand("insertHtml",t),current_editor.undoManger.save(),!0}i=strip_tags(i,"<br><p><h1><h2><h3><h4><h5><h6><img>");var a=$("<div>"+i+"</div>");a.find(".assistant").remove(),a.find("*").each(function(){$(this).removeAttr("style"),$(this).removeAttr("class"),$(this).removeAttr("placeholder")});var o=$("<div>"+t+"</div>");o.find("> ._h5editor").siblings("p").each(function(t){""!=$(this).html()&&"&nbsp;"!=$(this).html()&&"<br>"!=$(this).html()&&"<br/>"!=$(this).html()||"undefined"==typeof $(this).attr("style")&&$(this).remove()}),a.find("h1,h2,h3,h4,h5,h6").each(function(t){var e=o.find(".135title").eq(t);e&&e.size()>0?(e.html($.trim($(this).text())),$(this).remove()):$(this).replaceWith("<p>"+$(this).text()+"</p>")}),a.find("img").each(function(t){var e=o.find(".135bg").eq(t);e&&e.size()>0&&(e.css("background-image","url("+$(this).attr("src")+")"),$(this).remove())});var n=0;a.find("img").each(function(){for(var t=o.find("img").eq(n);t.hasClass("assistant");)n++,t=o.find("img").eq(n);t&&t.size()>0&&0==$(t).parents(".135brush").size()&&(t.attr("src",$(this).attr("src")),n++,$(this).remove())}),a.find("img").each(function(t){var e=o.find("image").eq(t);e&&e.size()>0&&(e.attr("xlink:href",$(this).attr("src")),$(this).remove())});var s=o.find(".135brush"),l=s.size();if(l>0)if(1==l){var d=o.find(".135brush:first");if("text"==d.data("brushtype"))d.html($.trim(a.text()));else{a.contents().each(function(t){var e=this;"IMG"!=this.tagName&&(""!=$.trim($(e).text())&&"BR"!=this.tagName&&""!=$(this).html()&&"&nbsp;"!=$(this).html()&&"<br>"!=$(this).html()&&"<br/>"!=$(this).html()||$(this).remove())});var c=d.data("style");c&&a.find("*").each(function(){$(this).attr("style",c)});var t=a.html();""!=t&&d.html(t)}}else a.contents().each(function(t){var e=this;if(3==e.nodeType&&(e=$("<p>"+$(this).text()+"</p>").get(0)),l>t){var i=s.eq(t);if("text"==i.data("brushtype"))i.html($.trim($(e).text()));else{var a=i.data("style");a&&$(e).attr("style",a),i.empty().append($(e))}}else{var i=s.eq(l-1);if("text"==i.data("brushtype"))i.append($(e).text());else{var a=i.data("style");a&&$(e).attr("style",a),i.append($(e))}}});return t=o.html(),current_editor.execCommand("insertHtml",t),current_editor.undoManger.save(),!0}return current_editor.execCommand("insertHtml",t),current_editor.undoManger.save(),!0}
	
	function parseMmbizUrl(t){return t;}
	
	function getSelectionHtml(t){var e=current_editor.selection.getRange();if("BODY"==e.startContainer.tagName&&e.startContainer===e.endContainer&&e.endOffset>0&&e.endOffset===e.startContainer.childNodes.length)return getEditorHtml(t);e.select();var i=current_editor.selection.getNative(),a=i.getRangeAt(0),r=a.cloneContents(),o=document.createElement("div");o.appendChild(r);var n=o.innerHTML;return""==n?"":parseH5EditorHtml(n)}

	function parseH5EditorHtml(t,e){t=parseMmbizUrl(t);var i=$("<div>"+t+" </div>");i.find("*").each(function(){if(this.style.transform)return void setElementTransform(this,this.style.transform);if("IMG"!=this.tagName&&"BR"!=this.tagName&&"TSPAN"!=this.tagName&&"TEXT"!=this.tagName&&"IMAGE"!=this.tagName){if("P"==this.tagName)return void $(this).css("white-space","normal");if("H1"==this.tagName)return $(this).css("font-weight","bold"),this.style.fontSize||(this.style.fontSize="32px"),void(this.style.lineHeight||(this.style.lineHeight="36px"));if("H2"==this.tagName)return $(this).css("font-weight","bold"),this.style.fontSize||(this.style.fontSize="27px"),void(this.style.lineHeight||(this.style.lineHeight="29px"));if("H3"==this.tagName)return $(this).css("font-weight","bold"),this.style.fontSize||(this.style.fontSize="19px"),void(this.style.lineHeight||(this.style.lineHeight="23px"));if("H4"==this.tagName)return $(this).css("font-weight","bold"),this.style.fontSize||(this.style.fontSize="16px"),void(this.style.lineHeight||(this.style.lineHeight="19px"));if("H5"==this.tagName)return $(this).css("font-weight","bold"),this.style.fontSize||(this.style.fontSize="14px"),void(this.style.lineHeight||(this.style.lineHeight="18px"));if("H6"==this.tagName)return $(this).css("font-weight","bold"),this.style.fontSize||(this.style.fontSize="12px"),void(this.style.lineHeight||(this.style.lineHeight="14px"));if("STRONG"!=this.tagName&&"SPAN"!=this.tagName&&"B"!=this.tagName&&"EM"!=this.tagName&&"I"!=this.tagName){if("OL"==this.tagName||"UL"==this.tagName||"DL"==this.tagName)return void $(this).css({margin:"0px",padding:"0 0 0 30px"});$(this).css({"box-sizing":"border-box"}),""==this.style.padding&&""==this.style.paddingLeft&&""==this.style.paddingRight&&""==this.style.paddingTop&&""==this.style.paddingBottom&&("TD"!=this.tagName&&"TH"!=this.tagName||(this.style.padding="5px 10px"))}}});var t=i.html();return""==$.trim(t)?"":t}
	
	function clean_helper(){}
	
	
	//功能扩展
	(function(){

	var utils = UE.utils;
	var dtd = UE.dom.dtd;
	var domUtils = UE.dom.domUtils;


	UE.plugins['autostyle'] = function(){

		this.setOpt({'autostyle': {
			mergeEmptyline: true,           //合并空行
			removeClass: true,              //去掉冗余的class
			removeEmptyline: false,         //去掉空行
			textAlign:"justify",               //段落的排版方式，可以是 left,right,center,justify 去掉这个属性表示不执行排版
			imageBlockLine: 'center',       //图片的浮动方式，独占一行剧中,左右浮动，默认: center,left,right,none 去掉这个属性表示不执行排版
			pasteFilter: false,             //根据规则过滤没事粘贴进来的内容
			clearFontSize: true,           //去掉所有的内嵌字号
			txtFontSize:'16px',
			clearFontFamily: false,         //去掉所有的内嵌字体，使用编辑器默认的字体
			removeEmptyNode: false,         // 去掉空节点
			//可以去掉的标签
			removeTagNames: utils.extend({div:1},dtd.$removeEmpty),
			indent: false,                  // 行首缩进
			indentValue : '2em',            //行首缩进的大小
			lineHeight:'1.6',
			marginBottom: '10px',
			clearAllStyle: true
		}});

		var me = this,
			opt = me.options.autostyle,
			remainClass = {
				'selectTdClass':1,
				'pagebreak':1,
				'anchorclass':1
			},
			remainTag = {
				'li':1
			},
			tags = {
				div:1,
				p:1,
				//trace:2183 这些也认为是行
				blockquote:1,center:1,h1:1,h2:1,h3:1,h4:1,h5:1,h6:1,
				span:1
			},
			highlightCont;
			
		//配置项目里没有对应的配置项
		if(!opt){
			return;
		}

		//readLocalOpts();

		function isLine(node,notEmpty){
			if(!node || node.nodeType == 3)
				return 0;
			if(domUtils.isBr(node))
				return 1;
			if(node && node.parentNode && tags[node.tagName.toLowerCase()]){
				if(highlightCont && highlightCont.contains(node)
					||
					node.getAttribute('pagebreak')
				){
					return 0;
				}

				return notEmpty ? !domUtils.isEmptyBlock(node) : domUtils.isEmptyBlock(node,new RegExp('[\\s'+ domUtils.fillChar	+']','g'));
			}
		}

		function removeNotAttributeSpan(node){
			if(!node.style.cssText){
				domUtils.removeAttributes(node,['style']);
				if(node.tagName.toLowerCase() == 'span' && domUtils.hasNoAttributes(node)){
					domUtils.remove(node,true);
				}
			}
		}
		
		
		
		function removeAllStyle(node){
			if(node.style.cssText){
				domUtils.removeAttributes(node,['style']);
			}
			
			domUtils.removeAttributes(node,['powered-by']);
		}
		
		function setImagMaxWidth(node){
			if(node.tagName.toLowerCase() == 'img'){
				//node.style.maxWidth = "100%";
				//node.style.cssText = "max-width:100%";
				domUtils.setStyle(node,'max-width', "100%");
				
			}

		}
		
		
		function autotype(type,html){

			var me = this,cont;
			if(html){
				if(!opt.pasteFilter){
					return;
				}
				cont = me.document.createElement('div');
				cont.innerHTML = html.html;
			}else{
				cont = me.document.body;
			}
			var nodes = domUtils.getElementsByTagName(cont,'*');

			// 行首缩进，段落方向，段间距，段内间距
			for(var i=0,ci;ci=nodes[i++];){

				if(me.fireEvent('excludeNodeinautotype',ci) === true){
					continue;
				}
				
				if(opt.clearAllStyle){
				
					removeAllStyle(ci);
					
				}
				
				 //font-size
				if(opt.clearFontSize && ci.style.fontSize){
					domUtils.removeStyle(ci,'font-size');

					removeNotAttributeSpan(ci);

				}
				//font-family
				if(opt.clearFontFamily && ci.style.fontFamily){
					domUtils.removeStyle(ci,'font-family');
					removeNotAttributeSpan(ci);
				}

				if(isLine(ci)){
					//合并空行
					if(opt.mergeEmptyline ){
						var next = ci.nextSibling,tmpNode,isBr = domUtils.isBr(ci);
						while(isLine(next)){
							tmpNode = next;
							next = tmpNode.nextSibling;
							if(isBr && (!next || next && !domUtils.isBr(next))){
								break;
							}
							domUtils.remove(tmpNode);
						}

					}
					 //去掉空行，保留占位的空行
					if(opt.removeEmptyline && domUtils.inDoc(ci,cont) && !remainTag[ci.parentNode.tagName.toLowerCase()] ){
						if(domUtils.isBr(ci)){
							next = ci.nextSibling;
							if(next && !domUtils.isBr(next)){
								continue;
							}
						}
						domUtils.remove(ci);
						continue;

					}

				}
				if(isLine(ci,true) && ci.tagName != 'SPAN'){
					if(opt.indent){
						ci.style.textIndent = opt.indentValue;
					}
					if(opt.textAlign){
						ci.style.textAlign = opt.textAlign;
					}
					if(opt.lineHeight){
						ci.style.lineHeight = opt.lineHeight;
					}
					
					if(opt.marginBottom){
						ci.style.marginBottom = opt.marginBottom;
					}

					if(opt.txtFontSize){
						ci.style.fontSize = opt.txtFontSize;
					}						

				}
				
				setImagMaxWidth(ci);

				//去掉class,保留的class不去掉
				if(opt.removeClass && ci.className && !remainClass[ci.className.toLowerCase()]){

					if(highlightCont && highlightCont.contains(ci)){
						 continue;
					}
					domUtils.removeAttributes(ci,['class']);
				}

				//表情不处理
				if(opt.imageBlockLine && ci.tagName.toLowerCase() == 'img' && !ci.getAttribute('emotion')){
					if(html){
						var img = ci;
						switch (opt.imageBlockLine){
							case 'left':
							case 'right':
							case 'none':
								var pN = img.parentNode,tmpNode,pre,next;
								while(dtd.$inline[pN.tagName] || pN.tagName == 'A'){
									pN = pN.parentNode;
								}
								tmpNode = pN;
								if(tmpNode.tagName == 'P' && domUtils.getStyle(tmpNode,'text-align') == 'center'){
									if(!domUtils.isBody(tmpNode) && domUtils.getChildCount(tmpNode,function(node){return !domUtils.isBr(node) && !domUtils.isWhitespace(node)}) == 1){
										pre = tmpNode.previousSibling;
										next = tmpNode.nextSibling;
										if(pre && next && pre.nodeType == 1 &&  next.nodeType == 1 && pre.tagName == next.tagName && domUtils.isBlockElm(pre)){
											pre.appendChild(tmpNode.firstChild);
											while(next.firstChild){
												pre.appendChild(next.firstChild);
											}
											domUtils.remove(tmpNode);
											domUtils.remove(next);
										}else{
											domUtils.setStyle(tmpNode,'text-align','');
										}


									}


								}
								domUtils.setStyle(img,'float', opt.imageBlockLine);
								break;
							case 'center':
								if(me.queryCommandValue('imagefloat') != 'center'){
									pN = img.parentNode;
									domUtils.setStyle(img,'float','none');
									tmpNode = img;
									while(pN && domUtils.getChildCount(pN,function(node){return !domUtils.isBr(node) && !domUtils.isWhitespace(node)}) == 1
										&& (dtd.$inline[pN.tagName] || pN.tagName == 'A')){
										tmpNode = pN;
										pN = pN.parentNode;
									}
									var pNode = me.document.createElement('p');
									domUtils.setAttributes(pNode,{

										style:'text-align:center'
									});
									tmpNode.parentNode.insertBefore(pNode,tmpNode);
									pNode.appendChild(tmpNode);
									domUtils.setStyle(tmpNode,'float','');

								}


						}
					} else {
						var range = me.selection.getRange();
						range.selectNode(ci).select();
						me.execCommand('imagefloat', opt.imageBlockLine);
					}

				}

				//去掉冗余的标签
				if(opt.removeEmptyNode){
					if(opt.removeTagNames[ci.tagName.toLowerCase()] && domUtils.hasNoAttributes(ci) && domUtils.isEmptyBlock(ci)){
						domUtils.remove(ci);
					}
				}
				
				
				
			}

			if(html){
				html.html = cont.innerHTML;
			}
		}
		if(opt.pasteFilter){
			me.addListener('beforepaste',autotype);
		}

		function DBC2SB(str) {
			var result = '';
			for (var i = 0; i < str.length; i++) {
				var code = str.charCodeAt(i); //获取当前字符的unicode编码
				if (code >= 65281 && code <= 65373)//在这个unicode编码范围中的是所有的英文字母已经各种字符
				{
					result += String.fromCharCode(str.charCodeAt(i) - 65248); //把全角字符的unicode编码转换为对应半角字符的unicode码
				} else if (code == 12288)//空格
				{
					result += String.fromCharCode(str.charCodeAt(i) - 12288 + 32);
				} else {
					result += str.charAt(i);
				}
			}
			return result;
		}
		function ToDBC(txtstring) {
			txtstring = utils.html(txtstring);
			var tmp = "";
			var mark = "";/*用于判断,如果是html尖括里的标记,则不进行全角的转换*/
			for (var i = 0; i < txtstring.length; i++) {
				if (txtstring.charCodeAt(i) == 32) {
					tmp = tmp + String.fromCharCode(12288);
				}
				else if (txtstring.charCodeAt(i) < 127) {
					tmp = tmp + String.fromCharCode(txtstring.charCodeAt(i) + 65248);
				}
				else {
					tmp += txtstring.charAt(i);
				}
			}
			return tmp;
		}

		function readLocalOpts() {
			var cookieOpt = me.getPreferences('autostyle');
			utils.extend(me.options.autostyle, cookieOpt);
		}

		me.commands['autostyle'] = {
			execCommand:function () {
				me.removeListener('beforepaste',autotype);
				if(opt.pasteFilter){
					me.addListener('beforepaste',autotype);
				}
				autotype.call(me)
			}

		};

	};

			
	})();
	
	
</script>

<style type="text/css">
* html{overflow-y:scroll;}
#nodeSelectDiv,#node2SelectDiv,#specialSelectDiv {position: absolute;top: 7px;right: 7px;height: 30px;}
input[name=node_name],input[name=specialnames]{padding-right:50px;}
.form-control{padding:0 12px;font-weight:400;}
.nav-tabs>li{margin:0;position:relative;bottom:-2px;}
.nav-tabs{border-bottom:none;}
a,a:hover,a:focus,a:link{outline:none;}
.in-tb td.in-lab{background:#fff;font-weight:700;color:#1AB394;}
.in-tb label{font-weight:400;}
#sourceurl_x,#sourceother{margin:10px 0;}
#datepicker{margin-bottom:0;}
.forwardlink{position:absolute;right:15px;top:10px;}
#imagesuploaddiv .fileinput-button,#fileuploaddiv .fileinput-button{margin-left:20px;margin-top:20px;}
#imagesuploaddiv .img_prev_div{padding-left:5px;}
#imagesuploaddiv .text-left{padding-left:0;}
#images_div > .col-lg-12.form-group{margin-left:0;}
.control-label.text-center{text-align:center;}
.info-content-box{height:auto;border:none;}
.info-content-box #edui1{max-width:100%;}
.edui-default .edui-editor-toolbarboxouter{background:none;}
.edui-default .edui-editor-toolbarbox{box-shadow:none;}
.edui-default .edui-box{padding:3px;}
.overlay{position:fixed;top:0;left:0;right:0;bottom:0;background-color:rgba(255,255,255,.8);color:#000;text-align:center;padding-top:20%;font-size:16px;z-index:99999;}
.overlay img{display:inline-block;margin-right:6px;width:24px;height:24px;}
._h5editor{max-width:100%!important;}
.ui-portlet-list{padding:0;padding-right:15px;}
.ui-portlet-list ul{list-style:none;}
.ui-portlet-list li{cursor:pointer;list-style:none;}
.edui-default .edui-for-mp3 .edui-icon{background-position:-18px -40px;}
.edui-default .edui-for-mp3 .edui-dialog-content{width:650px;height:400px;overflow:hidden;}
.edui-default .edui-for-autostyle .edui-icon{background-position:-640px -40px;}
.ui-portlet-list img{max-width:300px;max-height:300px;}
.tabs-container .tabs-left>.nav-tabs {margin-right: 0;}
#imageGallery{background:#fff;padding:10px;box-sizing: border-box;}
#imageGallery #listView{padding-bottom: 50px; min-height: 500px;}
#imageGallery .grid-item,#imageGallery .grid-sizer{width:23.5%;box-sizing: border-box;}
#imageGallery .gutter-sizer { width: 2%;}
#imageGallery .grid-item:after {content: '';display: block;clear: both;}
#imageGallery .grid-item{float: left;display: block;padding: 10px;background: #fff;margin-bottom: 10px;box-shadow: 0 1px 2px rgba(0,0,0,0.1);border: 1px solid #ddd;}
#imageGallery li .imageBox{width:100%;box-sizing: border-box;}
#imageGallery li .imageBox img{display:block;width:100%;}
#imageGallery li .title{text-align:left;padding: 5px 0px;    color: #e49a48;}
#imageGallery li .bottom{ background: #fafafa;padding:10px; margin-left: -10px;margin-right: -10px;margin-bottom: -10px;border-top: 1px solid #ddd;}
#imageGallery li .bottom .fileSize,#imageGallery li .bottom .createTime{font-size:12px;}   
#imageGallery li .bottom .createTime{float:right;position: relative;bottom: -2px;} 
#imageGallery .mui-pull-bottom {position: absolute;bottom: -0px;text-align: center;width: 100%;}
#imageGallery .mui-pull-bottom .u-loading img{width:16px;height:16px;} 
#imageType ,#image_title,#imageGallery_search {height: 28px;border-radius: 3px; border: 1px solid #ccc;}
#imageGallery_search{height:28px;padding: 4px 12px;border: 1px solid #31927e;}
#imageGallery .searchArea{border-bottom: 1px solid #ddd;margin-bottom: 10px; padding: 5px 0;}
#imageGallery .shadowBox{position:absolute;left:0;right:0;bottom:0;top:0;text-align:center;display:none;align-items:center;justify-content:center;background:rgba(0,0,0,0.6);z-index:9;}
#imageGallery .grid-item:hover .shadowBox{display:flex;}
#imageGallery ul{padding:0;list-style:none}
#imageGallery ul li{list-style:none}   
</style>

<div class="col-sm-12 p-info-form-h5">
	<div class="ibox-title">
		<h5>文档信息修改</h5>
	</div>
	<div class="ibox-title">
		<button class="btn btn-info " onclick="returnList();"  type="button" id="btn_back"><i class="fa fa-mail-reply-all"></i> 返回</button>
		<c:if test="${bean.id == null}"></c:if>
		<c:choose>  
		   <c:when test="${oprt=='create'}">  
				<button onclick="draftForm();" title='保存草稿' class="btn btn-info" type="button">保存草稿</button>
				<button onclick="submitFormCreat();" title='保存并返回' class="btn btn-info" type="button">保存并返回</button>
		   </c:when>  
		   <c:otherwise> 
				<button onclick="submitForm();" title='保存并返回' class="btn btn-info" type="button">保存并返回</button>
		   </c:otherwise>  
		</c:choose>
		
		<c:if test="${istoend==true }">
			<button class="btn btn-info " type="button" id="btn_finalPass"> 保存并发稿</button>
		</c:if>
		<c:if test="${bean.id != null}">
		<button class="btn btn-info " type="button" id="btn_auditPass"><i class="fa fa-mail-reply-all"></i> 保存并上呈</button>  
		<!-- <button class="btn btn-info " type="button" id="btn_auditReject"><i class="fa fa-mail-reply-all"></i> 撤销审核</button> -->
		<button class="btn btn-info " type="button" id="btn_auditReturn"><i class="fa fa-mail-reply-all"></i> 退稿</button>
		<!--  <button class="btn btn-info " type="button" id="btn_adriodpush"><i class="fa fa-mail-reply-all"></i> 安卓推送</button>
		<button class="btn btn-info " type="button" id="btn_iospush"><i class="fa fa-mail-reply-all"></i> IOS推送</button> -->
		<button class="btn btn-info " type="button" id="btn_iosadriodpush"><i class="fa fa-mail-reply-all"></i>一键推送</button>
		</c:if>
	</div>
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <form id="postForm" method="post" class="form-horizontal" action="cms/info/info${bean.id == null ? 'Save' : 'Update'}">
			   	<input type="hidden" name="cid" value="${cid}" />
			   	<input type="hidden" name="oid" value="${bean.id}" />
			   	<input type="hidden" name="queryNodeId" value="${queryNodeId}" />
			   	<input type="hidden" name="showDescendants" value="${showDescendants}" />
			   	<input type="hidden" name="queryStatus" id="queryStatus" value="${queryStatus}" />
			   	<input type="hidden" name="imageListJson" value="" />
			   	<input type="hidden" name="hasContent" id="hasContent" value="0" />
                <ul id="tabs" class="nav nav-tabs" style="display:none;">
					<li class="active" id="li_fields"><a href="javascript:void(0);">文档标题及属性</a></li>
					<li id="li_content"><a href="javascript:void(0);">文档正文</a></li>
				</ul>
				<div class="form-group">
				<table id="info_fields" border="0" cellpadding="0" cellspacing="0" class="in-tb">
				  <c:set var="colCount" value="${0}"/>
					  <c:forEach var="field" items="${model.normalFields}">
					  <c:if test="${colCount%2==0||!field.dblColumn}"><tr></c:if>
					  <td class="in-lab" width="15%"><c:if test="${field.required}"><em class="req">*</em></c:if><c:out value="${field.label}"/>:</td>
					  <td<c:if test="${field.type!=50}"> class="in-ctt"</c:if><c:choose><c:when test="${field.dblColumn}"> width="35%"</c:when><c:otherwise> width="85%" colspan="3"</c:otherwise></c:choose>>
					  <c:choose>
					  <c:when test="${field.custom || field.innerType == 3}">
					  	<c:choose>
					  		<c:when test="${field.name eq 'imageType'}">
							  	<select name="imageType" class="input-sm form-control input-s-sm inline">
									<c:forEach items="${imageTypeList }" var="sitem">
										<option value="${sitem.id }" <c:if test="${!empty imageType_selected && sitem.id==field.customs['imageType'] }">selected</c:if>>${sitem.name }</option>
									</c:forEach>  		
							  	</select>
							  	
					  		</c:when>
					  		<c:otherwise>
					  			<tags:feild_custom bean="${bean}" customs="${customs }"  field="${field}"/>
					  		</c:otherwise>	
					  	</c:choose>
					  </c:when>
					  <c:otherwise>
					  <c:choose>
					  <c:when test="${field.name eq 'node'}">
					  	<input type="hidden" id="nodeId" name="nodeId" value="${node.id}" />
				  	  	<input type="text" readonly="readonly" id="node_name" class="form-control" name="node_name" value="${node.name}">
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
					  	<input type="hidden" id="specialIds" name="specialIds" value="${specialIds}" />
				  	  	<input type="text" readonly="readonly" id="specialNames" class="form-control" name="specialNames" value="${specialNames}">
				  		<div id="specialSelectDiv"></div>
					  </c:when>
					  <c:when test="${field.name eq 'title'}">
					    <div>
				  			<input type="text" class="form-control" placeholder="${field.label}" maxlength="150" id="title" name="title" value="${bean.title}"/>
						    <label class="forwardLink"><input type="checkbox" onclick="changeLink();"<c:if test="${bean.linked}"> checked="checked"</c:if>/>转向链接</label>
						    <script type="text/javascript">
							    function changeLink(){
							    	$("#linkDiv").toggle(this.checked);
							    	if(!this.checked){
							    		$("input[name='link']").val('');
							    	}
							    }
						    </script>
						  </div>
					    <div id="linkDiv" style="padding-top:2px;<c:if test="${!bean.linked}">display:none;</c:if>">
				  			<input type="text" class="form-control" placeholder="超链接" maxlength="255" id="link" name="link" value="${bean.link}"/>
					  	</div>
					  </c:when>
					  <c:when test="${field.name eq 'subtitle'}">
			  			<input type="text" class="form-control" placeholder="${field.label}" maxlength="150" id="subtitle" name="subtitle" value="${bean.subtitle}"/>
					  </c:when>
					  <c:when test="${field.name eq 'fullTitle'}">
			  			<input type="text" class="form-control" placeholder="${field.label}" maxlength="150" id="fullTitle" name="fullTitle" value="${bean.fullTitle}"/>
					  </c:when>
					  <c:when test="${field.name eq 'metaDescription'}">
			   			<input type="hidden" name="remainDescription" value="true" />
				  		<textarea class="form-control" placeholder="${field.label}" maxlength="255" name="metaDescription">${bean.metaDescription}</textarea>
					  </c:when>
					  <c:when test="${field.name eq 'priority'}">
							<select name="priority" style="width:50px;">
					  		<c:forEach var="i" begin="0" end="9">
					  		<option<c:if test="${i==bean.priority}"> selected="selected"</c:if>>${i}</option>
					  		</c:forEach>
					  	</select>
					  </c:when>
					  <c:when test="${field.name eq 'publishDate'}">
			            <div class="form-group input-daterange" id="datepicker">
			                <div class="col-sm-5">
			                	<input type="text" id="publishDate" name="publishDate" class="form-control" value="<c:if test="${oprt=='edit'}"><fmt:formatDate value="${bean.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>" style="text-align: left"/>
			                </div>
			            	<label class="col-sm-1 control-label" for="offDate">至</label>
			                <div class="col-sm-5">
			                	<input type="text" id="offDate" name="offDate" class="form-control" value="<c:if test="${oprt=='edit'}"><fmt:formatDate value="${bean.offDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>" style="text-align: left"/>
			                </div>
			            </div>
					  </c:when>
					  <c:when test="${field.name eq 'infoTemplate'}">
	             			<input type="text" class="form-control" readonly="readonly" id="infoTemplate" name="infoTemplate" value="${bean.infoTemplate}"> 
	             			<div id="infoTemplateSelectDiv" class="SelectDiv"></div>
					  </c:when>
					  <c:when test="${field.name eq 'infoPath'}">
			  			<input type="text" class="form-control" placeholder="${field.label}" maxlength="255" id="infoPath" name="infoPath" value="${bean.infoPath}"/>
					  </c:when>
					  <c:when test="${field.name eq 'source'}">
					  	来源：
					  	<select name="sourceId" onchange="changeSource(this);">
							<option value="" iurl="" iname="">...请选择...</option>
							<c:forEach items="${sourceList }" var="sitem">
								<option iname="${sitem.name }" iurl="${sitem.url }" value="${sitem.id }" <c:if test="${!empty bean.infoSource && sitem.id==bean.infoSource.id }">selected</c:if>>${sitem.name }</option>
							</c:forEach>  		
					  	</select>
					    	名称：
				  			<input type="text"  style="width:150px;" class="form-control" placeholder="${field.label}" maxlength="255" id="sourceother" name="source" value="${bean.source}"/>
					    	地址:
					    	<input type="text"  style="width:200px;" class="form-control" placeholder="${field.label}" maxlength="255" id="sourceUrl_x" name="sourceUrl" value="${bean.sourceUrl}"/>
					    	<input type="checkbox" name=sourceFlag value="1" <c:if test="${bean.sourceFlag=='1'}"> checked="checked"</c:if>/>前端显示
					    <script type="text/javascript">
					    	function changeSource(obj){
					    		var opt = $(obj).find("option:selected");
					    		$("#sourceother").val(opt.attr("iname"));
					    		$("#sourceUrl_x").val(opt.attr("iurl"));
					    	}
					    </script>
					  </c:when>
					  <c:when test="${field.name eq 'author'}">
			  			<input type="text" class="form-control" placeholder="${field.label}" maxlength="50" id="author" name="author" value="${bean.author}"/>
					  </c:when>
					  <c:when test="${field.name eq 'allowComment'}">
					    <select name="allowComment">
					    	<option value="">--请选择--</option>
					    	<option value="true" <c:if test="${bean.allowComment!=null && bean.allowComment}">selected="selected"</c:if>>是</option>
					    	<option value="false" <c:if test="${bean.allowComment!=null && !bean.allowComment}">selected="selected"</c:if>>否</option>
					    </select>
					  </c:when>
					  <c:when test="${field.name eq 'attributes'}">
					  	<c:set var="attrs" value="${bean.attrs}"/>
					  	<c:forEach var="attr" items="${attrList}">
					  	<label><input type="checkbox" name="attrIds" value="${attr.id}" onclick="$('#attr_img_${attr.id}').toggle(this.checked);"<c:if test="${fnx:contains_co(attrs,attr)}"> checked="checked"</c:if>/><c:out value="${attr.name}"/>(<c:out value="${attr.number}"/>)</label> &nbsp;
					  	</c:forEach>
					    <c:forEach var="attr" items="${attrList}">
					    <c:if test="${attr.withImage}">
					    	</td>
					    </tr>
					    <tr id="attr_img_${attr.id}"<c:if test="${!fnx:contains_co(attrs,attr)}"> style="display:none;"</c:if>>
					    	<td class="in-lab" width="15%">
					    		<em class="req">*</em>${attr.name}
					    	</td>
					    	<td class="in-ctt" width="85%" colspan="3">
					    		<div id="attrImages_${attr.id}Div" data-with="${attr.imageWidth}" data-height="${attr.imageHeight}" data-value="${fnx:invoke1(bean,'getInfoAttr',attr).image}" data-watermark="${attr.watermark}" data-scale="${attr.scale}" data-exact="${attr.exact}"></div>
					    </c:if>
					    </c:forEach>
					  </c:when>
					  <c:when test="${field.name eq 'smallImage'}">
				  		<div id="smallImageUploadDiv" data-with="${field.customs['imageWidth']}" data-height="${field.customs['imageHeight']}" data-value="${bean.smallImage}" data-watermark="${field.customs['imageWatermark']}" data-scale="${field.customs['imageScale']}" data-stretch="${field.customs['imageExact']}"></div>
					  </c:when>
					  <c:when test="${field.name eq 'largeImage'}">
				  		<div id="largeImageUploadDiv" data-with="${field.customs['imageWidth']}" data-height="${field.customs['imageHeight']}" data-value="${bean.largeImage}" data-watermark="${field.customs['imageWatermark']}" data-scale="${field.customs['imageScale']}" data-stretch="${field.customs['imageExact']}"></div>
					  </c:when>
					  <c:when test="${field.name eq 'file'}">
					  	<div id="fileUploadDiv" data-fileNameId="fileName" data-fileName="${bean.fileName}" data-fileUrlId="file" data-fileUrl="${bean.file}" data-fileSizeId="fileLength" data-fileSize="${bean.fileLength}"></div>
					  </c:when>
					  <c:when test="${field.name eq 'video'}">
					  	<div id="videoUploadDiv" data-fileNameId="videoName" data-fileName="${bean.videoName}" data-fileUrlId="video" data-fileUrl="${bean.video}" data-fileSizeId="videoLength" data-fileSize="${bean.videoLength}" data-fileTimeId="videoTime" data-fileTime="${bean.videoTime}"></div>
					  </c:when>
					   <c:when test="${field.name eq 'audio'}">
					  	<div id="audioUploadDiv" data-fileNameId="audioName" data-fileName="${bean.audioName}" data-fileUrlId="audio" data-fileUrl="${bean.audio}" data-fileSizeId="audioLength" data-fileSize="${bean.audioLength}" data-fileTimeId="audioTime" data-fileTime="${bean.audioTime}"></div>
					  </c:when>
					  <c:when test="${field.name eq 'images'}">
					  	<div id="imagesUploadDiv" data-exact="${field.customs['imageExact']}"  data-watermark="${field.customs['imageWatermark']}"  data-scale="${field.customs['imageScale']}" data-with="${field.customs['imageWidth']}" data-height="${field.customs['imageHeight']}" data-imgListJson="${imgListJson}"></div>
					  </c:when>
					  <c:when test="${field.name eq 'tagKeywords'}">
					  	<input type="text" class="form-control" placeholder="${field.label}" maxlength="150" id="tagKeywords" name="tagKeywords" value="${bean.tagKeywords}"/>
					    <%-- <input type="button" value="<s:message code='info.getTagKeywords'/>" onclick="var button=this;$(button).prop('disabled',true);$.get('get_keywords.do',{title:$('input[name=title]').val()},function(data){$('input[name=tagKeywords]').val(data);$(button).prop('disabled',false);})"/> --%>
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
				    		
			<div class="row m-t-lg">
                <div class="col-lg-7">
                    <div id="h5_styles" class="tabs-container" style="display: none;">

                        <div class="tabs-left">
                            <ul class="nav nav-tabs">
                                <li class="active"><a data-toggle="tab" href="#tab-1-1">标题样式</a></li>
                                <li class=""><a data-toggle="tab" href="#tab-1-2">正文样式</a></li>
                                <li class=""><a data-toggle="tab" href="#tab-1-3">常用图片</a></li>
                                <li class="" id="tab_1_4"><a data-toggle="tab" href="#tab-1-4">图片库</a></li>
                            </ul>
                            <div class="tab-content ">
                                <div id="tab-1-1" class="tab-pane active">
                                    <div class="panel-body">
                                    <div id="editor-template-scroll" style="height:850px;overflow-y: auto;overflow-x: hidden;position:relative;">
                                        <div class="ui-portlet clearfix ">
											<ul class="editor-template-list ui-portlet-list">
												<li id="style-24" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="边框内容" class="style-item   tagtpl-109  tagtpl-226" data-id="24">
												
													<section class="_h5editor" data-tools="小标题" data-id="24"><section class="layout" style="margin:10px auto;"><section class="135brush" style="margin: 3px; padding: 15px;color: rgb(62, 62, 62); line-height: 24px;box-shadow: rgb(170, 170, 170) 0px 0px 3px;border:2px solid rgb(240,240,240)"><p>时间飞逝，人生百味杂陈，无法言说。仿佛一个人写了长长的信，但未等到那个可以投递的人。——《一封信》</p></section></section></section>
																		
												</li>
												<li id="style-29735" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="边框样式" class="style-item   tagtpl-109  tagtpl-226" data-id="29735">
												
													<section class="_h5editor" data-tools="小标题" data-id="29735">
														<section style="width:92px;margin-bottom:0px;">
														<p style="text-align: center; color: inherit; line-height: 2em;"><span style="border-color: #E27961; color: #E27961; font-size: 16px;"><strong style="border-color: rgb(226, 121, 97); color: inherit;">小标题</strong></span></p></section><section style="margin-top: 0px; padding: 0px 5px; line-height: 10px; color: inherit; border: 1px solid rgb(226, 121, 97);"><section style="padding: 0px; font-size: 16px; color: inherit; height: 8px; margin: -8px 0px 0px 140px; width: 50%; background-color: rgb(254, 254, 254);"><section style="width: 8px; height: 8px; border-radius: 100%; line-height: 1; box-sizing: border-box; font-size: 18px; text-decoration: inherit; border-color: rgb(226, 121, 97); display: inline-block; margin: 0px; color: inherit; background-color: rgb(226, 121, 97);"></section></section><section class="135brush" data-style="text-indent: 2em;" style="padding: 0px; line-height: 2em; color: rgb(62, 62, 62); font-size: 14px; margin: 15px;"><p style="text-indent: 2em;">每个人都有属于自己的一片森林。迷失的人，迷失了，相逢的人，会再相逢。 </p>
														<br>
														<p style="text-indent: 2em;">迟早要失去的东西并没有太多意义，必失之物的荣光并非真正的荣光。</p>
														<br>
														<p style="text-indent: 2em;">——村上春树《挪威的森林》</p>
														<br>
														<p style="text-indent: 2em;"></p>
														</section>
														<section style="padding: 0px; background-color: rgb(254, 254, 254); font-size: 16px; color: inherit; text-align: right; height: 10px; margin: 0px 0px -4px 25px; width: 65%;">
														<section style="margin: 0px auto 1px; border-radius: 100%; line-height: 1; box-sizing: border-box; text-decoration: inherit; border-color: rgb(226, 121, 97); display: inline-block; height: 8px; width: 8px; color: inherit; background-color: rgb(226, 121, 97);"></section></section></section>
													</section>
																		
												</li>
												<li id="style-39" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="尖右角标题" class="style-item   tagtpl-107  tagtpl-233" data-id="39">
												
													<section class="_h5editor" data-tools="小标题" data-id="39"><section style="max-width: 100%; margin: 0.8em 0px 0.5em; overflow: hidden; text-align: left;"><section class="135brush" data-brushtype="text" placeholder="请输入标题" style="height: 36px; display: inline-block; color: rgb(255, 255, 255); font-size: 16px; font-weight: bold; padding: 0px 10px; line-height: 36px; vertical-align: top; box-sizing: border-box !important; background-color: rgb(239, 112, 96);">请输入标题</section><section style="display: inline-block; height: 36px; vertical-align: top; border-left-width: 9px; border-left-style: solid; border-left-color: rgb(239, 112, 96); box-sizing: border-box !important; border-top-width: 18px !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 18px !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></section></section></section>
																		
												</li>
												
												<li id="style-33" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="编号标题" class="style-item   tagtpl-107  tagtpl-229" data-id="33">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="33"><p style="margin: 8px 0px 0px; padding: 0px; font-weight: bold; font-size: 16px; line-height: 28px; max-width: 100%; color: rgb(239, 112, 96); min-height: 32px; border-bottom-width: 2px; border-bottom-style: solid; border-color: rgb(239, 112, 96); text-align: justify;"><span class="autonum" placeholder="1" style="border-radius: 80% 100% 90% 20%; color: #FFFFFF; display: block; float: left; line-height: 20px; margin: 0px 8px 0px 0px; max-width: 100%; padding: 4px 10px; word-wrap: break-word !important; background-color: #EF7060;">1</span><strong class="135brush" data-brushtype="text">第一标题</strong></p></section>
										
					</li>
												<li id="style-2185" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="左侧编号内容格式" class="style-item   tagtpl-109  tagtpl-230" data-id="2185">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="2185"><section style="color: inherit; font-size: 16px; padding: 5px 10px 0px 35px; margin-left: 27px; border-left-width: 2px; border-left-style: dotted; border-left-color: rgb(228, 228, 228);"><section class="autonum" style="width: 32px; height: 32px; margin-left: -53px; margin-top: 23px; color: rgb(255, 255, 255); text-align: center; line-height: 32px; border-radius: 16px; background: rgb(157, 180, 194);">1</section><section class="135brush" style="margin-top: -30px;padding-bottom: 10px; color: inherit;"><p>生活哪里是一层一层上台阶或下台阶，生活分明是踩着一块浮冰去另一块浮冰，却永不知岸在何处。 by 吕彦妮</p></section></section></section>
										
					</li>
												<li id="style-45" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="编号标题" class="style-item   tagtpl-107  tagtpl-229" data-id="45">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="45"><section style="margin: 0.8em 0 0.5em 0;font-size: 16px;line-height: 32px; font-weight: bold;"><section style="display: inline-block; float: left; width: 32px; height: 32px; border-radius: 32px; vertical-align: top; text-align: center; border-color: rgb(239, 112, 96); color: rgb(255, 255, 255); background-color: rgb(239, 112, 96);"><section style="display: table; width: 100%; color: inherit; border-color: rgb(239, 112, 96);"><section class="autonum" placeholder="1" style="display: table-cell; text-indent: 0px; vertical-align: middle; font-style: normal; color: rgb(255, 255, 255); border-color: rgb(239, 112, 96);">1</section></section></section><section class="135brush" data-brushtype="text" style="margin-left: 36px; font-style: normal; color: rgb(239, 112, 96); border-color: rgb(239, 112, 96);">请输入标题</section></section></section>
										
					</li>
												<li id="style-32290" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="牙刷标题" class="style-item   tagtpl-107  tagtpl-237" data-id="32290">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="32290"><section style="clear: both; padding: 0px; border: 0px none; margin:5px 0px;"><section style="border-top-width: 2.5px; border-top-style: solid; border-color: rgb(239, 112, 96); font-size: 1em; font-weight: inherit; text-decoration: inherit; color: rgb(255, 255, 255); box-sizing: border-box;"><section style="border: 0px rgb(239, 112, 96); margin: 2px 0px 0px; overflow: hidden; padding: 0px; color: inherit;"><section style="display: inline-block; font-size: 1em; font-family: inherit; font-weight: inherit; text-align: inherit; text-decoration: inherit; color: inherit; border-color: rgb(239, 112, 96); background-color: transparent;"><section class="135brush" data-bcless="darken" data-brushtype="text" style="display: inline-block; line-height: 1.4em; padding: 5px 10px; height: 32px; vertical-align: top; font-size: 16px; font-family: inherit; font-weight: bold; float: left; color: inherit; border-color: rgb(212, 43, 21); box-sizing: border-box !important; background: rgb(239, 112, 96);">请输入标题</section><section style="display: inline-block; vertical-align: top; font-size: 16px; width: 0px; height: 0px; border-top-width: 32px; border-top-style: solid; border-top-color: rgb(239, 112, 96); border-right-width: 32px; border-right-style: solid; border-right-color: transparent; border-top-right-radius: 4px; border-bottom-left-radius: 2px; color: inherit; box-sizing: border-box !important;"></section></section></section></section></section></section>
										
					</li>
		
								
											</ul>
										</div>
										</div>
                                    </div>
                                </div>
                                <div id="tab-1-2" class="tab-pane">
                                    <div class="panel-body">
                                        <ul class="editor-template-list ui-portlet-list">
                                        	<li id="style-41" data-click="" data-time=""  data-placement="right"  data-container="body" data-toggle="tooltip" title="背景色标题灰色内容面板" class="style-item   tagtpl-109  tagtpl-228  tagtpl-243" data-id="41">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="41"><blockquote class="135brush" data-brushtype="text" style="max-width: 100%; margin: 0px; padding: 5px 15px; color: rgb(224, 209, 202); line-height: 25px; font-weight: bold; text-align: left; border-radius: 5px 5px 0px 0px; border: 0px; background-color: rgb(107, 77, 64);">红客编辑器</blockquote><blockquote class="135brush" style="max-width: 100%; margin: 0px; border-radius: 0px 0px 5px 5px; border: 0px none; background-color: rgb(239, 239, 239); line-height: 25px; padding: 10px 15px;"><p style="color: inherit;"><span style="color:inherit; font-size:14px">可在这输入内容，红客编辑器，做微信图文美化最方便的编辑器。</span></p></blockquote></section>
										
					</li>
					
											<li id="style-30" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="通知公告" class="style-item " data-id="30">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="30"><section style=" padding: 5px; border: 1px solid rgb(204, 204, 204);color: rgb(62, 62, 62); line-height: 24px; text-align: justify; box-shadow: rgb(165, 165, 165) 5px 5px 2px; background-color: rgb(250, 250, 250);margin-top: 20px;"><section style=" text-align: left;margin-left: 20px;;margin-top: -18px;"><section style="display: inline-block;border-radius: 0.1em 1.5em;box-shadow: rgb(165, 165, 165) 4px 4px 2px;color: rgb(255, 255, 255);padding: 4px 20px;text-align: justify;border: 1px solid rgb(89, 155, 171);background-color: rgb(89, 155, 171);"><strong class="135brush" data-brushtype="text">内容标题</strong></section></section><section class="135brush" style="padding: 15px; " data-style="margin-top: 2px; margin-bottom: 0px; max-width: 100%; min-height: 1.5em; line-height: 2em;"><p style="margin-top: 2px; margin-bottom: 0px; max-width: 100%; min-height: 1.5em; line-height: 2em;"><span style="font-size:14px">各位小伙伴们，红客新版编辑器正式上线了，欢迎大家多使用多提供反馈意见。使用<span style="color:inherit"><strong>谷歌与火狐浏览器</strong></span>，可获得与手机端一致的显示效果。ie内核的低版本浏览器可能有不兼容的情况</span></p></section></section></section>
										
					</li>
					
						<li id="style-391" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="斜标题介绍" class="style-item   tagtpl-109  tagtpl-226" data-id="391">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="391"><section style="margin: 1.5em 1em 1em; padding: 0px; border: 0px rgb(157, 180, 194); max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); font-size: 16px; line-height: 25px; display: inline-block; word-wrap: break-word !important;"><section style="max-width: 100%; word-wrap: break-word !important; box-sizing: border-box; line-height: 1.4; margin-left: -0.5em;"><section style="max-width: 100%; box-sizing: border-box; border-color: rgb(0, 0, 0); padding: 3px 8px; border-radius: 4px; color: rgb(255, 255, 255); font-size: 1em; display: inline-block; transform: rotate(-10deg); transform-origin: 0% 100% 0px; word-wrap: break-word !important; background-color: rgb(157, 180, 194);"><span class="135brush" data-brushtype="text" style="color:#FFFFFF">红客编辑器</span></section></section><section class="135brush" data-style="line-height:24px;color:rgb(89, 89, 89); font-size:14px" style="max-width: 100%; box-sizing: border-box; padding: 22px 16px 16px; border: 1px solid rgb(157, 180, 194); color: rgb(0, 0, 0); font-size: 14px; margin-top: -24px;"><p style="line-height:24px;"><span style="color:#595959; font-size:14px">红客编辑器提供非常好用的H5图文编辑器。希望新版编辑器能让大家提高工作效率。</span></p></section></section></section>
										
					</li>
					
					<li id="style-84261" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="折角正文样式" class="style-item   tagtpl-109  tagtpl-226" data-id="84261">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="84261"><section class="layout" style="border: 0px; margin: 5px auto; box-sizing: border-box; padding: 0px;-webkit-border-image:url(http://image2.135editor.com/img/stats.gif);"><section style="height: 25px; box-sizing: border-box; color: inherit;"><section style="height: 100%; width: 50px; float: left; border-top-width: 2px; border-top-style: solid; border-color: rgb(172, 29, 16); border-left-width: 2px; border-left-style: solid; box-sizing: border-box; color: inherit;"></section><section style="display: inline-block; color: rgb(172, 29, 16); clear: both; box-sizing: border-box; border-color: rgb(172, 29, 16);"></section></section><section class="135brush" data-style="color: rgb(51, 51, 51); font-size: 1em; line-height: 1.75em; word-break: break-all; word-wrap: break-word; text-align: justify;" style="margin: -0.8em 0.3em; padding: 0.8em; box-sizing: border-box; color: inherit;"><p style="color: rgb(51, 51, 51); font-size: 1em; line-height: 1.75em; word-break: break-all; word-wrap: break-word; text-align: justify;">为了给遗憾找个借口，我们从一开始就把一些人放在了等待怀念的位置。——郑执</p></section><section style="height: 25px; box-sizing: border-box; color: inherit;"><section style="height: 100%; width: 50px; float: right; border-bottom-width: 2px; border-bottom-style: solid; border-color: rgb(172, 29, 16); border-right-width: 2px; border-right-style: solid; box-sizing: border-box; color: inherit;"></section></section></section></section>
										
					</li>
					
					<li id="style-126" data-click="" data-time=""  data-placement="right"  data-container="body" data-toggle="tooltip" title="四角宽边框的样式" class="style-item   tagtpl-109  tagtpl-226" data-id="126">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="126"><section style="border: 0px; margin: 5px auto; box-sizing: border-box; padding: 0px;width: 100%;" class="layout"><section style="height: 1em; box-sizing: border-box;"><section style="height: 100%; width: 1.5em; float: left; border-top-width: 0.4em; border-top-style: solid; border-color: rgb(239, 112, 96); border-left-width: 0.4em; border-left-style: solid; box-sizing: border-box;"></section><section style="height: 100%; width: 1.5em; float: right; border-top-width: 0.4em; border-top-style: solid; border-color: rgb(239, 112, 96); border-right-width: 0.4em; border-right-style: solid; box-sizing: border-box;"></section><section style="display: inline-block; color: rgb(239, 112, 96); clear: both; box-sizing: border-box;"></section></section><section style="margin: -0.8em 0.1em -0.8em 0.2em; padding: 0.8em; border: 1px solid rgb(239, 112, 96); border-radius: 0.3em; box-sizing: border-box;"><section class="135brush" placeholder="四角宽边框的样式" style="color: rgb(51, 51, 51); font-size: 1em; line-height: 1.4; word-break: break-all; word-wrap: break-word; text-align: left;"><p>对一个人来说，一辈子注定会不时去寻找一些他们自身周围所不能提供的东西，要么他们以为自身周围无法提供，所以放弃了寻找，他们甚至在还没有真正开始寻找前，就放弃了。</p>
									<br><p>——J·D·塞林格《麦田守望者》</p>
									<br><p></p></section></section><section style="height: 1em; box-sizing: border-box;"><section style="height: 100%; width: 1.5em; float: left; border-bottom-width: 0.4em; border-bottom-style: solid; border-color: rgb(239, 112, 96); border-left-width: 0.4em; border-left-style: solid; box-sizing: border-box;"></section><section style="height: 100%; width: 1.5em; float: right; border-bottom-width: 0.4em; border-bottom-style: solid; border-color: rgb(239, 112, 96); border-right-width: 0.4em; border-right-style: solid; box-sizing: border-box;"></section></section></section></section>
										
					</li>
					
					
												<li id="style-23" data-click="" data-time="" data-placement="right" data-container="body" data-toggle="tooltip" title="引号形式引用" class="style-item   tagtpl-109  tagtpl-228  tagtpl-1089" data-id="23">
				
									<section class="_h5editor" data-tools="红客编辑器" data-id="23"><section class="135brush" style="font-size: 14px; line-height: 22.39px;margin: 10px 0px; padding:15px 20px 15px 45px; outline: 0px; border: 0px currentcolor; color: rgb(62, 62, 62); vertical-align: baseline; background-image: url(http://image2.135editor.com/cache/remote/aHR0cHM6Ly9tbWJpei5xbG9nby5jbi9tbWJpei95cVZBcW9adkRpYkhYSTVla29LUjFpY0E5bjJ0SU4yTWFmUno0Wk1sYlo5VkpJUHlIVnZBMzYyMlY5TTRvcHAwS2liV0c2Rjc4SGRjSE9BQlFiaWMxa3JmUHcvMD93eF9mbXQ9anBlZw==); background-color: rgb(241, 241, 241); background-position: 1% 5px; background-repeat: no-repeat no-repeat;"><p>人生就是一列开往坟墓的列车，路途上会有很多站，很难有人可以自始至终陪着走完。当陪你的人要下车时，即使不舍也该心存感激，然后挥手道别。——宫崎骏</p></section></section>
										
					</li>
                                        </ul>
                                    </div>
                                </div>
                                <div id="tab-1-3" class="tab-pane">
                                    <div class="panel-body" style="max-height: 920px;max-width: 480px;overflow-y: scroll;">
                                        <ul class="editor-template-list ui-portlet-list">

											<c:forEach items="${picDics }" var="picDic">
											<li id="style-${picDic.pId }" data-id="${picDic.pId }" data-placement="right"  data-container="body" data-toggle="tooltip" title="tougao" class="style-item   tagtpl-109  tagtpl-228  tagtpl-243"><p style="text-align:center;"><img src="${picDic.pic }"/>
												</p>
											</li>	
											</c:forEach>
											
                                        </ul>
                                    </div>
                                </div>
                                
                                <div id="tab-1-4" class="tab-pane">
                                	<div class="panel-body" style="max-height: 920px;overflow-y: scroll;padding:0;">
                                		<div id="imageGallery">
                                			<div class="searchArea">
                                				<label>图片类型：</label>
                                				<select id="imageType">
                                					<option value="0">全部分类</option>
	                                				<c:forEach items="${imageTypes }" var="type">
	                                				<option value="${type.id}">${type.name}</option>
													</c:forEach>
                                				</select>
                                				<label class="f-ml8">图片标题：</label>
                                				<input type="text" id="image_title" />
                                				<a class="btn btn-primary" id="imageGallery_search">搜索</a>
                                			</div>
                                			<ul class="mui-table-view m-list f-cb" id="listView">
									    		<li v-if="rows.length == 0" class="noorder">
									    			<a>没有找到图片</a>
												</li>
									    		<li class="grid-sizer"></li>
									    		<li class="gutter-sizer"></li>
											    <li v-for="entity in rows" class="grid-item">
											    	<div class="shadowBox">
											    		<a class="btn btn-w-m btn-warning btn-sm btn-insert" :data-url="entity.url">插入图片</a>
											    	</div>
									            	<div class="imageBox">
									            		<img v-on:load="imageLoad" :src="entity.url" alt="" />
									            	</div>
									                <p class="title">{{entity.title}}</p>
									                <div class="bottom">
									                	<span class="fileSize"></span>
									                	<div class="createTime">{{entity.createTime}}<div>
									                </div>
											    </li>
											    
											    <li class="mui-pull-bottom" v-if="rows.length >= 20">
													<div class="mui-pull-bottom-tips" style="clear:both;">
														<div class="mui-pull-bottom-wrapper">
															<span class="mui-pull-loading">加载中</span>
															<span class="u-loading"><img src="${ctxPath}/static/base/images/loading.gif"/></span>
														</div>
													</div>
											    </li>
											</ul>
                                		</div>		
                                	</div>
                                </div>

                            </div>

                        </div>

                    </div>
                </div>
                <div class="col-lg-5">
                
				    		<script id="clobs_text" type="text/plain" style="width:98%;">${bean.text}</script>
						  	<script type="text/javascript">
							  	$(document).ready(function(){
							  		var initialFrameHeight = document.body.scrollHeight - 640;
									if(initialFrameHeight < 0) {
										initialFrameHeight = 400;
									}
									
									<c:if test="${!empty field.customs['height']}"> initialFrameHeight = ${field.customs['height']};</c:if>
									//initialFrameHeight = initialFrameHeight> 960 ? 960 : initialFrameHeight;
									initialFrameHeight = 740;
								  		var ue = UE.getEditor('clobs_text',{
								  			initialFrameHeight : initialFrameHeight,
								  			textarea :"clobs_text",	
								  			configUrl:"${ctxPath}/static/base/js/h5editor/config.js",
									  		imageUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_image",
											wordImageUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_image",
											fileUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_file",
											videoUrl:"${ctxPath}/${SYS_ADMIN_PATH}/sys/upload_file",
									  	});
							  		
									window.current_editor= ue;
									
							  	});
								
								
							 </script>
						</div>
					</div>
						
						</c:when>
						<c:otherwise>
						 <tags:feild_custom bean="${bean}" customs="${customs }" field="${field}"/>
						</c:otherwise>
						</c:choose>
						</div>
					</c:forEach>
					</div>
                </div>

	            <div class="form-group">
	                <div class="col-sm-12 text-right">
		                 <%-- <c:if test="${oprt=='create'}">
				    		<input type="hidden" id="draft" name="draft" value="false"/>
				      		<button onclick="draftForm();" title='保存草稿' class="btn btn-primary" type="button">保存草稿</button>
				    	</c:if>  --%>
				    	<c:choose>  
						   <c:when test="${oprt=='create'}">  
						   		<input type="hidden" id="draft" name="draft" value="false"/>
				      			<button onclick="draftForm();" title='保存草稿' class="btn btn-primary" type="button">保存草稿</button>
								<button onclick="submitFormCreat();" title='保存并返回' class="btn btn-primary" type="button">保存并返回</button>
						   </c:when>  
						   <c:otherwise> 
								<button onclick="submitForm();" title='保存并返回' class="btn btn-primary" type="button">保存并返回</button>
						   </c:otherwise>  
						</c:choose>  
	                   
		                <c:if test="${bean.status=='B'}">
				    		<input type="hidden" id="pass" name="pass" value="false"/>
				      		<button onclick="vForm();" title='审核并返回' class="btn btn-primary" type="button">审核并返回</button>
				    	</c:if>
		                <c:if test="${bean.status=='X'}">
				    		<input type="hidden" id="recall" name="recall" value="false"/>
				      		<button onclick="recallForm();" title='召回并返回' class="btn btn-primary" type="button">召回并返回</button>
				    	</c:if>
	                </div>
	            </div>
	        </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/vue/vue.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/masonary/imagesloaded.pkgd.min.js"></script>
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/masonary/masonry.pkgd.min.js"></script>

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
	    		$('#h5_styles').show();	    		
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
    	$('#' + id).load('cms/selector/imgUploadTag?id='+name+'&original=true&width='+data_with+'&height='+data_height+'&value='+data_value+'&scale='+data_scale+'&watermark='+data_watermark+'&stretch='+data_stretch);
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
    	var keyWord = encodeURIComponent(encodeURIComponent('${keyWord}'));
    	loadFrameContent('cms/info/infoList?queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&queryStatus=${queryStatus}&start=${start}&columnFiled=${columnFiled}&keyWord='+keyWord+'&rnd='+ Math.random());
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
    
    function submitFormCreat(){
    	
		layer.confirm('保存并上呈下一节点？', {
			btn : [ '保存并上呈', '取消' ]
		,btnAlign: 'l'
		//按钮
		}, function(index, layero) {
			
			submitForm();
			layer.close(index);
		})
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
            	loadFrameContent('cms/info/infoList?queryNodeId=${queryNodeId}&showDescendants=${showDescendants}&start=${start}&columnFiled=${columnFiled}&keyWord='+encodeURIComponent('${keyWord}')+'&queryStatus=' + queryStatus);
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
    	if(confirm(' 确认上呈？ ')){
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
    	}
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
	  		value = encodeURIComponent((encodeURIComponent(value)));
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
    
    //图片库
    $(function(){
    	var loading = false, 
		page = 1,
		rows = 20,
		$grid;
		var vm = new Vue({
				el: '#listView',
			data: {rows:[]},
			mounted: function(){
				this.$nextTick(function () {
					this.getmore(function(len){
						$("#listView").show();
						if(len < rows){
							$("#loadMore").hide();
						}
					});
					this.bindScroll(); 
				})
			},
			methods:{
				getmore: function(callback){
					var hideloading = function(){
						loading = false;
						$(".mui-pull-bottom-tips").hide();
						$("#loadMore").show();
					}
					var typeId = $("#imageType").val();
					var title = $("#image_title").val();
					
					var searchParams = 'page='+page+'&rows='+rows+'&typeId='+typeId+'&columnFiled=LIKE_title&keyWord='+encodeURIComponent(encodeURIComponent(title));
					$.getJSON('cms/cmsImage/cmsImageListJson?'+searchParams, function(data){
						hideloading();
						if(data.rows.length < rows) {
							loading = true;
						} 
						vm.rows = vm.rows.concat(data.rows);
						if(callback){callback(data.rows.length);}
						vm.masonary();
						page++;
					})
				},
				reload: function(){
					vm.rows = [];
					$('#listView').masonry().masonry('destroy');
					loading = false;
					page = 1;
					vm.getmore(function(len){
						$("#listView").show();
						if(len < rows){
							$("#loadMore").hide();
						}
					});
				},
				masonary:function(){
					$('#listView').masonry().masonry('destroy');
					initMasonry();
				},
				imageLoad: function(event){
					var img = event.target,
						imageSize = img.width + " x " + img.height;
					$(img).parent(".imageBox").parent(".grid-item").find(".fileSize").text(imageSize);
				},
				//监听滚动，判断是否到底
				bindScroll: function(){
					var showloading = function(){
						loading = true;
						$(".mui-pull-bottom-tips").show();
						$("#loadMore").hide();
					}
					$("#tab-1-4 .panel-body").scroll(function(){
						var scrollTop = $("#tab-1-4 .panel-body")[0].scrollTop,
							scrollHeight = $("#tab-1-4 .panel-body")[0].scrollHeight,
							height = $("#tab-1-4 .panel-body").height();
						
						if(height + scrollTop == scrollHeight && !loading){
							console.log("loading");
							showloading();
							setTimeout(function(){
								vm.getmore();
							}, 500);
						}
						
					}); 
				}
			}
		});	
		
		function initMasonry(){
			setTimeout(function(){
				window.$grid = $('#listView').masonry({
	   				columnWidth: '.grid-sizer',
	   				itemSelector: '.grid-item',
	   				gutter: '.gutter-sizer',
	   				percentPosition: true
	    		});
					
				$('#listView').imagesLoaded().progress( function() {
					$('#listView').masonry('layout');
				});
			},1);
		}
		
		$("#imageGallery_search").on("click", function(){
			vm.reload();
		});
		
		$("#tab_1_4").on("click", function(){
			initMasonry();
		}); 
		
		$("#imageGallery").on("click", ".btn-insert", function(){
			var url = $(this).attr("data-url");
			current_editor.execCommand( 'insertimage', {
			     src: url,
			     height:'auto',
			     style:'max-width:100%'
			});
		});
		
    });
    	
    
    
   
    
    
</script>