package cn.mooc.app.module.cms.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
//import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.module.cms.data.entity.Global;
import cn.mooc.app.module.cms.data.entity.PublishPoint;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.SiteWatermark;
import cn.mooc.app.module.cms.support.Constants;
import cn.mooc.app.module.cms.support.FileHandler;
import cn.mooc.app.module.cms.support.Images;
import cn.mooc.app.module.cms.support.ScaleParam;
import cn.mooc.app.module.cms.support.ThumbnailParam;
import cn.mooc.app.module.cms.support.UploadUtils;
import cn.mooc.app.module.cms.support.Uploader;
import cn.mooc.app.module.cms.support.WatermarkParam;

import com.google.common.net.HttpHeaders;

@Service
public class UploadHandlerService {
	protected final Logger logger = LoggerFactory.getLogger(UploadHandlerService.class);
	
	public static final String VALID_EXT = "7z,aiff,asf,avi,bmp,csv,doc,fla,flv,gif,gz,gzip,jpeg,jpg,mid,mov,mp3,mp4,mpc,mpeg,mpg,ods,odt,pdf,png,ppt,pxd,qt,ram,rar,rm,rmi,rmvb,rtf,sdc,sitd,swf,sxc,sxw,tar,tgz,tif,tiff,txt,vsd,wav,wma,wmv,xls,xml,zip";

	public String copyImage(BufferedImage buff, String extension, String formatName, Site site, Boolean scale, Boolean exact, Integer width,
			Integer height, Boolean thumbnail, Integer thumbnailWidth, Integer thumbnailHeight, Boolean watermark, String ip, Integer userId,
			Integer siteId) {
		ScaleParam scaleParam = getScaleParam(scale, exact, width, height);
		scale = scaleParam.getScale();

		ThumbnailParam thumbnailParam = new ThumbnailParam(thumbnail, thumbnailWidth, thumbnailHeight);
		thumbnail = thumbnailParam.getThumbnail();

		SiteWatermark sw = site.getWatermark();
		WatermarkParam watermarkParam = sw.getWatermarkParam(watermark);
		watermark = watermarkParam.getWatermark();

		PublishPoint point = site.getUploadsPublishPoint();
		String urlPrefix = point.getUrlPrefix();
		FileHandler fileHandler = point.getFileHandler(pathResolver);
		String pathname = UploadUtils.getUrl(site.getId(), Uploader.IMAGE, extension);
		try {
			storeImage(buff, scaleParam, thumbnailParam, watermarkParam, formatName, pathname, fileHandler, ip, userId, siteId);
			long length = buff.getWidth() * buff.getHeight() / 3;
			attachmentService.save(pathname, length, ip, userId, siteId);
			return urlPrefix + pathname;
		} catch (IOException e) {
			logger.error(null, e);
			return null;
		}
	}

	public ScaleParam getScaleParam(Boolean scale, Boolean exact,
			Integer width, Integer height) {
		ScaleParam scaleInfo;
		if (scale == null) {
			width = 1920;
			height = 1280;
			scale = width != null || height != null;
			scaleInfo = new ScaleParam(scale, false, width, height);
		} else {
			scaleInfo = new ScaleParam(scale, exact, width, height);
		}
		return scaleInfo;
	}

	public String storeImage(BufferedImage buff, String extension, String formatName, Site site, String ip, Integer userId) {
		PublishPoint point = site.getUploadsPublishPoint();
		FileHandler fileHandler = point.getFileHandler(pathResolver);
		String urlPrefix = point.getUrlPrefix();
		String filename = UploadUtils.getUrl(site.getId(), Uploader.IMAGE, extension);
		try {
			fileHandler.storeImage(buff, formatName, filename);
			long length = buff.getWidth() * buff.getHeight() / 3;
			attachmentService.save(filename, length, ip, userId, site.getId());
			return urlPrefix + filename;
		} catch (IOException e) {
			logger.error(null, e);
			return null;
		}
	}

