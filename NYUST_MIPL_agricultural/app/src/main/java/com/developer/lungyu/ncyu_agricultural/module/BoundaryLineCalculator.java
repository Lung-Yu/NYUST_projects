package com.developer.lungyu.ncyu_agricultural.module;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lungyu on 11/29/17.
 */

public class BoundaryLineCalculator {

    private static String TAG = "BoundaryLineCalculator";

    private List<Integer> point_list;
    private List<Integer> source_list;

    private int boundary_count;

    private boolean[] isPostiveMoment ;
    public BoundaryLineCalculator(List<Integer> source){
        this.source_list = source;
        init();

        markAllMoment();
        calculatePoints();
    }

    private void init(){
        this.point_list = new LinkedList<>();
        this.isPostiveMoment = new boolean[source_list.size() - 1];
        this.boundary_count = source_list.size() / 60;
    }


    private void calculatePoints(){
        int moment_count = 0;
        for(int i=0;i<isPostiveMoment.length - 1;i++){
            if(isPostiveMoment[i] == isPostiveMoment[i+1])
                moment_count++;
            else{
                if(moment_count > boundary_count)
                    this.point_list.add(i);
                moment_count = 0;
            }
        }
    }

    private void markAllMoment(){
        for(int i=0;i<source_list.size() - 1;i++){
            int differnce =source_list.get(i+1) - source_list.get(i);
            markMoment(i,differnce);
        }
    }

    private void markMoment(int index,int differnce){
        if(differnce > 0)
            isPostiveMoment[index] = true;
        else
            isPostiveMoment[index] = false;
    }

    private static final int group_size = 3;
    public List<Integer> getAllBoundaryIndex(){
        int size = this.point_list.size() / group_size;
        List<Integer> result = new ArrayList<>();

        int sum = 0;

        for(int i =1;i<=this.point_list.size();i++){
            if(i % size != 0){
                sum += point_list.get(i-1);
            }else {
                result.add(sum/size);
                sum = 0;
            }
        }

        return result;
    }
}
