package cn.mooc.app.core.service.upload;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mooc.app.core.enums.GenNameType;
import cn.mooc.app.core.model.ImageInfo;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.plugin.IPlugin;
import cn.mooc.app.core.plugin.context.PluginContext;
import cn.mooc.app.core.plugin.manager.PluginsRegister;
import cn.mooc.app.core.plugin.support.UploadPlugin;
import cn.mooc.app.core.plugin.support.model.UploadFile;
import cn.mooc.app.core.service.UploadFileService;

/**
 * 通过调用插件进行上传
 * 
 * @author Taven
 *
 */
@Service
public class PluginUploadFileService implements UploadFileService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PluginsRegister pluginsRegister;
	@Autowired
	private PluginContext pluginContext;
	
	@Override
	public UploadResult uploadFile(String category, MultipartFile multipartFile) {
		
		return this.uploadFile(multipartFile, null);
	}

	@Override
	public UploadResult uploadFile(MultipartFile multipartFile) {
		return this.uploadFile(multipartFile, null);
	}

	@Override
	public UploadResult uploadFile(MultipartFile multipartFile,	ImageParam imageParam) {
		UploadResult uploadResult = new UploadResult();
		UploadPlugin uploadPlugin = this.getUploadPlugin();
		if(uploadPlugin==null){
			uploadResult.setStatus(false);
			uploadResult.setMsg("没有检测到启用的上传插件");
			return uploadResult;
		}
		UploadFile uploadFile = new UploadFile(multipartFile, GenNameType.Random, imageParam);
		uploadResult = uploadPlugin.uploadFile(pluginContext, uploadFile);
		return uploadResult;
	}
	
	private UploadPlugin getUploadPlugin(){
		for (IPlugin iPlugin : pluginsRegister.getPlugins().values()) {
			
			if(iPlugin instanceof UploadPlugin){
				return (UploadPlugin) iPlugin;
			}else if(AopUtils.isAopProxy(iPlugin)){
				//is proxy
			}
			
		}
		
		return null;
	}
	
	@Override
	public UploadResult uploadFile(MultipartFile multipartFile,	ImageParam imageParam, String destFileName) {
		UploadResult uploadResult = new UploadResult();
		UploadPlugin uploadPlugin = this.getUploadPlugin();
		if(uploadPlugin==null){
			uploadResult.setStatus(false);
			uploadResult.setMsg("没有检测到启用的上传插件");
			return uploadResult;
		}
		
		UploadFile uploadFile = new UploadFile(multipartFile, GenNameType.Random, imageParam, destFileName);
		uploadResult = uploadPlugin.uploadFile(pluginContext, uploadFile);

		return uploadResult;
	}
	
	@Override
	public UploadResult cropImage(String srcFileUrl, int topStart, int leftStart, int cutWidth, int cutHeight,	ImageParam imageParam){
		UploadResult uploadResult = new UploadResult();
		UploadPlugin uploadPlugin = this.getUploadPlugin();
		if(uploadPlugin==null){
			uploadResult.setStatus(false);
			uploadResult.setMsg("没有检测到启用的上传插件");
			return uploadResult;
		}
		
		UploadFile uploadFile = new UploadFile(null, GenNameType.Random, imageParam);
		uploadResult = uploadPlugin.cropImage(srcFileUrl, topStart, leftStart, cutWidth, cutHeight, pluginContext, uploadFile);

		return uploadResult;
	}
	
	public ImageInfo getImageFile(String targetFileUrl){
		UploadPlugin uploadPlugin = this.getUploadPlugin();

		if(uploadPlugin==null){
			return new ImageInfo();
		}
		
		return uploadPlugin.getImageFile(targetFileUrl);
	}
	
	
	public long getFileSize(String targetFileUrl) {
		UploadPlugin uploadPlugin = this.getUploadPlugin();
		if(uploadPlugin==null){
			return 0;
		}
		
		return uploadPlugin.getFileSize(targetFileUrl);
	}

	@Override
	public UploadResult delFile(String targetFileUrl) {
		UploadPlugin uploadPlugin = this.getUploadPlugin();
		if(uploadPlugin==null){
			UploadResult uploadResult = new UploadResult();
			uploadResult.setStatus(false);
			uploadResult.setMsg("没有检测到启用的上传插件");
			return uploadResult;
		}
		
		return uploadPlugin.delFile(targetFileUrl);
		
	}

	@Override
	public UploadResult moveFile(String srcFileUrl, String destFileUrl) {
		UploadPlugin uploadPlugin = this.getUploadPlugin();
		if(uploadPlugin==null){
			UploadResult uploadResult = new UploadResult();
			uploadResult.setStatus(false);
			uploadResult.setMsg("没有检测到启用的上传插件");
			return uploadResult;
		}
		
		return uploadPlugin.moveFile(srcFileUrl, destFileUrl);
	}

	@Override
	public File getFile(String targetFileUrl) throws Exception {
		UploadPlugin uploadPlugin = this.getUploadPlugin();
		if(uploadPlugin==null){
			return null;
		}
		
		return uploadPlugin.getFile(targetFileUrl);
	}

}
