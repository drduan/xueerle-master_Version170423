package com.neusoft.sample.View.xel_mine.MyHomeWork;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_HomeWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by wangyujie on 2016/10/5.
 */


public class Chiese_framents_for_homeWork extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private   DatePickerDialog dlg;
    private ArrayAdapter adapter;
    private ListView listView;
    private  ArrayList<String> stringList=new ArrayList<>();
    private TextView time_work;

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    private void onVisible() {
        final HashMap map = new HashMap();
        String classno = Db_UserService.getInstance(getContext()).loadAllNote().get(0).getClasses_nub();
        map.put("classno", classno);
        new AsyncTask<String, Integer, Map<String,Object>>() {

            @Override
            protected Map<String,Object>doInBackground(String... params) {

                Map<String,Object> stringObjectMap=new HashMap<String, Object>();
                try {

                    Log.d("发送的数据",JSON.toJSONString(map));
                    String response = Post_HomeWork.getStringCha(Constant.selectHomeWork, map);
                    Log.d("response_Tag", response + "pp");
                    stringList.clear();
                    String data1 = null;

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.get("success").equals("200")) {
                        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject dataitem = (JSONObject) jsonArray.get(i);
                            if ("语文".equals(dataitem.getString("subject"))) {
                                List<HomeWork_Entity> homeWork_entities = JSON.parseArray(dataitem.get("homework").toString(), HomeWork_Entity.class);
                                Collections.sort(homeWork_entities);
                                int position=1;
                                for (HomeWork_Entity homeWork_entity : homeWork_entities) {
//                                    stringList.add(homeWork_entity.getWorkname() + "   发布人:" + dataitem.get("jobperson"));
                                    stringList.add(position+"  "+homeWork_entity.getWorkname() );
                                    position++;
                                }

                                data1 = dataitem.getString("date1");
                                Log.d("dataString数学", JSON.toJSONString(stringList));

                                break;
                            }

                        }
                        stringObjectMap.put("data1", data1);
                        stringObjectMap.put("stringList", stringList);
                        Log.d("stringObjectMap", "oo" + JSON.toJSONString(stringObjectMap));
                    }else {
                        Looper.prepare();
                        Toast.makeText(getContext(),jsonObject.get("error").toString(),Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    return stringObjectMap;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                return stringObjectMap;
            }

            @Override
            protected void onPostExecute(  Map<String,Object> stringObjectMap) {
                super.onPostExecute(stringObjectMap);
                if (stringObjectMap!=null||!stringObjectMap.isEmpty()) {
                    Log.d("stringObjectMap","opppo"+JSON.toJSONString(stringObjectMap));
                    ArrayList<String>  strings= (ArrayList<String>) stringObjectMap.get("stringList");
                    if(strings.isEmpty()||strings==null)
                    {
                        Toast.makeText(getContext(),"该班级今日没有作业",Toast.LENGTH_LONG).show();

                    }
                    adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, strings);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    if (stringObjectMap.get("data1")!=null)
                        time_work.setText(""+stringObjectMap.get("data1").toString());
                    Log.d("dataString", JSON.toJSONString(strings));
                }
            }
        }.execute();


    }
    public Chiese_framents_for_homeWork() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     * @param
     *
     */
    public static Chiese_framents_for_homeWork newInstance() {
        Chiese_framents_for_homeWork fragment = new Chiese_framents_for_homeWork();


        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_home_work_chiese, container, false);
        listView = (ListView) rootView.findViewById(R.id.homework_list_chinese);
        listView.setEnabled(false);
        stringList = new ArrayList<>();
        time_work = (TextView)rootView.findViewById(R.id.time_work);
        Log.d("我是语文", "i" );


        time_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar d = Calendar.getInstance(Locale.CHINA);
                Date myDate = new Date();
                d.setTime(myDate);
                int year = d.get(Calendar.YEAR);
                int month = d.get(Calendar.MONTH);
                int day = d.get(Calendar.DAY_OF_MONTH);
                dlg = new DatePickerDialog(getContext(), Chiese_framents_for_homeWork.this, year, month, day);
                dlg.show();


            }
        });




        return rootView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        time_work.setText(Integer.toString(year) + "年" +
                ( Integer.toString(monthOfYear+1)) + "月" +
                Integer.toString(dayOfMonth) + "日  " + Constant.getWeek(year + "-" + ( Integer.toString(monthOfYear+1)) + "-" + dayOfMonth));

        final HashMap map = new HashMap();
        String classno = Db_UserService.getInstance(getContext()).loadAllNote().get(0).getClasses_nub();
        map.put("classno", classno);
        map.put("date1",Integer.toString(year) + "-" +
               ( Integer.toString(monthOfYear+1)) + "-" +
                Integer.toString(dayOfMonth) );

        new AsyncTask<String, Integer, Map<String,Object>>() {

            @Override
            protected Map<String,Object>doInBackground(String... params) {

                Map<String,Object> stringObjectMap=new HashMap<String, Object>();
                try {

                    Log.d("发送的数据",JSON.toJSONString(map));
                    String response = Post_HomeWork.getStringCha(Constant.selectHomeWork, map);
                    Log.d("response_Tag", response + "pp");
                    stringList.clear();
                    String data1 = null;

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.get("success").equals("200")) {
                    JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject dataitem = (JSONObject) jsonArray.get(i);
                        if ("语文".equals(dataitem.getString("subject"))) {
                            List<HomeWork_Entity> homeWork_entities = JSON.parseArray(dataitem.get("homework").toString(), HomeWork_Entity.class);
                            Collections.sort(homeWork_entities);
                            for (HomeWork_Entity homeWork_entity : homeWork_entities) {
                                stringList.add(homeWork_entity.getWorkname() + "   发布人:" + dataitem.get("jobperson"));
                            }

                            data1=dataitem.getString("date1");
                            Log.d("dataString语文", JSON.toJSONString(stringList));

                            break;
                        }
                    }
                    stringObjectMap.put("data1",data1);
                    stringObjectMap.put("stringList",stringList);
                    Log.d("stringObjectMap","oo"+JSON.toJSONString(stringObjectMap));
                    }else {
                        Looper.prepare();
                        Toast.makeText(getContext(),jsonObject.get("error").toString(),Toast.LENGTH_LONG).show();
                        Looper.loop();

                    }
                    return stringObjectMap;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                return stringObjectMap;
            }

            @Override
            protected void onPostExecute(  Map<String,Object> stringObjectMap) {
                super.onPostExecute(stringObjectMap);
                if (stringObjectMap!=null||!stringObjectMap.isEmpty()) {
                    Log.d("stringObjectMap","opppo"+JSON.toJSONString(stringObjectMap));
                    ArrayList<String>  strings= (ArrayList<String>) stringObjectMap.get("stringList");
                    if(strings.isEmpty()||strings==null)
                    {
                        Toast.makeText(getContext(),"该班级今日没有作业",Toast.LENGTH_LONG).show();

                    }
                    adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, strings);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    if (stringObjectMap.get("data1")!=null)
                    time_work.setText(stringObjectMap.get("data1").toString());
                    Log.d("dataString", JSON.toJSONString(strings));


                }
            }
        }.execute();

    }
}
