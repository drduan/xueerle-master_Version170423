package com.neusoft.sample.View.users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jaeger.library.StatusBarUtil;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_StudyGoodInfoService;
import com.neusoft.sample.Ctrl.Db_StudyGoodTermService;
import com.neusoft.sample.Ctrl.Db_TeacherService;
import com.neusoft.sample.Ctrl.Db_TextOneStructureService;
import com.neusoft.sample.Ctrl.Db_TextTwoStructureService;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.GreenDao.StudyGoodInfo;
import com.neusoft.sample.GreenDao.StudyGoodTerm;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.GreenDao.teacher;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Get_findOneTweGou;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.PostUploadSchedule;
import com.neusoft.sample.Model.PostUserValid;
import com.neusoft.sample.Model.PostUserValid_again;
import com.neusoft.sample.Model.PostUsertoID;
import com.neusoft.sample.Model.Post_common;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.BottomTabSwitcherActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by duanxudong on 16/5/26.
 */



/**
 * 潜在问题登陆的时候，情况如下：
 * user.setProductNo();
 * 没有出现给
 */
public class xel_user_login extends BaseActivity {
    private  String STATUS = "1";
    Handler handler;
    private Spinner login_spinner;
    public static final String TAG = "ttser";
    private List<String> list_spinner;
    private ArrayAdapter<String> adapter_spinner;
    private EditText login_phone;
    private EditText login_psword;
    private HashMap<String, String> map;
    private int Data;
    private int Data1;

    public static String QueryID;
    ProgressDialog dialog;

    public Handler handlerteacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        StatusBarUtil.setTranslucent(this, 0);

