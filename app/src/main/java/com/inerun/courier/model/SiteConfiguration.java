package com.inerun.courier.model;

import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import static com.inerun.courier.model.BaseModelClass.SYNC_STATUS_SUCCESS;

/**
 * Created by vineet on 31/05/18.
 */
@Table(database = AppDatabase.class, name = "site_configuration")
public class SiteConfiguration extends BaseModel {
    @Column
    @Unique
    @PrimaryKey(autoincrement = true)
    public int id;
    @Column
    private String auction_invoice_prefix;
    @Column
    private int last_auction_invoice_no;

    @Column
    private int lain;

    @Column (defaultValue = ""+SYNC_STATUS_SUCCESS)
    private int sync_status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuction_invoice_prefix() {
        return auction_invoice_prefix;
    }

    public void setAuction_invoice_prefix(String auction_invoice_prefix) {
        this.auction_invoice_prefix = auction_invoice_prefix;
    }

    public int getLast_auction_invoice_no() {
        return last_auction_invoice_no;
    }

    public void setLast_auction_invoice_no(int last_auction_invoice_no) {
        this.last_auction_invoice_no = last_auction_invoice_no;
    }

    public int getLain() {
        return lain;
    }

    public void setLain(int lain) {
        this.lain = lain;
    }

    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }
}
