package com.inerun.courier.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vineet on 9/12/2017.
 */

public class BaseModelClassAddress extends BaseModel {
    @Column
    @Unique
    @PrimaryKey(autoincrement = false)
    public int id=-1;

    public static final int SYNC_STATUS_UPDATE_PENDING=4;
    public static final int SYNC_STATUS_PENDING=3;
    public static final int SYNC_STATUS_SUCCESS=1;
    public static final int SYNC_STATUS_FAILED=2;

//    @Column
//    @Unique
//    @PrimaryKey(autoincrement = true)
//    private int localid;

    @Column(defaultValue = "1")
    private int status;

    @Column
    private Date created_on;

    @Column
    private Date modified_on;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }public void generateId() {
//        this.id = System.currentTimeMillis();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public void setCreated_on(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        sf.setLenient(true);
        try {
            this.created_on = sf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public int getLocalid() {
//        return localid;
//    }
//
//    public void setLocalid(int localid) {
//        this.localid = localid;
//    }

    public Date getModified_on() {
        return modified_on;
    }

    public void setModified_on(Date modified_on) {
        this.modified_on = modified_on;
    }

    public void setModified_on(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        sf.setLenient(true);
        try {
            this.modified_on = sf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
