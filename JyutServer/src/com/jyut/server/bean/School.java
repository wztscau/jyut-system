package com.jyut.server.bean;


/**
 *    @date 10/10/2016
 *    @author wztscau
 *    @project 粤盟管理系统服务端
 *
 */

public class School  {

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

}
