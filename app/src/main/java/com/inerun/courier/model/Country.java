package com.inerun.courier.model;

import com.google.gson.annotations.SerializedName;
import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by vineet on 06/06/18.
 */
@Table(database = AppDatabase.class, name = "country")
public class Country extends BaseModelClassAddress {


    @Column
    private String country_name;
    @Column
    private int country_code;
    @Column
    private int currency_for_frontend;
    @Column
    private int currency_for_ccount;
    @Column
    private String time_zone;
    @Column
    private String gmt;
    @SerializedName("dropdown")
    @Column
    private int isdropdown;


    public Country() {
    }

    public Country(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public int getCountry_code() {
        return country_code;
    }

    public void setCountry_code(int country_code) {
        this.country_code = country_code;
    }

    public int getCurrency_for_frontend() {
        return currency_for_frontend;
    }

    public void setCurrency_for_frontend(int currency_for_frontend) {
        this.currency_for_frontend = currency_for_frontend;
    }

    public int getCurrency_for_ccount() {
        return currency_for_ccount;
    }

    public void setCurrency_for_ccount(int currency_for_ccount) {
        this.currency_for_ccount = currency_for_ccount;
    }



    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }

    public int getIsdropdown() {
        return isdropdown;
    }

    public void setIsdropdown(int isdropdown) {
        this.isdropdown = isdropdown;
    }

    public boolean isDropdown() {
        return isdropdown == 0 ? false : true;
    }

}
