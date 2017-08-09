package com.neusoft.sample.View.xel_mine.MyHomeWork;

/**
 *我的作业实体
 */
public class HomeWork_Entity implements Comparable<HomeWork_Entity>{
    private String itemno;
    private String workname;

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    @Override
    public int compareTo(HomeWork_Entity another) {
        return itemno.compareTo(another.getItemno());
    }
}
