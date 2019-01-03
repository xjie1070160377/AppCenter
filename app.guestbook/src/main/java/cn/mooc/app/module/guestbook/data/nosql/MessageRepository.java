package cn.mooc.app.module.guestbook.data.nosql;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cn.mooc.app.module.guestbook.data.entity.Message;

public interface MessageRepository extends MongoRepository<Message, String>{

	@Query("{ ?0 : ?1 }")
	Page<Message> findList(String columnFiled,Object keyWord, Pageable pageable);
	
	@Query("{ ?0 : ?1 , userId:?2 }")
	Page<Message> findList(String columnFiled,Object keyWord,long userId, Pageable pageable);
	
	@Query("{userId:?0}")
	Page<Message> findList(long userId, Pageable pageable);
	
	@Query("{_id:?0}")
	Message findOne(String id);

	@Query("{type:?0,sourceId:?1}")
	List<Message> findList(String ftype, String sourceId);

	@Query("{sourceId:?0}")
	List<Message> findBySourceId(String sourceId);
	
	@Query("{parentId:?0}")
	List<Message> findByParentId(String parentId, Sort sort);

}
