package com.neusoft.sample.View.xel_course;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.Gson;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_CaculationTest;
import com.neusoft.sample.Ctrl.Db_XTCSJGService;
import com.neusoft.sample.Ctrl.Db_XTCTJLService;
import com.neusoft.sample.Ctrl.Db_XTDCTMService;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.CalculationTest;
import com.neusoft.sample.GreenDao.UserDoSubjectInfo;
import com.neusoft.sample.GreenDao.XTCSJG;
import com.neusoft.sample.GreenDao.XTCTJL;
import com.neusoft.sample.GreenDao.XTDCTM;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.GetBitmapImage;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.Observer;
import com.neusoft.sample.Model.PostDoexamFull;
import com.neusoft.sample.Model.PostStudentsChampionship;
import com.neusoft.sample.Model.Question;
import com.neusoft.sample.Model.QuestionGroup;
import com.neusoft.sample.Model.Resource;
import com.neusoft.sample.Model.fenshu_panduan;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Mp3.MusicPlayService;
import com.neusoft.sample.util.ContextHolder;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangyujie on 2016/7/7.
 */
public class DoEaxm extends BaseActivity implements Observer, View.OnClickListener {
    TextView tihao;
    TextView status;
    TextView stem;
    Button Answer1;
    Button Answer2;
    Button Answer3;
    Button Answer4;
    Button Anser_fen1, Anser_fen2, Anser_fen3, Anser_fen4;
    Button submit;
    TextView recTime;
    String shi;
    AlwaysMarqueeTextView tv_title;
    ImageButton ima_back;
    String zh_4;
    ImageView imageView;
    MusicPlayService mService;

    ImageButton voice_rijiyuelei;
    /*  10——7  分数的  改动  定义的东西*/
    LinearLayout lin_fenshu_timu;//分数题目的布局样式
    TextView timu1,timu2,timu3,timu4;//四个textview


    /*
    * 分数定义的东西
    * */
    TextView fenzi, fenmu, fenzi1, fenmu1, fenfu, fenqian_tv, fenhou_tv;
    LinearLayout lin_fen, lin_finqian, lin_fenhou, lin_choice_fen, linchoice_nofen;

    List<CalculationTest> list = new ArrayList<>();
    private QuestionGroup qlist;

    public char getSequence() {
        return sequence;
    }

    public void setSequence(char sequence) {
        this.sequence = sequence;
    }

    char sequence;
    private Toolbar toolbar;
    static int recLen = 0;
    static int reclen_ = 0;
    private Timer timer;
    static String nub_zu = "";
    Handler handlerl = null;

    /*
    * 秒表计时器*/
    static int miao = 0;
    static int hao_100 = 0;
    static int fen = 00;
    String uRl;
    String fen_ = "00";
    String miao_ = "00";

