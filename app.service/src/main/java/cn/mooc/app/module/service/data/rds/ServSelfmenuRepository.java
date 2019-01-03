package cn.mooc.app.module.service.data.rds;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.service.data.entity.ServSelfmenu;

/**
 * ServSelfmenuRepository
 * 服务号自定义菜单实现类数据访问
 * 
 * @author zjj
 * @date 2016-09-14
 */
public interface ServSelfmenuRepository extends JpaRepository<ServSelfmenu, Integer>,JpaSpecificationExecutor<ServSelfmenu>{
	
	@Modifying
	@Query("delete ServSelfmenu bean where bean.appService.serviceId = ?1 and bean.parent is not null")
	void deleteAllByServiceIdChild(Integer serviceId);
	
	@Modifying
	@Query("delete ServSelfmenu bean where bean.appService.serviceId = ?1")
	void deleteAllByServiceId(Integer serviceId);

	@Query("select bean from ServSelfmenu bean where bean.appService.serviceId = ?1 and bean.parent is null order by bean.menuorder")
	List<ServSelfmenu> findParentMenusByServiceId(Integer serviceId);

	@Query("select bean from ServSelfmenu bean where bean.parent.id = ?1 order by bean.menuorder")
	List<ServSelfmenu> findChildMenusByParentId(Integer parentid);
}
