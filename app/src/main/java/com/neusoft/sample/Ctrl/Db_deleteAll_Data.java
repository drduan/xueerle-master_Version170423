package com.neusoft.sample.Ctrl;

import android.content.Context;

/**
 * Created by wangyujie on 2016/9/17.
 */
public class Db_deleteAll_Data {
    public static  void DeleteAll(Context context)
    {
       Db_CaculationTest.getInstance(context).deleteAllNote();
        Db_ErrorSubjectNub.getInstance(context).deleteAllNote();
        Db_RankListService.getInstance(context).deleteAllNote();
        Db_StudyGoodInfoService.getInstance(context).deleteAllNote();
        Db_StudyGoodItemService.getInstance(context).deleteAllNote();
        Db_StudyGoodTermService.getInstance(context).deleteAllNote();
        Db_TextOneStructureService.getInstance(context).deleteAllNote();
        Db_TeacherService.getInstance(context).deleteAllNote();
        Db_UserService.getInstance(context).deleteAllNote();
        Db_XTCSGJService.getInstance(context).deleteAllNote();
        Db_XTCSJGService.getInstance(context).deleteAllNote();
        Db_XTCTJLService.getInstance(context).deleteAllNote();
        Db_XTDCTMService.getInstance(context).deleteAllNote();
        Db_UserService.getInstance(context).deleteAllNote();
        Db_TextTwoStructureService.getInstance(context).deleteAllNote();

    }


}
