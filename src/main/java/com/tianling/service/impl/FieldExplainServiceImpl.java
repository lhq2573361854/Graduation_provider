package com.tianling.service.impl;

import com.tianling.entities.FieldExplain;
import com.tianling.entities.ResponseInfo;
import com.tianling.repository.ReactiveFieldExplainSortingRepository;
import com.tianling.service.IFieldExplainService;
import com.tianling.utils.HttpResponseMessageUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-03-04
 */
@Service
public class FieldExplainServiceImpl  implements IFieldExplainService {
    @Resource
    ReactiveFieldExplainSortingRepository reactiveFieldExplainSortingRepository;

    @Override
    public Mono<ResponseInfo<FieldExplain>> getAllFieldExplain() {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveFieldExplainSortingRepository.findAll());
    }
}
