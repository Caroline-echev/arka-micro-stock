package com.arka.micro_stock.domain.util.constants;

public class SupplierConstants {
    private SupplierConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SUPPLIER_NOT_FOUND = "Supplier not found";
    public static final String SUPPLIER_NAME_ALREADY_EXISTS = "Supplier name already exists";
    public static final String SUPPLIER_EMAIL_ALREADY_EXISTS = "Supplier email already exists";
}
