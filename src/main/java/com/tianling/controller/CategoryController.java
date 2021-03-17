package com.tianling.controller;

import com.tianling.entities.Category;
import com.tianling.entities.ResponseInfo;
import com.tianling.service.ICategoryService;
import com.tianling.validator.category.CategoryAddValidator;
import com.tianling.validator.category.CategoryUpdateCategoryIdValidator;
import com.tianling.validator.category.CategoryUpdateCategoryNameValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/2/8 22:35
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Resource
    ICategoryService iCategoryService;
    @GetMapping("/getAllCategory")
    public Mono<ResponseInfo<Category>> getAllUser() {
        return iCategoryService.getAllCategory();
    }

    @GetMapping("/getCategoryById/{id}")
    public Mono<ResponseInfo<Category>> getCategoryById(@PathVariable Integer id) {
        return iCategoryService.getCategoryById(id);
    }

    @GetMapping("/getCategoryByCategoryName/{categoryName}")
    public Mono<ResponseInfo<Category>> getCategoryByCategoryName(@PathVariable String categoryName) {
        return iCategoryService.getCategoryByCategoryName(categoryName);
    }

    @PostMapping(value = "/addCategory",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Category>> addCategory(@Validated(CategoryAddValidator.class)  @RequestBody Category Category) {
        return iCategoryService.addCategory(Category);
    }

    @PostMapping(value = "/updateCategoryByCategoryName",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Category>> updateCategoryByCategoryName(@Validated(CategoryUpdateCategoryNameValidator.class)  @RequestBody Category Category) {
        return iCategoryService.updateCategoryByCategoryName(Category);
    }

    @PostMapping(value = "/updateCategoryById",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Category>> updateCategoryById(@Validated(CategoryUpdateCategoryIdValidator.class) @RequestBody Category Category) {
        return iCategoryService.updateCategoryById(Category);
    }

    @DeleteMapping(value = "/deleteCategoryById/{id}")
    public Mono<ResponseInfo<Boolean>> deleteCategoryById(@PathVariable Integer id) {
        return iCategoryService.deleteCategoryById(id);
    }

    @DeleteMapping(value = "/deleteCategoryByCategoryName/{categoryName}")
    public Mono<ResponseInfo<Boolean>> deleteCategoryByCategoryName(@PathVariable String categoryName) {
        return iCategoryService.deleteCategoryByCategoryName(categoryName);
    }
    




}
