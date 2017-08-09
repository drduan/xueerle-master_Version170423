package com.neusoft.sample.Ctrl.wenchengcheng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by AstroBoy on 2016/11/18.
 */

public class PkgSilentInstall {

    /**
     * 软件静默安装
     * @param apkAbsolutePath apk文件所在路径
     * @return 安装结果:获取到的result值<br>
     *
     * 如果安装成功的话是“
     * pkg: /data/local/tmp/Calculator.apk  /nSuccess”，<br>
     * 如果是失败的话，则没有结尾的“Success”。
     */
    public String silentInstall(String apkAbsolutePath) {
        String[] args = {"pm","install","-r",apkAbsolutePath};
        String result ="";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write("/n".getBytes());
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

}
