package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.ScheduleJob;

/**
 * ScheduleJobRepository
 * 定时任务数据访问
 * 
 * @author linwei
 * @date 2017-06-22
 */
public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Integer>,JpaSpecificationExecutor<ScheduleJob>{
	
}
