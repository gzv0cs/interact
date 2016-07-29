package com.navi.interact.tools.wrapper;

import java.util.HashMap;

/**
 * Created by mevan on 25/06/2016.
 */
public class DataRecord {

    private long id;
    private HashMap<String, Object> recodsData;

    public DataRecord(long id) {
        this.id = id;
        recodsData = new HashMap<String, Object>();
    }

    public long getRecordId() {
        return id;
    }

    public void add(String columnName, Object data) {
        recodsData.put(columnName, data);
    }

    public Object getColumnData(String columnName) {
        return recodsData.get(columnName);
    }
}

