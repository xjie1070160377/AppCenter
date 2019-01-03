package cn.mooc.jooq.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cn.mooc.jooq.demo.generated.tables.TSysConfig;
import cn.mooc.jooq.demo.generated.tables.records.TSysConfigRecord;





/**
 * Hello world!
 *
 */
public class JooQMysqlDemoApp 
{
    public static void main( String[] args )
    {

    	System.out.println("----------------------------------------");
    	
    	System.out.println("------------------开始----------------------");
    	
    	String userName = "root";
        String password = "grant";
        String url = "jdbc:mysql://localhost:3306/appcenter";
        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
        	
        	DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
        	Result<Record> result = create.select().from(TSysConfig.T_SYS_CONFIG).fetch();
        	
        	//Condition conditions = DSL.condition(true);
        	//conditions = conditions.and(TSysConfig.T_SYS_CONFIG.KEYNAME.like("sys.%"));        	
        	//Result<Record> result1 = create.select().where(conditions).fetch();
        	
        	List<TSysConfigRecord> result2 = create.select().from(TSysConfig.T_SYS_CONFIG).fetch().into(TSysConfigRecord.class);
        	List<TSysConfigRecord> result3 = create.select().from(TSysConfig.T_SYS_CONFIG).fetchInto(TSysConfigRecord.class);
        	
        	System.out.println("---------------取到数据："+result.size()+"-----------------");
        	for (Record r : result) {
        	    //Integer id = r.getValue(TSysConfig.T_SYS_CONFIG.ID);
        		
        	    String keyName = r.getValue(TSysConfig.T_SYS_CONFIG.KEYNAME);
        	    String keyValue = r.getValue(TSysConfig.T_SYS_CONFIG.KEYVALUE);
        	    System.out.println("keyName：" + keyName + "			keyValue:：" + keyValue);
        	}
        	
        	        	
        	
        } 
        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
        System.out.println("----------------------------------------");
    
    }
}
