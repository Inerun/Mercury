package com.inerun.courier.model;

import com.google.gson.annotations.SerializedName;
import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by vineet on 06/06/18.
 */
@Table(database = AppDatabase.class, name = "region")
public class Region extends BaseModelClassAddress {

    @SerializedName("state_name")
    @Column
    private String region_name;
    @Column
    private int country;

    public Region() {
    }

    public Region(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }
}
