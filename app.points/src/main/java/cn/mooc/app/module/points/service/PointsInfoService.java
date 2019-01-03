package cn.mooc.app.module.points.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.points.data.entity.PointsInfo;
import cn.mooc.app.module.points.data.rds.PointsInfoRepository;

@Service
@Transactional
public class PointsInfoService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PointsInfoRepository dao;
	
	@Autowired
	private InfoService infoQueryService;
	
	@Autowired
	private PointsRuleService ruleService;

	public Page<PointsInfo> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<PointsInfo> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public List<PointsInfo> findList() {
		return dao.findAll();
	}
	
	public Page<PointsInfo> findListByRule(Integer ruleId, PagerParam pageParam){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.EQ + "_rule.id", ruleId);
		
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<PointsInfo> spec = SpecDynamic.buildSpec(filters.values());
		
		return dao.findAll(spec, pageParam.getPageRequest());
	}
	
	public List<PointsInfo> findListByRule(Integer ruleId){
		return dao.findListByRule(ruleId);
	}
	
	public Page<Map> querySelectInfo(Integer ruleId, Integer infoId, String title, PagerParam pagerParam){
		String s = "";
		if(infoId != null){
			s += " and info.f_info_id="+infoId;
		}
		if(StringUtils.isNotEmpty(title)){
			s += " and info.f_title like '%"+title+"%'";
		}
		String str = "";
		if(ruleId != null){
			str = " and not exists (select * from app_points_info b where b.f_rule_id="+ruleId +" and b.f_info_id=info.f_info_id)";
		}
		String sql = "SELECT info.f_info_id,detail.f_title,DATE_FORMAT(info.f_publish_date,'%Y-%m-%d %H:%i')  as f_publish_date "+
				     "FROM cms_info info,cms_info_detail detail "+
				     "WHERE info.f_info_id=detail.f_info_id and info.f_status='A'"+s+str;
		if(pagerParam.getSort() != null){
			Sort sort = pagerParam.getSort();
			Iterator e = sort.iterator();
			String orderString = "";
			if(e.hasNext()){
				Order order = (Order) e.next();
				if(StringUtils.isEmpty(orderString)){
					orderString = "order by "+order.getProperty()+ " " + order.getDirection();
				}else{
					orderString += ", "+order.getProperty()+ " " + order.getDirection();
				}
			}
			sql += " " + orderString;
		}
		Query query = entityManager.createNativeQuery(sql);
		if (pagerParam != null) {
			int startPosition = pagerParam.getPageStart() * pagerParam.getRows();
			int maxResult = pagerParam.getPage() * pagerParam.getRows();
			query.setFirstResult(startPosition);
			query.setMaxResults(maxResult);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.getResultList();
		int total = countPage(sql);
		Page page = new PageImpl<>(list, new PageRequest(pagerParam.getPage(), pagerParam.getRows(), null), total);
		return page;
	}
	
	private int countPage(String sql) {
		sql = "select count(*) as count from (" + sql + ") t1";
		Query query = entityManager.createNativeQuery(sql);
		BigInteger obj = (BigInteger) query.getSingleResult();
		return obj.intValue();
	}

	public PointsInfo get(Integer id) {
		return dao.findOne(id);
	}

	public PointsInfo save(PointsInfo bean) {
		return dao.save(bean);
	}

	public PointsInfo update(PointsInfo bean) {
		return dao.save(bean);
	}
	
	public void updateByRule(Integer ruleId, List<Map> info_list){
		if(info_list==null || info_list.size()==0){
			dao.deleteByRule(ruleId);
		}else{
			List<PointsInfo> infos = this.findListByRule(ruleId);
			List<Integer> ids = new ArrayList<Integer>();
			for(PointsInfo info : infos){
				ids.add(info.getInfo().getId());
			}
			for(Map params : info_list){
				Integer infoId = Integer.parseInt(params.get("infoid")+"");
				Integer haslike = Integer.parseInt(params.get("haslike")+"");
				Integer hascomment = Integer.parseInt(params.get("hascomment")+"");
				if(ids.contains(infoId)){
					PointsInfo ai = dao.findByRuleInfo(ruleId, infoId);
					ai.setHascomments(hascomment);
					ai.setHaslike(haslike);
					dao.save(ai);
					ids.remove(infoId);
				}else{
					PointsInfo ai = new PointsInfo();
					ai.setHascomments(hascomment);
					ai.setHaslike(haslike);
					ai.setInfo(infoQueryService.getInfoById(infoId));
					ai.setRule(ruleService.get(ruleId));
					dao.save(ai);
				}
			}
			if(ids != null && ids.size() > 0){
				for(Integer id : ids){
					dao.deleteByRuleInfo(ruleId, id);
				}
			}
		}
	}

	public void delete(Integer... ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.delete(id);
			}
		}
	}
}
