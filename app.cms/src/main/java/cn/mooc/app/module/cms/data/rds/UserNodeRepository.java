package cn.mooc.app.module.cms.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.cms.data.entity.UserNode;

/**
 * UserNodeRepository
 * 用户订制栏目数据访问
 * 
 * @author zjj
 * @date 2016-07-06
 */
public interface UserNodeRepository extends JpaRepository<UserNode, Integer>,JpaSpecificationExecutor<UserNode>{

	@Modifying
	@Query("delete from UserNode bean where bean.userId=?1")
	public void deleteByUser(Long userId);
}
