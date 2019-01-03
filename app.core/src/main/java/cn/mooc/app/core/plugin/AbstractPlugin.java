package cn.mooc.app.core.plugin;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mooc.app.core.plugin.context.PluginContext;
import cn.mooc.app.core.plugin.manager.PluginsRegister;
import cn.mooc.app.core.utils.Encodes;
import cn.mooc.app.core.utils.JsonUtil;

public abstract class AbstractPlugin implements IPlugin {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected PluginInfo pluginInfo;
	
	private String logoBase64;
	
	@Override
	public String getPluginId() {
		return pluginInfo.getId();
	}

	@Override
	public PluginInfo getPluginInfo() {
		return pluginInfo;
	}
	
	@Override
	public String getPluginLogo(){
		
		if(StringUtils.isEmpty(logoBase64)){
			//只加载一次
			File logo = this.pluginInfo.getPluginFile(this.pluginInfo.getLogo());
			logoBase64 = Encodes.encodeImgageToBase64(logo);
			logoBase64 = "data:image/png;base64," + logoBase64;
		}
		
		return logoBase64;
		
	}

	@Override
	public void loadConfig(PluginInfo pluginInfo) {
		this.pluginInfo = pluginInfo;
		
	}

	@Override
	public String getPluginType() {
		return pluginInfo.getPluginType();
	}

	@Override
	public void start(PluginContext context) throws Exception {
		if(!this.checkCanStart()){
			throw new Exception("当前插件不能被启动");
		}
		
		if(this.pluginInfo.getStatus() == PluginStatus.Disable){
			this.pluginInfo.setStatus(PluginStatus.Enable);
		}
		
		JsonUtil.exportToFile(this.pluginInfo.getConfigFile(), pluginInfo);
		
	}

	@Override
	public void stop(PluginContext context) throws Exception {
		if(!this.checkCanStop()){
			throw new Exception("当前插件不能被停止");
		}
		
		if(this.pluginInfo.getStatus() == PluginStatus.Enable){
			this.pluginInfo.setStatus(PluginStatus.Disable);
		}
		
		JsonUtil.exportToFile(this.pluginInfo.getConfigFile(), pluginInfo);
		
	}
	
	@Override
	public void initWork(PluginContext context) throws Exception{
		
	}

	@Override
	public void refresh(PluginContext context) {
		//
		
	}

	@Override
	public void install(PluginContext context) {
		//
		
	}

	@Override
	public void uninstall(PluginContext context) {
		//
		
	}

	/**
	 * 启用插件前，调用该方法进行验证
	 * 
	 * @return
	 */
	public abstract boolean checkCanStart();
	
	/**
	 * 当前插件是否可以停止
	 * 
	 * @return
	 */
	public abstract boolean checkCanStop();
	
	/**
	 * @param pluginRegister
	 */
	public void registry(PluginsRegister pluginsRegister){
		
	}
	
	
}
