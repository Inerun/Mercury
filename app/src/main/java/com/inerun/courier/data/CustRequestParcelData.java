package com.inerun.courier.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vineet on 26/05/18.
 */

public class CustRequestParcelData implements Serializable{

    private boolean status;
    private int total;
    private String message;

    private ArrayList<RequestList> requestlist;


    public boolean isStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<RequestList> getRequestlist() {
        return requestlist;
    }

    

    public class RequestList implements Serializable{

        private String id;
        private String barcode;
        private String parcel_type;
        private String customer_id;
        private String amount;
        private String payment_type;
        private String comment;
        private String source;
        private String description;
        private String weight;
        private String customerusername;
        private String user_unique_id;
        private String first_name;
        private String last_name;
        private String cityname;
        private String destinationcityname;
        private String wh_barcode;

        public String getId() {
            return id;
        }

        public String getBarcode() {
            return barcode;
        }

        public String getParcel_type() {
            return parcel_type;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public String getAmount() {
            return amount;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public String getComment() {
            return comment;
        }

        public String getSource() {
            return source;
        }

        public String getDescription() {
            return description;
        }

        public String getWeight() {
            return weight;
        }

        public String getCustomerusername() {
            return customerusername;
        }

        public String getUser_unique_id() {
            return user_unique_id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getCityname() {
            return cityname;
        }

        public String getDestinationcityname() {
            return destinationcityname;
        }

        public String getWh_barcode() {
            return wh_barcode;
        }
    }


}
