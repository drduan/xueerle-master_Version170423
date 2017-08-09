package com.neusoft.sample.View.users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/8.
 */
public class testTime {


   public static  void test() throws ParseException {

       long L = Long.valueOf( "1467269862181"  ).longValue();
       Date date = getDateForSJC(L);

       // 生成时间戳 System.currentTimeMillis()


       System.out.println(getyy_mm_dd(date));
   }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @throws
     * 把时间戳 转化到  Date 类型
     */
    public static Date getDateForSJC(Long date) throws ParseException {

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Long  a =  System.currentTimeMillis() ;

        String d = format.format(date);
        Date D = format.parse(d);


        System.out.println(" date  ：" + D );

        return D ;

    }

    public static String getyy_mm_dd(Date date){

        SimpleDateFormat myFmt=new SimpleDateFormat("YYYY-MM-dd");

        return myFmt.format(date) ;

    }


}
