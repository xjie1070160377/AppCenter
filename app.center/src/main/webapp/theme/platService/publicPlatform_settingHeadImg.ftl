[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>红客移动客户端</title>
<script type="text/javascript" src="${ctxPath}/static/base/js/jquery-2.1.1.js">
</script><script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/jquery.form.min.js"></script>

<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/publicPlatform_settingHeadImg.css" />
<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/js/plugins/imgareaselect/css/imgareaselect-animated.css" />
<script type="text/javascript" src="${ctxPath}/static/base/js/plugins/imgareaselect/jquery.imgareaselect.pack.js"></script>


<script type="text/javascript">
var container=350,scale=1;
var previewContainer=150,previewScale=1;
var width,height,targetWidth,targetHeight;
var isa,src;
var targetWidth=140;
var targetHeight=140;
function setPreview(targetWidth,targetHeight) {
	if(targetWidth>previewContainer || targetHeight>previewContainer) {
		if(targetWidth>=targetHeight) {
			previewScale = previewContainer/targetWidth;
		} else {
			previewScale = previewContainer/targetHeight;
		}
	} else {
		previewScale = 1;
	}
	$("#img1").width(targetWidth*previewScale).height(targetHeight*previewScale);	
	$("#img2").width(targetWidth*previewScale).height(targetHeight*previewScale);	
}
setPreview(targetWidth,targetHeight);
function preview(img, selection) {
	  if (!selection.width || !selection.height){
	    return;
	  }
	  var s = parseFloat($("#scale").val());
	  setPreview(targetWidth,targetHeight);	  
	  show(selection.width,selection.height,selection.x1,selection.x2,selection.y1,selection.y2);
	}
	function show(w,h,x1,x2,y1,y2) {
	  var scaleX = targetWidth*previewScale / w;
	  var scaleY = targetHeight*previewScale / h;
	  $("#img1").css({
	    width: Math.round(scaleX * width * scale),
	    height: Math.round(scaleY * height * scale),
	    marginLeft: -Math.round(scaleX * x1),
	    marginTop: -Math.round(scaleY * y1)
	  });
	  $("#img2").css({
	    width: Math.round(scaleX * width * scale),
	    height: Math.round(scaleY * height * scale),
	    marginLeft: -Math.round(scaleX * x1),
	    marginTop: -Math.round(scaleY * y1)
	  });
	  var s;
	  
		  s = scale*targetWidth/w;
	  
	    s = scale*targetHeight/h;
	  
	  $("#scale").val(s);
	  var left = Math.floor(x1/scale);
	  var top = Math.floor(y1/scale);
	  var srcW = Math.floor(w/scale);
	  var srcH = Math.floor(h/scale);
	  if(left < 0) {
		  left = 0;
	  }
	  if(top < 0) {
		  top = 0;
	  }
	  if(srcW > width) {
		  srcW = width;
	  }
	  if(srcH > height) {
		  srcH = height;
	  }
	  $("#left").val(left);
	  $("#top").val(top);
	  $("#width").val(srcW);
	  $("#height").val(srcH);
	  $("#targetWidth").val(targetWidth);
	  $("#targetHeight").val(targetHeight);
	}
function doSetImgAreaSelect(reset) {
	var options = {};
    options.minWidth=targetWidth*scale;
    options.minHeight=targetHeight*scale;
    options.aspectRatio=targetWidth+":"+targetHeight;
  if(targetWidth>0 && targetHeight>0 && (!isa||reset)) {
    options.x1=0;
    options.y1=0;
    if(scale<previewScale) {
	    options.x2=targetWidth*previewScale;
	    options.y2=targetHeight*previewScale;
    } else {
    	options.x2=targetWidth*scale;
      options.y2=targetHeight*scale;
    }    	
  } else {
    options.x1=0;
    options.y1=0;
    options.x2=150;
    options.y2=150;      
  }
  options.onSelectChange=preview;
  options.handles=true;
  options.instance=true;
  
  if(!isa) {
    isa = $("#img0").imgAreaSelect(options);
  } else {
    isa.setOptions(options);
    isa.update();
  }
  //$("#photo").imgAreaSelect({minWidth:targetWidth*scale,minHeight:targetHeight*scale,aspectRatio:targetWidth+":"+targetHeight,x1:0,y1:0,x2:targetWidth*Scale,y2:targetHeight*previewScale,
  //    onSelectChange:preview,handles:true});
  if($("#img1").attr("src")!=src) {     
    $("#img1").attr("src",src).ready(function() {
      show(options.x2,options.y2,0,options.x2,0,options.y2);
      $("#img1").parent().css("visibility","visible");
    });
  } else {
    preview(null,isa.getSelection());
  }
}
function setImgInitParams(src1){
	src = src1;
	var imgHtml="<img id='srcImg2' src='"+src+"' style='position:absolute;top:-99999px;left:-99999px'/>";
	$(imgHtml).load(function(){
		width=this.width;
		height=this.height;
		setImgAreaSelect();
	});
}
function setImgAreaSelect(reset) {
	  if((width!=0&&height!=0)&&(width>container||height>container)) {
	    scale = container/width < container/height ? container/width : container/height;
	    $("#largeHeadImg").width(width*scale).height(height*scale);
	  } else {
	    $("#largeHeadImg").width(width).height(height);
	  }
	doSetImgAreaSelect(reset);
}
//jquery ajax图片上传
function saveImage(o) {
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
            	 $("#img0").show();
                 $("#srcImg").attr('src', result.url);
                 $("#img0").attr('src', result.url);
                 $("#img1").attr('src', result.url);
                 $("#img2").attr('src', result.url);
                 $("#src").val(result.url);
                 $("#image").val(result.url);
                 $("#name").val(result.title);
                 
                 $("#imgfileP").html('<input id="imageFile0" name="imageFile" type="file" accept="image/*" style="display:none;" onchange="saveImage(this)" />');
                 setImgInitParams(result.url);
             }
         });
}

