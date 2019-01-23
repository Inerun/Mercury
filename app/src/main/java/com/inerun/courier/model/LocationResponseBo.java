package com.inerun.courier.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinay on 06/11/17.
 */

public class LocationResponseBo {


    List<Country> country;
    @SerializedName("state")
    List<Region> region;

    List<City> city;


    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public List<Region> getRegion() {
        return region;
    }

    public void setRegion(List<Region> region) {
        this.region = region;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

}