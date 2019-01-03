package cn.mooc.app.core.plugin.support;

import java.io.File;

import cn.mooc.app.core.model.ImageInfo;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.plugin.AbstractPlugin;
import cn.mooc.app.core.plugin.context.PluginContext;
import cn.mooc.app.core.plugin.support.model.UploadFile;

/**
 * 文件上传类插件
 * 
 * @author Taven
 *
 */
public abstract class UploadPlugin extends AbstractPlugin {

	/**
	 * 取文件夹或文件大小，如果文件不存在，返回0
	 * 
	 * 支持URL文件或本地文件，自动识别
	 * 
	 * @param targetFileUrl
	 * @return
	 */
	public abstract long getFileSize(String targetFileUrl);
	
	/**
	 * 删除文件夹或文件
	 * 
	 * 如果传入的是文件，则删除文件；传入文件夹，删除文件夹
	 * 
	 * 只能删除上传目录下的文件
	 * 
	 * @param targetFileUrl
	 * @return
	 */
	public abstract UploadResult delFile(String targetFileUrl);
	
	/**
	 * 移动文件夹或文件
	 * 
	 * @param srcFileUrl
	 * @param destFileUrl
	 * @return
	 */
	public abstract UploadResult moveFile(String srcFileUrl, String destFileUrl);
	
	/**
	 * 
	 * 
	 * @param pluginContext
	 * @param uploadFile
	 * @return
	 */
	public abstract UploadResult uploadFile(PluginContext pluginContext, UploadFile uploadFile);


	/**
	 * 对指定的图片进行裁剪图片
	 * 
	 * @param srcFileUrl	上传的原始尺寸图片地址
	 * @param topStart	上面从多少位置开始（top 与 left确定一个开始点）
	 * @param leftStart	左边从多少位置开始
	 * @param cutWidth	从开始点，裁的宽度
	 * @param cutHeight	从开始点，裁的高度
	 * @param pluginContext
	 * @param imageParam
	 * @return
	 */
	public abstract UploadResult cropImage(String srcFileUrl, int topStart, int leftStart, int cutWidth, int cutHeight, PluginContext pluginContext, UploadFile uploadFile);
	
	
	public abstract ImageInfo getImageFile(String targetFileUrl);
	
	public abstract  File getFile(String targetFileUrl) throws Exception;
	
	
	
	
}
