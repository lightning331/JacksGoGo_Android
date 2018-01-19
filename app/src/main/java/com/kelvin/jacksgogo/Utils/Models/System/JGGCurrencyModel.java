package com.kelvin.jacksgogo.Utils.Models.System;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGCurrencyModel {

    private String CurrencyCode;
    private String CurrencyName;
    private String Symbol;

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName = currencyName;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }
}
