package com.arka.micro_stock.adapters.util;

public class CountryConstantsDriving {
    private CountryConstantsDriving() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_NOT_BLANK = "Country name must not be blank";
    public static final String NAME_MAX_SIZE_MESSAGE = "Country name must be 1-60 characters";

    public static final String DESCRIPTION_NOT_BLANK = "Country description must not be blank";
    public static final int NAME_MAX_SIZE = 60;
    public static final String NO_JWT_TOKEN_AVAILABLE = "No JWT token available";
    public static final String NO_AUTHENTICATION_FOUND = "No authentication found";
    public static final String COUNTRY_REQUIRED = "Country is required";
    public static final String STATE_REQUIRED = "State is required";
    public static final String CITY_REQUIRED = "City is required";
    public static final String STREET_REQUIRED = "Street is required";
    public static final String NOMENCLATURE_REQUIRED = "Nomenclature is required";
    public static final String OBSERVATION_TOO_LONG = "Observation must be less than 255 characters";
    public static final String PRODUCT_ID_REQUIRED = "Product ID is required";
    public static final String COUNTRY_ID_REQUIRED = "Country ID is required";
    public static final String STOCK_ACTUAL_REQUIRED = "Actual stock is required";
    public static final String STOCK_MIN_REQUIRED = "Minimum stock is required";
    public static final String SUPPLIERS_REQUIRED = "At least one supplier must be provided";
    public static final String SUPPLIER_ID_REQUIRED = "Supplier ID is required";
    public static final String PRICE_REQUIRED = "Price is required";
    public static final String PRICE_MIN = "Price must be greater than or equal to 0";
    public static final String DELIVERY_TIME_REQUIRED = "Delivery time is required";
    public static final String DELIVERY_TIME_MIN = "Delivery time must be zero or positive";
    public static final String NIT_REQUIRED = "NIT is required";
    public static final String NIT_NUMERIC = "NIT must be numeric";

    public static final String NAME_REQUIRED = "Name is required";
    public static final String NAME_MAX_LENGTH = "Name must be at most 60 characters";

    public static final String DESCRIPTION_REQUIRED = "Description is required";
    public static final String DESCRIPTION_MAX_LENGTH = "Description must be at most 200 characters";

    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String EMAIL_INVALID = "Email must be valid";

    public static final String PHONE_REQUIRED = "Phone number is required";
    public static final String PHONE_INVALID = "Phone must start with + and contain up to 16 digits including the prefix";

    public static final String WEBSITE_REQUIRED = "Website is required";
    public static final String CONTACT_NAME_REQUIRED = "Contact name is required";

    public static final int DESCRIPTION_MAX_SIZE = 200;

    public static final String REGEX_NUMERIC = "\\d+";
    public static final String REGEX_PHONE = "^\\+\\d{1,15}$";



}