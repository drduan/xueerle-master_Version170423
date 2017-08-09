package com.neusoft.sample.Model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.neusoft.sample.Model.Stitching.ht_ip;

/**
 * Created by wangyujie on 2016/7/7.
 */
public class Constant {
    //176062k330.iask.in:13202
//    public  static  String ht_ip = "176o62k330.iask.in:13202/Xrl";
    public static String ht_ip = "http://122.156.218.189:8080/Xrl/";
    //public static String ht_ip =  "http://172.19.162.250:8080/Xel/";
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String Post_url_For_UserValid = ht_ip + "app/userLogin";
    public static String Post_url_For_registerValid = ht_ip + "app/findUser";
    public static String Post_url_For_checkCode = ht_ip + "app/sendMassage";
    public static String Post_url_For_UsertoID = ht_ip + "app/selectJiaoOneTwoGoods";
    public static String Post_url_For_UploadSchedule = ht_ip + "app/JiaocaiNewOrOld";
    public static String Post_url = ht_ip + "app/addUser";
    public static String Url_PCR = ht_ip + "/app/select/selectPCD";
    public static String Url_SGC = ht_ip + "/app/select/findSNBByQuNo?quNo=";
    public static String post_Kousuan = ht_ip + "app/user/selectResourcesTest";
    public static String post_Error_subject = ht_ip + "app/test/ErrorSubject";
    public static String post_Error_subject_again = ht_ip + "app/test/uploadWrongTopicResults";
    public static String post_Learn = ht_ip + "Study/Resourse/obtainKouSuanCard";
    public static String PostStudentsChampionship = ht_ip + "app/test/rightSubject";
    public static String PostNotStudentsChampionship = ht_ip + "app/test/getZuGJ";
    public static String post_Learn_rijiyuelei = ht_ip + "app/user/selectResourcesStudy";
    public static String post_Learn_chengyujielong = ht_ip + "app/user/chengYvJielongList";
    public static String post_Learn_chengyujielong_zhi = ht_ip + "app/user/selectChengYuJieLong";
    public static String post_ErrorQuestion = ht_ip + "app/test/getErrorSubject";
    public static String post_ErrorQuestion_two = ht_ip + "app/test/subjectDetails";
    public static String ip_post_chegnyu_jinyici = ht_ip + "/app/user/selectChengYuJinYiCi/";
    public static String ip_post_english_jinyici = ht_ip + "/app/user/selectEnglishJinYiCi/";
    //特别接口--错题中一级结构
    public static String post_ErrorQuestion_one_speical = ht_ip + "app/test/subjectEnglishDetails";

    public static String post_user_info = ht_ip + "app/user/addUserMessage";//上传用户信息
    public static String post_get_user = ht_ip + "/app/user/selectUserMessage";//上传用户信息

    public static String selectUserMessage = ht_ip + "resources/upload/image/user";//查询图片路径
    public static String post_image = ht_ip + "app/user/addUserimg";//上传图片路径
    public static String post_forget__password = ht_ip + "app/forgotPassWord";//忘记密码上传
    public static String post_get_shengzi_gushici = ht_ip + "/app/user/selectShengZiGuShi";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String post_feedback_img_url = ht_ip + "app/user/feedback";//帮助与反馈图片上传接口
    public static String post_longhubang = ht_ip + "LongHuBang/selectLongHuBang";
    public static String post_notification = ht_ip + "app/notification/selectNotification";//通知与公告
    public static String post_learntrack = ht_ip + "app/parent/study_path";//学习轨迹
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*拼接路径专用*/
    public static String ip = ht_ip + "resources/xrlRes/";//课本学习（英语、语文）
    /*听写答案的接口*/
    public static String post_chinese_shengzi = ht_ip + "/app/user/selectShengZiTXAnswer";//语文课本学习 生字听写
    public static String post_chinese_cizu = ht_ip + "/app/user/selectCiZhuTingXieAnswer";//语文课本学习 词组听写
    public static String post_english_danci = ht_ip + "/app/user/selectDanCitingXieAnswer";//英语课本学习 单词听写
    public static String post_english_zhongwen = ht_ip + "/app/user/selectChineseTingXieAnswer";//英语课本学习 中文听写
    public static String post_xingainain_yingyu = ht_ip + "/app/user/selectXinGaiNianTingXieAnswer";//英语新概念单词听写
    public static String post_xingainain_zhongwen = ht_ip + "/app/user/selectXinGaiNianChineseAnswer";//英语新概念中文听写


