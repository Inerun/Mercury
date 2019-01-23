package com.inerun.courier.activity_driver;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.adapter.NavFilterListAdapter;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.ClickListener;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.data.DriverReportData;
import com.inerun.courier.data.SalesFilterData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.network.ServiceManager;
import com.inerun.courier.service.DIRequestCreator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by vineet on 9/27/2017.
 */

public class DeliveryReportFragment extends BaseFragment {

    private static int FROM_CALENDAR = 1;
    private static int TO_CALENDAR = 2;
    private static final String REPORT_REQUEST = "report_request";
    private Context context;
    private TextView error_textview;
    private TextView delivery_count, delivered_count, delivery_pending_count;
    private TextView collection_count, collected_count, collection_pending_count;
    private TextView payment_count, payment__cash_count, payment__card_count;
    private TextView returnparcelscount;
    private IonServiceManager serviceManager;
    private LinearLayout root_lay;

    private RecyclerView nav_recyclerview;
    private NavFilterListAdapter adapter;
    private LinearLayout calender_lay;
    private TextView tocalander_txt, fromcalander_txt, filter_submit_btn;
    private List<SalesFilterData> salesFilterDataList;
    private int lastSelectedPosition = 0;
    private ImageView fromcalendar_img, tocalendar_img;
    private TextView sales_filter_txt_value;
    private ImageView sales_filter_image;

    public static DeliveryReportFragment newInstance() {

        Bundle args = new Bundle();

        DeliveryReportFragment fragment = new DeliveryReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salesFilterDataList = DIHelper.getSalesFilterData(activity());
    }

    @Override
    public int inflateView() {
        return R.layout.delivery_report_fragment;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        context = activity();
        setShowBackArrow(true);
        initView(root);
        setListener();
        getData();
//        getDriverReportService(ServiceManager.SalesReportFilter.TODAY);


    }

    private void initView(View root) {

        error_textview = (TextView) root.findViewById(R.id.error_textview);



        delivery_count = (TextView) root.findViewById(R.id.delivery_count);
        delivered_count = (TextView) root.findViewById(R.id.delivered_count);
        delivery_pending_count = (TextView) root.findViewById(R.id.delivery_pending_count);

        collection_count = (TextView) root.findViewById(R.id.collection_count);
        collected_count = (TextView) root.findViewById(R.id.collected_count);
        collection_pending_count = (TextView) root.findViewById(R.id.collection_pending_count);

        payment_count = (TextView) root.findViewById(R.id.payment_count);
        payment__cash_count = (TextView) root.findViewById(R.id.payment__cash_count);
        payment__card_count = (TextView) root.findViewById(R.id.payment__card_count);

        returnparcelscount = (TextView) root.findViewById(R.id.return_count);

        root_lay = (LinearLayout) root.findViewById(R.id.root_lay);
        root_lay.setVisibility(View.GONE);


        if (activityFragment().filterNavView != null) {
            nav_recyclerview = activityFragment().filterNavView.findViewById(R.id.navfilter_recycler);
            calender_lay = activityFragment().filterNavView.findViewById(R.id.calender_lay);

            fromcalendar_img = activityFragment().filterNavView.findViewById(R.id.fromcalendar_img);
            tocalendar_img = activityFragment().filterNavView.findViewById(R.id.tocalendar_img);
            fromcalander_txt = activityFragment().filterNavView.findViewById(R.id.fromcalander_txt);
            tocalander_txt = activityFragment().filterNavView.findViewById(R.id.tocalander_txt);
            fromcalander_txt.setText("");
            tocalander_txt.setText("");

            filter_submit_btn = activityFragment().filterNavView.findViewById(R.id.filter_submit);

        }

        sales_filter_txt_value = root.findViewById(R.id.sales_filter_txt_value);
        sales_filter_image = root.findViewById(R.id.sales_filter_image);

    }


    private void getData() {
        DriverReportData driverReportData = new DriverReportData("10","3","7", "5", "4","1","1000.00", "600.00","400.00","7");

        setData(driverReportData);
    }


    private void getDriverReportService(int id) {
        String date_range = DIHelper.getDateRange(id);

        if (id == ServiceManager.SalesReportFilter.CUSTOM_RANGE) {

            String fromCalendar = (fromcalander_txt.getText() + "").trim();
            String toCalendar = (tocalander_txt.getText() + "").trim();

            if (!isValidString(fromCalendar)) {
                SweetAlertUtil.showSweetMessageLongToast(activity(), getString(R.string.from_date_missing_msg));
                return;
            } else if (!isValidString(toCalendar)) {
                SweetAlertUtil.showLongErrorMessage(activity(), getString(R.string.to_date_missing_msg));
                return;
            } else {
                (activityFragment()).drawer.closeDrawer(GravityCompat.END); /*Opens the Right Drawer*/
                date_range = fromCalendar + "-" + toCalendar;
                sales_filter_txt_value.setText(fromCalendar + " - " + toCalendar);
            }
        } else {
            date_range = DIHelper.getDateRange(id);
        }


        showProgress();
        Log.i("Start_TIME", "" + System.currentTimeMillis());
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getDriverReportMapParams(date_range);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_DRIVER_REPORT_DATA, params, null, response_listener, response_errorlistener, REPORT_REQUEST, this);
    }

    Response.Listener<String> response_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hideProgress();
            Gson gson = new Gson();

