package cn.mooc.app.core.service;

/**
 * 邮件发送接口
 * 
 * @author Taven
 *
 */
public interface MailService {

	/**
	 * 发送一封邮件
	 * 
	 * @param toEMail
	 * @param title
	 * @param content
	 * @return
	 */
	boolean sendMail(String toEMail, String title, String content);
	
	/**
	 * 群发邮件
	 * 
	 * @param toEMail
	 * @param title
	 * @param content
	 * @return
	 */
	boolean sendMail(String[] toEMail, String title, String content);
	
	
}
