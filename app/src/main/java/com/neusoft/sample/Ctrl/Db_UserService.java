package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.GreenDao.UserDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_UserService {

    private static final String TAG = Db_UserService.class.getSimpleName();
    private static Db_UserService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private UserDao userDao;


    private Db_UserService() {
    }

    public static Db_UserService getInstance(Context context) {
        if (instance == null) {
            instance = new Db_UserService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.userDao = instance.mDaoSession.getUserDao();
        }
        return instance;
    }


    public User loadNote(long id) {
        return userDao.load(id);
    }

    public List<User> loadAllNote(){
        return userDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

//    public List<User> queryNote(String where, String... params){
//        return userDao.queryRaw(where, params);
//    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(User note){
        return userDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        userDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    User note = list.get(i);
                    userDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        userDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        userDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(User note){
        userDao.delete(note);
    }

}
