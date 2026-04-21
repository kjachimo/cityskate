package com.cityskate.aspect;

import com.cityskate.service.StatsService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatsAspect {

    private final StatsService statsService;

    public StatsAspect(StatsService statsService) {
        this.statsService = statsService;
    }

    @Before("execution(* com.cityskate.controller.*.*(..))")
    public void countCalls(JoinPoint joinPoint) {

    String className = joinPoint.getSignature().getDeclaringTypeName();

    if (className.contains("StatsController")) {
        return;
    }

    String methodName = joinPoint.getSignature().toShortString();

    statsService.increment(methodName);
}
}