package com.inerun.courier.model;

import com.google.gson.annotations.SerializedName;
import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by vineet on 06/06/18.
 */
@Table(database = AppDatabase.class, name = "city")
public class City extends BaseModelClassAddress {


    @Column
    private String city_name;
    @Column
    private int country_id;
    @SerializedName("state_id")
    @Column
    private int region_id;

    public City() {
    }

    public City(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }


    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }
}
