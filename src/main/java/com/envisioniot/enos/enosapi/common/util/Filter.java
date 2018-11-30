package com.envisioniot.enos.enosapi.common.util;

import java.io.Serializable;

/**
 * This class defines filter used in getFilteredObjects API.
 *
 * @author jieyuan.shen
 */
public class Filter implements Serializable {

    private static final long serialVersionUID = 3827140941669021379L;

    public static final String OP_EQ = "=";
    public static final String OP_GT = ">";
    public static final String OP_LT = "<";
    public static final String OP_GE = ">=";
    public static final String OP_LE = "<=";
    public static final String OP_NEQ = "!=";

    private String column;            // mandatory

    private String operator;        // mandatory

    private String value;            // mandatory

    public Filter() {
    }

    public Filter(String column, String operator, String value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
