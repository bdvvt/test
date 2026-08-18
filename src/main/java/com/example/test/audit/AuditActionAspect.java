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

        String action = toAction(joinPoint.getSignature().getName());
        String endpoint = request == null ? "UNKNOWN" : request.getRequestURI();
        User user = currentUser();

        try {
            Object result = joinPoint.proceed();
            recordSuccessSafely(action, httpMethod, endpoint, user);
            return result;
        } catch (Throwable exception) {
            recordErrorSafely(action, httpMethod, endpoint, exception, user);
            throw exception;
        }
    }

    private void recordSuccessSafely(String action, String method, String endpoint, User user) {
        try {
            logService.recordAudit(action, method, endpoint, user);
        } catch (RuntimeException loggingException) {
            log.error("Không thể lưu audit log thành công cho {} {}", method, endpoint,
                    loggingException);
        }
    }

    private void recordErrorSafely(
            String action,
            String method,
            String endpoint,
            Throwable exception,
            User user
    ) {
        try {
            logService.recordError(action, method, endpoint, exception, user);
        } catch (RuntimeException loggingException) {
            log.error("Không thể lưu audit error log cho {} {}", method, endpoint,
                    loggingException);
        }
    }

    private boolean isAuditableMethod(String method) {
        return "POST".equals(method)
                || "PUT".equals(method)
                || "PATCH".equals(method)
                || "DELETE".equals(method);
    }

    private String toAction(String methodName) {
        return methodName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
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
