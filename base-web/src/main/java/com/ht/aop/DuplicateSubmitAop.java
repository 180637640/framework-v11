package com.ht.aop;

import com.ht.util.UF;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * 保存缓存拦截器
 * @author swt
 */
@Aspect
@Component
public class DuplicateSubmitAop {
    private static Log log = LogFactory.getLog(DuplicateSubmitAop.class);

    @Pointcut("@annotation(com.ht.aop.Token)")
    public void token(){};

    @Before("token()")
    public void before(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Token token = method.getAnnotation(Token.class);

        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        if(token.save()) {
            request.getSession(true).setAttribute("token", UF.getRandomUUID());
        }
        if(token.remove()) {
            if (isRepeatSubmit(request)) {
                response.sendRedirect(request.getContextPath()+"/error/500?message="+ URLEncoder.encode(URLEncoder.encode("请不要重复提交", "UTF-8"), "UTF-8"));
                return;
            }
            request.getSession(true).removeAttribute("token");
        }
    }

    /**
     * 是否重复提交
     * @param request
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(true).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clientToken = request.getParameter("token");
        if (clientToken == null) {
            return true;
        }
        if (!serverToken.equals(clientToken)) {
            return true;
        }
        return false;
    }
}
