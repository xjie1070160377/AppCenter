package cn.mooc.app.module.cms.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
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
import cn.mooc.app.module.cms.data.entity.CmsContribute;
import cn.mooc.app.module.cms.data.entity.CmsContributeRec;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.rds.CmsContributeRecRepository;
import cn.mooc.app.module.cms.model.InfoFindListPageParams;

/**
 * AppContributeRecServiceImpl
 * 用户投稿记录实现类
 * 
 * @author zjj
 * @date 2016-05-20
 */
@Service
public class CmsContributeRecService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private CmsContributeRecRepository dao;
	@Autowired
	private InfoService infoService;

	public Page<CmsContributeRec> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsContributeRec> spec = SpecDynamic.buildSpec(filters.values());
		
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public Page<CmsContributeRec> findList(Long[] userIds, PagerParam pageParam) {
		Specification<CmsContributeRec> spec = buildSpec(userIds);
		return dao.findAll(spec, pageParam.getPageRequest());
	}
	
	private Specification<CmsContributeRec> buildSpec(Long[] userIds){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(userIds)) {
			List<Long> list = Arrays.asList(userIds);
			searchParams.put(SpecOperator.IN + "_contribute.user.id", list);
		}
		
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsContributeRec> spec = SpecDynamic.buildSpec(filters.values());
//		spec = spec(spec, findParams.getAttrId(), findParams.getSpecialId(), findParams.getSpecialTitle(), findParams.getTitle(), findParams.getTreeNumber(), findParams.getExcludeTreeNumber());
		return spec;
	}
	
	public List<CmsContributeRec> findList() {
		return dao.findAll();
	}

	public CmsContributeRec get(Integer id) {
		return dao.findOne(id);
	}
	
	public List<CmsContributeRec> findByUser(Integer userId){
		return dao.findByUser(userId);
	}
	
	@Transactional
	public boolean bingdingInfo(Integer id, Integer fid, String replay, Integer status){
		CmsContributeRec rec = dao.findOne(id);
		if(fid != null){
			List<CmsContributeRec> list = dao.findByFid(fid);
			
			rec.setReplay(replay);
			if(rec.getFid() != null && rec.getFid().equals(fid)){
				if(status == null){
					Info info = infoService.getInfoById(fid);
					if(info.getStatus().equals(Info.NORMAL)){
						rec.setStatus(CmsContributeRec.PASS);
					}else if(info.getStatus().equals(Info.REJECTION)){
						rec.setStatus(CmsContributeRec.UNPASS);
					}else{
						rec.setStatus(CmsContributeRec.PEDDING);
					}
				}else{
					rec.setStatus(status);
					dao.save(rec);
				}
				return true;
			}else{
				if(list != null && list.size() > 0){
					if(status != null){
						rec.setStatus(status);
					}
					dao.save(rec);
					return false;
				}else{
					Info info = infoService.getInfoById(fid);
					if(status == null){
						if(info.getStatus().equals(Info.NORMAL)){
							rec.setStatus(CmsContributeRec.PASS);
						}else if(info.getStatus().equals(Info.REJECTION)){
							rec.setStatus(CmsContributeRec.UNPASS);
						}else{
							rec.setStatus(CmsContributeRec.PEDDING);
						}
					}else{
						rec.setStatus(status);
					}
					rec.setFid(fid);
					dao.save(rec);
					return true;
				}
			}
		}else{
			rec.setReplay(replay);
			if(status != null){
				rec.setStatus(status);
			}
			dao.save(rec);
			return true;
		}
	}
	@Transactional
	public void updateStatus(Integer[] ids, Integer status){
		for(Integer id : ids){
			CmsContributeRec rec = dao.findOne(id);
			rec.setStatus(status);
			dao.save(rec);
		}
	}

	@Transactional
	public CmsContributeRec save(CmsContributeRec bean) {
		return dao.save(bean);
	}

	@Transactional
	public CmsContributeRec update(CmsContributeRec bean) {
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