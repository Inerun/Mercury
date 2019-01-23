package com.inerun.courier.model;

import java.util.List;

/**
 * Created by vinay on 13/12/17.
 */

public class SyncData {
    List<AuctionItem> auctionitemdata;
    List<AuctionInvoice> auctioninvoicesdata;
    SiteConfiguration siteconfiguration;
    String device_name = "";
    String androidId = "";
    String userid = "";

    public SyncData() {
    }

    public SyncData(List<AuctionItem> auctionitemdata, List<AuctionInvoice> auctioninvoicesdata, SiteConfiguration siteconfiguration, String device_name, String androidId, String userid) {
        this.auctionitemdata = auctionitemdata;
        this.auctioninvoicesdata = auctioninvoicesdata;
        this.siteconfiguration = siteconfiguration;
        this.device_name = device_name;
        this.androidId = androidId;
        this.userid = userid;
    }

    public List<AuctionItem> getAuctionitemdata() {
        return auctionitemdata;
    }

    public void setAuctionitemdata(List<AuctionItem> auctionitemdata) {
        this.auctionitemdata = auctionitemdata;
    }

    public List<AuctionInvoice> getAuctioninvoicesdata() {
        return auctioninvoicesdata;
    }

    public void setAuctioninvoicesdata(List<AuctionInvoice> auctioninvoicesdata) {
        this.auctioninvoicesdata = auctioninvoicesdata;
    }

    public SiteConfiguration getSiteconfiguration() {
        return siteconfiguration;
    }

    public void setSiteconfiguration(SiteConfiguration siteconfiguration) {
        this.siteconfiguration = siteconfiguration;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
