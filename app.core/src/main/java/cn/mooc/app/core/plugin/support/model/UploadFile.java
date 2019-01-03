package cn.mooc.app.core.plugin.support.model;

import org.springframework.web.multipart.MultipartFile;

import cn.mooc.app.core.enums.GenNameType;
import cn.mooc.app.core.model.ImageParam;

public class UploadFile {
	
	private GenNameType genNameType;

	private MultipartFile multipartFile;
	
	private ImageParam imageParam;
	
	/**
	 * 指定文件名，不需要指定文件后缀
	 */
	private String destFileName;
	
	public UploadFile(MultipartFile multipartFile, GenNameType genNameType, ImageParam imageParam){
		this.multipartFile = multipartFile;
		this.genNameType = genNameType;
		this.imageParam = imageParam;
	}
	
	public UploadFile(MultipartFile multipartFile, GenNameType genNameType, ImageParam imageParam, String destFileName){
		this.multipartFile = multipartFile;
		this.genNameType = genNameType;
		this.imageParam = imageParam;
		this.destFileName = destFileName;
	}

	public GenNameType getGenNameType() {
		return genNameType;
	}

	public void setGenNameType(GenNameType genNameType) {
		this.genNameType = genNameType;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public ImageParam getImageParam() {
		return imageParam;
	}

	public void setImageParam(ImageParam imageParam) {
		this.imageParam = imageParam;
	}

	public String getDestFileName() {
		return destFileName;
	}

	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}




	
}
