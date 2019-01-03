package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>,
		JpaSpecificationExecutor<Task> {

}
