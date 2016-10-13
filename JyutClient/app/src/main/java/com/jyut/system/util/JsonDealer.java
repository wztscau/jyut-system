/**
 *
 */
package com.jyut.system.util;

import android.os.Parcelable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 一个处理json的工具类
 *
 * @author wztscau
 * @date Sep 24, 2016
 * @project 粤盟管理系统客户端
 */
public class JsonDealer {

    /**
     * 将bean转换成jsonobject的方法
     *
     * @param t bean
     * @return
     */
    public static <T> JSONObject beanToJsonObject(T t) {
        // 先由Bean转换成JsonString，再由JsonString转换成JsonObject。
        String jsonString = JSON.toJSONString(t);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return jsonObject;
    }

    /**
     * 将jsonobject转换成bean
     *
     * @param obj jsonobject
     * @param clz bean类型
     * @return
     */
    public static <T> T jsonObjectToBean(JSONObject obj, Class<T> clz) {
        T bean = JSON.toJavaObject(obj, clz);
        return bean;
    }

    /**
     * 将jsonstring转换成map
     *
     * @param jsonString
     * @return
     */
    public static Map<String, String> jsonStringToMap(String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Set<Entry<String, Object>> entrySet = jsonObject.entrySet();
        Map<String, String> map = new HashMap<>();
        for (Entry<String, Object> entry : entrySet) {
            String key = entry.getKey().toLowerCase();
            String value = String.valueOf(entry.getValue()).toLowerCase();
            map.put(key, value);
        }
        return map;
    }

    /**
     * 将JsonArray转换层bean的list集合,默认数量为10
     *
     * @see #jsonArrayToBeanList(JSONArray, Class, int)
     */
    public static <T> ArrayList<? extends Parcelable> jsonArrayToBeanList(JSONArray jsonArray, Class<T> clz) {
        return jsonArrayToBeanList(jsonArray, clz, 10);
    }

    /**
     * 将JsonArray转换层bean的list集合,可控制转换数量
     *
     * @param jsonArray
     * @param clz
     * @param count
     * @param <T>
     * @return
     */
    public static <T> ArrayList<? extends Parcelable> jsonArrayToBeanList(JSONArray jsonArray, Class<T> clz, int count) {
        ArrayList<Parcelable> memberList = new ArrayList<>();
        Iterator<Object> iterator = jsonArray.iterator();
        int i = 0;
        while (iterator.hasNext() && i++ < count) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            Log.i(C.T.RESPONSE, "jsonArrayToBeanList: " + jsonObject.toJSONString());
            Parcelable parcelable = (Parcelable) JSON.toJavaObject(jsonObject, clz);
            memberList.add(parcelable);
        }
        return memberList;
    }

}
