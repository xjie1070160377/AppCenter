package cn.mooc.app.module.push.service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushMsgService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ConnectionFactory connectionFactory;
	private Connection connection;
	
	/**
	 * @param broker tcp://192.168.1.200:61616
	 */
	public PushMsgService(String broker){
		
		connectionFactory = new ActiveMQConnectionFactory(broker);
		try {
			this.createConnection();
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("PushMsgService初始化异常",e);
		}
	}
	
	public PushMsgService(String broker, String appKey, String appSecret){
		connectionFactory = new ActiveMQConnectionFactory(appKey, appSecret, broker);
		try {
			this.createConnection();
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("PushMsgService初始化异常",e);
		}
	}
	
	private void createConnection() throws Exception{
		connection = connectionFactory.createConnection();
		connection.start();
	}
	
	
	public void msgSendByExpireHours(String group, String content, int expireHours){
		int liveDays = 1000 * 60 * 60 * expireHours;
		this.msgSendTopic(group, content, liveDays);
	}
	
	public void msgSendByExpireMinute(String group, String content, int expireMinute){
		int liveDays = 1000 * 60 * expireMinute;
		this.msgSendTopic(group, content, liveDays);
	}
	
	/**
	 * @param broker
	 * @param topic
	 * @param appKey
	 * @param appSecret
	 * @param content
	 * @param timeToLive one Day to Expired = 1000 * 60 * 60 * 24 * 1;
	 */
	public void msgSendTopic(String topic, String content, int timeToLive) {

		try {		
			
			//自动签收事务
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createTopic(topic);
			MessageProducer producer = session.createProducer(destination);
			//设置持久化消息
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			//设置消息过期时间，单位毫秒
			if (timeToLive > 0) {
				producer.setTimeToLive(timeToLive);
			}
			TextMessage message = session.createTextMessage();
			message.setText(content);
			
			//发出消息   
			producer.send(message);
					
			session.commit();
			session.close();
			
		} catch (Exception e) {
			logger.error("PushMsgService.msgSend 初始化异常",e);
		}
		

	}
	
	public void msgSendQueue(String queue, String content, int timeToLive) {

		try {		
			
			//自动签收事务
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue(queue);
			MessageProducer producer = session.createProducer(destination);
			//设置持久化消息
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			//设置消息过期时间，单位毫秒
			if (timeToLive > 0) {
				producer.setTimeToLive(timeToLive);
			}
			TextMessage message = session.createTextMessage();
			message.setText(content);
			
			//发出消息   
			producer.send(message);
					
			session.commit();
			session.close();
			
		} catch (Exception e) {
			logger.error("PushMsgService.msgSend 初始化异常",e);
		}
		

	}
	
	public void createQueue(String queueName) throws Exception{

		//自动签收事务
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		
		Destination destination = session.createQueue(queueName);
		MessageProducer producer = session.createProducer(destination);
				
		session.commit();
		session.close();
		
	}
	
	public void createTopic(String topicName) throws JMSException{
		//自动签收事务
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic(topicName);
		MessageProducer producer = session.createProducer(topic);
		session.commit();
		session.close();
	}
	
	
	
	public void stop() throws Exception{
		connection.close();
	}
	
}
