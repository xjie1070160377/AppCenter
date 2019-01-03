package cn.mooc.app.plugin.upload.local;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import cn.mooc.app.core.enums.ScaleSizeType;
import cn.mooc.app.core.model.ImageInfo;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.ThumbnailSize;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.plugin.context.PluginContext;
import cn.mooc.app.core.plugin.support.UploadPlugin;
import cn.mooc.app.core.plugin.support.model.PluginParam;
import cn.mooc.app.core.plugin.support.model.UploadFile;
import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.MessageFormatUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;

@Service
public class LocalUploadPlugin extends UploadPlugin {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String defUploadDir = "/uploads";
	
	private static Map<Integer,Positions> positions = new HashMap<Integer,Positions>();
	
	static{
		
		positions.put(1, Positions.TOP_LEFT);
		positions.put(2, Positions.TOP_CENTER);
		positions.put(3, Positions.TOP_RIGHT);
		positions.put(4, Positions.CENTER_LEFT);
		positions.put(5, Positions.CENTER);
		positions.put(6, Positions.CENTER_RIGHT);
		positions.put(7, Positions.BOTTOM_LEFT);
		positions.put(8, Positions.BOTTOM_CENTER);
		positions.put(9, Positions.BOTTOM_RIGHT);
		
	}
	
	@PostConstruct
	public void starting(){
		//为了测试而输出的内容
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println(LocalUploadPlugin.class.getSimpleName() + "被启动....");
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		
	}
	
	public long getFileSize(String targetFileUrl) {
		//取文件大小/或目录大小
		File file = new File(this.getUploadsPath(targetFileUrl));
		logger.debug("取文件路径的大小：{}", file.getAbsolutePath());
		return this.getFileSize(file);
	}
	
	private long getFileSize(File file) {     
        //判断文件是否存在     
        if (file.exists()) {     
            //如果是目录则递归计算其内容的总大小    
            if (file.isDirectory()) {     
                File[] children = file.listFiles();     
                long size = 0;     
                for (File f : children)     
                    size += getFileSize(f);     
                return size;     
            } else {
                return file.length();   
            }     
        } else {     
            //文件或文件夹不存在
        	logger.debug("文件不存在：{}", file.getAbsolutePath());
            return 0;     
        }     
    }
	
	public File getFile(String targetFileUrl) throws Exception {
		
		File file = new File(this.getUploadsPath(targetFileUrl));
		return file;
		
	}
	
	public UploadResult delFile(String targetFileUrl) {
		File file = new File(this.getUploadsPath(targetFileUrl));
				
		UploadResult uploadResult = new UploadResult();
		if(!file.exists()){
			uploadResult.setStatus(false);
			uploadResult.setMsg("文件不存在");
			return uploadResult;
		}
		
		try {
			boolean result = FileUtils.deleteQuietly(file);
			uploadResult.setStatus(result);			
			return uploadResult;
		} catch (Exception e) {
			logger.error("delFile",e);
			uploadResult.setStatus(false);
			uploadResult.setMsg("文件删除失败");
			return uploadResult;
		} 

	}
	
	public UploadResult moveFile(String srcFileUrl, String destFileUrl) {
		File srcFile = new File(this.getUploadsPath(srcFileUrl));
		File destFile = new File(this.getUploadsPath(destFileUrl));	
		
		return this.moveFile(srcFile, destFile);
		
	}
	
	public UploadResult moveFile(File srcFile, File destFile) {
		
		UploadResult uploadResult = new UploadResult();		
		try {
			if(srcFile.isFile()){
				if(destFile.isFile()){
					FileUtils.deleteQuietly(destFile);
				}
				FileUtils.copyFile(srcFile, destFile);
				srcFile.delete();
//				FileUtils.moveFile(srcFile, destFile);
				logger.debug("将文件 {} 移动到 {}", srcFile.getAbsolutePath(), destFile.getAbsolutePath());
				
				//如果是文件，则移动相关的文件，例如 _100x100.png				
				String baseName = FilenameUtils.getBaseName(srcFile.getName());
				String regex = baseName.concat(".*\\..*");
				File[] allfiles = srcFile.getParentFile().listFiles(new RelFileFilter(regex));
				
				String toFName = FilenameUtils.getBaseName(destFile.getName());
				for (File file : allfiles) {
					File moveToFile = new File(destFile.getParentFile() + File.separator + file.getName().replace(baseName+"_", toFName+"_"));
					FileUtils.deleteQuietly(moveToFile);
					FileUtils.moveFile(file, moveToFile);
					logger.debug("将文件 {} 移动到 {}", file.getAbsolutePath(), moveToFile.getAbsolutePath());
				}
				
			}else{
				if(srcFile.exists()){
					FileUtils.moveToDirectory(srcFile, destFile, true);
				}else{
					//待观察
					logger.error("文件 {} 不存在，但是用户却还要移动该文件",srcFile.getAbsolutePath());
					throw new Exception("文件移动失败，源路径不存在了");
					
				}
				
				logger.debug("将文件夹 {} 移动到 {}", srcFile.getAbsolutePath(), destFile.getAbsolutePath());
			}
			
			uploadResult.setStatus(true);			
			return uploadResult;
		} catch (Exception e) {
			logger.error("moveFile",e);
			uploadResult.setStatus(false);
			uploadResult.setMsg("文件移动失败");
			return uploadResult;
		} 
		
	}
	
