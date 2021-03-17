package com.tianling.controller;


import com.tianling.entities.LeaveMessage;
import com.tianling.entities.ResponseInfo;
import com.tianling.service.ILeaveMessageService;
import com.tianling.validator.leave.message.LeaveMessageAddValidator;
import com.tianling.validator.leave.message.LeaveMessageUpdateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author Tian Ling
 * @since 2021-02-22
 */
@Slf4j
@RestController
@RequestMapping("/leave-message")
public class LeaveMessageController {
    @Resource
    ILeaveMessageService iLeaveMessageService;

    @GetMapping("/getAllLeaveMessage")
    public Mono<ResponseInfo<LeaveMessage>> getLeaveMessageAll() {
        return iLeaveMessageService.getLeaveMessageAll();
    }

    @GetMapping("/getLeaveMessageByUserName/{userName}")
    public Mono<ResponseInfo<LeaveMessage>> getLeaveMessageByUserName(@PathVariable String userName) {
        return iLeaveMessageService.getLeaveMessageByUserName(userName);
    }
    @GetMapping("/getLeaveMessageById/{id}")
    public Mono<ResponseInfo<LeaveMessage>> getLeaveMessageById(@PathVariable Integer id) {
        return iLeaveMessageService.getLeaveMessageById(id);
    }


    @GetMapping("/getLeaveMessageByUserId/{userId}")
    public Mono<ResponseInfo<LeaveMessage>> getLeaveMessageByUserId(@PathVariable Integer userId) {
        return iLeaveMessageService.getLeaveMessageByUserId(userId);
    }

    @PostMapping(value = "/addLeaveMessage", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<LeaveMessage>> addLeaveMessage(@Validated(LeaveMessageAddValidator.class) @RequestBody LeaveMessage leaveMessage) {
        log.info("leaveMessage = {}",leaveMessage);
        return iLeaveMessageService.addLeaveMessage(leaveMessage);
    }

    @PostMapping(value = "/updateLeaveMessage", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<LeaveMessage>> updateLeaveMessage(@Validated(LeaveMessageUpdateValidator.class) @RequestBody  LeaveMessage LeaveMessage) {
        return iLeaveMessageService.updateLeaveMessage(LeaveMessage);
    }

    @DeleteMapping(value = "/deleteLeaveMessageById/{id}")
    public Mono<ResponseInfo<Boolean>> deleteLeaveMessageById(@PathVariable Integer id) {
        return  iLeaveMessageService.deleteLeaveMessageById(id);
    }

    @DeleteMapping(value = "/deleteLeaveMessageByUserName/{userName}")
    public Mono<ResponseInfo<Boolean>> deleteLeaveMessageByUserName(@PathVariable String userName) {
        return  iLeaveMessageService.deleteLeaveMessageByUserName(userName);
    }
}
