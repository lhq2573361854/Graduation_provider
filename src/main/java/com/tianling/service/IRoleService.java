package com.tianling.service;

import com.tianling.entities.ResponseInfo;
import com.tianling.entities.Role;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 用户的角色表 服务类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
public interface IRoleService{
    /**
     * 通过id获取角色
     * @param id
     * @return
     */
    Mono<ResponseInfo<Role>> getRoleById(Integer id);

    /**
     * 通过用户id获取角色
     * @param userId
     * @return
     */
    Mono<ResponseInfo<Role>> getRoleByUserId(Integer userId);

    /**
     * 从用户名获取role
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Role>> getRoleByUserName(String userName);

    /**
     * 增加用户权限 role
     * @param role
     * @return
     */
    Mono<ResponseInfo<Role>> updateRole(Role role);


    /**
     * 获取所有用户权限
     * @return
     */
    Mono<ResponseInfo<Role>> getAllRole();

    /**
     * 给用户添加 角色
     * @param role
     * @return
     */
    Mono<ResponseInfo<Role>> addRole(Role role);

    /**
     * 通过用户id删除用户角色
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteRoleById(Integer id);

    /**
     * 通过用户id删除角色
     * @param userId
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteRoleByUserId(Integer userId);

    /**
     * 通过用户名删除用户角色
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteRoleByUserName(String userName);
}
