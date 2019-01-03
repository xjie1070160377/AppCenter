package cn.mooc.app.module.sys.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.utils.JsonUtil;
@Controller
public class ForUploadController {
	
	@Autowired
	protected WebContext webContext;
	
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
			imageParam.setWatermark(watermark == null ? false : watermark);
			imageParam.setThumbnail(thumbnail == null ? false : thumbnail);
			imageParam.setOriginal(true);

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
}
