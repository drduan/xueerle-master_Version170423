package com.neusoft.sample.Model;

import com.neusoft.sample.Model.mdata.Stroke;

import java.util.Map;

/**
 * Created by xudong on 2017/5/16.
 */

public class Result {

    public String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public  Result(String Obj)
    {
        System.out.print(Obj);
        //System.out.print(Obj.toString());
    }
}
