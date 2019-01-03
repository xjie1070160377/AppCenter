package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.AttachmentRef;

/**
 * AttachmentRefRepository 附件数据访问
 * 
 * @author hwt
 * @date 2016-05-16
 */
public interface AttachmentRefRepository extends JpaRepository<AttachmentRef, Integer>, JpaSpecificationExecutor<AttachmentRef> {

	List<AttachmentRef> findByFtypeAndFid(String ftype, Integer fid);

}
