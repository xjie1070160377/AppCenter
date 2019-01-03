package cn.mooc.app.module.service.model;

public class MobileServMsgDetail implements Comparable<MobileServMsgDetail>{
	private String title;
	private String headImage;
	private String intro;
	private Integer infoid;
	private Integer index;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Integer getInfoid() {
		return infoid;
	}
	public void setInfoid(Integer infoid) {
		this.infoid = infoid;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public int compareTo(MobileServMsgDetail o) {
		return this.getIndex().compareTo(o.getIndex());  
	}
}
