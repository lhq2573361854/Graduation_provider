package com.tianling.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/1/16 20:35
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(* com.tianling.controller.*.*(..))")
    public void controllerAspect() {
    }

    @Around(value = "controllerAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if(i != 0){
                sb.append(",");
            }
        }

        log.info("传入的参数是 {} ", !StringUtils.isEmpty(sb.toString()) ? sb.toString():"参数为空");

        Object proceed = point.proceed();

        return proceed;
    }



}
