package com.inerun.courier.data;

/**
 * Created by vineet on 28/09/18.
 */

public class SalesFilterData {
    private int id;
    private String name;
    private boolean isSelected;

    public SalesFilterData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
