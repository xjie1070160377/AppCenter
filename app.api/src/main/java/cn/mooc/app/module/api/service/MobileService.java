package cn.mooc.app.module.api.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.web.model.PagerParam;


@Service
public class MobileService {
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public Page<Map> findPage(PagerParam pagerParam, Integer siteId) {
		String sql = "select id,                                                                                               "
				+ "       title,                                                                                               "
				+ "       description,                                                                                         "
				+ "       smallImage,                                                                                          "
				+ "       date_format(publishDate, '%Y-%m-%d %T') publishDate,                                                 "
				+ "       views,                                                                                               "
				+ "       priority,                                                                                            "
				+ "       type,                                                                                                "
				+ "       hasVideo,                                                                                            "
				+ "       images                                                                                               "
				+ "  from (select t.info_id      as id,                                                                      "
				+ "               IFNULL(t1.full_title,t1.title)       as title,                                           "
				+ "               t1.meta_description as description,                                                        "
				+ "               t1.small_image as smallImage,                                                              "
				+ "               t.publish_date as publishDate,                                                             "
				+ "               t.views        as views,                                                                   "
				+ "               ifnull(t.priority,0) as priority,                                                          "
				+ "               'info'           as type,                                                                    "
				+ "               IFNULL(t.is_with_video,0) hasVideo,                                                        "
				+ "               t2.images        as images                                                                   "
				+ "          from t_cms_info t, t_cms_info_detail t1                                                               "
				+ "          left join (SELECT info_id, GROUP_CONCAT(image) images                                         "
				+ "                      from t_cms_info_image                                                                   "
				+ "                     group by info_id) t2                                                                 "
				+ "            on t1.info_id = t2.info_id                                                                  "
				+ "         where t.info_id = t1.info_id                                                                   "
				+ "           and t.status = 'A'                                                                             "
				+ "           and t.site_id = " + siteId + "                                                                 "
				+ "  		  and not exists (select t3.info_id from t_cms_info_special t3 where t.info_id = t3.info_id)   "
				+ "        union                                                                                               "
				+ "        select t.special_id    as id,                                                                     "
				+ "               t.title         as title,                                                                  "
				+ "               t.meta_description as description,                                                         "
				+ "               t.small_image   as smallImage,                                                             "
				+ "               t.creation_date as publishDate,                                                            "
				+ "               t.views         as views,                                                                  "
				+ "               ifnull(t.priority,0) as priority,                                                          "
				+ "               'special'         as type,                                                                   "
				+ "               0 hasVideo,                                                        						   "
				+ "               ''		        as images                                                                  "
				+ "          from t_cms_special t                                                                                "
				+ "         where t.site_id = " + siteId + ") a                                                              "
				+ " order by a.priority desc, a.publishDate desc                                                                                ";
		Query query = entityManager.createNativeQuery(sql);
		if (pagerParam != null) {
			int startPosition = pagerParam.getPageStart() * pagerParam.getRows();
			int maxResult = pagerParam.getPage() * pagerParam.getRows();
			query.setFirstResult(startPosition);
			query.setMaxResults(maxResult);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List list = query.getResultList();
		Page<Map> page = new PageImpl<Map>(list);
		return page;
	}
}
