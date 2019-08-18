package com.developer.lungyu.ncyu_agricultural.datamodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lungyu on 11/3/17.
 */

public class DataModelTaskTypes {
    private String taskTypeId;
    private String taskTypeName;
    private List<DataModelTask> items;

    public String getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public List<DataModelTask> getTaskItems() {
        return items;
    }

    public void setTaskItems(List<DataModelTask> items) {
        this.items = items;
    }
}
