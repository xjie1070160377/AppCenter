package cn.mooc.app.core.model;

import java.io.Serializable;

public class ImageInfo implements Serializable {

	private static final long serialVersionUID = -1508965511298325372L;
	
	private int width;
	private int height;
	
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
