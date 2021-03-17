package com.tianling.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCachePut;
import com.tianling.common.ExceptionMessage;
import com.tianling.entities.LeaveMessage;
import com.tianling.entities.ResponseInfo;
import com.tianling.repository.ReactiveLeaveMessageSortingRepository;
import com.tianling.service.ILeaveMessageService;
import com.tianling.utils.HttpResponseMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 * 留言表 服务实现类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-02-22
 */
@Service
@Slf4j
public class LeaveMessageServiceImpl implements ILeaveMessageService {
    private static final String BASENAME = "leave_message_cache_leave_message_";

    @Resource
    ReactiveLeaveMessageSortingRepository reactiveLeaveMessageSortingRepository;

    @ReactiveRedisCachePut(cacheName = BASENAME,key=" 'all' ")
    @Override
    public Mono<ResponseInfo<LeaveMessage>> getLeaveMessageAll() {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveLeaveMessageSortingRepository.findAll());
    }

    @Override
    public Mono<ResponseInfo<LeaveMessage>> getLeaveMessageByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);
        return reactiveLeaveMessageSortingRepository.getLeaveMessageByUserName(userName)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.USERDATANOTEXISTPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<LeaveMessage>> getLeaveMessageById(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveLeaveMessageSortingRepository.findById(id)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<LeaveMessage>> getLeaveMessageByUserId(Integer userId) {
        Assert.notNull(userId,ExceptionMessage.USERIDNOTEXISTPARAMETERIZATION);
        return reactiveLeaveMessageSortingRepository.getLeaveMessageByUserId(userId)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.USERIDNOTEXISTPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<LeaveMessage>> addLeaveMessage(LeaveMessage leaveMessage) {
        return reactiveLeaveMessageSortingRepository.save(leaveMessage)
                .flatMap(leaveMessage1 -> reactiveLeaveMessageSortingRepository.findById(leaveMessage1.getId()))
                .flatMap(HttpResponseMessageUtils::insertSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.insertFailedCommonResponse());

    }

    @Override
    public Mono<ResponseInfo<LeaveMessage>> updateLeaveMessage(LeaveMessage leaveMessage) {
        return reactiveLeaveMessageSortingRepository
                .findById(leaveMessage.getId()).flatMap(leaveMessage1 -> {
                    if (!StrUtil.isBlank(leaveMessage.getMessage())) {
                        leaveMessage1.setMessage(leaveMessage.getMessage());
                    }
                    return reactiveLeaveMessageSortingRepository.save(leaveMessage1);
                }).flatMap(HttpResponseMessageUtils::updateSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse());
    }

    @Override
    public Mono<ResponseInfo<Boolean>> deleteLeaveMessageById(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveLeaveMessageSortingRepository.deleteById(id).then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse());
    }

    @Override
    public Mono<ResponseInfo<Boolean>> deleteLeaveMessageByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);
        return reactiveLeaveMessageSortingRepository.deleteByUserName(userName).then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(Boolean.FALSE));
    }
}
