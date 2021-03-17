package com.tianling.utils;

import com.tianling.common.ExceptionMessage;
import com.tianling.entities.ResponseInfo;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/1/17 20:04
 */
public class  HttpResponseMessageUtils<T> {

    public  static  <T> Mono<ResponseInfo<T>>  querySuccessResponse(Flux data){
        return data.collectList().flatMap(HttpResponseMessageUtils::querySuccessResponse);
    }

    public  static  <T> Mono<ResponseInfo<T>>  querySuccessResponse(T t){
        return ResponseInfo.ok(Mono.just(t), HttpStatus.OK.value(), ExceptionMessage.QUERYSUCCESS);
    }

    public  static  <T> Mono<ResponseInfo<T>>  queryFailedResponse(){
        return ResponseInfo.failed(Mono.empty(), HttpStatus.EXPECTATION_FAILED.value(), ExceptionMessage.QUERYFAILED);
    }

    public  static  <T> ResponseInfo<T>  queryFailedCommonResponse(){
        return new ResponseInfo<T>(ExceptionMessage.QUERYFAILED,HttpStatus.EXPECTATION_FAILED.value(),null);
    }

    public  static  <T> ResponseInfo<T>  queryFailedCommonResponse(String msg){
        return new ResponseInfo<T>(ExceptionMessage.QUERYFAILED,HttpStatus.EXPECTATION_FAILED.value(),msg);
    }
    public  static  <T> Mono<ResponseInfo<T>>  insertSuccessResponse(T t){
        return ResponseInfo.ok(Mono.just(t), HttpStatus.OK.value(), ExceptionMessage.INSERTSUCCESS);
    }

    public  static  <T> Mono<ResponseInfo<T>>  insertFailedResponse(T t){
        return ResponseInfo.failed(Mono.just(t), HttpStatus.EXPECTATION_FAILED.value(), ExceptionMessage.INSERTFAILED);
    }

    public  static  <T> Mono<ResponseInfo<T>>  insertFailedResponse(){
        return ResponseInfo.failed(Mono.empty(), HttpStatus.EXPECTATION_FAILED.value(), ExceptionMessage.INSERTFAILED);
    }

    public  static  <T> ResponseInfo<T>  insertFailedCommonResponse(){
         return new ResponseInfo<T>(ExceptionMessage.INSERTFAILED,HttpStatus.EXPECTATION_FAILED.value(),null);
    }

    public  static  <T> ResponseInfo<T>  updateFailedCommonResponse(){
        return new ResponseInfo<T>(ExceptionMessage.UPDATEFAILED,HttpStatus.EXPECTATION_FAILED.value(),null);
    }

    public  static  <T> ResponseInfo<T>  updateFailedCommonResponse(String msg){
        return new ResponseInfo<T>(ExceptionMessage.UPDATEFAILED,HttpStatus.EXPECTATION_FAILED.value(),msg);
    }

    public  static  <T> Mono<ResponseInfo<T>>  updateSuccessResponse(T t){
        return ResponseInfo.ok(Mono.just(t), HttpStatus.OK.value(), ExceptionMessage.UPDATESUCCESS);
    }

    public  static  <T> Mono<ResponseInfo<T>>  updateFailedResponse(T t){
        return ResponseInfo.failed(Mono.just(t), HttpStatus.EXPECTATION_FAILED.value(), ExceptionMessage.UPDATEFAILED);
    }


    public  static  <T> ResponseInfo<T>  deleteFailedCommonResponse(String msg){
        return new ResponseInfo<T>(ExceptionMessage.DELETEFAILED,HttpStatus.EXPECTATION_FAILED.value(),msg);
    }

    public  static  <T> ResponseInfo<T>  deleteFailedCommonResponse(){
        return new ResponseInfo<T>(ExceptionMessage.DELETEFAILED,HttpStatus.EXPECTATION_FAILED.value(),null);
    }

    public  static  <T> Mono<ResponseInfo<T>>  deleteSuccessResponse(T t){
        return ResponseInfo.ok(Mono.just(t), HttpStatus.OK.value(), ExceptionMessage.DELETESUCCESS);
    }


    public  static  <T> ResponseInfo<T>  deleteFailedCommonResponse(T t){
        return new ResponseInfo<T>(ExceptionMessage.DELETEFAILED,HttpStatus.EXPECTATION_FAILED.value(),t);
    }


    public static <T> Mono<ResponseInfo<T>> exceptionHandler(T result,String msg){
        return ResponseInfo.ok(Mono.just(result),HttpStatus.BAD_REQUEST.value(),msg);
    }
}
