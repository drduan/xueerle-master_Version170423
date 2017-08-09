package com.neusoft.sample.Virtual_data;


import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.neusoft.sample.GreenDao.CalculationTest;
import com.neusoft.sample.Model.ErrorSubject_two;
import com.neusoft.sample.Model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanxudong on 16/5/21.
 */
public class QuestionLib {
    private Context context;

    public List<Question> getAGroupOfQuesition(String nub_zu, List<CalculationTest> lists) {
        List<Question> questions = new ArrayList<Question>();
//        List<CalculationTest> list =Db_CaculationTest.getInstance(context).loadAllNote();

        List<CalculationTest> list =lists;
        Log.d("CalculationTest", JSON.toJSONString(list));
        if (nub_zu!=null) {
            for (CalculationTest e : list) {
                if (e.getItemNo().length()>10&&nub_zu.equals(e.getItemNo().substring(0, 11))) {

                    if (e.getHasStemSound()==null)
                    {
                        e.setHasStemSound(false);
                    }
                    Question question = new Question(e.getId(), e.getItemNo(), e.getStem(), e.getHasStemPic(),e.getPicName(),e.getHasStemSound(),e.getSoundName(), e.getChoiceA(), e.getChoiceB(), e.getChoiceC(), e.getChoiceD(), e.getRightAnswer());
                    questions.add(question);
                }
            }
        }
        else
        {
            for (CalculationTest e : list) {
                    Question question = new Question(e.getId(), e.getItemNo(), e.getStem(), e.getHasStemPic(),e.getPicName(),e.getHasStemSound(),e.getSoundName(), e.getChoiceA(), e.getChoiceB(), e.getChoiceC(), e.getChoiceD(), e.getRightAnswer());
                    questions.add(question);
            }
        }



        return questions;
    }



    public List<Question> getAGroupOfQuesitionDoagain(List<ErrorSubject_two> lists) {
        List<Question> questions = new ArrayList<Question>();
        List<ErrorSubject_two> list =lists;
        Log.d("CalculationTest", JSON.toJSONString(list));
        for (ErrorSubject_two e : list) {

                Question question = new Question(0, e.getItemNo(), e.getStem(), e.isHasStemPic(), e.getPicName(),e.isHasStemSound(),e.getSoundName(),e.getChoiceA(), e.getChoiceB(), e.getChoiceC(), e.getChoiceD(), e.getRightAnswer());
                questions.add(question);
            }
        return questions;
    }



}