        IniView_spinner();
        String[] phonewsdrole = MsharedPrefrence.Getphonewsd(this);
        login_phone = (EditText) findViewById(R.id.login_username);
        if (!Consant_stringg.is_internet(this)) {
            Toast.makeText(this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }
        Log.d("phone", phonewsdrole[0]);
        if ("".equals(phonewsdrole[0])) {
            App.getDaoMaster(this).newSession().clear();
        }
        login_phone.setText(phonewsdrole[0]);
        login_psword = (EditText) findViewById(R.id.login_passwork1);
        login_psword.setText(phonewsdrole[1]);

        login_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String role = list_spinner.get(position);
                Log.d("ROLE", role);
                if ("学生".equals(role)) {
                    STATUS = "1";
                } else if ("家长".equals(role)) {
                    STATUS = "2";
                } else if ("老师".equals(role)) {
                    STATUS = "0";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void Register(View view) {
        startActivity(new Intent().setClass(xel_user_login.this, Xel_Users_Register.class));
        finish();
    }

    public  void login(View view) {

        if (!(login_phone.getText().length() == 11)) {
            login_phone.setError("请输入正确的手机号");
        } else {
            dialog = ProgressDialog.show(this, "登录中...", "正在加载数据，请稍后！");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            Log.d("@@ROLE 当前状态", STATUS);
            if ("3".equals(STATUS)) {
                dialog.dismiss();
                Toast.makeText(this, "请选择正确的角色！", Toast.LENGTH_SHORT).show();
                return;
            }
            //老师登录
            if ("0".equals(STATUS)) {
                Db_TeacherService.getInstance(xel_user_login.this).deleteAllNote();

                final String mphone = login_phone.getText().toString();
                final String mpsword = login_psword.getText().toString();
                final HashMap<String, String> mapsp = new HashMap<>();
                mapsp.put("username", mphone);
                mapsp.put("password", mpsword);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String reponse = Post_common.getStringCha(Constant.teacherLogin, mapsp);
                            if (reponse != null) {
                                Thread.interrupted();
                                JSONObject jsonObject = new JSONObject(reponse);
                                Log.d(TAG,"TeacherLoginJson"+jsonObject);
                                if ("200".equals(jsonObject.getString("success")))
                                {
                                    teacher teachers = JSON.parseObject(jsonObject.getString("data"), teacher.class);
                                    Db_TeacherService.getInstance(xel_user_login.this).saveNote(teachers);
                                    Log.d("reponse--Data", reponse);
                                    String TEXTNUM =  teachers.getTextBookNum();
                                    Message message = new Message();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("TEXTNUM", TEXTNUM);
                                    message.setData(bundle);
                                    handlerteacher.sendMessage(message);
                                }else {
                                    dialog.cancel();
                                    Looper.prepare();
                                  Toast.makeText(xel_user_login.this,jsonObject.getString("error")+"",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                handlerteacher = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String TEXTNUM = msg.getData().getString("TEXTNUM");
                        final HashMap hashMap=new HashMap();
                        hashMap.put("bookNo",TEXTNUM);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                 Response response= Get_findOneTweGou.getStringCha(Constant.teacherfindOneTweGou,hashMap);
                                    if (response.code()==200) {
                                        String result = response.body().string();
                                        Log.d("result--data", result);
                                        if (result != null) {
                                            JSONObject object = new JSONObject(result);
                                            String success = (String) object.get("success");
                                            Log.d("object+success", object + "  " + success);
                                            if (success.equals("200")) {

                                                Db_TextOneStructureService.getInstance(xel_user_login.this).deleteAllNote();
                                                Db_TextTwoStructureService.getInstance(xel_user_login.this).deleteAllNote();

                                                //  快速  将  字符串转换成   hasmap
                                                Map<String, Object> map = (Map<String, Object>) JSON.parse(result);
                                                //***********************************************************************************************************************************
                                                //***********************************************************************************************************************************
                                                //教材一级结构的存储数据库
                                                String JcOneNumber = map.get("JcOneNumber").toString();
                                                int JcOneNum = Integer.parseInt(JcOneNumber);//获取当前的一级结构有多少个
                                                for (int i = 0; i < JcOneNum - 1; i++) {
                                                    int j = i + 1;
                                                    String one = "JcOnelist" + j;
                                                    String JcOneNumbers = map.get(one).toString();
                                                    List<TextOneStructure> textOneStructureList = JSON.parseArray(JcOneNumbers, TextOneStructure.class);
                                                    Db_TextOneStructureService.getInstance(xel_user_login.this).saveNoteLists(textOneStructureList);
                                                }
                                                //***********************************************************************************************************************************
                                                //教材二级结构的存储数据库
                                                String JcTwoNumber = map.get("JcTwoNumber").toString();
                                                int JcTwoNum = Integer.parseInt(JcTwoNumber);//获取当前的一级结构有多少个
                                                for (int i = 0; i < JcTwoNum; i++) {
                                                    int j = i + 1;
                                                    String one = "JcTwolist" + j;
                                                    if (map.get(one) != null) {
                                                        String JcOneNumbers = map.get(one).toString();
                                                        List<TextTwoStructure> textTwoStructureslist = JSON.parseArray(JcOneNumbers, TextTwoStructure.class);
                                                        Db_TextTwoStructureService.getInstance(xel_user_login.this).saveNoteLists(textTwoStructureslist);

                                                    }
                                                }
                                                startActivity(new Intent().setClass(xel_user_login.this, BottomTabSwitcherActivity.class));
                                                //老师登录手机号 密码 角存储
                                                MsharedPrefrence.Setphonewsd(xel_user_login.this, mphone, mpsword, "0");

                                                Thread.interrupted();

                                            } else {
                                                dialog.dismiss();
                                                Looper.prepare();
                                                Toast.makeText(xel_user_login.this, "请求失败,请查看网络连接状态!", Toast.LENGTH_LONG).show();
                                                Looper.loop();
                                            }
                                        }
                                    }else {
                                        dialog.dismiss();
                                        Looper.prepare();
                                        Toast.makeText(xel_user_login.this, "请求失败,服务器端错误!("+response.code()+")", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                };


            }else {
                if (CompareData()) {
                    //当前活动的用户做相应的操作
                    /***
                     //一,检索本地教材和章节结构
                     /**
                     * 获取其user表中的教材号
                     * 然后根据教材号判断教材表中是否存在其教材号
                     */
                    final String mphone = login_phone.getText().toString();
                    String mpsword = login_psword.getText().toString();
                    HashMap<String, String> mapsp = new HashMap<>();
                    mapsp.put("username", mphone);
                    mapsp.put("password", mpsword);
                    mapsp.put("type", "1");
                    PostUserValid_again.Linkage(xel_user_login.this, mphone, Constant.Post_url_For_UserValid, mapsp, dialog);
                    Log.d("验证成功", "pass");
                    MsharedPrefrence.Setphonewsd(this, mphone, mpsword, STATUS);
                    MsharedPrefrence.SetUserisFirstLoading(this, mphone, "0");
                    MsharedPrefrence.SetUserTempImgDir(this, mphone, null);

                    List<User> list = Db_UserService.getInstance(this).loadAllNote();//user表的list


                    if (list.get(0).getPhone().equals(mphone)) {//手机存在
                        final String nub = list.get(0).getProductNo();
                        String city = list.get(0).getCity();
                        final String cityNo = list.get(0).getCity_nub();
                        //本地无教材号

                        Log.d(TAG, "当前用户唯一服务码" + QueryID);
                        if (nub == null) {
                            Map<String, String> paramMaps = new HashMap<>();
                            Log.d("当前用户唯一服务码下面的user_id", "p" + list.get(0).getServer_id());
                            paramMaps.put("user_id", list.get(0).getServer_id());

                            PostUsertoID.Linkage(this, mphone, Constant.Post_url_For_UsertoID, paramMaps, dialog);

                            Log.d("本地该手机号的教材号无", "");

                        }
                        List<StudyGoodInfo> list1 = Db_StudyGoodInfoService.getInstance(this).loadAllNote();//教材表的list
                        for (final StudyGoodInfo studyGoodInfo : list1) {
                            if (nub.equals(studyGoodInfo.getProductNo()))//判断其商品中的号是否对应
                            {//存在时
                                Log.d("login", "核对用户的有效期-- " + studyGoodInfo.getDueDate());
                                new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        String da = "";
                                        String urlString = "http://122.156.218.189:8080";
                                        URL url;
                                        long ld;
                                        try {
                                            url = new URL(urlString);//获得资源对象
                                            URLConnection uc = url.openConnection();//生成连接对象
                                            uc.connect();//发出连接
                                            ld = uc.getDate();//取得网站日期时间(时间戳)
                                            Date date = new Date(ld);// 转换为标准时间对象

                                            String times = Constant.getyymmdd(date);
                                            da = times;
                                            Log.d("AA", da);

                                            if (Integer.parseInt(studyGoodInfo.getDueDate()) > Integer.parseInt(da))//在有效期内
                                            {
                                                Log.d("Vaild", "是本地教材的并且没过期");

                                                //上传本地教材最后更新时间戳列表

//                                                    List<StudyGoodItem> studyGoodItemList1 = Db_StudyGoodItemService.getInstance(xel_user_login.this).loadAllNote();
                                                map = new HashMap<>();
                                                String itemlist = null;
                                                List<StudyGoodTerm> list3 = Db_StudyGoodTermService.getInstance(xel_user_login.this).loadAllNote();
                                                itemlist = JSON.toJSONString(list3);
                                                map.put("allJiaocai", itemlist);
                                                map.put("cityNo", cityNo);
                                                Log.d("时间戳", map.get("allJiaocai").toString());
                                                PostUploadSchedule.Linkage(xel_user_login.this, mphone, Constant.Post_url_For_UploadSchedule, map, dialog);

                                            } else {
                                                //这里是过期的操作
                                                //Overdue=0表示过期
                                                Log.d("Vaild", "是本地教材的并且过期---Overdue=0表示过期");
                                                studyGoodInfo.setOverdue("0");
                                                return;
                                            }
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();

                            } else {
                                //不存在时
                                //上传用户的ID
                                Map<String, String> paramMaps = new HashMap<>();
                                List<User> list2 = Db_UserService.getInstance(this).loadAllNote();//user表的list
                                for (User users1 : list2) {
                                    if (users1.getPhone().equals(mphone)) {//手机存在
                                        paramMaps.put("user_id", users1.getServer_id());
                                    }
                                }
                                PostUsertoID.Linkage(this, mphone, Constant.Post_url_For_UsertoID, paramMaps, dialog);

                            }
                        }

                    }


                } else if (!CompareData())
                //本地无用户ID则上传后台进行后台有效性的验证
                {
                    CompareServer();

                } else {


                    dialog.dismiss();
                    Toast.makeText(this, "您输入的手机号或密码不对，请重新输入！！", Toast.LENGTH_SHORT).show();
                }
            }

        }


    }

    /**
     * 从后台数据库中验证用户的有效性
     */
    public void CompareServer() {
        String mphone = login_phone.getText().toString();
        String mpsword = login_psword.getText().toString();
        HashMap<String, String> map = new HashMap<>();
        map.put("username", mphone);
        map.put("password", mpsword);
        map.put("type", STATUS);
        User user = new User(null, mphone, mpsword, STATUS, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        //Db_deleteAll_Data.DeleteAll(this);

        Db_UserService.getInstance(this).saveNote(user);
        MsharedPrefrence.Setphonewsd(this, mphone, mpsword, STATUS);
        PostUserValid.Linkage(this, mphone, Constant.Post_url_For_UserValid, map, dialog);
        Log.d("SUCESS", "*********************************");
    }

    /**
     * 从本地数据库中读取数据然后与当前的内容进行对比
     */
    public boolean CompareData() {
        String mphone = login_phone.getText().toString();
        String mpsword = login_psword.getText().toString();
        List<User> list = Db_UserService.getInstance(this).loadAllNote();
        for (User user : list) {
            if (user.getPhone().equals(mphone) && user.getPsword().equals(mpsword) && STATUS.equals(user.getRole())) {
                App.GetSharePrefrence(this, mphone);
                return true;
            }
        }
        return false;
    }

    private void IniView_spinner() {
        login_spinner = (Spinner) findViewById(R.id.spinner_login);
        list_spinner = new ArrayList<String>();
        list_spinner.add("学生");
        list_spinner.add("家长");
        list_spinner.add("老师");
        adapter_spinner = new ArrayAdapter<String>(this, R.layout.login_spinner_itrm, list_spinner);
        adapter_spinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        login_spinner.setAdapter(adapter_spinner);
        if (MsharedPrefrence.Getphonewsd(this)[2].equals("1"))
            login_spinner.setSelection(0);
        else if (MsharedPrefrence.Getphonewsd(this)[2].equals("0")) {
            login_spinner.setSelection(2);
        } else {
            login_spinner.setSelection(1);
        }
    }

    public void forgetpassword(View view) {
        startActivity(new Intent().setClass(xel_user_login.this, xel_user_forgetpassword.class));
        finish();
    }

    public void Resetlogin(String mphone, String mpsword) {
        Toast.makeText(this, "用户不存在，请您注册！", Toast.LENGTH_SHORT).show();
        login_phone.setText(mphone);
        login_psword.setText(mpsword);
        STATUS = "3";
    }

    @Override
    protected void onStop() {
        super.onStop();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // dialog.cancel();
    }
}
