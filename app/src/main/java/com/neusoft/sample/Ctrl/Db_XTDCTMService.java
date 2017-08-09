package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.XTDCTM;
import com.neusoft.sample.GreenDao.XTDCTMDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_XTDCTMService {

    private static final String TAG = Db_XTDCTMService.class.getSimpleName();
    private static Db_XTDCTMService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private XTDCTMDao xtdctmDao;


    private Db_XTDCTMService() {
    }

    public static Db_XTDCTMService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_XTDCTMService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.xtdctmDao = instance.mDaoSession.getXTDCTMDao();
        }
        return instance;
    }


    public XTDCTM loadNote(long id) {
        return xtdctmDao.load(id);
    }

    public List<XTDCTM> loadAllNote(){
        return xtdctmDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<XTDCTM> queryNote(String where, String... params){
        return xtdctmDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(XTDCTM note){
        return xtdctmDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<XTDCTM> list){
        if(list == null || list.isEmpty()){
            return;
        }
        xtdctmDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    XTDCTM note = list.get(i);
                    xtdctmDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        xtdctmDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        xtdctmDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(XTDCTM note){
        xtdctmDao.delete(note);
    }

}