	private FileInfo getFileInfo(String fileName, long fileSize, UploadFile uploadFile){
		
		ImageParam imageParam = uploadFile.getImageParam();
		
		String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
				
		if(imageParam!=null && StringUtils.isNotBlank(imageParam.getToFileExt())){
			fileExt = imageParam.getToFileExt().toLowerCase();
		}
		
		String categoryDir = "";
		if(imageParam!=null && StringUtils.isNotBlank(imageParam.getCategoryDir())){
			categoryDir = imageParam.getCategoryDir();
		}
		
		String uploadDir = defUploadDir;
		if(imageParam!=null && StringUtils.isNotEmpty(imageParam.getUploadDir())){
			if(imageParam.getUploadDir().startsWith("/")){
				uploadDir = imageParam.getUploadDir();
			}else{
				uploadDir = "/" + imageParam.getUploadDir();
			}
			
		}
		
		
		String serverRootPath = this.getServerRootPath();
				
		String destFileName = null;
		boolean diyDestFile = false;
		if(StringUtils.isNotBlank(uploadFile.getDestFileName())){
			//指定了文件名，不需要指定文件后缀
			destFileName = uploadFile.getDestFileName();
			diyDestFile = true;
		}else{
			destFileName = this.getRandomFileName();
		}
		
		String saveToDir = this.getSaveFilePath(uploadDir, categoryDir, fileName, diyDestFile);
		
		String webPath = this.getImageUrl(saveToDir, destFileName, fileExt);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setServerRootPath(serverRootPath);
		fileInfo.setWebPath(webPath);
		fileInfo.setFileExt(fileExt);
		fileInfo.setSaveToDir(saveToDir);
		fileInfo.setDestFileName(destFileName);
		fileInfo.setFileName(fileName);
		fileInfo.setFileSize(fileSize);
		
		return fileInfo;
	}
	

	@Override
	public UploadResult cropImage(String srcFileUrl, int topStart, int leftStart, int cutWidth, int cutHeight, PluginContext pluginContext, UploadFile uploadFile) {
		//
		UploadResult uploadResult = new UploadResult();
		
		ImageParam imageParam = uploadFile.getImageParam();
		File imageFile = new File(this.getUploadsPath(srcFileUrl));
		
		String fileName = imageFile.getName();
		String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
		long fileSize = imageFile.length();
		
		try {
			BufferedImage originalImage = ImageIO.read(imageFile);
			int orgH = originalImage.getHeight();
			if ((leftStart + cutHeight) > orgH) {
				leftStart = leftStart - (leftStart + cutHeight - orgH);
			}
			
			BufferedImage crop = Scalr.crop(originalImage, topStart, leftStart, cutWidth, cutHeight);
			
			
			if(imageParam!=null && imageParam.isScaleAfterCrop()){
				//注意：剪完后再压缩的话，图片文件大小变小，质量降低
				//BufferedImage scale_crop = Scalr.resize(crop, imageParam.getWidth(), imageParam.getHeight());
				BufferedImage scale_crop = Scalr.resize(crop, Method.ULTRA_QUALITY, Mode.FIT_EXACT, imageParam.getWidth(), imageParam.getHeight());
				
				ImageIO.write(scale_crop, fileExt, imageFile);
			}else{
			
				//不降低质量，直接保存
				ImageIO.write(crop, fileExt, imageFile);
			}
			
			
			
			
			if(imageParam!=null){
				//如果带了参数，则将裁剪后的文件按规则生成
				FileInfo fileInfo = this.getFileInfo(fileName, fileSize, uploadFile);
				
				this.saveFileByImageParam(pluginContext, fileInfo, crop, imageParam);
			}
			
			uploadResult.setStatus(true);
			uploadResult.setFileName(fileName);
			uploadResult.setFileUrl(srcFileUrl);
			
		} catch (Exception e) {
			logger.error("cropImage", e);
			uploadResult.setStatus(false);
			uploadResult.setMsg(e.getMessage());
		}
		
		return uploadResult;
	}
	
