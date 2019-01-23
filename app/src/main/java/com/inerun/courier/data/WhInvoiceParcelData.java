package com.inerun.courier.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vineet on 2/20/2017.
 */

public class WhInvoiceParcelData implements Serializable {
    private boolean status;
    private ArrayList<Invoice> invoiceData;
    private ArrayList<CustomerExecutiveData> executivedata;

    public ArrayList<CustomerExecutiveData> getExecutivedata() {
        return executivedata;
    }

    public WhInvoiceParcelData(ArrayList<Invoice> invoiceData) {
        this.invoiceData = invoiceData;
    }

    public WhInvoiceParcelData() {
    }

    public boolean isStatus() {
        return status;
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
