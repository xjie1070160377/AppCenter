package cn.mooc.app.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginCheck {

	/**
	 * 指定登录页面URL，可以是相对路径
	 * 
	 * @return
	 */
	@AliasFor("value")
	String loginUrl() default "";

	/**
	 * 指定登录页面URL，可以是相对路径
	 * 
	 * @return
	 */
	@AliasFor("loginUrl")
	String value() default "";
	
	/**
	 * 对已登录的用户，执行扩展逻辑
	 * 
	 * @return
	 */
	String hasLoginMethod() default "hasLogined";
	
}
