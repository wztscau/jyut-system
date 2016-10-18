package com.jyut.system;

import android.app.Application;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;


/**
 * @author wztscau
 * @date 10/3/2016
 * @project 粤盟管理系统客户端
 */
public class MyApplication extends Application {

    private static MyApplication mApplication;

    public static MyApplication getApplication(){return mApplication;}

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        NoHttp.initialize(this);
        Logger.setDebug(true);
    }
}
