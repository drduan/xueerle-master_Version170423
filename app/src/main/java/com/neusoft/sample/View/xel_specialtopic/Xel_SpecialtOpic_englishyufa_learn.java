package com.neusoft.sample.View.xel_specialtopic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.yangkangkang.EnglishYuFaStudy;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.shockwave.pdfium.PdfDocument;

import org.json.JSONException;
import org.json.JSONObject;

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


public class Xel_SpecialtOpic_englishyufa_learn extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener, OnErrorListener {

    OkHttpClient mOkHttpClient = new OkHttpClient();
    PDFView pdfView;
    String pdfFileName;
    String pdfname;
    private ImageButton fanhui;
    private AlwaysMarqueeTextView toolbar_in_aosai_learns;
    private static final String TAG = Xel_SpecialtOpic_englishyufa_learn.class.getSimpleName();
    Integer pageNumber = 0;

    EnglishYuFaStudy englishYuFaStudy;//shiti
    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel__specialt_opic__english__yufa__learn);

        fanhui= (ImageButton) findViewById(R.id.fanhui);

        toolbar_in_aosai_learns= (AlwaysMarqueeTextView) findViewById(R.id.toolbar_in_aosai_learns);


        Intent intent=getIntent();
       pdfname=  intent.getStringExtra("pdfname");
        System.out.println("pdfnameh"+pdfname);
        try {
            JSONObject jsonObject = new JSONObject(pdfname);
            if("200".equals(jsonObject.get("success")))
            englishYuFaStudy = JSON.parseObject((String) jsonObject.get("data"),EnglishYuFaStudy.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       final String pdffanhuimingzi = englishYuFaStudy.getEnglishYuFa();


        System.out.println("pdfnamehh"+pdfname);



      final String yufajiegou="21"+intent.getStringExtra("yufa");
////教材编号
//        final String jiaocaizhang=yufajiegou.substring(2,7);  //yufa83151,01
        final String jiaocaijie=yufajiegou.substring(7,9);
//教材的章节结构
//        System.out.println("yufa"+jiaocaizhang+","+jiaocaijie);

//标题
        toolbar_in_aosai_learns.setText("专题>英语>英语语法>第"+jiaocaijie+"讲");
//奥赛aosainub




//        接下来去后台请求数据，返回pdf的名字











//        下面的是读取pdf
        final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.xel.www/";
        pdfView= (PDFView) findViewById(R.id.pdfView);
        final android.os.Handler handler=new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.getData().getInt("passkey")==1) {
                    if (new File(SDPath +pdffanhuimingzi+".pdf").exists()) {
//                        dialog.dismiss();
                        pdfView.fromFile(new File(SDPath + pdffanhuimingzi+".pdf"))
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .onLoad(Xel_SpecialtOpic_englishyufa_learn.this)
                                .onPageChange(Xel_SpecialtOpic_englishyufa_learn.this)
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
      String urlll ="http://122.156.218.189:8080/Xrl/resources/xrlRes/3/99/grammar/"+pdffanhuimingzi+".pdf";
        Request request = new Request.Builder().url(urlll).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

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
                    File file = new File(SDPath, pdffanhuimingzi+".pdf");
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
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



            }

        });

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
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
    public void onError(Throwable t) {

    }
}
