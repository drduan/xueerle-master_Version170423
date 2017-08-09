package com.neusoft.sample.Ctrl.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/30.
 */
public class JsonAnalysisOne {

    public static  ArrayList JsonAS(String json,String Nos,int end,String Nub,  ArrayList No_list){
        try {

            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = (JSONObject) array.get(i);
                String cityNo = (String) object1.get(Nos);
                if (cityNo.substring(0, end).equals(Nub)) {
                    No_list.add(cityNo);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return No_list;
    }

}
