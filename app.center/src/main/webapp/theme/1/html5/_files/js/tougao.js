var imgList=[],fileList=[];
var path = window.location.href;
$().ready(function(){
	$("#login_form").validate({
    });
    
    $("#login_btn").click(function(){
    	if($("#login_form").valid()){
    		$.ajax({
                cache: true,
                type: "post",
                url: ctx+"/ajaxlogin",
                data:$('#login_form').serialize(),
                async: false,
                success: function(data) {
                	if(data == 'org.apache.shiro.authc.IncorrectCredentialsException'){
                		alert("密码错误！");
                	}else if(data == 'org.apache.shiro.authc.UnknownAccountException'){
                		alert("用户名或密码错误！");
                	}else if(data){
                		alert("此用户不在或未激活！");
                	}else{
                		//alert(window.location.href);
                		//var path = window.location.href;
                   		window.location.href=path;
                	}
                }
            });
    		return false;
		}
    });
	
	if(typeof $.fn.dropzone == 'function'){
		//图片上传插件
		$(".i-addPic").dropzone({
			url: ctx+"/tg-upload.htx",
		    params : {type:1},
		    previewsContainer: "#imagePreview",
		    acceptedFiles: ".jpg, .jpeg,.gif, .png, .bmp",
		    addRemoveLinks: true,
		    dictRemoveLinks: "删除",
		    dictCancelUpload: "删除",
		    dictRemoveFile:"删除",
		    maxFiles: 12,
		    maxFilesize: 5,   //单张图片最大5M
		    dictMaxFilesExceeded: "图片大小超出，最大5M",
		    init: function() {
		        this.on("success", function(file) {
		        	var jsonResult = file.xhr.responseText;
		        	var data = eval('('+jsonResult+')');
		        	if(data.msg_code == '0'){
		        		imgList.push(data.result);
		        	}else{
		        		imgList.push('');
		        		alert(data.result);
		        	}
		        });
		        this.on("error", function(file, errorMessage) {
		        	if(errorMessage.indexOf("File is too big") != -1){
		        		alert("图片大小超出，最大"+this.options.maxFilesize+"M");
		        	}
		        });
		        this.on("removedfile", function(file) {
		        	imgList.splice(file.fileIndex, 1);
		        });
		        this.on("maxfilesexceeded", function(file) {
		//        	alert("一次最多只能上传"+this.options.maxFiles+"张图片!");
		//        	return false;
		        });
		    }
		});
			
		//附件上传
		$(".i-addEnclosure").dropzone({
			url: ctx+"/tg-upload.htx",
		    params : {type:2},
		    previewsContainer: "#filePreview",
		    addRemoveLinks: true,
		    dictRemoveLinks: "删除",
		    dictCancelUpload: "删除",
		    dictRemoveFile:"删除",
		    maxFiles: 12,
		    maxFilesize: 10,   
		    dictMaxFilesExceeded: "文件大小超出，最大10M",
		    init: function() {
		        this.on("success", function(file) {
		        	var jsonResult = file.xhr.responseText;
		        	var data = eval('('+jsonResult+')');
		        	if(data.msg_code == '0'){
		        		fileList.push(data.result);
		        	}else{
		        		fileList.push('');
		        		alert(data.result);
		        	}
		            
		        });
		        this.on("error", function(file, errorMessage) {
		        	if(errorMessage.indexOf("File is too big") != -1){
		        		alert("文件大小超出，最大"+this.options.maxFilesize+"M");
		        	}
		        });
		        this.on("removedfile", function(file) {
		        	fileList.splice(file.fileIndex, 1);
		        });
		        this.on("maxfilesexceeded", function(file) {
		//        	alert("最多只能上传"+this.options.maxFiles+"个文件!");
		//        	return false;
		 			// this.removeFile(file);
		
		        });
		    }
		});
		
		document.getElementById("reset").addEventListener("click", function(){
			document.getElementById("validForm").reset();
		});
		
		$.validator.setDefaults({
		    submitHandler: function() {
		      return true;
		    }
		});
		    
	    $("#validForm").validate({
	    });
	    
	    $("#submit").click(function(){
	    	if($("#validForm").valid()){
	    		if(fileList.length==0 && imgList.length==0){
					alert("请添加附件或图片！");
					return false;
				}
	    		$("#filedetails").val(fileList.join(","));
				$("#imagedetails").val(imgList.join(","));
				$("#validForm").submit();
			}
	    })

	}
	if($("#form_tougao").length>0){
		$("#form_tougao").validate({
	    });
	    
	    $(".submit").click(function(){
	    	if($("#form_tougao").valid()){
				$("#form_tougao").submit();
			}
	    });
	    document.getElementById("reset").addEventListener("click", function(){
			document.getElementById("form_tougao").reset();
		});
	}
});

/*$(function() {
	$("#validForm").validate({
		submitHandler: function(form) {
			
			$("#filedetails").val(fileList.join(","));
			$("#imagedetails").val(imgList.join(","));
		   	form.submit();
		}
	});
});*/


//查看
var sameId = $('*[id=bindPhoneNumber]')
$(sameId).click(function(){
	$(".sms-bind-modal").show();
});
$(".sms-bind-modal .close").click(function(){
	$(".sms-bind-modal").hide();
})



//登录
$(".login-btn").click(function(){
	$(".login-container").show();
});

$(".logout-btn").click(function(){
	window.location.href=ctx+"/logout?fallbackUrl="+path;
});

$(".login-container .icon-close").click(function(){
	$(".login-container").hide();
});




function gotoUrl(url){
	$.ajax({
		url : ctx+'/tg-islogin.htx',
		success: function(data) {
			if(data=='1'){
				window.location.href=url;
			}else{
				path = url;
				$(".login-container").show();
			}
		}
	});
}




/*$(".i-addEnclosure").click(function(){
	$("#file").trigger("click");
	$("#file").change(function(){
		var path = $("#file").val();
		var arr = path.split('\\');
		var filename = arr[arr.length -1];
		$(".right .filename").html(filename);
	});
});*/
