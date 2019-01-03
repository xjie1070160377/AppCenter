package cn.mooc.app.module.push;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.Gson;

public class PushMsgUtils {

	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	public static <T> T fromJson(String json,Class<T> objClass) {
		Gson gson = new Gson();
		return gson.fromJson(json, objClass);
	}
	
	
	public static NudtPushMessage createNewsMsg(int megtype, String title, String desc, int articleId, int showtype) {
		
		NewsMsg newsMsg = new NewsMsg();
        newsMsg.setArticleId(articleId);
        
        String msgBody = PushMsgUtils.toJson(newsMsg);
        
        NudtPushMessage nudtPushMessage = new NudtPushMessage();
        nudtPushMessage.setMsgType(megtype);
        nudtPushMessage.setMsgTitle(title);
        nudtPushMessage.setMsgDesc(desc);
        nudtPushMessage.setMsgBody(msgBody);
        nudtPushMessage.setShowtype(showtype);
        
        
        return nudtPushMessage;
	}
	
	public static NudtPushMessage createWebLinkMsg(int megtype, String title, String desc, String url, int showtype){
		
		WebLinkMsg webLinkMsg = new WebLinkMsg();
        webLinkMsg.setWebUrl(url);
        
        String msgBody = PushMsgUtils.toJson(webLinkMsg);
        
        NudtPushMessage nudtPushMessage = new NudtPushMessage();
        nudtPushMessage.setMsgType(megtype);
        nudtPushMessage.setMsgTitle(title);
        nudtPushMessage.setMsgDesc(desc);
        nudtPushMessage.setMsgBody(msgBody);
        nudtPushMessage.setShowtype(showtype);
        
        return nudtPushMessage;
	}
	
	public static NudtPushMessage createServInfoMsg(String title, String desc, int serviceId, String serviceName, int msgId) {
		
		ServInfoMsg servInfoMsg = new ServInfoMsg();
		servInfoMsg.setSpId(serviceId);
		servInfoMsg.setSpName(serviceName);
		servInfoMsg.setMsgId(msgId);
        
        String msgBody = PushMsgUtils.toJson(servInfoMsg);
        
        NudtPushMessage nudtPushMessage = new NudtPushMessage();
        nudtPushMessage.setMsgType(10);
        nudtPushMessage.setMsgTitle(title);
        nudtPushMessage.setMsgDesc(desc);
        nudtPushMessage.setMsgBody(msgBody);
        
        
        return nudtPushMessage;
	}
	
	public static NudtPushMessage createJustNoticeMsg(){

        NudtPushMessage nudtPushMessage = new NudtPushMessage();
        nudtPushMessage.setMsgType(-1);
        nudtPushMessage.setMsgTitle("消息提醒");
        nudtPushMessage.setMsgBody("您有新的消息");
        
        return nudtPushMessage;
	}
	
	public static String createNewsMsgJson(int msgtype, String title, String desc, int articleId, int showtype) {
		
		return PushMsgUtils.toJson(createNewsMsg(msgtype, title, desc, articleId, showtype));
		
	}
	
	public static String createWebLinkMsgJson(int msgtype, String title, String desc, String url, int showtype) {
				
		return PushMsgUtils.toJson(createWebLinkMsg(msgtype, title, desc, url, showtype));
		
	}
	
	public static String createServInfoMsgJson(String title, String desc, int serviceId, String serviceName) {
		
		return createServInfoMsgJson(title, desc, serviceId, serviceName, 0);
		
	}
	
	public static String createServInfoMsgJson(String title, String desc, int serviceId, String serviceName, int msgId) {
		
		return PushMsgUtils.toJson(createServInfoMsg(title, desc, serviceId, serviceName, msgId));
		
	}
	
	public static String createJustNoticeMsgJson() {
		
		return PushMsgUtils.toJson(createJustNoticeMsg());
		
	}
	

	public static void msgSend(String appKey, String appSecret, String content){
		msgSend("tcp://mapi.nudt.edu.cn:8001", appKey, appSecret, content);
	}
	
	public static void msgSend(String broker, String appKey, String appSecret, String content){
		msgSend(broker, "RedMoocPush", appKey, appSecret, content);
	}
	
	public static void msgSend(String broker, String group, String appKey, String appSecret, String content){
		msgSend(broker, group, 2, "Nudt_Sender", appKey, appSecret, content);
	}
	

	
	public static void msgSend(String broker ,String topic,int qos,String clientId, String appKey, String appSecret, String content) {
		MemoryPersistence persistence = new MemoryPersistence();
		
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(appKey);
            connOpts.setPassword(appSecret.toCharArray());
            
            //CleanSession 为false，表示接收离线消息
            connOpts.setCleanSession(false);
            sampleClient.connect(connOpts);

            System.out.println("Publishing message: "+ content);
            MqttMessage message = new MqttMessage(content.getBytes("UTF-8"));
            message.setQos(qos);
            sampleClient.publish(topic, message);
            //System.out.println("Message published");
            sampleClient.disconnect();
        } catch(Exception me) {
            me.printStackTrace();
        }
        
	}

}
