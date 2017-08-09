package com.neusoft.sample.View.AoSaiTest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.aosai_zu_Entity;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Question_pdf_loader extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener,OnErrorListener {
    private com.neusoft.sample.View.AlwaysMarqueeTextView title;
    private    String question_nub;
//    private PDFView question_pdfs;
    private PDFView question_answer_pdfs;
    private ScrollView scrollView;

    private  OkHttpClient mOkHttpClient = new OkHttpClient();
    private PDFView pdfView;
    private  String pdfFileName;
    private static final String TAG = Question_pdf_loader.class.getSimpleName();
    private  Integer pageNumber = 0;
    private  String jiaocaimulu;
    private TextView itemno_question;
    private ImageView question_image;
    private TextView itemno_question_jiexi;
    private TextView itemno_question_summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pdf_loader);
        question_nub="11"+getIntent().getStringExtra("question_nub")+"1";
        aosai_zu_Entity aosai_zu_entity=getIntent().getParcelableExtra("aosai_zu_Entity");
         jiaocaimulu=question_nub.substring(2,7);
        initView();
        if (aosai_zu_entity==null) {
            InitEvent();
        }else {
            question_answer_pdfs.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            //加载其具体的内容

            itemno_question_jiexi= (TextView) findViewById(R.id.itemno_question_jiexi);
            itemno_question_summary= (TextView) findViewById(R.id.itemno_question_summary);

            String jiexi=aosai_zu_entity.getXiangXiJiexi().toString().replaceAll("/n","\n");
            itemno_question_jiexi.setText(jiexi);
            SpannableStringBuilder style = new SpannableStringBuilder(aosai_zu_entity.getSummary());
            style.setSpan(new ForegroundColorSpan(Color.RED), 0, aosai_zu_entity.getSummary().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            itemno_question_summary.setText(style);


        }
    }

    private void InitEvent() {
        question_answer_pdfs.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
       final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.xel.www/";
       final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.getData().getInt("passkey")==1) {
                    if (new File(SDPath +  question_nub.substring(0,15)+"2"+".pdf").exists()) {
                        question_answer_pdfs.fromFile(new File(SDPath + question_nub.substring(0,15)+"2"+".pdf"))
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .onLoad(Question_pdf_loader.this)
                                .onPageChange(Question_pdf_loader.this)
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


        String urlll ="http://122.156.218.189:8080/Xrl/resources/xrlRes/1/11/"+jiaocaimulu+"/"+question_nub.substring(7,9)+"/"+question_nub.substring(0,15)+"2"+".pdf";
        Log.d("urlll","i"+urlll);
        Request request = new Request.Builder().url("http://122.156.218.189:8080/Xrl/resources/xrlRes/1/11/"+jiaocaimulu+"/"+question_nub.substring(7,9)+"/"+question_nub.substring(0,15)+"2"+".pdf").build();
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
                    File file = new File(SDPath, question_nub.substring(0,15)+"2"+".pdf");
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









    private void initView() {
        title= (com.neusoft.sample.View.AlwaysMarqueeTextView) findViewById(R.id.titles1);
        title.setText(QuestionnumberSwitchTitle.Gettitle(getIntent().getStringExtra("question_nub")));
//        question_pdfs= (PDFView) findViewById(R.id.question_pdfs);
        scrollView= (ScrollView) findViewById(R.id.itemno_question_all);
        question_answer_pdfs= (PDFView) findViewById(R.id.question_answer_pdfs);

    }
    public void fanhui(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = question_answer_pdfs.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(question_answer_pdfs.getTableOfContents(), "-");
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
