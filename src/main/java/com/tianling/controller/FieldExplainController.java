package com.tianling.controller;


import com.tianling.entities.FieldExplain;
import com.tianling.entities.ResponseInfo;
import com.tianling.service.IFieldExplainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Tian Ling
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/field-explain")
public class FieldExplainController {
    @Resource
    IFieldExplainService iFieldExplainService;

    @GetMapping("/getAllFieldExplain")
    public Mono<ResponseInfo<FieldExplain>> getAllFieldExplain(){
        return iFieldExplainService.getAllFieldExplain();
    }
}
