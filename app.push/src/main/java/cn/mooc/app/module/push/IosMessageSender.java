package cn.mooc.app.module.push;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.module.push.data.entity.IosMessageError;
import cn.mooc.app.module.push.data.entity.IosMessagePedding;
import cn.mooc.app.module.push.data.entity.IosToken;
import cn.mooc.app.module.push.service.IosMessageErrorService;
import cn.mooc.app.module.push.service.IosMessagePeddingService;
import cn.mooc.app.module.push.service.IosTokenService;

@Component
public class IosMessageSender {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/************************************************
	 * 测试推送服务器地址：gateway.sandbox.push.apple.com /2195
	 * 产品推送服务器地址：gateway.push.apple.com / 2195
	 * 
	 * 需要javaPNS_2.2.jar包
	 ***************************************************/
	/**
	 * 
	 * 这是一个比较简单的推送方法，
	 * 
	 * apple的推送方法
	 * 
	 * @param tokens
	 *            iphone手机获取的token
	 * 
	 * @param path
	 *            这里是一个.p12格式的文件路径，需要去apple官网申请一个
	 * 
	 * @param password
	 *            p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
	 * 
	 * @param message
	 *            推送消息的内容
	 * 
	 * @param count
	 *            应用图标上小红圈上的数值
	 * 
	 * @param sendCount
	 *            单发还是群发 true：单发 false：群发
	 */
	public void sendpush(Set<String> tokens, String path, String password,
			String message, String title) {

		try {
			if(tokens == null || tokens.size() == 0){
				return;
			}
			// message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}
			//PushNotificationPayload payLoad = PushNotificationPayload.alert(message);
					//.fromJSON(message);
			PushNotificationPayload payLoad = PushNotificationPayload.fromJSON("{aps:{message:"+message+"}}");
			title = title.length()> 35? title.substring(0,35)+"......":title;
			payLoad.addAlert(title); // 消息内容

			payLoad.addBadge(1); // iphone应用图标上小红圈上的数值

			payLoad.addSound("default"); // 铃音 默认

			PushNotificationManager pushManager = new PushNotificationManager();

			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
							path, password, this.production));

			List<PushedNotification> notifications = new ArrayList<PushedNotification>();

			// 发送push消息


			logger.debug("--------------------------apple 推送 单-------");
			
			List<Device> device = new ArrayList<Device>();

			for (String token : tokens) {
				device.add(new BasicDevice(token));
			}
			notifications = pushManager.sendNotifications(payLoad, device);


			List<PushedNotification> failedNotifications = PushedNotification
					.findFailedNotifications(notifications);

			List<PushedNotification> successfulNotifications = PushedNotification
					.findSuccessfulNotifications(notifications);

//			PushedNotification failedNotification = failedNotifications.get(0);
//			failedNotification.getDevice().getToken();
//			failedNotification.getException();
			int failed = failedNotifications.size();
			if(failed > 0){
				Date d = new Date();
				for(PushedNotification failedNotification : failedNotifications){
					IosMessagePedding mp = new IosMessagePedding();
					mp.setMessage(message);
					mp.setTitle(title);
					mp.setToken(failedNotification.getDevice().getToken());
					mp.setTravel(0);
					mp.setCreateTime(d);
					messageService.save(mp);
					
					IosMessageError ems = new IosMessageError();
					ems.setCreateTime(new Date());
					ems.setErrorMsg(getString(failedNotification.getException().getMessage(),2000));
					ems.setMessage(message);
					ems.setTitle(title);
					ems.setToken(failedNotification.getDevice().getToken());
					errorService.save(ems);
				}

			}

			int successful = successfulNotifications.size();

			if (successful > 0 && failed == 0) {

				logger.info("-----All notifications pushed 成功 ("
						+ successfulNotifications.size() + "):");

			} else if (successful == 0 && failed > 0) {
				logger.info("-----All notifications 失败 ("
						+ failedNotifications.size() + "):");

			} else if (successful == 0 && failed == 0) {

				logger.info("No notifications could be sent, probably because of a critical error");

			} else {

				logger.info("------Some notifications 失败 ("
						+ failedNotifications.size() + "):");

				logger.info("------Others 成功 (" + successfulNotifications.size()
						+ "):");

			}

			// pushManager.stopConnection();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	
	public static Integer push_flag = 0;
	public static final boolean production = true;
	
	public synchronized void sendpush() {
		try {
			this.push_flag = 1;
			PushNotificationManager pushManager = new PushNotificationManager();

			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
							path, password, this.production));
			
