package com.neusoft.sample.View.AoSaiTest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.gson.Gson;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_XTCSJGService;
import com.neusoft.sample.Ctrl.Db_XTCTJLService;
import com.neusoft.sample.Ctrl.Db_XTDCTMService;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.UserDoSubjectInfo;
import com.neusoft.sample.GreenDao.XTCSJG;
import com.neusoft.sample.GreenDao.XTCTJL;
import com.neusoft.sample.GreenDao.XTDCTM;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.GetBitmapImage;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.PostDoexamFull;
import com.neusoft.sample.Model.PostStudentsChampionship;
import com.neusoft.sample.Model.aosai_zu_Entity;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.util.ContextHolder;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;
import com.shockwave.pdfium.PdfDocument;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangyujie on 2016/7/7.
 */
public class Aosai_DoEaxm extends BaseActivity implements Aosai_Observer, View.OnClickListener ,OnPageChangeListener, OnLoadCompleteListener,OnErrorListener {
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
    TextView tv_title;
    ImageButton ima_back;
    String zh_4;
    ImageView imageView;

    //pdf逻辑布局内容区
    private LinearLayout aosai_question_linearLayout;
    private LinearLayout aosai_math_pdf;
    private PDFView pdfView;
    private Button question1, question2, question3, question4;

    OkHttpClient mOkHttpClient = new OkHttpClient();
    String pdfFileName;
    private static final String TAG = Aosai_DoEaxm.class.getSimpleName();
    Integer pageNumber = 0;


    List<aosai_zu_Entity> list = new ArrayList<>();
    List<aosai_zu_Entity> doagain_list;
    /*
    * 分数定义的东西
    * */
    TextView fenzi, fenmu, fenzi1, fenmu1, fenfu, fenqian_tv, fenhou_tv;
    LinearLayout lin_fen, lin_finqian, lin_fenhou, lin_choice_fen, linchoice_nofen;

    private Aosai_QuestionGroup qlist;

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

    String fen_ = "00";
    String miao_ = "00";

