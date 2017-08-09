package com.neusoft.sample.Model;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.Ctrl.Db_TextOneStructureService;
import com.neusoft.sample.Ctrl.Db_TextTwoStructureService;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class FindDB_kejiezu {
    public static List<String> stringList = new ArrayList<>();

    public static List<TextOneStructure> Get_ke(final Context context, String user_id, final String nub) {

        final List<TextOneStructure> string_KE_List = new ArrayList<>();
        string_KE_List.clear();

                List<TextOneStructure> textOneStructureList = Db_TextOneStructureService.getInstance(context).loadAllNote();
                for (TextOneStructure textOneStructure : textOneStructureList) {
                    if (nub.equals(textOneStructure.getChapterNo().substring(0, 1))) {
                        string_KE_List.add(textOneStructure);
                    }
                }
        return string_KE_List;
    }

    public static List<TextTwoStructure> Get_jie(Context context, String ChapterNo) {
        List<TextTwoStructure> textTwoStructures = new ArrayList<>();

        List<TextTwoStructure> textTwoStructureList = Db_TextTwoStructureService.getInstance(context).loadAllNote();


        for (TextTwoStructure textTwoStructure : textTwoStructureList) {
            if (ChapterNo.equals(textTwoStructure.getSectionNo().substring(0, 7)) && !"00".equals(textTwoStructure.getSectionNo().substring(7, 9))) {
                Log.d("textTwoStructure", textTwoStructure.getSectionNo());
                textTwoStructures.add(textTwoStructure);
            }
        }

        //TODO  这里排除多用户二级结构重复性

        return textTwoStructures;
    }


}
