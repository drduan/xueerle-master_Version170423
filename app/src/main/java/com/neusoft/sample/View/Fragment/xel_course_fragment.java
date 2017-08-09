package com.neusoft.sample.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_CaculationTest;
import com.neusoft.sample.GreenDao.CalculationTest;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.FindDB_kejiezu;
import com.neusoft.sample.Model.Post_Kousuan;
import com.neusoft.sample.Model.Post_er;
import com.neusoft.sample.Model.Post_learn;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.View.BaseFragment;
import com.neusoft.sample.View.SecondActivity;
import com.neusoft.sample.View.xel_course.Xel_Course_English_Textbooklearn_wordlearn;
import com.neusoft.sample.View.xel_course.xel_activity_noo;
import com.neusoft.sample.View.xel_course.xel_course_chinese_textbooklearn;
import com.neusoft.sample.View.xel_course.yangkangkang.Xel_Course_Chinese_IdiomaGame;
import com.neusoft.sample.View.xel_course.yangkangkang.Xel_Course_English_TextBookLearn;
import com.neusoft.sample.util.ContextHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by niccapdevila on 3/26/16.
 */
public class xel_course_fragment extends BaseFragment  {
    private Boolean bool;
    private Button btn_haoti;
    String nub_zu = "";//口算题卡
    String sectionNo = "";//口算题卡
    String no = "";//语文的课本学习
    String chinese_name;//语文课本学习获取的章节名
    String no_game = "";//语文的成语接龙
    String ke_id = "";//英语课本学习选中的教材id
    String eng_learn_word_id = "";//鞥与单词学习id
    String ke_name = "";//英语课本学习选中的章名称
    HashMap<String, String> map_english_ke = new HashMap<>();
    Handler handler_er;
    ArrayAdapter<String> zu_adapter_er;
    Handler handler_er_recite;

    public static xel_course_fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        xel_course_fragment fragment = new xel_course_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bool = Consant_stringg.is_internet(getActivity());
        if (bool == false) {
            Toast.makeText(getActivity(), "请检查您的网络设置!", Toast.LENGTH_SHORT).show();
        }

        final View v = inflater.inflate(R.layout.kecheng_layout, null);


                kousuanka(v);//口算题卡
                haoti(v);//好题精解




        //课程中的语数外的切换
        intiswitchView(v);

        return v;

    }

    private void intiswitchView(final View v) {
        final View math_line=v.findViewById(R.id.math_line);
        final     View chinese_line=v.findViewById(R.id.chinese_line);
        final  View english_line=v.findViewById(R.id.english_line);
        final TextView math = (TextView) v.findViewById(R.id.math);
        final TextView yuwen = (TextView) v.findViewById(R.id.chinese);
        final TextView english = (TextView) v.findViewById(R.id.english);
        final LinearLayout lay_math = (LinearLayout) v.findViewById(R.id.lay_math);
        final LinearLayout lay_yuwen = (LinearLayout) v.findViewById(R.id.lay_chinese);
        final LinearLayout lay_english = (LinearLayout) v.findViewById(R.id.lay_english);
        lay_math.setVisibility(View.VISIBLE);
        lay_english.setVisibility(View.GONE);
        lay_yuwen.setVisibility(View.GONE);

        math_line.setVisibility(View.VISIBLE);
        chinese_line.setVisibility(View.GONE);
        english_line.setVisibility(View.GONE);

        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kousuanka(v);//口算题卡
                haoti(v);//好题精解
//                math.setTextColor(getResources().getColor(R.color.zhuti));
//                yuwen.setTextColor(Color.BLACK);
//                english.setTextColor(Color.BLACK);

                math_line.setVisibility(View.VISIBLE);
                chinese_line.setVisibility(View.GONE);
                english_line.setVisibility(View.GONE);

                lay_english.setVisibility(View.GONE);
                lay_math.setVisibility(View.VISIBLE);
                lay_yuwen.setVisibility(View.GONE);
            }
        });
        yuwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chinese_textbook_learn(v);//语文的课本学习
                Chinese_IdiomGame(v);//语文成语接龙
                Chinese_Resourcecomment(v);//语文的资源评价
