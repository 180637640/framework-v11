package com.ht.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.config.CacheService;
import com.ht.config.CacheType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 保存缓存拦截器
 * @author swt
 */
@Aspect
@Component
public class SaveCacheAop {
    private static Log log = LogFactory.getLog(SaveCacheAop.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    public ObjectMapper objectMapper;

    @Pointcut("@annotation(com.ht.aop.SaveCache)")
    public void save(){};

    @Around("save()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if(null == method) {
            return joinPoint.proceed();
        }
        SaveCache saveCache = method.getAnnotation(SaveCache.class);
        if(null == saveCache) {
            return joinPoint.proceed();
        }

        // 检查缓存
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object obj;
        if(saveCache.cacheType() == CacheType.OBJECT && joinPoint.getArgs().length > 0) {
            obj = cacheService.getCache(typeName, saveCache.cacheType(), (String) joinPoint.getArgs()[0]);
        } else {
            obj = cacheService.getCache(typeName, saveCache.cacheType(), createKey(methodName, joinPoint.getArgs()));
        }
        if(null == obj) {
            return joinPoint.proceed();
        }

        return obj;
    }

    @AfterReturning(pointcut="save()", returning="returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        if(null == returnValue) {
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SaveCache saveCache = method.getAnnotation(SaveCache.class);

        // 保存数据到缓存
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        if(saveCache.cacheType() == CacheType.OBJECT && joinPoint.getArgs().length > 0) {
            cacheService.setCache(typeName, saveCache.cacheType(), (String) joinPoint.getArgs()[0], returnValue, saveCache.timeout(), TimeUnit.SECONDS);
        } else {
            cacheService.setCache(typeName, saveCache.cacheType(), createKey(methodName, joinPoint.getArgs()), returnValue, saveCache.timeout(), TimeUnit.SECONDS);
        }
    }

    /**
     * 生成Key
     * @param methodName    方法名
     * @param args          参数
     * @return              生成Key
     */
    protected String createKey(String methodName, Object[] args) {
        StringBuffer keyBuffer = new StringBuffer(methodName);
        for (Object o : args) {
            if(o instanceof String) {
                keyBuffer.append("String");
                keyBuffer.append(o);
            } else if(o instanceof Integer) {
                keyBuffer.append("Integer");
                keyBuffer.append(o);
            } else if(o instanceof Double) {
                keyBuffer.append("Double");
                keyBuffer.append(o);
            } else if(o instanceof BigDecimal) {
                keyBuffer.append("BigDecimal");
                keyBuffer.append(o);
            } else if(o instanceof Long) {
                keyBuffer.append("Long");
                keyBuffer.append(o);
            } else if(o instanceof Float) {
                keyBuffer.append("Float");
                keyBuffer.append(o);
            } else {
                try {
                    keyBuffer.append("Object");
                    keyBuffer.append(objectMapper.writeValueAsString(o));
                } catch (JsonProcessingException e) {
                    log.error(e);
                }
            }
        }

        return keyBuffer.toString();
    }
}
