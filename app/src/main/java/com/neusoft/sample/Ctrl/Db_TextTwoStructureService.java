package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.TextTwoStructureDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_TextTwoStructureService {

    private static final String TAG = Db_TextTwoStructureService.class.getSimpleName();
    private static Db_TextTwoStructureService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private TextTwoStructureDao textTwoStructureDao;


    private Db_TextTwoStructureService() {
    }

    public static Db_TextTwoStructureService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_TextTwoStructureService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.textTwoStructureDao = instance.mDaoSession.getTextTwoStructureDao();
        }
        return instance;
    }


    public TextTwoStructure loadNote(long id) {
        return textTwoStructureDao.load(id);
    }

    public List<TextTwoStructure> loadAllNote(){
        return textTwoStructureDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<TextTwoStructure> queryNote(String where, String... params){
        return textTwoStructureDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(TextTwoStructure note){
        return textTwoStructureDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<TextTwoStructure> list){
        if(list == null || list.isEmpty()){
            return;
        }
        textTwoStructureDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    TextTwoStructure note = list.get(i);
                    textTwoStructureDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        textTwoStructureDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        textTwoStructureDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(TextTwoStructure note){
        textTwoStructureDao.delete(note);
    }

}
