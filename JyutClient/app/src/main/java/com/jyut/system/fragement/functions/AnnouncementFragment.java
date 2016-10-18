package com.jyut.system.fragement.functions;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.jyut.system.C;
import com.jyut.system.LoginActivity;
import com.jyut.system.R;
import com.jyut.system.adapter.JsonArrayResponseAdapter;
import com.jyut.system.bean.School;
import com.jyut.system.http.HttpJsonRequest;
import com.jyut.system.util.JsonDealer;
import com.jyut.system.util.TextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wztscau
 * @date 10/5/2016
 * @project 粤盟管理系统客户端
 */

public class AnnouncementFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.spn_provinces)
    AppCompatSpinner spnProvinces;
    @Bind(R.id.spn_schools)
    AppCompatSpinner spnSchools;
    @Bind(R.id.btn_send)
    Button btnSend;
    private String locale;
    private String school;
    private String title;
    private String content;

    public AnnouncementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_func_annotation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        spnProvinces.setOnItemSelectedListener(this);
        spnSchools.setOnItemSelectedListener(this);

        loadSchools(TextUtil.getSpinnerSelection(spnProvinces));
    }

    public void loadSchools(String text) {
        HttpJsonRequest request = new HttpJsonRequest(LoginActivity.URL_SERVER + C.PATH_SERVER_SCHOOL_QUERY);
        request.setOnResponseListener(new JsonResponse(getContext()));
        List<String> columnsLimited = new ArrayList<>();
        List<String> valuesLimited = new ArrayList<>();
        columnsLimited.add(C.L.LOCALE);
        valuesLimited.add(text);

        Map<String, Object> map = new HashMap<>();
        map.put(C.L.COLUMNS_LIMITED, columnsLimited);
        map.put(C.L.VALUES_LIMITED, valuesLimited);
        map.put(C.L.ORDER_BY, C.L.SCHOOL);
        if ("".equals(text)) {
            request.sendRequest(null);
            return;
        }
        request.sendRequest(map);
}

    class JsonResponse extends JsonArrayResponseAdapter {

        public JsonResponse(Context context) {
            super(context);
        }

        @Override
        protected void displayResult(JSONArray jsonArray) {
            ArrayList<? extends Parcelable> parcelables = JsonDealer.jsonArrayToBeanList(jsonArray, School.class);
            ArrayList<String> schoolNameList = new ArrayList<>();
            if (parcelables.isEmpty()) {
                schoolNameList.add("无");
            } else {
                schoolNameList.add(getResources().getString(R.string.all));
            }
            for (Parcelable p : parcelables) {
                schoolNameList.add(((School) p).getSchool());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, schoolNameList);
            spnSchools.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_send)
    public void sendMessage(){
        Toast.makeText(getContext(),"消息已发出",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // view.id不是spinner的id，而是里面item的id，parent.id才是spinner的id
        switch (parent.getId()) {
            case R.id.spn_provinces:
                loadSchools(TextUtil.getSpinnerSelection(spnProvinces));
                break;
            case R.id.spn_schools:
                school = spnSchools.getSelectedItem().toString().trim();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
