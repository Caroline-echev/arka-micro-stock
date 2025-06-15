package com.arka.micro_stock.domain.util.constants;

public class CountryConstants {

    private CountryConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String COUNTRY_NAME_LENGTH_ERROR = "Country name must be 1-60 characters";
    public static final String COUNTRY_ALREADY_EXISTS = "Country name already exists";
    public static final String SUPERVISOR_NOT_FOUND_OR_INVALID = "Supervisor not found or invalid role";
    public static final String COUNTRY_NOT_FOUND = "Country not found";
    public static final String COUNTRY_STATUS_ACTIVE = "ACTIVE";
     public static final String SUPERVISOR_ALREADY_ASSIGNED = "Supervisor is already assigned to a country";
    public static final String ROLE_LOGISTIC = "LOGISTIC";
}
