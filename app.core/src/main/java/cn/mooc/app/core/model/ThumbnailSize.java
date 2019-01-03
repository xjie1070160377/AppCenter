package cn.mooc.app.core.model;

public class ThumbnailSize {

	/**
	 * 缩略图宽度
	 */
	private int width;
	
	/**
	 * 缩略图高度
	 */
	private int height;
	
	public ThumbnailSize(int width, int height){
		this.width = width;
		this.height = height;
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
	
	
	
}
