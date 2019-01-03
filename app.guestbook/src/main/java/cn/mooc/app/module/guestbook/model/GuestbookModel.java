package cn.mooc.app.module.guestbook.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.mooc.app.module.guestbook.data.entity.Guestbook;

public class GuestbookModel {

	private String id;
	
	private Long userId;
	
	private String name;
	
	private String createTime;
	
	private String icon;
	
	private String summary;
	
	private long total;
	
	private long replys;
	
	private String state;
	
	private int ranking;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getReplys() {
		return replys;
	}

	public void setReplys(long replys) {
		this.replys = replys;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public GuestbookModel(Guestbook gb) {
		super();
		this.id = gb.get_id();
		this.userId = gb.getUserId();
		this.name = gb.getName();
		this.createTime = dateFmt(gb.getCreateTime());
		this.icon = gb.getIcon();
		this.summary = gb.getSummary();
		this.total = gb.getTotal();
		this.replys = gb.getReplys();
		this.state = gb.getState().getValue();
		this.ranking = ranking;
	}
	
	public String dateFmt(Date date){
		DateFormat dft =  new SimpleDateFormat("YYYY-MM-dd HH:mm");
		return dft.format(date);
	}
}
