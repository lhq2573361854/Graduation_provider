package com.tianling.service.impl;

import cn.hutool.core.lang.Assert;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheEvict;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCachePut;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheable;
import com.tianling.common.ExceptionMessage;
import com.tianling.entities.ResponseInfo;
import com.tianling.entities.Role;
import com.tianling.repository.ReactiveRoleSortingRepository;
import com.tianling.service.IRoleService;
import com.tianling.utils.HttpResponseMessageUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 * 用户的角色表 服务实现类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
@Service
public class RoleServiceImpl  implements IRoleService {

    private final static String BASENAME = "role_cache_role_";

    @Resource
    private ReactiveRoleSortingRepository reactiveRoleSortingRepository;

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "all")
    @Override
    public Mono<ResponseInfo<Role>> getAllRole() {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveRoleSortingRepository.findAll());
    }



    @ReactiveRedisCacheable(cacheName = BASENAME,key = " 'id_'+ #id.toString()")
    @Override
    public Mono<ResponseInfo<Role>> getRoleById(Integer id){
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveRoleSortingRepository.findById(id).flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'userid_' + #userId.toString()")
    @Override
    public Mono<ResponseInfo<Role>> getRoleByUserId(Integer userId) {
        Assert.notNull(userId,ExceptionMessage.USERIDNOTNULLPARAMETERIZATION);
        return reactiveRoleSortingRepository.getRoleByUserId(userId).flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.USERIDNOTEXISTPARAMETERIZATION));
    }

    @ReactiveRedisCacheable(cacheName = BASENAME,key = "'username_' + #userName")
    @Override
    public Mono<ResponseInfo<Role>> getRoleByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);

        return reactiveRoleSortingRepository.getRoleByUserName(userName).flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<Role>> addRole(Role role) {
        return reactiveRoleSortingRepository.save(role).flatMap(HttpResponseMessageUtils::insertSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse());
    }

    @ReactiveRedisCachePut(cacheName = BASENAME,key = " 'id_' + #role.getId().toString()")
    @Override
    public Mono<ResponseInfo<Role>> updateRole(Role role) {
//        findById(role.getId()).flatMap(role1-> {
//            Set<String> set1 = Arrays.stream(role1.getUserRole().split(DateBaseOperator.DATABASESPLIT)).collect(Collectors.toSet());
//            String roleString = Arrays.stream(role.getUserRole().split(DateBaseOperator.DATABASESPLIT)).filter(i -> !set1.contains(i)).collect(Collectors.joining(","));
//            role1.setUserRole(role1.getUserRole()+roleString);
//            return  reactiveRoleSortingRepository.save(role1);
//        })
        return reactiveRoleSortingRepository.save(role).flatMap(HttpResponseMessageUtils::updateSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }


    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'id_' +#id.toString()")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteRoleById(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveRoleSortingRepository.deleteById(id)
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));

    }

    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'userid_'+#userId.toString()")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteRoleByUserId(Integer userId) {
        Assert.notNull(userId,ExceptionMessage.USERIDNOTNULLPARAMETERIZATION);
        return reactiveRoleSortingRepository.deleteByUserId(userId)
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));

    }


    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'username_' +#userName")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteRoleByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);
        return reactiveRoleSortingRepository.deleteRoleByUserName(userName)
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }




}
