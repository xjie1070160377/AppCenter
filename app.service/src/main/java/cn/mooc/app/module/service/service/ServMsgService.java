package cn.mooc.app.module.service.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.service.data.entity.AppService;
import cn.mooc.app.module.service.data.entity.ServInfo;
import cn.mooc.app.module.service.data.entity.ServMsg;
import cn.mooc.app.module.service.data.entity.ServMsgInfo;
import cn.mooc.app.module.service.data.entity.ServMsgInfoPK;
import cn.mooc.app.module.service.data.rds.ServMsgRepository;

/**
 * ServMsgService
 * 服务消息服务
 * 
 * @author zjj
 * @date 2016-09-13
 */
@Service
public class ServMsgService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServMsgRepository servMsgRepository;
	@Autowired
	private ServInfoService servInfoService;
	@Autowired
	private ServMsgInfoService servMsgInfoService;
	
	public ServMsg getServMsgById(Integer id) {
		ServMsg entity = servMsgRepository.findOne(id);
		return entity;
	}
	
	public List<ServMsg> getAllServMsgs(){
		return this.servMsgRepository.findAll();
	}
	
	public Page<ServMsg> findPageByServiceId(Integer serviceId, Integer state, Pageable pageable) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(serviceId != null){
			searchParams.put(SpecOperator.EQ+"_appService.serviceId", serviceId);
		}
		if(state != null){
			searchParams.put(SpecOperator.EQ+"_msgstate", state);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<ServMsg> spec = SpecDynamic.buildSpec(filters.values());
		return servMsgRepository.findAll(spec, pageable);
	}
	
	public Page<ServMsg> findServMsgPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<ServMsg> spec = SpecDynamic.buildSpec(filters.values());

		return servMsgRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public ServMsg save(ServMsg entity) throws Exception {
		//entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.servMsgRepository.save(entity);
	}

	@Transactional
	public ServMsg update(ServMsg entity) throws Exception{
		//entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.servMsgRepository.save(entity);
	}
	
	@Transactional
	public boolean delServMsg(Integer id){
		try{
			this.servMsgRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delServMsgs(Integer[] ids){
		try{
			List<ServMsg> entities = this.servMsgRepository.findAll(Arrays.asList(ids));
			this.servMsgRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	@Transactional
	public void save(Long userId, ServInfo info, AppService appService) throws Exception {
		// TODO Auto-generated method stub
		servInfoService.update(info, appService);
		
		ServMsg servMsg = new ServMsg();
		servMsg.setAppService(appService);
		servMsg.setMsgtype(ServMsg.MSGTYPE_IMGTEXT);
		servMsg.setCreatetime(new Timestamp(new Date().getTime()));
		servMsg.setUserId(userId);
		servMsg.setMsgstate(ServMsg.MSGSTATE_DRAFT);
		this.save(servMsg);
		
		ServMsgInfo servMsgInfo = new ServMsgInfo();
		servMsgInfo.setInfo(info);
		servMsgInfo.setMsg(servMsg);
		servMsgInfo.setIndex(0);
		ServMsgInfoPK servMsgInfoId = new ServMsgInfoPK();
		servMsgInfoId.setInfoId(info.getInfoId());
		servMsgInfoId.setMsgid(servMsg.getMsgid());
		servMsgInfo.setId(servMsgInfoId);
		servMsgInfoService.save(servMsgInfo);
//		
//		Set<ServMsgInfo> servMsgInfos = new HashSet<ServMsgInfo>();
//		servMsgInfos.add(servMsgInfo);
//		servMsg.setServMsgInfos(servMsgInfos);
	}

	@Transactional
	public void save(ServMsg servMsg, List<ServMsgInfo> rsList) throws Exception {
		// TODO Auto-generated method stub
		save(servMsg);
		String desc = "您有一条新的消息";
		for (ServMsgInfo servMsgInfo : rsList) {
			ServInfo info = servMsgInfo.getInfo();
			Integer index = servMsgInfo.getIndex();
			if(index == null || new Integer(0).equals(index)){
				desc = info.getDetail().getMetaDescription();
			}
			servInfoService.update(info, servMsg.getAppService());
			if(servMsg.getMsgid() != null && info.getInfoId() != null){
				ServMsgInfoPK servMsgInfoId = servMsgInfo.getId();
				servMsgInfoId.setInfoId(info.getInfoId());
				servMsgInfoId.setMsgid(servMsg.getMsgid());
				servMsgInfoService.save(servMsgInfo);
			}
		}
		if(servMsg.getMsgstate().equals(ServMsg.MSGSTATE_SENDING)){
			AppService appService = servMsg.getAppService();
//			msgPushService.pushServInfoMsg(appService.getServiceFullname(), desc, appService.getServiceId(), appService.getServiceFullname(), servMsg.getMsgid());
		}
	}
}
