package com.tianling.aspect;

import com.tianling.common.ExceptionMessage;
import com.tianling.entities.ResponseInfo;
import com.tianling.utils.HttpResponseMessageUtils;
import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * 异常处理类
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/1/17 1:38
 */
@ControllerAdvice
@Slf4j
public class CheckAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Mono<ResponseInfo<String>> illegalArgumentExceptionHandler(IllegalArgumentException throwable){
        return HttpResponseMessageUtils.exceptionHandler(throwable.getMessage(),"参数异常");
    }

    @ExceptionHandler(SpelEvaluationException.class)
    @ResponseBody
    public Mono<ResponseInfo<String>> spelEvaluationExceptionHandler(SpelEvaluationException spelEvaluationException){
        return HttpResponseMessageUtils.exceptionHandler(spelEvaluationException.getMessage(), ExceptionMessage.CACHE);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseBody
    public Mono<ResponseInfo<String>> webExchangeBindExceptionHandler(WebExchangeBindException throwable){
        String message = throwable.getBindingResult().getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(","));
        return HttpResponseMessageUtils.exceptionHandler(message,ExceptionMessage.DATABINDINGEXCEPTION);
    }


    @ExceptionHandler(R2dbcDataIntegrityViolationException.class)
    @ResponseBody
    public Mono<ResponseInfo<String>> dataIntegrityViolationExceptionHandler(R2dbcDataIntegrityViolationException throwable){
        return HttpResponseMessageUtils.exceptionHandler(throwable.getMessage(),ExceptionMessage.EXCEPTION_MESSAGE);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Mono<ResponseInfo<String>> commonExceptionHandler(RuntimeException throwable){
        return HttpResponseMessageUtils.exceptionHandler(throwable.getMessage(),ExceptionMessage.CREATFAILED);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Mono<ResponseInfo<String>> exceptionHandler(Exception throwable){
        return HttpResponseMessageUtils.exceptionHandler(throwable.getMessage(), ExceptionMessage.other);
    }
}
