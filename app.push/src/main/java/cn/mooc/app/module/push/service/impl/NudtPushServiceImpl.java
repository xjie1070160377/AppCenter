package cn.mooc.app.module.push.service.impl;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mooc.app.module.push.NotificationEngine;
import cn.mooc.app.module.push.PushMsgUtils;
import cn.mooc.app.module.push.service.MsgPushService;
import cn.mooc.app.module.push.service.PushMsgService;

//@Service
public class NudtPushServiceImpl{// implements MsgPushService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Properties properties;
	
	private PushMsgService pushMsgService;
	
	@Autowired
	private NotificationEngine messageSender;
	
	@Value("${push.secretKey}")
	private String appSecret;
	
	@Value("${push.apiKey}")
	private String apiKey;
	
	@Value("${push.msgServer}")
	private String msgServer;
	
	@Value("${push.msgGroup}")
	private String msgGroup;
	
	@PostConstruct
	private void init() {
		//System.out.println("----init----------msgServer:"+msgServer+";msgGroup:"+msgGroup+";apiKey:"+apiKey+";secretKey:"+appSecret+";");
		pushMsgService = new PushMsgService(msgServer, apiKey, appSecret);
		
		
	}
	
	@PreDestroy
	public void destroy(){
		try {
			pushMsgService.stop();
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("pushMsgService.stop", e);
		}
	}
	
	//@Override
	public void pushMsgToAll(final String title, final String des, String type, final String value, final Integer siteId, final String showType) {
		/*final String apiKey = properties.getProperty("push.apiKey");
		final String secretKey = properties.getProperty("push.secretKey");
		final String msgServer = properties.getProperty("push.msgServer");
		final String msgGroup = properties.getProperty("push.msgGroup");*/
		final String msgType = type;
		new Thread() {
			@Override
			public void run() {
				try {
					// 等待主线程结束，提交事务后，再运行。
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					logger.error(null, e);
				}
				try {
					String content="";
					if(msgType.equals("0")){
						content = PushMsgUtils.createWebLinkMsgJson(Integer.parseInt(msgType), title, des, value, Integer.parseInt(showType));
					}else if(msgType.equals("1")){
						content = PushMsgUtils.createNewsMsgJson(Integer.parseInt(msgType), title, des, Integer.parseInt(value), Integer.parseInt(showType));
					}else{
						content = PushMsgUtils.createJustNoticeMsgJson();
					}
//					System.out.println("--------------msgServer:"+msgServer+";msgGroup:"+msgGroup+";apiKey:"+apiKey+";secretKey:"+appSecret+";content:"+content);
					//PushMsgUtils.msgSend(msgServer, msgGroup, apiKey, secretKey, content);
					try{
						pushMsgService.msgSendByExpireHours(msgGroup, content, 48);
						
						System.out.println("--------------------activeMq推送-----------------------------------");
					}catch(Exception ex){
						logger.error("activeMq消息推送失败", ex.getMessage());
					}
					try{
						messageSender.sendpush(msgType, "0", des, value, siteId, showType);
						//System.out.println("--------------------IOS推送:"+content+"-----------------------------------");
					}catch(Exception ex){
						logger.error("IOS消息推送失败", ex.getMessage());
					}
					
					
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		}.start();
		
	}



	//@Override
	public void msgChannelCreate(String queueName) {
		try {
			pushMsgService.createTopic(queueName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("pushMsgService createQueue error : ", e.getMessage());
		}
	}
	
	

}
