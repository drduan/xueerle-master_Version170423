package com.neusoft.sample.View.users;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.jaeger.library.StatusBarUtil;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_CheckCode;
import com.neusoft.sample.Model.Post_RegisterVaild;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.util.ContextHolder;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class xel_user_forgetpassword extends BaseActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "XEL_USER_FORGET";
    private ArrayAdapter<String> adapter_spinner;
    private List<String> list_spinner;
    String duanxin_num = "";
    String get_phone = "";
    String get_yanzhengma = "";
    private static String num = "4";
    Boolean bool;
    int ff = 0;
    private Spinner login_spinner;   //spinner  选择角色
    private TimeCount time;
    private Button btn_yanzhengma;  //获取验证码
    private EditText et_pw1, et_pw2, et_yanzhengma, et_forget_phone;
    //验证用户有效性以决定发送验证码的返回原始数据
    String recode = "";
    Handler handler;
    JSONObject json = null;
    String success;
    int isUserExist = 0;    //   isUserExist = 1  ;  已经注册， isUserExist = 2；  该用户尚未注册不能修改密码
    @Override
    protected void onResume() {
        super.onResume();
        num = "4";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_user_forget_psw);
        StatusBarUtil.setTranslucent(this, 0);
        bool = Consant_stringg.is_internet(this);
        Init_num();
        IniView_spinner();
        InitView_yanzhengma();
        IntiView_yanzhengma();
        InitView_get_edit();
        InitView_yanzhegnma_trueandfalse();
    }
    private void InitView_yanzhegnma_trueandfalse() {
        et_yanzhengma = (EditText) findViewById(R.id.forget_yanzhengma);

        et_yanzhengma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_yanzhengma.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    get_yanzhengma = et_yanzhengma.getText().toString();
                    if (get_yanzhengma.equals(duanxin_num)) {
                        Toast.makeText(xel_user_forgetpassword.this, "验证码正确！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(xel_user_forgetpassword.this, "验证码错误请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void InitView_get_edit() {
        et_forget_phone = (EditText) findViewById(R.id.forget_phone);
        et_yanzhengma = (EditText) findViewById(R.id.forget_yanzhengma);
        login_spinner = (Spinner) findViewById(R.id.spinner_update);
    }
    private void IntiView_yanzhengma() {
        et_yanzhengma = (EditText) findViewById(R.id.forget_yanzhengma);
        String yanzhemgma = et_yanzhengma.getText().toString();
    }
    private void InitView_yanzhengma() {
        btn_yanzhengma = (Button) findViewById(R.id.user_phoneyangzhengma);
        btn_yanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fPhone = et_forget_phone.getText().toString();
                if (!(fPhone.length() == 11)) {
                    et_forget_phone.setError("请输入正确的手机号");
                } else if (num.equals("4")) {
                    Toast.makeText(xel_user_forgetpassword.this, "请选择角色", Toast.LENGTH_SHORT).show();
                } else if (bool) {
                    if (isUserExist==1) {
                        et_forget_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(xel_user_forgetpassword.this, "请等待读秒结束再进行修改", Toast.LENGTH_SHORT).show();
                            }
                        });
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    recode = Post_CheckCode.getStringCha(Constant.Post_url_For_checkCode, fPhone, "1");
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
                                Log.d("xel_user_forgetPsw", "response==" + response);
                                try {
                                    json = new JSONObject(response);
                                    success = json.getString("success");
                                    if (success.equals("200")) {
                                        duanxin_num = json.getString("data");
                                        Toast.makeText(xel_user_forgetpassword.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                                        time = new TimeCount(30000, 1000);//构造CountDownTimer对象
                                        time.start();//开始计时

                                    } else if (success.equals("100")) {
                                        Toast.makeText(xel_user_forgetpassword.this, "短信发送失败,请重试", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                    }else if (isUserExist==2){
                        Toast.makeText(ContextHolder.getContext(),"当前角色用户不存在",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(xel_user_forgetpassword.this, "验证码请求失败,请联网重试", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(xel_user_forgetpassword.this, "请检查您的网络设置！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String aa = list_spinner.get(position);
        final String mPhone = et_forget_phone.getText().toString();
        if (aa.equals("请选择角色")) {
            num = "4";
        } else{
            Toast.makeText(this,"检查号码有效性...",Toast.LENGTH_SHORT).show();
            if (mPhone.length()==11){
                if (aa.equals("学生")||aa.equals("家长")||aa.equals("老师")){
                    switch (aa) {
                        case "学生":
                            num = "1";
                            RoleVaildThread thread1 = new RoleVaildThread(mPhone,"1");
                            thread1.start();
                            break;
                        case "家长":
                            num = "2";
                            RoleVaildThread thread2 = new RoleVaildThread(mPhone,"2");
                            thread2.start();
                            break;
                        case "老师":
                            num = "0";
                            RoleVaildThread thread3 = new RoleVaildThread(mPhone,"0");
                            thread3.start();
                            break;
                    }
                }
            }else {
                et_forget_phone.setError("请输入正确号码");
            }
        }
        Log.d("@@", "position" + aa);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //手机验证码倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btn_yanzhengma.setText("重新验证");
            btn_yanzhengma.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btn_yanzhengma.setClickable(false);
            btn_yanzhengma.setText(millisUntilFinished / 1000 + "秒");
        }
    }


    //最后的提交修改
    public void update_true(View view) {
        et_forget_phone = (EditText) findViewById(R.id.forget_phone);
        et_pw1 = (EditText) findViewById(R.id.forget_password1);
        et_pw2 = (EditText) findViewById(R.id.forget_password2);
        final String pw1 = et_pw1.getText().toString();
        String pw2 = et_pw2.getText().toString();
        String get_yanzhengma = et_yanzhengma.getText().toString();
        final String forget_phone = et_forget_phone.getText().toString();
        if (!(get_yanzhengma.equals(duanxin_num))) {
            Toast.makeText(xel_user_forgetpassword.this, "验证码错误，请重新输入!", Toast.LENGTH_SHORT).show();
        } else if (!pw1.equals(pw2)) {
            Toast.makeText(xel_user_forgetpassword.this, "两次输入密码不一致  请重新输入", Toast.LENGTH_SHORT).show();
            et_pw1.setText("");
            et_pw2.setText("");
        } else {
            final HashMap<String, String> map = new HashMap<>();
            map.put("mobile", forget_phone);
            map.put("password", pw1);
            map.put("role", num);
            Log.d("@@","num"+num);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject json = null;
                    try {
                        try {
                            String a = Post_learn_rijiyuelei.getStringCha(Constant.post_forget__password, map);
                            json = new JSONObject(a);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String success = json.getString("success");
                        if (success.equals("200")) {
                            List<User> users = Db_UserService.getInstance(xel_user_forgetpassword.this).loadAllNote();
                            for (User user : users) {
                                if (forget_phone.equals(user.getPhone())) {
                                    user.setPsword(pw1);
                                    Db_UserService.getInstance(xel_user_forgetpassword.this).saveNote(user);
                                    break;
                                }
                            }
                            Intent intent = new Intent(xel_user_forgetpassword.this, xel_user_login.class);
                            startActivity(intent);
                            Log.d("@@", "跳转之后");
                            finish();
                        } else {
                            Looper.prepare();
                            Toast.makeText(xel_user_forgetpassword.this, "修改失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Log.d("@@", "ff" + ff);
            if (ff == 0) {
            }
        }
    }

    /*选择角色信息  并且验证  该用户是否注册*/
    private void Init_num() {
        duanxin_num = Consant_stringg.yanzhengma();
    }

    private class RoleVaildThread extends Thread{
        String mPhone = "";
        String Role = "";
        RoleVaildHandler rolehandler = new RoleVaildHandler();
        RoleVaildThread(String Phone, String role){
            this.mPhone = Phone;
            this.Role = role;
        }
        @Override
        public void run() {
            super.run();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("@@","角色"+Role);
                        recode = Post_RegisterVaild.getStringCha(Constant.Post_url_For_registerValid, mPhone, Role);
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", recode);
                        msg.setData(bundle);
                        rolehandler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private class RoleVaildHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String response = msg.getData().getString("response");
            Log.d(TAG, "response==" + response);
            try {
                json = new JSONObject(response);
                success = json.getString("success");
                if (success.equals("200")) {
                    isUserExist = 2;
                    Log.d("@@","角色未注册");
                    //该用户未注册不能修改密码
                } else if (success.equals("100")) {
                    isUserExist = 1;
                    Log.d("@@","角色已经注册" +
                            "");
                    //该用户已注册可以修改密码
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (isUserExist==2){
                Toast.makeText(ContextHolder.getContext(),"当前角色用户不存在"+"\n"+"请重新输入角色或号码",Toast.LENGTH_SHORT).show();
                login_spinner.setSelection(0,true);
                //如果该用户未注册，将选择清空，
            }else if(isUserExist==1){
                //如果该用户已经注册并且可以修改不做任何操作，
            }else {
                Log.d("reg","doNothing");
            }
        }
    }
    private void IniView_spinner() {
        login_spinner = (Spinner) findViewById(R.id.spinner_update);
        list_spinner = new ArrayList<String>();
        list_spinner.add("请选择角色");
        list_spinner.add("学生");
        list_spinner.add("家长");
        list_spinner.add("老师");
        adapter_spinner = new ArrayAdapter<String>(this, R.layout.login_spinner_itrm, list_spinner);
        adapter_spinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        login_spinner.setAdapter(adapter_spinner);
        login_spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = getIntent();
        in.setClass(this, xel_user_login.class);
        startActivity(in);
        finish();
    }
}