    @Override
    public void update(Aosai_QuestionGroup qlist) {


        Log.d("更新的数据", JSON.toJSONString(qlist) + "pp");
        int is_nump = 0;
        Aosai_Question question = qlist.getQuestions().get(qlist.getTheCurrentId());

        //题中是否有图片
        //题中是否有图片

        if (question.getIsStemPic()) {


//            imageView.setVisibility(View.VISIBLE);
            String url = question.getStemPicName();
            String Item_no = question.getItemNo();


            Log.d("图片名和题号", url + "i" + question.getItemNo());
            final String uRl = GetBitmapImage.Url(url, Item_no);

            VolleyUtil.getQueue(this).start();
            ImageRequest imageRequest = new ImageRequest(
                    uRl,
                    new com.android.volley.Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(response);
                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new com.android.volley.Response.ErrorListener() {
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


        if (question.isUsedStemPDF()) {
            aosai_question_linearLayout.setVisibility(View.GONE);
            aosai_math_pdf.setVisibility(View.VISIBLE);
            //// TODO: 2016/9/8  添加加载pdf的和加载其abcd的添加

            final String aosainub="11"+question.getItemNo()+"1";
            final String jiaocaimulu=aosainub.substring(2,7);
            final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.xel.www/";
            final Handler handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.getData().getInt("passkey")==1) {
                        if (new File(SDPath +  aosainub+".pdf").exists()) {

                            pdfView.fromFile(new File(SDPath + aosainub+".pdf"))
                                    .enableSwipe(true)
                                    .swipeHorizontal(false)
                                    .enableDoubletap(true)
                                    .defaultPage(0)
                                    .onLoad(Aosai_DoEaxm.this)
                                    .onPageChange(Aosai_DoEaxm.this)
                                    .enableAnnotationRendering(false)
                                    .password(null)
                                    .scrollHandle(null)
                                    .load();
                        }
                    }
                }
            };

            File file = new File(SDPath);
            if (!file.exists()){
                file.mkdirs();
            }

            String urlll ="http://122.156.218.189:8080/Xrl/resources/xrlRes/1/11/"+jiaocaimulu+"/"+aosainub.substring(7,9)+"/"+aosainub+".pdf";
            Log.d("urlll",urlll);//1134011010101012
            Request request = new Request.Builder().url("http://122.156.218.189:8080/Xrl/resources/xrlRes/1/11/"+jiaocaimulu+"/"+aosainub.substring(7,9)+"/"+aosainub+".pdf").build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("h_bl", "onFailure");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;

                    Log.d("SDpath",SDPath+"s");
                    try {
                        is = response.body().byteStream();
                        File file = new File(SDPath, aosainub+".pdf");
                        file.createNewFile();
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        fos.flush();
                        Log.d("h_bl", "文件下载成功");
                        Message message=new Message();
                        Bundle bundle=new Bundle();
                        bundle.putInt("passkey",1);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        Log.d("h_bl", "文件下载失败");
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                        }
                    }



                }

            });



        } else {
            aosai_question_linearLayout.setVisibility(View.VISIBLE);
            aosai_math_pdf.setVisibility(View.GONE);
        }


        int p = question.getStem().indexOf("/");
        if (p > 0) {
            is_nump = Consant_stringg.is_contain_num(String.valueOf(question.getStem().charAt(p - 1)));
        }
        if (question.getStem().contains("/") && is_nump == 1) {
            lin_fen.setVisibility(View.VISIBLE);
            stem.setVisibility(View.GONE);
            String[] stem_1 = Constant.getfen(question.getStem());
            String qianis = stem_1[0];
            String houis = stem_1[1];
            String fen_fu_ = stem_1[4];
            fenfu.setText(fen_fu_);//加减号
            if (qianis.equals("1")) {
                // 前边的是分数
                fenqian_tv.setVisibility(View.GONE);
                lin_finqian.setVisibility(View.VISIBLE);
                String fenzi_ = stem_1[5];
                String fenmu_ = stem_1[6];
                fenzi.setText(fenzi_);
                fenmu.setText(fenmu_);
            } else {
                //前边的不是分数
                fenqian_tv.setVisibility(View.VISIBLE);
                lin_finqian.setVisibility(View.GONE);
                String fenqian__ = stem_1[2];
                fenqian_tv.setText(fenqian__);
            }
            if (houis.equals("1")) {
                //后边的是分数
                fenhou_tv.setVisibility(View.GONE);
                lin_fenhou.setVisibility(View.VISIBLE);
                String fenzi1_ = stem_1[7];
                String fenmu1_ = stem_1[8];
                fenzi1.setText(fenzi1_);
                fenmu1.setText(fenmu1_);
            } else {
                //后边的不是分数
                fenhou_tv.setVisibility(View.VISIBLE);
                lin_fenhou.setVisibility(View.GONE);
                String fenhou__ = stem_1[3];
                fenhou_tv.setText(fenhou__);
            }

        } else {
            lin_fen.setVisibility(View.GONE);
            stem.setVisibility(View.VISIBLE);
            stem.setText(question.getStem());
        }
        status.setText((qlist.getTheCurrentId() + 1) + "/" + qlist.getQuestions().size());
        /*
        * 判断是不是分数*/
        int fen = 0;
        int fenshunub = question.getChoiceA().indexOf("/");
        if (fenshunub > 0) {
            fen = Consant_stringg.is_contain_num(String.valueOf(question.getChoiceA().charAt(fenshunub - 1)));
        }

        if (question.getChoiceA().contains("/") && fen == 1) {
            lin_choice_fen.setVisibility(View.VISIBLE);
            linchoice_nofen.setVisibility(View.GONE);
            String[] q1 = Constant.getfen_choice(question.getChoiceA());
            String fenzi_cho = q1[0];
            String fenmu_cho = q1[1];
            Anser_fen1.setText(fenzi_cho + "\n" + "—" + "\n" + fenmu_cho);
        } else {
            lin_choice_fen.setVisibility(View.GONE);
            linchoice_nofen.setVisibility(View.VISIBLE);
            Answer1.setText(question.getChoiceA());
        }
        if (question.getChoiceB().contains("/")) {
            String[] q1 = Constant.getfen_choice(question.getChoiceB());
            String fenzi_cho = q1[0];
            String fenmu_cho = q1[1];
            Anser_fen2.setText(fenzi_cho + "\n" + "—" + "\n" + fenmu_cho);
        } else {
            Answer2.setText(question.getChoiceB());
        }
        if (question.getChoiceC().contains("/")) {
            String[] q1 = Constant.getfen_choice(question.getChoiceC());
            String fenzi_cho = q1[0];
            String fenmu_cho = q1[1];
            Anser_fen3.setText(fenzi_cho + "\n" + "—" + "\n" + fenmu_cho);
        } else {
            Answer3.setText(question.getChoiceC());
        }
        if (question.getChoiceD().contains("/")) {
            String[] q1 = Constant.getfen_choice(question.getChoiceD());
            String fenzi_cho = q1[0];
            String fenmu_cho = q1[1];
            Anser_fen4.setText(fenzi_cho + "\n" + "—" + "\n" + fenmu_cho);
        } else {
            Answer4.setText(question.getChoiceD());
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
        setContentView(R.layout.xel_aosai_doeaxm);
        Intent intent = getIntent();
        nub_zu = intent.getStringExtra("nub_zu");

        list = intent.getParcelableArrayListExtra("Entity");


        doagain_list = intent.getParcelableArrayListExtra("list");
        Log.d("wrong_report", "2" + JSON.toJSONString(doagain_list));

        Log.d("Entity", "list" + JSON.toJSONString(list));

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
        setTitle(QuestionnumberSwitchTitle.GetAosaititle(nub_zu));
        qlist = Aosai_Resource.QUESTION_GROUP;
        qlist.addObserver(this);
        if ("1".equals(flag)) {
            list = new ArrayList<>();

            //重做flag
            List<XTCTJL> xtctjlList = Db_XTCTJLService.getInstance(this).loadAllNote();

            for (XTCTJL xtctjl : xtctjlList) {
                for (aosai_zu_Entity calculationTest : doagain_list) {
                    if (xtctjl.getXtdctm_id() != null && xtctjl.getXtdctm_id().equals(calculationTest.getItemNo())) {
                        Log.d("calculationTest", "i" + calculationTest);
                        list.add(calculationTest);
                    }
                }
            }
            //把之前的数据清除 错题
            Db_XTDCTMService.getInstance(ContextHolder.getContext()).deleteAllNote();
            Db_XTCTJLService.getInstance(ContextHolder.getContext()).deleteAllNote();
            Db_XTCSJGService.getInstance(ContextHolder.getContext()).deleteAllNote();
        }
        Log.d("list数据源", JSON.toJSONString(list));
        Aosai_Resource.CONTROLLER.load(nub_zu.substring(0, 11), list);
        timer = new Timer(true);
        timer.schedule(task, 0, 10);
    }

    public void initview() {
        //图片的加载
        imageView = (ImageView) findViewById(R.id.url_image);
        status = (TextView) findViewById(R.id.status);
        stem = (TextView) findViewById(R.id.stem);
        Answer1 = (Button) findViewById(R.id.Answer1);
        Answer2 = (Button) findViewById(R.id.Answer2);
        Answer3 = (Button) findViewById(R.id.Answer3);
        Answer4 = (Button) findViewById(R.id.Answer4);
        tv_title = (TextView) findViewById(R.id.tv_title);
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

        pdfView = (PDFView) findViewById(R.id.pdfView);
        question1 = (Button) findViewById(R.id.question_a);
        question2 = (Button) findViewById(R.id.question_b);
        question3 = (Button) findViewById(R.id.question_c);
        question4 = (Button) findViewById(R.id.question_d);


        lin_fen = (LinearLayout) findViewById(R.id.lin_kousuanti);

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
        /**
         * pdf事件处理
         */
        question1.setOnClickListener(this);
        question2.setOnClickListener(this);
        question3.setOnClickListener(this);
        question4.setOnClickListener(this);



        /*
        * 分数选项*/
        Anser_fen1.setOnClickListener(this);
        Anser_fen2.setOnClickListener(this);
        Anser_fen3.setOnClickListener(this);
        Anser_fen4.setOnClickListener(this);

        aosai_question_linearLayout = (LinearLayout) findViewById(R.id.aosai_question_linearLayout);
        aosai_math_pdf = (LinearLayout) findViewById(R.id.aosai_math_pdf);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //pdf事件处理
            case R.id.question_a:
                Aosai_Resource.CONTROLLER.submit("A");
                break;
            case R.id.question_b:
                Aosai_Resource.CONTROLLER.submit("B");
                break;
            case R.id.question_c:
                Aosai_Resource.CONTROLLER.submit("C");
                break;
            case R.id.question_d:
                Aosai_Resource.CONTROLLER.submit("D");
                break;


            //普通数据
            case R.id.Answer1:
                Aosai_Resource.CONTROLLER.submit("A");
                break;
            case R.id.Answer2:
                Aosai_Resource.CONTROLLER.submit("B");
                break;
            case R.id.Answer3:
                Aosai_Resource.CONTROLLER.submit("C");
                break;
            case R.id.Answer4:
                Aosai_Resource.CONTROLLER.submit("D");
                break;
            //分数
            case R.id.anser1_:
                Anser_fen1.setOnTouchListener(new Button.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            Anser_fen1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            Anser_fen1.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                        return false;
                    }
                });

                Aosai_Resource.CONTROLLER.submit("A");
                break;
            case R.id.anser2_:
                Anser_fen2.setOnTouchListener(new Button.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            Anser_fen2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            Anser_fen2.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                        return false;
                    }
                });

                Aosai_Resource.CONTROLLER.submit("B");
                break;
            case R.id.anser3_:
                Anser_fen3.setOnTouchListener(new Button.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            Anser_fen3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            Anser_fen3.setBackgroundColor(getResources().getColor(R.color.write));
                        }
                        return false;
                    }
                });
                Aosai_Resource.CONTROLLER.submit("C");
                break;
            case R.id.anser4_:
                Anser_fen4.setOnTouchListener(new Button.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            Anser_fen4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            Anser_fen4.setBackgroundColor(getResources().getColor(R.color.write));
                        }
                        return false;
                    }
                });
                Aosai_Resource.CONTROLLER.submit("D");
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
                // TODO: 16/7/12 在这里写一个跳转到满分的activity
                if (App.GetSharePrefrence_role(ContextHolder.getContext()).equals("1"))//我是学生
                {
                    System.out.print("我是满分学生doeaxm");
                    List<XTCSJG> xtcsjgList1 = Db_XTCSJGService.getInstance(this).loadAllNote();
                    HashMap<String, String> hmap = new HashMap<>();
                    String JiaocaiSBNo = xtcsjgList1.get(0).getTest_group_number().substring(0, 5);
                    hmap.put("JiaocaiSBNo", JiaocaiSBNo);
                    String CSJG = JSON.toJSONString(xtcsjgList1.get(0));
                    hmap.put("CSJG", CSJG);
                    //TODO 上传整组测试结果
                    System.out.println("这个是学生的满分--首次做");
                    PostStudentsChampionship.PostLink(ContextHolder.getContext(), Constant.PostStudentsChampionship, hmap);
                }
                String a = new Gson().toJson(xtcsjgList);
                Intent intent = new Intent(this, Aosai_ActivityGrade_full_Result.class);
                intent.putExtra("info", a);
                intent.putExtra("nub_zu", nub_zu);
                startActivity(intent);
                finish();
            } else {
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

                    Log.d("hmap","i"+JSON.toJSONString(hmap));

                    PostDoexamFull.PostLink(ContextHolder.getContext(), Constant.post_Error_subject, hmap);
                }
                String a = new Gson().toJson(xtcsjgList);
                Intent intent = new Intent(this, Aosai_ActivityGradeResult.class);
                intent.putExtra("info", a);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                startActivity(intent);
                finish();
            }
        }
        //再次做题
        else {
            String a = new Gson().toJson(xtcsjgList);//Aosai_ActivityGrade_full_Result
            Intent intent = new Intent(this, Aosai_ActivityGradeResult.class);
            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
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
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }
}
