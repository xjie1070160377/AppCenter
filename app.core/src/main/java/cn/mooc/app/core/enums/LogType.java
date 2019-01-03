package cn.mooc.app.core.enums;


public enum LogType {
	
	/**
	 * 系统操作日志
	 */
	SysOpr(0),
	/**
	 * 用户登录日志
	 */
	UserLogin(1),
	/**
	 * 用户操作日志
	 */
	UserOpr(2),
	/**
	 * 用户退出日志
	 */
	UserLogout(3),
/*	
	*//**
	 * 后台登录
	 *//*
	MUserLogin(4),
	
	*//**
	 * 后台退出
	 *//*
	MUserLogout(5),*/
	/**
	 * 积分操作
	 */
	PointsOpr(6),
	/**
	 * 推送操作
	 */
	PushOpr(7);
	
	private final int value;

	LogType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
}
