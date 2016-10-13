package com.jyut.system.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 *    @date 10/7/2016
 *    @author wztscau
 *    @project 粤盟管理系统客户端
 */

public class Message implements Parcelable {

    private String title;
    private String content;
    private boolean readed;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeByte(this.readed ? (byte) 1 : (byte) 0);
        dest.writeString(this.date);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.readed = in.readByte() != 0;
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