//                math.setTextColor(Color.BLACK);
//                yuwen.setTextColor(getResources().getColor(R.color.zhuti));
//                english.setTextColor(Color.BLACK);

                math_line.setVisibility(View.GONE);
                chinese_line.setVisibility(View.VISIBLE);
                english_line.setVisibility(View.GONE);


                lay_english.setVisibility(View.GONE);
                lay_math.setVisibility(View.GONE);
                lay_yuwen.setVisibility(View.VISIBLE);

            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                English_textBooklearn(v);//英语的课本学习
                English_Recitewords(v);//英语的背单词
                English_wordstest(v);//英语的单词测试
                English_Resourcecomment(v);//英语的资源评价
//                math.setTextColor(Color.BLACK);
//                yuwen.setTextColor(Color.BLACK);
//                english.setTextColor(getResources().getColor(R.color.zhuti));

                math_line.setVisibility(View.GONE);
                chinese_line.setVisibility(View.GONE);
                english_line.setVisibility(View.VISIBLE);

                lay_english.setVisibility(View.VISIBLE);
                lay_math.setVisibility(View.GONE);
                lay_yuwen.setVisibility(View.GONE);

            }
        });
    }

    private void English_Resourcecomment(View v) {

    }

    private void English_wordstest(View v) {
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final String[] chapterNo = new String[1];
        final ArrayList<String> list_data_zu = new ArrayList<String>();
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.english_beidanciceshi_ke);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.english_beidanciceshi_jie);
        final Spinner zu_spinner = (Spinner) v.findViewById(R.id.english_beidanciceshi_zu);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);

        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "5");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            if (!textOneStructure.getChapterName().equals("\"\""))
                ke_list.add("  "+textOneStructure.getChapterSequenceName() + " " + textOneStructure.getChapterName());
            else ke_list.add("  "+textOneStructure.getChapterSequenceName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                chapterNo[0] = Get_ke.get(position).getChapterNo();
                //节节节节节节节节节**************************************************************************8
                final List<String> zu_list = new ArrayList<>();

                final HashMap<String, String> map = new HashMap<>();
                map.put("No", chapterNo[0] +"00");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response = Post_er.getStringCha(Constant.post_Kousuan, map);
                            Log.d("背单词测试","i"+response);
                            Map<String, Object> map = (Map<String, Object>) JSON.parse(response);

                            String success = (String) map.get("success");
                            if (success.equals("200")) {
                                String ct = map.get("data").toString();

                                List<CalculationTest> list = JSON.parseArray(ct, CalculationTest.class);
                                Db_CaculationTest.getInstance(getContext()).saveNoteLists(list);
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
                                message.setData(bundle);
                                message.what = 1;
                                handler_er_recite.sendMessage(message);
                            } else {
                                Log.d("success", "查询不到结果");
                                Message message = Message.obtain();
                                message.what = 2;
                                handler_er_recite.sendMessage(message);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

                handler_er_recite = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 1) {
                            zu_spinner.setVisibility(View.VISIBLE);
                            List<CalculationTest> calculationTests = msg.getData().getParcelableArrayList("list");
                            if (!calculationTests.isEmpty()) {
                                Log.d("口算题卡", "题目下载成功---正在加载组" + JSON.toJSONString(calculationTests));
                                //获得有多少组
                                int t = 0;
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                for (CalculationTest test : calculationTests) {

                                    hashMap.put(test.getItemNo().substring(0, 11) + "", "");

//
                                }

                                Set<Map.Entry<String, String>> keyEntrySet = hashMap.entrySet();

                                list_data_zu.clear();
                                for (Map.Entry<String, String> entry : keyEntrySet) {
                                    String key = entry.getKey().substring(9, 11);
                                    list_data_zu.add(key);
                                }
                                Collections.sort(list_data_zu);
                                //组的数据源
                                for (int i = 0; i < list_data_zu.size(); i++) {
                                    zu_list.add("  "+"第" + list_data_zu.get(i) + "组");
                                }

                            }
                            zu_adapter_er = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, zu_list);
                            zu_adapter_er.notifyDataSetChanged();
                            zu_spinner.setAdapter(zu_adapter_er);

                        }
                        if (msg.what == 2) {
                            zu_spinner.setVisibility(View.INVISIBLE);
                        }
                    }
                };


                zu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if (position < 9) {
                            nub_zu[0] = "0" + (position + 1);
                        } else nub_zu[0] = String.valueOf(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        LinearLayout button4 = (LinearLayout) v.findViewById(R.id.english_beidanciceshi_go);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zu_spinner.getVisibility() == View.VISIBLE) {

                    if (nub_zu[0]!=null&&!"".equals(nub_zu[0])) {
                        final HashMap<String, String> map = new HashMap<>();
                        map.put("No", sectionNo[0]);
                        Post_Kousuan.Linkage(getContext(), Constant.post_Kousuan, map);
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SecondActivity.class);
                        intent.putExtra("kousuan_go", 3);
                        intent.putExtra("nub_zu", chapterNo[0] + "00" + nub_zu[0]);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getContext(), "当前无资源", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "当前无资源", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void English_Recitewords(View v) {
        final String[] sectionNo = {""};
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.English_Recitewords_ke);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "5");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            if (textOneStructure.getChapterName().equals("\"\""))
                ke_list.add("  "+textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
            else
                ke_list.add(textOneStructure.getChapterSequenceName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ke_id = Get_ke.get(position).getChapterNo();
                eng_learn_word_id = ke_id + "00";
                Log.d("@@@@", "英语背单词的编号" + eng_learn_word_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * 背单词的跳转 */
        LinearLayout button3 = (LinearLayout) v.findViewById(R.id.english_beidanci_go);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bool == false) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("No", eng_learn_word_id);
                    Log.d("@@@@", "frgment" + eng_learn_word_id);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String zhi = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                Intent intent_eng_word = new Intent(getActivity(), Xel_Course_English_Textbooklearn_wordlearn.class);
                                intent_eng_word.putExtra("ke_id", ke_id);
                                intent_eng_word.putExtra("zhi", zhi);
                                startActivity(intent_eng_word);
                            } catch (IOException e) {
                            }
                        }
                    }).start();
                }
            }
        });

    }

    private void English_textBooklearn(View v) {
        /**
         * test
         * 课程中英语的课本学习测试
         */
        final List<String> ke_learn_list = new ArrayList<>();
        final Spinner ke_learn_spinner = (Spinner) v.findViewById(R.id.ke_learn_english_ke);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "5");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            if (!textOneStructure.getChapterName().equals("\"\""))
                ke_learn_list.add("  "+textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
            else
                ke_learn_list.add("  "+textOneStructure.getChapterSequenceName());
        }
        ArrayAdapter<String> ke_learn_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_learn_list);
        ke_learn_spinner.setAdapter(ke_learn_adapter);
        ke_learn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ke_id = Get_ke.get(position).getChapterNo();
                ke_name = Get_ke.get(position).getChapterSequenceName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*
        *
        * */
        LinearLayout textView = (LinearLayout) v.findViewById(R.id.english_kebenxuexi_go);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bool == false) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    Post_learn.Linkage(getContext(), Constant.post_Learn, map_english_ke);
                    Intent intent = new Intent(getContext(), Xel_Course_English_TextBookLearn.class);
                    intent.putExtra("ke_id", ke_id);
                    intent.putExtra("ke_name", ke_name);
                    startActivity(intent);
                }
            }
        });
        LinearLayout button = (LinearLayout) v.findViewById(R.id.english_ziyuan_go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), xel_activity_noo.class);
