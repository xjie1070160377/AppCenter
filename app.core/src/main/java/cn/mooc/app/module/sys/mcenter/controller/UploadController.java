package cn.mooc.app.module.sys.mcenter.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.model.ImageInfo;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.service.TaskJobService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.HttpUtil;
import cn.mooc.app.core.utils.JsonUtil;

@Controller
@RequestMapping("/sys")
public class UploadController extends SysModuleController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private TaskJobService taskJobService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity uploadFile(MultipartFile file, HttpServletRequest request) {
		try {
			UploadResult uploadResult = webContext.uploadFile(file);
			if (uploadResult.isStatus()) {
				webContext.sysUserLog(LogType.UserOpr, "上传文件成功：" + uploadResult.getFileUrl());

				return new ResponseEntity<>("{\"fileUrl\": \"" + uploadResult.getFileUrl() + "\",\"fileName\":\"" + uploadResult.getFileName() + "\",\"fileSize\":\"" + uploadResult.getFileSize()
						+ "\" }", HttpStatus.OK);
			} else {
				// 返回失败消息
				return new ResponseEntity<>("{\"error\": \"" + uploadResult.getMsg() + ".\" }", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("{\"error\": \"文件上传失败.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity uploadImage(HttpServletRequest request, MultipartFile file, ImageParam imageParam) {
		try {
			//方便手机缩略图展示，生成固大小文件
			imageParam.addThumbnailSize(120, 120);
			imageParam=null;
			int multiSize = HttpUtil.getReqParamInt(request, "multiSize", 0);
			if (multiSize == 1) {
				//需要额外生成缩略图尺寸
				imageParam.addThumbnailSize(100, 100);
				imageParam.addThumbnailSize(200, 200);
				imageParam.addThumbnailSize(300, 300);
				imageParam.addThumbnailSize(400, 400);
			}
			
			UploadResult uploadResult = webContext.uploadFile(file, imageParam);
			if (uploadResult.isStatus()) {
				webContext.sysUserLog(LogType.UserOpr, "上传文件成功：" + uploadResult.getFileUrl());
				return new ResponseEntity<>("{\"fileUrl\": \"" + uploadResult.getFileUrl() + "\",\"fileName\":\"" + uploadResult.getFileName() + "\",\"fileSize\":\"" + uploadResult.getFileSize()
						+ "\" }", HttpStatus.OK);
			} else {
				// 返回失败消息
				return new ResponseEntity<>("{\"error\": \"" + uploadResult.getMsg() + ".\" }", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("{\"error\": \"文件上传失败.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ueditor文件上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload_file", method = RequestMethod.POST)
	@ResponseBody
	public String upload_file(HttpServletRequest request) {
		try {
			MultipartFile file = getMultipartFile(request);
			UploadResult uploadResult = webContext.uploadFile(file);
			if (uploadResult.isStatus()) {
				Map<String, String> umap = new HashMap<String, String>();
				umap.put("title", uploadResult.getFileName());
				umap.put("state", "SUCCESS");
				umap.put("original", uploadResult.getFileName());
				umap.put("url", uploadResult.getFileUrl());
				String json = JsonUtil.toJson(umap);
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ueditor图片上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload_image", method = RequestMethod.POST)
	@ResponseBody
	public String upload_image(Boolean scale, Boolean exact, Integer width, Integer height, Boolean thumbnail, Boolean watermark, HttpServletRequest request, HttpServletResponse response) {
		try {
			ImageParam imageParam = new ImageParam();
			imageParam.setWidth(width == null ? 0 : width);
			imageParam.setHeight(height == null ? 0 : height);
			imageParam.setScale(scale == null ? false : scale);
			imageParam.setStretch(exact == null ? false : exact);
			//默认加水印
			boolean defWaterMark = sysDataService.getWatermarkDefaultEnable();
			
			imageParam.setWatermark(watermark == null ? defWaterMark : watermark);
			imageParam.setThumbnail(thumbnail == null ? false : thumbnail);
			imageParam.setOriginal(true);
			imageParam=null;
			MultipartFile file = getMultipartFile(request);
			UploadResult uploadResult = webContext.uploadFile(file, imageParam);
			if (uploadResult.isStatus()) {
				Map<String, String> umap = new HashMap<String, String>();
				umap.put("title", uploadResult.getFileName());
				umap.put("state", "SUCCESS");
				umap.put("original", uploadResult.getFileName());
				umap.put("url", uploadResult.getFileUrl());
				String json = JsonUtil.toJson(umap);
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private MultipartFile getMultipartFile(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		if (CollectionUtils.isEmpty(fileMap)) {
			throw new IllegalStateException("No upload file found!");
		}
		return fileMap.entrySet().iterator().next().getValue();
	}

	/**
	 * ueditor图片上传前返回一个路径，无意义但ueditor需要
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload_image", method = RequestMethod.GET)
	public void imageSavePath(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getParameter("fetch") != null) {
			response.setHeader("Content-Type", "text/javascript");
			response.getWriter().print("updateSavePath( ['image'] );");
			return;
		}
	}

	/**
	 * moocUpload图片上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public String uploadImg(Boolean scale, Boolean exact, Integer width, Integer height, Boolean thumbnail, Boolean watermark, HttpServletRequest request, HttpServletResponse response) {
		try {
			ImageParam imageParam = new ImageParam();
			imageParam.setWidth(width == null ? 0 : width);
			imageParam.setHeight(height == null ? 0 : height);
			imageParam.setScale(scale == null ? false : scale);
			imageParam.setStretch(exact == null ? false : exact);
			imageParam.setWatermark(watermark == null ? false : watermark);
			imageParam.setThumbnail(thumbnail == null ? false : thumbnail);
			imageParam.setOriginal(true);

			MultipartFile file = getMultipartFile(request);
			if (file == null) {
				return "NoFile";
			}
			UploadResult uploadResult = webContext.uploadFile(file, imageParam);
			if (uploadResult.isStatus()) {
				String fileName = uploadResult.getFileName();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (StringUtils.isNotEmpty(ext) && !"gif".equals(ext) && !"jpeg".equals(ext) && !"jpg".equals(ext) && !"png".equals(ext) && !"bmp".equals(ext)) {
					return "格式不正确！";
				}
				return uploadResult.getFileUrl();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/imageCrop")
	public String imageCrop(Model model, HttpServletRequest request, String fileUrl, Integer cropWidth, Integer cropHeight) {
		
		model.addAttribute("fileUrl", fileUrl);
		
		ImageInfo imageInfo = webContext.getImageFile(fileUrl);
		//取图片文件真实尺寸大小等信息
		model.addAttribute("orgWidth", imageInfo.getWidth());
		model.addAttribute("orgHeight", imageInfo.getHeight());
		
		if(cropWidth == null){
			cropWidth = 200;
		}
		
		if(cropHeight == null){
			cropHeight = 200;
		}
		
		model.addAttribute("cropWidth", cropWidth);
		model.addAttribute("cropHeight", cropHeight);
		//参数透传
		model.addAttribute("pMap", request.getParameterMap());
		
		return ModuleView("/imageCrop");
	}
	
	@RequestMapping(value = "/cropImage")
	@ResponseBody
	public Map<String, Object> cropImage(HttpServletRequest request, String fileUrl, int topStart, int leftStart, int cutWidth, int cutHeight, int toWidth, int toHeight, ImageParam imageParam){
		
		if(imageParam!=null){
			imageParam.addThumbnailSize(120, 120);
			
			int multiSize = HttpUtil.getReqParamInt(request, "multiSize", 0);
			if (multiSize == 1) {
				//需要额外生成缩略图尺寸
				imageParam.addThumbnailSize(100, 100);
				imageParam.addThumbnailSize(200, 200);
				imageParam.addThumbnailSize(300, 300);
				imageParam.addThumbnailSize(400, 400);
			}
			
			//
			imageParam.setScale(true);
			imageParam.setWidth(toWidth);
			imageParam.setHeight(toHeight);
			
		}
		
		logger.debug("裁剪图片：{}", fileUrl);
		UploadResult uploadResult = webContext.cropImage(fileUrl, topStart, leftStart, cutWidth, cutHeight, imageParam);
				
		if (uploadResult.isStatus()) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			
			resMap.put("resultStatus", true);
			resMap.put("resultMsg", "SUCCESS");
			
			resMap.put("title", uploadResult.getFileName());
			resMap.put("fileUrl", uploadResult.getFileUrl());
			
			return resMap;
		}
		
		return HttpResponseUtil.failureJson("图片裁剪失败："+ uploadResult.getMsg());
	}
}
