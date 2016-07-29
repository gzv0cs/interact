package com.navi.interact.tools.entity;

import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mevan on 25/05/2016.
 */
public class BaseEntity<T> implements BaseEntityType,Cloneable {

    private String cretedBy;
    private Date createdDateTime;
    private String modifiedBy;
    private Date modifiedDateTime;

    protected BaseEntity() {
        this.cretedBy = "system";
        this.createdDateTime = Calendar.getInstance().getTime();
    }





}

