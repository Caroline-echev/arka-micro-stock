package com.arka.micro_stock.configuration.util;

public class ConstantsConfiguration {
    private ConstantsConfiguration() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MIGRATE = "migrate";


    public static final String API_TITLE = "Micro Catalog API";
    public static final String API_DESCRIPTION = "API for stock management";
    public static final String API_VERSION = "v1.0";
    public static final String API_GROUP = "catalog";
    public static final String API_PATHS = "/api/**";


    public static final String BEARER = "Bearer ";
    public static final int TOKEN_PREFIX_LENGTH = 7;
    public static final String ROLE_PREFIX = "ROLE_";
}
