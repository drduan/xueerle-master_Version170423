package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.Notification;
import com.neusoft.sample.GreenDao.NotificationDao;

import java.util.List;


/**
 * Created by Administrator on 2016/6/27.
 */
public class Db_Notification {

    private static final String TAG = Db_Notification.class.getSimpleName();
    private static Db_Notification instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private NotificationDao notificationDao;


    private Db_Notification() {
    }

    public static Db_Notification getInstance(Context context) {
        if (instance == null) {
            instance = new Db_Notification();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.notificationDao = instance.mDaoSession.getNotificationDao();
        }
        return instance;
    }


    public Notification loadNote(long id) {
        return notificationDao.load(id);
    }

    public List<Notification> loadAllNote(){
        return notificationDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<Notification> queryNote(String where, String... params){
        return notificationDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(Notification note){
        return notificationDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<Notification> list){
        if(list == null || list.isEmpty()){
            return;
        }
        notificationDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    Notification note = list.get(i);
                    notificationDao.insertOrReplace(note);
                }
            }
        });

    }


    /**
     * delete all note
     */
    public void deleteAllNote(){
        notificationDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        notificationDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(Notification note){
        notificationDao.delete(note);
    }

}
