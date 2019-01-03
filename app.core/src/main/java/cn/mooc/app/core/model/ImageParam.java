package cn.mooc.app.core.model;

import java.util.ArrayList;
import java.util.List;

import cn.mooc.app.core.enums.ScaleSizeType;

public class ImageParam {

	/**
	 * 压缩的宽度
	 */
	private Integer width;
	
	/**
	 * 压缩的高度
	 */
	private Integer height;
		
	/**
	 * 是否压缩
	 */
	private Boolean scale;
	
	/**
	 * 压缩大小的方式
	 * 0：自动按最小的值压缩，1：按最大的值压缩
	 */
	private ScaleSizeType scaleSizeType;
	
	/**
	 * 是否裁剪完后，强制压缩
	 */
	private boolean scaleAfterCrop;
	
	/**
	 * 是否拉伸
	 */
	private Boolean stretch;
	
	/**
	 * 是否加水印
	 */
	private Boolean watermark;
	
	/**
	 * 是否自动生成缩略图
	 */
	private Boolean thumbnail;
	
	/**
	 * 缩略图宽度
	 */
	private Integer thumbnailWidth;
	
	/**
	 * 缩略图高度
	 */
	private Integer thumbnailHeight;
	
	/**
	 * 更多缩略图尺寸，生成时，文件名中包含大小
	 */
	private List<ThumbnailSize> extThumbnailSizes = new ArrayList<ThumbnailSize>();
	
	/**
	 * 保留原图
	 */
	private Boolean original;
	
	private String uploadDir;
	
	private String categoryDir;
	
	/**
	 * 指定生成后的文件格式，自动转换
	 */
	private String toFileExt;
	
	/**
	 * <pre>
	 * 生成多尺寸缩略图的文件名规则：
	 * 规则：_{0}x{1}  如文件 xxxxxx.png 则 xxxxxx_100x100.png
	 * 规则：_{0}  如文件 xxxxxx.png 则 xxxxxx_100.png
	 * </pre>
	 */
	private String sizeFileNameFmt = "_{0}x{1}";
	
	public void addThumbnailSize(Integer width, Integer height) {
		this.extThumbnailSizes.add(new ThumbnailSize(width == null ? 0 : width, height == null ? 0 : height));
	}
	
	public void addThumbnailSize(ThumbnailSize thumbnailSize) {
		this.extThumbnailSizes.add(thumbnailSize);
	}

	public Integer getWidth() {
		return width == null ? 0 : width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height == null ? 0 : height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Boolean getScale() {
		return scale == null ? false : scale;
	}

	public void setScale(Boolean scale) {
		this.scale = scale;
	}

	public Boolean getStretch() {
		return stretch == null ? false : stretch;
	}

	public void setStretch(Boolean stretch) {
		this.stretch = stretch;
	}

	public Boolean getWatermark() {
		return watermark == null ? false : watermark;
	}

	public void setWatermark(Boolean watermark) {
		this.watermark = watermark;
	}

	public Boolean getThumbnail() {
		return thumbnail  == null ? false :  thumbnail;
	}

	public void setThumbnail(Boolean thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Integer getThumbnailWidth() {
		return thumbnailWidth == null ? 0 : thumbnailWidth;
	}

	public void setThumbnailWidth(Integer thumbnailWidth) {
		this.thumbnailWidth = thumbnailWidth;
	}

	public Integer getThumbnailHeight() {
		return thumbnailHeight == null ? 0 : thumbnailHeight;
	}

	public void setThumbnailHeight(Integer thumbnailHeight) {
		this.thumbnailHeight = thumbnailHeight;
	}

	public Boolean getOriginal() {
		return original == null ? false : original;
	}

	public void setOriginal(Boolean original) {
		this.original = original;
	}

	public String getCategoryDir() {
		return categoryDir;
	}

	public void setCategoryDir(String categoryDir) {
		this.categoryDir = categoryDir;
	}

	public List<ThumbnailSize> getExtThumbnailSizes() {
		return extThumbnailSizes;
	}

	public void setExtThumbnailSizes(List<ThumbnailSize> extThumbnailSizes) {
		this.extThumbnailSizes = extThumbnailSizes;
	}


	public String getToFileExt() {
		return toFileExt;
	}

	public void setToFileExt(String toFileExt) {
		this.toFileExt = toFileExt;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getSizeFileNameFmt() {
		return sizeFileNameFmt;
	}

	public void setSizeFileNameFmt(String sizeFileNameFmt) {
		this.sizeFileNameFmt = sizeFileNameFmt;
	}

	public boolean isScaleAfterCrop() {
		return scaleAfterCrop;
	}

	public void setScaleAfterCrop(boolean scaleAfterCrop) {
		this.scaleAfterCrop = scaleAfterCrop;
	}

	public ScaleSizeType getScaleSizeType() {
		return scaleSizeType;
	}

	public void setScaleSizeType(ScaleSizeType scaleSizeType) {
		this.scaleSizeType = scaleSizeType;
	}



	
}
