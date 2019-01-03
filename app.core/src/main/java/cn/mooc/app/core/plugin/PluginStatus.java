package cn.mooc.app.core.plugin;

public enum PluginStatus {

	/**
	 * 禁用
	 */
	Disable(0),
	/**
	 * 启用
	 */
	Enable(1);
	
	
	private int status = 0;
	
	private PluginStatus(int status){
		this.status = status;
	}
	
	public int getStatusVal(){
		return this.status;
	}
	
	
}
