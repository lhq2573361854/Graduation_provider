package com.tianling.repository;

import com.tianling.entities.Authority;
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
public interface ReactiveAuthoritySortingRepository extends ReactiveSortingRepository<Authority,Integer> {
    /**
     * 更剧用户名获取用户权限
     *
     * @param userName
     * @return
     */
    @Query("select * from authority where user_id = (select id from user where user_name=:userName)")
    Mono<Authority> getUserAuthority(String userName);

    /**
     * 删除 权限 通过userId
     * @param userId
     * @return
     */
    Mono<Void> deleteAuthorityById(Integer userId);

    /**
     * 删除 权限 通过用户名
     * @param userName
     * @return
     */
    @Query("delete from authority where user_id = (select id from user where user_name=:userName)")
    Mono<Void> deleteByUserName(String userName);

    /**
     * 通过userId获取
     * @param userId
     * @return
     */
    Mono<Authority> getAuthorityByUserId(Integer userId);
}
