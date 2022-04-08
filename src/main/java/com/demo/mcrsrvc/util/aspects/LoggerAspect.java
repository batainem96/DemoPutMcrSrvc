package com.demo.mcrsrvc.util.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Pointcut("within(com.demo.mcrsrvc.controllers..*)")
        public void logAll() {}

        @AfterReturning(pointcut = "logAll()", returning = "returnedObj")
        public void logMethodReturn(JoinPoint jp, Object returnedObj) {
            String methodSig = extractMethodSignature(jp);
            String argStr = Arrays.toString(jp.getArgs());
            logger.info("{} returned {} with input arg(s): {}", methodSig, returnedObj, argStr);
        }

        @AfterThrowing(pointcut = "logAll()", throwing = "e")
        public void logMethodException(JoinPoint jp, Throwable e) {
            String methodSig = extractMethodSignature(jp);
            logger.warn("{} threw {} with message: {}", methodSig, e.getClass().getSimpleName(), e.getMessage());
        }

        private String extractMethodSignature(JoinPoint jp) {
            return jp.getTarget().getClass().getSimpleName() + "." + jp.getSignature().getName();
        }

}
