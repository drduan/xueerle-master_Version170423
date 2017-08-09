package com.neusoft.sample.View.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.wenchengcheng.ActionSheetDialog;
import com.neusoft.sample.Ctrl.wenchengcheng.CheckForUpdateService;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.Ctrl.wenchengcheng.RoundImageView;
import com.neusoft.sample.Ctrl.wenchengcheng.SerializableMap;
import com.neusoft.sample.Ctrl.wenchengcheng.user_get;
import com.neusoft.sample.Ctrl.wenchengcheng.verifyStoragePermissions;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.Post_Pushwork;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.Model.mutils.ToastUtil;
import com.neusoft.sample.View.BaseFragment;
import com.neusoft.sample.View.users.BuyCourseActivity;
import com.neusoft.sample.View.users.xel_user_login;
import com.neusoft.sample.View.xel_mine.MyHomeWork.MyHomeWork;
import com.neusoft.sample.View.xel_mine.MyHomeWork.MyWorkActivity;
import com.neusoft.sample.View.xel_mine.MyHomeWork.get_tingxie;
import com.neusoft.sample.View.xel_mine.Xel_mine_bibeigushicimulu;
import com.neusoft.sample.View.xel_mine.Xel_mine_feedback;
import com.neusoft.sample.View.xel_mine.Xel_mine_learntrack.Xel_mine_learntrack;
import com.neusoft.sample.View.xel_mine.Xel_mine_notify.Xel_mine_notify_main;
import com.neusoft.sample.View.xel_mine.Xel_mine_ranklist;
import com.neusoft.sample.View.xel_mine.Xel_mine_teachermypage;
import com.neusoft.sample.View.xel_mine.Xel_mine_techangfudao;
import com.neusoft.sample.View.xel_mine.Xel_mine_tuozhangushicimulu;
import com.neusoft.sample.View.xel_mine.Xel_mine_youxiuzuowenshangxi;
import com.neusoft.sample.View.xel_mine.xel_mine_studentmypage;
import com.neusoft.sample.util.ContextHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.neusoft.sample.Model.MsharedPrefrence.Getphonewsd;

//import com.neusoft.sample.Ctrl.wenchengcheng.CheckForUpdateUtil;

//import com.neusoft.sample.View.xel_mine.Xel_mine_notice.Xel_mine_notify_main;

//import com.neusoft.sample.Ctrl.wenchengcheng.ScrollSwipeRefreshLayout;

public class xel_mine_fragment extends BaseFragment {
    private String phoneNum;
    private user_get userss;
    public static User cUserInfo;
    public static User tUserInfo;
    private Map<Object, String> content;
    private static String TAG = "MINE_FRAGMENT";
    private String Query_ID = null;
    private String i_phone_inform="无";
    private String statusCode="0";
    private String user_icon_url, phone, mobile, motto, gender, qq_number, weixin_number, email, recipient, address, zip_code = null;
    LinearLayout feedback_tag, kecheng_goumai, userInfo_tag, update_pkg, mine_swipe_layout, shangchuanchengji,
            longhubang, gerenshezhi, xuexiguiji, tongzhigonggao, haoshtuijian, jiaoziyoufngan, lay_techangfudao, bibeigusicimulu, tuozhangushicimulu,
            stu_exit,duhougan,learnTrack,pushwork;
    RoundImageView user_img;
    LinearLayout stu_include,per_include,tea_include;
    TextView i_motto, pkg_name;
    TextView i_phone;
    public static String sPhone = null;
    private String sImgDir = null;
    //ScrollSwipeRefreshLayout mSwipeLayout;
    Bitmap bitmap;
    Handler handler;
    Handler bitmaphandler;
    Handler updateHandler;
    ProgressDialog dialog;
    String role[];   //查看登录的角色
    private Integer requestCode = 0;
    private String postUrl = null;

    ToggleButton on_off_button;