    @Override
    public void update(QuestionGroup qlist) {


        Log.d("更新的数据", JSON.toJSONString(qlist) + "pp");
        int is_nump = 0;
        Question question = qlist.getQuestions().get(qlist.getTheCurrentId());

        //题中是否有声音
        Log.d("question_going","");
        if (question.getHasStemSound()) {
            voice_rijiyuelei.setVisibility(View.VISIBLE);
            String voice = question.getSoundName();
            String Item_no = question.getItem_no();
            uRl = GetBitmapImage.Voiceurl(Item_no, voice);

        } else {
            voice_rijiyuelei.setVisibility(View.GONE);
            mService.onDestroy();

        }


        //题中是否有图片

        if (question.isHas_stem_pic()) {
            String url = question.getPicName();
            String Item_no = question.getItem_no();
            final String uRl = GetBitmapImage.Url(url, Item_no);
            VolleyUtil.getQueue(this).start();
            ImageRequest imageRequest = new ImageRequest(
                    uRl,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(response);
                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    imageView.setVisibility(View.GONE);
                    Log.d("onErrorResponse", "" + error);
                }
            });
            VolleyUtil.getQueue(this).add(imageRequest);

        } else {
            Log.d("password", "to remove");
//            imageView.setVisibility(View.INVISIBLE);
        }
        int p = question.getStem().indexOf("/");
        if (p > 0) {
            is_nump = Consant_stringg.is_contain_num(String.valueOf(question.getStem().charAt(p - 1)));
        }
        if (question.getStem().contains("/") && is_nump == 1) {
            String [] fenshu = fenshu_panduan.get_fenshu(question.getStem());//将含有斜线的题目全部进入判断分数的方法过一遍
         /*判断需要 几个  textview  用来动态显示分数公式*/
           if(fenshu[3].equals("3")){
               timu1.setVisibility(View.VISIBLE);
               timu2.setVisibility(View.VISIBLE);
               timu3.setVisibility(View.VISIBLE);
               timu4.setVisibility(View.GONE);
               timu1.setText(fenshu[0]+" ");
               timu2.setText(fenshu[1]+" ");
               timu3.setText(fenshu[2]+" ");
           }else if(fenshu[3].equals("2")){
               timu1.setVisibility(View.VISIBLE);
               timu2.setVisibility(View.VISIBLE);
               timu3.setVisibility(View.GONE);
               timu4.setVisibility(View.GONE);
               timu1.setText(fenshu[0]+" ");
               timu2.setText(fenshu[1]+" ");
           }else {
               timu1.setVisibility(View.VISIBLE);
               timu2.setVisibility(View.VISIBLE);
               timu3.setVisibility(View.VISIBLE);
               timu4.setVisibility(View.VISIBLE);
               timu1.setText(fenshu[0]+" ");
               timu2.setText(fenshu[1]+" ");
               timu3.setText(fenshu[2]+" ");
               timu4.setText(fenshu[3]+" ");
           }


        } else {
            lin_fen.setVisibility(View.GONE);
            stem.setVisibility(View.VISIBLE);
            if(question.getItem_no().substring(0,1).equals("1"))
            {
                stem.setTextSize(35);

            }

            if(question.getItem_no().substring(0,1).equals("5")||question.getItem_no().substring(0,1).equals("8")||question.getItem_no().substring(0,1).equals("7"))
            {
                stem.setTextSize(30);
                stem.getPaint().setFakeBoldText(true);

            }
            if(question.getItem_no().substring(0,1).equals("C")||question.getItem_no().substring(0,1).equals("8"))
            {
                stem.setGravity(View.LAYOUT_DIRECTION_LTR);

            }


            stem.setText(question.getStem());
        }
        status.setText((qlist.getTheCurrentId() + 1) + "/" + qlist.getQuestions().size());
        /*
        * 判断是不是分数*/
        int fen = 0;
        int fenshunub = question.getChoice1().indexOf("/");
        if (fenshunub > 0) {
            fen = Consant_stringg.is_contain_num(String.valueOf(question.getChoice1().charAt(fenshunub - 1)));
        }

        if(question.getItem_no().substring(0,1).equals("1")&&(question.getChoice1().contains("/")||question.getChoice2().contains("/")
                ||question.getChoice3().contains("/")||question.getChoice4().contains("/"))) {
            lin_choice_fen.setVisibility(View.VISIBLE);
            linchoice_nofen.setVisibility(View.GONE);
            if (question.getChoice1().contains("/") ) {
                String[] q1 = Constant.getfen_choice(question.getChoice1());
                String fenzi_cho = q1[0];
                String fenmu_cho = q1[1];
                Anser_fen1.setText(fenzi_cho + "\n" + "—" + "\n" + fenmu_cho);
            } else {

                Anser_fen1.setText(question.getChoice1());
            }
            if (question.getChoice2().contains("/")) {
                String[] q1 = Constant.getfen_choice(question.getChoice2());
                String fenzi_cho = q1[0];
                String fenmu_cho = q1[1];
                Anser_fen2.setText(fenzi_cho + "\n" + "—" + "\n" + fenmu_cho);
            } else {
                Anser_fen2.setText(question.getChoice2());
            }
            if (question.getChoice3().contains("/")) {
                String[] q1 = Constant.getfen_choice(question.getChoice3());
                String fenzi_cho = q1[0];
                String fenmu_cho = q1[1];
                Anser_fen3.setText(fenzi_cho + "\n" + "—" + "\n" + fenmu_cho);
            } else {
                Anser_fen3.setText(question.getChoice3());
            }
            if (question.getChoice4().contains("/")) {
                String[] q1 = Constant.getfen_choice(question.getChoice4());
                String fenzi_cho = q1[0];
                String fenmu_cho = q1[1];
                Anser_fen4.setText(fenzi_cho + "\n" + "—" + "\n" + fenmu_cho);
            } else {
                Anser_fen4.setText(question.getChoice4());
            }
        }else if (question.getItem_no().substring(0,1).equals("1")&&!(question.getChoice1().contains("/")||question.getChoice2().contains("/")
                ||question.getChoice3().contains("/")||question.getChoice4().contains("/")))
        {
            lin_choice_fen.setVisibility(View.VISIBLE);
            linchoice_nofen.setVisibility(View.GONE);
            Anser_fen1.setText(question.getChoice1());
            Anser_fen2.setText(question.getChoice2());
            Anser_fen3.setText(question.getChoice3());
            Anser_fen4.setText(question.getChoice4());
        }
        else {
            lin_choice_fen.setVisibility(View.GONE);
            linchoice_nofen.setVisibility(View.VISIBLE);
            Answer1.setText(question.getChoice1());
            Answer2.setText(question.getChoice2());
            Answer3.setText(question.getChoice3());
            Answer4.setText(question.getChoice4());

        }


    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    reclen_++;
                    recLen++;
                    if (recLen == 10) {
                        hao_100++;
                        recLen = 0;
                    }
                    if (hao_100 == 10) {
                        miao++;
                        hao_100 = 0;
                    }
                    if (miao == 60) {
                        fen++;
                        miao = 0;
                    }
                    if (fen < 10) {
                        fen_ = "0" + fen;
                    } else {
                        fen_ = "" + fen;
                    }
                    if (miao < 10) {
                        miao_ = "0" + miao;
                    } else {
                        miao_ = "" + miao;
                    }
                    recTime.setText("" + fen_ + "：" + miao_ + "：" + hao_100 + recLen + "");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_kecheng_kousuantika);
        Intent intent = getIntent();
        nub_zu = intent.getStringExtra("nub_zu");
        zh_4 = nub_zu.substring(nub_zu.length() - 1, nub_zu.length());
        String zuhao = nub_zu.substring(nub_zu.length() - 2, nub_zu.length());
        String flag = intent.getStringExtra("error_q");
        if (flag != null) {
            Log.d("falg", flag);
        }
        initview();



        System.out.println("组号" + nub_zu);
        tihao = (TextView) findViewById(R.id.tihao);
        tihao.setText(zuhao);
        setTitle(QuestionnumberSwitchTitle.Gettitle(nub_zu));
        qlist = Resource.QUESTION_GROUP;
        qlist.addObserver(this);

        if ("1".equals(flag)) {
            //重做flag
            List<XTCTJL> xtctjlList = Db_XTCTJLService.getInstance(this).loadAllNote();
            List<CalculationTest> calculationTestList = Db_CaculationTest.getInstance(this).loadAllNote();
            for (XTCTJL xtctjl : xtctjlList) {
                for (CalculationTest calculationTest : calculationTestList) {
                    if (xtctjl.getXtdctm_id() != null && xtctjl.getXtdctm_id().equals(calculationTest.getItemNo())) {

                        list.add(calculationTest);
                    }
                }
            }
            //把之前的数据清除 错题
            Db_XTDCTMService.getInstance(ContextHolder.getContext()).deleteAllNote();
            Db_XTCTJLService.getInstance(ContextHolder.getContext()).deleteAllNote();
            Db_XTCSJGService.getInstance(ContextHolder.getContext()).deleteAllNote();
        } else {
            list = Db_CaculationTest.getInstance(this).loadAllNote();
        }
        Log.d("list数据源", JSON.toJSONString(list));
        Resource.CONTROLLER.load(nub_zu.substring(0, 11), list);
        timer = new Timer(true);
        timer.schedule(task, 0, 10);
    }

    public void initview() {
        //图片的加载
        imageView = (ImageView) findViewById(R.id.url_image);
        voice_rijiyuelei = (ImageButton) findViewById(R.id.voice_rijiyuelei);
       //加载网络音频
        App app= (App) getApplication();
        mService=app.getmService();
        status = (TextView) findViewById(R.id.status);
        stem = (TextView) findViewById(R.id.stem);
        Answer1 = (Button) findViewById(R.id.Answer1);
        Answer2 = (Button) findViewById(R.id.Answer2);
        Answer3 = (Button) findViewById(R.id.Answer3);
        Answer4 = (Button) findViewById(R.id.Answer4);
        tv_title = (AlwaysMarqueeTextView) findViewById(R.id.tv_title);

        /*分数的   布局定义  初始化   */
        lin_fen = (LinearLayout) findViewById(R.id.fenshu);
        timu1 = (TextView) findViewById(R.id.timu_tv_1);
        timu2 = (TextView) findViewById(R.id.timu_tv_2);
        timu3 = (TextView) findViewById(R.id.timu_tv_3);
        timu4 = (TextView) findViewById(R.id.timu_tv_4);


         /*分数的   布局定义  初始化   */
        /*
        * 分数初始化控件*/
        Anser_fen1 = (Button) findViewById(R.id.anser1_);
        Anser_fen2 = (Button) findViewById(R.id.anser2_);
        Anser_fen3 = (Button) findViewById(R.id.anser3_);
        Anser_fen4 = (Button) findViewById(R.id.anser4_);
        lin_choice_fen = (LinearLayout) findViewById(R.id.lin_fenshu);
        linchoice_nofen = (LinearLayout) findViewById(R.id.lin_nofen);


        tv_title.setText(QuestionnumberSwitchTitle.Gettitle(nub_zu));

        ima_back = (ImageButton) findViewById(R.id.img_back);
//        submit = (Button) findViewById(R.id.submit);
        recTime = (TextView) findViewById(R.id.rc_time);
//        submit.setOnClickListener(this);
        stem.setOnClickListener(this);
        Answer1.setOnClickListener(this);
        Answer2.setOnClickListener(this);
        Answer3.setOnClickListener(this);
        Answer4.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        ima_back.setOnClickListener(this);
        /*
        * 分数选项*/
        Anser_fen1.setOnClickListener(this);
        Anser_fen2.setOnClickListener(this);
        Anser_fen3.setOnClickListener(this);
        Anser_fen4.setOnClickListener(this);


        voice_rijiyuelei.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Answer1:
                Resource.CONTROLLER.submit("A");

                break;
            case R.id.Answer2:
                Resource.CONTROLLER.submit("B");

                break;
            case R.id.Answer3:
                Resource.CONTROLLER.submit("C");

                break;
            case R.id.Answer4:
                Resource.CONTROLLER.submit("D");

                break;
            case R.id.anser1_:

                Resource.CONTROLLER.submit("A");
                break;
            case R.id.anser2_:


                Resource.CONTROLLER.submit("B");
                break;
            case R.id.anser3_:

                Resource.CONTROLLER.submit("C");
                break;
            case R.id.anser4_:

                Resource.CONTROLLER.submit("D");
                break;

            case R.id.voice_rijiyuelei:
                voice_rijiyuelei.setBackgroundColor(getResources().getColor(R.color.background));
                mService.playMusics(uRl);
                break;
            case R.id.leftbutton:
                recLen = 0;
                reclen_ = 0;
                miao_ = "00";
                miao = 0;
                fen = 0;
                fen_ = "00";
                hao_100 = 0;
                task.cancel();
                mService.onDestroy();
                finish();
                break;
            case R.id.img_back:
                recLen = 0;
                reclen_ = 0;
                miao_ = "00";
                miao = 0;
                fen = 0;
                fen_ = "00";
                hao_100 = 0;
                mService.onDestroy();
                task.cancel();
                finish();
                break;
            default:
                break;
        }
    }

    /*
    * 获取章节组列表
    * */
    // TODO: 16/6/26  统计问题结果 保存网络 本地备份
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String mapstring) {
        timer.cancel();
        task.cancel();
        timer = null;
        task = null;
        /**
         * 把整租测试表存到数据库中
         * 把错题列表存到数据库中
         */
        //***********************************************************************************************************
        HashMap<String, Object> map = JSON.parseObject(mapstring, HashMap.class);
        XTCSJG xtcsjgList = JSON.parseObject(map.get("xtcsjg").toString(), XTCSJG.class);


        xtcsjgList.setDuration((reclen_ * 10));
        recLen = 0;
        reclen_ = 0;
        Db_XTCSJGService.getInstance(this).saveNote(xtcsjgList);
        //第一次做
        if (!"doagain".equals(MsharedPrefrence.GetisDoGain(ContextHolder.getContext()))) {
            if (100 - xtcsjgList.getScore() == 0) {
             //第一次的100分学生
                Log.d("角色的身份：","--"+App.GetSharePrefrence_role(ContextHolder.getContext()));
                if (App.GetSharePrefrence_role(ContextHolder.getContext()).equals("1"))//我是学生
                {
                    System.out.print("我是满分学生doeaxm");
                    List<XTCSJG> xtcsjgList1 = Db_XTCSJGService.getInstance(this).loadAllNote();
                    HashMap<String, String> hmap = new HashMap<>();
                    String JiaocaiSBNo = xtcsjgList1.get(0).getTest_group_number().substring(0, 5);
                    hmap.put("JiaocaiSBNo", JiaocaiSBNo);
                    String CSJG = JSON.toJSONString(xtcsjgList1.get(0));
                    hmap.put("CSJG", CSJG);
                    System.out.println("这个是学生的满分--首次做");
                    Log.d("上传后台数据：","："+JSON.toJSONString(hmap));
                    PostStudentsChampionship.PostLink(ContextHolder.getContext(), Constant.PostStudentsChampionship, hmap);
                }
                String a = new Gson().toJson(xtcsjgList);
                Intent intent = new Intent(this, ActivityGrade_full_Result.class);
                intent.putExtra("info", a);
                intent.putExtra("nub_zu", nub_zu);
                startActivity(intent);
                finish();
            } else {
                //不是100分 第一次
                if (App.GetSharePrefrence_role(ContextHolder.getContext()).equals("1")) {
                    List<XTDCTM> errorxtdctmList = JSON.parseArray(map.get("errorxtdctmList").toString(), XTDCTM.class);
                    List<XTCTJL> errorxtctjlList = JSON.parseArray(map.get("errorxtctjlList").toString(), XTCTJL.class);
                    List<XTDCTM> xtdctmList2 = errorxtdctmList;
                    List<XTCTJL> xtctjlList2 = errorxtctjlList;

                    List<XTCSJG> xtcsjgList1 = Db_XTCSJGService.getInstance(this).loadAllNote();
                    List<UserDoSubjectInfo> userDoSubjectInfoList = new ArrayList<>();
                    for (int i = 0; i < xtdctmList2.size(); i++) {
                        UserDoSubjectInfo userDoSubjectInfo = new UserDoSubjectInfo();
                        userDoSubjectInfo.setXtdctm_id(xtdctmList2.get(i).getXtdctm_id());
                        userDoSubjectInfo.setExamination_number(xtdctmList2.get(i).getExamination_number());
                        userDoSubjectInfo.setTest_group_number(xtdctmList2.get(i).getTest_group_number());
                        userDoSubjectInfo.setIsmastered(xtdctmList2.get(i).getIsmastered());
                        userDoSubjectInfo.setUser_id(xtdctmList2.get(i).getUser_id());
                        userDoSubjectInfo.setXtcsjl_id(xtctjlList2.get(i).getXtcsjl_id());
                        userDoSubjectInfo.setAnswer(xtctjlList2.get(i).getAnswer());
                        userDoSubjectInfo.setJl_datetime(xtctjlList2.get(i).getJl_datetime());
                        userDoSubjectInfoList.add(userDoSubjectInfo);
                    }
                    HashMap<String, String> hmap = new HashMap<>();
                    String JiaocaiSBNo = xtcsjgList1.get(0).getTest_group_number().substring(0, 5);
                    hmap.put("JiaocaiSBNo", JiaocaiSBNo);
                    String CSJG = JSON.toJSONString(xtcsjgList1.get(0));
                    hmap.put("CSJG", CSJG);
                    hmap.put("DCTMCTJL", JSON.toJSONString(userDoSubjectInfoList));
                    Log.d("上传后台数据：","："+JSON.toJSONString(userDoSubjectInfoList));
                    PostDoexamFull.PostLink(ContextHolder.getContext(), Constant.post_Error_subject, hmap);
                }
                String a = new Gson().toJson(xtcsjgList);
                Intent intent = new Intent(this, ActivityGradeResult.class);
                intent.putExtra("info", a);
                startActivity(intent);
                finish();
            }
        }
        //再次做题
        else {
            String a = new Gson().toJson(xtcsjgList);
            Intent intent = new Intent(this, ActivityGradeResult.class);
            intent.putExtra("info", a);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        recLen = 0;
        reclen_ = 0;
        miao_ = "00";
        miao = 0;
        fen = 0;
        fen_ = "00";
        hao_100 = 0;
        list.clear();

        super.onStop();
        this.finish();
    }

    int flag = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            flag++;

            if (flag == 1) {
                Toast.makeText(this, "再次点击退出做题界面！", Toast.LENGTH_SHORT).show();
            }
            if (flag == 2) {
                recLen = 0;
                reclen_ = 0;
                miao_ = "00";
                miao = 0;
                fen = 0;
                fen_ = "00";
                hao_100 = 0;
                task.cancel();
                mService.onDestroy();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
