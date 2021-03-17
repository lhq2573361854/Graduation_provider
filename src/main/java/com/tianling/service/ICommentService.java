package com.tianling.service;

import com.tianling.entities.Comment;
import com.tianling.entities.Pagination;
import com.tianling.entities.ResponseInfo;
import reactor.core.publisher.Mono;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-12
 */
public interface ICommentService{
    /**
     * 获取所有的评论信息
     * @return
     */
    Mono<ResponseInfo<Comment>> getAllComment();

    /**
     * 通过用户名获取评论
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Comment>> getCommentByUserName(String userName);

    /**
     * 通过用户的id获取评论信息
     * @param id
     * @return
     */
    Mono<ResponseInfo<Comment>> getCommentById(Integer id);

    /**
     * 更新用户信息
     * @param comment
     * @return
     */
    Mono<ResponseInfo<Comment>> updateComment(Comment comment);

    /**
     * 通过用户id
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteCommentById(Integer id);

    /**
     * 通过用户名删除
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteCommentByUserName(String userName);

    /**
     * 增加star 评论的点赞数量
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> setCommentStar(Integer id);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    Mono<ResponseInfo<Comment>> addComment(Comment comment);

    /**
     * 通过文章获取评论信息
     * @param articleId
     * @return
     */
    Mono<ResponseInfo<Comment>> getCommentByArticleId(Integer articleId);

    /**
     *  分页查询-- 通过文章id
     * @param pagination
     * @param articleId
     * @return
     */
    Mono<ResponseInfo<Comment>> getPaginationByArticleId(Integer articleId,Pagination pagination);


    /**
     * 获取某个文章 评论的条数
     * @param articleId
     * @return
     */
    Mono<ResponseInfo<Integer>> getCommentTotalByArticleId(Integer articleId);
}
