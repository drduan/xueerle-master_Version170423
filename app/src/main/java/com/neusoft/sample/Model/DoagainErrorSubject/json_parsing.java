package com.neusoft.sample.Model.DoagainErrorSubject;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.neusoft.sample.Ctrl.yangkangkang.EnglishWordStudy_bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

/**
 * Created by 杨康 on 2016/8/10.
 */

public class json_parsing {
    static List aa ;
    public static List getjson (String zhi){
        try {
            JSONObject json = new JSONObject(zhi);
            aa = JSON.parseArray((String) json.get("data"), EnglishWordStudy_bean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return aa;
    }
}
