package com.arka.micro_stock.configuration.util;

public class ConstantsConfiguration {
    private ConstantsConfiguration() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MIGRATE = "migrate";
    public static final String BEARER = "Bearer ";
    public static final String ROLE_PREFIX = "ROLE_";

    // Swagger constants
    public static final String SWAGGER_TITLE = "Arka stock Management API";
    public static final String SWAGGER_VERSION = "v1.0";
    public static final String SWAGGER_DESCRIPTION = "API para gestión de stock";
    public static final String SWAGGER_SECURITY_NAME = "bearerAuth";
    public static final String SWAGGER_SECURITY_SCHEME = "bearer";
    public static final String SWAGGER_BEARER_FORMAT = "JWT";
}
