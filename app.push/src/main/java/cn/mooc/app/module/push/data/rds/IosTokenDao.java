package cn.mooc.app.module.push.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.push.data.entity.IosToken;

/**
 * IosTokenDao
 * IOS设备token数据访问
 * 
 * @author zjj
 * @date 2016-03-16
 */
public interface IosTokenDao extends JpaRepository<IosToken, Long> {
	
	@Query("select bean from IosToken bean where bean.token=?1")
	List<IosToken> findByToken(String token);
	
	@Query("select bean from IosToken bean where  bean.isios=?1")
	List<IosToken> findIosToken(Boolean isios);
}
