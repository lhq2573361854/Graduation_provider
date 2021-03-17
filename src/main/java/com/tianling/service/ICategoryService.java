package com.tianling.service;

import com.tianling.entities.Category;
import com.tianling.entities.ResponseInfo;
import reactor.core.publisher.Mono;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/2/8 22:36
 */
public interface ICategoryService {
    /**
     * 返回所有的栏目
     * @return
     */
    Mono<ResponseInfo<Category>> getAllCategory();

    /**
     * 通过id获取栏目
     * @param id
     * @return
     */
    Mono<ResponseInfo<Category>> getCategoryById(Integer id);

    /**通过栏目名 获取栏目
     * @param categoryName
     * @return
     */
    Mono<ResponseInfo<Category>> getCategoryByCategoryName(String categoryName);

    /**
     * 添加栏目
     * @param category
     * @return
     */
    Mono<ResponseInfo<Category>> addCategory(Category category);

    /**
     * 修改栏目信息 通过栏目名
     * @param category
     * @return
     */
    Mono<ResponseInfo<Category>> updateCategoryByCategoryName(Category category);

    /**
     * 修改栏目信息 通过栏目id
     * @param category
     * @return
     */
    Mono<ResponseInfo<Category>> updateCategoryById(Category category);

    /**
     *  删除栏目 通过栏目id
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteCategoryById(Integer id);

    /**
     * 删除栏目 通过栏目名字
     * @param categoryName
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteCategoryByCategoryName(String categoryName);
}
