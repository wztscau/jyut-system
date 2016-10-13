package com.jyut.system.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 *    @date 10/7/2016
 *    @author wztscau
 *    @project 粤盟管理系统客户端
 *
 */

public abstract class BaseHandler<T> extends Handler {

    private WeakReference<T> mWeakReference;

    public BaseHandler(T t) {
        super();
        mWeakReference = new WeakReference<T>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        //doSomething();
        T t= mWeakReference.get();
        if(t != null){
            handleMessage(msg, t);
        }
    }

    public abstract void handleMessage(Message msg, T t);
}
