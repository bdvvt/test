package com.example.test.audit;

import com.example.test.models.entities.User;
import com.example.test.models.services.ILogService;
import com.example.test.security.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditActionAspect {
    private final ILogService logService;
    private final SecurityUtils securityUtils;

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object record(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = currentRequest();
        String httpMethod = request == null ? null : request.getMethod();

        if (!isAuditableMethod(httpMethod)) {
            return joinPoint.proceed();
        }

        Object result = joinPoint.proceed();
        User user = currentUser();
        String action = toAction(joinPoint.getSignature().getName());
        String endpoint = request.getRequestURI();
        logService.recordAudit(action, httpMethod, endpoint, user);
        return result;
    }

    private boolean isAuditableMethod(String httpMethod) {
        return "POST".equals(httpMethod)
                || "PUT".equals(httpMethod)
                || "PATCH".equals(httpMethod)
                || "DELETE".equals(httpMethod);
    }

    private String toAction(String methodName) {
        return methodName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    private User currentUser() {
        try {
            return securityUtils.getCurrentUser();
        } catch (RuntimeException ignored) {
            return null;
        }
    }
}