package cn.mooc.app.module.points.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.UserPoints;

public interface UserPointsRepository extends JpaRepository<UserPoints, Long>,
		JpaSpecificationExecutor<UserPoints> {

}
