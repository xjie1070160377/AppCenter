package cn.mooc.app.core.plugin;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.mooc.app.core.plugin.support.model.PluginParam;


public class PluginInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7518995390078616081L;
	
	//@JsonIgnore
	private String id;	
	private String name = "无名插件";
	private String desc = "";
	private String logo = "logo.png";
	private String version = "1.0.0";
	private PluginStatus status = PluginStatus.Disable;
	/**
	 * Payment：支付插件，Message：消息发送插件，Express：快递，UAuth：第三方认证
	 */
	private String pluginType = "Payment";
	private List<PluginPlatform> platforms = new ArrayList<PluginPlatform>();
	private Map<String, PluginParam> pluginData = new HashMap<String, PluginParam>();
	private Map<String, Object> extendData = new HashMap<String, Object>();
	
	
	@JsonIgnore
	private Set<String> classNames = new HashSet<String>();	
	@JsonIgnore
	private File pluginDir;
	@JsonIgnore
	private File configFile;
	
	public void setData(String dataKey, String pval) {
		PluginParam val = this.getParamData(dataKey);
		val.setVal(pval);
		this.pluginData.put(dataKey, val);
	}
	
	public PluginParam getParamData(String dataKey) {
		if(!this.pluginData.containsKey(dataKey)){
			return new PluginParam("");
		}
		return this.pluginData.get(dataKey);
	}
	
	public String getData(String dataKey) {
		return this.getParamData(dataKey).getVal().toString();
	}
	
	public boolean blankData(String dataKey) {
		if(!this.pluginData.containsKey(dataKey)){
			return true;
		}
		return this.pluginData.get(dataKey)==null;
	}
	
	public int getDataInt(String key, int def){
		try{
			PluginParam val = this.getParamData(key);
			return Integer.parseInt(val.getVal().toString());
		}catch(Exception e){
			e.printStackTrace();
			return def;
		}
	}
	
	public <T>  T getExtendData(String dataKey) {
		if(!this.extendData.containsKey(dataKey)){
			return null;
		}
		return (T) this.extendData.get(dataKey);
	}
	
	public double getDataDouble(String key, double def){
		try{
			PluginParam val = this.getParamData(key);
			return Double.parseDouble(val.getVal().toString());
		}catch(Exception e){
			e.printStackTrace();
			return def;
		}
	}
	
	/**
	 * 取当前插件下的某个文件
	 * 
	 * @param pFilePath
	 * @return
	 */
	public File getPluginFile(String pFilePath) {
		return new File(pluginDir.getAbsolutePath() + File.separator + pFilePath);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public PluginStatus getStatus() {
		return status;
	}
	public void setStatus(PluginStatus status) {
		this.status = status;
	}
	public String getPluginType() {
		return pluginType;
	}
	public void setPluginType(String pluginType) {
		this.pluginType = pluginType;
	}
	public List<PluginPlatform> getPlatforms() {
		return platforms;
	}
	public void setPlatforms(List<PluginPlatform> platforms) {
		this.platforms = platforms;
	}
	public Map<String, PluginParam> getPluginData() {
		return pluginData;
	}
	public void setPluginData(Map<String, PluginParam> pluginData) {
		this.pluginData = pluginData;
	}
	public File getPluginDir() {
		return pluginDir;
	}
	public void setPluginDir(File pluginDir) {
		this.pluginDir = pluginDir;
	}
	public File getConfigFile() {
		return configFile;
	}
	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}
	public Set<String> getClassNames() {
		return classNames;
	}
	public void setClassNames(Set<String> classNames) {
		this.classNames = classNames;
	}

	public Map<String, Object> getExtendData() {
		return extendData;
	}

	public void setExtendData(Map<String, Object> extendData) {
		this.extendData = extendData;
	}


	
	
}
