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

public class Xel_mine_bibeigushicimulu extends BaseActivity {
    ImageButton back;
    private ListView listView;
    List<HashMap<String, String>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xel_mine_bibeigushicimulu);
        //1.获取ListView对象,通过findViewById方法
        listView = (ListView) findViewById(R.id.bibeigushicimulu_ListView);
        //2.定义一个数组，显示具体内容
        String[] gushici = {"第1组	第1首	江南（汉乐府）",
                "第1组	第2首	长歌行（汉乐府）",
                "第1组	第3首	敕勒歌（北朝民歌）",
                "第1组	第4首	咏鹅（唐•骆宾王）",
                "第1组	第5首	风（唐•李峤）",
                "第2组	第6首	咏柳（唐•贺知章）",
                "第2组	第7首	回乡偶书（唐•贺知章）",
                "第2组	第8首	凉州词（唐•王之涣）",
                "第2组	第9首	登鹳雀楼（唐•王之涣）",
                "第2组	第10首	春晓（唐•孟浩然）",
                "第3组	第11首	凉州词（唐•王翰）",
                "第3组	第12首	出塞（唐•王昌龄）",
                "第3组	第13首	芙蓉楼送辛渐（唐•王昌龄）",
                "第3组	第14首	鹿柴（唐•王维）",
                "第3组	第15首	送元二使安西（唐•王维）",
                "第4组	第16首	九月九日忆山东兄弟（唐•王维）",
                "第4组	第17首	静夜思（唐•李白）",
                "第4组	第18首	古朗月行（节选）（唐•李白）",
                "第4组	第19首	望庐山瀑布（唐•李白）",
                "第4组	第20首	赠汪伦（唐•李白）",
                "第5组	第21首	黄鹤楼送孟浩然之广陵（唐•李白）",
                "第5组	第22首	早发白帝城（唐•李白）",
                "第5组	第23首	望天门山（唐•李白）",
                "第5组	第24首	别董大（唐•高适）",
                "第5组	第25首	绝句（唐•杜甫）",
                "第6组	第26首	春夜喜雨（唐•杜甫）",
                "第6组	第27首	绝句（唐•杜甫）",
                "第6组	第28首	江畔独步寻花（唐•杜甫）",
                "第6组	第29首	枫桥夜泊（唐•张继）",
                "第6组	第30首	滁州西涧（唐•韦应物）",
                "第7组	第31首	游子吟（唐•孟郊）",
                "第7组	第32首	早春呈水部张十八员外（唐•韩愈）",
                "第7组	第33首	渔歌子（唐•张志和）",
                "第7组	第34首	塞下曲（唐•卢纶）",
                "第7组	第35首	望洞庭（唐•刘禹锡）",
                "第8组	第36首	浪淘沙（唐•刘禹锡）",
                "第8组	第37首	赋得古原草送别（唐•白居易）",
                "第8组	第38首	池上（唐•白居易）",
                "第8组	第39首	忆江南（唐•白居易）",
                "第8组	第40首	小儿垂钓（唐•胡令能）",
                "第9组	第41首	悯农（一）（唐•李绅）",
                "第9组	第42首	悯农（二）（唐•李绅）",
                "第9组	第43首	江雪（唐•柳宗元）",
                "第9组	第44首	寻隐者不遇（唐•贾岛）",
                "第9组	第45首	山行（唐•杜牧）",
                "第10组	第46首	清明（唐•杜牧）",
                "第10组	第47首	江南春（唐•杜牧）",
                "第10组	第48首	蜂（唐•罗隐）",
                "第10组	第49首	江上渔者（北宋•范仲淹）",
                "第10组	第50首	元日（北宋•王安石）",
                "第11组	第51首	泊船瓜洲（北宋•王安石）",
                "第11组	第52首	书湖阴先生壁（北宋•王安石）",
                "第11组	第53首	六月二十七日望湖楼醉书（北宋•苏轼）",
                "第11组	第54首	饮湖上初晴后雨（北宋•苏轼）",
                "第11组	第55首	惠崇春江晓景（北宋•苏轼）",
                "第12组	第56首	题西林壁（北宋•苏轼）",
                "第12组	第57首	夏日绝句（南宋•李清照）",
                "第12组	第58首	三衢道中（宋•曾几）",
                "第12组	第59首	示儿（南宋•陆游）",
                "第12组	第60首	秋夜将晓出篱门迎凉有感（南宋•陆游）",
                "第13组	第61首	四时田园杂兴1（南宋•范大成）",
                "第13组	第62首	四时田园杂兴2（南宋•范大成）",
                "第13组	第63首	小池（南宋•杨万里）",
                "第13组	第64首	晓出净慈寺送林子方（南宋•杨万里）",
                "第13组	第65首	春日（南宋•朱熹）",
                "第14组	第66首	观书有感（南宋•朱熹）",
                "第14组	第67首	题临安邸（南宋•林升）",
                "第14组	第68首	游园不值（南宋•叶绍翁）",
                "第14组	第69首	乡村四月（南宋•翁卷）",
                "第14组	第70首	墨梅（元•王冕）",
                "第15组	第71首	石灰吟（明•于谦）",
                "第15组	第72首	竹石（清•郑燮）",
                "第15组	第73首	所见（清•袁枚）",
                "第15组	第74首	村居（清•高鼎）",
                "第15组	第75首	己亥杂诗（清•龚自珍）"
        };
        //3.写一个adapter不写具体内容，内容从之前定义好的资源文件里引用过来
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
