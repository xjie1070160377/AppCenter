package cn.mooc.app.core.data.entity;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import cn.mooc.app.core.enums.LogType;

@Document(collection = "t_sys_log")
public class SysLogEntity {

	@Id
	private ObjectId id;
	/**
	 * 用户ID
	 */
	@Indexed
	private long userId;
	
	/**
	 * 用户昵称
	 */
	@Indexed
	private String userName;

	
	/**
	 * 0:系统日志,1:登录日志,2:操作日志
	 */
	@Indexed
	private LogType logType;

	/**
	 * 用户操作IP
	 */
	private String userIp;
	/**
	 * 操作点
	 */
	private String oprPoint;
	/**
	 * 日志描述
	 */
	private String logDesc;
	/**
	 * 
	 */
	@Indexed(direction = IndexDirection.DESCENDING)
	private Date createTime;
			
	/**
	 * 模块名称
	 */
	@Indexed
	private String moduleName;
	/**
	 * 模块自己定义的日志分类
	 */
	@Indexed
	private String moduleLogType;
	
	
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public LogType getLogType() {
		return logType;
	}
	public void setLogType(LogType logType) {
		this.logType = logType;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getOprPoint() {
		return oprPoint;
	}
	public void setOprPoint(String oprPoint) {
		this.oprPoint = oprPoint;
	}
	public String getLogDesc() {
		return logDesc;
	}
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleLogType() {
		return moduleLogType;
	}
	public void setModuleLogType(String moduleLogType) {
		this.moduleLogType = moduleLogType;
	}
	
	
	
	
}
