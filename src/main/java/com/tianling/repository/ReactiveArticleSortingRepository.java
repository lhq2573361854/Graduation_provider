package com.tianling.repository;


import com.tianling.entities.Article;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>
 *   评论的dao
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-12
 */
@Repository
public interface ReactiveArticleSortingRepository extends ReactiveSortingRepository<Article,Integer> {

    /**
     * 查询用户的文章信息
     * @param userName
     * @return
     */
    @Query("select * from article where user_id = (select id from user where user_name=:userName) ")
    Flux<Article> getArticleByUserName(String userName);

    /**
     * 通过用户名删除用户的文章
     * @param userName
     * @return
     */
    @Query("delete from article where user_id = (select id from user where user_name=:userName) ")
    Mono<Void> deleteArticleByUserName(String userName);

    /**
     * 查询所有的记录
      * @return
     */
    @Query("select count(1) from article")
    Mono<Integer> articleTotal();

    /**
     * 查询指定栏目的记录
     * @param categoryName
     * @return
     */
    @Query("select count(1) from article where category_id = (select id from category where category_name=:categoryName) ")
    Mono<Integer> articleTotalByCategoryName(String categoryName);

    /**
     * 查询对应栏目的文章
     * @param categoryName
     * @return
     */
    @Query("select * from article where category_id = (select id from category where category_name=:categoryName) ")
    Flux<Article> getArticlesByCategoryName(String categoryName);
}
