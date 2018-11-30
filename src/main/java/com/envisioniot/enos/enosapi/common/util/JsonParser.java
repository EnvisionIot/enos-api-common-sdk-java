/**
 * Project: eeop
 * <p>
 * Copyright http://www.envisioncn.com/ All rights reserved.
 *
 * @author xiaomin.zhou
 */
package com.envisioniot.enos.enosapi.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Json Parser
 */
public class JsonParser {
    private static final Gson gson = new Gson();

    //Dummy Constructor
    private JsonParser() {

    }

    //Json To Object By Gson
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    //Json To Object By Gson
    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    //Json To Object By Gson
    public static <T> T fromJson(String json, TypeToken typeToken) {
        return gson.fromJson(json, typeToken.getType());
    }

    //Object To Json By Gson
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static boolean validate(String jsonStr) {
        if(jsonStr == null) {
            return false;
        }
        JsonElement jsonElement;
        try {
            jsonElement = new com.google.gson.JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

}
