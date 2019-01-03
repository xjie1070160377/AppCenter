package cn.mooc.app.module.cms.model;

public class NodeFindListPageParams {
	private Integer[] siteId;
	private Integer parentId;
	private String treeNumber;
	private Boolean isRealNode;
	private Boolean isHidden;
	private String nodeModelNumber;
	private Integer[] p1;
	private Integer[] p2;
	private Integer[] p3;
	private Integer[] p4;
	private Integer[] p5;
	private Integer[] p6;
	private String[] nodeNumber;
	
	
	public Integer[] getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer[] siteId) {
		this.siteId = siteId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getTreeNumber() {
		return treeNumber;
	}
	public void setTreeNumber(String treeNumber) {
		this.treeNumber = treeNumber;
	}
	public Boolean getIsRealNode() {
		return isRealNode;
	}
	public void setIsRealNode(Boolean isRealNode) {
		this.isRealNode = isRealNode;
	}
	public Boolean getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	public Integer[] getP1() {
		return p1;
	}
	public void setP1(Integer[] p1) {
		this.p1 = p1;
	}
	public Integer[] getP2() {
		return p2;
	}
	public void setP2(Integer[] p2) {
		this.p2 = p2;
	}
	public Integer[] getP3() {
		return p3;
	}
	public void setP3(Integer[] p3) {
		this.p3 = p3;
	}
	public Integer[] getP4() {
		return p4;
	}
	public void setP4(Integer[] p4) {
		this.p4 = p4;
	}
	public Integer[] getP5() {
		return p5;
	}
	public void setP5(Integer[] p5) {
		this.p5 = p5;
	}
	public Integer[] getP6() {
		return p6;
	}
	public void setP6(Integer[] p6) {
		this.p6 = p6;
	}
	public String[] getNodeNumber() {
		return nodeNumber;
	}
	public void setNodeNumber(String[] nodeNumber) {
		this.nodeNumber = nodeNumber;
	}
	public String getNodeModelNumber() {
		return nodeModelNumber;
	}
	public void setNodeModelNumber(String nodeModelNumber) {
		this.nodeModelNumber = nodeModelNumber;
	}
}
