package com.mipl.lungyu.licenseplaterecognition.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by lungyu on 2016/11/14.
 */

public class FileOption {
    //從sdcard讀檔
    public static String readFromSDcard(String fileFullPath){
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(fileFullPath);
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fin = new FileInputStream(file);
            byte[] data = new byte[fin.available()];
            while (fin.read(data) != -1) {
                sb.append(new String(data));
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
