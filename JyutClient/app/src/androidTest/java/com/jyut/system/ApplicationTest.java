package com.jyut.system;

import android.app.Application;
import android.content.Intent;
import android.os.Parcelable;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jyut.system.bean.ClubMember;
import com.jyut.system.util.Encryption;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "test";

    public ApplicationTest() {
        super(Application.class);
    }

    public void test1(){
        Intent intent = new Intent(getContext(),getContext().getClass());
        ClubMember member = new ClubMember();
        member.setClub("jingchou");
        member.setLocale("guangdong");
        ArrayList<Parcelable> list = new ArrayList<>();
        for(int i  = 0 ;i< 3 ;i++){
            list.add(member);
        }
        intent.putParcelableArrayListExtra(C.MEMBER,list);

//        Intent intent1 =
        ArrayList<Parcelable> parcelables = intent.getParcelableArrayListExtra(C.MEMBER);
        Log.i(TAG, "test1: " + parcelables.toString());


    }

    public void testJsonTransient(){
        User1 user1 = User1.getInstance("username", "password");
        String jsonString = JSON.toJSONString(user1);
        System.out.println(jsonString);

    }

    public static class User1 {

        private String userName;
        private String password;
        private String permission;
        private static User1 mUser;
        private transient String uname;

        public String getUname() {
            return uname;
        }

        public static User1 getInstance(String userName, String password){
            if(mUser==null){
                mUser = new User1(userName,password);
            }
            return mUser;
        }

         User1(){
            // Default constructor
            permission = "5";
        }
        /**
         * @param userName
         * @param password
         */
         User1(String userName, String password) {
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
}