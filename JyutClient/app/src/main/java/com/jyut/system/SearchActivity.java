/**
 *
 */
package com.jyut.system;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C.L;
import com.jyut.system.C.S;
import com.jyut.system.adapter.JsonResponseAdapter;
import com.jyut.system.bean.ClubMember;
import com.jyut.system.bean.DepartmentMember;
import com.jyut.system.bean.SerialiableMap;
import com.jyut.system.http.HttpJsonRequest;
import com.jyut.system.util.JsonDealer;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jyut.system.util.TextUtil.getSpinnerSelection;

/**
 * 查找数据的界面
 *
 * @author wztscau
 * @date Sep 29, 2016
 * @project 粤盟管理系统客户端
 */
public class SearchActivity extends Activity {

    private static final String TAG = "SearchActivity";
    @Bind(R.id.ibtn_search)
    ImageButton ibtnSearch;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.spn_provinces)
    Spinner spnProvinces;
    /**
     * 需要查找的bean类型
     */
    private String type;
    /**
     * 提供一个序列化的map,用来传递数据
     */
    private SerialiableMap<String, Object> serialiableMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bar);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra(C.TYPE);
        initView();
    }

    private void initView() {
        etSearch.setTextColor(Color.WHITE);
        switch (type) {
            case C.TYPE_DEPT:
                spnProvinces.setVisibility(View.GONE);
                etSearch.setHint(R.string.input_name_or_school);
                break;
            case C.TYPE_CLUB:
                etSearch.setHint(R.string.input_name_or_school_club);
                break;
        }
    }


    @OnClick(R.id.ibtn_search)
    public void search() {
        String value = etSearch.getText().toString().trim();
        String url = LoginActivity.URL_SERVER;

        Map<String, Object> map = new HashMap<>();
        List<String> columnsAlter = new ArrayList<>();
        List<String> valuesLimited = new ArrayList<>();
        List<String> columnsLimited = new ArrayList<>();
        switch (type) {
            case C.TYPE_CLUB:
                url = url + C.PATH_SERVER_CLUB_QUERY;
                columnsAlter.add(L.SCHOOL);
                columnsAlter.add(L.NAME);
                columnsAlter.add(L.CLUB);
                columnsLimited.add(L.LOCALE);
                valuesLimited.add(getSpinnerSelection(spnProvinces));
                break;
            case C.TYPE_DEPT:
                url = url + C.PATH_SERVER_DEPT_QUERY;
                columnsAlter.add(L.NAME);
                columnsAlter.add(L.SCHOOL);
                break;
            default:
                throw new IllegalArgumentException("We have not this type [" + type + "] Have you input the wrong type?");

        }
        map.put(L.COLUMNS_LIMITED, columnsLimited);
        map.put(L.VALUES_LIMITED, valuesLimited);
        map.put(L.VALUES_ALTER, value);
        map.put(L.COLUMNS_ALTER, columnsAlter);
        // 创建请求
        HttpJsonRequest request = new HttpJsonRequest(url);
        Log.d(TAG, "search: " + url);
        // 为请求添加回应监听
        request.setOnResponseListener(new JsonResponse());
        if ("".equals(getSpinnerSelection(spnProvinces))) {
            map.remove(L.COLUMNS_LIMITED);
            map.remove(L.VALUES_LIMITED);
            Log.d(TAG, "search: " + map);

            // 实例化序列化的map
            serialiableMap = new SerialiableMap<>();
            serialiableMap.setMap(map);
            if (TextUtils.isEmpty(value)) {
                // 请求对象为null,说明没有筛选条件(全部数据)
                request.sendRequest(null);
                return;
            }
        }
        Log.d(TAG, "search: " + map);
        // 发送请求
        request.sendRequest(map);
    }

    class JsonResponse extends JsonResponseAdapter<JSONObject> {

        @Override
        public void onSucceed(int what, Response<JSONObject> response) {
            super.onSucceed(what, response);
            JSONObject jsonObject = response.get();
            if (jsonObject == null) {
                onFailed(what, response);
                return;
            }
            Log.i(C.T.RESPONSE, jsonObject.toJSONString());
            String msg = jsonObject.getString(L.MESSAGE);
            if (S.QUERY_SUCCESS.equals(msg)) {
                Log.i(TAG, jsonObject.getString(L.DATA));
                String jsonString = String.valueOf(jsonObject.get(L.DATA));
                // 因为是查询,所以返回的数据是array(多条数据jsonobject做不到)
                JSONArray jsonArray = JSONArray.parseArray(jsonString);
                Log.i(C.T.RESPONSE, jsonArray.toJSONString());

                //最后处理结果
                displayResult(jsonArray);
            } else {
                Toast.makeText(SearchActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }

        private void displayResult(JSONArray jsonArray) {
            Intent intent = new Intent();

            // 这个不可能为空吧,哈哈
            ArrayList<? extends Parcelable> beanList = null;
            switch (type) {
                case C.TYPE_CLUB:
                    beanList = JsonDealer.jsonArrayToBeanList(jsonArray, ClubMember.class, C.PAGE_SIZE_DEFAULT);
                    intent.setClass(SearchActivity.this, MainActivity.class);
                    break;
                case C.TYPE_DEPT:
                    beanList = JsonDealer.jsonArrayToBeanList(jsonArray, DepartmentMember.class, C.PAGE_SIZE_DEFAULT);
                    intent.setClass(SearchActivity.this, DepartmentActivity.class);
                    break;
                default:
                    throw new IllegalArgumentException("We have not this type [" + type + "] Have you input the wrong type?");
            }
            Log.i(C.T.RESPONSE, "displayResult: " + beanList.toString());

            // 把传递的对象都放入bundle中
            Bundle bundle = new Bundle();
            bundle.putSerializable(L.QUERY, serialiableMap);
            bundle.putParcelableArrayList(C.MEMBER, beanList);
            bundle.putString(L.LOCALE, getSpinnerSelection(spnProvinces));
            // 最后还要将bundle放进intent里面
            intent.putExtras(bundle);
            startActivity(intent);
//        finish();
        }
    }


}
