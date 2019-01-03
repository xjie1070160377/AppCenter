package cn.mooc.app.core.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.entity.SeqData;

import com.mongodb.WriteResult;

/**
 * 对MongoDb的操作类
 * 
 * 其它方式：QueryDslMongoRepository
 * 
 * @author Taven
 *
 */
@Service
public class MongoDbOpr {

	@Autowired
	private MongoOperations mongoOperations ;
	
	public int getNextSeqIntFor(Class<?> cls){
		Document document = AnnotationUtils.findAnnotation(cls, Document.class);
		String collectionName = (String) AnnotationUtils.getValue(document, "collection");
		
		return (int) this.getNextSeqFor(collectionName);
		
	}
	
	public long getNextSeqFor(Class<?> cls){
		Document document = AnnotationUtils.findAnnotation(cls, Document.class);
		String collectionName = (String) AnnotationUtils.getValue(document, "collection");
		
		return this.getNextSeqFor(collectionName);
		
	}
	
	public long getNextSeqFor(String collectionName){
		Query query = new Query(Criteria.where("_id").is(collectionName));
		Update update = new Update();
		update.inc("seq", 1);
		SeqData seqData = mongoOperations.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), SeqData.class);
		
		if(seqData==null){
			SeqData _seqData = new SeqData();
			_seqData.set_id(collectionName);
			_seqData.setSeq(1);
			mongoOperations.insert(_seqData);
			return _seqData.getSeq();
		}
		
		return seqData.getSeq();
	}
	
	public void insert(Object objectToSave){
		mongoOperations.insert(objectToSave);
	}
	
	public void insertAll(Collection<? extends Object> objectsToSave){
		mongoOperations.insertAll(objectsToSave);
	}
	
	public <T> T findById(Object id, Class<T> entityClass){

		return mongoOperations.findById(id, entityClass);
	}
	
	public <T> T findOne(Query query, Class<T> entityClass){
		return mongoOperations.findOne(query, entityClass);
	}
	
	public long count(Query query, Class<?> entityClass){
		return mongoOperations.count(query, entityClass);
	}
	
	public boolean exists(Query query, Class<?> entityClass){
		return mongoOperations.exists(query, entityClass);
	}
	
	public <T> List<T> find(Query query, Class<T> entityClass){
		
		return mongoOperations.find(query, entityClass);
	}	

	public <T> Page<T> findPage(Pageable pageable, Criteria criteria, Class<T> entityClass){
		Query query = new Query();
		query.addCriteria(criteria);
		
		long total = this.count(query, entityClass);
		
		query.with(pageable);
		
        List<T> rows = this.find(query, entityClass);

        return new PageImpl<T>(rows, pageable, total);
	}
	
	public <T> Page<T> findPage(Pageable pageable, Query query, Class<T> entityClass){
		long total = this.count(query, entityClass);
		
		query.with(pageable);
		
        List<T> rows = this.find(query, entityClass);

        return new PageImpl<T>(rows, pageable, total);
	}
	
	public WriteResult remove(Query query, Class<?> entityClass){

		return mongoOperations.remove(query, entityClass);

	}
	
	public void demo(){
		//文档 spring-data-mongodb-reference
		
		//Query query = new Query(Criteria.where("deliveryTime").lt(time));
		
		//Query query = TextQuery.searching(new TextCriteria().matchingAny("coffee", "cake")).sortByScore();
		//List<Document> page = mongoOperations.find(query, Document.class);
		
		//TextQuery.queryText(new TextCriteria().matching("coffee").notMatching("cake"));
		
		
		
	}
	
}
