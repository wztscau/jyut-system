package com.jyut.system.fragement.functions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C;
import com.jyut.system.LoginActivity;
import com.jyut.system.R;
import com.jyut.system.adapter.JsonResponseAdapter;
import com.jyut.system.bean.School;
import com.jyut.system.http.HttpJsonRequest;
import com.jyut.system.util.TextUtil;
import com.yolanda.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wztscau
 * @date 10/5/2016
 * @project 粤盟管理系统客户端
 */

public class SchoolFragment extends BaseFragment {

    @Bind(R.id.btn_add)
    AppCompatButton btnAdd;
    @Bind(R.id.et_locale)
    EditText etLocale;
    @Bind(R.id.et_school)
    EditText etSchool;
    @Bind(R.id.lil_container)
    LinearLayout lilContainer;

    public SchoolFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_func_school, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_add)
    public void add() {
        boolean  b = attemptSend();
        if(!b){
            return ;
        }
        School school = new School(etLocale.getText().toString().trim(), etSchool.getText().toString().trim());
        HttpJsonRequest request = new HttpJsonRequest(LoginActivity.URL_SERVER + C.PATH_SERVER_SCHOOL_INSERT);
        request.setOnResponseListener(new JsonResponse());
        request.sendRequest(school);

    }

    private boolean attemptSend() {
        if(TextUtil.isEmpty(etLocale)){
            etLocale.setError(getResources().getString(R.string.error_locale_empty));
            return false;
        }
        String[] array = getResources().getStringArray(R.array.provinces);
        boolean b = true;
        for (int i = 1; i < array.length; i++) {
            b = false;
            if (array[i].substring(3).equals(TextUtil.getText(etLocale))) {
                b = true;
                break;
            }
        }
        if (b == false) {
            etLocale.setError(getResources().getString(R.string.error_locale));
            return false;
        }
        if(TextUtil.isEmpty(etSchool)){
            etSchool.setError(getResources().getString(R.string.error_school_empty));
            return false;
        }
        return true;
    }

    class JsonResponse extends JsonResponseAdapter<JSONObject> {
        @Override
        public void onSucceed(int what, Response<JSONObject> response) {
            super.onSucceed(what, response);
            String msg = response.get().getString(C.L.MESSAGE);
            if (C.S.INSERT_SUCCESS.equals(msg)|| C.S.UPDATE_SUCCESS.equals(msg)) {
                Snackbar.make(lilContainer, msg, Snackbar.LENGTH_SHORT).show();
            } else {
                onFailed(what, response);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
