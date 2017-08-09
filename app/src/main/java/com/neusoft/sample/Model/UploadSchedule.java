package com.neusoft.sample.Model;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/7.
 */
public class UploadSchedule {

    Date Updatatime;
    String Bookno;

    public void setBookno(String bookno) {
        Bookno = bookno;
    }

    public Date getUpdatatime() {
        return Updatatime;
    }

    public void setUpdatatime(Date updatatime) {
        Updatatime = updatatime;
    }

    public String getBookno() {
        return Bookno;
    }
}
