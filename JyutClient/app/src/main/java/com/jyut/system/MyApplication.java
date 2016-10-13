package com.jyut.system;

import android.app.Application;
import android.os.Build;
import android.view.Window;

import com.yolanda.nohttp.NoHttp;

/**
 @date 10/3/2016
 @author wztscau
 @project 粤盟管理系统客户端
 *
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
    }
}