			List<IosMessagePedding> messages = messageService.findByCount(5);
			for(IosMessagePedding message : messages){
				PushNotificationPayload payLoad = PushNotificationPayload.fromJSON("{aps:{message:"+message.getMessage()+"}}");
				payLoad.addAlert(message.getTitle());
				payLoad.addBadge(1);
				payLoad.addSound("default");
				
				List<PushedNotification> notifications = new ArrayList<PushedNotification>();

				// 发送push消息


				logger.debug("--------------------------apple 推送 单-------");
				
				Device device = new BasicDevice();
				device.setToken(message.getToken());

				PushedNotification notification = pushManager.sendNotification(
						device, payLoad, false);

				if(notification.isSuccessful()){
					messageService.delete(message.getId());
				}else{
					int count = message.getTravel()+1;
					message.setTravel(count);
					messageService.save(message);
					
					IosMessageError ems = new IosMessageError();
					ems.setCreateTime(new Date());
					ems.setErrorMsg(getString(notification.getException().getMessage(),2000));
					ems.setMessage(message.getMessage());
					ems.setTitle(message.getTitle());
					ems.setToken(message.getToken());
					errorService.save(ems);
					
//					if(count == 5){
//						List<Integer> ids = new ArrayList<Integer>();
//						List<Integer> siteIds = new ArrayList<Integer>();
//						List<IosToken> iosLs = service.findByToken(message.getToken());
//						for(IosToken b : iosLs){
//							b.setFailCount(count);
//							service.save(b);
//							ids.add(b.getId());
//							if(!siteIds.contains(b.getSiteId())){
//								siteIds.add(b.getSiteId());
//							}
//						}
//						for(Integer siteId : siteIds){
//							List<IosToken> iosList = redisService.getIosTokens(siteId);
//							List<IosToken> is = new ArrayList<IosToken>();
//							for(IosToken t : iosList){
//								if(!ids.contains(t.getId())){
//									is.add(t);
//								}
//							}
//							redisService.setIosTokens(siteId, is);
//						}
//					}
				}
			}
			pushManager.stopConnection();
			this.push_flag = 0;
		} catch (Exception e) {
			this.push_flag = 0;
			e.printStackTrace();
		}
	}
	
	private String getString(String str, int length){
		if(StringUtils.isEmpty(str)){
			return "";
		}
		if(str.length() < length){
			return str;
		}else{
			return str.substring(0, length);
		}
	}
//			// 发送push消息
//
//			if (sendCount) {
//
//				logger.debug("--------------------------apple 推送 单-------");
//
//				Device device = new BasicDevice();
//
//				device.setToken(tokens.get(0));
//
//				PushedNotification notification = pushManager.sendNotification(
//						device, payLoad, true);
//
//				notifications.add(notification);
//
//			} else {
//
//				logger.debug("--------------------------apple 推送 群-------");
//
//				List<Device> device = new ArrayList<Device>();
//
//				for (String token : tokens) {
//
//					device.add(new BasicDevice(token));
//
//				}
//
//				notifications = pushManager.sendNotifications(payLoad, device);
//
//			}
//
	
	public void sendpush(String msgtype, String encodeType, String des, String value, Integer siteId, String showType) {
		/*if(StringUtils.isEmpty(pushable)){
			pushable = "false";
		}*/
		if(pushable){
			tokens = service.findIosList();
			Set<String> ts = new HashSet<String>();
			if(tokens != null && tokens.size() > 0){
				for(IosToken token : tokens){
					ts.add(token.getToken());
				}
				sendpush(ts, path, password, convertString(msgtype, encodeType, value, showType), des);
			}
		}
	}
	
	public static String convertString(String msgtype, String encodeType, String value, String showType){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("msgType", msgtype);
		Map<String, String> p = new HashMap<String, String>();
		if(msgtype.equals("0")){
			p.put("webUrl", value);
		}else if(msgtype.equals("1")){
			p.put("articleId", value);
			p.put("showType", showType);
		}
		params.put("msgBody", JsonUtil.toJson(p));
//		System.out.println(mapper.toJson(params));
		return JsonUtil.toJson(params);
	}
	
	
	
	private List<IosToken> tokens;
	@Value("${ios.path}")
	private String path;
	@Value("${ios.password}")
	private String password;
	@Value("${ios.pushmessage}")
	private Boolean pushable;
	@Autowired
	private IosTokenService service;
	
	@Autowired
	private IosMessagePeddingService messageService;
	@Autowired
	private IosMessageErrorService errorService;
	
	
	public List<IosToken> getTokens() {
		return tokens;
	}

	public void setTokens(List<IosToken> tokens) {
		this.tokens = tokens;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		IosMessageSender send = new IosMessageSender();
		String token = "e4225c2fdd7e2d2caca451e38fa483bd117b0b244e6e2d320af51ed4f0e39715";
		String path = "F:/workspace-sj/moocms_school/WebRoot/WEB-INF/conf/aps_developer_identity.p12";
		String password = "Push2016";
		String message = "单点推送测试，单独给您手机发的信息，收到请回复下吴智";
//		send.sendpush(token, path, password, message);
		Set s = new HashSet<String>();
//		s.add(token);
		s.add("1d746ade3fe9bbfc5821202e653f6a8280601adfe12f81b2a02ae02dae6f4386");
		int i = 0;
		while(true){
			i++;
		send.sendpush(s, path, password, convertString("1", "0", "31123", ""), message);
		System.out.println(i+"\n");
		}

	}
}
