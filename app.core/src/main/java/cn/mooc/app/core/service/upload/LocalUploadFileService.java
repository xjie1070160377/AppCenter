/*package cn.mooc.app.core.service.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.service.UploadFileService;
import cn.mooc.app.core.utils.DateTimeUtil;

*//**
 * 暂实现的本地上传接口，等插件机制完善后，由插件接管
 * @author Taven
 *
 *//*
//@Service
public class LocalUploadFileService implements UploadFileService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UploadResult uploadFile(String category, MultipartFile multipartFile) {
		
		String fileName = multipartFile.getOriginalFilename();
		String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
				
		String contextPath = this.getSaveFilePath(category, multipartFile);		

		String uploadsPath = this.getUploadsPath(contextPath);
		
		String radomFileName = this.getRandomFileName(contextPath, fileExt);
		
		String webPath = this.getImageUrl(contextPath, radomFileName, fileExt);			
		
		UploadResult uploadResult = new UploadResult();
		
		try {
			multipartFile.transferTo(new File(uploadsPath.concat(webPath)));
			uploadResult.setStatus(true);
			uploadResult.setFileUrl(webPath);
			uploadResult.setFileName(fileName);
			uploadResult.setFileSize(multipartFile.getSize());
		} catch (Exception e) {
			logger.error("uploadFile",e);
			uploadResult.setStatus(false);
			uploadResult.setMsg("文件存储失败");
			return uploadResult;
		} 
		
		return uploadResult;
	}

	
	@Override
	public UploadResult uploadFile(MultipartFile multipartFile,	ImageParam imageParam) {
		
		if(!isImageFile(multipartFile)){
			return new UploadResult(false, "不是图片文件类型");
		}
		
		String fileName = multipartFile.getOriginalFilename();
		String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
		long fileSize = multipartFile.getSize();
				
		String contextPath = this.getSaveFilePath("", multipartFile);		

		String uploadsPath = this.getUploadsPath(contextPath);
		
		String radomFileName = this.getRandomFileName(contextPath, fileExt);
		
		String webPath = this.getImageUrl(contextPath, radomFileName, fileExt);		
		
		UploadResult uploadResult = new UploadResult();
		
		try {
			BufferedImage originalImage = ImageIO.read(multipartFile.getInputStream());
			
			Builder<?> builder = Thumbnails.of(originalImage);
			 
			if(imageParam.getWidth()>0 && imageParam.getHeight()>0){
				builder.size(imageParam.getWidth(), imageParam.getHeight());
			}else	if(imageParam.isScale()){
				//设置了宽度，就不能设置Scale，否则有冲突
				//builder.scale(0.3, 0.5);
				builder.scale(0.5);
			}else{
				builder.scale(1);
			}
				
			if(imageParam.isWatermark()){
				BufferedImage watermarkImage = ImageIO.read(new File(uploadsPath + "watermark.png"));
				builder.watermark(Positions.BOTTOM_RIGHT, watermarkImage, 0.5f);
			}
			
			builder.toFile(uploadsPath.concat(webPath));
						
			if(imageParam.isThumbnail()){
				//生成缩略图
				String smallImage = this.getSmallImageUrl(contextPath, radomFileName, fileExt);
				Thumbnails.of(originalImage).scale(0.5).toFile(uploadsPath.concat(smallImage));
			}
						
			if(imageParam.isOriginal()){
				//保留原图
				String orgImage = this.getOrgImageUrl(contextPath, radomFileName, fileExt);
				ImageIO.write(originalImage, fileExt, new File(uploadsPath.concat(orgImage)));
				
			}
			
			uploadResult.setStatus(true);
			uploadResult.setFileUrl(webPath);
			uploadResult.setFileName(fileName);
			uploadResult.setFileSize(fileSize);
		} catch (Exception e) {
			e.printStackTrace();
			uploadResult.setStatus(false);
			uploadResult.setMsg(e.getMessage());
		}
		
		return uploadResult;
	}

	@Override
	public UploadResult uploadFile(MultipartFile multipartFile) {
		
		return this.uploadFile("", multipartFile);
	}


	private boolean isImageFile(MultipartFile multipartFile){
		String fileName = multipartFile.getOriginalFilename();
		String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
		
		return ThumbnailatorUtils.isSupportedOutputFormat(fileExt);
	}
	
	
	private String getSaveFilePath(String category, MultipartFile multipartFile){
		String fileName = multipartFile.getOriginalFilename();
		String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();

		String contextPath =  "/uploads";
		if(!StringUtils.containsWhitespace(category) && StringUtils.isNotBlank(category)){
			contextPath += "/" + category;
		}else{
			contextPath += "/p";
		}
		
		contextPath += "/" + fileExt;
		contextPath += "/" + DateTimeUtil.dateFormat(new Date(), "yyyyMMdd");
		
		return contextPath;
	}
	
	private String getUploadsPath(String contextPath){
		
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String uploadsPath = servletContext.getRealPath("/") ;
				
		File dirFile = new File(uploadsPath.concat(contextPath));
		if(!dirFile.exists()){
			//如果目录不存在
			dirFile.mkdirs();
		}
		
		return uploadsPath;
		
	}
	
	private String getRandomFileName(String contextPath, String fileExt){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	private String getImageUrl(String contextPath, String fileName, String fileExt){

		String nfileName = fileName.concat(".").concat(fileExt);
		String webPath = contextPath.concat("/").concat(nfileName);
		
		return webPath;
	}
	
	private String getSmallImageUrl(String contextPath, String fileName, String fileExt){

		String nfileName = fileName.concat("_s.").concat(fileExt);
		String webPath = contextPath.concat("/").concat(nfileName);
		
		return webPath;
	}
	
	private String getOrgImageUrl(String contextPath, String fileName, String fileExt){

		String nfileName = fileName.concat("_org.").concat(fileExt);
		String webPath = contextPath.concat("/").concat(nfileName);
		
		return webPath;
	}

}
*/