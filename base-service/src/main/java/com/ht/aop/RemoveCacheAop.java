package com.ht.aop;

import com.ht.config.CacheService;
import com.ht.config.CacheType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 保存缓存拦截器
 * @author swt
 */
@Aspect
@Component
public class RemoveCacheAop {
    private static Log log = LogFactory.getLog(RemoveCacheAop.class);

    @Autowired
    private CacheService cacheService;

    @Pointcut("@annotation(com.ht.aop.RemoveCache)")
    public void remove(){};

    @Before("remove()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RemoveCache removeCache = method.getAnnotation(RemoveCache.class);

        // 清除其他关联类
        cacheService.cleanCache(removeCache.cleanService());

        // 从缓存清理数据
        String typeName = joinPoint.getSignature().getDeclaringTypeName();

        // 清理所有 包含Search和Object
        if(removeCache.cleanAll()) {
            cacheService.cleanCache(typeName);
            return;
        }

        // 清除Search
        if(removeCache.cleanSearch()) {
            cacheService.cleanCache(typeName, CacheType.SEARCH);
        }

        // 清除Object
        Object[] args = joinPoint.getArgs();
        if(null == args) {
            return;
        }
        int keyPosition = removeCache.cleanObjectByKeyPosition();
        if(keyPosition < 0 || args.length <= keyPosition) {
            return;
        }
        Object arg = args[keyPosition];
        if(arg instanceof String) {
            cacheService.cleanCache(typeName, CacheType.OBJECT, (String) arg);
        }
        if(arg instanceof String[]) {
            cacheService.cleanCache(typeName, CacheType.OBJECT, (String[]) arg);
        }
    }

}
