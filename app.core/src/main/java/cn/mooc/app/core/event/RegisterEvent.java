package cn.mooc.app.core.event;

import cn.mooc.app.core.data.entity.SysUserEntity;

public class RegisterEvent extends OprEvent {
	
	private Long introducerId;
	private SysUserEntity registerUser;

	public Long getIntroducerId() {
		return introducerId;
	}

	public void setIntroducerId(Long introducerId) {
		this.introducerId = introducerId;
	}

	public SysUserEntity getRegisterUser() {
		return registerUser;
	}

	public void setRegisterUser(SysUserEntity registerUser) {
		this.registerUser = registerUser;
	}
	
}
