
package com.jyut.system.bean;

import android.os.Parcel;

/**
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class DepartmentMember extends Member {

    /**
     *
     */
    private static final long serialVersionUID = -7117205925356989630L;
    private String head_path;
    private String sex;
    private String nickname;
    private String account;
    private int permission;
    private int status;

    public DepartmentMember() {

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.head_path);
        dest.writeString(this.sex);
        dest.writeString(this.nickname);
        dest.writeString(this.account);
        dest.writeInt(this.permission);
        dest.writeInt(this.status);
        dest.writeString(this.name);
        dest.writeString(this.school);
        dest.writeString(this.tel);
        dest.writeInt(this.id);
    }

    protected DepartmentMember(Parcel in) {
        super(in);
        this.head_path = in.readString();
        this.sex = in.readString();
        this.nickname = in.readString();
        this.account = in.readString();
        this.permission = in.readInt();
        this.status = in.readInt();
        this.name = in.readString();
        this.school = in.readString();
        this.tel = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<DepartmentMember> CREATOR = new Creator<DepartmentMember>() {
        @Override
        public DepartmentMember createFromParcel(Parcel source) {
            return new DepartmentMember(source);
        }

        @Override
        public DepartmentMember[] newArray(int size) {
            return new DepartmentMember[size];
        }
    };
}
