package com.tianling.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCachePut;
import com.tianling.common.ExceptionMessage;
import com.tianling.entities.Category;
import com.tianling.entities.ResponseInfo;
import com.tianling.repository.ReactiveCategorySortingRepository;
import com.tianling.service.ICategoryService;
import com.tianling.utils.HttpResponseMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/2/8 22:37
 */
@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    ReactiveCategorySortingRepository reactiveCategorySortingRepository;

    private static final String BASENAME = "category_cache_category_";

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "all")
    @Override
    public Mono<ResponseInfo<Category>> getAllCategory() {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveCategorySortingRepository.findAll());
    }

    @Override
    public Mono<ResponseInfo<Category>> getCategoryById(Integer id) {
        Assert.notNull(id,ExceptionMessage.CATEGORYIDNOTNULLPARAMETERIZATION);
        return reactiveCategorySortingRepository.findById(id).flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.CATEGORYIDNOTEXISTPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<Category>> getCategoryByCategoryName(String categoryName) {
        Assert.notNull(categoryName,ExceptionMessage.CATEGORYNAMENULLPARAMETERIZATION);
        return reactiveCategorySortingRepository.getCategoryByCategoryName(categoryName).flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.CATEGORYNAMENOTEXISTPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<Category>> addCategory(Category category) {
        Assert.notNull(category,ExceptionMessage.PARAMETERIZATION);
        return reactiveCategorySortingRepository.save(category).flatMap(HttpResponseMessageUtils::insertSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.insertFailedCommonResponse());
    }

    @Override
    public Mono<ResponseInfo<Category>> updateCategoryByCategoryName(Category category) {
        return updateCategoryData(category, reactiveCategorySortingRepository.getCategoryByCategoryName(category.getCategoryName()));
    }

    private Mono<ResponseInfo<Category>> updateCategoryData(Category category, Mono<Category> categoryByCategoryName) {
        return categoryByCategoryName.flatMap(oldCategory -> {
            if (ObjectUtil.isNotEmpty(category.getCategoryName())) {
                oldCategory.setCategoryName(category.getCategoryName());
            }

            if (ObjectUtil.isNotEmpty(category.getCategoryAlias())) {
                oldCategory.setCategoryAlias(category.getCategoryAlias());
            }

            if (ObjectUtil.isNotEmpty(category.getCategoryDesc())) {
                oldCategory.setCategoryDesc(category.getCategoryDesc());
            }
            return reactiveCategorySortingRepository.save(oldCategory);

        }).flatMap(HttpResponseMessageUtils::updateSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse());
    }

    @Override
    public Mono<ResponseInfo<Category>> updateCategoryById(Category category) {
        return updateCategoryData(category, reactiveCategorySortingRepository.findById(category.getId()));
    }

    @Override
    public Mono<ResponseInfo<Boolean>> deleteCategoryById(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveCategorySortingRepository.deleteById(id).then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.CATEGORYIDNOTEXISTPARAMETERIZATION));

    }

    @Override
    public Mono<ResponseInfo<Boolean>> deleteCategoryByCategoryName(String categoryName) {
        Assert.notNull(categoryName,ExceptionMessage.CATEGORYNAMENULLPARAMETERIZATION);
        return reactiveCategorySortingRepository.deleteByCategoryName(categoryName).then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.CATEGORYIDNOTEXISTPARAMETERIZATION));
    }


}
