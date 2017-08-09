package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.XTCSGJ;
import com.neusoft.sample.GreenDao.XTCSGJDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_XTCSGJService {

    private static final String TAG = Db_XTCSGJService.class.getSimpleName();
    private static Db_XTCSGJService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private XTCSGJDao xtcsgjDao;


    private Db_XTCSGJService() {
    }

    public static Db_XTCSGJService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_XTCSGJService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.xtcsgjDao = instance.mDaoSession.getXTCSGJDao();
        }
        return instance;
    }


    public XTCSGJ loadNote(long id) {
        return xtcsgjDao.load(id);
    }

    public List<XTCSGJ> loadAllNote(){
        return xtcsgjDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<XTCSGJ> queryNote(String where, String... params){
        return xtcsgjDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(XTCSGJ note){
        return xtcsgjDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<XTCSGJ> list){
        if(list == null || list.isEmpty()){
            return;
        }
        xtcsgjDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    XTCSGJ note = list.get(i);
                    xtcsgjDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        xtcsgjDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        xtcsgjDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(XTCSGJ note){
        xtcsgjDao.delete(note);
    }

}
