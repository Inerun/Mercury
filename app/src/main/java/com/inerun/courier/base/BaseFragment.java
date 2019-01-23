package com.inerun.courier.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.activity_driver.FragmentBaseActivity;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.helper.DIHelper;
import com.victor.loading.rotate.RotateLoading;

import cn.pedant.SweetAlert.SweetAlertDialog;


abstract public class BaseFragment extends Fragment {
    View root;
    BaseActivity currentActivity;
    FragmentBaseActivity currentFragmentActivity;


    boolean showBackArrow = false;
    private SweetAlertDialog.OnSweetClickListener dailog_listener = new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {

        }


    };
    private RotateLoading progress;
    private Toolbar toolbar;
    private RelativeLayout progress_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = null;
        inflater = (LayoutInflater) activity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        int layout_id = inflateView();
        if (layout_id != 0) {
            root = inflater.inflate(layout_id, null);
            try {


                toolbar = (Toolbar) activity().findViewById(R.id.toolbar);
                progress_layout = (RelativeLayout) activity().findViewById(R.id.progressbar_layout);
                progress = (RotateLoading) activity().findViewById(R.id.progressBar);
                customOnCreateView(root, inflater, container, savedInstanceState);
                Log.i("showBackArrow", "" + showBackArrow);
                if (toolbar != null) {
                    if (showBackArrow) {


                        toolbar.setNavigationIcon(R.mipmap.back_arrow_white);
                    } else {
                        toolbar.setNavigationIcon(null);
                        toolbar.setNavigationOnClickListener(backarrowclick);
                    }
                } else {
                    Log.e("BaseFragment", "toolbar is not available in activity ,cannot set back arrow");
                }

            } catch (Exception e) {
                e.printStackTrace();
                SweetAlertUtil.showDialogwithNeutralButton(activity(), getString(R.string.exception), e.getMessage(), getString(R.string.ok), dailog_listener).show();
            }
        } else {
            Log.i("layout error", "Layout id cannot be zero");
        }
        return root;
    }


    abstract public int inflateView();

    abstract public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception;

    protected abstract String getAnalyticsName();

    @Override
    public void onStart() {
        super.onStart();
        String name = getAnalyticsName();
        if (name != null && name.length() > 0) {
//            GAnalyticsUtil.sendScreenName(activity(), name);
        }

    }

    public void showLongToast(int res_id) {
        Toast.makeText(activity(), res_id, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(int res_id) {
        Toast.makeText(activity(), res_id, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String string) {
        Toast.makeText(activity(), string, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String string) {
        Toast.makeText(activity(), string, Toast.LENGTH_SHORT).show();
    }

    public Drawable getDrawable(int id) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            return ContextCompat.getDrawable( activity(), id);
//        } else {
//            return getResources().getDrawable(id);
//        }
        return ContextCompat.getDrawable(activity(), id);
    }

    protected void gotoActivity(Class classobj, Bundle bundle) {

        Intent intent = new Intent(activity(), classobj);
        if (bundle != null) {
            intent.putExtra(UrlConstants.KEY_DATA, bundle);
        }
        startActivity(intent);
    }

    protected void gotoActivityForResult(Class classobj, Bundle bundle, int requestcode) {

        Intent intent = new Intent(activity(), classobj);
        if (bundle != null) {
            intent.putExtra(UrlConstants.KEY_DATA, bundle);
        }
        startActivityForResult(intent, requestcode);
    }

    public void setBackground(View view, Drawable d) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(d);
        } else {
            view.setBackground(d);
        }
    }

    public Bundle getBundleFromIntent(Activity activity) {
        Intent intent = activity.getIntent();
        if (intent.hasExtra(UrlConstants.KEY_DATA)) {
            return intent.getBundleExtra(UrlConstants.KEY_DATA);
        } else {
            return null;
        }
    }

    public View getViewById(int id) {
        return root.findViewById(id);
    }

//    public void navigateToFragment(Context context, Fragment fragment) {
//        String backStateName = fragment.getClass().getName();
//
//        FragmentManager manager = ((BaseActivity) context).getSupportFragmentManager();
//        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
//
//        if (!fragmentPopped) { //fragment not in back stack, create it.
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.container, fragment);
//            ft.addToBackStack(backStateName);
//            ft.commit();
//        }
//    }

    //    public void showProgress() {
