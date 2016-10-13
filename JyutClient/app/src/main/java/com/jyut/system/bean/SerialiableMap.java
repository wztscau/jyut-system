package com.jyut.system.bean;

import java.io.Serializable;
import java.util.Map;

/**
 *  因为java的map没有序列化功能,因此特定增添一个序列化的map,用来传递数据
 *    @date 10/8/2016
 *    @author wztscau
 *    @project 粤盟管理系统客户端
 *
 */

public class SerialiableMap<String,V> implements Serializable{

    public Map<String, V> getMap() {
        return map;
    }

    public void setMap(Map<String, V> map) {
        this.map = map;
    }

    private Map<String,V> map;

}
