package cn.mooc.app.module.interact.model;

public class SnsOpLogEchartsData {
	private String name;
	private Integer[] data;
	
	public SnsOpLogEchartsData(String name, Integer[] data){
		this.setName(name);
		this.setData(data);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer[] getData() {
		return data;
	}
	public void setData(Integer[] data) {
		this.data = data;
	}
}
