package com.arka.micro_stock.domain.util.constants;

public class InventoryConstants {
    private InventoryConstants() {
        throw new IllegalStateException("Utility class");
    }
    public static final String STOCK_VALIDATION_ERROR = "The actual stock cannot be less than the minimum stock";
    public static final String SUPPLIER_REQUIRED_ERROR = "At least one supplier is required";
    public static final String PRODUCT_NOT_FOUND_ERROR = "The product does not exist";
    public static final String COUNTRY_NOT_FOUND_ERROR = "The country does not exist";
    public static final String SUPPLIERS_NOT_FOUND_ERROR = "One or more of the suppliers does not exist";
    public static final String PRODUCT_ALREADY_EXISTS_ERROR = "The product already exists in the country";

    public static final String SUPPLIER_NOT_ASSOCIATED = "Supplier is not associated with the inventory";
 }
