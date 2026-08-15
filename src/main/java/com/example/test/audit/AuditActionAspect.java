package com.example.test.audit;

import com.example.test.models.dto.req.UserReq;
import com.example.test.models.entities.User;
import com.example.test.models.services.ILogService;
import com.example.test.security.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditActionAspect {
    private final ILogService logService;
    private final SecurityUtils securityUtils;

    @Around("execution(* com.example.test.controllers..*(..))")
    public Object record(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String httpMethod = resolveHttpMethod(method);

        if (httpMethod == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = currentRequest();
        Object result = joinPoint.proceed();
        User user = currentUserOrNull();
        String action = toAction(method.getName());
        String endpoint = request == null ? "UNKNOWN" : request.getRequestURI();
        logService.recordAudit(action, httpMethod, endpoint, user);
        return result;
    }

    private String resolveHttpMethod(Method method) {
        if (method.isAnnotationPresent(PostMapping.class)) return "POST";
        if (method.isAnnotationPresent(PutMapping.class)) return "PUT";
        if (method.isAnnotationPresent(PatchMapping.class)) return "PATCH";
        if (method.isAnnotationPresent(DeleteMapping.class)) return "DELETE";
        return null;
    }

    private String toAction(String methodName) {
        return methodName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    private User currentUserOrNull() {
        try {
            return securityUtils.getCurrentUser();
        } catch (RuntimeException ignored) {
            return null;
        }
    }
}