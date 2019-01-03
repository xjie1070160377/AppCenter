package cn.mooc.app.module.cms.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoVisitor;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.InfoVisitorDao;
import cn.mooc.app.module.cms.orm.SearchFilter;


@Service
public class InfoVisitorService {
	@Resource
	private InfoVisitorDao dao;
//	@Resource
//	private SQLDao sqlDao;
	
	@PersistenceContext
	private EntityManager entityManager;

	public Page<InfoVisitor> findAll(Map<String, String[]> params, Integer siteId, Pageable pageable) {
		return dao.findAll(spec(params, siteId), pageable);
	}

	private Specification<InfoVisitor> spec(Map<String, String[]> params, final Integer siteId) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<InfoVisitor> fsp = SearchFilter.spec(filters, InfoVisitor.class);
		Specification<InfoVisitor> sp = new Specification<InfoVisitor>() {
			public Predicate toPredicate(Root<InfoVisitor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				if (siteId != null) {
					pred = cb.and(pred, cb.equal(root.get("site").get("id"), siteId));
				}
				return pred;
			}
		};
		return sp;
	}

	public List<InfoVisitor> findList() {
		return dao.findAll();
	}

	public InfoVisitor get(long userId, Integer siteId, Integer infoId) {
		return dao.find(userId, siteId, infoId);
	}

	@Transactional
	public void visite(SysUserEntity user, Site site, Info info, String ip) {
		InfoVisitor infoVisitor = get(user.getId(), site.getId(), info.getId());
		if (infoVisitor == null) {
			infoVisitor = new InfoVisitor();
			infoVisitor.setUserId(user.getId());
			infoVisitor.setSite(site);
			infoVisitor.setInfo(info);
			infoVisitor.setIp(ip);
			infoVisitor.setTime(new Date());
			infoVisitor.setTitle(info.getTitle());
			infoVisitor.setName(user.getUserName());
			dao.save(infoVisitor);
		}
	}

	public int countUnReadInfo(Integer userId, Integer siteId, String nodeIds) {
		String sql = "select count(*) count                                                                        			"
				+ "  from t_cms_info t                                                                       				" 
				+ " where t.site_id = " + siteId
				+ "   and t.status = 'A'                                                       						"
				+ "   and not EXISTS (select t1.id                                                       				"
				+ "          from t_cms_info_visitor t1                                                      				"
				+ "         where t.info_id = t1.info_id                                               				" 
				+ "           and t1.user_id = " + userId 
				+ "           and t1.site_id = " + siteId + ")                                                        ";
		if (StringUtils.isNotEmpty(nodeIds)) {
			sql += " and t.node_id in (" + nodeIds + ")";
		}
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.getResultList();
		if(list != null && list.size() > 0){
			Map result = list.get(0);
			return StringUtil.string2Int(result.get("count"));
		}
		return 0;
//		entityManager.createNativeQuery(sqlString, resultSetMapping)
//		return sqlDao.count(sql);
	}

	public Page<Map> pageUnReadInfo(PagerParam pagerParam, Integer userId, Integer siteId, String nodeIds) {
		String sql = "select t.info_id as id,                                                                                "
				+ "       IFNULL(t1.full_title,t1.title) as title,                                                         "
				+ "       t1.meta_description as description,                                                                "
				+ "       t1.small_image as smallImage,                                                                      "
				+ "       date_format(t.publish_date, '%Y-%m-%d %T') as publishDate,                                                                     "
				+ "       t.views as views,                                                                                  "
				+ "       ifnull(t.priority, 0) as priority,                                                                 "
				+ "       IFNULL(t.is_with_video,0) hasVideo,                                                        		   "
				+ "       t2.images as images                                                                                  "
				+ "  from t_cms_info t, t_cms_info_detail t1                                                                       "
				+ "  left join (SELECT info_id, GROUP_CONCAT(image) images                                                 "
				+ "               from t_cms_info_image                                                                          "
				+ "              group by info_id) t2                                                                        "
				+ "    on t1.info_id = t2.info_id                                                                          "
				+ " where t.info_id = t1.info_id                                                                           "
				+ "   and t.status = 'A'                                                                                     "
				+ "   and t.site_id = " + siteId + "                                                                         "
				+ "   and not EXISTS (select t1.id                                                                           "
				+ "          from t_cms_info_visitor t1                                                                          "
				+ "         where t.info_id = t1.info_id                                                                   "
				+ "           and t1.user_id = " + userId + "                                                                "
				+ "           and t1.site_id = " + siteId + ")        													   ";
		if (StringUtils.isNotEmpty(nodeIds)) {
			sql += " and t.node_id in (" + nodeIds + ")";
		}
		sql += " order by t.priority desc, t.publish_date desc";
		Query query = entityManager.createNativeQuery(sql);
		if (pagerParam != null) {
			int startPosition = pagerParam.getPageStart() * pagerParam.getRows();
			int maxResult = pagerParam.getPage() * pagerParam.getRows();
			query.setFirstResult(startPosition);
			query.setMaxResults(maxResult);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.getResultList();
		Page page = new PageImpl<>(list);
		return page;
		//return sqlDao.findPage(pageable, sql, new String[] { "id", "title", "description", "smallImage", "publishDate", "views", "priority", "hasVideo", "images" });
	}
}
