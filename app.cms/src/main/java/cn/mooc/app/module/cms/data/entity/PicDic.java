package cn.mooc.app.module.cms.data.entity;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_pic_dic")
public class PicDic {

	@Id
	private String _id;
	@Indexed(unique = true, dropDups = true)
	private Long pId;
	/**
	 * 常用图片库 备注信息
	 */
	private String note;	
	/**
	 * 图片路径
	 */
	private String pic;	
	/**
	 * 排序索引
	 */
	private int sortIndex;
	
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public int getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	
	
	
}



