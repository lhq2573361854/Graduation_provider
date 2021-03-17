package com.tianling.service.impl;

import cn.hutool.core.lang.Assert;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheEvict;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCachePut;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheable;
import com.tianling.common.ExceptionMessage;
import com.tianling.entities.ResponseInfo;
import com.tianling.entities.User;
import com.tianling.repository.ReactiveUserSortingRepository;
import com.tianling.service.IUserService;
import com.tianling.utils.HttpResponseMessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 用户的业务实现
 *
 * @author TianLing
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    ReactiveUserSortingRepository reactiveUserSortingRepository;

    private final static String BASENAME = "user_cache_user_";

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "all")
    @Override
    public Mono<ResponseInfo<User>> getAllUser() {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveUserSortingRepository.findAll());
    }

    @ReactiveRedisCacheable(cacheName = BASENAME,key = "'id_' + #id.toString()")
    @Override
    public Mono<ResponseInfo<User>> getUserById(Integer id) {

        return reactiveUserSortingRepository.findById(id)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'username_' + #userName")
    @Override
    public Mono<ResponseInfo<User>> getUserByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);

        return reactiveUserSortingRepository.getUserByUserName(userName)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<User>> getUserByUserPhone(String phone) {
        Assert.notBlank(phone,ExceptionMessage.USERPHONENOTEXSISTPARAMETERIZATION);
        return reactiveUserSortingRepository.getUserByUserPhone(phone)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.USERPHONEPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<User>> createUser(User user) {
        return reactiveUserSortingRepository.save(user)
                .flatMap(user1 -> HttpResponseMessageUtils.insertSuccessResponse(user))
                .defaultIfEmpty(HttpResponseMessageUtils.insertFailedCommonResponse());
    }

    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'username_' + #username")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteUserByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);

        return reactiveUserSortingRepository.deleteUserByUserName(userName)
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }

    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'id_' +#id.toString()")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteUserById(Integer id) {

        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveUserSortingRepository.findById(id)
                .flatMap(user1 -> reactiveUserSortingRepository.deleteById(user1.getId()))
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }

    @Override
    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'id_' +#user.getId().toString()")
    public Mono<ResponseInfo<User>> updateUserById(User user) {
        return updateUserDataById(user);
    }

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'username_' +#user.getUserName()")
    @Override
    public Mono<ResponseInfo<User>> updateUserByUserName(User user) {
        return updateUserDataByUserName(user);
    }



    private Mono<ResponseInfo<User>> updateUserDataById(User user){
        return reactiveUserSortingRepository.findById(user.getId()).flatMap(oldUser->{
            setUserData(user, oldUser);
            return reactiveUserSortingRepository.save(oldUser);
        }).flatMap(HttpResponseMessageUtils::updateSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse());
    }

    private Mono<ResponseInfo<User>> updateUserDataByUserName(User user){
        return reactiveUserSortingRepository.getUserByUserName(user.getUserName()).flatMap(oldUser->{
            setUserData(user, oldUser);
            return reactiveUserSortingRepository.save(oldUser);
        }).flatMap(HttpResponseMessageUtils::updateSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }

    private void setUserData(User user, User oldUser) {
        if (!StringUtils.isEmpty(user.getUserPass())) {
            oldUser.setUserPass(user.getUserPass());
        }

        if (!StringUtils.isEmpty(user.getUserEmail())) {
            oldUser.setUserEmail(user.getUserEmail());
        }

        if (!StringUtils.isEmpty(user.getUserEmail())) {
            oldUser.setUserEmail(user.getUserEmail());
        }
    }


}
