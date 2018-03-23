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
    private String Street;
    private String PlaceName;
    private String CountryCode;
    private double Lat;
    private double Lon;
    private boolean IsShowFullAddress;

    private String fullAddress;

    public String getFullAddress() {
        fullAddress = "";
        if (getStreet() == null) {
            fullAddress = fullAddress + getAddress() + ", " + getPostalCode();
        } else {
            if (getUnit() != null)
                fullAddress = fullAddress + getUnit() + " ";
            fullAddress = fullAddress + getStreet() + ", ";
            if (getPostalCode() != null)
                fullAddress = fullAddress + getPostalCode();
        }
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

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
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

    public boolean isShowFullAddress() {
        return IsShowFullAddress;
    }

    public void setShowFullAddress(boolean showFullAddress) {
        IsShowFullAddress = showFullAddress;
    }
}
