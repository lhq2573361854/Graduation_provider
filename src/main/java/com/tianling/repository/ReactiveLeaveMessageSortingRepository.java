package com.tianling.repository;

import com.tianling.entities.LeaveMessage;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/2/22 20:07
 */
public interface ReactiveLeaveMessageSortingRepository extends ReactiveSortingRepository<LeaveMessage,Integer> {

    /**
     * 通过用户 id获取留言
     *
     * @param userId
     * @return
     */
    Mono<LeaveMessage> getLeaveMessageByUserId(Integer userId);

    /**
     * 通过用户名获取
     *
     * @param userName
     * @return
     */
    @Query("select * from leave_message where user_id = (select id from user where user_name = :userName)")
    Mono<LeaveMessage> getLeaveMessageByUserName(String userName);

    /**
     * 删除用户通过用户名
     * @param userName
     * @return
     */
    @Query("delete from leave_message where user_id = (select id  from user where user_name=:userName)")
    Mono<Void> deleteByUserName(String userName);




}
