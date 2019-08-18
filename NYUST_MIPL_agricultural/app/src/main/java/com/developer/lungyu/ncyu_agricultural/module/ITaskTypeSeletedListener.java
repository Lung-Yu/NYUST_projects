package com.developer.lungyu.ncyu_agricultural.module;

import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelTask;

import java.util.List;

/**
 * Created by lungyu on 11/6/17.
 */

public interface ITaskTypeSeletedListener {
    void onItemSelected(List<DataModelTask> taskItems);
}
