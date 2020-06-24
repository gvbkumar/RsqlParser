package com.gvbk.Constants;

public final class RsqlOperatorConstants {

    public static final String NOT_EQUAL = "!=";
    public static final String GREATER_THAN = ">";
    public static final String GREATER_THAN_STR = "=gt=";
    public static final String GREATER_THAN_OR_EQUAL = ">=";
    public static final String GREATER_THAN_OR_EQUAL_STR = "=ge=";
    public static final String LESS_THAN = "<";
    public static final String LESS_THAN_STR = "=lt=";
    public static final String LESS_THAN_OR_EQUAL = "<=";
    public static final String LESS_THAN_OR_EQUAL_STR = "=le=";
    public static final String IN = "=in=";
    public static final String NOT_IN = "=out=";

    //custom operator
    public static final String LIKE = "=like=";

    private RsqlOperatorConstants() {
        throw new IllegalStateException();
    }
}
