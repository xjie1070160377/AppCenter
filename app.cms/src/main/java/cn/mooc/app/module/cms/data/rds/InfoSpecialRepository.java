package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.InfoSpecial;

/**
 * InfoSpecialRepository 文档专题数据访问
 * 
 * @author hwt
 * @date 2016-05-16
 */
public interface InfoSpecialRepository extends JpaRepository<InfoSpecial, Integer>, JpaSpecificationExecutor<InfoSpecial> {

	@Query("select bean from InfoSpecial bean where bean.info.id = ?1")
	List<InfoSpecial> findInfoSpecialByInfoId(Integer infoId);
	
	@Query("select max(bean.specialIndex) from InfoSpecial bean where bean.special.id=?1")
	public Integer findMaxSeqBySpecial(Integer specialId);

}
