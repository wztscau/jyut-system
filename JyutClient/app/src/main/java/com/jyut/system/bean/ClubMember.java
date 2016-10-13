
package com.jyut.system.bean;

import android.os.Parcel;

/**
 *
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class ClubMember extends Member {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3419243660401973595L;
	private String locale;
	private String club;
	private String tel;
	private String qq;
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

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(this.locale);
		dest.writeString(this.club);
		dest.writeString(this.tel);
		dest.writeString(this.qq);
		dest.writeString(this.wechat);
		dest.writeString(this.modifier);
		dest.writeString(this.head_path);
		dest.writeString(this.name);
		dest.writeString(this.school);
		dest.writeString(this.tel);
		dest.writeInt(this.id);
	}

	protected ClubMember(Parcel in) {
		super(in);
		this.locale = in.readString();
		this.club = in.readString();
		this.tel = in.readString();
		this.qq = in.readString();
		this.wechat = in.readString();
		this.modifier = in.readString();
		this.head_path = in.readString();
		this.name = in.readString();
		this.school = in.readString();
		this.tel = in.readString();
		this.id = in.readInt();
	}

	public static final Creator<ClubMember> CREATOR = new Creator<ClubMember>() {
		@Override
		public ClubMember createFromParcel(Parcel source) {
			return new ClubMember(source);
		}

		@Override
		public ClubMember[] newArray(int size) {
			return new ClubMember[size];
		}
	};
}
