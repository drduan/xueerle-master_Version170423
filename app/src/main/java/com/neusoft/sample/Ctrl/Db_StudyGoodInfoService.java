package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.StudyGoodInfo;
import com.neusoft.sample.GreenDao.StudyGoodInfoDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_StudyGoodInfoService {

    private static final String TAG = Db_StudyGoodInfoService.class.getSimpleName();
    private static Db_StudyGoodInfoService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private StudyGoodInfoDao studyGoodInfoDao;


    private Db_StudyGoodInfoService() {
    }

    public static Db_StudyGoodInfoService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_StudyGoodInfoService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.studyGoodInfoDao = instance.mDaoSession.getStudyGoodInfoDao();
        }
        return instance;
    }


    public StudyGoodInfo loadNote(long id) {
        return studyGoodInfoDao.load(id);
    }

    public List<StudyGoodInfo> loadAllNote(){
        return studyGoodInfoDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<StudyGoodInfo> queryNote(String where, String... params){
        return studyGoodInfoDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(StudyGoodInfo note){
        return studyGoodInfoDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<StudyGoodInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        studyGoodInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    StudyGoodInfo note = list.get(i);
                    studyGoodInfoDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        studyGoodInfoDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        studyGoodInfoDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(StudyGoodInfo note){
        studyGoodInfoDao.delete(note);
    }

}
