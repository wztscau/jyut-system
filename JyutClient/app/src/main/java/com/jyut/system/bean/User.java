/**
 * 
 */
package com.jyut.system.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.jyut.system.C;
import com.jyut.system.util.Encryption;

/**
 * @author wztscau
 * @date Sep 26, 2016
 * @project 粤盟管理系统客户端
 */
public class User {

	private String userName;
	private String password;
	private String permission;
	public static User mUser;

	private transient String uname;

	public String getUname() {
		return uname;
	}

	public static User getInstance(){
		return getInstance("","");
	}

	public static User getInstance(String userName, String password){
		if(mUser==null){
			mUser = new User(userName,password);
		}
		return mUser;
	}

	private User(){
		// Default constructor
		permission = "5";
	}
	/**
	 * @param userName
	 * @param password
	 */
	private User(String userName, String password) {
		this();
		this.userName = userName;
		this.password = password;
		uname = userName;
		if(C.ENCRYTED){
			this.userName = Encryption.encryptMD5(userName);
			this.password = Encryption.encryptMD5(password);
		}
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	private void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	private void setPassword(String password) {
		this.password = password;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}

}
