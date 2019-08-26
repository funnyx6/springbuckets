package com.funnyx.oauth.aspect;

import com.funnyx.oauth.exception.AuthorizationException;
import com.funnyx.oauth.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class AuthorizationAspect {

  @Pointcut("@annotation(com.funnyx.oauth.annotation.CheckAuthorization)")
  public void authorizationAspect() {}

  @Autowired private JwtUtil jwtUtil;

  @Around(value = "authorizationAspect()")
  public Object checkAuthorization(ProceedingJoinPoint joinPoint) {
    try {
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      HttpServletRequest request = attributes.getRequest();
      String auth = request.getHeader("auth");
      boolean validateToken = jwtUtil.validateToken(auth);
      if (!validateToken) {
        throw new AuthorizationException(100, "Invalid Token");
      }
      return joinPoint.proceed();
    } catch (Throwable throwable) {
      throw new AuthorizationException(100, "Invalid Token");
    }
  }
}
