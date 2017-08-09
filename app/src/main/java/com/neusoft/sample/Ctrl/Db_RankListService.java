package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.Ranklist;
import com.neusoft.sample.GreenDao.RanklistDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_RankListService {

    private static final String TAG = Db_RankListService.class.getSimpleName();
    private static Db_RankListService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private RanklistDao ranklistDao;


    private Db_RankListService() {
    }

    public static Db_RankListService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_RankListService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.ranklistDao = instance.mDaoSession.getRanklistDao();
        }
        return instance;
    }


    public Ranklist loadNote(long id) {
        return ranklistDao.load(id);
    }

    public List<Ranklist> loadAllNote(){
        return ranklistDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<Ranklist> queryNote(String where, String... params){
        return ranklistDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(Ranklist note){
        return ranklistDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<Ranklist> list){
        if(list == null || list.isEmpty()){
            return;
        }
        ranklistDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    Ranklist note = list.get(i);
                    ranklistDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        ranklistDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        ranklistDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(Ranklist note){
        ranklistDao.delete(note);
    }

}
