package com.jyut.system.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C;
import com.jyut.system.bean.Member;
import com.jyut.system.util.JsonDealer;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;

/**
 *
 *    这个类用来作为处理从服务器返回的data数据里面的JsonArrayString的父类，
 *    需要子类去实现处理返回JsonArray的方法。
 *    @date 10/10/2016
 *    @author wztscau
 *    @project 粤盟管理系统客户端
 *
 */

public abstract class JsonArrayResponseAdapter extends JsonResponseAdapter<JSONObject>{

    private static String TAG;
    public JsonArrayResponseAdapter(Context context){
        super(context);
        TAG = getClass().getName();
    }

    @Override
    public void onSucceed(int what, Response<JSONObject> response) {
        super.onSucceed(what, response);
        // 服务器返回的JsonObject对象
        JSONObject jsonObject = response.get();
        // 返回JsonObject里面的Message数据
        String msg = jsonObject.getString(C.L.MESSAGE);
        if (C.S.QUERY_SUCCESS.equals(msg)) {
//            Log.i(C.TAG_CLUB, jsonObject.getString(C.L.DATA));
            // 提取JsonObject里面的data数据
            String jsonString = String.valueOf(jsonObject.get(C.L.DATA));
            // 将JsonString转成JsonArray
            JSONArray jsonArray = JSONArray.parseArray(jsonString);
            Log.i(C.T.RESPONSE, "onSucceed::jsonArray::"+jsonArray.toJSONString());

            // 子类实现处理结果数据的方法
            displayResult(jsonArray);
        } else {
            onFailed(what, response);
            onInconformityError(msg,response);
        }
    }

    /**
     * 子类实现处理结果数据的方法
     * @param jsonArray 返回的data里面的JsonArray数据
     */
    protected abstract void displayResult(JSONArray jsonArray);

    /**
     * 不符合条件的返回结果,以error形式呈现
     * @param msg 返回的message数据
     * @param response 返回的response兑现
     */
    protected void onInconformityError(String msg,Response<JSONObject> response){}

}
