package com.neusoft.sample.View.xel_mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Composition;
import com.neusoft.sample.Ctrl.CompositionItemAdapter;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Fragment.xel_mine_fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xel_mine_youxiuzuowenshangxi extends BaseActivity {
    ImageView back;
    private ListView listView;
    List<HashMap<String, String>> data;
    public CompositionItemAdapter listInfoAdapter;
    public User cUserInfo;
    Boolean succ;//判断是否请求成功 作文
   Handler handler;
    private  TextView feedback_title;
    //定义优秀作文的实体
    List<Composition> youxiuzuowen=new ArrayList<>();
    String url;
    public List<Map<Object,String>> listInfo = new ArrayList<Map<Object,String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cUserInfo = xel_mine_fragment.tUserInfo;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xel_mine_youxiuzuowenshangxi);
        feedback_title= (TextView) findViewById(R.id.feedback_title);
        //返给后台的省市2306，年级1,学期1
        String fanhuihoutai = cUserInfo.getCity_nub().substring(0,4)+cUserInfo.getGrade_nub().substring(9,10)+ getNumber();
//     向后台传数据
        final HashMap hashMap=new HashMap();
        hashMap.put("No",fanhuihoutai);
        if(getIntent().getFlags()==1)//优秀作文赏析
        {
            url=  Constant.youxiuzuowen;
            feedback_title.setText("优秀作文赏析");

        }
        else if (getIntent().getFlags()==0)//好书推荐
        {
            url =  Constant.selectExcellentBook;
            feedback_title.setText("好书推荐");
        }
        else if (getIntent().getFlags()==2)//读后感
        {
            url =  Constant.selectBookReview;
            feedback_title.setText("读后感");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response= Post_learn_rijiyuelei.getStringCha(url,hashMap);
                    JSONObject dataJson = new JSONObject(response);
                    JSONArray data = dataJson.getJSONArray("data");
                    String success = dataJson.getString("success");
                    Log.d("@+@","data"+data);
                    if(success.equals("200")){
                        succ = true;
                        for (int i=0;i<data.length();i++)
                        {
                           String dataitem= data.getString(i);
                            Log.d("@+@","success"+ dataitem);
                            Composition composition=JSON.parseObject(dataitem,Composition.class);
                            youxiuzuowen.add(composition);
                        }

                        Log.d("@+@","success"+ JSON.toJSONString(youxiuzuowen));

                        for (Composition composition : youxiuzuowen){

                            //  String title1 = composition.getTitle1();
                            HashMap<Object, String> map = new HashMap<Object, String>();
                            map.put("title1", composition.getTitle1());
                            map.put("title3",composition.getTitle3());
                            map.put("chapterSequence", composition.getChapterSequence());
                            map.put("pic",composition.getPic());
                            listInfo.add(map);
                        }
                        listInfoAdapter = new CompositionItemAdapter(Xel_mine_youxiuzuowenshangxi.this, listInfo);
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);

                    }
                    else {
                        succ = true;
                        Toast.makeText(Xel_mine_youxiuzuowenshangxi.this, "目前没有资源", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    listView.setAdapter(listInfoAdapter);
                }
            }
        };

        //1.获取ListView对象,通过findViewById方法
        listView = (ListView) findViewById(R.id.youxiuzuowenshangxi_ListView);
        //2.定义一个数组，显示具体内容
//        String[] zuowen = {"1.我的爸爸", "2.未来的桥", "3.我的事情我做主", "4.寻找春天的足迹", "5.写作指导：英语看图作文五步曲"};

        //3.写一个adapter不写具体内容，内容从之前定义好的资源文件里引用过来
        //4.创建Adapter对象设置Adapter
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        Uri uri0 = Uri.parse("http://m.zuowen.chazidian.com/xiezuojiqiao3838/");
//                        Intent intent0 = new Intent(Intent.ACTION_VIEW, uri0);
//                        startActivity(intent0);
//                        break;
//                    case 1:
//                        Uri uri1 = Uri.parse("http://m.zuowen.chazidian.com/zuowen20284257/");
//                        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
//                        startActivity(intent1);
//                        break;
//                    case 2:
//                        Uri uri2 = Uri.parse("http://m.zuowen.chazidian.com/zuowen20284257/");
//                        Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
//                        startActivity(intent2);
//                        break;
//                    case 3:
//                        Uri uri3 = Uri.parse("http://m.zuowen.chazidian.com/zuowen20284172/");
//                        Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
//                        startActivity(intent3);
//                        break;
//                    case 4:
//                        Uri uri4 = Uri.parse("http://m.zuowen.chazidian.com/zuowen20284310/");
//                        Intent intent4 = new Intent(Intent.ACTION_VIEW, uri4);
//                        startActivity(intent4);
//                        break;
//                }
//            }
//        });
        back = (ImageView) findViewById(R.id.rankList_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    /**
     * 判断当前时间  是在  上半年    还是下半年
     */
    public static String getNumber(){

        int  month = new Date().getMonth() ;

        if( month <=8 && month >= 2){
            return "2" ;
        }else{
            return "1" ;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
