package com.funnyx.oauth.aspect;

import com.funnyx.oauth.annotation.CheckRole;
import com.funnyx.oauth.exception.AuthorizationException;
import com.funnyx.oauth.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
      HttpServletRequest request = getHttpServletRequest();
      String auth = request.getHeader("auth");
      boolean validateToken = jwtUtil.validateToken(auth);
      if (!validateToken) {
        throw new AuthorizationException(100, "Invalid Token");
      }
      return joinPoint.proceed();
    } catch (Throwable throwable) {
      throw new AuthorizationException(500, "token解析错误");
    }
  }

  private HttpServletRequest getHttpServletRequest() {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    return request;
  }

  @Around("@annotation(com.funnyx.oauth.annotation.CheckAuthorization)")
  public Object checkRole(ProceedingJoinPoint joinPoint) {
    try {
      HttpServletRequest request = getHttpServletRequest();
      String role = (String) request.getAttribute("role");
      // 获取checkrole注解的值
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      CheckRole annotation = signature.getMethod().getAnnotation(CheckRole.class);
      String[] roles = annotation.roles();
      if (!Arrays.asList(roles).contains(role)) {
        throw new AuthorizationException(200, "该用户无权限访问");
      }
      return joinPoint.proceed();
    } catch (Throwable throwable) {
      throw new AuthorizationException(500, "系统异常");
    }
  }
}
