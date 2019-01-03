package cn.mooc.app.module.cms.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.module.cms.data.entity.Attachment;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.AttachmentRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * AttachmentService
 * 附件服务
 * 
 * @author hwt
 * @date 2016-05-16
 */
@Service
public class AttachmentService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AttachmentRepository attachmentRepository;
	@Autowired
	private SiteService siteService;
	
	public Attachment getAttachmentById(Integer id) {
		Attachment entity = attachmentRepository.findOne(id);
		return entity;
	}
	
	public List<Attachment> getAllAttachments(){
		return this.attachmentRepository.findAll();
	}
	
	public Page<Attachment> findAttachmentList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Attachment> spec = SpecDynamic.buildSpec(filters.values());
		
		return attachmentRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public Attachment saveAttachment(Attachment entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.attachmentRepository.save(entity);
	}

	@Transactional
	public Attachment updateAttachment(Attachment entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.attachmentRepository.save(entity);
	}
	
	@Transactional
	public boolean delAttachment(Integer id){
		try{
			this.attachmentRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delAttachments(Integer[] ids){
		try{
			List<Attachment> entities = this.attachmentRepository.findAll(Arrays.asList(ids));
			this.attachmentRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	public Attachment findByName(String name) {
		return attachmentRepository.findByName(name);
	}

	@Transactional
	public Attachment save(String name, Long length, String ip, Integer userId,
			Integer siteId) {
		Attachment bean = findByName(name);
		if (bean != null) {
			// 重名的，不重复保存。
			return bean;
		}
		bean = new Attachment();
		bean.setName(name);
		bean.setLength(length);
		bean.setIp(ip);
		save(bean, userId, siteId);
		return bean;
	}

	@Transactional
	public Attachment save(Attachment bean, Integer userId, Integer siteId) {
		bean.setUserId(userId);
		Site site = siteService.getSiteById(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		bean = attachmentRepository.save(bean);
		return bean;
	}
}
