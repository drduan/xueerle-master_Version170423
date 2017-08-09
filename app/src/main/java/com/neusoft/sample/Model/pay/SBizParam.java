package com.neusoft.sample.Model.pay;

/**
 * Created by xudong on 2017/7/29.
 * 项目实际使用
 */

public class SBizParam {

    private String timeout_express;
    private String product_code;
    private String total_amount;
    private String subject;
    private String body;
    private String out_trade_no;
    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }
    public String getTimeout_express() {
        return timeout_express;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
    public String getProduct_code() {
        return product_code;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
    public String getTotal_amount() {
        return total_amount;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSubject() {
        return subject;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public String getBody() {
        return body;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }

}
