package com.jyut.system.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *    @date 10/10/2016
 *    @author wztscau
 *    @project 粤盟管理系统客户端
 *
 */

public class School implements Parcelable {

    private String locale;

    public School(String locale, String school) {
        this.locale = locale;
        this.school = school;
    }

    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.locale);
        dest.writeString(this.school);
    }

    public School() {
    }

    protected School(Parcel in) {
        this.locale = in.readString();
        this.school = in.readString();
    }

    public static final Parcelable.Creator<School> CREATOR = new Parcelable.Creator<School>() {
        @Override
        public School createFromParcel(Parcel source) {
            return new School(source);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };
}
