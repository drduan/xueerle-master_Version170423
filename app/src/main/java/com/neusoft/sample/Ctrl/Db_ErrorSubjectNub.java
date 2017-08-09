package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.ErrorSubjectNub;
import com.neusoft.sample.GreenDao.ErrorSubjectNubDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_ErrorSubjectNub {

    private static final String TAG = Db_ErrorSubjectNub.class.getSimpleName();
    private static Db_ErrorSubjectNub instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private ErrorSubjectNubDao errorSubjectNubDao;


    private Db_ErrorSubjectNub() {
    }

    public static Db_ErrorSubjectNub getInstance(Context context) {
        if (instance == null) {
            instance = new Db_ErrorSubjectNub();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.errorSubjectNubDao = instance.mDaoSession.getErrorSubjectNubDao();
        }
        return instance;
    }


    public ErrorSubjectNub loadNote(long id) {
        return errorSubjectNubDao.load(id);
    }

    public List<ErrorSubjectNub> loadAllNote(){
        return errorSubjectNubDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<ErrorSubjectNub> queryNote(String where, String... params){
        return errorSubjectNubDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(ErrorSubjectNub note){
        return errorSubjectNubDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<ErrorSubjectNub> list){
        if(list == null || list.isEmpty()){
            return;
        }
        errorSubjectNubDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    ErrorSubjectNub note = list.get(i);
                    errorSubjectNubDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        errorSubjectNubDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        errorSubjectNubDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(ErrorSubjectNub note){
        errorSubjectNubDao.delete(note);
    }

}
