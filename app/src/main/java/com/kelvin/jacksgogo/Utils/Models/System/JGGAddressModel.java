package com.kelvin.jacksgogo.Utils.Models.System;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGAddressModel {

    private String Unit;
    private String Floor;
    private String Address;
    private String City;
    private String State;
    private String PostalCode;
    private double Lat;
    private double Lon;
    private String CountryCode;
    private String fullAddress;

    public String getFullAddress() {
        fullAddress = "";
        if (getFloor() != null) fullAddress = fullAddress + getFloor() + " ";
        if (getUnit() != null) fullAddress = fullAddress + getUnit() + " ";
        if (getAddress() != null) fullAddress = fullAddress + getAddress() + ", ";
        if (getCity() != null) fullAddress = fullAddress + getCity() + ", ";
        if (getState() != null) fullAddress = fullAddress + getState() + ", ";
        if (getPostalCode() != null) fullAddress = fullAddress + getPostalCode();
        return fullAddress;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }
}
