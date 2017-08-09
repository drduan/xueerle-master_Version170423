package com.neusoft.sample.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by duanxudong on 16/5/26.
 */

public class BaseActivity extends AppCompatActivity {

    final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.xel.www/";//要删除的pdf路径
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDarkStatusIcon(true);
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setDarkStatusIcon(boolean bDark) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decorView = getWindow().getDecorView();
            if(decorView != null){
                int vis = decorView.getSystemUiVisibility();
                if(bDark){
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else{
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }


    /**
     * 删除该目录下的pdf文件
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        File file=new File(SDPath);
        if (file.exists()) {
            for (File fileitem : file.listFiles()) {
                if (fileitem.isFile() && fileitem.getName().endsWith(".pdf")) {
                    fileitem.delete();
                }
            }
        }

    }


     public  String  getUserId()
     {
         return this.getSharedPreferences("User_ir", Context.MODE_PRIVATE).getString("user_id","");
//         editor.("user_id",user.getServer_id());
//         editor.putString("role", user.getRole());

     }
}
