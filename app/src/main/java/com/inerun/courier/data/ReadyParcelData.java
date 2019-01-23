package com.inerun.courier.data;

import java.io.Serializable;

/**
 * Created by vineet on 2/18/2017.
 */

public class ReadyParcelData implements Serializable {
    private String barcode;

    public ReadyParcelData(String barcode) {
        this.barcode = barcode;
    }
}
