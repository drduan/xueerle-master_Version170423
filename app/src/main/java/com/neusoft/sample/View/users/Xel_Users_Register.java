package com.neusoft.sample.View.users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jaeger.library.StatusBarUtil;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.Db_deleteAll_Data;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.ToastUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.DaoMaster;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.PostUser;
import com.neusoft.sample.Model.Post_CheckCode;
import com.neusoft.sample.Model.Post_RegisterVaild;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.MArrayAdapter;
import com.neusoft.sample.util.ContextHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by V-man on 16/6/29.
 * Version 5
 */

/**
 * 所以逻辑都在提交按钮中
 */
public class Xel_Users_Register extends BaseActivity {
    //json假数据
    //    String json_province = "[{\"provinceName\":\"黑龙江 \",\"provinceNo\":\"23\"},{\"provinceName\":\"辽宁 \",\"provinceNo\":\"24\"}]";
    String json_province = "";
    //    String json_city = "[{\"cityName\":\"大庆\",\"cityNo\":\"2306\"},{\"cityName\":\"哈尔滨\",\"cityNo\":\"2307\"},{\"cityName\":\"大连\",\"cityNo\":\"2408\"},{\"cityName\":\"沈阳\",\"cityNo\":\"2409\"}]";
    String json_city = "";
    //String json_regin = "[{\"districtName\":\"萨尔图区\",\"districtNo\":\"230601\"},{\"districtName\":\"让胡路区\",\"districtNo\":\"230702\"},{\"districtName\":\"甘井子\",\"districtNo\":\"240803\"},{\"districtName\":\"皇姑区\",\"districtNo\":\"240904\"}]";
    String json_regin = "";
    String dataSch = "";
    String dataNian = "";
    String dataBan = "";
    Boolean bool;
    //角色的默认值为3
    private static String ROLE = "3";
    private SQLiteDatabase db;
    private EditText editText;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;
    public static final String TAG = "ttser";
    private EditText phone;
    private EditText psword;
    private EditText psword1;

    //Spinner
    private Spinner spiner_province;
    private Spinner spiner_city;
    private Spinner spiner_region;
    private Spinner spiner_grade;
    private Spinner spiner_class;
    private Spinner spiner_school;
    private ArrayList spiner_province_list;
    private ArrayList spiner_city_list;
    private ArrayList spiner_region_list;
    private ArrayList spiner_grade_list;
    private ArrayList spiner_class_list;
    private ArrayList spiner_school_list;
    //省份到城市 list
    private List<List> list_city;
    private List list_p;
    //城市到地区 list
    private List<List> list_region;
    private List list_c;
    //存取省份的编号
    ArrayList provinceNo_list = new ArrayList();
    ArrayList cityNo_list = new ArrayList();
    ArrayList regionNo_list = new ArrayList();
    ArrayList schoolNo_list = new ArrayList();
    ArrayList gradeNo_list = new ArrayList();
    ArrayList classNo_list = new ArrayList();


    //Spinner的adapter
    private MArrayAdapter arr_adapter;

    //点击省份后的编号
    String provinceNub;
    String cityNub;
    String regionNub;
    String schoolNub;
    String gradeNub;
    String clssNub;

    //获取其省份及城市
    String province;
    String city;
    String region;
    String school;
    String grades;
    String clss;

    //验证码  获取的   定义
    private Button btn_rigist_get_yanzhengme, student_perent_btn, teacher_btn;
    private TimeCount time;
    private EditText et_ris_phone, et_ris_yanzhengma;
    private TextView tv_rigist_true;
    String get_rigist_phone = "";
    String get_rigist_yanzhengma = "";
    String duanxin_num = "";
    int isUserExist = 0;
    String mPhone = "";
    boolean isPhonechanged = false;
    //验证码  获取的   定义
    //存取省份和城市及地区的list
    private ArrayList<String> listall = new ArrayList<>();

    //验证用户有效性以决定发送验证码的返回原始数据
    String recode = "";
    Handler handler;
    JSONObject json = null;
    String success;

