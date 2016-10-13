package com.jyut.system.database;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C;
import com.jyut.system.bean.SerialiableMap;
import com.jyut.system.http.HttpJsonRequest;
import com.yolanda.nohttp.rest.OnResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wztscau
 * @date 10/9/2016
 * @project 粤盟管理系统客户端
 */

public class DataLoader {

    /**
     * 服务器的url,包含path
     */
    private final String url;
    private OnResponseListener<JSONObject> listener;
    /**
     * 用于封装数据的bundle
     */
    private Bundle bundle;
    /**
     * 是否需要清空数据列表
     */
    private boolean clearList;
    /**
     * 需要数据读取到的索引
     */
    private int index;
    private LoadListener loadListener;
    /**
     * 需要排序的字段名
     */
    private String orderBy;

    private static final String TAG = "DataLoader";

    public DataLoader(String url, Bundle bundle) {
        this.url = url;
        this.bundle = bundle;
        clearList = true;
        index = 1;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isClearList() {
        return clearList;
    }

    public void setClearList(boolean clearList) {
        this.clearList = clearList;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setOnResponseListener(OnResponseListener<JSONObject> listener) {
        this.listener = listener;
    }

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }

    /**
     * 开始向服务器发送请求,并且接收服务器传回的数据,数据封装在bundle中
     * 和bundle里面的SerialiableMap中,SerialiableMap里面放的是列名和
     * 所在列对应的值
     * @param count 需要一次读取多少条数据
     */
    public void loadData(int count) {
        if (clearList) {
            // 数据库查询的limit从0开始
            index = 0;
        }
        Log.d(TAG, "loadData: clearList--" + clearList);
        // 1.创建请求
        HttpJsonRequest request = new HttpJsonRequest(url);
        // 2.设置响应的监听器
        request.setOnResponseListener(listener);
        // 是否要读取全部数据,或者分页读取
        boolean b = count >= 0;
        // 监听开始
        if (loadListener != null) {
            loadListener.onLoadStart(request);
        }
        if (b) {
        /*
         * 以map的方式传递数据,分别是:
         * ①是否需要分页查询
         * ②分页查询的第一个值
         * ③分页的页面大小
         * ④是否需要排序,排序的字段名
         */
            Map<String, Object> map = new HashMap<>();
            map.put(C.L.LIMIT, b);
            map.put(C.L.LIMIT_START, index);
            map.put(C.L.LIMIT_SIZE, count);
            map.put(C.L.ORDER_BY, orderBy);
            Log.d(TAG, "boolean: " + b);
            if (bundle == null) {

            } else {
                // 从最大的封装类bundle里面提取封装列名和所在列的对应值
                SerialiableMap<String, Object> sMap = (SerialiableMap) bundle.getSerializable(C.L.QUERY);
                if (sMap != null) {
                    Map preMap = sMap.getMap();
                    map.put(C.L.COLUMNS_ALTER, preMap.get(C.L.COLUMNS_ALTER));
                    map.put(C.L.COLUMNS_LIMITED, preMap.get(C.L.COLUMNS_LIMITED));
                    map.put(C.L.VALUES_ALTER, preMap.get(C.L.VALUES_ALTER));
                    map.put(C.L.VALUES_LIMITED, preMap.get(C.L.VALUES_LIMITED));

                    Log.d(TAG, "loadData: map--" + map);
                    // 重新把SerialiableMap放入到bundle里面
                    bundle.putSerializable(C.L.QUERY, sMap);
                }
            }
            index += count;
            Log.d(TAG, "loadData: index" + index);
            request.sendRequest(map);
//            setBundle(bundle);
        } else {
            // 如果没有任何查询约束将查询所有数据
            request.sendRequest(null);
        }
        // 监听结束
        if (loadListener != null) {
            loadListener.onLoadFinish(bundle);
        }

    }


    /**
     * @author wztscau
     * @date 10/9/2016
     * @project 粤盟管理系统客户端
     */
    public interface LoadListener {

        void onLoadStart(HttpJsonRequest request);

        void onLoading();

        void onLoadFinish(Bundle bundle);
    }
}
