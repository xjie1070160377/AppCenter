package cn.mooc.app.module.cms.support;

import org.apache.commons.lang3.StringUtils;

/**
 * UploadUtils
 * 
 * @author csmooc
 * 
 */
public class UploadUtils {
	/**
	 * 快速上传文件夹
	 */
	public static final String QUICK_UPLOAD = "public";

	public static final String THUMBNAIL = "_min";

	public static String getUrl(Integer siteId, String type, String extension) {
		StringBuilder name = new StringBuilder();
		name.append('/').append(siteId);
		name.append('/').append(type);
		name.append('/').append(QUICK_UPLOAD);
		name.append(Uploader.randomPathname(extension));
		return name.toString();
	}

	public static String getThumbnailPath(String path) {
		if (StringUtils.isBlank(path)) {
			return path;
		}
		int index = path.lastIndexOf('.');
		if (index != -1) {
			return path.substring(0, index) + THUMBNAIL + path.substring(index);
		} else {
			return path;
		}
	}
}
