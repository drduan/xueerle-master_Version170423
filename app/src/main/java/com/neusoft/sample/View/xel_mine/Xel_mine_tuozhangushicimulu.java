package com.neusoft.sample.View.xel_mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.View.BaseActivity;

import java.util.HashMap;
import java.util.List;

public class Xel_mine_tuozhangushicimulu extends BaseActivity {

    ImageButton back;
    private ListView listView;
    List<HashMap<String, String>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xel_mine_tuozhangushicimulu);
        //1.获取ListView对象,通过findViewById方法
        listView = (ListView) findViewById(R.id.tuozhangushicimulu_ListView);
        //2.定义一个数组，显示具体内容
        String[] gushici = {"第1组	第1首	关雎（诗经）",
                "第1组	第2首	蒹葭（诗经）",
                "第1组	第3首	十五从军征（汉乐府）",
                "第1组	第4首	观沧海（魏•曹操）",
                "第1组	第5首	饮酒（东晋•陶渊明）",
                "第2组	第6首	木兰诗（北朝民歌）",
                "第2组	第7首	送杜少府之任蜀州（唐•王勃）",
                "第2组	第8首	登幽州台歌（唐•陈子昂）",
                "第2组	第9首	次北固山下（唐•王湾）",
                "第2组	第10首	使至塞上（唐•王维）",
                "第3组	第11首	闻王昌龄左迁龙标遥有此寄（唐•李白）",
                "第3组	第12首	行路难（唐•李白）",
                "第3组	第13首	黄鹤楼（唐•崔颢）",
                "第3组	第14首	望岳（唐•杜甫）",
                "第3组	第15首	春望（唐•杜甫）",
                "第4组	第16首	茅屋为秋风所破歌（唐•杜甫）",
                "第4组	第17首	白雪歌送武判官归京（唐•岑参）",
                "第4组	第18首	酬乐天扬州初逢席上见赠（唐•刘禹锡）",
                "第4组	第19首	卖炭翁（唐•白居易）",
                "第4组	第20首	钱塘湖春行（唐•白居易）",
                "第5组	第21首	雁门太守行（唐•李贺）",
                "第5组	第22首	赤壁（唐•杜牧）",
                "第5组	第23首	泊秦淮（唐•杜牧）",
                "第5组	第24首	夜雨寄北（唐•李商隐）",
                "第5组	第25首	无题（唐•李商隐）",
                "第6组	第26首	相见欢（唐•李煜）",
                "第6组	第27首	渔家傲•秋思（北宋•范仲淹）",
                "第6组	第28首	浣溪沙（一曲新词酒一杯）（北宋•晏殊）",
                "第6组	第29首	登飞来峰（北宋•王安石）",
                "第6组	第30首	江城子•密州出猎（北宋•苏轼）",
                "第7组	第31首	水调歌头（北宋•苏轼）",
                "第7组	第32首	渔家傲（宋•李清照）",
                "第7组	第33首	游山西村（南宋•陆游）",
                "第7组	第34首	南乡子•登京口北固亭有怀（南宋•辛弃疾）",
                "第7组	第35首	破阵子（南宋•辛弃疾）",
                "第8组	第36首	过零丁洋（南宋•文天祥）",
                "第8组	第37首	天净沙•秋思（元•马致远）",
                "第8组	第38首	山坡羊•潼关怀古（元•张养浩）",
                "第8组	第39首	己亥杂诗（清•龚自珍）",
                "第8组	第40首	满江红（南宋•岳飞）"
        };
        //3.写一个adapter不写具体内容，内容从之前定义好的资源文件里引用过来
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                R.layout.support_simple_spinner_dropdown_item, gushici);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, gushici);
        //4.创建Adapter对象设置Adapter
        listView.setAdapter(adapter);
        back = (ImageButton) findViewById(R.id.rankList_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
