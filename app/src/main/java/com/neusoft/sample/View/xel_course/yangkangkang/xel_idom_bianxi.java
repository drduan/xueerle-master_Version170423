package com.neusoft.sample.View.xel_course.yangkangkang;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.wenchengcheng.verifyStoragePermissions;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.Model.pdf_http;
import com.neusoft.sample.View.BaseActivity;

import java.io.File;

public class xel_idom_bianxi extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener, OnErrorListener {
    String url = "";
    ImageButton btn_bainxi_leftbutton;
    TextView tv_title;
    PDFView pdfView;
    private ProgressBar pb_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_idom_bianxi);
        verifyStoragePermissions.verifyStoragePermissions(this);
        INit_view();
        INI_getconce();//合成路径

    }

    //    合成lujing
    private void INI_getconce() {
        String urlll = "";
        Consant_stringg.getNum_2();
        Consant_stringg.getChengyu();
        Consant_stringg.getUrl_pdf();
        if(Consant_stringg.getA() ==0 ||Consant_stringg.getA() == 3){
            urlll = Stitching.ht_ip + "3/99/esynonym/" + Consant_stringg.getUrl_pdf() + ".pdf";
            Log.d("@@","打印pdf路径"+urlll);

        }else {
            urlll = Stitching.ht_ip + "2/99/cynonym/" + Consant_stringg.getUrl_pdf() + ".pdf";
        }
        Log.d("@@","打印路径"+urlll);
        url = urlll;
        pb_bar.setVisibility(View.VISIBLE);
        pdf_http.pdfhttp(url);
        pb_bar.setVisibility(View.VISIBLE);
        pdf_http.handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.getData().getInt("passkey") == 1) {
                    pb_bar.setVisibility(View.GONE);
                    INI_uppdf();
                }
            }
        };
        if(Consant_stringg.getA() == 2 ){
            tv_title.setText("课程>语文>成语接龙>近义词>"+Consant_stringg.getChengyu());
        }else if(Consant_stringg.getA() == 2){
            tv_title.setText("课程>语文>成语接龙>反义词>"+Consant_stringg.getChengyu());
        }else if(Consant_stringg.getA() == 0){
            tv_title.setText("课程>英语背单词>近义词>"+Consant_stringg.getChengyu());
        }else {
            tv_title.setText("课程>英语背单词>近义词>"+Consant_stringg.getChengyu());
        }
    }

    /*加载pdf*/

    private void INI_uppdf() {
        pdfView.setVisibility(View.VISIBLE);
        Log.d("@@", "路径" + pdf_http.getlujing() + "pdf.pdf");
        pdfView.fromFile(new File(pdf_http.getlujing() + "pdf.pdf"))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .onLoad(xel_idom_bianxi.this)
                .onPageChange(xel_idom_bianxi.this)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .load();
    }

    /*更新视图初始化控件*/

    private void INit_view() {
        pb_bar = (ProgressBar) findViewById(R.id.pdf_bar);
        btn_bainxi_leftbutton = (ImageButton) findViewById(R.id.bainxi_leftbutton);
        tv_title = (TextView) findViewById(R.id.bianxi_idioma_navtitle);
        pdfView = (PDFView) findViewById(R.id.bianxi_pdfView);
        btn_bainxi_leftbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}
