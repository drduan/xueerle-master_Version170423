package com.neusoft.sample.View.xel_mine;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.ncapdevi.sample.R;
import com.neusoft.sample.View.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Xel_mine_techangfudao extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener,OnErrorListener {
    private LinearLayout kecheng, yishu, tiyu, techangfudaoyincang,techangfudaoxianshi;
    String pdfName;
    ImageButton back;
    ListView listView;
    OkHttpClient mOkHttpClient = new OkHttpClient();
    private PDFView question_answer_pdfs;
    private String[] name = {"陪你阅读", "数独", "新概念", "电影配音（英文）"};
    private int[] imageids = {R.drawable.xel_mine_techangfudao_peiniyuedu,
            R.drawable.xel_mine_techangfudao_shudu,
            R.drawable.xel_mine_techangfudao_xingainian,
            R.drawable.xel_mine_techangfudao_dianying
    };
    private String[] name1 = {"吉他", "钢琴", "古筝", "绘画", "书法"};
    private int[] imageids1 = {R.drawable.xel_mine_techangfudao_jita,
            R.drawable.xel_mine_techangfudao_gangqin,
            R.drawable.xel_mine_techangfudao_guzheng,
            R.drawable.xel_mine_techangfudao_huihua,
            R.drawable.xel_mine_techangfudao_shufa
    };
    private String[] name2 = {"乒乓球", "羽毛球", "游泳", "跆拳道", "棋类"};
    private int[] imageids2 = {R.drawable.xel_mine_techangfudao_pingpangqiu,
            R.drawable.xel_mine_techangfudao_yumaoqiu,
            R.drawable.xel_mine_techangfudao_youyong,
            R.drawable.xel_mine_techangfudao_taiquandao,
            R.drawable.xel_mine_techangfudao_qilei
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xel_mine_techangfudao);
        back = (ImageButton) findViewById(R.id.rankList_back);
        listView = (ListView) findViewById(R.id.listView);
        techangfudaoyincang = (LinearLayout) findViewById(R.id.techangfudaoyincang);
        techangfudaoxianshi = (LinearLayout) findViewById(R.id.techangfudaoxianshi);
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < name.length; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("head", imageids[i]);
            listem.put("name", name[i]);
            listems.add(listem);
        }
        SimpleAdapter simplead = new SimpleAdapter(Xel_mine_techangfudao.this, listems,
                R.layout.xel_mine_techangfudao_listview, new String[]{"name", "head"},
                new int[]{R.id.textView_icon, R.id.imageView_icon});
        listView.setAdapter(simplead);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //                 这里面是课程的点击事件"陪你阅读", "数独", "新概念", "电影配音（英文）"
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        techangfudaoyincang.setVisibility(View.GONE);
                        techangfudaoxianshi.setVisibility(View.VISIBLE);
