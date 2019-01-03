package cn.mooc.app.core.model;

import org.apache.commons.lang3.StringUtils;

public class SysUserInfo {

	
	private long userId;
	
	private String userName;
	
	private String nickName;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	
	/**
	 * 头像地址
	 */
	private String avatarUrl;
	
	
	/**
	 * 性别：Female：女，Male：男
	 * （保密：0, 男：1, 女：2）
	 */
	private int gender;
	
	/**
	 * 扩展字段，用户类型/用户分类；
	 * 红客系统中约定：0：校外用户，10：校内实名用户
	 */
	private int uType;
	
	
	/**
	 * 电话
	 */
	private String phone;
	
	
	/**
	 * 邮箱
	 */
	private String email;
	
	private String friendName;

	private String bankCard;
	
	public String getUName() {
		if (StringUtils.isNotEmpty(nickName)) {
			return nickName;
		}
		
		if (StringUtils.isNotEmpty(realName)) {
			return realName;
		}
		
		return userName;
	}
	
	public String getGenderName(){
		
		return gender == 1 ? "男" : gender == 2 ? "女" : "保密";
		
	}
	
	public int getUserIdInt() {
		return (int)userId;
	}
	
	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return StringUtils.isNotBlank(userName) ? userName : "";
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getNickName() {
		return StringUtils.isNotBlank(nickName) ? nickName : "";
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getRealName() {
		return StringUtils.isNotBlank(realName) ? realName : "";
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getAvatarUrl() {
		return avatarUrl;
	}
	public String getAvatarUrlFull(String prefix) {
		if (StringUtils.isNotBlank(avatarUrl) && avatarUrl.startsWith("/")) {
			return prefix + avatarUrl;
		}
		return "";
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}


	public int getGender() {
		return gender;
	}


	public void setGender(int gender) {
		this.gender = gender;
	}


	public String getPhone() {
		return StringUtils.isNotBlank(phone) ? phone : "";
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return StringUtils.isNotBlank(email) ? email : "";
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public int getuType() {
		return uType;
	}

	public void setuType(int uType) {
		this.uType = uType;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	
	
}
