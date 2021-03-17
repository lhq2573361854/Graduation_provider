package com.tianling.repository;

import com.tianling.entities.User;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * <p>
 *   用户的DAO
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-12
 */
@Repository
public interface ReactiveUserSortingRepository  extends ReactiveSortingRepository<User,Integer> {
    /**
     * 获取用户
     * @param userName
     * @return
     */
    Mono<User> getUserByUserName(String userName);

    /**
     *  通过用户名删除用户
     * @param userName
     * @return
     */
    Mono<Void> deleteUserByUserName(String userName);

    /**
     * 获取用户通过电话号码
     * @param userPhone
     * @return
     */
    Mono<User> getUserByUserPhone(String userPhone);
}
