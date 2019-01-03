package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.CmsImage;

/**
 * CmsImageDetailRepository
 * 图片详情数据访问
 * 
 * @author linwei
 * @date 2017-05-27
 */
public interface CmsImageRepository extends JpaRepository<CmsImage, Integer>,JpaSpecificationExecutor<CmsImage>{
	
	@Query("select bean from CmsImage bean where bean.site.id = ?1 and bean.sourceType = ?2 and bean.sourceId = ?3")
	List<CmsImage> findBySource(Integer siteId, Integer sourceType, Integer sourceId);
	
}
