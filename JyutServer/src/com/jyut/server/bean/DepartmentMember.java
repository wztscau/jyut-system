
package com.jyut.server.bean;

/**
 * 
 * @date Sep 22, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public class DepartmentMember extends Member {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7117205925356989630L;
	private String head_path;
	private String sex;
	private String nickName;
	private String account;
	private int status;
	private int permission;
	
	public DepartmentMember(){
		status = 1;
		permission = 5;
		nickName = "unname";
	}
	/**
	 * @return the head_path
	 */
	public String getHead_path() {
		return head_path;
	}
	/**
	 * @param head_path the head_path to set
	 */
	public void setHead_path(String head_path) {
		this.head_path = head_path;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the permission
	 */
	public int getPermission() {
		return permission;
	}
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(int permission) {
		this.permission = permission;
	}
	
}
