package com.envisioniot.enos.enosapi.common.util;

import com.envisioniot.enos.enosapi.common.exception.EnOSRuleException;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RuleCheckUtils {
    private RuleCheckUtils() {
    }

    public static final String ERROR_CODE_ARGUMENTS_MISS = "40";// Missing Required Arguments
    public static final String ERROR_CODE_ARGUMENTS_INVALID = "41";// Invalid Arguments

    public static void checkNotEmpty(Object value, String fieldName) throws EnOSRuleException {
        if (value == null) {
            throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_MISS,
                    "client-error:Missing Required Arguments:" + fieldName + "");
        }
        if (value instanceof String) {
            if (((String) value).trim().length() == 0) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_MISS,
                        "client-error:Missing Required Arguments:" + fieldName + "");
            }
        } else if (value instanceof Collection<?>) {
            if (((Collection<?>) value).isEmpty()) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_MISS,
                        "client-error:Missing Required Arguments:" + fieldName + "");
            }
        } else if (value instanceof Map<?, ?>) {
            if (((Map<?, ?>) value).isEmpty()) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_MISS,
                        "client-error:Missing Required Arguments:" + fieldName + "");
            }
        }
    }

    public static void checkNotNull(Object value, String fieldName) throws EnOSRuleException {
        if (value == null) {
            throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_MISS,
                    "client-error:Missing Required Arguments:" + fieldName + "");
        }
    }

    public static void checkMaxLength(String value, int maxLength, String fieldName) throws EnOSRuleException {
        if (value != null) {
            if (value.length() > maxLength) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                        "client-error:Invalid Arguments:the length of " + fieldName +
                                " can not be larger than " + maxLength + ".");
            }
        }
    }

    public static void checkMaxListSize(String value, int maxSize, String fieldName) throws EnOSRuleException {
        if (value != null) {
            String[] list = value.split(",");
            if (list != null && list.length > maxSize) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                        "client-error:Invalid Arguments:the listsize(the string split by \",\") of " +
                                fieldName + " must be less than " + maxSize + ".");
            }
        }
    }

    public static void checkMaxValue(Long value, long maxValue, String fieldName) throws EnOSRuleException {
        if (value != null) {
            if (value > maxValue) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                        "client-error:Invalid Arguments:the value of " + fieldName +
                                " can not be larger than " + maxValue + ".");
            }
        }
    }

    public static void checkMinValue(Long value, long minValue, String fieldName) throws EnOSRuleException {
        if (value != null) {
            if (value < minValue) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                        "client-error:Invalid Arguments:the value of " + fieldName +
                                " can not be less than " + minValue + ".");
            }
        }
    }

    public static void checkDateFormat(String beginTime, String beginTimeFieldName, String endTime, String endTimeFieldName) throws EnOSRuleException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginTimeDate = df.parse(beginTime);
            if (endTime != null) {
                Date endTimeDate = df.parse(endTime);
                if (beginTimeDate.getTime() > endTimeDate.getTime()) {
                    throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                            "client-error:Invalid Arguments:the value of " + beginTimeFieldName +
                                    " can not be later than " + endTimeFieldName);
                }
            }
        } catch (ParseException e) {
            throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                    "client-error:Invalid Arguments:the value of date format should be yyyy-MM-dd HH:mm:ss");
        }
    }

    public static void checkDateFormat(String timestamp, String dateForamt) throws EnOSRuleException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dateForamt != null && !dateForamt.isEmpty()) {
            df = new SimpleDateFormat(dateForamt);
        }
        try {
            df.parse(timestamp);
        } catch (ParseException e) {
            throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                    "client-error:Invalid Arguments:the value of date format should be " + dateForamt);
        }
    }

    public static void checkFilterFormat(String value, String fieldName) throws EnOSRuleException {
        checkNotEmpty(value, fieldName);
        List<Filter> filters = JsonParser.fromJson(value, new TypeToken<List<Filter>>() {
        }.getType());
        for (Filter filter : filters) {
            checkNotEmpty(filter.getColumn(), fieldName + ".column");
            if (filter.getOperator().equals(Filter.OP_GT) ||
                    filter.getOperator().equals(Filter.OP_LT) ||
                    filter.getOperator().equals(Filter.OP_GE) ||
                    filter.getOperator().equals(Filter.OP_LE)) {
                checkNotEmpty(filter.getValue(), fieldName + ".value");
                try {
                    Double.parseDouble(filter.getValue());
                } catch (NumberFormatException e) {
                    throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                            "client-error:Invalid Arguments:the value of filter " +
                                    filter.getColumn() + " must be number, got " + filter.getValue());
                }
            } else if (!filter.getOperator().equals(Filter.OP_EQ) &&
                    !filter.getOperator().equals(Filter.OP_NEQ)) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                        "client-error:Invalid Arguments:the operator of filter " +
                                filter.getColumn() + " is not supported, got " + filter.getOperator());
            }
        }
    }

    public static void checkRange(int value, int minInclusive, int maxInclusive, String fieldName) throws EnOSRuleException {
        if (value < minInclusive || value > maxInclusive) {
            throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                    "client-error:Invalid Arguments:the value of " + fieldName +
                            " must be in range [" + minInclusive + ", " + maxInclusive + "].");
        }
    }

    public static void checkArgument(boolean argument, String message) throws EnOSRuleException {
        if (!argument) {
            throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID,
                    "client-error:Invalid Arguments:" + message);
        }
    }

    public static void checkList(List<String> stringList, String listName) throws EnOSRuleException {
        if (stringList == null || stringList.isEmpty()) {
            throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID, "client-error:Invalid Arguments: " +
                    listName + " is null or empty.");
        }
        for (String str : stringList) {
            if (str == null || str.trim().isEmpty()) {
                throw new EnOSRuleException(ERROR_CODE_ARGUMENTS_INVALID, "client-error:Invalid Arguments: " +
                        listName + " contains null or empty elements.");
            }
        }
    }
}
