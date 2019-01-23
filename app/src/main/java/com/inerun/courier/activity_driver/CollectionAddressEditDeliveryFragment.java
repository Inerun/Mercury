package com.inerun.courier.activity_driver;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.inerun.courier.CourierApplication;
import com.inerun.courier.Exception.ExceptionMessages;
import com.inerun.courier.R;
import com.inerun.courier.base.BaseActivity;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.constant.UrlConstants;
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
import java.util.List;
import java.util.Map;

/**
 * Created by vineet on 01/01/19.
 */

public class CollectionAddressEditDeliveryFragment extends BaseFragment implements View.OnClickListener {

    private EditText fname_edt, lname_edt, deparment_edt, email_edt, phone_edt, landline_edt, extension_edt, address1_edt, address2_edt, region_edt, city_edt, pincode_edt, taxid_edt;
    public MaterialBetterSpinner country_spinner, region_spinner, city_spinner;
    private ArrayAdapter<String> counrtyarrayAdapter;
    private ArrayAdapter<String> regionarrayAdapter;
    private ArrayAdapter<String> cityarrayAdapter;
    private Button submit, cancel_btn, next_btn;
    //    private Client client;
    private List<Country> countryList;
    private List<Region> regionList;
    private List<City> cityList;

    private RadioButton home_rBtn, office_rbtn, weekend_yes_rbtn, weekend_no_rbtn, guard_yes_rbtn, guard_no_rbtn, save_yes_rbtn, save_no_rbtn;
    private LinearLayout guard_received_lay;

    private PickupParcelData pickupParcelData;

