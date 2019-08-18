package com.developer.lungyu.ncyu_agricultural.datamodel;

/**
 * Created by lungyu on 11/29/17.
 */

public class ImageSubDataModel {
    private int start;
    private int end;

    public int getDifference(){
        return end - start;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