    /*老师端用户请求路径*/
    public static String teacher_cuoti = ht_ip + "app/test/ findTeacherWrong";//老师请求错题
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String selectHomeWork = ht_ip + "/app/homework/selectHomeWork/";//数独
    public static String youxiuzuowen = ht_ip + "/app/user/selectExcellentCompostion";//优秀作文赏析路径
    public static String selectBookReview = ht_ip + "/app/user/ selectBookReview";//读后感
    public static String selectExcellentBook = ht_ip + "/app/user/selectExcellentBook";//好书推荐
    public static String yufaxuexi = ht_ip + "/app/user/selectEnglishYuFaStudy/";
    //老师逻辑
    public static String teacherLogin = ht_ip + "app/teacherLogin";
    public static String teacherfindOneTweGou = ht_ip + "app/findOneTweGou";
    public static String teacherfindTeacherWrong = ht_ip +"app/test/findTeacherWrong";
    //家长听写权限   发布作业
    public static String  editpower=ht_ip+"/app/user/tingxie_power";
    public static String  findpower=ht_ip+"/app/user/find_tingxie_power";//获取听写权限
    public static String  my_work = ht_ip + "";//   我的作业
    public static String  add_work = ht_ip +"app/homework/addHomeWork"; //发布作业

    public static String  check_work_teacher = ht_ip + "app/homework/selectTeacherHomeWork" ;//老师查看发布的作业

    public static String getyy_mm_dd(Date date) {
        Log.d("datas", date.toString());
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("datasfinnish", myFmt.format(date));
        return myFmt.format(date);
    }

    /**
     * 时间转换
     *
     * @param date
     * @return
     */
    public static String getyymmdd(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMdd");
        return myFmt.format(date);
    }

    public static String getyymmddhhmmss(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFmt.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @throws
     */
    public static Date getDateForSJC(Long date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(date);
        Date D = format.parse(d);
        System.out.println(" date  ：" + D);
        return D;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyyMMdd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取星期几
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEEE");
        String week = sdf.format(date);
        return week;
    }

    public static String getSystemTime(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        return sDateFormat.format(new Date());
    }


    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
//        Date currentTime = new Date(System.currentTimeMillis());
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
//        String dateString = formatter.format(currentTime);
//        ParsePosition pos = new ParsePosition(8);
//        Date currentTime_2 = formatter.parse(dateString, pos);

//        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());

        return curDate;
    }

    /**
     * 获取网络时间
     *
     * @return
     */

    public static String getTime() {
        String da = "";
        String urlString = "http://saesea.cn";
        URL url;
        long ld;
        try {
            url = new URL(urlString);//获得资源对象
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect();//发出连接
            ld = uc.getDate();//取得网站日期时间(时间戳)
            Date date = new Date(ld);// 转换为标准时间对象

            String times = getyymmdd(date);
            da = times;
            Log.d("AA", da);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return da;
    }


    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */

//  String pTime = "2012-03-12";
    public static String getWeek(String pTime) {
        String Week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "六";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "一";
        }


        return Week;
    }


    /*
   *获取对象中的变量的变量名称
   *
    */
    public static synchronized String getName(Object o, String findName) {

        String name = "";


        Field[] fields = o.getClass().getDeclaredFields();

        int j = 0;
        for (int i = 0; i < fields.length; i++) {

            if (fields[i].getName().substring(6).equals(findName)) {
                name = fields[i].getName();
                break;
            }

        }
        return name;
    }

    ////////////////////////////////////////////杨康///////////////////////////////
    public static String getString(String ff) {
        ff = ff.replaceAll("\"", "");
        ff = ff.replaceAll(" ", "");
        if (ff.length() == 0) {
            ff = "";
        } else if (ff.length() == 1) {
            ff = "      " + ff;
        } else if (ff.length() == 2) {
            ff = "     " + ff;
        } else if (ff.length() == 3) {
            ff = "     " + ff;
        } else if (ff.length() == 4) {
            ff = "     " + ff;
        } else {
            ff = "     " + ff + "";
        }
        return ff;
    }

    public static String getString_guo(String ff) {
        ff = ff.replaceAll("\"", "");
        ff = ff.replaceAll(" ", "");
        if (ff.length() == 0) {
            ff = "";
        } else if (ff.length() == 1) {
            ff = "      " + ff;
        } else if (ff.length() == 2) {
            ff = "     " + ff;
        } else if (ff.length() == 3) {
            ff = "     " + ff;
        } else if (ff.length() == 4) {
            ff = "     " + ff;
        } else {
            ff = "     " + ff + "";
        }
        return ff;
    }

