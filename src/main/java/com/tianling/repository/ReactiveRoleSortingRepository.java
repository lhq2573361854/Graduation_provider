package com.tianling.repository;

import com.tianling.entities.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * <p>
 *   评论的dao
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-12
 */
@Repository
public interface ReactiveRoleSortingRepository extends ReactiveSortingRepository<Role,Integer> {

    /**
     * 通过UserId 获取用户的角色
     * @param id
     * @return
     */
    Mono<Role> getRoleByUserId(Integer id);

    /**
     *通过用户名获取用户的权限信息
     * @param userName
     * @return
     */
    @Query("select * from  role where user_id = (select id from user where user_name=:userName)")
    Mono<Role> getRoleByUserName(String userName);

    /**
     * 清空角色通过用户名
     * @param userName
     * @return
     */
    @Query("delete from role where user_id = (select id  from user where user_name=:userName)")
    Mono<Void> deleteRoleByUserName(String userName);

    /**
     * 通过用户id删除
     * @param userId
     * @return
     */
    Mono<Void> deleteByUserId(Integer userId);
}
