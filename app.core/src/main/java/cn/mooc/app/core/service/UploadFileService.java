package cn.mooc.app.core.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import cn.mooc.app.core.model.ImageInfo;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.UploadResult;

/**
 * 文件上传接口
 * 
 * @author Taven
 *
 */
public interface UploadFileService {

	UploadResult uploadFile(String category, MultipartFile multipartFile);
	
	long getFileSize(String targetFileUrl);
	
	UploadResult uploadFile(MultipartFile multipartFile);
	
	UploadResult uploadFile(MultipartFile multipartFile,	ImageParam imageParam);
	
	UploadResult delFile(String targetFileUrl);
	
	UploadResult moveFile(String srcFileUrl, String destFileUrl);

	/**
	 * 上传文件后生成自己指定的文件名，不用随机文件名
	 * 
	 * @param multipartFile
	 * @param imageParam
	 * @param destFileName 指定保存后的文件名，需上传端控制好文件名，保证唯一性，否则会覆盖同名文件
	 * @return
	 */
	UploadResult uploadFile(MultipartFile multipartFile, ImageParam imageParam, String destFileName);
	
	/**
	 * 裁剪图片
	 * 
	 * @param srcFileUrl
	 * @param topStart
	 * @param leftStart
	 * @param cutWidth
	 * @param cutHeight
	 * @param imageParam 如果为空，则只将srcFileUrl文件进行裁剪
	 * @return
	 */
	UploadResult cropImage(String srcFileUrl, int topStart, int leftStart, int cutWidth, int cutHeight,	ImageParam imageParam);
	
	
	public ImageInfo getImageFile(String targetFileUrl);
	
	public File getFile(String targetFileUrl) throws Exception;
	
/*	UploadResult uploadFile(InputStream inputStream);
	
	UploadResult uploadFile(FileItem fileItems);*/
	
	
}
