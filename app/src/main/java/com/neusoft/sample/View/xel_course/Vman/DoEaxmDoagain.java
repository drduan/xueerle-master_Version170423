package com.neusoft.sample.View.xel_course.Vman;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
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
import com.neusoft.sample.Ctrl.Db_XTCSJGService;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.XTCSJG;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.DoagainErrorSubject.ObserverDoagain;
import com.neusoft.sample.Model.DoagainErrorSubject.QuestionGroupAgain;
import com.neusoft.sample.Model.ErrorSubject_two;
import com.neusoft.sample.Model.GetBitmapImage;
import com.neusoft.sample.Model.Question;
import com.neusoft.sample.Model.Resource;
import com.neusoft.sample.Model.fenshu_panduan;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Mp3.MusicPlayService;
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
 * Created by wangyujie on 2016/7/27.
 */

public class DoEaxmDoagain extends BaseActivity implements ObserverDoagain, View.OnClickListener {

    TextView status;
    TextView stem;
    TextView tihao;
    Button Answer1;
    Button Answer2;
    Button Answer3;
    Button Answer4;
    Button submit;
    TextView recTime;
    Button Anser_fen1, Anser_fen2, Anser_fen3, Anser_fen4;

    AlwaysMarqueeTextView tv_title;
    ImageButton ima_back;
    String zh_4;
    /*
       * 分数定义的东西
       * */
    TextView fenzi, fenmu, fenzi1, fenmu1, fenfu, fenqian_tv, fenhou_tv;
    LinearLayout lin_fen, lin_finqian, lin_fenhou, lin_choice_fen, linchoice_nofen;

    static int recLen = 0;
    static int reclen_ = 0;
    /*
    * 秒表计时器*/
    static int miao = 0;
    static int hao_100 = 0;
    static int fen = 00;

    String fen_ = "00";
    String miao_="00";

    ImageButton voice_rijiyuelei;
    String uRl;
    TextView timu1,timu2,timu3,timu4;//四个textview
    MusicPlayService mService;

    private QuestionGroupAgain qlist;

    public char getSequence() {
        return sequence;
    }

    public void setSequence(char sequence) {
        this.sequence = sequence;
    }

    char sequence;
    ImageView imageView;
    private Toolbar toolbar;

    private Timer timer;
   static String nub_zu="";
    List<ErrorSubject_two> errorSubject_twoListe;


