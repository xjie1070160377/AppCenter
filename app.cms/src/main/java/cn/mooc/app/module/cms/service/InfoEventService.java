package cn.mooc.app.module.cms.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoImage;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.WorkflowStep;
import cn.mooc.app.module.cms.data.entity.WorkflowStepRole;
import cn.mooc.app.module.cms.enums.CmsOprEventType;
import cn.mooc.app.module.cms.enums.PushType;
import cn.mooc.app.module.cms.event.listener.InfoEventListener;
import cn.mooc.app.module.cms.event.selector.InfoEventSelector;
import cn.mooc.app.module.push.service.MsgPushService;

@Service
public class InfoEventService implements InfoEventListener {

	@Autowired
	private EventDispatch eventDispatch;	
	@Autowired
	private MsgPushService msgPushService;
	@Autowired
	private SysDataService dataService;
	@Autowired
	private InfoService infoService;
	@Value("${push.type}")
	private String pushType;
	@Value("${push.info.title}")
	private String pushInfoTitle;
	
	
	@PostConstruct
	public void init() {
		eventDispatch.registry(this);
	}

	public void submitInfoSaveEvent(Info bean) {
		this.submitInfoChangedEvent(CmsOprEventType.InfoSave, bean);
	}
	
	public void submitInfoUpdateEvent(Info bean) {
		this.submitInfoChangedEvent(CmsOprEventType.InfoUpdate, bean);
		
	}
	
	public void submitInfoDelEvent(Info bean) {
		this.submitInfoChangedEvent(CmsOprEventType.InfoDel, bean);
		
	}
	
	public void submitInfoPassEvent(Info bean) {
		SysEvent event = new SysEvent();
		this.submitInfoChangedEvent(CmsOprEventType.InfoPass, bean);
	}
	
	public void submitInfoRejctEvent(Info bean, String opnion, Long userId, WorkflowStep step, Long opUserId, String opUserName, String ip, String reqUrl) {
		SysEvent event = new SysEvent();
		event.addSelector(new InfoEventSelector());		
		event.addParam("eventType", CmsOprEventType.InfoReject);
		
		String showType = "";
		//bean = infoService.getInfoById(bean.getId());
		if (bean.getImages() != null && !bean.getImages().isEmpty()) {
			showType = "1";
		}else if(bean.getWithVideo() != null && bean.getWithVideo()){
			showType = "2";
		}else{
			showType = "0";
		}
		List<WorkflowStepRole> steproles = step.getStepRoles();
		Set<SysUserEntity> users = new HashSet<SysUserEntity>();
		for(WorkflowStepRole steprole : steproles){
			SysRoleEntity role = dataService.getSysRole(steprole.getRoleId());
			users.addAll(role.getUsers());
		}
		Long[] userIds = new Long[users.size()];
		Iterator it=users.iterator();
		int i = 0;
		while(it.hasNext()){
			userIds[i] = ((SysUserEntity)it.next()).getId();
			i++;
		}
		
		event.addParam("bean", bean);
		event.addParam("showType", showType);
		event.addParam("siteId", bean.getSite().getId());
		event.addParam("userIds", userIds);
		event.addParam("opUserId", opUserId);
		event.addParam("opUserName", opUserName);
		
		eventDispatch.postEvent(event);
	}

	public void submitInfoChangedEvent(CmsOprEventType eventType, Info bean) {
		SysEvent event = new SysEvent();
		event.addSelector(new InfoEventSelector());		
		event.addParam("eventType", eventType);
		event.addParam("bean", bean);
		
		eventDispatch.postEvent(event);
		
	}
	
	public void changed(SysEvent event, SysContext sysContext) {
		//
		CmsOprEventType eventType = event.getParam("eventType");
		Info bean = event.getParam("bean");
		switch(eventType){
			case InfoSave:
			case InfoUpdate:
			case InfoPass:
				//this.infoPush(event);
				break;
			case InfoReject:
				this.infoRejectPush(event);
				break;
			default:
				break;
		}
		
	}
	
	private void infoRejectPush(SysEvent event) {
		Info bean = event.getParam("bean");
		String ip = event.getParam("ip");
		String reqUrl = event.getParam("reqUrl");
		String showType = event.getParam("showType");
		Long[] userIds = event.getParam("userIds");
		Integer siteId = event.getParam("siteId");
		String iosTitle = "【退稿通知】"+bean.getTitle();
		Long opUserId = event.getParam("opUserId");
		String opUserName = event.getParam("opUserName");
		msgPushService.pushMsg("您有一封退稿通知", bean.getTitle(), PushType.Audit.getValue()+"", bean.getId().toString(),siteId, showType, userIds, iosTitle, opUserId, opUserName, ip, reqUrl);
	}
	
	private void infoPush(SysEvent event) {
		Info bean = event.getParam("bean");
		Integer push = bean.getPush();
		String status = bean.getStatus();
		if ((push == null || push == 0) && Info.NORMAL.equals(status)) {
			Node node = bean.getNode();
			Integer nodePush = node.getP3();
			Integer infoPush = bean.getP3();
			if ((nodePush != null && nodePush == 1) || (infoPush != null && infoPush == 1)) {

				String showType = "";
				if (bean.getImages() != null && !bean.getImages().isEmpty()) {
					showType = "1";
				}else if(bean.getWithVideo() != null && bean.getWithVideo()){
					showType = "2";
				}else{
					showType = "0";
				}
				Long opUserId = event.getParam("opUserId");
				String opUserName = event.getParam("opUserName");
				String ip = event.getParam("ip");
				String reqUrl = event.getParam("reqUrl");
				msgPushService.pushMsgToAll(pushInfoTitle, bean.getTitle(), PushType.Info.getValue()+"", bean.getId().toString(), bean.getSite().getId(), showType, opUserId, opUserName, ip, reqUrl);
			}
		}
	}
	
	
	
}
