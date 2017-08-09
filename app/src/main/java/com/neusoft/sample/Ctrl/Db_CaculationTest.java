package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.CalculationTest;
import com.neusoft.sample.GreenDao.CalculationTestDao;
import com.neusoft.sample.GreenDao.DaoSession;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_CaculationTest {

    private static final String TAG = Db_CaculationTest.class.getSimpleName();
    private static Db_CaculationTest instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private CalculationTestDao calculationTestDao;


    private Db_CaculationTest() {
    }

    public static Db_CaculationTest getInstance(Context context) {
        if (instance == null) {
            instance = new Db_CaculationTest();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.calculationTestDao = instance.mDaoSession.getCalculationTestDao();
        }
        return instance;
    }


    public CalculationTest loadNote(long id) {
        return calculationTestDao.load(id);
    }

    public List<CalculationTest> loadAllNote(){
        return calculationTestDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<CalculationTest> queryNote(String where, String... params){
        return calculationTestDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(CalculationTest note){
        return calculationTestDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<CalculationTest> list){
        if(list == null || list.isEmpty()){
            return;
        }
        calculationTestDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    CalculationTest note = list.get(i);
                    calculationTestDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        calculationTestDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        calculationTestDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(CalculationTest note){
        calculationTestDao.delete(note);
    }

}
