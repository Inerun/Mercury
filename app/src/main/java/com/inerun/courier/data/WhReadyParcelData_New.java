package com.inerun.courier.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vineet on 2/20/2017.
 */

public class WhReadyParcelData_New implements Serializable {
    private boolean Status;
    private boolean Success;
    private int total;
    private int count;
    private ArrayList<ProductData> ProductData;


    public void setStatus(boolean status) {
        Status = status;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setProductData(ArrayList<WhReadyParcelData_New.ProductData> productData) {
        ProductData = productData;
    }

    public boolean isStatus() {
        return Status;
    }

    public boolean isSuccess() {
        return Success;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<WhReadyParcelData_New.ProductData> getProductData() {
        return ProductData;
    }

    public class ProductData implements Serializable{

        private String ProductName;
        private String ProductId;


        public void setProductName(String productName) {
            ProductName = productName;
        }

        public void setProductId(String productId) {
            ProductId = productId;
        }


        public String getProductName() {
            return ProductName;
        }

        public String getProductId() {
            return ProductId;
        }
    }



}
