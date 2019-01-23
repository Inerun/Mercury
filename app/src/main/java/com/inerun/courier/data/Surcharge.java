package com.inerun.courier.data;

/**
 * Created by vineet on 16/01/19.
 */

public class Surcharge {
    private String id;
    private String surcharge_name;

    public Surcharge(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurcharge_name() {
        return surcharge_name;
    }

    public void setSurcharge_name(String surcharge_name) {
        this.surcharge_name = surcharge_name;
    }

    @Override
    public boolean equals(Object obj) {
        return getId().equals(((Surcharge)obj).getId());
    }
}
