package cn.mooc.app.module.api.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.mooc.app.module.api.extend.MobileTokenExtends;

@Service
public class MobileSyncTokenService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private List<MobileTokenExtends> tokenExtends = new ArrayList<MobileTokenExtends>();
	
	public void registry(MobileTokenExtends mobileTokenExtends){
		//logger.debug("注册扩展：{}", mobileTokenExtends);
		this.tokenExtends.add(mobileTokenExtends);
		
	}

	public List<MobileTokenExtends> getTokenExtends() {
		return tokenExtends;
	}
	
	
	
}
