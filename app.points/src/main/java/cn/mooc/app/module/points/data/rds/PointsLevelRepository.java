package cn.mooc.app.module.points.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.points.data.entity.PointsLevel;

public interface PointsLevelRepository extends JpaRepository<PointsLevel, Integer>,
		JpaSpecificationExecutor<PointsLevel> {

	@Query("select bean from PointsLevel bean where bean.minPoints<=?1 order by bean.minPoints desc")
	public List<PointsLevel> findByScore(Double score);
}