    /**
     * 获取网落图片资源
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /*
    * 这是分数的
    * 判断之类的东西
    * */
    public static String[] getfen(String stem) {
        String pattern = "【+-x÷】";
        String[] fen;
        String fenzi = "";
        String fenmu = "";
        String fen_hou = "";
        String fenzi1 = "";
        String fenmu1 = "";
        String fen_hou1 = "";
        String fen_fu = "";
        String qianis = "0";
        String houis = "0";
        String fenqian__ = "";
        String fenhou__ = "";
        /*
        * 当我写到这的时候我是欲哭无泪啊
        * 这是根据特定字符从字符串中提取部分字符串
        *
        * */
        if (stem.contains("÷")) {
            String qian = stem.substring(0, stem.indexOf("÷") - 1);
            String hou = stem.substring(stem.indexOf("÷") + 1, stem.length());
            fen_fu = "÷";
            if (qian.contains("/")) {
                qianis = "1";
                fenzi = stem.substring(0, stem.indexOf("/"));
                fenmu = stem.substring(stem.indexOf("/") + 1, stem.indexOf("÷"));
                if (fen_hou.contains("/")) {
                } else {
                    fenqian__ = qian;
                }
                if (hou.contains("/")) {
                    houis = "1";
                    fenzi1 = hou.substring(0, hou.indexOf("/"));
                    fenmu1 = hou.substring(hou.indexOf("/") + 1, hou.length());
                } else {
                    fenhou__ = hou;
                }
            }

        } else if (stem.contains("x")) {
            String qian = stem.substring(0, stem.indexOf("x") - 1);
            String hou = stem.substring(stem.indexOf("x") + 1, stem.length());
            fen_fu = "x";
            if (qian.contains("/")) {
                qianis = "1";
                fenzi = stem.substring(0, stem.indexOf("/"));
                fenmu = stem.substring(stem.indexOf("/") + 1, stem.indexOf("x"));
                //  fen_hou = stem.substring(stem.indexOf("÷"), stem.length());
                if (fen_hou.contains("/")) {
                } else {
                    fenqian__ = qian;
                }
                if (hou.contains("/")) {
                    houis = "1";
                    fenzi1 = hou.substring(0, hou.indexOf("/"));
                    fenmu1 = hou.substring(hou.indexOf("/") + 1, hou.length());
                    //  fen_hou1 = fen_hou.substring(fen_hou.indexOf("=")+1,fen_hou.length());
                } else {
                    fenhou__ = hou;
                }
            }
        } else if (stem.contains("+")) {
            String qian = stem.substring(0, stem.indexOf("+") - 1);
            String hou = stem.substring(stem.indexOf("+") + 1, stem.length());
            fen_fu = "+";
            if (qian.contains("/")) {
                qianis = "1";
                fenzi = stem.substring(0, stem.indexOf("/"));
                fenmu = stem.substring(stem.indexOf("/") + 1, stem.indexOf("+"));
                //  fen_hou = stem.substring(stem.indexOf("÷"), stem.length());
                if (fen_hou.contains("/")) {
                } else {
                    fenqian__ = qian;
                }
                if (hou.contains("/")) {
                    houis = "1";
                    fenzi1 = hou.substring(0, hou.indexOf("/"));
                    fenmu1 = hou.substring(hou.indexOf("/") + 1, hou.length());
                    //  fen_hou1 = fen_hou.substring(fen_hou.indexOf("=")+1,fen_hou.length());
                } else {
                    fenhou__ = hou;
                }
            }
        } else if (stem.contains("-")) {
            String qian = stem.substring(0, stem.indexOf("-") - 1);
            String hou = stem.substring(stem.indexOf("-") + 1, stem.length());
            fen_fu = "-";
            if (qian.contains("/")) {
                qianis = "1";
                fenzi = stem.substring(0, stem.indexOf("/"));
                fenmu = stem.substring(stem.indexOf("/") + 1, stem.indexOf("x"));
                //  fen_hou = stem.substring(stem.indexOf("÷"), stem.length());
                if (fen_hou.contains("/")) {
                } else {
                    fenqian__ = qian;
                }
                if (hou.contains("/")) {
                    houis = "1";
                    fenzi1 = hou.substring(0, hou.indexOf("/"));
                    fenmu1 = hou.substring(hou.indexOf("/") + 1, hou.length());
                    //  fen_hou1 = fen_hou.substring(fen_hou.indexOf("=")+1,fen_hou.length());
                } else {
                    fenhou__ = hou;
                }
            }
        } else if (stem.contains(",")) {
            String qian = stem.substring(0, stem.indexOf(",") - 1);
            String hou = stem.substring(stem.indexOf(",") + 1, stem.length());
            fen_fu = "-";
            if (qian.contains("/")) {
                qianis = "1";
                fenzi = stem.substring(0, stem.indexOf("/"));
                fenmu = stem.substring(stem.indexOf("/") + 1, stem.indexOf(","));
                //  fen_hou = stem.substring(stem.indexOf("÷"), stem.length());
                if (fen_hou.contains("/")) {
                } else {
                    fenqian__ = qian;
                }
                if (hou.contains("/")) {
                    houis = "1";
                    fenzi1 = hou.substring(0, hou.indexOf("/"));
                    fenmu1 = hou.substring(hou.indexOf("/") + 1, hou.length());
                    //  fen_hou1 = fen_hou.substring(fen_hou.indexOf("=")+1,fen_hou.length());
                } else {
                    fenhou__ = hou;
                }
            }
        }

        fen = new String[]{qianis, houis, fenqian__, fenhou__, fen_fu, fenzi, fenmu, fenzi1, fenmu1, fen_hou1};
        return fen;
    }

    /*
    * 提取选项中的分数的分子和分母
    * */
    public static String[] getfen_choice(String choice) {
        String[] fenshu;
        String fenzi = choice.substring(0, choice.indexOf("/"));
        String fenmu = choice.substring(choice.indexOf("/") + 1, choice.length());
        fenshu = new String[]{fenzi, fenmu};
        return fenshu;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

}