</script>
</head>
<body>


	<!-- Main Start -->
	<div class="main">
		<div class="info_box clearfix">
			<div class="form_wrp right">
				<img id='srcImg' src='javascript:false' style='position:absolute;top:-99999px;left:-99999px'/>
				<form action="/mobile/pp/img_crop.htx" method="post" class="form" id="cropImgForm">
					<input type="hidden" name="serviceId" value="${appService.serviceId}"/>
					<input type="hidden" id="scale" name="scale"/>
			        <input type="hidden" id="top" name="top"/>
			        <input type="hidden" id="left" name="left"/>
			        <input type="hidden" id="width" name="width"/>
			        <input type="hidden" id="height" name="height"/>
			        <input type="hidden" id="src" name="src" value="${src}"/>
			        <input type="hidden" id="name" name="fileName" value="${name}"/>
			        <input type="hidden" id="targetWidth" name="targetWidth"/>
			        <input type="hidden" id="targetHeight" name="targetHeight"/>
	                <input type="hidden" id="image" name="image" /> 
        		</form>
						<!-- 
						<div class="frm_control_group">
						
							<input type="hidden" id="image" name="image" /> 
							<input type='button' value='选择图片'  OnClick='javascript:$("#imageFile0").click();'/>
							<input id="imageFile0" name="imageFile" type="file" style="display:none;" onchange="saveImage(this)" />
							<img id="img0" src="${ctxPath}/theme/platService/_files/images/getheadimg.jpg" width="100px" height="100px" style="display:none;"/>
							<div id="over" class="over" style="display:none;"></div>
<div id="layout" class="layout" style="display:none;"><img src="${ctxPath}/theme/platService/_files/images/getheadimg.jpg" alt="" /></div>
						</div>
						
						 <p style="text-align: left; margin-right:6px; margin-bottom:10px;">
						 	<input type="button" class="btn btn-primary" value="提交" onclick="formSubmit()" />
		                    <input id="commit" style="display:none;" type="submit" value="提交" />
		                    
		                   
		                </p> -->
		                <p id="imgfileP">
		                <input id="imageFile0" name="imageFile" type="file" accept="image/*" style="display:none;" onchange="saveImage(this)" />
		                </p>
		                <div class="dialog">
		                <div class="dialog_bd">
		                <div class="setting_dialog_content avatar_setting modify" id="step1Desc">
						<div class="modify_content">
            <div id="cropImgArea" class="avatar_crop_area">                
                <div class="upload_box">                                       
                    <div class="upload_area webuploader-container">
                    	<span class="btn btn_primary btn_input js_btn_p" style="display: inline-block;"><button type="button" class="js_btn" data-index="2" onclick='javascript:$("#imageFile0").click();'>选择图片</button></span>
                        
                    </div>                        
               </div>
                <div class="crop_wrp">
                   <div id="largeHeadImg" class="crop_placeholder edit">
                   	<img src="${ctxPath}/theme/platService/_files/images/getheadimg.jpg" style="display: none; visibility: hidden; width: 1px; height: 1px;">
                   	<img id="img0" src="javascript:false" style="display: none; width:100%;height:100%;">
                   	</div>               
                    <button id="saveAvatar" class="saveAvatar" style="display:none" disable="false">保存</button>
                </div>
            </div>
            <div class="avatar_preview_area">
                <p class="preview_tips">头像预览</p>
                <div class="inner_avatar_preview">
                	<div id="squareHeadImg" class="preview_item square">
                        <span class="marked_text">方形头像</span>
                    <img id="img1" class="square_img" id="uploadFileSrc" src="${ctxPath}/theme/platService/_files/images/spacer218877.gif" style="width: 226px; height: 169px; margin-left: 0px; margin-top: 0px;"></div>
                	<div id="circleHeadImg" class="preview_item circle">
                        <span class="marked_text">圆形头像</span>
                    <img class="round_white_img" src="${ctxPath}/theme/platService/_files/images/cut-round-white22f1a8.png"><img id="img2" class="circle_img" src="${ctxPath}/theme/platService/_files/images/spacer218877.gif" style="width: 226px; height: 169px; margin-left: 0px; margin-top: 0px;"></div>
                </div>
            </div>
        </div>		
        </div>
		</div>
        <div class="dialog_ft">
			
	        
            <span class="btn btn_primary btn_input js_btn_p" style="display: inline-block;"><button type="button" class="js_btn" data-index="2" onclick="$('#cropImgForm').submit();setTimeout(function(){window.close()},100);">确定</button></span>
	        
		</div>
		</div>
				
			</div>
		</div>
	
		
			
		
	</div>
	<!-- Main End -->
	
	
    
    
</body>
</html>
[/#escape]