    @Override
    public void update(QuestionGroupAgain qlist) {


        Log.d("更新的数据", JSON.toJSONString(qlist)+"pp");
        int is_nump=0;
        Question question = qlist.getQuestions().get(qlist.getTheCurrentId());
        if (question.getHasStemSound()!=null) {
            if (question.getHasStemSound()) {
                voice_rijiyuelei.setVisibility(View.VISIBLE);
                String voice = question.getSoundName();
                String Item_no = question.getItem_no();
                uRl = GetBitmapImage.Voiceurl(Item_no, voice);


            } else {
                voice_rijiyuelei.setVisibility(View.GONE);
                mService.onDestroy();

            }
        }


        //题中是否有图片

        if (question.isHas_stem_pic())
        {


//            imageView.setVisibility(View.VISIBLE);
            String url=question.getPicName();
            String Item_no=question.getItem_no();
            final String uRl= GetBitmapImage.Url(url,Item_no);

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
                    Log.d("onErrorResponse",""+error);
                }
            });
            VolleyUtil.getQueue(this).add(imageRequest);

        }
        else {
            Log.d("password","to remove");
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

            Log.d("地址linerlayout",lin_fen+"1");
            lin_fen.setVisibility(View.GONE);
            if(question.getItem_no().substring(0,1).equals("C"))
            {
                stem.setGravity(View.LAYOUT_DIRECTION_LTR);

            }
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
            stem.setVisibility(View.VISIBLE);
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
                    if(recLen==10){
                        hao_100 ++;
                        recLen = 0;
                    }
                    if(hao_100 == 10){
                        miao++;
                        hao_100 = 0;
                    }
                    if(miao == 60){
                        fen ++;
                        miao = 0;
                    }
                    if(fen<10){
                        fen_ = "0"+fen;
                    } else{
                        fen_ = ""+fen;
                    }
                    if(miao<10){
                        miao_ = "0"+miao;
                    }else {
                        miao_= ""+miao;
                    }
                    recTime.setText("" + fen_ + "：" + miao_+ "：" + hao_100+recLen+ "");
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
        setContentView(R.layout.xel_error_kousuantika);

        Intent intent=getIntent();
        nub_zu=intent.getStringExtra("nub_zu");
        initview();
        errorSubject_twoListe=intent.getParcelableArrayListExtra("response");

//        QuestionnumberSwitchTitle.Gettitle(nub_zu)
        System.out.println("组号"+nub_zu);
        tv_title.setText(QuestionnumberSwitchTitle.GetErrortitle(nub_zu));
        tihao.setText("第"+nub_zu.substring(7,9)+"节");
        if (nub_zu.substring(0,1).equals("C")||nub_zu.substring(0,1).equals("5")||nub_zu.substring(0,1).equals("8"))
        {
            tihao.setText("第"+nub_zu.substring(5,7)+"章");
        }
        qlist = Resource.QUESTION_GROUPDoAgain;
        qlist.addObserver(this);
        // TODO 这里的list就是我们要的数据源
        Resource.CONTROLLER.loadDoagain(errorSubject_twoListe);
        timer = new Timer(true);
        timer.schedule(task, 0, 10);
    }

    public void initview() {
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
        tihao= (TextView) findViewById(R.id.tihao);
        imageView= (ImageView) findViewById(R.id.url_image);
           /*分数的   布局定义  初始化   */
        lin_fen = (LinearLayout) findViewById(R.id.error_fenshu);
        timu1 = (TextView) findViewById(R.id.timu_tv_1);
        timu2 = (TextView) findViewById(R.id.timu_tv_2);
        timu3 = (TextView) findViewById(R.id.timu_tv_3);
        timu4 = (TextView) findViewById(R.id.timu_tv_4);

        /*
        * 分数初始化控件*/
        Anser_fen1 = (Button) findViewById(R.id.anser1_);
        Anser_fen2 = (Button) findViewById(R.id.anser2_);
        Anser_fen3 = (Button) findViewById(R.id.anser3_);
        Anser_fen4 = (Button) findViewById(R.id.anser4_);
        lin_choice_fen = (LinearLayout) findViewById(R.id.lin_fenshu);
        linchoice_nofen = (LinearLayout) findViewById(R.id.lin_nofen);
        fenzi = (TextView) findViewById(R.id.fenzi);
        fenmu = (TextView) findViewById(R.id.fenmu);
        fenzi1 = (TextView) findViewById(R.id.fenzi1);
        fenmu1 = (TextView) findViewById(R.id.fenmu1);
        fenfu = (TextView) findViewById(R.id.fen_fu);
        lin_finqian = (LinearLayout) findViewById(R.id.lin_fen_qian);
        lin_fenhou = (LinearLayout) findViewById(R.id.lin_fen_hou);
        fenqian_tv = (TextView) findViewById(R.id.tv_fen_qian);
        fenhou_tv = (TextView) findViewById(R.id.tv_fen_hou);


        if (QuestionnumberSwitchTitle.Gettitle(nub_zu).contains("背单词")) {
            tv_title.setText(QuestionnumberSwitchTitle.Gettitle(nub_zu));
        } else {
            tv_title.setText(QuestionnumberSwitchTitle.Gettitle(nub_zu) + ">第" + zh_4 + "组");
        }
        //+zh_1+">第"+zh_2+"章>第"+zh_3+"节>第"+zh_4+"组"
        Log.d("@@@", "章节组" + tv_title);
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
                Resource.CONTROLLER.submitDoagain("A");
                break;
            case R.id.Answer2:
                Resource.CONTROLLER.submitDoagain("B");
                break;
            case R.id.Answer3:
                Resource.CONTROLLER.submitDoagain("C");
                break;
            case R.id.Answer4:
                Resource.CONTROLLER.submitDoagain("D");
                break;
            case R.id.anser1_:


                Resource.CONTROLLER.submitDoagain("A");
                break;
            case R.id.anser2_:


                Resource.CONTROLLER.submitDoagain("B");
                break;
            case R.id.anser3_:

                Resource.CONTROLLER.submitDoagain("C");
                break;
            case R.id.anser4_:

                Resource.CONTROLLER.submitDoagain("D");
                break;
            case R.id.voice_rijiyuelei:
                voice_rijiyuelei.setBackgroundColor(getResources().getColor(R.color.background));

                    mService.playMusics(uRl);

                break;

            case R.id.leftbutton:
                recLen = 0;
                timer.cancel();
                task.cancel();
                timer = null;
                task = null;
                timer.cancel();
                task.cancel();
                mService.onDestroy();
                finish();
                break;
            case R.id.img_back:
                recLen = 0;
                reclen_ = 0;
                miao_="00";
                miao = 0;
                fen = 0;
                fen_="00";
                hao_100 = 0;
                task.cancel();
                mService.onDestroy();
                finish();
                break;
            default:
                break;
        }
    }

    // TODO: 16/6/26  统计问题结果 保存网络 本地备份


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(  String mapstring) {
            timer.cancel();
            task.cancel();
              timer=null;
            task = null;
        /**
         * 把整租测试表存到数据库中
         * 把错题列表存到数据库中
         */
        //***********************************************************************************************************
       HashMap<String,Object> map= JSON.parseObject(mapstring, HashMap.class);
        Log.d("maps", new Gson().toJson(map).toString());
        XTCSJG xtcsjgList= JSON.parseObject(map.get("xtcsjg").toString(), XTCSJG.class);
        xtcsjgList.setDuration((reclen_*10));
        recLen = 0;
        reclen_ = 0;

        Db_XTCSJGService.getInstance(this).saveNote(xtcsjgList);
        Log.d("maps0", xtcsjgList.getScore().toString());

                String a = new Gson().toJson(xtcsjgList);
                Log.d("DoEaxm",a) ;
                Intent intent = new Intent(this, ActivityGradeResultDoagain.class);
                intent.putExtra("info", a);
                intent.putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) errorSubject_twoListe);
                startActivity(intent);
                finish();




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
        miao_="00";
        miao = 0;
        fen = 0;
        fen_="00";
        hao_100 = 0;

        super.onStop();
        this.finish();
    }
    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Object mHelperUtils;
                Toast.makeText(this, "如果从当前返回，则不会保留数据！\n再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                recLen = 0;
                reclen_ = 0;
                miao_="00";
                miao = 0;
                fen = 0;
                fen_="00";
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
