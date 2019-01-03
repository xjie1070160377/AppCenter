package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import cn.mooc.app.module.cms.data.entity.CmsImage;
import cn.mooc.app.module.cms.data.entity.CmsImageType;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.CmsImageRepository;
import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CmsImageService
 * 图片详情服务
 * 
 * @author linwei
 * @date 2017-05-27
 */
@Service
public class CmsImageService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CmsImageRepository cmsImageRepository;
	@Autowired
	private CmsImageTypeService cmsImageTypeService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private WebContext webContext;
	
	public CmsImage getCmsImageById(Integer id) {
		CmsImage entity = cmsImageRepository.findOne(id);
		return entity;
	}
	
	public List<CmsImage> getAllCmsImages(){
		return this.cmsImageRepository.findAll();
	}
	
	public Page<CmsImage> findCmsImagePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsImage> spec = SpecDynamic.buildSpec(filters.values());

		return cmsImageRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	public List<CmsImage> findBySource(Integer siteId, Integer sourceType, Integer sourceId){
		return cmsImageRepository.findBySource(siteId, sourceType, sourceId);
	}
	
	
	@Transactional
	public CmsImage saveCmsImage(CmsImage entity) throws Exception {
		return this.cmsImageRepository.save(entity);
	}
	
	@Transactional
	public CmsImage saveCmsImage(String title, String tag, String url,Integer imageType, Integer sourceType, Integer sourceId) throws Exception {
		
		Site site = siteService.getCurrentSite();
		CmsImageType cmsImageType = cmsImageTypeService.getCmsImageTypeById(imageType);
		CmsImage entity = new CmsImage();
		Integer creatorId = webContext.getCurrentIntUserId();
		entity.setSourceId(sourceId);
		entity.setSourceType(sourceType);
		entity.setUrl(url);
		entity.setSite(site);
		entity.setCreatorId(creatorId);
		entity.setCreateTime(new Date());
		entity.setTitle(title);
		entity.setCmsImageType(cmsImageType);
		CmsImage image = this.cmsImageRepository.save(entity);
		return image;
		
	}

	@Transactional
	public CmsImage updateCmsImage(CmsImage entity) throws Exception{
		return this.cmsImageRepository.save(entity);
	}
	
	@Transactional
	public boolean delCmsImage(Integer id){
		try{
			this.cmsImageRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	
	@Transactional
	public int delCmsImages(Integer[] ids){
		try{
			List<CmsImage> entities = this.cmsImageRepository.findAll(Arrays.asList(ids));
			this.cmsImageRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
