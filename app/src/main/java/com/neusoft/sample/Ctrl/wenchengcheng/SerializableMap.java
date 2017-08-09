package com.neusoft.sample.Ctrl.wenchengcheng;

/**
 * Created by AstroBoy on 2016/10/28.
 */

import java.io.Serializable;
import java.util.Map;

/**
 * 序列化map供Bundle传递map使用
 * Created  on 13-12-9.
 */
public class SerializableMap implements Serializable {

    private Map<Object,String> map;

    public Map<Object,String> getMap() {
        return map;
    }

    public void setMap(Map< Object,String> map) {
        this.map = map;
    }


}