	public ImageInfo getImageFile(String targetFileUrl) {
		File imageFile = new File(this.getUploadsPath(targetFileUrl));
		ImageInfo imageInfo = new ImageInfo();
		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(imageFile);
			imageInfo.setWidth(originalImage.getWidth());
			imageInfo.setHeight(originalImage.getHeight());
		} catch (IOException e) {
			logger.error("getImageFile", e);
		}
		
		return imageInfo;
	}

	
	public UploadResult uploadFile(PluginContext pluginContext, UploadFile uploadFile) {
		logger.debug("使用 {} 插件上传...", this.getClass().getSimpleName());
		//
		MultipartFile multipartFile = uploadFile.getMultipartFile();
		ImageParam imageParam = uploadFile.getImageParam();

		if(imageParam!=null && !isImageFile(multipartFile)){
			return new UploadResult(false, "不是图片文件类型");
		}
		
		String fileName = multipartFile.getOriginalFilename();
		long fileSize = multipartFile.getSize();
				
		FileInfo fileInfo = this.getFileInfo(fileName, fileSize, uploadFile);
		
		UploadResult uploadResult = new UploadResult();
		
		try {
			if(imageParam==null) {
				try {
					//没传处理参数，直接将源文件保存
					multipartFile.transferTo(new File(fileInfo.getServerRootPath().concat(fileInfo.getWebPath())));
					uploadResult.setStatus(true);
					uploadResult.setFileUrl(fileInfo.getWebPath());
					uploadResult.setFileName(fileName);
					uploadResult.setFileSize(multipartFile.getSize());
					return uploadResult;
				} catch (Exception e) {
					logger.error("uploadFile",e);
					uploadResult.setStatus(false);
					uploadResult.setMsg("文件存储失败");
					return uploadResult;
				} 
			}
			
			BufferedImage originalImage = ImageIO.read(multipartFile.getInputStream());
			
			return this.saveFileByImageParam(pluginContext, fileInfo, originalImage, imageParam);

		} catch (Exception e) {
			e.printStackTrace();
			uploadResult.setStatus(false);
			uploadResult.setMsg(e.getMessage());
		}
		
		return uploadResult;
	}
	
	
	private UploadResult saveFileByImageParam(PluginContext pluginContext, FileInfo fileInfo, BufferedImage originalImage, ImageParam imageParam) throws Exception{
		UploadResult uploadResult = new UploadResult();
				
		Builder<?> builder = Thumbnails.of(originalImage);
		//
		
		boolean canResize = true;
		if(imageParam.getScale()){
			
			if(!imageParam.getStretch() && (imageParam.getWidth()> originalImage.getWidth() || imageParam.getHeight() > originalImage.getHeight())){
				canResize = false;
			}
			
			if(canResize){
				builder.scalingMode(ScalingMode.BICUBIC);			
				if(imageParam.getWidth()>0 && imageParam.getHeight()>0){
					if (imageParam.getScaleSizeType() == ScaleSizeType.ByMax) {
						if(imageParam.getWidth()> imageParam.getHeight()){
							builder.width(imageParam.getWidth());
						}else{
							builder.height(imageParam.getHeight());
						}
					}else{						
						builder.size(imageParam.getWidth(), imageParam.getHeight());
					}
					
				}else{
					if(imageParam.getWidth()>0){
						builder.width(imageParam.getWidth());
					}
					
					if(imageParam.getHeight()>0){
						builder.height(imageParam.getHeight());
					}
					
				}
			}else {
				builder.size(originalImage.getWidth(), originalImage.getHeight());
			}
			
			
		}else{
			builder.size(originalImage.getWidth(), originalImage.getHeight());
		}
		
		if(imageParam.getScale() && canResize && imageParam.getWidth()<=0 && imageParam.getHeight()<=0){
			//设置了宽度，就不能设置Scale，否则有冲突
			//builder.scale(0.3, 0.5);
			//如果设置了要压缩，但是又都没有指定宽和高，就自动按50%比例压缩
			builder.scale(0.5);
		}
		

		
		//加水印
		BufferedImage watermarkImage = null;
		BufferedImage addWaterImage = null;
		int opacity = 50;
		int position = 9;
		if(imageParam.getWatermark()){
			opacity = pluginContext.getSysConfigInt("sys.watermark.transparent", 50);
			position = pluginContext.getSysConfigInt("sys.watermark.position", 9);
			String wmUrl = pluginContext.getSysConfig("sys.watermark.url");
						
			if(StringUtils.isNotEmpty(wmUrl)){
				if(wmUrl.startsWith("http")){
					watermarkImage = ImageIO.read(new URL(wmUrl));
				}else{
					File waterMarkFile = new File(fileInfo.getServerRootPath() + wmUrl);
					if(waterMarkFile.exists()){
						watermarkImage = ImageIO.read(waterMarkFile);
					}
				}
				
				
			}else{
				File waterMarkFile = new File(fileInfo.getServerRootPath() + "watermark.png");
				if(waterMarkFile.exists()){
					watermarkImage = ImageIO.read(waterMarkFile);
				}
			}
			
			if(watermarkImage!=null){
				int width = (int) (originalImage.getWidth() * 0.2);
				watermarkImage = Thumbnails.of(watermarkImage).width(width).asBufferedImage();
				builder.watermark(positions.get(position), watermarkImage, opacity/100);
				addWaterImage = builder.asBufferedImage();
			}else{
				logger.error("需要生成水印，但是读取文印文件失败");
			}
			
			
			
		}
		
		if(imageParam.getScale() && canResize){				
			builder.toFile(fileInfo.getServerRootPath().concat(fileInfo.getWebPath()));
			logger.debug("{} 文件需要压缩，保存到 {}", fileInfo.getFileName(), fileInfo.getServerRootPath().concat(fileInfo.getWebPath()));
		}else{
			if(imageParam.getWatermark() && addWaterImage!=null){				
				ImageIO.write(addWaterImage, fileInfo.getFileExt(), new File(fileInfo.getServerRootPath().concat(fileInfo.getWebPath())));
			}else{
				ImageIO.write(originalImage, fileInfo.getFileExt(), new File(fileInfo.getServerRootPath().concat(fileInfo.getWebPath())));
			}
			
			logger.debug("{} 文件不需要压缩，保存到 {}", fileInfo.getFileName(), fileInfo.getServerRootPath().concat(fileInfo.getWebPath()));
		}
		
					
		//
		if(imageParam.getThumbnail()){
			//生成缩略图
			String smallImage = this.getSmallImageUrl(fileInfo.getSaveToDir(), fileInfo.getDestFileName(), fileInfo.getFileExt());
			
			this.createThumbnail(originalImage, new ThumbnailSize(imageParam.getThumbnailWidth(), imageParam.getThumbnailHeight()), fileInfo.getServerRootPath().concat(smallImage));
			logger.debug("{} 文件需要生成缩略图，生成到 {} ", fileInfo.getFileName(), smallImage);
			
			List<ThumbnailSize> extThumbnailSizes = imageParam.getExtThumbnailSizes();
			for (ThumbnailSize thumbnailSize : extThumbnailSizes) {
				//有额外尺寸生成要求
				String sizeImage = this.getSizeImageUrl(fileInfo.getSaveToDir(), imageParam.getSizeFileNameFmt(), fileInfo.getDestFileName(), fileInfo.getFileExt(), thumbnailSize);
				this.createThumbnail(originalImage, thumbnailSize, fileInfo.getServerRootPath().concat(sizeImage));
			}

		}
					
		if(imageParam.getOriginal()) {
			//保留原图
			String orgImage = this.getOrgImageUrl(fileInfo.getSaveToDir(), fileInfo.getDestFileName(), fileInfo.getFileExt());
			ImageIO.write(originalImage, fileInfo.getFileExt(), new File(fileInfo.getServerRootPath().concat(orgImage)));
			logger.debug("{} 文件需要额外保留原图，保留为：{}", fileInfo.getFileName(), orgImage);
		}
		
		uploadResult.setStatus(true);
		uploadResult.setFileUrl(fileInfo.getWebPath());
		uploadResult.setFileName(fileInfo.getFileName());
		uploadResult.setFileSize(fileInfo.getFileSize());
		
		logger.debug("上传成功，url地址：{}", fileInfo.getWebPath());
		
		return uploadResult;
	}
	
	private void createThumbnail(BufferedImage originalImage, ThumbnailSize thumbnailSize, String toFilePath) throws IOException{
		//生成缩略图
		
		Builder<?> thumbnailBuilder = Thumbnails.of(originalImage);
		
		logger.debug("thumbnailSize {} * {}", thumbnailSize.getWidth(), thumbnailSize.getHeight());
		
		if(thumbnailSize.getWidth()>0 && thumbnailSize.getHeight()>0){
			thumbnailBuilder.size(thumbnailSize.getWidth(), thumbnailSize.getHeight());
		}else{
			//查原始图宽度
			//System.out.println(originalImage.getWidth());
			if(thumbnailSize.getWidth()>0){
				thumbnailBuilder.width(thumbnailSize.getWidth());
			}
			
			if(thumbnailSize.getHeight()>0){
				thumbnailBuilder.height(thumbnailSize.getHeight());
			}
			
		}
		
		if(thumbnailSize.getWidth()<=0 && thumbnailSize.getHeight()<=0){
			thumbnailBuilder.scale(0.5);
		}
		thumbnailBuilder.toFile(toFilePath);
		
		logger.debug("缩略图创建成功，文件地址：{}", toFilePath);
	}
	

	@Override
	public boolean checkCanStart() {
		//对插件启动的必备条件进行检查，例如判断某个参数必须有
		Map<String, PluginParam> pluginData = this.getPluginInfo().getPluginData();
		if(!pluginData.containsKey("BaseUrl") || pluginData.get("BaseUrl") == null){
			return false;
		}
		
		//或者判断某个文件或目录必须存在
		if(!this.getPluginInfo().getPluginDir().exists()){
			return false;
		}

		return true;
	}

	@Override
	public boolean checkCanStop() {
		return true;
	}
	
	private boolean isImageFile(MultipartFile multipartFile){
		String fileName = multipartFile.getOriginalFilename();
		String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
		String[] diyExt = new String[]{ "tbi"};
		
		if(ArrayUtils.contains(diyExt, fileExt)){
			return true;
		}
		return isImageFile(fileExt);
	}
	
	private boolean isImageFile(String fileExt){		
		return ThumbnailatorUtils.isSupportedOutputFormat(fileExt);
	}
	
	
	/**
	 * 取upload目录下默认存放的路径，按日期存放
	 * 
	 * @param category
	 * @param multipartFile
	 * @return
	 */
	private String getSaveFilePath(String uploadDir, String category, String fileName, boolean diyDestFile){
		String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();

		String contextPath =  uploadDir;
		
		if(!StringUtils.containsWhitespace(category) && StringUtils.isNotBlank(category)){
			contextPath += "/" + category;
		}else{
			contextPath += "/p";
		}
		
		if(!diyDestFile){
			contextPath += "/" + DateTimeUtil.dateFormat(new Date(), "yyyyMMdd");
			contextPath += "/" + fileExt;
		}
		
		
		this.createUploadPath(contextPath);
		
		return contextPath;
	}



	/**
	 * 取服务器上web根目录的物理路径
	 * 
	 * @return
	 */
	private String getServerRootPath(){
		
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String serverRootPath = servletContext.getRealPath("/") ;
		
		return serverRootPath;
		
	}
	
	/**
	 * 在网站根目录下检查或创建一个文件路径，如果不存在则创建
	 * 
	 * @param uploadsPath
	 * @return
	 */
	private String createUploadPath(String uploadsPath){
		
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String serverRootPath = servletContext.getRealPath("/") ;
				
		File dirFile = new File(serverRootPath.concat(uploadsPath));
		if(!dirFile.exists()){
			//如果目录不存在
			dirFile.mkdirs();
		}
		
		return dirFile.getAbsolutePath();
		
	}
	
	private String getUploadsPath(String uploadsPath){
		
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String serverRootPath = servletContext.getRealPath("/") ;
		
		return serverRootPath.concat(uploadsPath);
		
	}
	
	private String getRandomFileName(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	private String getImageUrl(String saveToDir, String fileName, String fileExt){

		String nfileName = StringUtils.isNotEmpty(fileExt)?fileName.concat(".").concat(fileExt):fileName;
		String webPath = saveToDir.concat("/").concat(nfileName);
		
		return webPath;
	}
	
	private String getSizeImageUrl(String saveToDir, String sizeFileNameFmt, String fileName, String fileExt, ThumbnailSize thumbnailSize){
		// _{0}x{1}
		String sizeFileName = MessageFormatUtil.formatByNum(sizeFileNameFmt, thumbnailSize.getWidth(), thumbnailSize.getHeight());
		String nfileName = fileName.concat(sizeFileName).concat(".").concat(fileExt);
		String webPath = saveToDir.concat("/").concat(nfileName);
		
		return webPath;
	}
	
	private String getSmallImageUrl(String saveToDir, String fileName, String fileExt){

		String nfileName = fileName.concat("_min.").concat(fileExt);
		String webPath = saveToDir.concat("/").concat(nfileName);
		
		return webPath;
	}
	
	private String getOrgImageUrl(String saveToDir, String fileName, String fileExt){

		String nfileName = fileName.concat("_org.").concat(fileExt);
		String webPath = saveToDir.concat("/").concat(nfileName);
		
		return webPath;
	}




}
