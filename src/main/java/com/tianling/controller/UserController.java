package com.tianling.controller;

import com.tianling.entities.ResponseInfo;
import com.tianling.entities.User;
import com.tianling.service.IUserService;
import com.tianling.validator.user.UserAddValidator;
import com.tianling.validator.user.UserUpdateByIdValidator;
import com.tianling.validator.user.UserUpdateByUserNameValidator;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 用户的控制器
 *
 * @author TianLing
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;
    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/getAllUser")
    public Mono<ResponseInfo<User>> getAllUser() {
        return userService.getAllUser();
    }

    /**
     * 查询
     * @param id
     * @return
     */
    @GetMapping("/getUserById/{id}")
    public Mono<ResponseInfo<User>> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }


    /**
     * 查询
     * @param phone
     * @return
     */
    @GetMapping("/getUserByUserPhone/{phone}")
    public Mono<ResponseInfo<User>> getUserByUserPhone(@PathVariable String phone) {
        return userService.getUserByUserPhone(phone);
    }

    /**
     * 查询 指定用户
     *
     * @return
     */
    @GetMapping("/getUserByUserName/{userName}")
    public Mono<ResponseInfo<User>> getUserByUserName(@PathVariable String userName) {
        return userService.getUserByUserName(userName);
    }

    /**
     * 创建用户
     *
     * @return
     */
    @PostMapping(value = "/createUser",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<User>> createUser( @Validated(UserAddValidator.class) @RequestBody  User user) {
        return userService.createUser(user);
    }

    /**
     * 删除用户 通过id
     *
     * @return
     */
    @DeleteMapping(value = "/deleteUserById/{id}")
    public Mono<ResponseInfo<Boolean>> deleteUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }


    /**
     * 删除用户 通过用户名
     *
     * @return
     */
    @DeleteMapping(value = "/deleteUserByUserName/{userName}")
    public Mono<ResponseInfo<Boolean>> deleteUserByUserName(@PathVariable String userName) {
        return userService.deleteUserByUserName(userName);
    }

    /**
     * 更新用户
     *
     * @return
     */
    @PostMapping(value = "/updateUserById", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<User>> updateUserById(@Validated({UserUpdateByIdValidator.class}) @RequestBody User user) {
        return userService.updateUserById(user);
    }

    /**
     * 更新用户
     *
     * @return
     */
    @PostMapping(value = "/updateUserByUserName", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<User>> updateUserByUserName(@Validated({UserUpdateByUserNameValidator.class}) @RequestBody User user) {
        return userService.updateUserByUserName(user);
    }

}