    public static xel_mine_fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        xel_mine_fragment fragment = new xel_mine_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExitApplication.getInstance().addActivity(getActivity());
        Boolean bool = Consant_stringg.is_internet(getActivity());
        if (!bool) {
            Toast.makeText(ContextHolder.getContext(), "请检查您的网络设置!", Toast.LENGTH_SHORT).show();
        }
        role =   MsharedPrefrence.Getphonewsd(getActivity());
        int layoutRes = -1;
        switch (role[2]){
            case "0":
                layoutRes = R.layout.xel_mine_fragment_teacher;
                break;
            case "1":
                layoutRes = R.layout.xel_mine_fragment_student;
                break;
            case "2":
                layoutRes = R.layout.xel_mine_fragment_parent;
                break;
        }
        View v = inflater.inflate(layoutRes, container, false);

        bitmaphandler = new Handler();
        verifyStoragePermissions.verifyStoragePermissions(getActivity());
        try {
            init(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if (role[2].equals("1")||role[2].equals("2")) {
            determineStateToGetServerInfo();
        }else {

        }*/
        return v;
    }

    private void init(View v) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = ContextHolder.getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(ContextHolder.getContext().getPackageName(), 0);
        String version = packInfo.versionName;
        String versiontv = "v" + Constant.getAppVersionName(ContextHolder.getContext());
        Log.d(TAG,"INIT ROLE"+role[2]);
        switch (role[2]){
            case "0":
                //tea_include.setVisibility(View.VISIBLE);
                gerenshezhi = (LinearLayout) v.findViewById(R.id.lay_gerenshezhi);
                update_pkg = (LinearLayout) v.findViewById(R.id.update_pkgVer);
                feedback_tag = (LinearLayout) v.findViewById(R.id.feedback_tag);
                kecheng_goumai = (LinearLayout) v.findViewById(R.id.kecheng_goumai);
                userInfo_tag = (LinearLayout) v.findViewById(R.id.userInfo_tag);
                tongzhigonggao = (LinearLayout) v.findViewById(R.id.lay_tongzhigonggao);
                lay_techangfudao = (LinearLayout) v.findViewById(R.id.lay_techangfudao);
                haoshtuijian = (LinearLayout) v.findViewById(R.id.lay_haoshutuijian);
                longhubang = (LinearLayout) v.findViewById(R.id.lay_longhubang);
                stu_exit = (LinearLayout) v.findViewById(R.id.exit);
                bibeigusicimulu = (LinearLayout) v.findViewById(R.id.lay_bibeigushicimulu);
                tuozhangushicimulu = (LinearLayout) v.findViewById(R.id.tuozhangushicimulu);
                duhougan = (LinearLayout) v.findViewById(R.id.duhougan);
                jiaoziyoufngan = (LinearLayout) v.findViewById(R.id.lay_jiaoziyoufang);
                pushwork= (LinearLayout) v.findViewById(R.id.pushwork);
                i_phone = (TextView) v.findViewById(R.id.info_phone);
                pkg_name = (TextView) v.findViewById(R.id.pkg_name);
                i_motto = (TextView) v.findViewById(R.id.info_motto);
                i_motto.getPaint().setFakeBoldText(true);
                user_img = (RoundImageView) v.findViewById(R.id.userInfo_tag_Icon);
                break;
            case "1":
                //stu_include.setVisibility(View.VISIBLE);
                gerenshezhi = (LinearLayout) v.findViewById(R.id.lay_gerenshezhi);
                update_pkg = (LinearLayout) v.findViewById(R.id.update_pkgVer);
                feedback_tag = (LinearLayout) v.findViewById(R.id.feedback_tag);
                kecheng_goumai = (LinearLayout) v.findViewById(R.id.kecheng_goumai);
                userInfo_tag = (LinearLayout) v.findViewById(R.id.userInfo_tag);
                tongzhigonggao = (LinearLayout) v.findViewById(R.id.lay_tongzhigonggao);
                lay_techangfudao = (LinearLayout) v.findViewById(R.id.lay_techangfudao);
                haoshtuijian = (LinearLayout) v.findViewById(R.id.lay_haoshutuijian);
                longhubang = (LinearLayout) v.findViewById(R.id.lay_longhubang);
                stu_exit = (LinearLayout) v.findViewById(R.id.exit);
                bibeigusicimulu = (LinearLayout) v.findViewById(R.id.lay_bibeigushicimulu);
                tuozhangushicimulu = (LinearLayout) v.findViewById(R.id.tuozhangushicimulu);
                duhougan = (LinearLayout) v.findViewById(R.id.duhougan);
                jiaoziyoufngan = (LinearLayout) v.findViewById(R.id.lay_jiaoziyoufang);
                pushwork= (LinearLayout) v.findViewById(R.id.pushwork);
                i_phone = (TextView) v.findViewById(R.id.info_phone);
                pkg_name = (TextView) v.findViewById(R.id.pkg_name);
                i_motto = (TextView) v.findViewById(R.id.info_motto);
                i_motto.getPaint().setFakeBoldText(true);
                user_img = (RoundImageView) v.findViewById(R.id.userInfo_tag_Icon);
                break;
            case "2":
                //per_include.setVisibility(View.VISIBLE);
                gerenshezhi = (LinearLayout) v.findViewById(R.id.lay_gerenshezhi);
                update_pkg = (LinearLayout) v.findViewById(R.id.update_pkgVer);
                feedback_tag = (LinearLayout) v.findViewById(R.id.feedback_tag);
                kecheng_goumai = (LinearLayout) v.findViewById(R.id.kecheng_goumai);
                userInfo_tag = (LinearLayout) v.findViewById(R.id.userInfo_tag);
                tongzhigonggao = (LinearLayout) v.findViewById(R.id.lay_tongzhigonggao);
                lay_techangfudao = (LinearLayout) v.findViewById(R.id.lay_techangfudao);
                haoshtuijian = (LinearLayout) v.findViewById(R.id.lay_haoshutuijian);
                longhubang = (LinearLayout) v.findViewById(R.id.lay_longhubang);
                xuexiguiji = (LinearLayout) v.findViewById(R.id.lay_xuexiguiji);
                stu_exit = (LinearLayout) v.findViewById(R.id.exit);
                bibeigusicimulu = (LinearLayout) v.findViewById(R.id.lay_bibeigushicimulu);
                tuozhangushicimulu = (LinearLayout) v.findViewById(R.id.tuozhangushicimulu);
                duhougan = (LinearLayout) v.findViewById(R.id.duhougan);
                learnTrack = (LinearLayout) v.findViewById(R.id.xel_learnTrack);
                jiaoziyoufngan = (LinearLayout) v.findViewById(R.id.lay_jiaoziyoufang);
                i_phone = (TextView) v.findViewById(R.id.info_phone);
                pkg_name = (TextView) v.findViewById(R.id.pkg_name);
                i_motto = (TextView) v.findViewById(R.id.info_motto);
                i_motto.getPaint().setFakeBoldText(true);
                user_img = (RoundImageView) v.findViewById(R.id.userInfo_tag_Icon);
                on_off_button= (ToggleButton) v.findViewById(R.id.on_off_button);
                break;
        }
        //mine_swipe_layout = (LinearLayout) v.findViewById(R.id.downSwipe_layout);
        //mSwipeLayout = (ScrollSwipeRefreshLayout) v.findViewById(R.id.mine_fragment_swipe);
        //mSwipeLayout.setOnRefreshListener(this);
        //mSwipeLayout.setViewGroup(mine_swipe_layout);
        //mSwipeLayout.setColorSchemeResources(R.color.zhuti);//设置刷新圆球的循环颜色，若只有一种则只循环单颜色

        pkg_name.setText(versiontv);



        /*判断是否开启权限按钮*/

         String  power =  get_tingxie.a;
        if(power.equals("0")) {
            on_off_button.setChecked(true);
        }else {
            on_off_button.setChecked(false);
        }
        /**/
        on_off_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final HashMap hashMap=new HashMap();
            //0 表示开 1表示关
            if (isChecked==true)
            {
                hashMap.clear();
                hashMap.put("power","0");
                String phone= MsharedPrefrence.Getphonewsd(getContext())[0];
                hashMap.put("phone",phone);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String respones= Post_Pushwork.getStringCha(Constant.editpower,hashMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else {
                hashMap.clear();
                hashMap.put("power","1");
                String phone= MsharedPrefrence.Getphonewsd(getContext())[0];
                hashMap.put("phone",phone);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String respones= Post_Pushwork.getStringCha(Constant.editpower,hashMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
        });
        //发布作业
        pushwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getContext(), MyWorkActivity.class));
            }
        });
        //通知公告
        tongzhigonggao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_notify_main.class);
                intent.putExtra("requestCode", "1");
                intent.putExtra("isReadQuery", "1");
                startActivity(intent);
            }
        });

        //特长辅导
        lay_techangfudao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_techangfudao.class);
                startActivity(intent);
            }
        });

        //优秀作文赏析
        jiaoziyoufngan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                intent.setFlags(1);
                startActivity(intent);
            }
        });

        //好书推荐
        haoshtuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                intent.setFlags(0);
                startActivity(intent);
            }
        });

        //读后感
        duhougan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                intent.setFlags(2);
                startActivity(intent);
            }
        });

        //必背古诗词目录
        bibeigusicimulu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_bibeigushicimulu.class);
                startActivity(intent);
            }
        });
        //拓展古诗词目录
        tuozhangushicimulu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_tuozhangushicimulu.class);
                startActivity(intent);
            }
        });
        //龙虎榜
        longhubang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_ranklist.class);
                startActivity(intent);
            }
        });

        //学习轨迹
        xuexiguiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyHomeWork.class);
                startActivity(intent);
            }
        });

        //个人设置
        gerenshezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), xel_mine_studentmypage.class);
                startActivity(intent);
            }
        });

        learnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_learntrack.class);
                startActivity(intent);
            }
        });

        //课程购买
        kecheng_goumai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BuyCourseActivity.class);
                startActivity(intent);
            }
        });

        //查看&修改 个人详细信息
        userInfo_tag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                if (role[2].equals("0")){
                    intent = new Intent(getActivity(), Xel_mine_teachermypage.class);
                }else {
                    intent = new Intent(getActivity(), xel_mine_studentmypage.class);
                }
                startActivity(intent);
            }
        });

        //帮助与反馈
        feedback_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xel_mine_feedback.class);
                startActivity(intent);
            }
        });
        update_pkg.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                content = new CheckForUpdateService().CheckForUpdateService(getContext());
                /*final SerializableMap myMap = new SerializableMap();
                myMap.setMap(content);//将map数据添加到封装的myMap中
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("map", myMap);
                msg.setData(bundle);
                updateHandler.sendMessage(msg);
                updateHandler.postDelayed(mRunnable, 3000); // 在Handler中执行子线程并延迟3s。*/
               /* Log.d(TAG,"contentMap:"+content.toString());
                if (content != null) {
                    String canUpdate = content.get("canUpdate");
                    if (canUpdate.equals("")) {
                        Toast.makeText(getContext(), "有更新版本", Toast.LENGTH_SHORT).show();

                    } else if(canUpdate.equals("no")){
                        Toast.makeText(getContext(), "没有更新版本", Toast.LENGTH_SHORT).show();

                    }else if (canUpdate.equals("fail")){

                    }
                } else{
                    Toast.makeText(getContext(), "获取更新信息失败,请重试", Toast.LENGTH_SHORT).show();
                }*/


            //Ini_CheckUpdate_Handler();


        }
        });
        stu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActionSheetDialog(getContext())
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("退出登陆", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                ExitApplication.getInstance().Login();
                            }
                        })
                        .addSheetItem("退出易学而乐", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                ExitApplication.getInstance().exit();
                            }
                        })
                        .show();
            }
        });
    }
   /* private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            content = CheckForUpdateUtil.content;
            Log.d(TAG,"contentMap:"+content.toString());
        }
    };*/
    private void Ini_CheckUpdate_Handler() {
        updateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle response = msg.getData();
                SerializableMap serializableMap = (SerializableMap) response.get("map");
                if (serializableMap != null) {
                    String canUpdate = serializableMap.getMap().get("canUpdate");
                    if (canUpdate.equals("yes")) {
                        Toast.makeText(ContextHolder.getContext(), "有更新版本", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ContextHolder.getContext(), "没有更新版本", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ContextHolder.getContext(), "获取更新信息失败,请重试", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    private void determineStateToGetServerInfo() {
        Query_ID = App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext());//获取当前登陆用户的ID
        String[] phonePsw = Getphonewsd(ContextHolder.getContext());
        String[] UserTempInfo = MsharedPrefrence.GetUserInfo(ContextHolder.getContext());
        phoneNum = phonePsw[0];
        Log.d(TAG, "登陆用户是否有有效信息的判断前");
        if (UserTempInfo[1] != null) {
            Log.d(TAG, "进入判断");
            if (phoneNum.equals(UserTempInfo[1])) {
                Ini_Post_userInfo();
                Log.d(TAG, "登陆用户是上次用户并点击过我的，则信息存在，直接设置信息");
            } else {
                Ini_Post_userInfo();
                Log.d(TAG, "登陆用户为新用户未点击过我的，请求信息");
            }
        } else {
            Log.d(TAG, "登陆用户是首次打开客户端，直接请求信息");
            Ini_Post_userInfo();
            //setUserInfo();
        }
    }

    public void initData() {
        Log.d(TAG, "请求的ID" + Query_ID);
        //String[] getFirst = MsharedPrefrence.GetUserisFirstLoading(getContext());//获取当前用户是否第一次登陆的值 0 首次 1 非首次
        String[] phoneWSD = Getphonewsd(ContextHolder.getContext());
        String[] info = MsharedPrefrence.GetUserInfo(ContextHolder.getContext());
        String[] imgDir = MsharedPrefrence.GetUserUserTempImgDir(ContextHolder.getContext());

        sPhone = phoneWSD[0];
        String sMotto = info[2];
        sImgDir = imgDir[1];
        if (imgDir[1] != null) {
            sImgDir = imgDir[1];
        }
        Log.d(TAG, "SP路径" + sImgDir);
        if (sPhone != null) {
            i_phone_inform = info[7];
            Log.d(TAG, "进入phone设置TV" + sPhone);
            i_phone.setText(isNullConvert(i_phone_inform));
        } else {
            //i_phone.setText("");
            Log.d(TAG, "用户无手机号码");
        }
        if (motto != null) {
            i_motto.setText(sMotto);
        } else {
            Log.d(TAG, "用户无签名");
        }
        /*Drawable drawable = this.getResources().getDrawable(R.drawable.mylose);
        user_img.setImageResource(R.drawable.mylose);*/
        /*if (sImgDir != null || sImgDir.equals("")) {

            Bitmap bm = BitmapFactory.decodeFile(sImgDir);
            user_img.setImageBitmap(bm);
        } else {
            

        }*/
        if (getRequestCode() == 200) {
            //Bitmap bm = BitmapFactory.decodeFile(sImgDir);
            Ini_Post_UserImg();
            Log.d(TAG, "图片路径" + cUserInfo.getUser_icon_url());
            /*bitmaphandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Bitmap bitmaps = msg.getData().getParcelable("bitmap");
                    user_img.setImageBitmap(bitmaps);
                }
            }*/
        } else if (getRequestCode() == 100) {
            Bitmap bm = BitmapFactory.decodeFile(sImgDir);
            user_img.setImageBitmap(bm);
        } else {
            Log.d(TAG, "用户无头像");
            MsharedPrefrence.SetUserTempImgDir(getContext(), phoneNum, null);
            user_img.setImageResource(R.drawable.mylose);
        }
    }

    private void Ini_Post_userInfo() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("user_id", Query_ID);
        Log.d(TAG, map.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("@@", "dayinmap" + map);
                String getInfo;
                try {
                    getInfo = Post_learn_rijiyuelei.getStringCha(Constant.post_get_user, map);
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", getInfo);
                    message.setData(bundle);
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Ini_Post_userInfo_Handler();
    }

    public void Ini_Post_userInfo_Handler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String response = msg.getData().getString("response");
                try {
                    JSONObject json = new JSONObject(response);
                    Log.d(TAG, "Json内容" + json);
                    JSONObject data = json.getJSONObject("data");
                    Log.d("@@", "请求信息成功 data" + data);
                    //setUserInfo();//在数据库中查询当前用户以在存储网络数据
                    userss = JSON.parseObject(String.valueOf(data),
                            user_get.class);
                    Log.d("@@", "请求信息成功" + data);
                    if (userss.getUser_icon_url() != null) {
                        postUrl = Constant.selectUserMessage + "/" + userss.getUser_icon_url();
                    }
                    user_icon_url = userss.getUser_icon_url();
                    phone = String.valueOf(userss.getPhone());
                    mobile = String.valueOf(userss.getPhone());
                    motto = userss.getMotto();
                    gender = userss.getGender();
                    qq_number = userss.getQq_number();
                    weixin_number = userss.getWeixin_number();
                    email = userss.getEmail();
                    recipient = userss.getRecipient();
                    address = userss.getAddress();
                    zip_code = String.valueOf(userss.getZip_code());

                    String[] sharePerf = Getphonewsd(ContextHolder.getContext());
                    sPhone = sharePerf[0];
                    List<User> userInfo = Db_UserService.getInstance(ContextHolder.getContext()).loadAllNote();
                    Log.d(TAG, "list.size()===" + userInfo.size());
                    //int state = 100;
                    Log.d(TAG, "PerPhone" + phone);
                    for (User user : userInfo) {
                        if (user.getServer_id().equals(Query_ID)) {
                            Log.d(TAG, "存之前的cUserInfo:" + user);
                            cUserInfo = user;
                            cUserInfo.setUser_icon_url(user_icon_url);
                            cUserInfo.setPhone(phone);
                            cUserInfo.setMobile(mobile);
                            cUserInfo.setMotto(motto);
                            cUserInfo.setGender(gender);
                            cUserInfo.setQq_Number(qq_number);
                            cUserInfo.setWeixin_number(weixin_number);
                            cUserInfo.setEmail(email);
                            cUserInfo.setRecipient(recipient);
                            cUserInfo.setAddress(address);
                            cUserInfo.setZip_code(zip_code);
                            Db_UserService.getInstance(ContextHolder.getContext()).saveNote(cUserInfo);
                            getUserInfo(cUserInfo);
                            Log.d(TAG, "存好的cUserInfo:" + cUserInfo);
                        }
                    }

                    if (!(recipient==null)) {
                        i_phone_inform=recipient;
                        Log.d(TAG,"iphone"+i_phone_inform);
                        statusCode="1";
                        i_phone.setText(i_phone_inform);
                    }else {
                        i_phone_inform="请设置昵称";
                        i_phone.setText(i_phone_inform);
                        Log.d(TAG,"iphone"+"请设置昵称");
                    }
                    if (postUrl != null && !postUrl.equals("")) {
                        Log.d(TAG, "在线程内，并且用户有头像地址");
                        setRequestCode(200);

                    } else {
                        Log.d(TAG, "在线程内，并且用户没有头像地址");
                        MsharedPrefrence.SetUserTempImgDir(ContextHolder.getContext(), phoneNum, "");
                        setRequestCode(0);

                    }
                    MsharedPrefrence.SetUserInfo(ContextHolder.getContext(), postUrl, phone, motto, gender, qq_number, weixin_number, email, recipient, address, zip_code);
                    //Toast.makeText(getContext(), "用户信息同步成功!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "已经将网络请求好的数据保存至本地数据库");
                    initData();

                } catch (JSONException e) {
                    String err = String.valueOf(e);
                    i_phone_inform="未成功同步个人信息";
                    Toast.makeText(getContext(), "未成功同步个人信息,错误" + err, Toast.LENGTH_LONG).show();
                    userInfo_tag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "请下拉刷新后重试", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        };
    }


    private void setUserInfo() {
        //Message msg = new Message();
        List<User> userInfo = Db_UserService.getInstance(ContextHolder.getContext()).loadAllNote();
        Log.d(TAG, "list.size()===" + userInfo.size());
        String[] sharePerf = Getphonewsd(ContextHolder.getContext());
        sPhone = sharePerf[0];
        int state = 100;
        Log.d("@@@", "PerPhone" + sPhone);
        if (userInfo.equals("") || userInfo.isEmpty()) {
            Intent intent = new Intent(ContextHolder.getContext(), xel_user_login.class);
            startActivity(intent);
        } else {
            for (User user : userInfo) {
                if (user.getServer_id().equals(Query_ID)) {
                    cUserInfo = user;
                    state = 200;
                }
            }
        }
        if (state == 200) {
            Log.d(TAG, "GET INFO SUCCESS");
        } else {
            ToastUtil.show(ContextHolder.getContext(), "当前信息获取异常，请重新登录");
        }

    }

    public User getUserInfo(User cUserInfo) {
        xel_mine_fragment.tUserInfo = cUserInfo;
        return tUserInfo;
    }

    private synchronized void Ini_Post_UserImg() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(postUrl); //path图片的网络地址

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                        bitmaphandler.post(new Runnable() {
                            @Override
                            public void run() {
                                user_img.setImageBitmap(bitmap);
                                File appDir = new File(Environment.getExternalStorageDirectory(), "Xel_Resources");
                                if (!appDir.exists()) {
                                    appDir.mkdir();
                                }
                                String fileName = userss.getUser_icon_url();
                                Log.d(TAG, "fileName" + fileName);
                                File file = new File(appDir, fileName);
                                sImgDir = appDir + "/" + fileName + ".jpg";
                                Integer i = sImgDir.length();
                                sImgDir = sImgDir.substring(1, i - 4);
                                Log.d(TAG, "裁剪完后的Uri" + sImgDir);
                                MsharedPrefrence.SetUserTempImgDir(ContextHolder.getContext(), phoneNum, sImgDir);
                                String[] imgDir = MsharedPrefrence.GetUserUserTempImgDir(ContextHolder.getContext());
                                Log.d(TAG, "ImgDir(SP):" + imgDir[1]);
                                Log.d(TAG, "Astro");
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                    fos.flush();
                                    fos.close();
                                    Log.d(TAG, "已将图片保存至本地");
                                    Log.d(TAG, "本地图片路径" + user_icon_url);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //Toast.makeText(getContext(),"未将图片保存至本地",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Looper.prepare();
                        //Toast.makeText(getContext(),"未将图片保存至本地",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "网络图片获取失败");
                    bitmap = null;
                }
            }
        }).start();
    }


    @Override
    public void onStart() {
        super.onStart();
        //initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public Integer getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(Integer requestCode) {
        this.requestCode = requestCode;
    }

    private String isNullConvert(String content) {
        String inValidContent = "无";
        if (content == null) {
            return inValidContent;
        } else {
            return content;
        }
    }
}