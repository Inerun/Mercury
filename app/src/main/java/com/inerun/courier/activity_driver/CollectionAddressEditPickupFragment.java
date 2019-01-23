package com.inerun.courier.activity_driver;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.arasthel.asyncjob.AsyncJob;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.Exception.ExceptionMessages;
import com.inerun.courier.R;
import com.inerun.courier.activity_auction.IonServiceManager;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.UrlConstants;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.helper.DIHelper;
import com.inerun.courier.model.City;
import com.inerun.courier.model.City_Table;
import com.inerun.courier.model.Country;
import com.inerun.courier.model.Country_Table;
import com.inerun.courier.model.LocationResponseBo;
import com.inerun.courier.model.Region;
import com.inerun.courier.model.Region_Table;
import com.inerun.courier.service.DIRequestCreator;
import com.inerun.courier.sqldb.AppDatabase;
import com.loopj.android.http.RequestParams;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vineet on 01/01/19.
 */

public class CollectionAddressEditPickupFragment extends BaseFragment implements View.OnClickListener {

    private EditText fname_edt, lname_edt, deparment_edt, email_edt, phone_edt, landline_edt, extension_edt, address1_edt, address2_edt, region_edt, city_edt, pincode_edt, taxid_edt;
    public MaterialBetterSpinner country_spinner, region_spinner, city_spinner;
    private ArrayAdapter<String> counrtyarrayAdapter;
    private ArrayAdapter<String> regionarrayAdapter;
    private ArrayAdapter<String> cityarrayAdapter;
    private Button submit, cancel_btn,next_btn;
    //    private Client client;
    private List<Country> countryList;
    private List<Region> regionList;
    private List<City> cityList;

    private RadioButton home_rBtn, office_rbtn, weekend_yes_rbtn, weekend_no_rbtn, save_yes_rbtn, save_no_rbtn;

    private PickupParcelData pickupParcelData;
    private SweetAlertDialog progressdialog;

    public static CollectionAddressEditPickupFragment newInstance(PickupParcelData pickupParcelData) {

        Bundle args = new Bundle();

        CollectionAddressEditPickupFragment fragment = new CollectionAddressEditPickupFragment();
        args.putSerializable("DATA", pickupParcelData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickupParcelData = (PickupParcelData) getArguments().getSerializable("DATA");
    }

    @Override
    public int inflateView() {
        return R.layout.activity_collection_address_edit;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        ((CollectionAddressEditActivity) getActivity()).clickingOnTab(0);
        setShowBackArrow(true);
        fname_edt = root.findViewById(R.id.firstname_edt);
        lname_edt = root.findViewById(R.id.lastname_edt);
        deparment_edt = root.findViewById(R.id.deparment_edt);
        email_edt = root.findViewById(R.id.email_edt);
        phone_edt = root.findViewById(R.id.phone_edt);
        landline_edt = root.findViewById(R.id.landline_edt);
        extension_edt = root.findViewById(R.id.extension_edt);
        address1_edt = root.findViewById(R.id.address1_edt);
        address2_edt = root.findViewById(R.id.address2_edt);
        region_edt = root.findViewById(R.id.region_edt);
        city_edt = root.findViewById(R.id.city_edt);
        pincode_edt = root.findViewById(R.id.pincode_edt);
        taxid_edt = root.findViewById(R.id.taxid_edt);

        country_spinner = root.findViewById(R.id.country_spinner);
        region_spinner = root.findViewById(R.id.region_spinner);
        city_spinner = root.findViewById(R.id.city_spinner);

        home_rBtn = root.findViewById(R.id.home_rBtn);
        office_rbtn = root.findViewById(R.id.office_rbtn);
        weekend_yes_rbtn = root.findViewById(R.id.weekend_yes_rbtn);
        weekend_no_rbtn = root.findViewById(R.id.weekend_no_rbtn);
        save_yes_rbtn = root.findViewById(R.id.save_yes_rbtn);
        save_no_rbtn = root.findViewById(R.id.save_no_rbtn);

        submit = root.findViewById(R.id.submit);
        next_btn = root.findViewById(R.id.next_btn);
        cancel_btn = root.findViewById(R.id.cancel_btn);

        submit.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);

        if (countryList == null)
//            getLocationsDataService();
        getRegionService();


    }


