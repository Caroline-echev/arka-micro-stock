package com.arka.micro_stock.adapters.util;

public class BrandConstantsDriving {
    private BrandConstantsDriving() {
        throw new IllegalStateException("Utility class");
    }
    public static final int MAX_PAGE_SIZE = 100;
    public static final String NAME_NOT_BLANK = "Name cannot be blank";
    public static final String NAME_MAX_SIZE =  "Name cannot be longer than 100 characters";
    public static final String DESCRIPTION_NOT_BLANK = "Description cannot be blank";
}
