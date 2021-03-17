package com.tianling.controller;


import com.tianling.entities.Authority;
import com.tianling.entities.ResponseInfo;
import com.tianling.service.IAuthorityService;
import com.tianling.validator.authority.AuthorityAddValidator;
import com.tianling.validator.authority.AuthorityUpdateValidator;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 * 用户所有的权限表 前端控制器
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController{
    @Resource
    private IAuthorityService iAuthorityService;

    @GetMapping("/getAllAuthority")
    public Mono<ResponseInfo<Authority>> getAllAuthority(){
        return iAuthorityService.getAllAuthority();
    }

    @GetMapping("/getAuthorityById/{id}")
    public Mono<ResponseInfo<Authority>> getAuthorityById(@PathVariable Integer id){
        return iAuthorityService.getAuthorityById(id);
    }

    @GetMapping("/getAuthorityByUserName/{userName}")
    public Mono<ResponseInfo<Authority>> getUserAuthorityByUserName(@PathVariable String userName){
        return iAuthorityService.getAuthorityByUserName(userName);
    }

    @GetMapping("/getAuthorityByUserId/{userId}")
    public Mono<ResponseInfo<Authority>> getUserAuthorityByUserName(@PathVariable Integer userId){
        return iAuthorityService.getAuthorityByUserId(userId);
    }

    @PostMapping(value = "/addAuthority",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Authority>> addAuthority(@Validated(AuthorityAddValidator.class)  @RequestBody Authority authority) {
        return iAuthorityService.addAuthority(authority);
    }

    @PostMapping(value = "/updateAuthority",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Authority>> updateAuthority(@Validated(AuthorityUpdateValidator.class)  @RequestBody Authority authority) {
        return iAuthorityService.updateAuthority(authority);
    }

    @DeleteMapping(value = "/deleteAuthorityById/{id}")
    public Mono<ResponseInfo<Boolean>> deleteAuthorityById(@PathVariable Integer id) {
        return iAuthorityService.deleteAuthorityById(id);
    }

    @DeleteMapping(value = "/deleteAuthorityByUserId/{id}")
    public Mono<ResponseInfo<Boolean>> deleteAuthorityByUserId(@PathVariable Integer id) {
        return iAuthorityService.deleteAuthorityByUserId(id);
    }

    @DeleteMapping(value = "/deleteAuthorityByUserId/{userName}")
    public Mono<ResponseInfo<Boolean>> deleteAuthorityByUserId(@PathVariable String userName) {
        return iAuthorityService.deleteAuthorityByUserName(userName);
    }





}
