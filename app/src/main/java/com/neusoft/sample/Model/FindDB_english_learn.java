package com.neusoft.sample.Model;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.Ctrl.Db_StudyGoodItemService;
import com.neusoft.sample.Ctrl.Db_StudyGoodTermService;
import com.neusoft.sample.Ctrl.Db_TextOneStructureService;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.GreenDao.StudyGoodItem;
import com.neusoft.sample.GreenDao.StudyGoodTerm;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class FindDB_english_learn {
   public static List<String> stringList=new ArrayList<>();

    public static    List<TextOneStructure> Get_english_learn(Context context,String user_id){
        Log.d("english_learn",user_id);
        List<TextOneStructure> string_KE_List=new ArrayList<>();
        List<User> list= Db_UserService.getInstance(context).loadAllNote();
        for (User user:list)
        {
            if (user_id.equals(user.getServer_id()))
            {
                Log.d("kejiezu_user1",user.getServer_id());
//                Log.d("kejiezu_user31",user.getProductNo().toString());
                List<StudyGoodItem> studyGoodItemList= Db_StudyGoodItemService.getInstance(context).loadAllNote();
                for (StudyGoodItem studyGoodItem:studyGoodItemList)
                {
                    if (user.getProductNo().equals(studyGoodItem.getProductId())&&studyGoodItem.getBookNo().substring(0,1).equals("1"))
                    {
                        Log.d("kejiezu_user2",studyGoodItem.getProductId());
                        List<StudyGoodTerm> studyGoodTermList= Db_StudyGoodTermService.getInstance(context).loadAllNote();
                        for (StudyGoodTerm studyGoodTerm:studyGoodTermList)
                        {
                            Log.d("kejiezu_user3go",studyGoodItem.getBookNo().substring(0,1));
                            //这里的“1”是口算题卡
                            if (studyGoodItem.getBookNo().equals(studyGoodTerm.getBookNo()))
                            {

                                Log.d("kejiezu_user3",studyGoodTerm.getBookNo());
                               List<TextOneStructure> textOneStructureList= Db_TextOneStructureService.getInstance(context).loadAllNote();
                                for (TextOneStructure textOneStructure:textOneStructureList)
                                {
                                    if (studyGoodItem.getBookNo().equals(textOneStructure.getChapterNo().substring(0,5)))
                                    {
                                        string_KE_List.add(textOneStructure);
                                        stringList.add(textOneStructure.getChapterNo());
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }

            }
        }
        //TODO 这里排除多用户一级结构重复性

        Log.d("kejiezu_list",string_KE_List.toString());

    return string_KE_List;

    }
}
