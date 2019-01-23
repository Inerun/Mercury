package com.inerun.courier.activity_auction;

import com.inerun.courier.R;
import com.inerun.courier.base.AuctionBaseActivity;
import com.ncapdevi.fragnav.FragNavController;



/**
 * Created by vinay on 03/10/17.
 */

abstract public class FullScreenBaseActivity extends AuctionBaseActivity {


    private FragNavController.Builder builder;
    private FragNavController fragNavController;

    @Override
    public int initLayout() {

        setTheme(R.style.AppTheme1);

        return R.layout.activity_fullscreen_layout;
    }

    @Override
    public void initBase() {


    }


    /**
     * @return Framelayout id in which fragment is inflated
     */
    @Override
    public int initFragmentContainer() {
        return R.id.fullScreenFragmentContainer;
    }

    /**
     * @returning null because we dont want Header Title for Full Screen Activities
     */
    @Override
    public String getHeaderTitle() {
        return null;
    }

    /**
     * @returning 0 because we dont want Top Right Menu for Full Screen Activities
     */
    @Override
    protected int getMenuLayout() {
        return 0;
    }
}
