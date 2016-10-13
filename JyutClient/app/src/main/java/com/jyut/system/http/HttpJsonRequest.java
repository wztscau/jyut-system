/**
 *
 */
package com.jyut.system.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C;
import com.jyut.system.util.Encryption;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * 一个对fastjson再次封装的类,不仅创建请求而且发送请求和一些发送前的数据处理
 *
 * @author wztscau
 * @date Sep 25, 2016
 * @project 粤盟管理系统客户端
 */
public class HttpJsonRequest {

    private static final String TAG = "HttpJsonRequest";
    private OnResponseListener<JSONObject> listener;
    private String url;
    /**
     * 所要处理的json请求数据
     */
    private FastjsonRequest request;

    public HttpJsonRequest(String url) {
        this.url = url;
    }

    /**
     * 发送请求,包含创建,处理数据,和发送过程
     *
     * @param obj 要发送的bean对象
     */
    public void sendRequest(Object obj) {
        // 1.首先创建请求对象
        request = new FastjsonRequest(url, RequestMethod.GET);
        // 2.把bean转换成json对象
        String jsonData = JSON.toJSONString(obj);
        // 3.加密
        if (C.ENCRYTED) {
            jsonData = encryJson(jsonData);
        }
        // 4.将json对象放入请求的data字段中
        request.addData(jsonData);
        // 5.创建消息队列
        RequestQueue queue = NoHttp.newRequestQueue();
        // 6.发送请求
        execute(queue, request);
    }

    public void setOnResponseListener(OnResponseListener<JSONObject> listener) {
        this.listener = listener;
    }

    /**
     * 将json加密
     *
     * @param src 未加密的json
     * @return 加密了的json
     */
    private String encryJson(String src) {
        String data = src;
        Log.i(TAG, data);
        return Encryption.encryptAES(data);
    }

    /**
     * 使用队列发送请求
     *
     * @param queue   队列
     * @param request 请求
     */
    private void execute(RequestQueue queue, Request<JSONObject> request) {
        queue.add(C.WHAT_SUCCESS, request, listener);
    }

    /**
     * 增加自定义的请求字段和值
     *
     * @param key   字段名
     * @param value 字段值
     */
    public void add(String key, String value) {
        request.add(key, value);
    }

}
