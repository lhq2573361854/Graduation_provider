package com.tianling.service;

import com.tianling.entities.Article;
import com.tianling.entities.Pagination;
import com.tianling.entities.ResponseInfo;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 文章信息 服务类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
public interface IArticleService {

    /**
     * 获取所有的额文章信息
     * @return
     */
    Mono<ResponseInfo<Article>> getAllArticle();

    /**
     * 获取分页
     * @param page
     * @return
     */
    Mono<ResponseInfo<Article>> getPagination(Pagination page);

    /**
     * 根据用户名查询用户的文章信息
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Article>> getArticleByUserName(String userName);

    /**
     * 更剧用户id获取
     * @param id
     * @return
     */
    Mono<ResponseInfo<Article>> getArticleByUserId(Integer id);

    /**
     * 插入一个新的数据
     * @param article
     * @return
     */
    Mono<ResponseInfo<Article>> insertArticle(Article article);

    /**
     * 更新用户数据
     * @param article
     * @return
     */
    Mono<ResponseInfo<Article>> updateArticle(Article article);


    /**
     * 删除指定的用户数据通过id
     *
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteArticleById(Integer id);
    /**
     * 删除指定的用户数据通过username
     *
     * @param userName
     * @return
     */
    Mono<ResponseInfo<Boolean>> deleteArticleByUserName(String userName);

    /**
     * 更新文章的star
     * @param id
     * @return
     */
    Mono<ResponseInfo<Boolean>> setArticleStar(Integer id);

    /**
     * 返回文章总数
     * @return
     */
    Mono<ResponseInfo<Integer>> getAllArticleTotal();

    /**
     * 推荐文章
     * @param limit
     * @return
     */
    Mono<ResponseInfo<Article>> getCommandArticle(Integer limit);

    /**
     * 返回对应的栏目的数量
     * @param categoryName
     * @return
     */
    Mono<ResponseInfo<Integer>> getArticlesTotalByCategoryName(String categoryName);

    /**
     * 返回对应的栏目的文章
     * @param categoryName
     * @return
     */
    Mono<ResponseInfo<Article>> getPageArticleByCategoryName(Pagination pagination, String categoryName);



    /**
     * 通过用户id查询文章
     * @param  userId
     * @return
     */
    Mono<ResponseInfo<Article>> getArticlesByUserId(Integer userId);
}
