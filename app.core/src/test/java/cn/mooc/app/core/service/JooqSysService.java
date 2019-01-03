package cn.mooc.app.core.service;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.jooq.generated.tables.TSysConfig;
import cn.mooc.app.core.jooq.generated.tables.records.TSysConfigRecord;



@Service
public class JooqSysService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	DSLContext create;
	
	public void jooqDemo(){
		
		Result<Record> result = create.select().from(TSysConfig.T_SYS_CONFIG).fetch();
		List<TSysConfigRecord> result3 = create.select().from(TSysConfig.T_SYS_CONFIG).fetchInto(TSysConfigRecord.class);
		
		for (Record r : result) {
    	    //Integer id = r.getValue(TSysConfig.T_SYS_CONFIG.ID);
    		
    	    String keyName = r.getValue(TSysConfig.T_SYS_CONFIG.KEYNAME);
    	    String keyValue = r.getValue(TSysConfig.T_SYS_CONFIG.KEYVALUE);
    	    System.out.println("keyName：" + keyName + "			keyValue:：" + keyValue);
    	}
		
	}
	
	
}
