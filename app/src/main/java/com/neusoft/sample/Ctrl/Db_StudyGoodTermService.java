package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.StudyGoodTerm;
import com.neusoft.sample.GreenDao.StudyGoodTermDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_StudyGoodTermService {

    private static final String TAG = Db_StudyGoodTermService.class.getSimpleName();
    private static Db_StudyGoodTermService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private StudyGoodTermDao studyGoodTermDao;


    private Db_StudyGoodTermService() {
    }

    public static Db_StudyGoodTermService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_StudyGoodTermService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.studyGoodTermDao = instance.mDaoSession.getStudyGoodTermDao();
        }
        return instance;
    }


    public StudyGoodTerm loadNote(long id) {
        return studyGoodTermDao.load(id);
    }

    public List<StudyGoodTerm> loadAllNote(){
        return studyGoodTermDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<StudyGoodTerm> queryNote(String where, String... params){
        return studyGoodTermDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(StudyGoodTerm note){
        return studyGoodTermDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<StudyGoodTerm> list){
        if(list == null || list.isEmpty()){
            return;
        }
        studyGoodTermDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    StudyGoodTerm note = list.get(i);
                    studyGoodTermDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        studyGoodTermDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        studyGoodTermDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(StudyGoodTerm note){
        studyGoodTermDao.delete(note);
    }

}
