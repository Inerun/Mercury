package com.inerun.courier.activity_auction;


import com.inerun.courier.R;
import com.inerun.courier.base.AuctionBaseActivity;

/**
 * Created by vinay on 24/05/18.
 */

public class AuctionHomeActivity extends AuctionBaseActivity{
    @Override
    public String getHeaderTitle() {
//        if(isValidString(getApp().appData.getStation().getStation_name())) {
//            return getApp().appData.getStation().getStation_name();
//        }else
//        {
            return getString(R.string.default_auction_title);
//        }
    }


    @Override
    public int initLayout() {

//        return R.layout.activity_main_with_auction;
        return R.layout.activity_main_wo_navigation;
    }

    @Override
    public void initView() {

//        setUpFabMenu();

    }

    @Override
    protected int getMenuLayout() {
        return -1;
    }



}
