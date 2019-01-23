package com.inerun.courier.activity_auction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inerun.courier.Exception.PrinterExceptions;
import com.inerun.courier.R;
import com.inerun.courier.adapter.AuctionAddItemAdapter;
import com.inerun.courier.base.AuctionBaseFragment;
import com.inerun.courier.base.DeviceInfoUtil;
import com.inerun.courier.base.SweetAlertUtil;
import com.inerun.courier.constant.Utils;
import com.inerun.courier.helper.ExpandableHeightListView;
import com.inerun.courier.model.AuctionDetail;
import com.inerun.courier.model.AuctionInvoice;
import com.inerun.courier.model.AuctionInvoice_Table;
import com.inerun.courier.model.AuctionItem;
import com.inerun.courier.model.AuctionItem_Table;
import com.inerun.courier.model.BaseModelClass;
import com.inerun.courier.model.SiteConfiguration;
import com.inerun.courier.printer.AppConstant;
import com.inerun.courier.printer.PrinterManager;
import com.inerun.courier.scanner.CameraTestActivity;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONException;

import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vineet on 28/05/18.
 */

public class AuctionCreateInvoiceFragment extends AuctionBaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,TextWatcher {

    private static final int ITEM_SCAN = 108;
    private ExpandableHeightListView itemlistview;
    private LinearLayout addItem;
    private TextView createInvoice;
    public TextView invoice_total;
    private AuctionAddItemAdapter adapter;
    private EditText cust_name_edt, cust_phone_edt, cust_email_edt;

    private CheckBox cash_chk, card_chk, cheque_chk, bank_trans_chk;
    private LinearLayout cash_price_lay, card_price_lay, card_trans_id_lay, cheque_price_lay, cheque_trans_id_lay, bank_price_lay, bank_trans_id_lay;
    private EditText cash_price_edt, card_price_edt, cheque_price_edt, bank_price_edt;
    private EditText card_transid_edt, cheque_transid_edt, bank_transid_edt;

    private float cashPrice = 0, cardPrice = 0, chequePrice = 0, bankTransferPrice = 0;
    private String cardTransactionId = "", chequeTransactionId = "", bankTransactionId = "";
    private PrinterManager printerManager;
    private TextView return_amount_txt;

    public static AuctionCreateInvoiceFragment newInstance() {

        Bundle args = new Bundle();

        AuctionCreateInvoiceFragment fragment = new AuctionCreateInvoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        setShowBackArrow(true);
        return R.layout.auction_create_invoice_fragment;
    }

    @Override
    protected void initView(View rootview) throws IonServiceManager.InvalidParametersException {
        setActionBarTitle(getString(R.string.create_invoice_title));

        printerManager = new PrinterManager(activity());
        addItem = rootview.findViewById(R.id.additem_layout);
        itemlistview = rootview.findViewById(R.id.item_listview);
        createInvoice = rootview.findViewById(R.id.create_invoice_btn);

        cash_chk = rootview.findViewById(R.id.invoice_cash_chk);
        card_chk = rootview.findViewById(R.id.invoice_card_chk);
        cheque_chk = rootview.findViewById(R.id.invoice_cheque_chk);
        bank_trans_chk = rootview.findViewById(R.id.invoice_bank_chk);

        cash_price_lay = rootview.findViewById(R.id.cash_price_lay);

        card_price_lay = rootview.findViewById(R.id.card_price_lay);
        card_trans_id_lay = rootview.findViewById(R.id.card_trans_id_lay);

        cheque_price_lay = rootview.findViewById(R.id.cheque_price_lay);
        cheque_trans_id_lay = rootview.findViewById(R.id.cheque_trans_id_lay);

        bank_price_lay = rootview.findViewById(R.id.bank_price_lay);
        bank_trans_id_lay = rootview.findViewById(R.id.bank_trans_id_lay);

        cust_name_edt = rootview.findViewById(R.id.cust_name_edt);
        cust_phone_edt = rootview.findViewById(R.id.cust_phone_edt);
        cust_email_edt = rootview.findViewById(R.id.cust_email_edt);

        cash_price_edt = rootview.findViewById(R.id.cash_price_edt);
        card_price_edt = rootview.findViewById(R.id.card_price_edt);
        cheque_price_edt = rootview.findViewById(R.id.cheque_price_edt);
        bank_price_edt = rootview.findViewById(R.id.bank_trans_price_edt);
        card_transid_edt = rootview.findViewById(R.id.card_trans_id_edt);
        cheque_transid_edt = rootview.findViewById(R.id.cheque_trans_id_edt);
        bank_transid_edt = rootview.findViewById(R.id.bank_trans_id_edt);

        invoice_total = rootview.findViewById(R.id.invoice_total);
        return_amount_txt = rootview.findViewById(R.id.invoice_return_val);


        setListener();

        setData();
    }

