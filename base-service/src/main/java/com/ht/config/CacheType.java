package com.ht.config;

/**
 * 缓存类型
 * @author swt
 */
public enum CacheType {
    SEARCH("SEARCH"), OBJECT("OBJECT");

    private final String value;

    CacheType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
