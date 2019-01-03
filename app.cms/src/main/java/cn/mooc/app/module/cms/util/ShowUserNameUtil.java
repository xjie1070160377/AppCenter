package cn.mooc.app.module.cms.util;

import org.apache.commons.lang3.StringUtils;

public class ShowUserNameUtil {

	/**
	 * 根据用户的 账号 昵称 真实姓名，返回推荐显示的名称
	 * 
	 * @param userName
	 * @param nickName
	 * @param realName
	 * @return
	 */
	public static String getMUserName(String userName, String nickName, String realName) {
		if (StringUtils.isNotEmpty(nickName)) {
			return nickName;
		}
		
		if (StringUtils.isNotEmpty(realName)) {
			return realName;
		}
		
		return userName;
	}
	
	
}
