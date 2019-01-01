package com.inerun.courier.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vineet on 9/28/2017.
 */

public class PickupAddress implements Serializable {
    private String fName;
    private String lName;
    private String email;
    @SerializedName("mobile_no")
    private String phone;
    private String landline;
    private String ext;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    @SerializedName("contact_person")
    private String department;
    private String company;



    public PickupAddress(String fName, String lName, String phone, String landline, String ext, String address1, String address2, String city, String state, String country, String zipCode) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.landline = landline;
        this.ext = ext;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public PickupAddress(String fName, String lName, String email, String phone, String landline, String ext, String address1, String address2, String city, String state, String country, String zipCode) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
        this.landline = landline;
        this.ext = ext;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public PickupAddress(String fName, String lName, String email, String phone, String landline, String ext, String address1, String address2, String city, String state, String country, String zipCode, String department, String company) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
        this.landline = landline;
        this.ext = ext;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.department = department;
        this.company = company;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
