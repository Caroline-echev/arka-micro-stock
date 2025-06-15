package com.arka.micro_stock.configuration.exception.handler.util;

public class ExceptionHandlerConstants {
    private ExceptionHandlerConstants() {
        throw new IllegalStateException("Utility class");
    }
    public static final String ERRORS = "errors";
    public static final String VALIDATION_ERROR_CODE = "ERR_";
    public static final String VALIDATION_ERROR_MESSAGE = "Errors in request body";

    public static final String INTERNAL_ERROR_PREFIX = "An unexpected error occurred: ";
    public static final String ERROR_PROCESSING_RESPONSE = "Error processing response";
    public static final String ERROR_WRITING_JSON = "Error writing error response as JSON";

}