    @Override
    protected void onResume() {
        super.onResume();
        ROLE = "3";
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_user_register);
        StatusBarUtil.setTranslucent(this, 0);
        if (!Consant_stringg.is_internet(this)) {
            Toast.makeText(this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }
        bool = Consant_stringg.is_internet(this);
        initView();
        InitView_yanzhengma();
        //InitView_rgister_et();
        //Init_num();

        /**
         * 数据库
         */
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Xel.db", null);
        db = helper.getWritableDatabase();
        App.getDaoMaster(this);
        Db_UserService.getInstance(this);
        Spinner_operation();


        JsonObjectRequest request = new JsonObjectRequest(StringUtil.preUrl(Constant.Url_PCR), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("JSON", response.toString() + "");
                        try {
                            json_province = response.get("dataP").toString();
                            json_city = response.get("dataC").toString();
                            json_regin = response.get("dataD").toString();

                            try {
                                JSONArray array = new JSONArray(json_province);

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object1 = (JSONObject) array.get(i);
                                    Log.d("array", object1 + "");
                                    String provinceNo = (String) object1.get("provinceNo");
                                    String province = (String) object1.get("provinceName");
                                    spiner_province_list.add(province);
                                    provinceNo_list.add(provinceNo);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                            ArrayAdapter arr_adapter = new ArrayAdapter(Xel_Users_Register.this, R.layout.xel_user_register_class_spinner_item, spiner_province_list);
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

                                    Log.d("JSONsM", listall + "");
                                    try {

                                        JSONArray array = new JSONArray(json_city);
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject object1 = (JSONObject) array.get(i);
                                            String city = (String) object1.get("cityName");
                                            Log.d("TT", city);
                                            String cityNo = (String) object1.get("cityNo");
                                            if (cityNo.substring(0, 2).equals(provinceNub)) {
                                                spiner_city_list.add(city);
                                                cityNo_list.add(cityNo);
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    ArrayAdapter city_apater = new ArrayAdapter(Xel_Users_Register.this, R.layout.xel_user_register_class_spinner_item, spiner_city_list);
                                    city_apater.notifyDataSetChanged();
                                    spiner_city.setAdapter(city_apater);
                                    spiner_city.setPrompt("请选择市");

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            //点击城市传递对应的地区
                            spiner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spiner_region_list.clear();
                                    regionNo_list.clear();
                                    city = (String) spiner_city_list.get(position);

                                    cityNub = (String) cityNo_list.get(position);
                                    //TODO
                                    /**
                                     * 做数据库存储
                                     * province
                                     * provinceNub
                                     */

                                    //绑定city的数据

                                    try {

                                        JSONArray array = new JSONArray(json_regin);
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject object1 = (JSONObject) array.get(i);
                                            String region = (String) object1.get("districtName");
                                            Log.d("TT", region);
                                            String regionNo = (String) object1.get("districtNo");
                                            if (regionNo.substring(0, 4).equals(cityNub)) {
                                                spiner_region_list.add(region);
                                                regionNo_list.add(regionNo);
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    ArrayAdapter region_apater = new ArrayAdapter(Xel_Users_Register.this, R.layout.xel_user_register_class_spinner_item, spiner_region_list);
                                    region_apater.notifyDataSetChanged();
                                    spiner_region.setAdapter(region_apater);
                                    spiner_region.setPrompt("请选择地区");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            //点击地区传递对应的学校


                            /**
                             *
                             *
                             *
                             *
                             *
                             * 当点击地区后会做第二次的网络请求
                             */
                            spiner_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spiner_school_list.clear();
                                    schoolNo_list.clear();
                                    region = (String) spiner_region_list.get(position);

                                    regionNub = (String) regionNo_list.get(position);

                                    String url = Constant.Url_SGC + regionNub;
                                    JsonObjectRequest request = new JsonObjectRequest(StringUtil.preUrl(url), null,
                                            new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    try {
                                                        final ArrayList[] list = {new ArrayList()};
                                                        dataSch = response.get("Slist").toString();
                                                        dataNian = response.get("Nlist").toString();
                                                        dataBan = response.get("Blist").toString();
                                                        /**
                                                         * 解析学校
                                                         */
                                                        try {

                                                            JSONArray array = new JSONArray(dataSch);
                                                            for (int i = 0; i < array.length(); i++) {
                                                                JSONObject object1 = (JSONObject) array.get(i);
                                                                String city = (String) object1.get("name");
                                                                Log.d("TT", city);
                                                                String cityNo = (String) object1.get("schoolNo");
                                                                if (cityNo.substring(0, 6).equals(regionNub)) {
                                                                    spiner_school_list.add(city);
                                                                    schoolNo_list.add(cityNo);
                                                                }
                                                            }
                                                            ArrayAdapter region_apatera = new ArrayAdapter(Xel_Users_Register.this, R.layout.xel_user_register_class_spinner_item, spiner_school_list);
                                                            region_apatera.notifyDataSetChanged();
                                                            spiner_school.setAdapter(region_apatera);
                                                            spiner_school.setPrompt("请选择学校");
                                                            spiner_school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                    spiner_grade_list.clear();
                                                                    gradeNo_list.clear();
                                                                    school = (String) spiner_school_list.get(position);

                                                                    schoolNub = (String) schoolNo_list.get(position);
                                                                    Log.d("nub", schoolNub);

                                                                    /**
                                                                     * 解析年级
                                                                     */
                                                                    try {

                                                                        JSONArray array = new JSONArray(dataNian);
                                                                        for (int i = 0; i < array.length(); i++) {
                                                                            JSONObject object1 = (JSONObject) array.get(i);
                                                                            String cityNo = (String) object1.get("gradeNo");
                                                                            if (cityNo.substring(0, 8).equals(schoolNub)) {
                                                                                Log.d("nubs", cityNo);
                                                                                final int grade = Integer.parseInt(cityNo.substring(9));
                                                                                spiner_grade_list.add(grade);
                                                                                Collections.sort(spiner_grade_list);
                                                                                gradeNo_list.add(cityNo);
                                                                                Collections.sort(gradeNo_list);
                                                                                ArrayAdapter region_apaterS = new ArrayAdapter(Xel_Users_Register.this, R.layout.xel_user_register_class_spinner_item, spiner_grade_list);
                                                                                region_apaterS.notifyDataSetChanged();
                                                                                spiner_grade.setAdapter(region_apaterS);
                                                                                Log.d("nub1----", spiner_grade_list.toString());
                                                                                spiner_grade.setPrompt("请选择年级");
                                                                                spiner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                    @Override
                                                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                                        classNo_list.clear();
                                                                                        spiner_class_list.clear();
                                                                                        grades = (String) gradeNo_list.get(position);
                                                                                        gradeNub = (String) gradeNo_list.get(position);
                                                                                        Log.d("年级的号", gradeNub.toString());

                                                                                        try {

                                                                                            JSONArray array = new JSONArray(dataBan);
                                                                                            for (int i = 0; i < array.length(); i++) {
                                                                                                JSONObject object1 = (JSONObject) array.get(i);
                                                                                                String cityNo = (String) object1.get("name");
                                                                                                if (cityNo.substring(0, 10).equals(gradeNub)) {
                                                                                                    Log.d("匹配年级的号", cityNo.toString());
                                                                                                    int grade = Integer.parseInt(cityNo.substring(10));

                                                                                                    spiner_class_list.add(grade);
                                                                                                    Collections.sort(spiner_class_list);
                                                                                                    Log.d("排序后的年级", spiner_class_list.toString());
                                                                                                    classNo_list.add(cityNo);
                                                                                                    Collections.sort(classNo_list);

                                                                                                }
                                                                                            }
                                                                                            ArrayAdapter region_apaterC = new ArrayAdapter(Xel_Users_Register.this, R.layout.xel_user_register_class_spinner_item, spiner_class_list);
                                                                                            region_apaterC.notifyDataSetChanged();
                                                                                            spiner_class.setAdapter(region_apaterC);
                                                                                            spiner_class.setPrompt("请选择班级");
                                                                                            spiner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                                @Override
                                                                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                                                    clss = spiner_class_list.get(position).toString();
                                                                                                    Log.d("排序后的年级", spiner_class_list.toString());
                                                                                                    clssNub = spiner_class_list.get(position).toString();
                                                                                                }

                                                                                                @Override
                                                                                                public void onNothingSelected(AdapterView<?> parent) {

                                                                                                }
                                                                                            });
                                                                                        } catch (JSONException e) {
                                                                                            e.printStackTrace();
                                                                                        }

                                                                                    }

                                                                                    @Override
                                                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                                                    }
                                                                                });


                                                                            }
                                                                        }


                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }

                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> parent) {

                                                                }
                                                            });


                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        /**
                                                         * 绑定学校数据
                                                         */


//                                                        JsonAnalysisOne.JsonAS(dataSch,"schoolNo",10,regionNub,classNo_list);

                                                        Log.d("GG", dataSch.toString());
                                                        Log.d("GG", dataNian.toString());
                                                        Log.d("GG", dataBan.toString());


                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


//                                                    Log.d("GG",response.toString());


                                                }
                                            }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError arg0) {

                                            ToastUtil.showToast(Xel_Users_Register.this, "请求失败,请查看网络链接");
                                            Log.d("JSON", arg0 + "");
                                        }
                                    });
                                    request.setTag(this);

                                    VolleyUtil.getQueue(Xel_Users_Register.this).add(request);


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

                ToastUtil.showToast(Xel_Users_Register.this, "获取信息失败，请查看联网状态！");
                Log.d("JSON", arg0 + "");
            }
        });


        request.setTag(this);

        VolleyUtil.getQueue(this).add(request);
    }

    /**
     * 下面是对省份，城市，地区
     * 学校，班级
     * 列表操作
     *
     * @param
     */
    public void Spinner_operation() {
        //省份Spinner
        spiner_province = (Spinner) findViewById(R.id.spiner_province);
        spiner_city = (Spinner) findViewById(R.id.spiner_city);
        spiner_region = (Spinner) findViewById(R.id.spiner_region);
        spiner_grade = (Spinner) findViewById(R.id.spiner_grade);
        spiner_class = (Spinner) findViewById(R.id.spiner_class);
        spiner_school = (Spinner) findViewById(R.id.spiner_school);

        //Spinner的list
        spiner_province_list = new ArrayList();
        spiner_city_list = new ArrayList();
        spiner_region_list = new ArrayList();
        spiner_school_list = new ArrayList();
        spiner_class_list = new ArrayList();
        spiner_grade_list = new ArrayList();


    }

    //点击事件
    //学生
    public void student_btn(View view) {
        phone.setEnabled(false);
        final String mphone = phone.getText().toString();
        isUserExist = 0;
        ROLE = "1";
        Log.d(TAG, "传入stu_btn的参数:" + "mphone" + mphone + " ROLE" + ROLE);
        if (mphone.length() == 11) {
            final ProgressDialog dialog = ProgressDialog.show(this, "验证用户中,请稍后...", " ");
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        recode = Post_RegisterVaild.getStringCha(Constant.Post_url_For_registerValid, mphone, ROLE);
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", recode);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String response = msg.getData().getString("response");
                    Log.d(TAG, "response==" + response);
                    try {
                        json = new JSONObject(response);
                        success = json.getString("success");
                        if (success.equals("200")) {
                            isUserExist = 1;
                        } else if (success.equals("100")) {
                            isUserExist = 2;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    if (isUserExist == 2) {
                        ROLE = "3";
                        student_perent_btn.setBackground(getResources().getDrawable(R.drawable.boderbar_nor_all));
                        //        parent_btn.setBackground(getResources().getDrawable(R.drawable.parent_nor));
                        teacher_btn.setBackground(getResources().getDrawable(R.drawable.boderbar_nor_all));
                        Toast.makeText(ContextHolder.getContext(), "当前角色用户已存在", Toast.LENGTH_SHORT).show();
                    } else if (isUserExist == 1) {
                        student_perent_btn.setBackground(getResources().getDrawable(R.drawable.boderbar_sel_all));
                        teacher_btn.setBackground(getResources().getDrawable(R.drawable.boderbar_nor_all));
                    } else {
                        ROLE = "3";
                        Log.d("reg", "doNothing");
                    }

                }
            };

        } else {
            phone.setError("请输入正确的手机号");
        }
        phone.setEnabled(true);

    }

    //老师
    public void teacher_btn(View view) {
        phone.setEnabled(false);
        final String mphone = phone.getText().toString();
        isUserExist = 0;
        ROLE = "0";
        Log.d(TAG, "传入tCher_btn的参数:" + "mphone" + mphone + " ROLE" + ROLE);
        if (mphone.length() == 11) {
            InitView_rgister_et();
            final ProgressDialog dialog = ProgressDialog.show(this, "验证用户中,请稍后...", " ");
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        recode = Post_RegisterVaild.getStringCha(Constant.Post_url_For_registerValid, mphone, ROLE);
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", recode);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String response = msg.getData().getString("response");
                    Log.d(TAG, "response==" + response);
                    try {
                        json = new JSONObject(response);
                        success = json.getString("success");

                        if (success.equals("200")) {
                            isUserExist = 1;
                        } else if (success.equals("100")) {
                            isUserExist = 2;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    if (isUserExist == 2) {
                        ROLE = "3";
                        student_perent_btn.setBackground(getResources().getDrawable(R.drawable.boderbar_nor_all));
                        //        parent_btn.setBackground(getResources().getDrawable(R.drawable.parent_nor));
                        teacher_btn.setBackground(getResources().getDrawable(R.drawable.boderbar_nor_all));
                        Toast.makeText(ContextHolder.getContext(), "当前角色用户已存在", Toast.LENGTH_SHORT).show();

                    } else if (isUserExist == 1) {
                        student_perent_btn.setBackground(getResources().getDrawable(R.drawable.boderbar_nor_all));
                        //        parent_btn.setBackground(getResources().getDrawable(R.drawable.parent_nor));
                        teacher_btn.setBackground(getResources().getDrawable(R.drawable.boderbar_sel_all));
                    } else {
                        ROLE = "3";
                        Log.d("reg", "doNothing");
                    }

                }
            };
        } else {
            phone.setError("请输入正确的手机号");
        }
        phone.setEnabled(true);
    }

    //提交按钮
    public void Register_btn(View view) {



        String mphone = phone.getText().toString();
        String mpsword = psword.getText().toString();
        String mpsword1 = psword1.getText().toString();
        if (phone.getText().length() != 11 ) {
            phone.setError("请输入正确的手机号");
        } else {
            if (psword.getText().length() < 6 && psword.getText().length() < 16) {
                psword.setError("密码长度不符合要求！");
            } else {

                if (!mpsword1.equals(mpsword)) {
                    Toast.makeText(Xel_Users_Register.this, "密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                    psword.setText("");
                    psword1.setText("");
                    return;
                }
                if (ROLE.equals("3")) {
                    Toast.makeText(Xel_Users_Register.this, "请选择角色", Toast.LENGTH_SHORT).show();
                } else {

                    if (et_ris_yanzhengma == null) {
                        Toast.makeText(Xel_Users_Register.this, "请输入验证码！", Toast.LENGTH_SHORT).show();
                    } else {
                        String checkCode = et_ris_yanzhengma.getText().toString();


                        if (!(checkCode.equals(duanxin_num))) {
                            Toast.makeText(Xel_Users_Register.this, "验证码错误，请先输入验证码！", Toast.LENGTH_SHORT).show();
                        } else {
                            List<User> list = Db_UserService.getInstance(this).loadAllNote();
                            for (User user : list) {
                                if (user.getPhone().equals(mphone)) {
                                    Toast.makeText(Xel_Users_Register.this, "该用户已经存在！", Toast.LENGTH_SHORT).show();
                                    phone.setText("");
                                    psword.setText("");
                                    psword1.setText("");
                                    return;
                                }
                            }
                            //  String region_nub, String school, String school_nub, String grade, String grade_nub, String classes, String classes_nub
                            User user = new User(null, mphone, mpsword, ROLE, null, province, provinceNub, city, cityNub, region, regionNub, school, schoolNub, grades, gradeNub, clss, clssNub, null, null, null, null, null, null, null, null, null, null, null);
                            Log.d("User_data", "i" + JSON.toJSONString(user));
                            Db_deleteAll_Data.DeleteAll(this);
                            Db_UserService.getInstance(this).saveNote(user);


//        App.getDaoSession(this).getUserDao().insert(user);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("mobile", user.getPhone());
                            map.put("password", user.getPsword());
                            map.put("role", user.getRole());

                            map.put("city_number", user.getCity_nub());
                            map.put("class_number", user.getGrade_nub() + user.getClasses_nub());
                            MsharedPrefrence.Setphonewsd(this, mphone, mpsword, ROLE);
                            ROLE = "3";

                            ProgressDialog dialog = ProgressDialog.show(this, "注册中...", "请稍后！");
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setCancelable(true);
                            PostUser.Linkage(this, user.getPhone(), Constant.Post_url, map, dialog);

                        }
                    }
                }

            }
        }
    }


    private void initView() {
        phone = (EditText) findViewById(R.id.rigist_et_phone);
        psword = (EditText) findViewById(R.id.psword);
        psword1 = (EditText) findViewById(R.id.psword1);

        student_perent_btn = (Button) findViewById(R.id.student_perent_btn);
//        parent_btn = (Button) findViewById(R.id.home1_btn);
        teacher_btn = (Button) findViewById(R.id.teacher_btn);

        btn_rigist_get_yanzhengme = (Button) findViewById(R.id.rigister_yanzhegnma_btn);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent myIntent;
            myIntent = new Intent(Xel_Users_Register.this, xel_user_login.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void InitView_rgister_et() {
        //et_ris_phone = (EditText) findViewById(R.id.rigist_et_phone);
        et_ris_yanzhengma = (EditText) findViewById(R.id.rigist_et_yanzhengma);
        et_ris_yanzhengma.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
                    get_rigist_yanzhengma = et_ris_yanzhengma.getText().toString();
                    if (get_rigist_yanzhengma.equals(duanxin_num)) {
                        //tv_rigist_true.setVisibility(View.VISIBLE);
                        btn_rigist_get_yanzhengme.setText("验证成功");
                    } else {
                        Toast.makeText(Xel_Users_Register.this, "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        et_ris_yanzhengma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6 && duanxin_num.equals(s)) {
                    btn_rigist_get_yanzhengme.setText("验证码正确");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void InitView_yanzhengma() {
        btn_rigist_get_yanzhengme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setEnabled(false);
                final String mphones = phone.getText().toString();
                isUserExist = 0;
                ROLE = "0";
                Log.d(TAG, "传入tCher_btn的参数:" + "mphone" + mphones + " ROLE" + ROLE);
                if (mphones.length() == 11) {
                    InitView_rgister_et();
                    final ProgressDialog dialog = ProgressDialog.show(Xel_Users_Register.this, "验证用户中,请稍后...", " ");
                    dialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                recode = Post_RegisterVaild.getStringCha(Constant.Post_url_For_registerValid, mphones, ROLE);
                                Message msg = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("response", recode);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String response = msg.getData().getString("response");
                            Log.d(TAG, "response==" + response);
                            try {
                                json = new JSONObject(response);
                                success = json.getString("success");

                                if (success.equals("200")) {
                                    isUserExist = 1;
                                } else if (success.equals("100")) {
                                    isUserExist = 2;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                            if (isUserExist == 2) {
                                ROLE = "3";
                                Toast.makeText(ContextHolder.getContext(), ""+success.equals("error"), Toast.LENGTH_SHORT).show();
                            }else if (isUserExist == 1)
                            {
                                phone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(Xel_Users_Register.this, "请等待读秒结束再进行修改", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            recode = Post_CheckCode.getStringCha(Constant.Post_url_For_checkCode, mphones, "0");
                                            Message msg = new Message();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("response", recode);
                                            msg.setData(bundle);
                                            handler.sendMessage(msg);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                handler = new Handler() {
                                    @Override
                                    public void handleMessage(Message msg) {
                                        super.handleMessage(msg);
                                        String response = msg.getData().getString("response");
                                        Log.d(TAG, "response==" + response);
                                        try {
                                            json = new JSONObject(response);
                                            success = json.getString("success");
                                            if (success.equals("200")) {
                                                duanxin_num = json.getString("data");
                                                Toast.makeText(Xel_Users_Register.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                                                time = new TimeCount(30000, 1000);//构造CountDownTimer对象
                                                time.start();//开始计时

                                            } else if (success.equals("100")) {
                                                Toast.makeText(Xel_Users_Register.this, "短信发送失败,请重试", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                            }
                            else {
                                ROLE = "3";
                                Log.d("reg", "doNothing");
                            }
                        }
                    };

                } else {
                    phone.setError("请输入正确的手机号");
                }

            }
        });
        phone.setEnabled(true);
    }

    //手机验证码倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btn_rigist_get_yanzhengme.setText("重新验证");
            btn_rigist_get_yanzhengme.setClickable(true);

        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btn_rigist_get_yanzhengme.setClickable(false);
            btn_rigist_get_yanzhengme.setText(millisUntilFinished / 1000 + "秒");
        }
    }







}
