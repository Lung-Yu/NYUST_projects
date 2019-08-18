package com.developer.lungyu.ncyu_agricultural;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lungyu on 2017/5/18.
 */

public class QrcodeMaker {
    private static final int WIDTH = 1000;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, WIDTH, WIDTH,getHitnts());
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }

    private static Map<EncodeHintType,String> getHitnts(){
        Map<EncodeHintType,String> hints = new TreeMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        return  hints;
    }
}
