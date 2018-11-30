package com.envisioniot.enos.enosapi.common.util;

import com.envisioniot.enos.enosapi.common.response.EnOSPage;
import com.envisioniot.enos.enosapi.common.response.EnOSResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @Description: Java反射机制相关的工具类
 */
public class TypeReflectUtils {
    private static final String[] BASIC_TYPES = {
            "byte", "short", "int", "long", "float", "double", "char", "boolean",
            "Byte", "Short", "Integer", "Long", "Float", "Double", "Character", "Boolean", "String"
    };

    /**
     * @param simpleTypeName SimpleType的短类型名（不包含包名）
     * @return boolean
     * @Description 判断某SimpleType是否是基本类型
     **/
    public static boolean isBasicType(String simpleTypeName) {
        for (String basicType : BASIC_TYPES) {
            if (basicType.equals(simpleTypeName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param clazz 与泛型绑定的class对象
     * @param index 指定返回的泛型类型的序号
     * @return java.lang.reflect.Type
     * @Description 获取class对象的特定的泛型类型
     **/
    public static Type getActualGenricType(Class clazz, int index) {

        // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();
        return getGenricParameter(genType, index);
    }

    public static Type getGenricParameter(Type genricType, int index) {


        if (genricType == null) {
            return null;
        }

        if (!(genricType instanceof ParameterizedType)) {
            return null;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genricType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return null;
        }
        return params[index];
    }

    public static Type[] getGenricParameters(Type genricType) {


        if (genricType == null) {
            return null;
        }

        if (!(genricType instanceof ParameterizedType)) {
            return null;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genricType).getActualTypeArguments();
        return params;
    }

    public static Class getGenricClass(ParameterizedType genricType) {
        Type type = genricType.getRawType();
        if(type instanceof Class) {
            return (Class) type;
        }
        return null;
    }

    public static String getSimpleName(Type genricType) {
        if(genricType instanceof Class) {
            return ((Class) genricType).getSimpleName();
        } else if(genricType instanceof ParameterizedType) {
            StringBuilder sb = new StringBuilder();
            Type[] genericParams = getGenricParameters(genricType);
            if(genericParams != null && genericParams.length > 0) {
                StringBuilder paramStringBuilder = new StringBuilder();
                for(int i = 0; i < genericParams.length; i ++) {
                    if(i != 0) {
                        paramStringBuilder.append(", ");
                    }
                    paramStringBuilder.append(getSimpleName(genericParams[i]));
                }
                sb.append(getGenricClass((ParameterizedType) genricType).getSimpleName())
                        .append("<")
                        .append(paramStringBuilder.toString())
                        .append(">");
                return sb.toString();

            } else {
                return genricType.toString();
            }

        } else {
            return genricType.toString();
        }
    }

    public static Set<String> getImportClasses(Type genricType) {
        Set<String> importClasses = new HashSet<>();
        if(genricType instanceof Class) {
            String typeName = ((Class) genricType).getName();
            if(typeName.contains(".")) {
                importClasses.add(typeName);
            }

        } else if(genricType instanceof ParameterizedType) {
            Class rawClass = getGenricClass((ParameterizedType) genricType);
            if(rawClass != null) {
                String typeName = rawClass.getName();
                if(typeName.contains(".")) {
                    importClasses.add(typeName);
                }
            }
            Type[] genericParams = getGenricParameters(genricType);
            if(genericParams != null && genericParams.length > 0) {
                for (int i = 0; i < genericParams.length; i++) {
                    importClasses.addAll(getImportClasses(genericParams[i]));
                }
            }

        }
        return importClasses;
    }

    public static void main(String[] args) {
        System.out.println(getImportClasses(getActualGenricType(new EnOSResponse<ArrayList<EnOSPage<Map<String, EnOSPage<String>>>>>(){}.getClass(), 0)));
    }
}




