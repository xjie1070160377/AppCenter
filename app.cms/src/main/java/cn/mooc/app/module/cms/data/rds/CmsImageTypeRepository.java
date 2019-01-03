package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mooc.app.module.cms.data.entity.CmsImageType;

/**
 * CmsImageTypeRepository
 * 图片分类数据访问
 * 
 * @author linwei
 * @date 2017-05-27
 */
public interface CmsImageTypeRepository extends JpaRepository<CmsImageType, Integer>,JpaSpecificationExecutor<CmsImageType>{
	
}
