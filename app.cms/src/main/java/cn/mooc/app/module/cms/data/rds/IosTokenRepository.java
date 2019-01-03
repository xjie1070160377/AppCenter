/*package cn.mooc.app.module.cms.data.rds;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.IosToken;

public interface IosTokenRepository  extends JpaRepository<IosToken, Integer> {

	Page<IosToken> findAll(Specification<IosToken> spec, Pageable pageable);
	
	@Query("select bean from IosToken bean where bean.userId=?2 and bean.siteId=?1")
	IosToken findIosToken(Integer siteId, Long userId);

	@Query("select bean from IosToken bean where bean.token=?1 and bean.failCount<?2")
	List<IosToken> findByToken(String token, int count);
	
}
*/