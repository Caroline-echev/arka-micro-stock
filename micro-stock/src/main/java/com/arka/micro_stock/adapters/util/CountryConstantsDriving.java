package com.arka.micro_stock.adapters.util;

public class CountryConstantsDriving {
    private CountryConstantsDriving() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_NOT_BLANK = "Country name must not be blank";
    public static final String NAME_MAX_SIZE_MESSAGE = "Country name must be 1-60 characters";

    public static final String DESCRIPTION_NOT_BLANK = "Country description must not be blank";
    public static final int NAME_MAX_SIZE = 60;

}