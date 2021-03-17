package com.tianling.controller;


import com.tianling.entities.ResponseInfo;
import com.tianling.entities.Role;
import com.tianling.service.IRoleService;
import com.tianling.validator.role.RoleAddValidator;
import com.tianling.validator.role.RoleUpdateValidator;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 * 用户的角色表 前端控制器
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/role")
public class RoleController{

    @Resource
    IRoleService iRoleService;

    @GetMapping("/getAllRole")
    public Mono<ResponseInfo<Role>> getAllRole(){
        return iRoleService.getAllRole();
    }

    @GetMapping("/getRoleById/{id}")
    public Mono<ResponseInfo<Role>> getRoleById(@PathVariable Integer id){
        return iRoleService.getRoleById(id);
    }

    @GetMapping("/getRoleByUserId/{userId}")
    public Mono<ResponseInfo<Role>> getRoleByUserId(@PathVariable Integer userId){
        return iRoleService.getRoleByUserId(userId);
    }

    @GetMapping("/getRoleByUserName/{userName}")
    public Mono<ResponseInfo<Role>> getRoleByUserName(@PathVariable String userName){
        return iRoleService.getRoleByUserName(userName);
    }
    @PostMapping(value = "/addRole", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Role>> addRole(@Validated(RoleAddValidator.class) @RequestBody  Role role){
        return iRoleService.addRole(role);
    }

    @PostMapping(value = "/updateRole", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Role>> updateRole(@Validated(RoleUpdateValidator.class) @RequestBody Role role){
        return iRoleService.updateRole(role);
    }

    @DeleteMapping(value = "/deleteRoleById/{id}")
    public Mono<ResponseInfo<Boolean>> deleteRoleById(@PathVariable Integer id){
        return iRoleService.deleteRoleById(id);
    }

    @DeleteMapping(value = "/deleteRoleByUserId/{userId}")
    public Mono<ResponseInfo<Boolean>> deleteRoleByUserId(@PathVariable Integer userId){
        return iRoleService.deleteRoleByUserId(userId);
    }

    @DeleteMapping(value = "/deleteRoleByUserName/{userName}")
    public Mono<ResponseInfo<Boolean>> deleteRoleByUserName(@PathVariable String userName){
        return iRoleService.deleteRoleByUserName(userName);
    }
}
