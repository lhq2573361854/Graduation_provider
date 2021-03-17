package com.tianling.service.impl;

import cn.hutool.core.lang.Assert;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheEvict;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCachePut;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheable;
import com.tianling.common.ExceptionMessage;
import com.tianling.entities.Authority;
import com.tianling.entities.ResponseInfo;
import com.tianling.repository.ReactiveAuthoritySortingRepository;
import com.tianling.service.IAuthorityService;
import com.tianling.utils.HttpResponseMessageUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 * 用户所有的权限表 服务实现类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService {
    private static final String BASENAME = "authority_cache_authority_";

    @Resource
    ReactiveAuthoritySortingRepository reactiveAuthoritySortingRepository;

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "all")
    @Override
    public Mono<ResponseInfo<Authority>> getAllAuthority() {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveAuthoritySortingRepository.findAll());
    }

    @ReactiveRedisCacheable(cacheName = BASENAME,key = "'id_' + #id.toString()")
    @Override
    public Mono<ResponseInfo<Authority>> getAuthorityById(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveAuthoritySortingRepository.findById(id)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.USERIDNOTEXISTPARAMETERIZATION));
    }

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'username_' + #userName")
    @Override
    public Mono<ResponseInfo<Authority>> getAuthorityByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);

        return reactiveAuthoritySortingRepository.getUserAuthority(userName).flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse());
    }


    @ReactiveRedisCacheable(cacheName = BASENAME,key = "'userid_' + #userId.toString()")
    @Override
    public Mono<ResponseInfo<Authority>> getAuthorityByUserId(Integer userId) {
        Assert.notNull(userId,ExceptionMessage.USERIDNOTNULLPARAMETERIZATION);

        return reactiveAuthoritySortingRepository.getAuthorityByUserId(userId)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .switchIfEmpty(HttpResponseMessageUtils.queryFailedResponse());
    }

    @Override
    public Mono<ResponseInfo<Authority>> addAuthority(Authority authority) {
        return reactiveAuthoritySortingRepository.save(authority)
                .flatMap(authority1 -> HttpResponseMessageUtils.insertSuccessResponse(authority))
                .switchIfEmpty(HttpResponseMessageUtils.insertFailedResponse());
    }

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'id_' + #authority.getId().toString()")
    @Override
    public Mono<ResponseInfo<Authority>> updateAuthority(Authority authority) {
        return updateAuthorityData(authority);
    }

    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'id_' + #id.toString()")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteAuthorityById(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveAuthoritySortingRepository.findById(id)
                .flatMap(authority1 -> reactiveAuthoritySortingRepository.deleteById(id))
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.IDNULLPARAMETERIZATION));
    }

    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'username_' + #userName")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteAuthorityByUserName(String userName) {

        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);


        return reactiveAuthoritySortingRepository.deleteByUserName(userName)
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }

    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'userid_' + #userId.toString()")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteAuthorityByUserId(Integer userId) {
        Assert.notNull(userId,ExceptionMessage.USERIDNOTNULLPARAMETERIZATION);
        return reactiveAuthoritySortingRepository.deleteAuthorityById(userId)
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.USERIDNOTEXISTPARAMETERIZATION));
    }

    private Mono<ResponseInfo<Authority>> updateAuthorityData(Authority authority){
        return reactiveAuthoritySortingRepository.findById(authority.getId()).flatMap(oldAuthority->{
            oldAuthority.setUserAuthority(authority.getUserAuthority());
            return reactiveAuthoritySortingRepository.save(oldAuthority);
        }).flatMap(HttpResponseMessageUtils::updateSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse());
    }






}
