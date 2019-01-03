package cn.mooc.app.module.cms.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.entity.SysUserEntity;

@Service
public class UserTypeUtil {

	@Value("${app.anonymous.uid}")
	private long anonymous_uid = 3;
	
	public static UserTypeUtil typeutil;
	
	@PostConstruct
    public void init() {  
		typeutil = this;  
		typeutil.anonymous_uid = this.anonymous_uid;
    }  
	
	public static boolean outUser(int userType){
		if(userType < 10){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean anonymousUser(long userId){
		if(typeutil.anonymous_uid == userId){
			return true;
		}
		return false;
	}
}
