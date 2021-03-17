package com.tianling.repository;

import com.tianling.entities.Category;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/2/8 22:35
 */
@Repository
public interface ReactiveCategorySortingRepository extends ReactiveSortingRepository<Category,Integer> {
    /**
     * 通过栏目名获取category信息
     * @param categoryName
     * @return
     */
     Mono<Category> getCategoryByCategoryName(String categoryName);

    /**
     * 删除通过categoryName
     * @param categoryName
     * @return
     */
    Mono<Void> deleteByCategoryName(String categoryName);
}
