package cn.mooc.app.module.cms.service;

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

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.InfoSource;
import cn.mooc.app.module.cms.data.rds.InfoSourceDao;

/**
 * InfoSourceServiceImpl
 * 来源实现类
 * 
 * @author zjj
 * @date 2016-02-26
 */
@Service
public class InfoSourceService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InfoSourceDao dao;
	
	public Page<InfoSource> findInfoSourcePage(Map<String, Object> searchParams, PagerParam pageParam) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<InfoSource> spec = SpecDynamic.buildSpec(filters.values());
		
		return dao.findAll(spec, pageParam.getPageRequest());
	}

//	public Page<InfoSource> findAll(Map<String, String[]> params, Pageable pageable) {
//		return dao.findAll(spec(params), pageable);
//	}
//
//	private Specification<InfoSource> spec(Map<String, String[]> params) {
//		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
//		final Specification<InfoSource> fsp = SearchFilter.spec(filters, InfoSource.class);
//		Specification<InfoSource> sp = new Specification<InfoSource>() {
//			public Predicate toPredicate(Root<InfoSource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Predicate pred = fsp.toPredicate(root, query, cb);
//				return pred;
//			}
//		};
//		return sp;
//	}

	public List<InfoSource> findList() {
		return dao.findAll();
	}

	public InfoSource get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public InfoSource save(InfoSource bean) throws SaveFailureException {
		if(StringUtils.isEmpty(bean.getName())){
			throw new SaveFailureException("名称不能为空.");
		}
		
		return dao.save(bean);
	}

	@Transactional
	public InfoSource update(InfoSource bean) throws SaveFailureException {
		if(bean.getName()== null){
			throw new SaveFailureException("名称不能为空.");
		}
		return dao.save(bean);
	}

	@Transactional
	public boolean delete(Integer... ids) {
		try{
			if (ids != null) {
				for (Integer id : ids) {
					dao.delete(id);
				}
			}
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		
		
	}
}
