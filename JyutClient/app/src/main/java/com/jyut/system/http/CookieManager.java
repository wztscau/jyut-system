/**
 *
 */
package com.jyut.system.http;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C;
import com.jyut.system.util.Encryption;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author wztscau
 * @date Sep 25, 2016
 * @project 粤盟管理系统客户端
 */
public class CookieManager {

    private Context context;
    private String spName;

    public CookieManager(Context context) {
        this.context = context;
        spName = C.PREFERENCE_DEFAULT;
    }

    public void setFileName(String name) {
        this.spName = name;
        // 文件名加密用md5
        if (C.ENCRYTED) {
            spName = Encryption.encryptMD5(spName);
        }
    }

    /**
     * 将数据保存到本地--SharedPreferences
     *
     * @param map 数据map
     */
    public void saveToLocal(Map<String, String> map) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        // 1.先得到编辑器
        Editor editor = sp.edit();
        // 2.清空文件,防止数据重复累赘
        editor.clear();
        Set<Entry<String, String>> set = map.entrySet();
        // 3.将数据存入至编辑器中
        for (Entry<String, String> entry : set) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (C.ENCRYTED) {
                // key也要加密
                key = Encryption.encryptAES(key);
                value = Encryption.encryptAES(value);
            }
            editor.putString(key, value);
        }
        // 4.最后记得提交
        editor.commit();
    }

    /**
     * 从本地文件--SharedPreferences还原数据
     *
     * @return
     */
    public Map<String, String> loadFromLocal() {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        @SuppressWarnings("unchecked")
        Map<String, String> srcMap = (Map<String, String>) sp.getAll();
        Map<String, String> desMap = new HashMap<>();
        Set<Entry<String, String>> entrySet = srcMap.entrySet();
        for (Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (C.ENCRYTED) {
                key = Encryption.decryptAES(key);
                value = Encryption.decryptAES(value);
            }
            desMap.put(key, value);
        }
        return desMap;
    }

    public static List<HttpCookie> loadCookies() {
        List<HttpCookie> cookies = NoHttp.getDefaultCookieManager().getCookieStore().getCookies();
        List<HttpCookie> list = new ArrayList<>();
        if (C.ENCRYTED) {
            for (HttpCookie cookie : cookies) {
                cookie.setValue(Encryption.decryptAES(cookie.getValue()));
                list.add(cookie);
            }
            return list;
        }
        return cookies;
    }

    public static void saveCookies(List<HttpCookie> cookies, URI uri) {
        NoHttp.getDefaultCookieManager().getCookieStore().getCookies().clear();
        for (HttpCookie cookie : cookies) {
            if(C.ENCRYTED){
                cookie.setValue(Encryption.encryptAES(cookie.getValue()));
            }
            NoHttp.getDefaultCookieManager().getCookieStore().add(uri, cookie);
        }
    }

    public static String getUserName() {
        List<HttpCookie> cookies = loadCookies();
        for (HttpCookie cookie : cookies) {
            if (C.L.USERNAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }

}
