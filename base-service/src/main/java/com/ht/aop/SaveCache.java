package com.ht.aop;


import com.ht.config.CacheType;

import java.lang.annotation.*;

/**
 * 保存缓存
 * @author swt
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaveCache {

    CacheType cacheType() default CacheType.SEARCH;

    int timeout() default 60;

}
