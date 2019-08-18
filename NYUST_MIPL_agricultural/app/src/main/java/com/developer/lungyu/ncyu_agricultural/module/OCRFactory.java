package com.developer.lungyu.ncyu_agricultural.module;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Created by lungyu on 7/10/2017.
 */

public class OCRFactory {
    private static final String DEFAULT_LANGUAGE = "eng";
    private static final String CHINESE_LANGUAGE = "chi_tra";
    private static final String TESSBASE_PATH = "/storage/emulated/0/download/tesseract/";
    private static final String LUNG_WRITE_LANGUAGE = "num";
    public static String ocr(Bitmap bitmap) {
        String resString = "";

        final TessBaseAPI ocrApi = new TessBaseAPI();

        ocrApi.init(TESSBASE_PATH, LUNG_WRITE_LANGUAGE);
        ocrApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);

        ocrApi.setImage(bitmap);
        resString = ocrApi.getUTF8Text();

        ocrApi.clear();
        ocrApi.end();
        return  resString;
    }
}
