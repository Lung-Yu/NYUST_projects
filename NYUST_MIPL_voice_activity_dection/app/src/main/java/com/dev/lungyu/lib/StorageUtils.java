package com.dev.lungyu.lib;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lungyu on 9/17/17.
 */

public class StorageUtils {

    private static final String APP_FOLDER_NAME = "baby_vad";
    private File _AppFolder;

    //Design pattern :: Singleton Pattern
    private static StorageUtils _thisObj;
    public static StorageUtils getInstance(){
        if(_thisObj == null){
            _thisObj = new StorageUtils();
        }
        return  _thisObj;
    }

    private StorageUtils(){
        loadFolderPath();
        buildAllFolder(_AppFolder);
    }


    public String makeFilePath(String filename){
        return _AppFolder.getAbsolutePath()+ "/" + filename;
    }

    public void make_sample(){
        String filename = makeFilePath("myTest.txt");
        buildFile(filename);
    }

    private void loadFolderPath(){
        File vSDCard = Environment.getExternalStorageDirectory();
        String path = vSDCard.getAbsolutePath() + "/" + APP_FOLDER_NAME + "/";
        _AppFolder = new File(path);
    }


    private boolean hasSDCard(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED))
            return false;
        else return true;
    }

    public boolean buildFile(String filename){
        try {
            File f = new File(filename);

            if(!f.getParentFile().exists())
                f.getParentFile().mkdir();

            if(!f.exists())
                f.createNewFile();

            return  true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void buildAllFolder(File f){

        if(!f.getParentFile().exists()){
            buildAllFolder(f.getParentFile());
        }else if(!f.exists())
            f.mkdir();
    }
}
