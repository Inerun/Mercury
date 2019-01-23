package com.inerun.courier.model;

import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by vineet on 06/06/18.
 */
@Table(database = AppDatabase.class, name = "whparcel_stock")
public class WhPhysicalStock extends BaseModel {

    @Column
    @Unique
    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String parcel_barcode;

    @Column(defaultValue = "1")
    private int status;

    @Column
    private Date created_on;

    public WhPhysicalStock() {
    }

    public WhPhysicalStock(String parcel_barcode) {
        this.parcel_barcode = parcel_barcode;
    }

    public String getParcel_barcode() {
        return parcel_barcode;
    }

    public void setParcel_barcode(String parcel_barcode) {
        this.parcel_barcode = parcel_barcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
