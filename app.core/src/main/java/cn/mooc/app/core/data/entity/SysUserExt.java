package cn.mooc.app.core.data.entity;

import java.io.Serializable;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户扩展信息
 * 
 * @author Taven
 *
 */
@Document(collection = "t_sys_u_ext")
public class SysUserExt implements Serializable {

	private static final long serialVersionUID = 694745077680167831L;

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
	private String nickName;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	private String userName;
	/**
	 * 头像地址
	 */
	private String avatarUrl;
	
	
	/**
	 * 性别：Female：女，Male：男
	 * （保密：0, 男：1, 女：2）
	 */
	private int gender;
	
	
	/**
	 * 电话
	 */
	private String phone;
	
	
	/**
	 * 邮箱
	 */
	private String email;
	
	private String bankCard;


	public ObjectId getId() {
		return id;
	}


	public String getBankCard() {
		if(bankCard==null) {
			bankCard="";
		}
		return bankCard;
	}


	public void setBankCard(String bankCard) {
		if(bankCard==null) {
			bankCard="";
		}
		this.bankCard = bankCard;
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


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getAvatarUrl() {
		return avatarUrl;
	}


	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}


	public int getGender() {
		return gender;
	}


	public void setGender(int gender) {
		this.gender = gender;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 性别：Female：女，Male：男
	 * （保密：0, 男：1, 女：2）
	 */
	public String getGenderStr(){
		if(this.gender == 1){
			return "男";
		}else if(this.gender == 2){
			return "女";
		}else {
			return "保密";
		}
		
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
