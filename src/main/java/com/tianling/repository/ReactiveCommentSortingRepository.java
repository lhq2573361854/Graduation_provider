package com.tianling.repository;

import com.tianling.entities.Comment;
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
public interface ReactiveCommentSortingRepository extends ReactiveSortingRepository<Comment,Integer> {

    /**
     * 通过用户名获取密码
     * @param userName
     * @return
     */
    @Query("select * from comment where comment_user_id = (select id from user where user_name = :userName)")
    Flux<Comment> getCommentByUserName(String userName);



    /**
     * 通过id 获取评论
     * @param id
     * @return
     */
    Mono<Comment> getCommentById(Integer id);

    /**
     * 通过文章id获取评论信息
     * @param commentArticleId
     * @return
     */
    Flux<Comment> getCommentByCommentArticleId(Integer commentArticleId);

    /**
     * 通过用户名查找用户评论
     * @param userName
     * @return
     */
    @Query("delete from comment where comment_user_id = (select id from user where user_name=:userName)")
    Mono<Void> deleteCommentByUserName(String userName);

    /**
     * 某片文章评论的条数
     * @param articleId
     * @return
     */
    @Query("select count(1) from comment where comment_article_id=:articleId ")
    Mono<Integer> getCommentTotalByArticleId(Integer articleId);

}
