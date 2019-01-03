package cn.mooc.app.module.push.data.rds;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.mooc.app.module.push.data.entity.IosMessageError;

/**
 * IosMessageErrorDao
 * Ios处理失败的消息数据访问
 * 
 * @author zjj
 * @date 2016-03-29
 */
public interface IosMessageErrorDao extends JpaRepository<IosMessageError, Integer> {

	Page<IosMessageError> findAll(Specification<IosMessageError> spec, Pageable pageable);

}
