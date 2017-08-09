package com.neusoft.sample.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.Json.Json;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.ToastUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.Ctrl.wenchengcheng.Class_bean;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.pay.BizParam;
import com.neusoft.sample.Model.pay.JcItemsResult;
import com.neusoft.sample.Model.pay.Order;
import com.neusoft.sample.View.users.Xel_Users_Register;
import com.neusoft.sample.View.util.OrderInfoUtil2_0;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by duanxudong on 16/6/17.
 * Version 1
 */

public class Confirm_buy extends  BaseActivity {


    /** 正式环境 支付宝支付业务：入参app_id */
    public static final String APPID = "2016091701912747";
    /**  下面为沙箱环境APPID*/
    //   public static final String APPID = "2016091600523784";
    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";

    //OkHttpClient client = new OkHttpClient() ;

    //存取省份和城市及地区的list
    private ArrayList<String> listall = new ArrayList<>();
    private ArrayList spiner_province_list = new ArrayList();
    ArrayList provinceNo_list = new ArrayList();
    private ArrayList spiner_city_list = new ArrayList();
    ArrayList cityNo_list = new ArrayList();
    //json假数据
    String json_province = "";
    String json_city = "";
    //获取其省份及城市
    String province;
    String city;


    //点击省份后的编号
    String provinceNub;
    String cityNub;


    /** 商户私钥，pkcs8格式 */
    public static final String RSA_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDVqExr3GZk0sK1D9+su/ksYCeLH7FSRfwmylxhtvi7LZR0u3qLWciucOmvMu94+GZqp8Ypd/EmrN4/7waorvWUEjQxtW3BIjwsRmtQUyjfeQMbq/gnzBziOzJyd/jrIQrvzAa2qRKKrGyU" +
            "2bhHQXPCk7M4QOlrLONlUqNsxkeHwnsnRfGPdLF4OQ3FVIMvCMC8mwqzkiX8dcDJ" +
            "ZCUNuS5DCxWpi3a//kBUPaV5wu9Efnp2f0KR3+OS3lwl9g2nNlM490i5r8On+seI" +
            "idl1vhJXJb0lb0kSY4Rb0BupWs0po8FWob9ti76FveYRtMjmFC5CpElV0Z2R5PuI" +
            "TeIpcRlpAgMBAAECggEBAI1yNN23dMZMzYBG+QKHE0fwhF1TZvYbxPn9QDmkg3iP" +
            "jVfWgiNdFkF+GQbWxrkkYdE8LzFm6Wx8R0q2OIdWBNi5UHqULQwOWR3KpOLxmv6P" +
            "Q6J/L/NQ6tJO9G/Zwx4Oqwnj3ZHxzoel7OCCXsA1sahgVebo+l7swcJjejPZ9KMH" +
            "xI4OUiOzMZog0rCQ4waduuPAQv6rUwnkUIZQAC2SN9SxJq1IN6mtiKINP3iuKj2Z" +
            "Um1++7YdFHDyTpuzfltiyFeXLhSXnWWTBCscjMF+g8u011VZ6hsNUdmfXZzRWhy6" +
            "1w1XBXxgd7wCFxhFln9COhMp3akDj0READiOuurmA90CgYEA93jm1Q0ZkKIbz4Jm" +
            "l+QCYK27c0bXmKXmGqxOLt8R1ZMWv+nEZZGGEaWUyyHbD5HAau+70/2ZE/rrOF9u" +
            "LyqdFj/b/B+5wTiXDXeIY+czOaBceQSVl2/PV3fbREVNQwrWnb34PNVb6uTlFrZk" +
            "+VgZSnPJHvu17EpmqxnaGBl2RxcCgYEA3QUYm0RdU8vNwpxVofFCBOx/MstHzx1F" +
            "r2Kkb3MQ+okQqpsc/ST5fkxxRirdRvZVe2j+WJgDRZbcE+jJq3qFVPY65dFrpULS" +
            "TrVKshCja4CidYTxaqjXrqbtbdl9C6CcLc8QMhBNHZM0zWJCJZbKeyyYi5RI4EDW" +
            "EVdbxZ2f838CgYEA1POR1yXVqPMGcXNp6yyGq0D0vAbrAW1I7Az3njfQdfODsRNt" +
            "MGWgc9EDkCULB4PJi3vOKmbgciQKg1EDVM0brgi3uWZnhxC2Ux1YHXfXSbBnwq+i" +
            "NlU9m6wE3+Ouz5ElC0pssZR7606aoAfGLhaUdM/b8rBxZOrFHFk7pl9Ku3MCgYBU" +
            "2cgAk9ZgfTWZKPuXGFAII+MRVQlUciB2nKAUSVCTXluPo80VQkSmQCKclYgt3hQj" +
            "NMOwr2tSJACJhC0oL/lQDNe/sw5oe6bRoLf0dH7InrG//DHSiM+EtYEhUwW2mQpc" +
            "a8oD7k4stH7o+nf6hlFlyFEoBN2Vek60iZ0p2MG3cQKBgQDv+lmvMH/sDeLPbvEU" +
            "tvgUwX+A/Y7Xa65PWszArXIKm2l23ikmP0xFY8dez+OSL86elw/b2f+dVrMWZIKc" +
            "zuVal2bkMkAye9G9l0kjdBDDhx1NW5ee81RxBidndZjJVag4RmY9Pg/SkoYHVM5D" +
            "Ht+bULUrSxoSm2JnEOr5wUWPig==";


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @BindView(R.id.confirm_buy)
    Button confirm_but;
    @BindView(R.id.zf_spiner_province)
    Spinner spiner_province;
    @BindView(R.id.zf_spiner_city)
    Spinner spiner_city;


