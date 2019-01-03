package cn.mooc.app.module.push.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.push.NotificationEngine;
import cn.mooc.app.module.push.PushMsgUtils;
import cn.mooc.app.module.push.data.entity.IosToken;
import cn.mooc.app.module.push.service.IosTokenService;
import cn.mooc.app.module.push.service.MsgPushService;
import cn.mooc.umeng.module.push.AndroidNotification;
import cn.mooc.umeng.module.push.PushClient;
import cn.mooc.umeng.module.push.android.AndroidBroadcast;
import cn.mooc.umeng.module.push.android.AndroidUnicast;

@Service
public class UmengPushServiceImpl implements MsgPushService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Properties properties;
	@Autowired
	private WebContext webContext;
	
	@Autowired
	private NotificationEngine messageSender;
	
	@Autowired
	private IosTokenService iosTokenService;
	
	@Value("${push.expire_time}")
	private Integer expire_time;
	
	@Value("${umeng.appkey}")
	private String appkey;
	
	@Value("${umeng.appMasterSecret}")
	private String appMasterSecret;
	
	private PushClient client = new PushClient();
	
	
	
	public void pushtest(String title, String type, String showType, String infotitle, String infoId, Integer siteId , Integer flag, Long userId, String userName, String ip, String reqUrl){
		
		new Thread() {
			@Override
			public void run() {
				try{
					if(flag.intValue()==1 || flag.intValue()==3){
						String content="";
						//超链接
						if(type.equals("0")){
		//					createWebLinkMsg(title, desc, url, showtype)
							content = PushMsgUtils.createWebLinkMsgJson(Integer.parseInt(type), title, infotitle, infoId, Integer.parseInt(showType));
						}else if(type.equals("1")){
						//站内文章
							//createWebLinkMsg(title, desc, url, showtype)
							content = PushMsgUtils.createNewsMsgJson(Integer.parseInt(type), title, infotitle, Integer.parseInt(infoId), Integer.parseInt(showType));
						}else{
							//createJustNoticeMsg()
							content = PushMsgUtils.createJustNoticeMsgJson();
						}
						AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
						broadcast.setTicker("NUDT通知"); //通知栏提示文字
						broadcast.setTitle("红客消息通知"); //必填 通知标题
						broadcast.setText("您有新的消息["+DateTimeUtil.getCurrentTimeStr()+"]");//通知文字描述  
						//broadcast.goAppAfterOpen();
						broadcast.goCustomAfterOpen(content);
						broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
						// TODO Set 'production_mode' to 'false' if it's a test device. 
						// For how to register a test device, please see the developer doc.
						broadcast.setProductionMode();
						// Set customized fields
						//broadcast.setExtraField("test", "helloworld");
						if(expire_time == null){
							expire_time = 1;
						}
						Calendar c = Calendar.getInstance();
						c.setTime(new Date());
						c.add(Calendar.DATE, expire_time);
						Date d = c.getTime();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						broadcast.setExpireTime(format.format(d));
						
						
						client.send(broadcast);
						//pushMsgService.msgSendByExpireHours(msgGroup, content, 48);
						logger.info("安卓推送成功：{}", content);
						webContext.sysUserLog(LogType.PushOpr, userId, userName, "sys", "normal", "安卓推送广播：" +content, ip, reqUrl);
					}
					if(flag.intValue()==2 || flag.intValue()==3){
						messageSender.sendpush(type, "0", infotitle, infoId, siteId, showType);
						logger.info("IOS推送测试成功：{}", infotitle);
						webContext.sysUserLog(LogType.PushOpr, userId, userName, "sys", "normal", "IOS推送广播：" +infotitle, ip, reqUrl);
					}
				}catch(Exception e){
					logger.error("pushMsgService pushtest error : ", e);
				}
			}
		}.start();
		
	}
	@Override
	/**
	 * title 消息标题
	 * desc 消息内容
	 * type 
	 */
	public void pushMsgToAll(final String title, final String des, String type, final String value, final Integer siteId, final String showType,Long  userId, String userName, String ip, String reqUrl) {
		
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
					try{
						AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
						broadcast.setTicker("NUDT通知"); //通知栏提示文字
						broadcast.setTitle("红客消息通知"); //必填 通知标题
						broadcast.setText("您有新的消息["+DateTimeUtil.getCurrentTimeStr()+"]");//通知文字描述 
						broadcast.goCustomAfterOpen(content);
						broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
						broadcast.setProductionMode();
						if(expire_time == null){
							expire_time = 1;
						}
						Calendar c = Calendar.getInstance();
						c.setTime(new Date());
						c.add(Calendar.DATE, expire_time);
						Date d = c.getTime();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						broadcast.setExpireTime(format.format(d));
						client.send(broadcast);
						logger.info("安卓推送成功：{}", content);
						webContext.sysUserLog(LogType.PushOpr, userId, userName, "sys", "normal", "安卓推送广播：" +content, ip, reqUrl);
					}catch(Exception ex){
						logger.error("消息推送失败{}", ex);
					}
					try{
						messageSender.sendpush(msgType, "0", des, value, siteId, showType);
						logger.debug("IOS推送成功：{}", des);
						webContext.sysUserLog(LogType.PushOpr, userId, userName, "sys", "normal", "IOS推送广播：" +des, ip, reqUrl);
					}catch(Exception ex){
						logger.error("IOS消息推送失败", ex);
					}
				} catch (Exception e) {
					logger.error("UmengPushServiceImpl",e);
				}
			}
		}.start();
		
	}
	
	public void pushMsg(final String title, final String des, String type, final String value, final Integer siteId, final String showType, final Long[] userIds, final String iosTitle, Long userId, String userName, String ip, String reqUrl) {
		
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
					for(Long userId : userIds){
						IosToken token = iosTokenService.get(userId);
						if(token != null){
							if(!token.getIsios()){
								String content="";
								if(msgType.equals("0")){
									content = PushMsgUtils.createWebLinkMsgJson(Integer.parseInt(msgType), title, des, value, StringUtil.isNull(showType)?0:Integer.parseInt(showType));
								}else{
									content = PushMsgUtils.createNewsMsgJson(Integer.parseInt(msgType), title, des, StringUtil.isNull(value)?0:Integer.parseInt(value), StringUtil.isNull(showType)?0:Integer.parseInt(showType));
								}
								try{
									AndroidUnicast broadcast = new AndroidUnicast(appkey,appMasterSecret);
									broadcast.setDeviceToken(token.getToken());
									broadcast.setTicker("NUDT通知"); //通知栏提示文字
									broadcast.setTitle("红客消息通知"); //必填 通知标题
									broadcast.setText("您有新的消息["+DateTimeUtil.getCurrentTimeStr()+"]");//通知文字描述 
									broadcast.goCustomAfterOpen(content);
									broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
									broadcast.setProductionMode();
									if(expire_time == null){
										expire_time = 1;
									}
									Calendar c = Calendar.getInstance();
									c.setTime(new Date());
									c.add(Calendar.DATE, expire_time);
									Date d = c.getTime();
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
									broadcast.setExpireTime(format.format(d));
									client.send(broadcast);
									logger.info("安卓推送成功：{}", content);
									webContext.sysUserLog(LogType.PushOpr, userId, userName, "sys", "normal", "安卓推送【"+token.getToken()+"】：" +content, ip, reqUrl);
								}catch(Exception ex){
									logger.error("消息推送失败", ex);
								}
							}else{
								try{
									messageSender.sendpush(msgType, "0", iosTitle, value, siteId, showType, token.getToken());
									logger.debug("IOS推送成功：{}", iosTitle);
									webContext.sysUserLog(LogType.PushOpr, userId, userName, "sys", "normal", "IOS推送【"+token.getToken()+"】" +iosTitle, ip, reqUrl);
								}catch(Exception ex){
									logger.error("IOS消息推送失败", ex);
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error("UmengPushServiceImpl",e);
				}
			}
		}.start();
		
	}

	@Override
	public void msgChannelCreate(String queueName) {
//		try {
//			pushMsgService.createTopic(queueName);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("pushMsgService createQueue error : ", e.getMessage());
//		}
	}
	
	

}
