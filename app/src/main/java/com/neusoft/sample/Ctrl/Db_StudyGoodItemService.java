package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.StudyGoodItem;
import com.neusoft.sample.GreenDao.StudyGoodItemDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_StudyGoodItemService {

    private static final String TAG = Db_StudyGoodItemService.class.getSimpleName();
    private static Db_StudyGoodItemService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private StudyGoodItemDao studyGoodItemDao;


    private Db_StudyGoodItemService() {
    }

    public static Db_StudyGoodItemService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_StudyGoodItemService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.studyGoodItemDao = instance.mDaoSession.getStudyGoodItemDao();
        }
        return instance;
    }


    public StudyGoodItem loadNote(long id) {
        return studyGoodItemDao.load(id);
    }

    public List<StudyGoodItem> loadAllNote(){
        return studyGoodItemDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<StudyGoodItem> queryNote(String where, String... params){
        return studyGoodItemDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(StudyGoodItem note){
        return studyGoodItemDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<StudyGoodItem> list){
        if(list == null || list.isEmpty()){
            return;
        }
        studyGoodItemDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    StudyGoodItem note = list.get(i);
                    studyGoodItemDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        studyGoodItemDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        studyGoodItemDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(StudyGoodItem note){
        studyGoodItemDao.delete(note);
    }

}