    public static CollectionAddressEditDeliveryFragment newInstance(PickupParcelData pickupParcelData) {

        Bundle args = new Bundle();

        CollectionAddressEditDeliveryFragment fragment = new CollectionAddressEditDeliveryFragment();
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
        return R.layout.activity_collection_delivery_address_edit;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        ((CollectionAddressEditActivity) getActivity()).clickingOnTab(1);
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
        guard_yes_rbtn = root.findViewById(R.id.guard_yes_rbtn);
        guard_no_rbtn = root.findViewById(R.id.guard_no_rbtn);
        save_yes_rbtn = root.findViewById(R.id.save_yes_rbtn);
        save_no_rbtn = root.findViewById(R.id.save_no_rbtn);
        guard_received_lay = root.findViewById(R.id.guard_received_lay);

        submit = root.findViewById(R.id.submit);
        next_btn = root.findViewById(R.id.next_btn);
        cancel_btn = root.findViewById(R.id.cancel_btn);

        submit.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

//        getLocationsDataService();

        setCountry();
        setData();


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
                region_edt.setText(pickupParcelData.getDelivery_address().getState());
                city_edt.setText(pickupParcelData.getDelivery_address().getCity());

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
                state = pickupParcelData.getDelivery_address().getState();

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
                citystr = pickupParcelData.getDelivery_address().getCity();


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


        guard_received_lay.setVisibility(View.VISIBLE);

        fname_edt.setText(pickupParcelData.getDelivery_address().getfName());
        lname_edt.setText(pickupParcelData.getDelivery_address().getlName());
        deparment_edt.setText(pickupParcelData.getDelivery_address().getDepartment());
        email_edt.setText(pickupParcelData.getDelivery_address().getEmail());
        phone_edt.setText(pickupParcelData.getDelivery_address().getPhone());
        landline_edt.setText(pickupParcelData.getDelivery_address().getLandline());
        extension_edt.setText(pickupParcelData.getDelivery_address().getExt());
        address1_edt.setText(pickupParcelData.getDelivery_address().getAddress1());
        address2_edt.setText(pickupParcelData.getDelivery_address().getAddress2());

        String pincode = pickupParcelData.getDelivery_address().getZipCode();
        if (pincode != null) {
            pincode_edt.setText(pincode);
        }

        String taxId = pickupParcelData.getDelivery_address().getTaxid();
        if (taxId != null) {
            taxid_edt.setText(taxId);
        }

        if (pickupParcelData.getDelivery_address().getHome_or_office() == 0) {
            home_rBtn.setChecked(false);
            office_rbtn.setChecked(true);
        } else {
            home_rBtn.setChecked(true);
            office_rbtn.setChecked(false);
        }

        if (pickupParcelData.getDelivery_address().getWeekend_available() == 0) {
            weekend_yes_rbtn.setChecked(false);
            weekend_no_rbtn.setChecked(true);
        } else {
            weekend_yes_rbtn.setChecked(true);
            weekend_no_rbtn.setChecked(false);
        }

        if (pickupParcelData.getDelivery_address().getDelivered_to_guard() == 0) {
            guard_yes_rbtn.setChecked(false);
            guard_no_rbtn.setChecked(true);
        } else {
            guard_yes_rbtn.setChecked(true);
            guard_no_rbtn.setChecked(false);
        }

        try {

            ArrayAdapter myAdap = (ArrayAdapter) country_spinner.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(pickupParcelData.getDelivery_address().getCountry());
            Log.i("spinnerPosition", "" + spinnerPosition);

            if (spinnerPosition != -1) {
                //set the default according to value

                Country country = SQLite.select().from(Country.class).where(Country_Table.country_name.eq(pickupParcelData.getDelivery_address().getCountry())).querySingle();
                Log.i("setCountry", SQLite.select().from(Country.class).where(Country_Table.country_name.eq(pickupParcelData.getDelivery_address().getCountry())).getQuery());
                if (country != null) {
                    country_spinner.setText(pickupParcelData.getDelivery_address().getCountry());
                    setRegionAndCity(country, true);
                }
            } else {
//                    if (pickupParcelData.getDelivery_address().getCountry() != null)
//                        country_spinner.setText(pickupParcelData.getDelivery_address().getCountry());
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
            case R.id.cancel_btn:
                activity().finish();
                break;
            case R.id.next_btn:
                if (validateData()) {
                    saveData();
                }
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
        int delivered_to_guard = guard_yes_rbtn.isChecked() ? 1 : 0;
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
            pickupParcelData.getDelivery_address().setfName(fname);
            pickupParcelData.getDelivery_address().setlName(lname);
            pickupParcelData.getDelivery_address().setEmail(email);
            pickupParcelData.getDelivery_address().setPhone(phone);
            pickupParcelData.getDelivery_address().setLandline(landline);
            pickupParcelData.getDelivery_address().setExt(extension);
            pickupParcelData.getDelivery_address().setAddress1(address1);
            pickupParcelData.getDelivery_address().setAddress2(address2);
            pickupParcelData.getDelivery_address().setCity(city);
            pickupParcelData.getDelivery_address().setState(region);
            pickupParcelData.getDelivery_address().setCountry(countrySpin);
            pickupParcelData.getDelivery_address().setZipCode(pincode);
            pickupParcelData.getDelivery_address().setDepartment(department);

            pickupParcelData.getDelivery_address().setTaxid(taxid);
            pickupParcelData.getDelivery_address().setHome_or_office(home_or_office);
            pickupParcelData.getDelivery_address().setWeekend_available(weekend_available);
            pickupParcelData.getDelivery_address().setDelivered_to_guard(delivered_to_guard);
            pickupParcelData.getDelivery_address().setSave_option(save_option);
            pickupParcelData.getDelivery_address().setCountryid(country_id + "");
            pickupParcelData.getDelivery_address().setStateid(state_id + "");
            pickupParcelData.getDelivery_address().setCityid(city_id + "");
            return true;
        }


    }

    private void saveData() {
        DeliveryShipmentViewFragment shipmentviewfragment = DeliveryShipmentViewFragment.newInstance(pickupParcelData);
        ((CollectionAddressEditActivity) getActivity()).setShipmentFragment(shipmentviewfragment);
        ((BaseActivity) getActivity()).navigateToFragment(getActivity(), shipmentviewfragment);
    }



//    private void saveData() {
//        ((BaseActivity) getActivity()).navigateToFragment(getActivity(), DeliveryShipmentViewFragment.newInstance(pickupParcelData));
//    }

}
