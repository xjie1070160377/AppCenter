package cn.mooc.app.module.push;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;
import javapns.org.json.JSONException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.push.data.entity.IosMessageError;
import cn.mooc.app.module.push.data.entity.IosToken;
import cn.mooc.app.module.push.service.IosMessageErrorService;
import cn.mooc.app.module.push.service.IosTokenService;




@Component
public class NotificationEngine  {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // 10 个线程在后台发送通知
    public static final int EXCE_THREAD_POOL_SIZE = 10;
    
    @Value("${ios.path}")
	private String path;
	@Value("${ios.password}")
	private String password;
	@Value("${ios.pushmessage}")
	private Boolean pushable;
	@Value("${ios.pushmodel}")
	private Boolean pushmodel;
	@Autowired
	private IosTokenService service;
	@Autowired
	private IosMessageErrorService errorService;

    private void shutdownEngine(){
    	logger.info("------------shutdownEngine-----------------------------");
        // 关闭 ExecutorService
        if(!exec.isShutdown()){
            exec.shutdown();
        }
    }
    
    @PostConstruct
    public void init(){
        // 注册 jvm 关闭时操作
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run() {
                NotificationEngine.this.shutdownEngine();
            }
        });
    }
    
    @PreDestroy
    public void destroy(){
        Thread t = new Thread(){
            public void run() {
                NotificationEngine.this.shutdownEngine();
            }
        };
        t.start();
    }
    
    private ExecutorService exec = Executors.newFixedThreadPool(EXCE_THREAD_POOL_SIZE);
    
    private FutureTask<List<PushedNotification>> doSendNotificationList(final String payload, final String message, final int badge, final String sound, final String keystore, final String password, final boolean production, final List<String> tokens){    
        // 带返回结果发送动作
        FutureTask<List<PushedNotification>> future = new FutureTask<List<PushedNotification>>(
                new Callable<List<PushedNotification>>() {
                    public List<PushedNotification> call() throws JSONException {
                        List<PushedNotification> notifications = null;
                        try {
                        	PushNotificationPayload payLoad = PushNotificationPayload.fromJSON("{aps:{message:"+message+"}}");
                        	String title = payload.length()> 35? payload.substring(0,35)+"......":payload;
                			payLoad.addAlert(title); // 消息内容
                			payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
                			payLoad.addSound(sound); // 铃音 默认
                			notifications = Push.payload(payLoad, keystore, password, production, tokens);
                        } catch (CommunicationException e) {
                            e.printStackTrace();
                        } catch (KeystoreException e) {
                            e.printStackTrace();
                        }
                        return notifications;
                    }
                });
        // 提交到执行框架执行
        exec.submit(future);
        return future;
    }
    
    private void sendNotificationList(final String payload, final String message, final int badge, final String sound, final String keystore, final String password, final boolean production, final List<String> tokens){    
        FutureTask<List<PushedNotification>> futrue = doSendNotificationList(payload, message, badge,sound,keystore,password,production,tokens);
        try {
            // 阻塞至接收到返回结果。
            List<PushedNotification> list = futrue.get();
            
            Set<Long> ids = new HashSet<Long>();
//            List<IosMessageError> errors = new ArrayList<IosMessageError>();
            
            for (PushedNotification notification : list) {
                if (notification.isSuccessful()) {
                        /* Apple accepted the notification and should deliver it */  
                	logger.info("Push notification sent successfully to: " + notification.getDevice().getToken());
                        /* Still need to query the Feedback Service regularly */  
                } else {
                        String invalidToken = notification.getDevice().getToken();
                        List<IosToken> ts = service.findByToken(invalidToken);
                        if(ts != null && ts.size() > 0){
	                        for(IosToken iost : ts){
	                        	ids.add(iost.getId());
	                        }
                        }
                }
            }
            if(ids.size() > 0){
            	Long[] a = new Long[ids.size()];
            	ids.toArray(a);
            	service.delete(a);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
    }
    
    private void payload(final String payload, final String message, final int badge, final String sound, final String keystore, final String password, final boolean production, final Set<String> tokens){    
    	int pos = 0;
    	Object[] args = tokens.toArray();
    	while(tokens.size()-1 >= pos){
    		sendNotificationList(payload, message, badge, sound, keystore, password, production, copyWith(args, pos, pos+200));
    		pos = pos+200;
    	}
    }
    
    private List<String> copyWith(Object[] args, int start, int end){
    	List<String> list = new ArrayList<String>();
    	end = args.length-1 > end ? end : args.length-1;
    	for(int i = start; i <= end; i++){
    		list.add(StringUtil.strnull(args[i]));
    	}
    	return list;
    }
    
    public void sendpush(String msgtype, String encodeType, String des, String value, Integer siteId, String showType) {
		if(pushable){
			List<IosToken> tokens = service.findIosList();
			Set<String> ts = new HashSet<String>();
			if(tokens != null && tokens.size() > 0){
				for(IosToken token : tokens){
					ts.add(token.getToken());
				}
				payload(des, convertString(msgtype, encodeType, value, showType), 1, "default", path, password, pushmodel, ts);
			}
		}
	}
    
    public void sendpush(String msgtype, String encodeType, String des, String value, Integer siteId, String showType, String tokens) {
		if(pushable){
			Set<String> ts = new HashSet<String>();
			ts.add(tokens);
			payload(des, convertString(msgtype, encodeType, value, showType), 1, "default", path, password, pushmodel, ts);
		}
	}
	
	private static String convertString(String msgtype, String encodeType, String value, String showType){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("msgType", msgtype);
		Map<String, String> p = new HashMap<String, String>();
		if(msgtype.equals("0")){
			p.put("webUrl", value);
		}else{
			p.put("articleId", value);
		}
		params.put("showtype", showType);
		params.put("msgBody", JsonUtil.toJson(p));
		return JsonUtil.toJson(params);
	}
    
    public static void main(String[] args) {
//    	String keystore = "F:/workspace-sj/moocms_school/WebRoot/WEB-INF/conf/aps_developer_identity.p12";
//        String password = "Push2016";
//         
//        String deviceToken = "1d746ade3fe9bbfc5821202e653f6a8280601adfe12f81b2a02ae02dae6f4386";
//        String message = "单点推送测试，单独给您手机发的信息，骚扰模式开始";
//        NotificationEngine engine = new NotificationEngine();
//        List<String> s = new ArrayList<String>();
//        s.add(deviceToken);
//        engine.sendNotificationList("{}", message, 1, "default", keystore, password, true, s);
//        engine.destroy();
    }
}