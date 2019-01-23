package com.inerun.courier.activity_driver;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.ClickListener;

/**
 * Created by vinay on 13/01/17.
 */

abstract public class  FragmentBaseActivity extends BaseActivity {
    private Toolbar toolbar;
    public NavigationView filterNavView;
    public DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    public int customSetContentView() {
        return R.layout.activity_cordinator_container;
    }

    @Override
    public void customOnCreate(Bundle savedInstanceState) {
//        getSupportFragmentManager().beginTransaction().replace(R.id.container,
//                getFragment()).commit();

        initFragmewnt();
        setToolbarTitle();
        setUpDrawer();
        setToolbarBackArrow();



    }

    protected void initFragmewnt(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                getFragment()).commit();
    }


    protected void setToolbarBackArrow() {
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIcon(getDrawable(this,R.mipmap.back_arrow_white));
        toolbar.setNavigationOnClickListener(new ClickListener(this) {
            @Override
            public void click(View view)throws Exception {
//               onBackArrowPressed();
                onBackPressed();
            }
        });



    }

//    private void onBackArrowPressed() {
//        handleFragmentBackPressed();
//    }

    private void setToolbarTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle(toolBarTitle());
        }
    } private void setToolbarTitle(int resid) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle(resid);
        }
    }

    public int toolBarTitle() {
        return R.string.app_name;
    }


//    @Override
//    public void onBackPressed() {
////        handleFragmentBackPressed();
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    abstract public Fragment getFragment();

    private void setUpDrawer() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        filterNavView = (NavigationView) findViewById(R.id.filternav_view);



    }

    public CourierApplication getApp() {
        return (CourierApplication) getApplication();
    }


    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.END)) {
            this.drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }



}