//                intent.putExtra("ke_id",ke_id);
//                intent.putExtra("ke_name",ke_name);
                startActivity(intent);
            }
        });
    }

    private void Chinese_Resourcecomment(View v) {
        /**
         * test
         * 课程中语文的资源评价测试
         */
        LinearLayout textView2 = (LinearLayout) v.findViewById(R.id.chinese_ziyuanpingjia_go);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(bool == false){
//                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
//                }else {
//                    Intent intent = new Intent(getContext(), Xel_Course_Chinese_resourcecomment.class);
//
//                    startActivity(intent);
//                }
                Intent intent = new Intent(getContext(), xel_activity_noo.class);

                startActivity(intent);
            }
        });
    }

    private void Chinese_IdiomGame(View v) {
        final List<String> ke_list = new ArrayList<>();
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_learn = FindDB_kejiezu.Get_ke(getContext(), user_id, "B");
        Collections.sort(Get_learn);
        for (TextOneStructure textOneStructure : Get_learn) {
            //chapterSequence
            ke_list.add( "  " +textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName());
        }

        Spinner Chinese_IdiomGame_spinner = (Spinner) v.findViewById(R.id.Chinese_IdiomGame_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        Chinese_IdiomGame_spinner.setAdapter(adapter);
        Chinese_IdiomGame_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                no_game = Get_learn.get(position).getChapterNo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //TODO 这里的no是你要的号码
        /**
         * 成语接龙的跳转
         *  chinese_chengyuxuexi_go
         */
        LinearLayout button2 = (LinearLayout) v.findViewById(R.id.chinese_chengyuxuexi_go);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bool) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("No", no_game);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String chengyu_zhi = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_chengyujielong, map);
                                Intent intent = new Intent(getContext(), Xel_Course_Chinese_IdiomaGame.class);
                                intent.putExtra("zhi", chengyu_zhi);
                                intent.putExtra("num", no_game);
                                startActivity(intent);
                            } catch (IOException e) {
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private void Chinese_textbook_learn(View v) {
        final List<String> ke_list = new ArrayList<>();

        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        final List<TextOneStructure> Get_learn = FindDB_kejiezu.Get_ke(getContext(), user_id, "9");
        Collections.sort(Get_learn);
        for (TextOneStructure textOneStructure : Get_learn) {
            if (!textOneStructure.getChapterSequenceName().equals("\"\""))
                ke_list.add( "  " +textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName());
            else
                ke_list.add("  "+textOneStructure.getChapterName());
        }
        Spinner chinese_textbooklearn_spinner = (Spinner) v.findViewById(R.id.chinese_textbooklearn_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        chinese_textbooklearn_spinner.setAdapter(adapter);
        chinese_textbooklearn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                no = Get_learn.get(position).getChapterNo();
                chinese_name = Get_learn.get(position).getChapterName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //TODO 这里的no是你要的号码
        LinearLayout btn_chinses_kebenxuexi = (LinearLayout) v.findViewById(R.id.chinese_kebenxuexxi_go);
        btn_chinses_kebenxuexi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_chinese_1 = new Intent();
                intent_chinese_1.putExtra("mum", no);
                intent_chinese_1.putExtra("mum_name", chinese_name);
                intent_chinese_1.setClass(getActivity(), xel_course_chinese_textbooklearn.class);
                startActivity(intent_chinese_1);
            }
        });
    }

    private void haoti(View v) {
        //好题跳转进入
        LinearLayout button1 = (LinearLayout) v.findViewById(R.id.haoti_gos);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!bool) {
//                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
//                } else {
                Intent intent = new Intent();
                intent.setClass(getActivity(), xel_activity_noo.class);
//                    intent.putExtra("haoti_go", 2);
                startActivity(intent);
            }
        });
    }

    private void kousuanka(View v) {
        /**
         * Spinner的数据源
         */
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.ke_select);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.jie_select);
        final Spinner zu_spinner = (Spinner) v.findViewById(R.id.zu_select);
        final ArrayList<String> list_data_zu = new ArrayList<String>();
        //获取存在sharePrefrence当前的用户的标识
        final String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);

