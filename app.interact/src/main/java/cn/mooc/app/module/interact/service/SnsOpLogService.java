package cn.mooc.app.module.interact.service;


//imports as static
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.utils.ChineseToPinYin;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.interact.data.entity.SnsOpLog;
import cn.mooc.app.module.interact.data.nosql.SnsOpLogRepostory;
import cn.mooc.app.module.interact.model.SnsOpLogGroupByFtype;

@Service
public class SnsOpLogService {
	@Autowired
	private SnsOpLogRepostory snsOpLogRepostory;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	@Autowired
	  MongoTemplate mongoTemplate;
	 
	
	public Page<SnsOpLog> findAll(Integer siteId, String name, PagerParam pagerParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (siteId != null) {
			searchParams.put(SpecOperator.EQ + "_siteId", siteId);
		}
		if (StringUtils.isNotBlank(name)) {
			searchParams.put(SpecOperator.LIKE + "_author", name);
		}
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pagerParam.getPageRequest(), criteria, SnsOpLog.class);
	}
	
	/**
	 * 分组统计查询，按语句拼出来的方式
	 *  db.t_interact_sns_op_log.aggregate( [
		 { $project : {
		        _id :0,
		        ftype : 1 ,
		        createTime : 1 ,
		        fid : 1,
		        crateDate : { $dateToString: { format: "%Y-%m-%d", date: "$createTime" } }
		    }},
            { $match : {"createTime" : {$gte : ISODate("2015-12-31"), $lte : ISODate("2016-01-02")}} },
            { $group: { _id: {ftype:"$ftype",crateDate:"$crateDate"}, count: { $sum: 1 } } }
       ] );             
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SnsOpLogGroupByFtype> groupByFtypeByDay_bak(Date startDate, Date endDate){
		DBObject dateToString = new BasicDBObject();
		dateToString.put("format", "%Y-%m-%d");
		dateToString.put("date", "$createTime");
		
		DBObject project = new BasicDBObject();
		project.put("_id",0);
		project.put("ftype",1);
		project.put("createTime",1);
		project.put("crateDate",new BasicDBObject("$dateToString", dateToString));
		
		DBObject matchSub = new BasicDBObject();
		matchSub.put("$gte", startDate);
		matchSub.put("$lte", endDate);
		
		DBObject match = new BasicDBObject();
		match.put("createTime",matchSub);
		
		DBObject groupId = new BasicDBObject();
		groupId.put("ftype", "$ftype");
		groupId.put("crateDate", "$crateDate");
		
		DBObject group = new BasicDBObject();
		group.put("_id",groupId);
		group.put("count",new BasicDBObject("$sum", 1));
		
		List<DBObject> cmdList = new ArrayList<DBObject>();
		cmdList.add(new BasicDBObject("$project", project));
		cmdList.add(new BasicDBObject("$match", match));
		cmdList.add(new BasicDBObject("$group", group));
		
		CommandResult result =mongoTemplate.getCollection("t_interact_sns_op_log").aggregate(cmdList).getCommandResult();
		return null;
	}
	/**
	 * 按类型按天统计总数  
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SnsOpLogGroupByFtype> groupByFtypeByDay(Date startDate, Date endDate){
		Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        startCalendar.add(Calendar.HOUR_OF_DAY, 8);  
        endCalendar.setTime(endDate);
        endCalendar.add(Calendar.HOUR_OF_DAY, 8);  
        endCalendar.add(Calendar.DAY_OF_MONTH, 1);
        startDate = startCalendar.getTime();
        endDate = endCalendar.getTime();
		Aggregation agg = newAggregation(
		  project("ftype", "createTime")
		  .andExpression("substr(createTime,0,10)").as("crateDate"),
	      match(Criteria.where("createTime").gte(startDate).andOperator(Criteria.where("createTime").lte(endDate))),
	      group("ftype", "crateDate").count().as("total"),
	      sort(Sort.Direction.ASC, "ftype")
	    );

	    AggregationResults<SnsOpLogGroupByFtype> groupResults 
	      = mongoTemplate.aggregate(agg, SnsOpLog.class, SnsOpLogGroupByFtype.class);
	    List<SnsOpLogGroupByFtype> result = groupResults.getMappedResults();
	 
	    return result;
	}

	public void operation(Integer siteId, Integer userId, Integer ftype, Integer fid, String title, String author) {
		SnsOpLog opLog = new SnsOpLog();
		opLog.setLogId(mongoDbOpr.getNextSeqIntFor(SnsOpLog.class));
		opLog.setSiteId(siteId);
		opLog.setUserId(userId);
		opLog.setFtype(ftype);
		opLog.setFid(fid);
		opLog.setCreateTime(new Date());
		opLog.setTitle(title);
		opLog.setAuthor(author);
		if (StringUtils.isNotEmpty(author)) {
			opLog.setPyCode(ChineseToPinYin.getPinYinHeadChar(author));
		}
		snsOpLogRepostory.save(opLog);
//		dao.save(opLog);
//		return opLog;
	}

	public void clearByUIDFtypeFid(Integer userId, Integer ftype, Integer fid) {
		List<SnsOpLog> opLogs = snsOpLogRepostory.findList(userId, ftype, fid);
		for(SnsOpLog log : opLogs){
			snsOpLogRepostory.delete(new ObjectId(log.get_id()));
		}
	}

	
}