//            whReadyParcelData = gson.fromJson(response, WhReadyParcelData.class);

//            setData(whReadyParcelData);

            DriverReportData driverReportData = gson.fromJson(response, DriverReportData.class);

            setData(driverReportData);
            error_textview.setVisibility(View.GONE);
        }
    };

    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideProgress();
            error_textview.setVisibility(View.VISIBLE);
            root_lay.setVisibility(View.GONE);
            showSnackbar(error.getMessage());
            Log.i("Error_MSG", "" + error.getMessage());

        }
    };


    private void setData(DriverReportData driverReportData) {



        delivery_count.setText(driverReportData.getTotal_delivery_count());
        delivered_count.setText(driverReportData.getDelivered_count());
        delivery_pending_count.setText(driverReportData.getDelivery_pending_count());

        collection_count.setText(driverReportData.getTotal_collection_count());
        collected_count.setText(driverReportData.getCollected_count());
        collection_pending_count.setText(driverReportData.getCollection_pending_count());

        payment_count.setText(driverReportData.getTotal_payment());
        payment__cash_count.setText(driverReportData.getPayment_cash());
        payment__card_count.setText(driverReportData.getPayment_card());

        returnparcelscount.setText(driverReportData.getRetrun_parcels());
        root_lay.setVisibility(View.VISIBLE);


    }


    private void setSalesFilterAdapter() {
        if (nav_recyclerview != null) {
            adapter = new NavFilterListAdapter(activity(), this);
            nav_recyclerview.setLayoutManager(new LinearLayoutManager(activity()));
            nav_recyclerview.setItemAnimator(new DefaultItemAnimator());
//            dietype_nav_recyclerview.addItemDecoration(new SimpleDividerItemDecoration(activity()));
            nav_recyclerview.setAdapter(adapter);


            adapter.addDataListToList(salesFilterDataList);
            adapter.setLastSelectedPosition(lastSelectedPosition);


        }


    }

    public void setFilterData(int pos) {
        Log.i("Report_pos", "" + pos);
        if (pos == adapter.getDataList().size() - 1) {
            calender_lay.setVisibility(View.VISIBLE);

        } else {
            (activityFragment()).drawer.closeDrawer(GravityCompat.END); /*Opens the Right Drawer*/
            calender_lay.setVisibility(View.GONE);

            fromcalander_txt.setText("");
            tocalander_txt.setText("");

//            try {
//                getSalesReportDataService(adapter.getDataList().get(pos).getId());
//            } catch (ServiceManager.InvalidParametersException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            sales_filter_txt_value.setText(adapter.getDataList().get(pos).getName());

        }


        lastSelectedPosition = pos;
        salesFilterDataList.clear();
        salesFilterDataList.addAll(adapter.getDataList());
        adapter.setLastSelectedPosition(lastSelectedPosition);
//        sales_filter_txt_value.setText(adapter.getDataList().get(pos).getName());

    }

    private void setListener() {
        sales_filter_image.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View v) throws Exception {
                setSalesFilterAdapter();
                (activityFragment()).drawer.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
            }
        });

        fromcalendar_img.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View v) throws Exception {
                showDatePickerDialog(v, FROM_CALENDAR);
            }
        });

        tocalendar_img.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View v) throws Exception {
                showDatePickerDialog(v, TO_CALENDAR);
            }
        });

        filter_submit_btn.setOnClickListener(new ClickListener(activity()) {
            @Override
            public void click(View v) throws Exception {

                try {
                    getDriverReportService(ServiceManager.SalesReportFilter.CUSTOM_RANGE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showDatePickerDialog(View view, final int is_from_calendar) {
        // TODO Auto-generated method stub
        // To show current date in the datepicker
        Calendar mcurrentDate = Calendar.getInstance();
        final int[] mYear = {mcurrentDate.get(Calendar.YEAR)};
        final int[] mMonth = {mcurrentDate.get(Calendar.MONTH)};
        final int[] mDay = {mcurrentDate.get(Calendar.DAY_OF_MONTH)};

        DatePickerDialog mDatePicker = new DatePickerDialog(activity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
//                String myFormat = "dd/MM/yyyy"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(ServiceManager.DateRangeFormat);
                if (is_from_calendar == FROM_CALENDAR) {
                    fromcalander_txt.setText(sdf.format(myCalendar.getTime()));
                } else {
                    tocalander_txt.setText(sdf.format(myCalendar.getTime()));
                }


                mDay[0] = selectedday;
                mMonth[0] = selectedmonth;
                mYear[0] = selectedyear;
            }
        }, mYear[0], mMonth[0], mDay[0]);
        //mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }


}
