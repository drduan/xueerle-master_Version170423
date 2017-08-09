package com.neusoft.sample.Ctrl.Json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/30.
 */
public class JsonAnalysis {

    public static  void JsonAS(String json,String Name ,String Nos,int end,String Nub,ArrayList spiner_list,  ArrayList No_list){
        try {

            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = (JSONObject) array.get(i);
                String city = (String) object1.get(Name);
                Log.d("TT", city);
                String cityNo = (String) object1.get(Nos);
                if (cityNo.substring(0, end).equals(Nub)) {
                    spiner_list.add(city);
                    No_list.add(cityNo);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
