package cn.mooc.app.module.push.data.rds;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.mooc.app.module.push.data.entity.IosMessagePedding;

/**
 * IosMessagePeddingDao
 * Ios正在处理的消息数据访问
 * 
 * @author zjj
 * @date 2016-03-29
 */
public interface IosMessagePeddingDao extends JpaRepository<IosMessagePedding, Integer> {

	Page<IosMessagePedding> findAll(Specification<IosMessagePedding> spec, Pageable pageable);
	
	@Query("select bean from IosMessagePedding bean where bean.travel<?1")
	List<IosMessagePedding> findByCount(int count);

}
