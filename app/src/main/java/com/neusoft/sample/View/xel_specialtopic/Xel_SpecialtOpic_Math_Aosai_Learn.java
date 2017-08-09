package com.neusoft.sample.View.xel_specialtopic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.ncapdevi.sample.R;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
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


public class Xel_SpecialtOpic_Math_Aosai_Learn extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener,OnErrorListener {
    private ImageButton toolbar;
    private AlwaysMarqueeTextView alwaysMarqueeTextView;
    OkHttpClient mOkHttpClient = new OkHttpClient();
    PDFView pdfView;
    String pdfFileName;
    private static final String TAG = Xel_SpecialtOpic_Math_Aosai_Learn.class.getSimpleName();
    Integer pageNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel__specialt_opic__math__aosai__learn);
        toolbar = (ImageButton) findViewById(R.id.toolbar_in_aosai_learns);
        alwaysMarqueeTextView= (AlwaysMarqueeTextView) findViewById(R.id.titles1);

        final ProgressDialog     dialog = ProgressDialog.show(this, "请稍后", "正在加载数据..");
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条
        Intent intent=getIntent();
      final String aosainub="21"+intent.getStringExtra("aosainub");
        alwaysMarqueeTextView.setText(QuestionnumberSwitchTitle.GetSudytitle(intent.getStringExtra("aosainub")));

        System.out.println("奥赛的返的");
        //教材编号
        final String jiaocaimulu=aosainub.substring(2,7);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.xel.www/";
        pdfView= (PDFView) findViewById(R.id.pdfView);
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
              if (msg.getData().getInt("passkey")==1) {
                  if (new File(SDPath +  aosainub+".pdf").exists()) {
                      dialog.dismiss();
                      pdfView.fromFile(new File(SDPath + aosainub+".pdf"))
                              .enableSwipe(true)
                              .swipeHorizontal(false)
                              .enableDoubletap(true)
                              .defaultPage(0)
                              .onLoad(Xel_SpecialtOpic_Math_Aosai_Learn.this)
                              .onPageChange(Xel_SpecialtOpic_Math_Aosai_Learn.this)
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
        String urlll ="http://122.156.218.189:8080/Xrl/resources/xrlRes/1/21/"+jiaocaimulu+"/"+aosainub+".pdf";
        Log.d("urlll",urlll);
        Request request = new Request.Builder().url("http://122.156.218.189:8080/Xrl/resources/xrlRes/1/21/"+jiaocaimulu+"/"+aosainub+".pdf").build();
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
