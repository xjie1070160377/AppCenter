package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.Attachment;

/**
 * AttachmentRepository
 * 附件数据访问
 * 
 * @author hwt
 * @date 2016-05-16
 */
public interface AttachmentRepository extends JpaRepository<Attachment, Integer>,JpaSpecificationExecutor<Attachment>{

	Attachment findByName(String name);
	
}
