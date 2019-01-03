package cn.mooc.app.module.cms.data.rds;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>,
		JpaSpecificationExecutor<Tag> {
	
	public List<Tag> findBySiteIdAndName(Integer siteId, String name);

	@Query("select count(*) from Tag bean where bean.site.id in ?1")
	public long countBySiteId(Collection<Integer> siteIds);
}
