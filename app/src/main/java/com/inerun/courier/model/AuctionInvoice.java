package com.inerun.courier.model;

import com.inerun.courier.sqldb.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.ArrayList;

/**
 * Created by vineet on 30/05/18.
 */

@Table(database = AppDatabase.class, name = "auction_invoice")
public class AuctionInvoice extends BaseModelClass {


    private final String PAYMENT_TYPE_CHEQUE = "Cq";
    private final String PAYMENT_TYPE_CASH = "Ch";
    private final String PAYMENT_TYPE_CARD = "Cr";
    private final String PAYMENT_TYPE_BANK_TRANSFER = "Bt";
    private final String PAYMENT_TYPE_CHEQUE_FULL = "Cheque";
    private final String PAYMENT_TYPE_CASH_FULL = "Cash";
    private final String PAYMENT_TYPE_CARD_FULL = "Card";
    private final String PAYMENT_TYPE_BANK_TRANSFER_FULL = "Bank";
    private String paymentmethod;
    @Column

    private String invoice_number;
    @Column
    private int auction_id;
    @Column
    private float amount_received;
    @Column
    private float actual_receivable_amount;
    @Column
    private float cash_amount;
    @Column
    private float card_amount;
    @Column
    private String card_transaction_id;
    @Column
    private float cheque_amount;
    @Column
    private String cheque_transaction_id;
    @Column
    private float bank_transfer;
    @Column
    private String bank_transfer_transaction_id;
    @Column
    private float amount_returned;
    @Column
    private String item_ids;
    @Column
    private String customer_name;
    @Column
    private String customer_email;
    @Column
    private String customer_phone;
    @Column
    private float total;
    @Column
    private float grand_total;
    @Column
    private int is_cancel;
    @Column
    private int created_by;
    @Column
    private int cancel_by;
    @Column
    private String androidid;
    @Column
    private String device_name;
    @Column(defaultValue = "" + SYNC_STATUS_SUCCESS)
    private int sync_status;

    private int sum;


    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public int getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(int auction_id) {
        this.auction_id = auction_id;
    }

    public float getCash_amount() {
        return cash_amount;
    }

    public void setCash_amount(float cash_amount) {
        this.cash_amount = cash_amount;
    }

    public float getCard_amount() {
        return card_amount;
    }

    public void setCard_amount(float card_amount) {
        this.card_amount = card_amount;
    }

    public String getCard_transaction_id() {
        return card_transaction_id;
    }

    public void setCard_transaction_id(String card_transaction_id) {
        this.card_transaction_id = card_transaction_id;
    }

    public float getCheque_amount() {
        return cheque_amount;
    }

    public void setCheque_amount(float cheque_amount) {
        this.cheque_amount = cheque_amount;
    }

    public String getCheque_transaction_id() {
        return cheque_transaction_id;
    }

    public void setCheque_transaction_id(String cheque_transaction_id) {
        this.cheque_transaction_id = cheque_transaction_id;
    }

    public float getBank_transfer() {
        return bank_transfer;
    }

    public void setBank_transfer(float bank_transfer) {
        this.bank_transfer = bank_transfer;
    }

    public String getBank_transfer_transaction_id() {
        return bank_transfer_transaction_id;
    }

    public void setBank_transfer_transaction_id(String bank_transfer_transaction_id) {
        this.bank_transfer_transaction_id = bank_transfer_transaction_id;
    }

    public float getAmount_returned() {
        return amount_returned;
    }

    public void setAmount_returned(float amount_returned) {
        this.amount_returned = amount_returned;
    }

    public String getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String item_ids) {
        this.item_ids = item_ids;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(float grand_total) {
        this.grand_total = grand_total;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public float getAmount_received() {
        return amount_received;
    }

    public void setAmount_received(float amount_received) {
        this.amount_received = amount_received;
    }

    public boolean isCashPayment() {
        if (getCash_amount() == 0.0f || getAmount_returned() == 0.0f) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isChequePayment() {
        if (getCheque_amount() == 0.0f) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isCardPayment() {
        if (getCard_amount() == 0.0f) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBankTransfer() {
        if (getBank_transfer() == 0.0f) {
            return false;
        } else {
            return true;
        }
    }

    public String getPaymentMethods() {
        if (paymentmethod == null || paymentmethod.length() == 0) {
            ArrayList<String> paymentmethods = new ArrayList();
            if (getCash_amount() != 0.0f || getAmount_returned() != 0.0f) {
                paymentmethods.add(PAYMENT_TYPE_CASH);
            }
            if (getCard_amount() != 0.0f) {
                paymentmethods.add(PAYMENT_TYPE_CARD);
            }
            if (getBank_transfer() != 0.0f) {
                paymentmethods.add(PAYMENT_TYPE_BANK_TRANSFER);
            }
            if (getCheque_amount() != 0.0f) {
                paymentmethods.add(PAYMENT_TYPE_CHEQUE);
            }

            if (paymentmethods.size() == 1) {

                switch (paymentmethods.get(0)) {
                    case PAYMENT_TYPE_CASH:
                        paymentmethod = PAYMENT_TYPE_CASH_FULL;
                        break;
                    case PAYMENT_TYPE_CARD:
                        paymentmethod = PAYMENT_TYPE_CARD_FULL;
                        break;
                    case PAYMENT_TYPE_CHEQUE:
                        paymentmethod = PAYMENT_TYPE_CHEQUE_FULL;
                        break;
                    case PAYMENT_TYPE_BANK_TRANSFER:
                        paymentmethod = PAYMENT_TYPE_BANK_TRANSFER_FULL;
                        break;
                }

            } else {

                for (int i = 0; i < paymentmethods.size(); i++) {
                    String paymenttype = paymentmethods.get(i);
                    if (i == 0) {
                        paymentmethod = paymenttype;
                    } else {
                        paymentmethod = paymentmethod + "+" + paymenttype;
                    }
                }
            }
        }

        return paymentmethod;


    }


    public float getActual_receivable_amount() {
        return actual_receivable_amount;
    }

    public void setActual_receivable_amount(float actual_receivable_amount) {
        this.actual_receivable_amount = actual_receivable_amount;
    }

    public int getIs_cancel() {
        return is_cancel;
    }

    public void setIs_cancel(int is_cancel) {
        this.is_cancel = is_cancel;
    }

    public int getCancel_by() {
        return cancel_by;
    }

    public void setCancel_by(int cancel_by) {
        this.cancel_by = cancel_by;
    }

    public String getAndroidid() {
        return androidid;
    }

    public void setAndroidid(String androidid) {
        this.androidid = androidid;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
