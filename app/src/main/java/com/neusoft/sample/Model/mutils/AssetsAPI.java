package com.neusoft.sample.Model.mutils;
 

import android.content.Context;
import android.util.Log;

/**
 * 读取asses文件
 *
 * @author freePC
 *
 */
public class AssetsAPI {
    private static AssetsAPI instance = null;
    FileUtils file;
    public AssetsAPI() {}

    public static AssetsAPI getInstance() {
        if (instance == null) {
            instance = new AssetsAPI();
        }

        return instance;
    }

    public String getFontJson(Context context, String fileName) {
    	String result=null;
        try {
            result  = new FileUtils().getAssets(context,fileName);
            
        } catch (Exception e) {
        }

        return result;
    }
    
    public String getFromAssets(Context context, String fileName){
        String result=null;
        try {
            result  = FileUtils.getFromAssets(context,fileName);
            Log.d("@@","AssetsAPI打印resulte"+result);
        } catch (Exception e) {
        }
        Log.d("@@","AssetsAPI打印resulte"+result);
        return result;
    }
} 