//        List<TextOneStructure> textOneStructureList = Db_TextOneStructureService.getInstance(getContext()).queryNote(TextOneStructureDao.Properties.ChapterNo.name+" LIKE 1_",null);
//       Log.d("textOneStructureList","i"+JSON.toJSONString(textOneStructureList));

        List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "1");

        ke_list.clear();
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add( "  " +textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_adapter.notifyDataSetChanged();
        ke_spinner.setAdapter(ke_adapter);
        final List<TextOneStructure> finalGet_ke = Get_ke;
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = finalGet_ke.get(position).getChapterNo();
                //节节节节节节节节节**************************************************************************8
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add( "  " +textTwoStructure.getSectionSequenceName() + "  " + textTwoStructure.getSectionName());
                }
                ArrayAdapter<String> jie_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                jie_spinner.setAdapter(jie_adapter);
                jie_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, final View view, int position, long id) {
                        final List<String> zu_list = new ArrayList<>();
                        zu_list.clear();
                        sectionNo = Get_jie.get(position).getSectionNo();
                        final HashMap<String, String> map = new HashMap<>();
                        map.put("No", sectionNo);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String response = Post_er.getStringCha(Constant.post_Kousuan, map);

                                    Map<String, Object> maps = (Map<String, Object>) JSON.parse(response);

                                    String success = (String) maps.get("success");
                                    if (success.equals("200")) {
                                        String ct = maps.get("data").toString();
                                        Log.d("OO", ct.toString());
                                        List<CalculationTest> list = JSON.parseArray(ct, CalculationTest.class);
                                        Log.d("OO", JSON.toJSONString(list));
                                        Db_CaculationTest.getInstance(getContext()).saveNoteLists(list);
                                        Message message = new Message();
                                        Bundle bundle=new Bundle();
                                        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
                                        message.setData(bundle);
                                        message.what = 1;
                                        handler_er.sendMessage(message);
                                        Log.d("口算题卡", "题目下载成功---正在加载组");

                                    } else {
                                        Log.d("success", "查询不到结果");
                                        Message message = Message.obtain();
                                        message.what = 2;
                                        handler_er.sendMessage(message);
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        handler_er = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                if (msg.what == 1) {
                                    zu_spinner.setVisibility(View.VISIBLE);
                                    List<CalculationTest> calculationTests = msg.getData().getParcelableArrayList("list");
                                    if (!calculationTests.isEmpty()) {
                                        Log.d("口算题卡", "题目下载成功---正在加载组" + JSON.toJSONString(calculationTests));
                                        //获得有多少组
                                        int t = 0;
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        for (CalculationTest test : calculationTests) {

                                                hashMap.put(test.getItemNo().substring(0, 11) + "", "");

//
                                        }

                                        Set<Map.Entry<String, String>> keyEntrySet = hashMap.entrySet();

                                        list_data_zu.clear();
                                        for (Map.Entry<String, String> entry : keyEntrySet) {
                                            String key = entry.getKey().substring(9, 11);
                                            list_data_zu.add(key);
                                        }
                                        Collections.sort(list_data_zu);
                                        //组的数据源
                                        for (int i = 0; i < list_data_zu.size(); i++) {
                                            zu_list.add("  "+"第" + list_data_zu.get(i) + "组");
                                        }

                                    }
                                    zu_adapter_er = new ArrayAdapter<String>(ContextHolder.getContext(), R.layout.xel_spiner_custom_viewforkoushuantika_zu, zu_list);

                                    zu_adapter_er.notifyDataSetChanged();
                                    zu_spinner.setAdapter(zu_adapter_er);
                                }
                                if (msg.what == 2) {
                                    zu_spinner.setVisibility(View.INVISIBLE);
                                }

                            }
                        };


                        zu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                nub_zu = list_data_zu.get(position);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //口算跳转进入
        LinearLayout button = (LinearLayout) v.findViewById(R.id.kousuan_go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bool) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    if (zu_spinner.getVisibility() == View.VISIBLE) {
                        if (!nub_zu.equals("")) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), SecondActivity.class);
                            intent.putExtra("kousuan_go", 3);
                            intent.putExtra("zh_1", "课程>数学>口算题卡");
                            intent.putExtra("nub_zu", sectionNo + nub_zu);
//                            Log.d("zhunub",sectionNo + nub_zu+"s");
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getActivity(), "当前无资源", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
