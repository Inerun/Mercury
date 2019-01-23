package com.inerun.courier.data;

import java.io.Serializable;

/**
 * Created by vineet on 13/11/18.
 */

public class ParcelType implements Serializable {
    private String type_id;
    private String type_name;
    private String textfield;

    public ParcelType(String type_id) {
        this.type_id = type_id;
    }

    public ParcelType(String type_id, String type_name, String textfield) {
        this.type_id = type_id;
        this.type_name = type_name;
        this.textfield = textfield;
    }


    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getTextfield() {
        return textfield;
    }public boolean hasTextfield() {
        return (textfield!=null&&textfield.length()>0);
    }

    public void setTextfield(String textfield) {
        this.textfield = textfield;
    }


    @Override
    public boolean equals(Object obj) {
        return type_id.equals(((ParcelType)obj).getType_id());
    }
}
