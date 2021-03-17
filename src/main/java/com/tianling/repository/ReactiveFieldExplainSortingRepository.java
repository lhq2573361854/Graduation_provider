package com.tianling.repository;

import com.tianling.entities.FieldExplain;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/3/4 14:53
 */
@Repository
public interface ReactiveFieldExplainSortingRepository extends ReactiveSortingRepository<FieldExplain,Integer > {
}
