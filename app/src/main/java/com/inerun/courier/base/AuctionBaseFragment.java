package com.inerun.courier.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.model.AuctionInvoice;
import com.inerun.courier.model.AuctionItem;
import com.inerun.courier.printer.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by vinay on 09/09/17.
 */

abstract public class AuctionBaseFragment extends Fragment {


    public View rootview;
    public IonServiceManager serviceManager;
    AuctionBaseActivity currentActivity;
    boolean showBackArrow = false;
    private Toolbar toolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initCurrentActivityContext(context);
    }

    protected  void initCurrentActivityContext(Context context){
        currentActivity=(AuctionBaseActivity) context;
        if(getApp()!=null) {
            getApp().setCurrentActivity((AuctionBaseActivity) context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int layout_id = initLayout();
        serviceManager = getApp().ionserviceManager;
        rootview = inflater.inflate(layout_id, container, false);

        toolbar = (Toolbar) activity().findViewById(R.id.toolbar);
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

        return rootview;
    }

    protected abstract int initLayout();

    protected abstract void initView(View rootview) throws IonServiceManager.InvalidParametersException;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       processActivityCreated();
    }

    public void processActivityCreated() {
        try {
            initView(rootview);
        } catch (IonServiceManager.InvalidParametersException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        StateSaver.saveInstanceState(this, outState);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("BaseF","onResume");
    }

    public void setActionBarTitle(String title) {
        activity().setToolBarTitle(title);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getApp().setCurrentActivity(null);

    }

    public AuctionBaseActivity activity() {
//        return (BaseActivity) getActivity();
        return currentActivity;
    }

//    public FcsPref getPrefs() {
//
//        return getApp().fcsPref;
//    }




    public void pushFragment(Fragment fragment) {
        activity().pushFragment(fragment);
    }

    public void popFragment() {
        try {
            activity().popFragment();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void popMultipleFragments(int num) {
        activity().popMultipleFragments(num);

    }

    public void replaceCurrentFragment(Fragment fragment) {
        activity().replaceCurrentFragment(fragment);
    }


    public void showLongToast(String msg) {
        Toast.makeText(activity(), msg, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String msg) {
        Toast.makeText(activity(), msg, Toast.LENGTH_SHORT).show();
    }

    public IonServiceManager serviceManager() {

        return getApp().ionserviceManager;
    }

    public View getRootview() {
        return rootview;
    }


    public boolean isValidString(String string) {
       return activity().isValidString(string);

    }

    /**
     * function to get Application Instance in Activities
     *
     * @return FCSApplication Instance
     */

    public CourierApplication getApp() {

         return activity().getApp();
    }


//    public void showOkAlert(int title, int message, int button,
//                            Alert.ConnectionDialogClickListener dialogclicklistener, int dialog_id) {
//        AlertUtil.showDialogwithNeutralButton(activity(), title, message, button, dialogclicklistener, dialog_id).show();
//    }
//
//    public boolean isTokenPresent() {
//
//        return getPrefs().hasToken();
//
//
//    }

    public Gson getGsonInstance() {
        return DIHelper.getGsonInstance();
    }

//    public void showEmptyDataFragment(final String errorstring) {
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                replaceCurrentFragment(ErrorFragment.newInstance(errorstring));
//            }
//        }, 0);
//
//
//    }


    public void refreshFragment(){

    }


    public void showException(Exception e) {
        SweetAlertUtil.showErrorMessage(activity(), IonServiceManager.ResponseCallback.getExceptionMessage(activity(), e));
        e.printStackTrace();
    }

    public Drawable getDrawable(int id) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            return ContextCompat.getDrawable( getActivity(), id);
//        } else {
//            return getResources().getDrawable(id);
//        }
        return ContextCompat.getDrawable(activity(), id);
    }


    protected String preparePrintData(AuctionInvoice invoice, List<AuctionItem> itemList) throws JSONException {


        JSONObject jsonObject = new JSONObject();


        String stringDate = DateFormat.getDateTimeInstance().format(invoice.getCreated_on());
        jsonObject.put(AppConstant.Keys.Date, stringDate);
        jsonObject.put(AppConstant.Keys.Order_num, invoice.getInvoice_number());
//        jsonObject.put(AppConstant.Keys.Order_time,stringDate);
//        jsonObject.put(AppConstant.Keys.Order_time, "11:59");
        jsonObject.put(AppConstant.Keys.Cashier_name, "" + Utils.getName(activity()));
        jsonObject.put(AppConstant.Keys.Customer_name, "" + invoice.getCustomer_name());
        jsonObject.put(AppConstant.Keys.Customer_email, "" + invoice.getCustomer_email());
        jsonObject.put(AppConstant.Keys.Customer_phone, "" + invoice.getCustomer_phone());
        jsonObject.put(AppConstant.Keys.total_incl_vat, "" + invoice.getGrand_total());

        jsonObject.put(AppConstant.Keys.cash_received, "" + invoice.getAmount_returned());
        jsonObject.put(AppConstant.Keys.payment_method, ""+invoice.getPaymentMethods());



        if (invoice.isCashPayment()) {
            jsonObject.put(AppConstant.Keys.cash_given, "" + invoice.getCash_amount());

        } if (invoice.isCardPayment()) {
            jsonObject.put(AppConstant.Keys.card_amount, ""+invoice.getCard_amount());

        }
        if (invoice.isChequePayment()) {
            jsonObject.put(AppConstant.Keys.cheque_amount, ""+invoice.getCheque_amount());

        }

        if (invoice.isBankTransfer()) {
            jsonObject.put(AppConstant.Keys.bank_amount, ""+invoice.getBank_transfer());

        }


        if (itemList != null && itemList.size() > 0) {

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < itemList.size(); i++) {
                AuctionItem item = itemList.get(i);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put(AppConstant.Keys.Qty, "" + item.getItem_barcode());
                jsonObject1.put(AppConstant.Keys.Desc, "" + item.getDescription());
                jsonObject1.put(AppConstant.Keys.Amount, "" + item.getAap());
                jsonArray.put(jsonObject1);

            }
            jsonObject.put(AppConstant.Keys.Items, jsonArray);

        }


        return jsonObject.toString();
    }

    ClickListener backarrowclick = new ClickListener(activity()) {
        @Override
        public void click(View v) throws Exception{
//            ((AuctionBaseActivity) getActivity()).handleFragmentBackPressed();
            ((AuctionBaseActivity) activity()).handleBackEvent();
        }
    };

    public boolean isShowBackArrow() {
        return showBackArrow;
    }

    public void setShowBackArrow(boolean showBackArrow) {
        this.showBackArrow = showBackArrow;
    }
}
