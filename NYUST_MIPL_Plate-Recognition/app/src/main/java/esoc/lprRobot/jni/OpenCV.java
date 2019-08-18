package esoc.lprRobot.jni;

/**
 * Created by lungyu on 2016/11/21.
 */

public class OpenCV {
    static
    {
        System.loadLibrary("opencv");
    }
    public native boolean setSourceImage(int[] pixels, int width, int height);
    public native byte[] getSourceImage();
    public native void extractSURFFeature();
    public native int[] LPD(String cascade, int[] pixels, int w, int h, int T);
    public native int[] rgb2gray(int[] pixels, int w, int h);
    public native int[] DRO(int[] pixels, int w,int h, int GAUSSIAN);
}
