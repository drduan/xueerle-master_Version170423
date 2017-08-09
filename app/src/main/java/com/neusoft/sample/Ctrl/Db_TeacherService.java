package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.teacher;
import com.neusoft.sample.GreenDao.teacherDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_TeacherService {

    private static final String TAG = Db_TeacherService.class.getSimpleName();
    private static Db_TeacherService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private teacherDao teacherDao;


    private Db_TeacherService() {
    }

    public static Db_TeacherService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_TeacherService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.teacherDao = instance.mDaoSession.getTeacherDao();
        }
        return instance;
    }


    public teacher loadNote(long id) {
        return teacherDao.load(id);
    }

    public List<teacher> loadAllNote(){
        return teacherDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<teacher> queryNote(String where, String... params){
        return teacherDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(teacher note){
        return teacherDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<teacher> list){
        if(list == null || list.isEmpty()){
            return;
        }
        teacherDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    teacher note = list.get(i);
                    teacherDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        teacherDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        teacherDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(teacher note){
        teacherDao.delete(note);
    }

}
