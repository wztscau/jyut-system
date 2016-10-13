package com.jyut.system.fragement.functions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jyut.system.R;

/**
 *
 *  @date 10/5/2016
 *  @author wztscau
 *  @project 粤盟管理系统客户端
 *
 */

public class ClubCheckFragment extends BaseFragment{

    public ClubCheckFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.function_undone,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

}
