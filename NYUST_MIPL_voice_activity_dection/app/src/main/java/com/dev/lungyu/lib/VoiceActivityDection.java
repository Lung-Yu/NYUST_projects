package com.dev.lungyu.lib;

import android.util.Log;

import java.io.File;

/**
 * Created by lungyu on 9/17/17.
 */

public class VoiceActivityDection {


    private static final String FREQ_LEVEL_TMP_FILE = "freqlevel.txt";
    private static String FREQ_LEVEL_TMP_FILE_FILE_PATH;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public VoiceActivityDection(){
        buildLogFileForAnalysis();
    }

    private void buildLogFileForAnalysis(){
        FREQ_LEVEL_TMP_FILE_FILE_PATH = StorageUtils.getInstance().makeFilePath(FREQ_LEVEL_TMP_FILE);
        StorageUtils.getInstance().buildFile(FREQ_LEVEL_TMP_FILE_FILE_PATH);
    }

    public String getRootFreqFilePath(){
        return FREQ_LEVEL_TMP_FILE_FILE_PATH;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public int hasVoice(String filename){
        String freqfile = getRootFreqFilePath();
        String targetFile = StorageUtils.getInstance().makeFilePath(filename);
        return hasVoice(freqfile,targetFile);
    }

    private native int hasVoice(String folderpath,String filepath);


}
