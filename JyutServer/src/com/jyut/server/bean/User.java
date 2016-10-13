/**
 * 
 */
package com.jyut.server.bean;

/**
 * @author wztscau
 * @date Sep 26, 2016
 * @project 粤盟管理系统
 */
public class User {

	private String userName;
	private String password;
	private String permission;
	
	public User(){
		// Default constructor
	}
	/**
	 * @param userName
	 * @param password
	 */
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
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
	public void setUserName(String userName) {
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
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}
}
