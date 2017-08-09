package com.neusoft.sample.Ctrl;

import android.os.AsyncTask;

import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.Model.FindDB_kejiezu;
import com.neusoft.sample.util.ContextHolder;

import java.util.List;

/**
 * Created by wangyujie on 2016/9/3.
 */
public class DataLoaderAsyncTask extends AsyncTask<List<TextOneStructure>,Integer, List<TextOneStructure>>{
    @Override
    protected List<TextOneStructure> doInBackground(List<TextOneStructure>... params) {
        List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(ContextHolder.getContext(), null, "1");



        return Get_ke;
    }

    @Override
    protected void onPostExecute(List<TextOneStructure> textOneStructures) {
        super.onPostExecute(textOneStructures);




    }
}
