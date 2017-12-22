package com.ht.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 常用函数
 * @author swt
 */
public class UF extends UserFunction{


    /**
     * double转成字符串
     * @param value     double
     * @param pattern   格式 #.## （1.234 -> 1.23 | 1.1 -> 1.1） #。00 （1.234 -> 1.234 | 1.1 -> 1.10）
     * @return
     */
    public static final String toString(double value, String pattern) {
        if(StringUtils.isBlank(pattern)) {
            pattern = "#.##";
        }
        DecimalFormat df = new DecimalFormat(pattern);

        return df.format(value);
    }

    /**
     * double转成字符串, 保留小数点后两位，不足两位补0
     * @param value     double
     * @return
     */
    public static final String toStringWith2P(double value) {
        return toString(value, "#.00");
    }



    /**
     * 通过反射获取Bean中所有属性和属性值
     * @param o Bean
     * @return  属性和属性值
     */
    public static final Map<String, String> getFieldMap (Object o) {
        Map<String, String> fieldMap = new HashMap<>();

        try {
            Field[] fields = o.getClass().getDeclaredFields();
            Field.setAccessible(fields, true);
            for (Field field : fields) {
                Object obj = field.get(o);

                if(obj instanceof String || obj instanceof Integer || obj instanceof Double) {
                    fieldMap.put(field.getName(), field.get(o).toString());
                    continue;
                }
            }
            if(null != o.getClass().getGenericSuperclass()) {
                // 如果有父类
                fields = o.getClass().getSuperclass().getDeclaredFields();
                Field.setAccessible(fields, true);
                for (Field field : fields) {
                    Object obj = field.get(o);

                    if(obj instanceof String || obj instanceof Integer || obj instanceof Double) {
                        fieldMap.put(field.getName(), field.get(o).toString());
                        continue;
                    }
                }
            }
        } catch (Exception e) {
        }

        return fieldMap;
    }
}