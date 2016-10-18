
package com.jyut.system.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C;
import com.jyut.system.C.L;
import com.jyut.system.util.Encryption;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;
import com.yolanda.nohttp.tools.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 一个自定义的NoHttp里面的Request类(fastjson)<br>
 * 在jsonobject里面自定义了三个字段data,message,error<br>
 * <a href="https://github.com/yanzhenjie/NoHttp">https://github.com/yanzhenjie/NoHttp</a>
 *
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class FastjsonRequest extends RestRequest<JSONObject> {

    private static final String TAG = "FastjsonRequest";
    /**
     * 请求方法GET,POST,PULL等
     */
    private String method;
    /**
     * 请求的url
     */
    private String url;

    /**
     * constructor
     * 默认为GET方法
     *
     * @param url
     */
    public FastjsonRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public FastjsonRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        this.url = url;
        this.method = requestMethod.name();
        // 设置服务器接受的请求对象类型
        setAccept(Headers.HEAD_VALUE_ACCEPT_APPLICATION_JSON);
    }

    @Override
    public void onPreExecute() {
        // 这个方法会在真正请求前被调用，在这里可以做一些加密之类的工作。这个方法在子线程被调用。
        // 比如，我们做个模拟加密：
        MultiValueMap<String, Object> multiValueMap = getParamKeyValues();
        Set<String> keySet = multiValueMap.keySet();
        for (String key : keySet) {
            List<Object> values = multiValueMap.getValues(key);// POST, PUT,
            // DELETE,
            //PATCH请求方法传文件的时候，一个Key下允许有多个值。
            for (Object value : values) {
                // 这里就拿到所有的参数值了，你可以做加密啦。
            }
        }
    }

    /**
     * nohttp1.0.6少了一个参数url，1.0.3就有，其他未测试过 。
     *
     * @param responseHeaders 服务端的响应头
     * @param responseBody    服务端的响应数据
     * @return 你解析后的对象
     */
    @Override
    public JSONObject parseResponse(Headers responseHeaders, byte[] responseBody) {
        return parse(responseHeaders, responseBody);
    }

    /**
     * 解析服务端数据成{@link com.alibaba.fastjson.JSONObject}
     * 这是服务器返回的数据
     *
     * @param responseHeaders
     * @param responseBody
     * @return
     */
    private JSONObject parse(Headers responseHeaders, byte[] responseBody) {
        String response = StringRequest.parseResponseString(responseHeaders, responseBody);
        if (C.ENCRYTED) {
            response = Encryption.decryptAES(response);
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(response);
            Log.i(TAG, "parse succeed");
        } catch (Exception e) {
            // 可能返回的数据不是json，或者其他异常
            // 主要此处jsonObject不为空就可以
            Map<String, Object> map = new HashMap<>();
            map.put(L.ERROR, -1);
            map.put(L.URL, url);
            map.put(L.DATA, response);
            map.put(L.METHOD, method);
            jsonObject = (JSONObject) JSON.toJSON(map);
            Log.i(TAG, "parse failed");
        }
        return jsonObject;
    }

    /**
     * 设置自定义字段里面的data字段
     *
     * @param data data内容
     */
    public void addData(String data) {
        add(L.DATA, data);
    }

    /**
     * 设置自定义字段里面的error字段
     *
     * @param errorCode 错误码
     */
    public void addError(int errorCode) {
        add(L.ERROR, errorCode);
    }

}
