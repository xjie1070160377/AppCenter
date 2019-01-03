package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
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

import cn.mooc.app.module.cms.data.entity.CmsImageType;
import cn.mooc.app.module.cms.data.rds.CmsImageTypeRepository;
import cn.mooc.app.module.cms.model.CmsImageTypeModel;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CmsImageTypeService
 * 图片分类服务
 * 
 * @author linwei
 * @date 2017-05-27
 */
@Service
public class CmsImageTypeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CmsImageTypeRepository cmsImageTypeRepository;
	
	public CmsImageType getCmsImageTypeById(Integer id) {
		CmsImageType entity = cmsImageTypeRepository.findOne(id);
		return entity;
	}
	
	public List<CmsImageType> getAllCmsImageTypes(){
		return this.cmsImageTypeRepository.findAll();
	}
	
	public List<CmsImageTypeModel> getImageTypeList(){
		List<CmsImageType> list =  cmsImageTypeRepository.findAll();
		List<CmsImageTypeModel> mList = new ArrayList<CmsImageTypeModel>();
		for(CmsImageType type : list){
			mList.add(new CmsImageTypeModel(type));
		}
		return mList;
	}
	
	public Page<CmsImageType> findCmsImageTypePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsImageType> spec = SpecDynamic.buildSpec(filters.values());

		return cmsImageTypeRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public CmsImageType saveCmsImageType(CmsImageType entity) throws Exception {
		return this.cmsImageTypeRepository.save(entity);
	}

	@Transactional
	public CmsImageType updateCmsImageType(CmsImageType entity) throws Exception{
		return this.cmsImageTypeRepository.save(entity);
	}
	
	@Transactional
	public boolean delCmsImageType(Integer id){
		try{
			this.cmsImageTypeRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delCmsImageTypes(Integer[] ids){
		try{
			List<CmsImageType> entities = this.cmsImageTypeRepository.findAll(Arrays.asList(ids));
			this.cmsImageTypeRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
