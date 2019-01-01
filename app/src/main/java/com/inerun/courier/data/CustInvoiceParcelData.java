package com.inerun.courier.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vineet on 2/20/2017.
 */

public class CustInvoiceParcelData implements Serializable {
    private boolean status;
    private int total;
    private int count;
    private ArrayList<Invoice> invoiceData;


    public CustInvoiceParcelData(ArrayList<Invoice> invoiceData) {
        this.invoiceData = invoiceData;
    }

    public CustInvoiceParcelData() {
    }

    public boolean isStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<Invoice> getInvoiceData() {
        return invoiceData;
    }

    public class Invoice implements Serializable{
        private String invoice_number;
        private ArrayList<ParcelListingData.ParcelData> parcelData;

        public Invoice(String invoice_number, ArrayList<ParcelListingData.ParcelData> parcelData) {
            this.invoice_number = invoice_number;
            this.parcelData = parcelData;
        }

        public String getInvoice_number() {
            return invoice_number;
        }

        public ArrayList<ParcelListingData.ParcelData> getParcelData() {
            return parcelData;
        }
    }


}
