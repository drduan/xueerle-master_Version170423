package com.neusoft.sample.Model.pay;

import java.util.List;

/**
 * Created by xudong on 2017/7/28.
 */

public class JcItemsResult {

    /**
     * Copyright 2017 bejson.com
     */

    /**
     * Auto-generated: 2017-07-28 21:51:34
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

        private String success;
        private List<DataGoodTerm> dataGoodTerm;
        private DataGood dataGood;
        private  String ok;

        public void setSuccess(String success) {
            this.success = success;
        }
        public String getSuccess() {
            return success;
        }

        public void setDataGoodTerm(List<DataGoodTerm> dataGoodTerm) {
            this.dataGoodTerm = dataGoodTerm;
        }
        public List<DataGoodTerm> getDataGoodTerm() {
            return dataGoodTerm;
        }

        public void setDataGood(DataGood dataGood) {
            this.dataGood = dataGood;
        }
        public DataGood getDataGood() {
            return dataGood;
        }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
