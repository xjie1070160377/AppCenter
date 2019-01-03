package cn.mooc.app.module.cms.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsContributeFile;
import cn.mooc.app.module.cms.data.rds.CmsContributeFileRepository;
import cn.mooc.app.module.cms.support.Uploader;

/**
 * AppContributeFileServiceImpl
 * 投稿附加信息实现类
 * 
 * @author zjj
 * @date 2016-05-16
 */
@Service
public class CmsContributeFileService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private CmsContributeFileRepository dao;

	public Page<CmsContributeFile> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsContributeFile> spec = SpecDynamic.buildSpec(filters.values());
		
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public List<CmsContributeFile> findList() {
		return dao.findAll();
	}

	public CmsContributeFile get(Integer id) {
		return dao.findOne(id);
	}
	
	public List<CmsContributeFile> findImageByRec(Integer recId){
		return dao.findByRec(recId, Uploader.IMAGE);
	}
	
	public List<CmsContributeFile> findFileByRec(Integer recId){
		return dao.findByRec(recId, Uploader.FILE);
	}

	@Transactional
	public CmsContributeFile save(CmsContributeFile bean) {
		return dao.save(bean);
	}
	
	@Transactional
	public List<CmsContributeFile> save(List<CmsContributeFile> beans){
		return dao.save(beans);
	}

	@Transactional
	public CmsContributeFile update(CmsContributeFile bean) {
		return dao.save(bean);
	}

	@Transactional
	public void delete(Integer... ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.delete(id);
			}
		}
	}
}