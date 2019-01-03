package cn.mooc.app.module.points.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.points.data.entity.PointsLevel;
import cn.mooc.app.module.points.data.rds.PointsLevelRepository;

@Service
public class PointsLevelService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PointsLevelRepository dao;
	
	public Page<PointsLevel> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<PointsLevel> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public List<PointsLevel> findList() {
		return dao.findAll();
	}

	public PointsLevel get(Integer id) {
		return dao.findOne(id);
	}
	
	public PointsLevel findByScore(Double score){
		List<PointsLevel> levelList = dao.findByScore(score);
		if(levelList != null && levelList.size() > 0){
			return levelList.get(0);
		}else{
			return null;
		}
	}

	public PointsLevel save(PointsLevel bean) {
		return dao.save(bean);
	}

	public PointsLevel update(PointsLevel bean) {
		return dao.save(bean);
	}

	public void delete(Integer... ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.delete(id);
			}
		}
	}
}
