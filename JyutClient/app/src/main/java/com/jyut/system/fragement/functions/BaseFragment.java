package com.jyut.system.fragement.functions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wztscau
 * @date 10/4/2016
 * @project 粤盟管理系统客户端
 */

public abstract class BaseFragment extends Fragment {

    protected BaseFragment(){
    }

    @Nullable
    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public abstract void onViewCreated(View view, @Nullable Bundle savedInstanceState) ;
}
