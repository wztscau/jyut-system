
package com.jyut.system.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public abstract class Member implements Parcelable {
    /**
     *
     */
    private static final long serialVersionUID = -6636205630294241035L;
    String name;
    String school;
    String tel;
    int id;
    @JSONField(serialize = false, deserialize = false)
    Bitmap head;

    public Bitmap getHead() {
        return head;
    }

    public void setHead(Bitmap icon) {
        this.head = icon;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.school);
        dest.writeString(this.tel);
        dest.writeInt(this.id);
    }

    public Member() {
    }

    protected Member(Parcel in) {
        this.name = in.readString();
        this.school = in.readString();
        this.tel = in.readString();
        this.id = in.readInt();
    }

}
