package com.neusoft.sample.util;

/**
 * Created by wangyujie on 2016/9/17.
 */
public class DirectoryConversion {

    public static String GetUrl(String  SequenceNumber){
     String nub=SequenceNumber.substring(0,1);
     if ("1".equals(nub))
     {
         return "1/10/"+SequenceNumber.substring(0,5)+"/"+SequenceNumber.substring(5,7)+"/";
     }
        else if ("3".equals(nub))
     {
         return "1/11/"+SequenceNumber.substring(0,5)+"/"+SequenceNumber.substring(5,7)+"/";
     }
     else if ("C".equals(nub))
     {
         return "2/30/"+SequenceNumber.substring(0,5)+"/"+SequenceNumber.substring(5,7)+"/";
     }
     else if ("5".equals(nub))
     {
         return "3/99/pic/";
     }
     else if ("8".equals(nub))
     {
         return "3/51/"+SequenceNumber.substring(0,5)+"/"+SequenceNumber.substring(5,7)+"/";
     }
     else if ("7".equals(nub))
     {
         return "3/99/pic/";
     }
        return "转换失败请查看util下DirectoryConversion类";

    }

}
