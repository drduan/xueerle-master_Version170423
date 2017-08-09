package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.XTCTJL;
import com.neusoft.sample.GreenDao.XTCTJLDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_XTCTJLService {

    private static final String TAG = Db_XTCTJLService.class.getSimpleName();
    private static Db_XTCTJLService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private XTCTJLDao xtctjlDao;


    private Db_XTCTJLService() {
    }

    public static Db_XTCTJLService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_XTCTJLService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.xtctjlDao = instance.mDaoSession.getXTCTJLDao();
        }
        return instance;
    }


    public XTCTJL loadNote(long id) {
        return xtctjlDao.load(id);
    }

    public List<XTCTJL> loadAllNote(){
        return xtctjlDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<XTCTJL> queryNote(String where, String... params){
        return xtctjlDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(XTCTJL note){
        return xtctjlDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<XTCTJL> list){
        if(list == null || list.isEmpty()){
            return;
        }
        xtctjlDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    XTCTJL note = list.get(i);
                    xtctjlDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        xtctjlDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        xtctjlDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(XTCTJL note){
        xtctjlDao.delete(note);
    }

}