//                                下面需要读取网络的pdf，等郭哥上传资源
                        pdfName = "陪你阅读";
                        InitEvent(pdfName);
                        break;
                    case 1:
                        techangfudaoyincang.setVisibility(View.GONE);
                        techangfudaoxianshi.setVisibility(View.VISIBLE);
                        pdfName = "数独";
                        InitEvent(pdfName);
                        break;
                    case 2:
                        techangfudaoyincang.setVisibility(View.GONE);
                        techangfudaoxianshi.setVisibility(View.VISIBLE);
                        pdfName = "新概念";
                        InitEvent(pdfName);
                        break;
                    case 3:
                        techangfudaoyincang.setVisibility(View.GONE);
                        techangfudaoxianshi.setVisibility(View.VISIBLE);
                        pdfName = "电影配音";
                        InitEvent(pdfName);
                        break;

                }
            }
        });
        //课程按钮点击
        kecheng = (LinearLayout) findViewById(R.id.kecheng);
        kecheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techangfudaoyincang.setVisibility(View.VISIBLE);
                techangfudaoxianshi.setVisibility(View.GONE);
                //ListView
                List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < name.length; i++) {
                    Map<String, Object> listem = new HashMap<String, Object>();
                    listem.put("head", imageids[i]);
                    listem.put("name", name[i]);
                    listems.add(listem);
                }
                SimpleAdapter simplead = new SimpleAdapter(Xel_mine_techangfudao.this, listems,
                        R.layout.xel_mine_techangfudao_listview, new String[]{"name", "head"},
                        new int[]{R.id.textView_icon, R.id.imageView_icon});
                listView.setAdapter(simplead);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    //                 这里面是课程的点击事件"陪你阅读", "数独", "新概念", "电影配音（英文）"
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
//                                下面需要读取网络的pdf，等郭哥上传资源
                                pdfName = "陪你阅读";
                                InitEvent(pdfName);
                                break;
                            case 1:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "数独";
                                InitEvent(pdfName);
                                break;
                            case 2:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "新概念";
                                InitEvent(pdfName);
                                break;
                            case 3:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "电影配音";
                                InitEvent(pdfName);
                                break;

                        }
                    }
                });
            }
        });

        //艺术按钮点击"吉他", "钢琴", "古筝", "绘画", "书法"
        yishu = (LinearLayout) findViewById(R.id.yishu);
        yishu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techangfudaoyincang.setVisibility(View.VISIBLE);
                techangfudaoxianshi.setVisibility(View.GONE);
                //ListView
                List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < name1.length; i++) {
                    Map<String, Object> listem = new HashMap<String, Object>();
                    listem.put("head", imageids1[i]);
                    listem.put("name", name1[i]);
                    listems.add(listem);
                }
                SimpleAdapter simplead1 = new SimpleAdapter(Xel_mine_techangfudao.this, listems,
                        R.layout.xel_mine_techangfudao_listview, new String[]{"name", "head"},
                        new int[]{R.id.textView_icon, R.id.imageView_icon});
                listView.setAdapter(simplead1);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    //                 这里面是艺术的点击事件"吉他", "钢琴", "古筝", "绘画", "书法"
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
//                                下面需要读取网络的pdf，等郭哥上传资源
                                pdfName = "吉他";
                                InitEvent(pdfName);
                                break;
                            case 1:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "钢琴";
                                InitEvent(pdfName);
                                break;
                            case 2:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "古筝";
                                InitEvent(pdfName);
                                break;
                            case 3:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "绘画";
                                InitEvent(pdfName);
                                break;
                            case 4:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "书法";
                                InitEvent(pdfName);
                                break;
                        }
                    }
                });
            }
        });
        //体育按钮点击"乒乓球", "羽毛球", "游泳", "跆拳道", "棋类"
        tiyu = (LinearLayout) findViewById(R.id.tiyu);
        tiyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techangfudaoyincang.setVisibility(View.VISIBLE);
                techangfudaoxianshi.setVisibility(View.GONE);
                //ListView
                List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < name2.length; i++) {
                    Map<String, Object> listem = new HashMap<String, Object>();
                    listem.put("head", imageids2[i]);
                    listem.put("name", name2[i]);
                    listems.add(listem);
                }
                SimpleAdapter simplead2 = new SimpleAdapter(Xel_mine_techangfudao.this, listems,
                        R.layout.xel_mine_techangfudao_listview, new String[]{"name", "head"},
                        new int[]{R.id.textView_icon, R.id.imageView_icon});
                listView.setAdapter(simplead2);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    //                 这里面是体育的点击事件乒乓球", "羽毛球", "游泳", "跆拳道", "棋类"
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
//                                下面需要读取网络的pdf，等郭哥上传资源
                                pdfName = "乒乓球";
                                InitEvent(pdfName);
                                break;
                            case 1:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "羽毛球";
                                InitEvent(pdfName);
                                break;
                            case 2:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "游泳";
                                InitEvent(pdfName);
                                break;
                            case 3:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "跆拳道";
                                InitEvent(pdfName);
                                break;
                            case 4:
                                techangfudaoyincang.setVisibility(View.GONE);
                                techangfudaoxianshi.setVisibility(View.VISIBLE);
                                pdfName = "棋类";
                                InitEvent(pdfName);
                                break;

                        }
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    //粘的
    private void InitEvent(final String pdfName) {
        question_answer_pdfs= (PDFView) findViewById(R.id.question_pdfss);
        final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.xel.www/";
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.getData().getInt("passkey")==1) {
                    if (new File(SDPath +pdfName+".pdf").exists()) {
                        question_answer_pdfs.fromFile(new File(SDPath +pdfName+".pdf"))
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .onLoad(Xel_mine_techangfudao.this)
                                .onPageChange(Xel_mine_techangfudao.this)
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




//        String urlll ="http://122.156.218.189:8080/Xrl/resources/xrlRes/1/11/34051/06/1134051060101031.pdf";
       String urlll ="http://122.156.218.189:8080/Xrl/resources/xrlRes/0/techang/"+pdfName+".pdf";
        Log.d("urlll","i"+urlll);
        Request request = new Request.Builder().url(urlll).build();
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
                    File file = new File(SDPath, pdfName+".pdf");
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
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
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
