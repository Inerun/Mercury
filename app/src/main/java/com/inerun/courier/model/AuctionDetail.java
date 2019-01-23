package com.inerun.courier.model;

import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by vineet on 31/05/18.
 */
@Table(database = AppDatabase.class, name = "auction")
public class AuctionDetail extends BaseModelClass {
    @Column
    private String auction_name;
    @Column
    private String auction_description;
    @Column
    private String start_date;
    @Column
    private String end_date;
    @Column
    private int is_deleted;
    @Column
    private String created_by;

    public String getAuction_name() {
        return auction_name;
    }

    public void setAuction_name(String auction_name) {
        this.auction_name = auction_name;
    }

    public String getAuction_description() {
        return auction_description;
    }

    public void setAuction_description(String auction_description) {
        this.auction_description = auction_description;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
