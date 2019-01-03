package cn.mooc.app.module.guestbook.data.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.mooc.app.module.guestbook.data.entity.Guestbook;

public interface GuestbookRepository extends MongoRepository<Guestbook, String>{

	@Query("{ ?0 : ?1 }")
	Page<Guestbook> findList(String columnFiled,Object keyWord, Pageable pageable);
	
	@Query("{_id:?0}")
	Guestbook findOne(String id);
	
	@Query("{userId:?0}")
	Guestbook findOneByUserId(Long userId);

}
