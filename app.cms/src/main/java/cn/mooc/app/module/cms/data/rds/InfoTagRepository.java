package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.InfoTag;

public interface InfoTagRepository extends JpaRepository<InfoTag, Integer>,
		JpaSpecificationExecutor<InfoTag> {

	@Modifying
	@Query("delete from InfoTag t where t.info.id=?1")
	public int deleteByInfoId(Integer infoId);

	@Modifying
	@Query("delete from InfoTag t where t.tag.id=?1")
	public int deleteByTagId(Integer tagId);
}
