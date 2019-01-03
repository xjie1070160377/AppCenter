package cn.mooc.app.module.api.extend;

public interface MobileTokenExtends {

	/**
	 * @param uid
	 * @param name
	 * @param newToken 要写入的token
	 * @param oldToken 要删除的token
	 * @throws Exception
	 */
	public void syncUserToken(long uid, String name, String newToken, String oldToken) throws Exception;
	
}
