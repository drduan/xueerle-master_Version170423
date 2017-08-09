package com.neusoft.sample.Ctrl;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

/**
 * Created by wangyujie on 2016/8/29.
 */
public class FTPnewStatic {
    public static  void FtpnewInstance(final String downloadFileName){

        final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file=new File(SDPath+File.separator+"www.xel.com");
        if (!file.exists())
        {
            file.mkdirs();
        }

        final File files=new File(file.getPath(),downloadFileName);
        Log.d("files----SS",""+files.getAbsolutePath());
        if (!files.exists())
        {
            try {
                files.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final FTPClient client = new FTPClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!client.isConnected()) {
                        client.connect("122.156.218.189", 6199);
                        client.login("l0459", "H0459H0411h");
                    }
                    String dir = client.currentDirectory();
                    Log.d("FTP----DIR",dir);
                    client.download(dir+File.separator+downloadFileName, files);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FTPIllegalReplyException e) {
                    e.printStackTrace();
                } catch (FTPException e) {
                    e.printStackTrace();
                } catch (FTPAbortedException e) {
                    e.printStackTrace();
                } catch (FTPDataTransferException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
//删除文件夹下所有东西
    private static boolean FTPdeleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = FTPdeleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
