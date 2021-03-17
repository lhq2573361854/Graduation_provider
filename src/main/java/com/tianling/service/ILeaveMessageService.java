package com.tianling.service;

import com.tianling.entities.LeaveMessage;
import com.tianling.entities.ResponseInfo;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 留言表 服务类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-02-22
 */
public interface ILeaveMessageService {
    /**
     * 获取全部留言
     * @return
     */
    Mono<ResponseInfo<LeaveMessage>> getLeaveMessageAll();

    /**
     * 通过用户名获取留言
     * @param userName
     * @return
     */
    Mono<ResponseInfo<LeaveMessage>> getLeaveMessageByUserName(String userName);

    /**
     * 通过id获取留言
     * @param id
     * @return
     */
    Mono<ResponseInfo<LeaveMessage>> getLeaveMessageById(Integer id);

    /**
     * 通过用户id获取留言
     * @param userId
     * @return
     */
    Mono<ResponseInfo<LeaveMessage>> getLeaveMessageByUserId(Integer userId);

    /**
     * 添加留言
     * @param leaveMessage
     * @return
     */
    Mono<ResponseInfo<LeaveMessage>> addLeaveMessage(LeaveMessage leaveMessage);

    /**
     * 更新留言
     * @param leaveMessage
     * @return
     */
    Mono<ResponseInfo<LeaveMessage>> updateLeaveMessage(LeaveMessage leaveMessage);

    /**
     * 通过id删除用户留言
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteLeaveMessageById(Integer id);

    /**
     * 通过用户名删除用户留言
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteLeaveMessageByUserName(String userName);
}
