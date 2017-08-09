
package com.neusoft.sample.Model;

import android.util.Log;

import com.neusoft.sample.util.DirectoryConversion;

/**
 * Created by wangyujie on 2016/9/2.
 */
public class GetBitmapImage {
    public static  String downlowd_out = "http://122.156.218.189:8080/Xrl/resources/xrlRes/";
   public static String Url(String groupnub,String item_no){

//       10  1401101010101 1

       String urls=downlowd_out+DirectoryConversion.GetUrl(item_no)+groupnub+".png";
       Log.d("当前图片的url","--:"+urls);
       return urls ;
   }
    public static String Voiceurl(String item_no,String voice){
        String urls=downlowd_out+DirectoryConversion.GetUrl(item_no)+voice+".mp3";
        Log.d("当前声音的url","--:"+urls);
        return urls ;


    }


}
