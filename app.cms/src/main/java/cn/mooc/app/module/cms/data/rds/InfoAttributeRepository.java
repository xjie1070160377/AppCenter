package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.InfoAttribute;

/**
 * InfoAttributeRepository
 * 文章明细数据访问
 * 
 * @author hwt
 * @date 2016-05-19
 */
public interface InfoAttributeRepository extends JpaRepository<InfoAttribute, Integer>,JpaSpecificationExecutor<InfoAttribute>{

	@Query("select bean from InfoAttribute bean where bean.info.id=?1 and bean.attribute.id=?2")
	public InfoAttribute findByInfoId(Integer id, Integer attrId);
	
	@Query("select bean from InfoAttribute bean where bean.info.id=?1")
	public List<InfoAttribute> findByInfoId(Integer id);
	
	@Query("select min(bean.seq) from InfoAttribute bean where bean.attribute.id=?1")
	public Integer findMinSeqByAttr(Integer attrId);
	
}
