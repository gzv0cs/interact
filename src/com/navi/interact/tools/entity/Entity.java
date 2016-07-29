package com.navi.interact.tools.entity;

import com.navi.interact.tools.util.DBUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mevan on 30/05/2016.
 */
public class Entity extends BaseEntity {

    private String createdBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;

    public Entity() {
        this.createdDateTime = Calendar.getInstance().getTime();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Date getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
