package com.tianling.service;


import com.tianling.entities.FieldExplain;
import com.tianling.entities.ResponseInfo;
import reactor.core.publisher.Mono;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-03-04
 */
public interface IFieldExplainService{
    /**
     * 获取所有的字段说明
     * @return
     */
    Mono<ResponseInfo<FieldExplain>> getAllFieldExplain();
}
