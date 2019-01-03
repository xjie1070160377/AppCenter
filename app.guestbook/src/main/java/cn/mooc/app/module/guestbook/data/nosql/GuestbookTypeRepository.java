package cn.mooc.app.module.guestbook.data.nosql;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.mooc.app.module.guestbook.data.entity.GuestbookType;

public interface GuestbookTypeRepository extends MongoRepository<GuestbookType, String>{

	@Query("{ ?0 : ?1 }")
	Page<GuestbookType> findList(String columnFiled,Object keyWord, Pageable pageable);
	
	@Query("{_id:?0}")
	GuestbookType findOne(String id);

}