    private void setListener() {
        addItem.setOnClickListener(this);
        createInvoice.setOnClickListener(this);
        cash_chk.setOnCheckedChangeListener(this);
        card_chk.setOnCheckedChangeListener(this);
        cheque_chk.setOnCheckedChangeListener(this);
        bank_trans_chk.setOnCheckedChangeListener(this);


        cash_price_edt.addTextChangedListener(this);
        card_price_edt.addTextChangedListener(this);
        cheque_price_edt.addTextChangedListener(this);
        bank_price_edt.addTextChangedListener(this);

    }


    private void setData() {
        adapter = new AuctionAddItemAdapter(activity(), this);
        itemlistview.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.additem_layout:
                startActivityForResult(new Intent(activity(), CameraTestActivity.class), ITEM_SCAN);
                break;
            case R.id.create_invoice_btn:
                if (validateInvoice()) {
                    Log.i("Save_Invoce", "validation complete");
                    saveInvoice();
                }
                break;
        }
    }

    private void saveInvoice() {
        AuctionDetail auctionDetail = SQLite.select().from(AuctionDetail.class).querySingle();

        SiteConfiguration siteConfiguration = SQLite.select().from(SiteConfiguration.class).querySingle();

        String invoiceId = siteConfiguration.getAuction_invoice_prefix() + String.format("%06d", siteConfiguration.getLain() + 1);
        Log.i("invoiceId", invoiceId);


        List<AuctionItem> auctionItemList = adapter.getAuctionItemList();

        float grandTotal = 0;
        String itemIds = "";

        for (AuctionItem auctionItem : auctionItemList) {
            grandTotal = grandTotal + auctionItem.getAap();
            itemIds = itemIds + auctionItem.getId() + ",";
//            AuctionItem auctionItem1 = SQLite.select().from(AuctionItem.class).where(AuctionItem_Table.item_barcode.eq(auctionItem.getItem_barcode())).querySingle();
            auctionItem.setInvoice_id(invoiceId);
            auctionItem.setAuction_id("" + auctionDetail.getId());
            auctionItem.setSync_status(AuctionItem.SYNC_STATUS_PENDING);
            auctionItem.update();
        }
        itemIds = itemIds.substring(0, itemIds.lastIndexOf(","));

        String custName = "" + cust_name_edt.getText();
        String custPhone = "" + cust_phone_edt.getText();
        String custEmail = "" + cust_email_edt.getText();


        float receivedAmount = cashPrice + cardPrice + chequePrice + bankTransferPrice;


        AuctionInvoice LastauctionInvoice = SQLite.select(AuctionInvoice_Table.id).from(AuctionInvoice.class).orderBy(AuctionInvoice_Table.id, false).limit(1).querySingle();

        final AuctionInvoice auctionInvoice = new AuctionInvoice();

        if (LastauctionInvoice != null) {
            auctionInvoice.setId(LastauctionInvoice.getId() + 1);
        } else {
            auctionInvoice.setId(1);
        }
        auctionInvoice.setAuction_id(auctionDetail.getId());

        auctionInvoice.setInvoice_number(invoiceId);

        auctionInvoice.setAmount_received(receivedAmount);
        auctionInvoice.setActual_receivable_amount(grandTotal);

        auctionInvoice.setCash_amount(cashPrice);

        auctionInvoice.setCard_amount(cardPrice);
        auctionInvoice.setCard_transaction_id(cardTransactionId);

        auctionInvoice.setCheque_amount(chequePrice);
        auctionInvoice.setCheque_transaction_id(chequeTransactionId);

        auctionInvoice.setBank_transfer(bankTransferPrice);
        auctionInvoice.setBank_transfer_transaction_id(bankTransactionId);

        auctionInvoice.setAmount_returned(receivedAmount - grandTotal);

        auctionInvoice.setItem_ids(itemIds);

        auctionInvoice.setCustomer_name(custName);
        auctionInvoice.setCustomer_phone(custPhone);
        auctionInvoice.setCustomer_email(custEmail);

        auctionInvoice.setTotal(grandTotal);
        auctionInvoice.setGrand_total(grandTotal);

        auctionInvoice.setIs_cancel(0);
        if (Utils.isUserLoggedIn(activity())) {
            auctionInvoice.setCreated_by(Integer.parseInt(Utils.getUserId(activity())));
        }
//        auctionInvoice.setCancel_by(0);
        auctionInvoice.setAndroidid(DeviceInfoUtil.getAndroidID(activity()));
        auctionInvoice.setDevice_name(DeviceInfoUtil.getModelName(activity()));
        auctionInvoice.setSync_status(AuctionInvoice.SYNC_STATUS_PENDING);
        auctionInvoice.setStatus(1);
        auctionInvoice.setCreated_on(new Date());


        if (auctionInvoice.save()) {

            siteConfiguration.setLain(siteConfiguration.getLain() + 1);
            siteConfiguration.setSync_status(BaseModelClass.SYNC_STATUS_PENDING);
            siteConfiguration.update();
            SweetAlertUtil.showAlertDialogwithListener(activity(), R.string.print, R.string.create_invoice_success_message, R.string.yes, R.string.no, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();

                    try {
                        String data = "{\"date\":\"01.04.2016\",\"order_num\":\"1330\",\"date_time\":\"11:35\",\"cashier_name\":\"admin\",\"customer_name\":\"Amit Rathi\",\"customer_email\":\"rathi_kota@yahoo.co.in\",\"customer_phone\":\"9988998899\",\"total_incl_vat\":\"900\",\"cash_given\":\"1,100\",\"cash_received\":\"100\"," +
                                "\"payment_method\":\"Cheque\",\"items\":[{\"qty\":\"123456789011\",\"desc\":\"Mobile Phone Etc...\",\"amount\":\"99999\"},{\"qty\":\"120987654321\",\"desc\":\"DSLR Camera\",\"amount\":\"99900\"}]}";
                        data = preparePrintData(auctionInvoice, adapter.getAuctionItemList());
                        Log.i("Printer", "Printer invoice" + data);
                        //                printerService.addPrintJob(AppConstant.Keys.TYPE_PRINT_RECEIPT_ONLY, data);
                        Log.i("Printer", "Printer invoice" + data);

                        Log.i("Printer", "Printer invoice" + data);
                        printerManager.addRequestToPrinter(AppConstant.Keys.TYPE_PRINT_RECEIPT_ONLY, data);
                    } catch (PrinterExceptions printerExceptions) {
                        printerExceptions.printStackTrace();
                        SweetAlertUtil.showAlertDialog(activity(), printerExceptions.toString());


                    } catch (JSONException printerExceptions) {
                        printerExceptions.printStackTrace();
                        SweetAlertUtil.showAlertDialog(activity(), getString(R.string.invoice_detail_json_date_error));
//
//
//        }

//old

                    } catch (Exception printerExceptions) {
                        printerExceptions.printStackTrace();
                        SweetAlertUtil.showAlertDialog(activity(), printerExceptions.toString());


                    }
                    popFragment();

                }

            }, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    popFragment();

                }
            }).show();


        } else {
//            Toast.makeText(activity(),"Something went wrong. pls try again.", Toast.LENGTH_LONG).show();
//            SweetAlertUtil.showSweetMessageToast(activity(), getString(R.string.invoice_error_msg));
            SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.invoice_error_msg));
        }


    }

    private boolean validateInvoice() {

        List<AuctionItem> auctionItemList = adapter.getAuctionItemList();


        String custName = "" + cust_name_edt.getText();
        String custPhone = "" + cust_phone_edt.getText();
        String custEmail = "" + cust_email_edt.getText();

        boolean cashChk = cash_chk.isChecked();
        boolean cardChk = card_chk.isChecked();
        boolean chequeChk = cheque_chk.isChecked();
        boolean bankTransChk = bank_trans_chk.isChecked();


        if (!(auctionItemList != null && auctionItemList.size() > 0)) {
//            Toast.makeText(activity(),getString(R.string.item_scan_error_msg),Toast.LENGTH_LONG).show();
            SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.item_scan_error_msg));
            return false;
        } else if (!isValidString(custName)) {
//            Toast.makeText(activity(),"pls enter Customer Name",Toast.LENGTH_LONG).show();
            SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.customer_name_error_msg));
            return false;
        } else if (!isValidString(custPhone)) {
//            Toast.makeText(activity(),"pls enter Customer Phone number",Toast.LENGTH_LONG).show();
            SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.customer_phone_error_msg));
            return false;
        } else if (!isValidString(custEmail)) {
//            Toast.makeText(activity(),"pls enter Customer email",Toast.LENGTH_LONG).show();
            SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.customer_email_error_msg));
            return false;
        } else if (!cashChk && !cardChk && !chequeChk && !bankTransChk) {
//            Toast.makeText(activity(),"pls select one payment method.",Toast.LENGTH_LONG).show();
            SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.payment_mode_error_msg));
            return false;
        } else {

        }

        if (cashChk) {
            String cashPriceStr = "" + cash_price_edt.getText();
            if (!isValidString(cashPriceStr)) {
//                Toast.makeText(activity(),"pls enter cash value.",Toast.LENGTH_LONG).show();
                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.cash_val_error_msg));
                return false;
            } else {
                cashPrice = Float.parseFloat(cashPriceStr);
            }

        }
        if (cardChk) {

            String cardPriceStr = "" + card_price_edt.getText();
            String cardTransactionIdStr = "" + card_transid_edt.getText();
            if (!(cardPriceStr != null && cardPriceStr.length() > 0)) {
//                Toast.makeText(activity(),"pls enter card value.",Toast.LENGTH_LONG).show();
                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.card_val_error_msg));
                return false;
            } else if (!(cardTransactionIdStr != null && cardTransactionIdStr.length() > 0)) {
//                Toast.makeText(activity(),"pls enter card Transaction id.",Toast.LENGTH_LONG).show();
                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.card_transactionid_error_msg));
                return false;
            } else {
                cardPrice = Float.parseFloat(cardPriceStr);
                cardTransactionId = cardTransactionIdStr;
            }

        }
        if (chequeChk) {

            String chequePriceStr = "" + cheque_price_edt.getText();
            String chequeTransactionIdStr = "" + cheque_transid_edt.getText();

            if (!(chequePriceStr != null && chequePriceStr.length() > 0)) {
//                Toast.makeText(activity(),"pls enter cheque value.",Toast.LENGTH_LONG).show();
                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.cheque_val_error_msg));
                return false;
            } else if (!(chequeTransactionIdStr != null && chequeTransactionIdStr.length() > 0)) {
//                Toast.makeText(activity(),"pls enter cheque Transaction id.",Toast.LENGTH_LONG).show();
                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.cheque_transactionid_error_msg));
                return false;
            } else {
                chequePrice = Float.parseFloat(chequePriceStr);
                chequeTransactionId = chequeTransactionIdStr;
            }
        }
        if (bankTransChk) {

            String bankTransPriceStr = "" + bank_price_edt.getText();
            String bankTransactionIdStr = "" + bank_transid_edt.getText();
            if (!(bankTransPriceStr != null && bankTransPriceStr.length() > 0)) {
//                Toast.makeText(activity(),"pls enter Bank Transfer value.",Toast.LENGTH_LONG).show();
                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.bank_val_error_msg));
                return false;
            } else if (!(bankTransactionIdStr != null && bankTransactionIdStr.length() > 0)) {
//                Toast.makeText(activity(),"pls enter Bank Transfer Transaction id.",Toast.LENGTH_LONG).show();
                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.bank_transactionid_error_msg));
                return false;
            } else {
                bankTransferPrice = Float.parseFloat(bankTransPriceStr);
                bankTransactionId = bankTransactionIdStr;
            }
        }


        float grandTotal = 0;
        for (AuctionItem auctionItem : auctionItemList) {
            grandTotal = grandTotal + auctionItem.getAap();
        }

        float receivedAmount = cashPrice + cardPrice + chequePrice + bankTransferPrice;

        if (grandTotal > receivedAmount) {
            SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.invoice_amount_error_msg));
            return false;
        }

        return true;

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.invoice_cash_chk:
                if (isChecked) {
                    cash_price_lay.setVisibility(View.VISIBLE);
                } else {
                    cash_price_lay.setVisibility(View.GONE);
                    cash_price_edt.setText("");
                    setReturnAmount();
                }
                break;

            case R.id.invoice_card_chk:
                if (isChecked) {
                    card_price_lay.setVisibility(View.VISIBLE);
                    card_trans_id_lay.setVisibility(View.VISIBLE);
                } else {
                    card_price_lay.setVisibility(View.GONE);
                    card_trans_id_lay.setVisibility(View.GONE);
                    card_price_edt.setText("");
                    card_transid_edt.setText("");
                    setReturnAmount();
                }
                break;

            case R.id.invoice_cheque_chk:
                if (isChecked) {
                    cheque_price_lay.setVisibility(View.VISIBLE);
                    cheque_trans_id_lay.setVisibility(View.VISIBLE);
                } else {
                    cheque_price_lay.setVisibility(View.GONE);
                    cheque_trans_id_lay.setVisibility(View.GONE);
                    cheque_price_edt.setText("");
                    cheque_transid_edt.setText("");
                    setReturnAmount();
                }
                break;

            case R.id.invoice_bank_chk:
                if (isChecked) {
                    bank_price_lay.setVisibility(View.VISIBLE);
                    bank_trans_id_lay.setVisibility(View.VISIBLE);
                } else {
                    bank_price_lay.setVisibility(View.GONE);
                    bank_trans_id_lay.setVisibility(View.GONE);
                    bank_price_edt.setText("");
                    bank_transid_edt.setText("");
                    setReturnAmount();
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case ITEM_SCAN:

                if (resultCode == Activity.RESULT_OK && data.hasExtra(CameraTestActivity.INTENT_BARCODE_VALUE)) {

                    String barcode = data.getStringExtra(CameraTestActivity.INTENT_BARCODE_VALUE);

                    Log.i("Query", SQLite.select().from(AuctionItem.class).where(AuctionItem_Table.item_barcode.eq(barcode)).and(AuctionItem_Table.invoice_id.isNull()).getQuery());
                    AuctionItem auctionItem = SQLite.select().from(AuctionItem.class).where(AuctionItem_Table.item_barcode.eq(barcode)).and(AuctionItem_Table.invoice_id.isNull()).querySingle();
//                    AuctionItem auctionItem = SQLite.select().from(AuctionItem.class).where(AuctionItem_Table.item_barcode.eq(barcode)).and(AuctionItem_Table.invoice_id.eq("")).querySingle();

                    if (auctionItem != null) {
                        if (adapter.getAuctionItemList() != null && adapter.getAuctionItemList().size() > 0) {
                            List<AuctionItem> auctionItemList = adapter.getAuctionItemList();


                            int index = auctionItemList.indexOf(new AuctionItem(auctionItem.getItem_barcode()) {
                                @Override
                                public boolean equals(Object obj) {
                                    return getItem_barcode().equals(((AuctionItem) obj).getItem_barcode());
                                }
                            });

                            if (index == -1) {
                                auctionItem.setAap(auctionItem.getMap());
                                adapter.addItemToList(auctionItem);

                            } else {
//                                Toast.makeText(activity(), "This item already scanned", Toast.LENGTH_LONG).show();
                                SweetAlertUtil.showSweetErrorToast(activity(), getString(R.string.item_scanned_error_msg));
                            }


                        } else {
                            auctionItem.setAap(auctionItem.getMap());
                            adapter.addItemToList(auctionItem);
                        }

                    } else {
//                        Toast.makeText(activity(), "Please Enter valid Barcode.", Toast.LENGTH_LONG).show();
                        SweetAlertUtil.showAlertDialog(activity(), getString(R.string.valid_barcode_error_msg));
                        Log.i("Valid Barcode", barcode + "Barcode not valid");
                    }
                }
                break;
        }

    }


    @Override
    public void onResume() {
        super.onResume();
//        registerReceiver(uiUpdated, new IntentFilter("LOG_UPDATED"));

//old
//        startPrinterService();
        try {
            if (printerManager.isACTIVE())
                printerManager.startPrinter();
        } catch (PrinterExceptions printerExceptions) {
            printerExceptions.printStackTrace();

        }
//        readerManager.startReader();
//        registerReceiver(broadcastReceiver, new IntentFilter(AppConstant.ReaderCommands.MA_RECEIVER));
    }


    @Override
    public void onPause() {
        super.onPause();


        try {

//            mReaderBoundService.addRequest(SharedPref.IDS.STOP_POLLING_REQUEST,null);
//            readerManager.stopReader();
//            unregisterReceiver(broadcastReceiver);
//            unregisterReceiver(uiUpdated);

            printerManager.stopPrinter();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i("afterTextChanged", "Enter pressed");
        setReturnAmount();
    }


    private void setReturnAmount(){

        float cash = 0;
        float card = 0;
        float cheque = 0;
        float bank = 0;

        List<AuctionItem> auctionItemList = adapter.getAuctionItemList();
        float grandTotal = 0;
        for (AuctionItem auctionItem : auctionItemList) {
            grandTotal = grandTotal + auctionItem.getAap();
        }

        String cashPriceStr = "" + cash_price_edt.getText();
        String cardPriceStr = "" + card_price_edt.getText();
        String chequePriceStr = "" + cheque_price_edt.getText();
        String bankTransPriceStr = "" + bank_price_edt.getText();

        if(cashPriceStr != null && cashPriceStr.length() > 0){
            cash = Integer.parseInt(cashPriceStr);
        }
        if(cardPriceStr != null && cardPriceStr.length() > 0){
            card = Integer.parseInt(cardPriceStr);
        }
        if(chequePriceStr != null && chequePriceStr.length() > 0){
            cheque = Integer.parseInt(chequePriceStr);
        }
        if(bankTransPriceStr != null && bankTransPriceStr.length() > 0){
            bank = Integer.parseInt(bankTransPriceStr);
        }

        float receivedAmount = cash + card + cheque + bank;

        float returnAmount = receivedAmount - grandTotal;

        if(returnAmount >= 0) {

            return_amount_txt.setText(getString(R.string.auction_collection_val, "" + returnAmount));
        }else{
            return_amount_txt.setText(getString(R.string.auction_collection_val, "" + 0.0));
        }

    }


}
