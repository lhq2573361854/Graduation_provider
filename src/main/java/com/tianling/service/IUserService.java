package com.tianling.service;

import com.tianling.entities.ResponseInfo;
import com.tianling.entities.User;
import reactor.core.publisher.Mono;

/**
 * 用户的业务
 *
 * @author TianLing
 */
public interface IUserService {
    /**
     * 获取用户列表
     *
     * @return
     */
    Mono<ResponseInfo<User>> getAllUser();

    /**
     * 通过id获取
     * @param id
     * @return
     */
    Mono<ResponseInfo<User>> getUserById(Integer id);

    /**
     * 更具用户名获取用户
     * @param userName
     * @return
     */
    Mono<ResponseInfo<User>> getUserByUserName(String userName);

    /**
     * 创建用户
     * @param user
     * @return
     */
    Mono<ResponseInfo<User>> createUser(User user);

    /**
     * 删除用户
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteUserByUserName(String userName);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteUserById(Integer id);

    /**
     * 更新用户数据 通过id
     * @param user
     * @return
     */
    Mono<ResponseInfo<User>> updateUserById(User user);

    /**
     * 更新用户数据 通过username
     * @param user
     * @return
     */
    Mono<ResponseInfo<User>> updateUserByUserName(User user);

    /**
     * 通过手机号查询用户
     * @param phone
     * @return
     */
    Mono<ResponseInfo<User>> getUserByUserPhone(String phone);
}
