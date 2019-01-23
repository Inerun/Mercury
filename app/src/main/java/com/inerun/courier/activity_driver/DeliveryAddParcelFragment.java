package com.inerun.courier.activity_driver;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inerun.courier.R;
import com.inerun.courier.base.BaseFragment;
import com.inerun.courier.data.PickParcelDetailData;
import com.inerun.courier.data.PickupParcelData;
import com.inerun.courier.helper.DIHelper;

/**
 * Created by vineet on 9/27/2017.
 */

public class DeliveryAddParcelFragment extends BaseFragment implements View.OnClickListener {

    private EditText user_fname_edt, user_lname_edt, user_email_edt, user_phone_edt, user_landline_edt, user_ext_edt;
    private EditText parcel_length_edt, parcel_height_edt, parcel_width_edt, parcel_actual_weight_edt, parcel_price_edt, parcel_descrip_edt, parcel_special_ins_edt;
    private TextView pickup_address_txt, delivery_address_txt, parcel_implication_txt, parcel_volum_weight_txt;
    private Button submit_btn;
    private Context context;


    public static DeliveryAddParcelFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DeliveryAddParcelFragment fragment = new DeliveryAddParcelFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int inflateView() {
        return R.layout.delivery_pickup_add_parcel;
    }

    @Override
    public void customOnCreateView(View root, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws Exception {
        context = activity();
        setShowBackArrow(true);
        initView(root);


    }

    private void initView(View root) {
        /*User Details*/
        user_fname_edt = (EditText) root.findViewById(R.id.pickup_form_fname);
        user_lname_edt = (EditText) root.findViewById(R.id.pickup_form_lname);
        user_email_edt = (EditText) root.findViewById(R.id.pickup_form_email);
        user_phone_edt = (EditText) root.findViewById(R.id.pickup_form_phone);
        user_landline_edt = (EditText) root.findViewById(R.id.pickup_form_landline);
        user_ext_edt = (EditText) root.findViewById(R.id.pickup_form_extension);

        /*Address Detail pickup and Delivery*/
        pickup_address_txt = (TextView) root.findViewById(R.id.pickup_address_text);
        delivery_address_txt = (TextView) root.findViewById(R.id.delivery_address_text);

        /*Parcel Details*/
        parcel_length_edt = (EditText) root.findViewById(R.id.pickup_parcel_length);
        parcel_height_edt = (EditText) root.findViewById(R.id.pickup_parcel_height);
        parcel_width_edt = (EditText) root.findViewById(R.id.pickup_parcel_width);
        parcel_volum_weight_txt = (TextView) root.findViewById(R.id.pickup_parcel_volumetric_weight_val);
        parcel_actual_weight_edt = (EditText) root.findViewById(R.id.pickup_parcel_actual_weight_val);
        parcel_implication_txt = (TextView) root.findViewById(R.id.pickup_parcel_implication_val);
        parcel_price_edt = (EditText) root.findViewById(R.id.pickup_parcel_value);
        parcel_descrip_edt = (EditText) root.findViewById(R.id.pickup_parcel_description);
        parcel_special_ins_edt = (EditText) root.findViewById(R.id.pickup_parcel_special_instruction);

        submit_btn = (Button) root.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
        pickup_address_txt.setOnClickListener(this);
        delivery_address_txt.setOnClickListener(this);


    }

    @Override
    protected String getAnalyticsName() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pickup_address_text:
                openPickupAddressPopup();
                break;
            case R.id.delivery_address_text:
                openDeliveryAddressPopup();
                break;
            case R.id.submit_btn:
                submit();
                break;

        }


    }

    private void openDeliveryAddressPopup() {
    }

    private void openPickupAddressPopup() {
    }

    private void submit() {
        String user_fname = user_fname_edt.getText() + "";
        String user_lname = user_lname_edt.getText() + "";
        String user_email = user_email_edt.getText() + "";
        String user_phone = user_phone_edt.getText() + "";
        String user_landline = user_landline_edt.getText() + "";
        String user_ext = user_ext_edt.getText() + "";

        String pickup_address = pickup_address_txt.getText() + "";
        String delivery_address = delivery_address_txt.getText() + "";

        String parcel_length = parcel_length_edt.getText() + "";
        String parcel_height = parcel_height_edt.getText() + "";
        String parcel_width = parcel_width_edt.getText() + "";
        String parcel_volum_weight = parcel_volum_weight_txt.getText() + "";
        String parcel_actual_weight = parcel_actual_weight_edt.getText() + "";
        String parcel_implication = parcel_implication_txt.getText() + "";
        String parcel_price = parcel_price_edt.getText() + "";
        String parcel_descrip = parcel_descrip_edt.getText() + "";
        String parcel_special_ins = parcel_special_ins_edt.getText() + "";

        PickParcelDetailData pickParcelDetailData = new PickParcelDetailData(parcel_length,parcel_height, parcel_width, parcel_volum_weight, parcel_actual_weight, parcel_implication, parcel_price, parcel_descrip, parcel_special_ins);

        PickupParcelData pickupParcelData = new PickupParcelData("1111",user_fname,  user_lname, user_email, user_phone, user_landline, user_ext, null, null,pickParcelDetailData);

        if(DIHelper.validateLoginPickupAddParcel(context, pickupParcelData)){

        }
    }


}
