package com.inerun.courier.data;

import java.io.Serializable;

/**
 * Created by vineet on 2/18/2017.
 */

public class CustomerExecutiveData implements Serializable {
    private String id;
    private String username;

    public CustomerExecutiveData(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
