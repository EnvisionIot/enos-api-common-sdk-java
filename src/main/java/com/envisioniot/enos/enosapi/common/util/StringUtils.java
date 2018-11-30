package com.envisioniot.enos.enosapi.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * String Utility
 */
public abstract class StringUtils {

    private StringUtils() {
    }

    /**
     * check empty
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the string is numeric
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        String str = obj.toString();
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * check string list is empty
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * convert code format to chinese code format
     */
    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }

    /**
     * file characters
     */
    public static String stripNonValidXMLCharacters(String input) {
        if (input == null || ("".equals(input))) return "";
        StringBuilder out = new StringBuilder();
        char current;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF)) || ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }

    /**
     * Convert List To String separate by the Separator
     */
    public static String listToString(List<String> stringList, final String separator) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(separator);
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * Convert List To String separate by the Separator
     */
    public static String listToString(List<String> stringList, final char separator) {
        return listToString(stringList, String.valueOf(separator));
    }

    /**
     * Split String to array
     *
     * @param line
     * @param seperator
     * @return
     */
    public static String[] split(String line, String seperator) {
        if (line == null || seperator == null || seperator.length() == 0) return new String[]{};

        ArrayList<String> list = new ArrayList<String>();
        int pos1 = 0;
        int pos2;
        for (; ; ) {
            pos2 = line.indexOf(seperator, pos1);
            if (pos2 < 0) {
                list.add(line.substring(pos1));
                break;
            }
            list.add(line.substring(pos1, pos2));
            pos1 = pos2 + seperator.length();
        }

        for (int i = list.size() - 1; i >= 0 && list.get(i).length() == 0; --i) {
            list.remove(i);
        }
        return list.toArray(new String[0]);
    }

    /**
     * 保留字符串的前maxLength个字符
     * suffix通常是"..."
     */
    public static String cutString(String str, int maxLength, String suffix) {
        if (str == null || maxLength >= str.length()) return str;
        if (suffix == null) suffix = "";
        if (maxLength < 0) maxLength = str.length();
        return str.substring(0, maxLength) + suffix;
    }
}
