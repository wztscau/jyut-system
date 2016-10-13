package com.jyut.system.fragement.functions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jyut.system.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wztscau
 * @date 10/5/2016
 * @project 粤盟管理系统客户端
 */

public class MessageSenderFragment extends BaseFragment {

    @Bind(R.id.btn_send)
    AppCompatButton btnSend;

    public MessageSenderFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_func_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_send)
    public void sendMessage(){
        Toast.makeText(getContext(),"信息已发出",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