//        if (progress != null) {
//            progress.setVisibility(View.VISIBLE);
//            progress.start();
//        }
//    }
//
//    public void hideProgress() {
//        if (progress != null) {
//            progress.setVisibility(View.GONE);
//            progress.stop();
//        }
//    }
//    public void showProgress() {
//        if (progress != null) {
//            Log.i("Base", "Progress is visible");
//            progress_layout.setVisibility(View.VISIBLE);
//            progress.start();
//        } else {
//            Log.i("Base", "Progress is null");
//        }
//    }
//
//    public void hideProgress() {
//        if (progress != null) {
//            Log.i("Base", "Progress is invisible");
//            progress_layout.setVisibility(View.GONE);
//            progress.stop();
//        } else {
//            Log.i("Base", "Progress is null");
//        }
//    }

    public void showProgress() {
        if (progress != null) {
            Log.i("Base", "Progress is visible");
            if (progress_layout != null) {
                Log.i("Base", "progress_layout is visible");
                progress_layout.setVisibility(View.VISIBLE);
            }
            progress.start();
        } else {
            Log.i("Base", "Progress is null");
        }
    }

    public void hideProgress() {
        if (progress != null) {
            Log.i("Base", "Progress is invisible");
            if (progress_layout != null) {
                Log.i("Base", "progress_layout is invisible");
                progress_layout.setVisibility(View.GONE);
            }
            progress.stop();
        } else {
            Log.i("Base", "Progress is null");
        }
    }

    public void navigateToFragment(Context context, Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = ((BaseActivity) context).getSupportFragmentManager();

//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);

        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
//            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(backStateName);

            ft.commitAllowingStateLoss();
        }
    }

    public void showSnackbar(int msg) {

        Snackbar snackbar = Snackbar.make((CoordinatorLayout) activity().findViewById(R.id.root_appbar), msg, Snackbar.LENGTH_LONG);
//        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this,  R.color.colorPrimary));
//        snackbar.getView().setBackgroundResource(R.color.colorPrimary);
        snackbar.show();
    }

    public void showSnackbar(String msg) {

        Snackbar snackbar = Snackbar.make((CoordinatorLayout) activity().findViewById(R.id.root_appbar), msg, Snackbar.LENGTH_LONG);
//        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this,  R.color.colorPrimary));
//        snackbar.getView().setBackgroundResource(R.color.colorPrimary);
        snackbar.show();
    }

    public boolean isStringValid(String string) {
        if (string != null && string.length() > 0) {
            return true;
        }
        return false;
    }

    public CoordinatorLayout getCordinatorLayout() {
        return (CoordinatorLayout) activity().findViewById(R.id.root_appbar);
    }


    public boolean isShowBackArrow() {
        return showBackArrow;
    }

    public void setShowBackArrow(boolean showBackArrow) {
        this.showBackArrow = showBackArrow;
    }

    public void setToolBarTitle(int resid) {
        if (toolbar != null) {
            toolbar.setTitle(resid);
        }
    }


    ClickListener backarrowclick = new ClickListener(activity()) {
        @Override
        public void click(View v) throws Exception {
            ((BaseActivity) activity()).handleFragmentBackPressed();
        }
    };

    public void syncData() {
        ((BaseActivity) activity()).syncData();
    }

    public int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity().getColor(id);
        } else {
            //noinspection deprecation
            return activity().getResources().getColor(id);
        }
    }

    public RotateLoading getProgress() {
        return progress;
    }


    public Gson getGsonInstance() {
        return DIHelper.getGsonInstance();
    }

    public void showException(Exception e) {
        SweetAlertUtil.showErrorMessage(activity(), IonServiceManager.ResponseCallback.getExceptionMessage(activity(), e));
        e.printStackTrace();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initCurrentActivityContext(context);
        initCurrentFragmentActivityContext(context);
    }

    protected void initCurrentActivityContext(Context context) {
        currentActivity = (BaseActivity) context;
        if (getApp() != null) {
            getApp().setCurrentBaseActivity((BaseActivity) context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getApp().setCurrentActivity(null);
        getApp1().setCurrentFragmentBaseActivity(null);

    }

    public BaseActivity activity() {
        return currentActivity;
    }

    /**
     * function to get Application Instance in Activities
     *
     * @return FCSApplication Instance
     */

    public CourierApplication getApp() {

        return activity().getApp();
    }

    public FragmentBaseActivity activityFragment() {
        return currentFragmentActivity;
    }

    protected void initCurrentFragmentActivityContext(Context context) {
        try {
            currentFragmentActivity = (FragmentBaseActivity) context;
            if (getApp1() != null) {
                getApp1().setCurrentFragmentBaseActivity((FragmentBaseActivity) context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CourierApplication getApp1() {

        return activityFragment().getApp();
    }

    public boolean isValidString(String string) {
        return activity().isValidString(string);

    }


    public boolean isEmpty(String variable) {
        return !(variable != null && variable.length() > 0);

    }
    
    
}
