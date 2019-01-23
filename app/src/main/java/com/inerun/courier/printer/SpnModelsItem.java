package com.inerun.courier.printer;

public class SpnModelsItem {
    private String mModelName = "";
    private int mModelConstant = 0;
    private String mModelStringConstant = "";

    public SpnModelsItem(String modelName, int modelConstant) {
        mModelName = modelName;
        mModelConstant = modelConstant;
    }

    public SpnModelsItem(String mModelName, int mModelConstant, String mModelStringConstant) {
        this.mModelStringConstant = mModelStringConstant;
        this.mModelName = mModelName;
        this.mModelConstant = mModelConstant;
    }


    public int getModelConstant() {
        return mModelConstant;
    }

    @Override
    public String toString() {
        return mModelName;
    }

    @Override
    public boolean equals(Object obj) {
        SpnModelsItem a = (SpnModelsItem) obj;
        return (getModelConstant()==((SpnModelsItem)obj).getModelConstant());
    }

    public String getmModelName() {
        return mModelName;
    }

    public String getmModelStringConstant() {
        return mModelStringConstant;
    }
}
