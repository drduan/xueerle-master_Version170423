package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextOneStructureDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_TextOneStructureService {

    private static final String TAG = Db_TextOneStructureService.class.getSimpleName();
    private static Db_TextOneStructureService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private TextOneStructureDao textOneStructureDao;


    private Db_TextOneStructureService() {
    }

    public static Db_TextOneStructureService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_TextOneStructureService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.textOneStructureDao = instance.mDaoSession.getTextOneStructureDao();
        }
        return instance;
    }


    public TextOneStructure loadNote(long id) {
        return textOneStructureDao.load(id);
    }

    public List<TextOneStructure> loadAllNote(){
        return textOneStructureDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<TextOneStructure> queryNote(String where, String... params){
        return textOneStructureDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(TextOneStructure note){
        return textOneStructureDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<TextOneStructure> list){
        if(list == null || list.isEmpty()){
            return;
        }
        textOneStructureDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    TextOneStructure note = list.get(i);
                    textOneStructureDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        textOneStructureDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        textOneStructureDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(TextOneStructure note){
        textOneStructureDao.delete(note);
    }

}