  //   String orderInfo = "";   // 订单信息
   // Map<String,Object>  map;
//    private Order order;

@SuppressLint("HandlerLeak")
private Handler mHandler = new Handler() {
    @SuppressWarnings("unused")
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(Confirm_buy.this, "支付成功", Toast.LENGTH_SHORT).show();
                    confirm_but.setEnabled(false);
                    confirm_but.setText("已经购买了当前产品！");
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(Confirm_buy.this, "支付失败"+resultInfo+resultStatus, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case SDK_AUTH_FLAG: {
                @SuppressWarnings("unchecked")
                AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                String resultStatus = authResult.getResultStatus();

                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    Toast.makeText(Confirm_buy.this,
                            "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // 其他状态值则为授权失败
                    Toast.makeText(Confirm_buy.this,
                            "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                }
                break;
            }
            default:
                break;
        }
    };
};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_querengoumasi);
        ButterKnife.bind(this);
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
//        order = new Order();
//        order.setCount(12);
//        order.setName("book");
        JsonObjectRequest request = new JsonObjectRequest(StringUtil.preUrl(Constant.Url_PCR), null,
                new Response.Listener<org.json.JSONObject>() {

                    @Override
                    public void onResponse(org.json.JSONObject response) {
                        try {
                            json_province = response.get("dataP").toString();
                            json_city = response.get("dataC").toString();

                            try {
                                JSONArray array = new JSONArray(json_province);

                                for (int i = 0; i < array.length(); i++) {
                                    org.json.JSONObject object1 = (org.json.JSONObject) array.get(i);
                                    String provinceNo = (String) object1.get("provinceNo");
                                    String province = (String) object1.get("provinceName");
                                    spiner_province_list.add(province);
                                    provinceNo_list.add(provinceNo);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                            ArrayAdapter arr_adapter = new ArrayAdapter(Confirm_buy.this, R.layout.xel_user_register_class_spinner_item, spiner_province_list);
//                            arr_adapter = new MArrayAdapter(Xel_Users_Register.this, spiner_province_list);
                            arr_adapter.notifyDataSetChanged();
                            spiner_province.setAdapter(arr_adapter);
                            spiner_province.setPrompt("请选择省份");

                            //点击省份传递对应的城市
                            spiner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spiner_city_list.clear();
                                    cityNo_list.clear();
                                    province = (String) spiner_province_list.get(position);

                                    provinceNub = (String) provinceNo_list.get(position);
                                    //TODO
                                    /**
                                     * 做数据库存储
                                     * province
                                     * provinceNub
                                     */


                                    //绑定city的数据

                                    try {

                                        JSONArray array = new JSONArray(json_city);
                                        for (int i = 0; i < array.length(); i++) {
                                            org.json.JSONObject object1 = (org.json.JSONObject) array.get(i);
                                            String city = (String) object1.get("cityName");
                                            String cityNo = (String) object1.get("cityNo");
                                            if (cityNo.substring(0, 2).equals(provinceNub)) {
                                                spiner_city_list.add(city);
                                                cityNo_list.add(cityNo);
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    ArrayAdapter city_apater = new ArrayAdapter(Confirm_buy.this, R.layout.xel_user_register_class_spinner_item, spiner_city_list);
                                    city_apater.notifyDataSetChanged();
                                    spiner_city.setAdapter(city_apater);
                                    spiner_city.setPrompt("请选择市");

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {

                ToastUtil.showToast(Confirm_buy.this, "获取信息失败，请查看联网状态！");
            }
        });

        spiner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                  @Override
                                                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                      city = (String) spiner_city_list.get(position);

                                                      cityNub = (String) cityNo_list.get(position);
                                                  }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        request.setTag(this);

        VolleyUtil.getQueue(this).add(request);

        }

    private Order order ;
    private BizParam bizParam;

    public  void  confirm_buy(View view)
    {

    // 提示框输出 2306
        if (Db_UserService.getInstance(this).loadAllNote().size()==0)
        {
            Toast.makeText(this,"获取用户信息失败，请重新登录",Toast.LENGTH_LONG).show();
            return;
        }
     final String grade = Db_UserService.getInstance(this).loadAllNote().get(0).getGrade();
     String _term = Db_UserService.getInstance(this).loadAllNote().get(0).getGrade_nub();
     String  term = _term.substring(_term.length()-4,_term.length());
        order = new Order();
        order.setGood_name(province+city+"教材");
        order.setGood_type("教材");
        order.setNotify_url("");
        order.setOrder_price("122.00");

        HttpParams params1 = new HttpParams();
        params1.put("bookGroupNo",2306102);
        params1.put("username", App.GetSharePrefrence_Phone(this));

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
       final boolean rsa2 = (RSA_PRIVATE.length() > 0);
        /** */



        OkGo.<String>get("http://176o62k330.iask.in:13202/Xel/app/pay/getStudyGoods")//
//        OkGo.<String>get("http://122.156.218.189:8080/Xrl/app/pay/getStudyGoods")//
                    .tag(this)//
                    .cacheKey("cacheKey")
                    .params(params1)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                            //ok

                            Log.d("@@@@@",response.body());
                            Gson gson = new Gson();
                            JcItemsResult jcitems = gson.fromJson(response.body(), JcItemsResult.class);

                            if (jcitems.getOk()!="100") {
                                Toast.makeText(Confirm_buy.this, "订单已创建成功，请勿重复提交", Toast.LENGTH_LONG).show();
                            }

                            HttpParams params = new HttpParams();
//                          params.put("order_price",jcitems.getDataGood().getPriceOnSale());
                            params.put("order_price",0.01);
                            params.put("good_no",jcitems.getDataGood().getBookGroupNo());
                            params.put("order_name",jcitems.getDataGood().getIntroduce());
                            params.put("good_name",jcitems.getDataGood().getIntroduce());
                            params.put("user_id",getUserId());
                            params.put("good_price",jcitems.getDataGood().getPrice()); // 实际价格
                            params.put("good_number",1);
                            params.put("good_type","教材");
                            params.put("ok",jcitems.getOk());

                            OkGo.<String>post("http://176o62k330.iask.in:13202/Xel/app/pay/trade")//
                                    .tag(this)//
                                    .params(params)
                                    .cacheKey("cacheKey1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                                            Gson gson = new Gson();
                                            OrderResult dealedOrder = gson.fromJson(response.body(),OrderResult.class);

                                            Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,dealedOrder.getData());
                                            String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                                            //String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                                            String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, rsa2);
                                            final String orderInfo = orderParam + "&" + sign;

                                            final  Runnable payRunnable = new Runnable() {

                                                @Override
                                                public void run() {

                                                    PayTask alipay = new PayTask(Confirm_buy.this);
                                                    Map<String, String> result = alipay.payV2(orderInfo, true);

                                                    Message msg = new Message();
                                                    msg.what = SDK_PAY_FLAG;
                                                    msg.obj = result;
                                                    mHandler.sendMessage(msg);
                                                }
                                            };
                                            Thread payThread = new Thread(payRunnable);
                                            payThread.start();
                                        }

                                        @Override
                                        public void onError(com.lzy.okgo.model.Response<String> response) {
                                            super.onError(response);
                                        }

                                    });


                        }

                        @Override
                        public void onError(com.lzy.okgo.model.Response<String> response) {
                            super.onError(response);
                        }

                    });



//    if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
//        new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialoginterface, int i) {
//                        finish();
//                    }
//                }).show();
//        return;
//    }

}
class OrderResult {
    private String error;
    private  String Success;
    private  Order Data;
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public Order getData() {
        return Data;
    }

    public void setData(Order data) {
        Data = data;
    }

}
    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
        String url = "http://m.taobao.com";
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");



}
