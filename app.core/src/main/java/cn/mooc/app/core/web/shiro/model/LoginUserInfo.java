package cn.mooc.app.core.web.shiro.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LoginUserInfo  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2341165845251493557L;

	private long userId;

	private String userName;
	
	private String nickName;
	
	private String avatarUrl;
	
	private Map<String, Object> paramData = new HashMap<String, Object>();
	
	public <T> T getParam(String key, T def) {
		if(paramData.containsKey(key)){
			return (T) paramData.get(key);
		}
		return def;
		
	}
	
	public <T> void setParam(String key, T val) {
		paramData.put(key, val);
		
	}
	
	public LoginUserInfo(long userId,String userName){
		this.userId = userId;
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	
	
}