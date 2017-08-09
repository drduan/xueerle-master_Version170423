package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.XTCSJG;
import com.neusoft.sample.GreenDao.XTCSJGDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_XTCSJGService {

    private static final String TAG = Db_XTCSJGService.class.getSimpleName();
    private static Db_XTCSJGService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private XTCSJGDao XTCSJGDao;


    private Db_XTCSJGService() {
    }

    public static Db_XTCSJGService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_XTCSJGService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.XTCSJGDao = instance.mDaoSession.getXTCSJGDao();
        }
        return instance;
    }


    public XTCSJG loadNote(long id) {
        return XTCSJGDao.load(id);
    }

    public List<XTCSJG> loadAllNote(){
        return XTCSJGDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<XTCSJG> queryNote(String where, String... params){
        return XTCSJGDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(XTCSJG note){
        return XTCSJGDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<XTCSJG> list){
        if(list == null || list.isEmpty()){
            return;
        }
        XTCSJGDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    XTCSJG note = list.get(i);
                    XTCSJGDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        XTCSJGDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        XTCSJGDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(XTCSJG note){
        XTCSJGDao.delete(note);
    }

}
