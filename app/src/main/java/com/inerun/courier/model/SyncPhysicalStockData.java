package com.inerun.courier.model;

import java.util.List;

/**
 * Created by vinay on 13/12/17.
 */

public class SyncPhysicalStockData {
    List<WhPhysicalStock> whPhysicalStockList;
    String userid = "";


    public SyncPhysicalStockData(List<WhPhysicalStock> whPhysicalStockList, String userid) {
        this.whPhysicalStockList = whPhysicalStockList;

        this.userid = userid;
    }
}
