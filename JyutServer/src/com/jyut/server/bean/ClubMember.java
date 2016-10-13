
package com.jyut.server.bean;

/**
 * 
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统
 */
public class ClubMember extends Member {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3419243660401973595L;
	private String locale;
	private String club;
	private String tel;
	private String QQ;
	private String wechat;
	private String modifier;
	private String head_path;

	public ClubMember() {

	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the club
	 */
	public String getClub() {
		return club;
	}

	/**
	 * @param club the club to set
	 */
	public void setClub(String club) {
		this.club = club;
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
	 * @return the qQ
	 */
	public String getQQ() {
		return QQ;
	}

	/**
	 * @param qQ the qQ to set
	 */
	public void setQQ(String qQ) {
		QQ = qQ;
	}

	/**
	 * @return the wechat
	 */
	public String getWechat() {
		return wechat;
	}

	/**
	 * @param wechat the wechat to set
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	/**
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
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



}
