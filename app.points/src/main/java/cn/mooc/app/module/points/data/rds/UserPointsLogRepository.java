package cn.mooc.app.module.points.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.points.data.entity.UserPointsLog;

public interface UserPointsLogRepository extends JpaRepository<UserPointsLog, Integer>,
		JpaSpecificationExecutor<UserPointsLog> {
}