    private void getLocationsDataService() {
        showProgress();
        Log.i("performRequestSyncData", "start" + System.currentTimeMillis());
        Map<String, String> params = DIRequestCreator.getInstance(activity()).getLocationMapParams();
        RequestParams requestparams = new RequestParams(params);
        Log.i("Params", "" + requestparams);

        CourierApplication.serviceManager().postRequest(UrlConstants.URL_LOCATION_DATA, params, null, response_listener, response_errorlistener, "LOCATION_DATA");
//        hideProgress();

    }


    Response.Listener response_listener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {

//            Log.d("Response: ", response.toString());

            //response = "{\"message\":\"Successfully sync .\",\"status\":true,\"data\":{\"country\":[{\"id\":\"1\",\"country_name\":\"Zambia\",\"country_code\":\"260\",\"created_date\":\"2018-08-29 14:25:20\",\"status\":\"1\",\"currency_for_frontend\":\"1\",\"currency_for_account\":\"1\",\"time_zone\":\"Africa\\/Lusaka\",\"gmt\":\"+7\",\"isdropdown\":\"1\"},{\"id\":\"2\",\"country_name\":\"Canada\",\"country_code\":\"1\",\"created_date\":\"2018-08-29 14:28:53\",\"status\":\"1\",\"currency_for_frontend\":\"1\",\"currency_for_account\":\"1\",\"time_zone\":\"America\\/Toronto\",\"gmt\":\"-5\",\"isdropdown\":\"0\"}],\"region\":[{\"id\":\"1\",\"region_name\":\"Lusaka Province\",\"country\":\"1\",\"status\":\"1\",\"created_on\":\"2018-08-29 14:25:51\",\"modified_on\":\"2018-08-29 12:25:51\"},{\"id\":\"2\",\"region_name\":\"Ontario\",\"country\":\"2\",\"status\":\"1\",\"created_on\":\"2018-08-29 14:29:40\",\"modified_on\":\"2018-08-29 12:29:40\"}],\"city\":[{\"id\":\"1\",\"city_name\":\"Lusaka\",\"country_id\":\"1\",\"region_id\":\"1\",\"created_date\":\"2018-08-29 14:26:01\",\"modified_on\":\"2018-08-29 12:26:01\",\"status\":\"1\"},{\"id\":\"2\",\"city_name\":\"Toronto\",\"country_id\":\"2\",\"region_id\":\"2\",\"created_date\":\"2018-08-29 14:30:03\",\"modified_on\":\"2018-08-29 12:30:03\",\"status\":\"1\"},{\"id\":\"3\",\"city_name\":\"LONDON\",\"country_id\":\"1\",\"region_id\":\"1\",\"created_date\":\"2018-09-06 09:44:44\",\"modified_on\":\"2018-09-06 07:44:44\",\"status\":\"1\"}]}}";

            try {
                JSONObject jsonObject = new JSONObject(response);
//                response = jsonObject.toString();

                //Toast.makeText(BaseActivity.this, DIHelper.getMessage(jsonObject), Toast.LENGTH_LONG).show();
//                showSnackbar(DIHelper.getMessage(jsonObject));
                if (DIHelper.getStatus(jsonObject)) {
//                    Gson gson = new Gson();
                    Log.i("DB", "starttime: " + System.currentTimeMillis());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    JsonObject jsonObject2 = new JsonParser().parse(jsonObject1.toString()).getAsJsonObject();

                    parseJsonAndSaveIntoDatabase(jsonObject2);

                    Log.i("gson", "starttime: " + System.currentTimeMillis());
//                    DIDbHelper.deleteTables(BaseActivity.this);
                    Log.i("deleteTables", "starttime: " + System.currentTimeMillis());
                    Log.i("DB", "endtime: " + System.currentTimeMillis());

                } else {

//                    login_btn.setClickable(true);

                }

            } catch (JSONException e) {
                e.printStackTrace();

                showSnackbar(ExceptionMessages.getMessageFromException(activity(), -1, e));

            } catch (Exception e) {
                e.printStackTrace();

                showSnackbar(ExceptionMessages.getMessageFromException(activity(), -1, e));
            }

            hideProgress();
        }
    };


    Response.ErrorListener response_errorlistener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("onErrorResponse: ", error.toString());
            hideProgress();

            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                showSnackbar(R.string.exception_alert_message_timeout_exception);
            } else if (error instanceof AuthFailureError) {
                //TODO
                showSnackbar("AuthFailure Error");
            } else if (error instanceof ServerError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_internal_server_error);
            } else if (error instanceof NetworkError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_network);
            } else if (error instanceof ParseError) {
                //TODO
                showSnackbar(R.string.exception_alert_message_parsing_exception);
            } else {
                showSnackbar(R.string.exception_alert_message_error);
            }
        }
    };

    public void parseJsonAndSaveIntoDatabase(final JsonObject result) throws JsonSyntaxException {


        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {

                        try {

                            if (deleteAllMenus()) {
                                Log.i("deleteAllMenus", "AfterdeleteAllMenus");
                                JsonObject array = result.getAsJsonObject();
                                Log.i("Array", "" + array);
                                Log.i("", "" + System.currentTimeMillis());
                                Gson gson = getGsonInstance();

                                LocationResponseBo responseBo = gson.fromJson(array, LocationResponseBo.class);

                                saveDataintoLocalDb(responseBo);
                                return true;
                            } else {
                                return false;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
//                            showException(e);
                            hideProgress();
                            return false;
                        }

                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {


                        if (result) {
                            Log.i("result", result + "");
                            setCountry();
                            setData();

                        } else {

                            showException(new RuntimeException(getString(R.string.foodmenu_error_unknown_error)));
//                            popFragment();
                        }
                        hideProgress();

                    }
                }).create().start();


    }


    public boolean deleteAllMenus() {
        Log.i("deleteAllMenus", "deleteAllMenus");
        String sourcePath = FlowManager.getContext().getDatabasePath(AppDatabase.NAME) + ".db";
        Log.i("path", "sourcePath");
        File source = new File(sourcePath);
        if (source.exists()) {
//            Delete.tables(Country.class, Region.class, City.class);
//            FlowManager.getDatabase(AppDatabase.class).reset();
            Country country = new Country();
            country.delete();
            Region region = new Region();
            region.delete();
            City city = new City();
            city.delete();
            Log.i("deleteAllMenus", "Completed");
        }
        return true;

    }


    private void saveDataintoLocalDb(LocationResponseBo responseBo) {

        Log.i("saveData", "saveDataintoLocalDb");

        List country = responseBo.getCountry();
        Log.i("country_out", country.size() + "");
        List region = responseBo.getRegion();
        List city = responseBo.getCity();


        if (country != null) {
            FlowManager.getDatabase(AppDatabase.class)
                    .getTransactionManager()
                    .getSaveQueue()
                    .addAll(country);
            Log.i("country", country.size() + "");

        }
        if (region != null) {
            FlowManager.getDatabase(AppDatabase.class)
                    .getTransactionManager()
                    .getSaveQueue()
                    .addAll(region);
            Log.i("region", region.size() + "");

        }
        if (city != null) {
            FlowManager.getDatabase(AppDatabase.class)
                    .getTransactionManager()
                    .getSaveQueue()
                    .addAll(city);
            Log.i("city", city.size() + "");

        }


//        getApp().fcsPref.saveLastSync("" + Helper.getFormattedDateAsString(System.currentTimeMillis()));
        Log.i("sync", "finished");

    }

    private void setCountry() {

        countryList = SQLite.select().from(Country.class).queryList();
        Log.i("setCountry", SQLite.select().from(Country.class).getQuery());
        Log.i("countryList", countryList.size() + "");
        counrtyarrayAdapter = new ArrayAdapter<>(activity(), android.R.layout.simple_dropdown_item_1line, DIHelper.getCountries(countryList));
        country_spinner.setAdapter(counrtyarrayAdapter);

        country_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Pos", position + "");
//                region_spinner.setVisibility(View.GONE);
//                city_spinner.setVisibility(View.GONE);
//                setRegion(countryList.get(position));
                setRegionAndCity(countryList.get(position), false);

            }
        });


    }

    private void setRegionAndCity(Country country, boolean auto_selection) {
        if (country.isDropdown()) {
            region_spinner.setVisibility(View.GONE);
            city_spinner.setVisibility(View.GONE);
            region_edt.setVisibility(View.GONE);
            city_edt.setVisibility(View.GONE);
            setRegion(country, auto_selection);

        } else {
            region_spinner.setVisibility(View.GONE);
            city_spinner.setVisibility(View.GONE);
            region_edt.setVisibility(View.VISIBLE);
            city_edt.setVisibility(View.VISIBLE);

            if (auto_selection) {
                region_edt.setText(pickupParcelData.getPickup_address().getState());
                city_edt.setText(pickupParcelData.getPickup_address().getCity());

            } else {
                region_edt.setText(null);
                city_edt.setText(null);

            }
        }

    }


    private void setRegion(Country country, boolean auto_selection) {
        region_spinner.setText(null);
        city_spinner.setText(null);

        Log.i("setRegion", SQLite.select().from(Region.class).where(Region_Table.country.eq(country.id)).getQuery());
        regionList = SQLite.select().from(Region.class).where(Region_Table.country.eq(country.id)).queryList();
        regionarrayAdapter = new ArrayAdapter<>(activity(), android.R.layout.simple_dropdown_item_1line, DIHelper.getRegion(regionList));
        region_spinner.setAdapter(regionarrayAdapter);

        region_spinner.setVisibility(View.VISIBLE);

        region_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCity(regionList.get(position), false);
            }
        });

        if (auto_selection) {

            try {
                String state = "";
                state = pickupParcelData.getPickup_address().getState();

                ArrayAdapter myAdap = (ArrayAdapter) region_spinner.getAdapter(); //cast to an ArrayAdapter

                int spinnerPosition = myAdap.getPosition(state);
                Log.i("spinnerPosition", "" + spinnerPosition);
                Log.i("region", "" + state);

                if (spinnerPosition != -1) {
                    //set the default according to value

                    Region region = SQLite.select().from(Region.class).where(Region_Table.region_name.eq(state)).querySingle();
                    Log.i("region", SQLite.select().from(Region.class).where(Region_Table.region_name.eq(state)).getQuery());
                    if (region != null) {
                        region_spinner.setText(state);
                        setCity(region, true);
                    }
                } else {
//                    if (pickupParcelData.getPickup_address().getCountry() != null)
//                        country_spinner.setText(pickupParcelData.getPickup_address().getCountry());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void setCity(Region region, boolean auto_selection) {
        city_spinner.setText(null);

        Log.i("setCity", SQLite.select().from(City.class).where(City_Table.region_id.eq(region.getId())).getQuery());
        cityList = SQLite.select().from(City.class).where(City_Table.region_id.eq(region.getId())).queryList();
        cityarrayAdapter = new ArrayAdapter<>(activity(), android.R.layout.simple_dropdown_item_1line, DIHelper.getCity(cityList));
        city_spinner.setAdapter(cityarrayAdapter);

        city_spinner.setVisibility(View.VISIBLE);

        if (auto_selection) {

            try {
                String citystr = "";
                citystr = pickupParcelData.getPickup_address().getCity();


                ArrayAdapter myAdap = (ArrayAdapter) city_spinner.getAdapter(); //cast to an ArrayAdapter

                int spinnerPosition = myAdap.getPosition(citystr);
                Log.i("spinnerPosition", "" + spinnerPosition);
                Log.i("city", "" + citystr);

                if (spinnerPosition != -1) {
                    //set the default according to value

                    City city = SQLite.select().from(City.class).where(City_Table.city_name.eq(citystr)).querySingle();
                    Log.i("city", SQLite.select().from(City.class).where(City_Table.city_name.eq(citystr)).getQuery());
                    if (city != null) {
                        city_spinner.setText(citystr);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setData() {


        fname_edt.setText(pickupParcelData.getPickup_address().getfName());
        lname_edt.setText(pickupParcelData.getPickup_address().getlName());
        deparment_edt.setText(pickupParcelData.getPickup_address().getDepartment());
        email_edt.setText(pickupParcelData.getPickup_address().getEmail());
        phone_edt.setText(pickupParcelData.getPickup_address().getPhone());
        landline_edt.setText(pickupParcelData.getPickup_address().getLandline());
        extension_edt.setText(pickupParcelData.getPickup_address().getExt());
        address1_edt.setText(pickupParcelData.getPickup_address().getAddress1());
        address2_edt.setText(pickupParcelData.getPickup_address().getAddress2());

        String pincode = pickupParcelData.getPickup_address().getZipCode();
        if (pincode != null) {
            pincode_edt.setText(pincode);
        }

        String taxId = pickupParcelData.getPickup_address().getTaxid();
        if (taxId != null) {
            taxid_edt.setText(taxId);
        }

        if (pickupParcelData.getPickup_address().getHome_or_office() == 0) {
            home_rBtn.setChecked(false);
            office_rbtn.setChecked(true);
        } else {
            home_rBtn.setChecked(true);
            office_rbtn.setChecked(false);
        }

        if (pickupParcelData.getPickup_address().getWeekend_available() == 0) {
            weekend_yes_rbtn.setChecked(false);
            weekend_no_rbtn.setChecked(true);
        } else {
            weekend_yes_rbtn.setChecked(true);
            weekend_no_rbtn.setChecked(false);
        }


        try {


            ArrayAdapter myAdap = (ArrayAdapter) country_spinner.getAdapter(); //cast to an ArrayAdapter

            int spinnerPosition = myAdap.getPosition(pickupParcelData.getPickup_address().getCountry());
            Log.i("spinnerPosition", "" + spinnerPosition);
            Log.i("country", "" + pickupParcelData.getPickup_address().getCountry());

            if (spinnerPosition != -1) {
                //set the default according to value
//                    country_spinner.setSelection(spinnerPosition);

                Country country = SQLite.select().from(Country.class).where(Country_Table.country_name.eq(pickupParcelData.getPickup_address().getCountry())).querySingle();
                Log.i("setCountry", SQLite.select().from(Country.class).where(Country_Table.country_name.eq(pickupParcelData.getPickup_address().getCountry())).getQuery());
                if (country != null) {
                    country_spinner.setText(pickupParcelData.getPickup_address().getCountry());
                    setRegionAndCity(country, true);
                }
            } else {
//                    if (pickupParcelData.getPickup_address().getCountry() != null)
//                        country_spinner.setText(pickupParcelData.getPickup_address().getCountry());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (validateData()) {
                    saveData();
                }
                break;
                case R.id.next_btn:
                if (validateData()) {
                    saveData();
                }
                break;
            case R.id.cancel_btn:
                activity().finish();
                break;
        }
    }

    private boolean validateData() {


        String fname = ("" + fname_edt.getText()).trim();
        String lname = ("" + lname_edt.getText()).trim();
        String department = ("" + deparment_edt.getText()).trim();
        String email = ("" + email_edt.getText()).trim();
        String phone = ("" + phone_edt.getText()).trim();
        String landline = ("" + landline_edt.getText()).trim();
        String extension = ("" + extension_edt.getText()).trim();
        String address1 = ("" + address1_edt.getText()).trim();
        String address2 = ("" + address2_edt.getText()).trim();

        String pincode = ("" + pincode_edt.getText()).trim();
        String taxid = ("" + taxid_edt.getText()).trim();

        String countrySpin = ("" + country_spinner.getText()).trim();


        Country country = (SQLite.select().from(Country.class).where(Country_Table.country_name.eq(countrySpin)).querySingle());

        int country_id = country.getId();

        String region = "";
        String city = "";
        int state_id = -1;
        int city_id = -1;

        if (country.isDropdown()) {
            String regionSpin = ("" + region_spinner.getText()).trim();
            String citySpin = ("" + city_spinner.getText()).trim();
            region = regionSpin;
            city = citySpin;
            state_id = (SQLite.select().from(Region.class).where(Region_Table.region_name.eq(regionSpin)).querySingle()).getId();
            city_id = (SQLite.select().from(City.class).where(City_Table.city_name.eq(citySpin)).querySingle()).getId();
        } else {
            region = ("" + region_edt.getText()).trim();
            city = ("" + city_edt.getText()).trim();
            state_id = -1;
            city_id = -1;
        }


        int home_or_office = home_rBtn.isChecked() ? 1 : 0;
        int weekend_available = weekend_yes_rbtn.isChecked() ? 1 : 0;
        int save_option = save_yes_rbtn.isChecked() ? 1 : 0;

        if (!isStringValid(fname)) {
            Toast.makeText(activity(), getString(R.string.error_fname_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(lname)) {
            Toast.makeText(activity(), getString(R.string.error_lname_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(email)) {
            Toast.makeText(activity(), getString(R.string.error_email_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(phone)) {
            Toast.makeText(activity(), getString(R.string.error_phone_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(landline)) {
            Toast.makeText(activity(), getString(R.string.error_landline_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(extension)) {
            Toast.makeText(activity(), getString(R.string.error_extension_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(address1)) {
            Toast.makeText(activity(), getString(R.string.error_address1_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(countrySpin)) {
            Toast.makeText(activity(), getString(R.string.error_country_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(region)) {
            Toast.makeText(activity(), getString(R.string.error_region_msg), Toast.LENGTH_LONG).show();
            return false;
        } else if (!isStringValid(city)) {
            Toast.makeText(activity(), getString(R.string.error_city_msg), Toast.LENGTH_LONG).show();
            return false;
        } else {
            pickupParcelData.getPickup_address().setfName(fname);
            pickupParcelData.getPickup_address().setlName(lname);
            pickupParcelData.getPickup_address().setEmail(email);
            pickupParcelData.getPickup_address().setPhone(phone);
            pickupParcelData.getPickup_address().setLandline(landline);
            pickupParcelData.getPickup_address().setExt(extension);
            pickupParcelData.getPickup_address().setAddress1(address1);
            pickupParcelData.getPickup_address().setAddress2(address2);
            pickupParcelData.getPickup_address().setCity(city);
            pickupParcelData.getPickup_address().setState(region);
            pickupParcelData.getPickup_address().setCountry(countrySpin);
            pickupParcelData.getPickup_address().setZipCode(pincode);
            pickupParcelData.getPickup_address().setDepartment(department);

            pickupParcelData.getPickup_address().setTaxid(taxid);
            pickupParcelData.getPickup_address().setHome_or_office(home_or_office);
            pickupParcelData.getPickup_address().setWeekend_available(weekend_available);
//                pickupParcelData.getPickup_address().setDelivered_to_guard(delivered_to_guard);
            pickupParcelData.getPickup_address().setSave_option(save_option);
            pickupParcelData.getPickup_address().setCountryid(country_id + "");
            pickupParcelData.getPickup_address().setStateid(state_id + "");
            pickupParcelData.getPickup_address().setCityid(city_id + "");
            return true;
        }


    }

    private void saveData() {
        ((BaseActivity) getActivity()).navigateToFragment(getActivity(), CollectionAddressEditDeliveryFragment.newInstance(pickupParcelData));
    }


    private void getRegionService() throws IonServiceManager.InvalidParametersException {
        progressdialog = SweetAlertUtil.getProgressDialog(activity());
        progressdialog.show();

        String android_version = DeviceInfoUtil.getDeviceAndroidVersionName(activity());
        String brand = DeviceInfoUtil.getBrandName(activity());
        String model = DeviceInfoUtil.getModelName(activity());
        String version_code = "" + DeviceInfoUtil.getSelfVersionCode(activity());
        String userId = "";
        String userType = "";

        if (Utils.isUserLoggedIn(activity())) {
            userId = ""+Utils.getUserId(activity());
            userType =  "" + Utils.getUserType(activity());
        }


        String params[] = {IonServiceManager.KEYS.ANDROID_ID, DeviceInfoUtil.getAndroidID(activity()),IonServiceManager.KEYS.USER_ID, userId,IonServiceManager.KEYS.USERTYPE, userType, IonServiceManager.KEYS.ANDROID_VERSION, android_version, IonServiceManager.KEYS.BRAND, brand, IonServiceManager.KEYS.MODEL, model,IonServiceManager.KEYS.VERSION_CODE, version_code};
        Log.i("params", params.toString());
        IonServiceManager serviceManager = getApp().ionserviceManager;
        serviceManager.postRequestToServerWOprogress(serviceManager.getAddress().LocationService, new IonServiceManager.ResponseCallback(activity()) {
            @Override
            public void onException(final String exception) {
                Log.i("Req Completed", "" + exception);
//                showException(exception);
                progressdialog.dismiss();
                SweetAlertUtil.showWarningWithCallback(activity(), exception, activity().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        ((Activity) activity()).finish();
                        Log.i("MSG", exception.toString());
                    }
                });
                hideProgress();
//                navigateToFragment(getActivity(), CollectionServiceEditFragment.newInstance(pickupParcelData));

            }

            @Override
            public void onResponse(boolean status, String message, Object result) throws JSONException, JsonSyntaxException {

                progressdialog.dismiss();
                JsonObject jsonObject = (JsonObject) result;
//                JsonArray jsonArray = jsonObject.get(IonServiceManager.KEYS.SEVICE_ARRAY).getAsJsonArray();
//                jsonArray.remove(0);

//                List<Services> servicesList = (ArrayList<Services>) new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Services>>() {
//                }.getType());

                parseJsonAndSaveIntoDatabase(jsonObject);


//                navigateToFragment(getActivity(), CollectionServiceEditFragment.newInstance(pickupParcelData));

            }

        }, params);
    }

}
