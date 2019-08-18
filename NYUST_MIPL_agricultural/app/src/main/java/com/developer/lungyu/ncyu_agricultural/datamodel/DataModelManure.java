package com.developer.lungyu.ncyu_agricultural.datamodel;

/**
 * Created by lungyu on 11/3/17.
 */

public class DataModelManure {
    private String code;
    private String name;
    private String type;
    private String interval;
    private String unitQty;
    private String diluteMultiple;
    private String Usage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(String unitQty) {
        this.unitQty = unitQty;
    }

    public String getDiluteMultiple() {
        return diluteMultiple;
    }

    public void setDiluteMultiple(String diluteMultiple) {
        this.diluteMultiple = diluteMultiple;
    }

    public String getUsage() {
        return Usage;
    }

    public void setUsage(String usage) {
        Usage = usage;
    }
}
