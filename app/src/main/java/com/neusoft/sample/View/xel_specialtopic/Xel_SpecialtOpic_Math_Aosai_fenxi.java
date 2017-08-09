package com.neusoft.sample.View.xel_specialtopic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.aosai_zu_Entity;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Xel_SpecialtOpic_Math_Aosai_fenxi extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener, OnErrorListener {
    private ImageButton toolbar;
    private ListView listView;
    OkHttpClient mOkHttpClient = new OkHttpClient();
    AlwaysMarqueeTextView tv_text;
    private List<Object> list;
    private Adapter adapter;
    private static final String TAG = Xel_SpecialtOpic_Math_Aosai_fenxi.class.getSimpleName();
    private static final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.xel.www/";
    private PDFView pdfView;
    private Button pdfq1, pdfq2, pdfq3, pdfq4;


    private TextView stem;
    private Button question1, question2, question3, question4;

    private LinearLayout question_container, pdf_container;

    //pdf类型的答案存储
    ArrayList<String> pdflist_an=new ArrayList();
    //question类型的答案存储
    ArrayList<String> pdflist_que=new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel__specialt_opic__math__aosai__fenxi);
        toolbar = (ImageButton) findViewById(R.id.toolbar_in_aosai_learns);
//        tv_text = (AlwaysMarqueeTextView) findViewById(R.id.title_tootbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        pdfInitView();
//        questionInitView();

        Intent intent = getIntent();
        tv_text.setText(QuestionnumberSwitchTitle.Gettitle(intent.getStringExtra("nub_zu")));
        List<aosai_zu_Entity> list = intent.getParcelableArrayListExtra("Entity");
        Log.d("Entity", "list" + JSON.toJSONString(list));


        recurrence_list(list);





    }

    private void recurrence_list( List<aosai_zu_Entity> list) {
        if (!list.isEmpty()) {
            int i = 0;
            if (i < list.size()) {
                if (list.get(i).getUsedStemPDF()) {//pdf
                    pdf_container.setVisibility(View.VISIBLE);
                    question_container.setVisibility(View.INVISIBLE);
                    LoaderPdf(list.get(i).getItemNo());
                    LoaderButton(i);


                } else {//question
                    pdf_container.setVisibility(View.INVISIBLE);
                    question_container.setVisibility(View.VISIBLE);


                }
            }
        }
    }

    private void LoaderPdf(final String itemno) {
        pdfView= (PDFView) findViewById(R.id.pdfView);
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.getData().getInt("passkey")==1) {
                    if (new File(SDPath +  itemno+".pdf").exists()) {

                        pdfView.fromFile(new File(SDPath + itemno+".pdf"))
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .onLoad(Xel_SpecialtOpic_Math_Aosai_fenxi.this)
                                .onPageChange(Xel_SpecialtOpic_Math_Aosai_fenxi.this)
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
            file.mkdir();
        }
//        "http://122.156.218.189:8080/Xrl/resources/xrlRes/2/45/bishun/二_out.json"
        String urlll ="http://122.156.218.189:8080/Xrl/resources/xrlRes/1/21/"+itemno.substring(0,5)+"/"+"10"+itemno+"1"+".pdf";
        Log.d("urlll",urlll);
        Request request = new Request.Builder().url("http://122.156.218.189:8080/Xrl/resources/xrlRes/1/21/"+itemno.substring(0,5)+"/"+"10"+itemno+"1"+".pdf").build();
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
                    File file = new File(SDPath, itemno+".pdf");
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
    }

    private void LoaderButton(final int i) {
        pdfq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                pdflist_an.add("A");
//                list.get(i);
//                recurrence_list(list);


            }
        });
        pdfq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdflist_an.add("B");
            }
        });
        pdfq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdflist_an.add("C");
            }
        });
        pdfq4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdflist_an.add("D");
            }
        });
    }

//    private void questionInitView() {
//        question_container = (LinearLayout) findViewById(R.id.aosai_question_container);
//        pdfView = (PDFView) findViewById(R.id.pdfView);
//        pdfq1 = (Button) findViewById(R.id.question_a);
//        pdfq2 = (Button) findViewById(R.id.question_b);
//        pdfq3 = (Button) findViewById(R.id.question_c);
//        pdfq4 = (Button) findViewById(R.id.question_d);
//    }
//
//    private void pdfInitView() {
//        pdf_container = (LinearLayout) findViewById(R.id.aosai_math_pdf);
//        stem = (TextView) findViewById(R.id.stem);
//        question1 = (Button) findViewById(R.id.Answer1);
//        question2 = (Button) findViewById(R.id.Answer2);
//        question3 = (Button) findViewById(R.id.Answer3);
//        question4 = (Button) findViewById(R.id.Answer4);
//
//    }


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


    }

    @Override
    public void onPageChanged(int page, int pageCount) {

        setTitle(String.format("%s %s / %s", "", page + 1, pageCount));
    }
}
