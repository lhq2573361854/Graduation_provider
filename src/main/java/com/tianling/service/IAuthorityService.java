package com.tianling.service;

import com.tianling.entities.Authority;
import com.tianling.entities.ResponseInfo;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 用户所有的权限表 服务类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
public interface IAuthorityService {

    /**
     * 获取用户的权限
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Authority>> getAuthorityByUserName(String userName);

    /**
     * 获取用户的权限
     * @param userId
     * @return
     */
    Mono<ResponseInfo<Authority>> getAuthorityByUserId(Integer userId);




    /**
     * 添加用户的权限信息
     * @param authority
     * @return
     */
    Mono<ResponseInfo<Authority>> addAuthority(Authority authority);

    /**
     * 更新用户的信息
     * @param authority
     * @return
     */
    Mono<ResponseInfo<Authority>> updateAuthority(Authority authority);

    /**
     * 删除用户权限通过id
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteAuthorityById(Integer id);

    /**
     * 删除用户权限通过用户名
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteAuthorityByUserName(String userName);

    /**
     * 删除用户权限通过用户名
     * @param userId
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteAuthorityByUserId(Integer userId);

    /**
     * 获取所有人的Authority信息
     * @return
     */
    Mono<ResponseInfo<Authority>> getAllAuthority();

    /**
     * 通过id获取权限内容
     * @param id
     * @return
     */
    Mono<ResponseInfo<Authority>> getAuthorityById(Integer id);
}
