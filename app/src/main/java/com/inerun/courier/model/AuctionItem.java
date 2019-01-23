package com.inerun.courier.model;

import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by vineet on 30/05/18.
 */
@Table(database = AppDatabase.class, name = "auction_item_table")
public class AuctionItem extends BaseModelClass {

    @Column
    private String auction_id;
    @Column
    private String parcel_id;
    @Column
    private String parcel_barcode;
    @Column
    private String parcel_barcode_imge;
    @Column
    private String item_barcode;
    @Column
    private String item_barcode_image;
    @Column
    private String description;
    @Column
    private String comment;
    @Column
    private float map; //minimum auction price
    @Column
    private float aap; // actual auction price
    @Column
    private int print_status;
    @Column
    private int created_by;
    @Column
    private String invoice_id;
    @Column (defaultValue = ""+SYNC_STATUS_SUCCESS)
    private int sync_status;

    public AuctionItem() {
    }

    public AuctionItem(String item_barcode) {
        this.item_barcode = item_barcode;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getParcel_id() {
        return parcel_id;
    }

    public void setParcel_id(String parcel_id) {
        this.parcel_id = parcel_id;
    }

    public String getParcel_barcode() {
        return parcel_barcode;
    }

    public void setParcel_barcode(String parcel_barcode) {
        this.parcel_barcode = parcel_barcode;
    }

    public String getParcel_barcode_imge() {
        return parcel_barcode_imge;
    }

    public void setParcel_barcode_imge(String parcel_barcode_imge) {
        this.parcel_barcode_imge = parcel_barcode_imge;
    }

    public String getItem_barcode() {
        return item_barcode;
    }

    public void setItem_barcode(String item_barcode) {
        this.item_barcode = item_barcode;
    }

    public String getItem_barcode_image() {
        return item_barcode_image;
    }

    public void setItem_barcode_image(String item_barcode_image) {
        this.item_barcode_image = item_barcode_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getMap() {
        return map;
    }

    public void setMap(float map) {
        this.map = map;
    }

    public float getAap() {
        return aap;
    }

    public void setAap(float aap) {
        this.aap = aap;
    }

    public int getPrint_status() {
        return print_status;
    }

    public void setPrint_status(int print_status) {
        this.print_status = print_status;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }

    //    @Override
//    public boolean equals(Object obj) {
//        return getItem_barcode().equals(((AuctionItem)obj).getItem_barcode());
//    }
}
