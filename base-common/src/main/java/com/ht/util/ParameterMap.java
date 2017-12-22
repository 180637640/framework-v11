package com.ht.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Parameter HashMap
 * @author swt
 */
public class ParameterMap {
    Map<String, Object> paramsMap = new HashMap();

    /**
     * 构造
     */
    public ParameterMap() {

    }

    /**
     * 添加键值对
     * @param key   键
     * @param value 值
     * @return      this
     */
    public ParameterMap put(String key, Object value) {
        if(null == value) {
            return this;
        }
        paramsMap.put(key, value);
        return this;
    }

    /**
     * 移除键值对
     * @param key   键
     * @return      this
     */
    public ParameterMap remove(String key) {
        if(paramsMap.containsKey(key)) {
            paramsMap.remove(key);
        }
        return this;
    }

    /**
     * 获取参数Map
     * @return  参数Map
     */
    public Map<String, Object> toMap() {
        return paramsMap;
    }

    /**
     * 获取值的字符串(先对键进行排序，然后把值连起来后进行MD5加密) 相对 formatValueHashCode 效率低
     * @return  值的字符串
     */
    @Deprecated
    public String formatValueHex() {
        if(null == paramsMap || paramsMap.isEmpty()) {
            return "";
        }

        List<String> list = new ArrayList<>(paramsMap.keySet());
        list.sort(Comparator.comparing(String::toString));

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : list) {
            stringBuilder.append(paramsMap.get(key));
        }

        return DigestUtils.md5Hex(stringBuilder.toString());
    }

    /**
     * 获取值的字符串(先对键进行排序，然后把值连起来后取HashCode)
     * @return  值的字符串
     */
    public String formatValueHashCode() {
        if(null == paramsMap || paramsMap.isEmpty()) {
            return "";
        }

        List<String> list = new ArrayList<>(paramsMap.keySet());
        list.sort(Comparator.comparing(String::toString));

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : list) {
            stringBuilder.append(paramsMap.get(key));
        }

        return Integer.toString(stringBuilder.toString().hashCode());
    }

    /**
     * 获取值的字符串(先对键进行排序，然后把值连起来后取HashCode)
     * @param method    方法名
     * @return  值的字符串（method-hashCode）
     */
    public String formatValueHashCode(String method) {
        return String.format("%s-%s", method, formatValueHashCode());
    }
}
