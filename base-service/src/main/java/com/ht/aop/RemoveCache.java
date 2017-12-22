package com.ht.aop;


import java.lang.annotation.*;

/**
 * 移除缓存
 * @author swt
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoveCache {

    int cleanObjectByKeyPosition() default -1;

    boolean cleanAll() default false;

    boolean cleanSearch() default false;

    Class<?>[] cleanService() default {};
}
