package com.neusoft.sample.View.AoSaiTest;


import android.content.Context;

import com.neusoft.sample.Model.aosai_zu_Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanxudong on 16/5/21.
 */
public class Aosai_QuestionLib {
    private Context context;

    public List<Aosai_Question> getAGroupOfQuesition(String nub_zu, List<aosai_zu_Entity> lists) {

        List<Aosai_Question> questions = new ArrayList<>();
        for (aosai_zu_Entity list : lists) {
            Aosai_Question aosai_question = new Aosai_Question();
            aosai_question.setChoiceA(list.getChoiceA());
            aosai_question.setChoiceB(list.getChoiceB());
            aosai_question.setChoiceC(list.getChoiceC());
            aosai_question.setChoiceD(list.getChoiceD());
            aosai_question.setItemNo(list.getItemNo());
            aosai_question.setRightAnswer(list.getRightAnswer());
            aosai_question.setStem(list.getStem());
            aosai_question.setSummary(list.getSummary());
            aosai_question.setUsedAnaSumPDF(list.getUsedAnaSumPDF());
            aosai_question.setUsedStemPDF(list.getUsedStemPDF());
            aosai_question.setXiangXiJiexi(list.getXiangXiJiexi());
            aosai_question.setStemPic(list.getIsStemPic());
            aosai_question.setStemPicName(list.getStemPicName());
            questions.add(aosai_question);
        }


        return questions;
    }


}