	public void upload(String url, String type, Site site, Integer userId, String ip, UploadResult result) {
		upload(url, type, site, userId, ip, result, null, null, null, null, null, null, null, null);
	}

	/*public void upload(MultipartFile partFile, String type, Site site, Integer userId, String ip, UploadResult result) {
		upload(partFile, type, site, userId, ip, result, null, null, null, null, null, null, null, null);
	}*/

	public void upload(String url, String type, Site site, Integer userId, String ip, UploadResult result, Boolean scale, Boolean exact,
			Integer width, Integer height, Boolean thumbnail, Integer thumbnailWidth, Integer thumbnailHeight, Boolean watermark) {
		try {
			URL source = new URL(url);
			// file（下载）支持重定向支持，其他的不支持。
			if (Uploader.FILE.equals(type)) {
				HttpURLConnection.setFollowRedirects(true);
			} else {
				HttpURLConnection.setFollowRedirects(false);
			}
			HttpURLConnection conn = (HttpURLConnection) source.openConnection();
			conn.setRequestProperty("User-Agent", Constants.USER_ANGENT);
			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				result.setStatus(false);
				result.setMsg("URL response error:" + responseCode);
				return;
			}
			if (Uploader.IMAGE.equals(type)) {
				String contentType = conn.getContentType();
				if (!validateImageContentType(contentType, result)) {
					return;
				}
			}
			String disposition = conn.getHeaderField(HttpHeaders.CONTENT_DISPOSITION);
			String fileName = StringUtils.substringBetween(disposition, "filename=\"", "\"");
			if (StringUtils.isBlank(fileName)) {
				fileName = FilenameUtils.getName(source.getPath());
			}
			String ext = FilenameUtils.getExtension(fileName);
			File temp = File.createTempFile("upload", "." + ext);
			InputStream is = conn.getInputStream();
			FileUtils.copyInputStreamToFile(is, temp);
			IOUtils.closeQuietly(is);
			try {
				doUpload(temp, fileName, type, site, userId, ip, result, scale, exact, width, height, thumbnail, thumbnailWidth, thumbnailHeight,
						watermark);
			} finally {
				FileUtils.deleteQuietly(temp);
			}
		} catch (Exception e) {
			result.setStatus(false);;
			result.setMsg(e.getMessage());
		}
		return;
	}

	/*public void upload(MultipartFile partFile, String type, Site site, Integer userId, String ip, UploadResult result, Boolean scale, Boolean exact,
			Integer width, Integer height, Boolean thumbnail, Integer thumbnailWidth, Integer thumbnailHeight, Boolean watermark) {
		try {
			if (!validateFile(partFile, result)) {
				return;
			}
			String fileName = partFile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(fileName);
			File temp = File.createTempFile("upload", "." + ext);
			partFile.transferTo(temp);
			try {
				doUpload(temp, fileName, type, site, userId, ip, result, scale, exact, width, height, thumbnail, thumbnailWidth, thumbnailHeight,
						watermark);
			} finally {
				FileUtils.deleteQuietly(temp);
			}
		} catch (Exception e) {
			result.setError(e.getMessage());
			logger.error(null, e);
		}
		return;
	}
	
	public void upload2(MultipartFile partFile, String type, Site site, Integer userId, String ip, UploadResult bigresult, UploadResult smallresult, Boolean exact,
			Integer width, Integer height, Boolean thumbnail, Integer thumbnailWidth, Integer thumbnailHeight, Boolean watermark) {
		try {
			if (!validateFile(partFile, bigresult)) {
				return;
			}
			String fileName = partFile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(fileName);
			File temp = File.createTempFile("upload", "." + ext);
			partFile.transferTo(temp);
			try {
				doUpload(temp, fileName, type, site, userId, ip, smallresult, true, exact, width, height, thumbnail, thumbnailWidth, thumbnailHeight,
						watermark);
				
				doUpload(temp, fileName, type, site, userId, ip, bigresult, false, exact, width, height, thumbnail, thumbnailWidth, thumbnailHeight,
						watermark);
				
			} finally {
				FileUtils.deleteQuietly(temp);
			}
		} catch (Exception e) {
			bigresult.setError(e.getMessage());
			logger.error(null, e);
		}
		return;
	}*/

	private UploadResult doUpload(File file, String fileName, String type, Site site, Integer userId, String ip, UploadResult result, Boolean scale,
			Boolean exact, Integer width, Integer height, Boolean thumbnail, Integer thumbnailWidth, Integer thumbnailHeight, Boolean watermark)
			throws Exception {
		Integer siteId = site.getId();
		long fileLength = file.length();
		String ext = FilenameUtils.getExtension(fileName).toLowerCase();
		// 后缀名是否合法
		if (!validateExt(ext, VALID_EXT, result)) {
			return result;
		}
		// 文库是否开启
		if (type == Uploader.DOC && !isDocEnabled(result, site.getGlobal())) {
			return result;
		}
		PublishPoint point = site.getUploadsPublishPoint();
		String urlPrefix = point.getUrlPrefix();
		FileHandler fileHandler = point.getFileHandler(pathResolver);

		String pathname = site.getSiteBase(Uploader.getQuickPathname(type, ext));
		String fileUrl = urlPrefix + pathname;
		String pdfUrl = null;
		String swfUrl = null;
		String mediaPicPath = null;
		Integer timeLength = null;
		if (Uploader.IMAGE.equals(type)) {
			SiteWatermark sw = site.getWatermark();
			doUploadImage(fileHandler, file, pathname, scale, exact, width, height, thumbnail, thumbnailWidth, thumbnailHeight, watermark, sw,
					ip, userId, siteId);
		} else {
			fileHandler.storeFile(file, pathname);
		}
		attachmentService.save(pathname, fileLength, ip, userId, siteId);
		result.setStatus(true);
		result.setFileUrl(fileUrl);
		result.setFileName(fileName);
		result.setFileSize(fileLength);
		return result;
	}

	private void doUploadImage(FileHandler fileHandler, File file, String pathname, Boolean scale, Boolean exact, Integer width, Integer height,
			Boolean thumbnail, Integer thumbnailWidth, Integer thumbnailHeight, Boolean watermark, SiteWatermark sw, String ip,
			Integer userId, Integer siteId) throws IOException {
		
		ScaleParam scaleParam = getScaleParam(scale, exact, width, height);
		scale = scaleParam.getScale();

		ThumbnailParam thumbnailParam = new ThumbnailParam(thumbnail, thumbnailWidth, thumbnailHeight);
		thumbnail = thumbnailParam.getThumbnail();

		WatermarkParam watermarkParam = sw.getWatermarkParam(watermark);
		watermark = watermarkParam.getWatermark();

		String formatName = null;
		if (watermark || scale || thumbnail) {
			InputStream is = FileUtils.openInputStream(file);
			formatName = Images.getFormatName(is);
			IOUtils.closeQuietly(is);
		}
		if (StringUtils.isNotBlank(formatName)) {
			// 可以且需要处理的图片
			BufferedImage buff = ImageIO.read(file);
			storeImage(buff, scaleParam, thumbnailParam, watermarkParam, formatName, pathname, fileHandler, ip, userId, siteId);
		} else {
			// 不可处理的图片
			fileHandler.storeFile(file, pathname);
		}
	}

	private void storeImage(BufferedImage buff, ScaleParam scaleParam, ThumbnailParam thumbnailParam, WatermarkParam watermarkParam,
			String formatName, String pathname, FileHandler fileHandler, String ip, Integer userId, Integer siteId) throws IOException {
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		List<String> filenames = new ArrayList<String>();
		if (scaleParam.getScale()) {
			buff = Images.resize(buff, scaleParam);
		}
		BufferedImage thumbnailBuff = null;
		String thumbnailName = null;
		if (thumbnailParam.getThumbnail()) {
			Integer width = thumbnailParam.getWidth();
			Integer height = thumbnailParam.getHeight();
			thumbnailBuff = Scalr.resize(buff, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, width, height);
			thumbnailName = Uploader.getThumbnailName(pathname);
			images.add(thumbnailBuff);
			filenames.add(thumbnailName);
		}
		if (watermarkParam.getWatermark()) {
			String imagePath = watermarkParam.getImagePath();
			FileHandler handler = FileHandler.getFileHandler(pathResolver, Constants.TEMPLATE_STORE_PATH);
			BufferedImage watermarkBuff = handler.readImage(imagePath);
			if (watermarkBuff != null) {
				Images.watermark(buff, watermarkBuff, watermarkParam);
			}
		}
		images.add(buff);
		filenames.add(pathname);
		fileHandler.storeImages(images, formatName, filenames);
		if (thumbnailName != null) {
			long length = thumbnailBuff.getWidth() * thumbnailBuff.getHeight() / 3;
			attachmentService.save(thumbnailName, length, ip, userId, siteId);
		}
	}

	/*private void doUploadDoc(FileHandler fileHandler, String pathname, String extension, String pdfPathname, String swfPathname, File file,
			String ip, Integer userId, Integer siteId) throws Exception {
		File pdfFrom = file;
		File pdfTemp = null;
		if (StringUtils.isNotBlank(pdfPathname)) {
			pdfTemp = File.createTempFile("pdf", ".pdf");
			OpenOfficeConverter.doc2pdf(file, pdfTemp, Constants.OPENOFFICE_HOST, Constants.OPENOFFICE_PORT);
			pdfFrom = pdfTemp;
		}
		File swfTemp = File.createTempFile("swf", ".swf");
		SwfConverter.pdf2swf(pdfFrom, swfTemp, Constants.SWFTOOLS_PDF2SWF);

		List<File> files = new ArrayList<File>();
		List<String> pathnames = new ArrayList<String>();
		files.add(file);
		pathnames.add(pathname);
		files.add(swfTemp);
		pathnames.add(swfPathname);
		if (pdfTemp != null) {
			files.add(pdfTemp);
			pathnames.add(pdfPathname);
		}
		fileHandler.storeFile(files, pathnames);
		attachmentService.save(swfPathname, swfTemp.length(), ip, userId, siteId);
		if (pdfTemp != null) {
			attachmentService.save(pdfPathname, swfTemp.length(), ip, userId, siteId);
		}
		for (File f : files) {
			FileUtils.deleteQuietly(f);
		}
	}*/


	private boolean isDocEnabled(UploadResult result, Global global) {
		if (!global.isDocEnabled()) {
			result.setMsg("DOC Converter is not available!");
			return false;
		}
		return true;
	}

	private boolean validateFile(MultipartFile partFile, UploadResult result) {
		if (partFile == null || partFile.isEmpty()) {
			logger.debug("file is empty");
			result.setMsg("no file upload!");
			return false;
		}
		return true;
	}

	private boolean validateImageContentType(String contentType, UploadResult result) {
		if (!StringUtils.contains(contentType, "image")) {
			logger.debug("ContentType not contain Image: " + contentType);
			result.setMsg("ContentType not contain Image: " + contentType);
			return false;
		}
		return true;
	}
	
	
	private boolean validateExt(String extension, String allowed,  UploadResult result) {
		if (!isValid(extension, allowed)){
			logger.debug("image extension not allowed: " + extension);
			result.setMsg("imageExtensionNotAllowed".concat(new String[] { extension }.toString()));
			return false;
		}
		return true;
	}
	
	private boolean isValid(String extension, String allowed) {
		if (StringUtils.isNotBlank(allowed)) {
			for (String a : StringUtils.split(allowed, ',')) {
				if (a.equalsIgnoreCase(extension)) {
					return true;
				}
			}
			return false;
		}
		return true;

	}
	

	@Autowired
	protected AttachmentService attachmentService;
	@Autowired
	protected PathResolver pathResolver;
	@Autowired
	protected Properties properties;
}
