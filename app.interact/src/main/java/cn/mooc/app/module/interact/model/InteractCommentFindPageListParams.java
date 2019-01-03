package cn.mooc.app.module.interact.model;

public class InteractCommentFindPageListParams {
	private String ftype;
	private Integer fid;
	private Integer sourceId;
	private Integer[] status;
	private Integer[] siteId;
	private String content;
	private String[] ftypes;
	private Long userId;
	
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public Integer[] getStatus() {
		return status;
	}
	public void setStatus(Integer[] status) {
		this.status = status;
	}
	public Integer[] getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer[] siteId) {
		this.siteId = siteId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getFtypes() {
		return ftypes;
	}
	public void setFtypes(String[] ftypes) {
		this.ftypes = ftypes;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
