package com.inerun.courier.model;

import java.util.List;

/**
 * Created by vinay on 06/11/17.
 */

public class SyncResponseBo {


    List<AuctionInvoice> auctioninvoicesdata;
    List<AuctionItem> auctionitemdata;

    AuctionDetail auctiondetail;
    SiteConfiguration siteconfiguration;

    public List<AuctionInvoice> getAuctioninvoicesdata() {
        return auctioninvoicesdata;
    }

    public List<AuctionItem> getAuctionitemdata() {
        return auctionitemdata;
    }

    public AuctionDetail getAuctiondetail() {
        return auctiondetail;
    }

    public SiteConfiguration getSiteconfiguration() {
        return siteconfiguration;
    }
}
