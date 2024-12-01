package com.informula.movieservice.config;

import com.informula.movieservice.constants.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MetricAspect {

    @Before("@annotation(metricProvider)")
    @Order(1)
    public void metricOpening(JoinPoint joinPoint, MetricProvider metricProvider) {
        log.info(LogConstants.LOG_METHOD_CALLED, metricProvider.metricName());
    }

    @AfterReturning(value = "@annotation(metricProvider)", returning = "response")
    @Order(2)
    public void metricClosing(JoinPoint joinPoint, MetricProvider metricProvider, Object response) {
        log.info(LogConstants.LOG_METHOD_DONE, metricProvider.metricName());
    }
}
