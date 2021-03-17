package com.tianling.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheEvict;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCachePut;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheable;
import com.tianling.common.ExceptionMessage;
import com.tianling.entities.Comment;
import com.tianling.entities.Pagination;
import com.tianling.entities.ResponseInfo;
import com.tianling.repository.ReactiveCommentSortingRepository;
import com.tianling.service.ICommentService;
import com.tianling.utils.HttpResponseMessageUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-12
 */
@Service
public class CommentServiceImpl implements ICommentService {
    private static final String BASENAME = "comment_cache_comment_";

    @Resource
    ReactiveCommentSortingRepository reactiveCommentSortingRepository;

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "all")
    @Override
    public Mono<ResponseInfo<Comment>> getAllComment() {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveCommentSortingRepository.findAll());
    }

    @ReactiveRedisCacheable(cacheName = BASENAME,key = "'username_' + #userName")
    @Override
    public Mono<ResponseInfo<Comment>> getCommentByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);
        return HttpResponseMessageUtils.querySuccessResponse(reactiveCommentSortingRepository.getCommentByUserName(userName));
    }

    @Override
    public Mono<ResponseInfo<Comment>> getCommentByArticleId(Integer articleId) {
        Assert.notNull(articleId,ExceptionMessage.IDNULLPARAMETERIZATION);
        return HttpResponseMessageUtils.querySuccessResponse(reactiveCommentSortingRepository.getCommentByCommentArticleId(articleId));
    }

    @Override
    public Mono<ResponseInfo<Comment>> getPaginationByArticleId(Integer articleId,Pagination pagination) {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveCommentSortingRepository.getCommentByCommentArticleId(articleId).skip((long) (pagination.getPage() - 1) * pagination.getPageShowNumber()).take(pagination.getPageShowNumber()));
    }

    @Override
    public Mono<ResponseInfo<Integer>> getCommentTotalByArticleId(Integer articleId) {
        return reactiveCommentSortingRepository.getCommentTotalByArticleId(articleId)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }


    @ReactiveRedisCacheable(cacheName = BASENAME,key = "'id_' + #id.toString()")
    @Override
    public Mono<ResponseInfo<Comment>> getCommentById(Integer id) {
        Assert.notNull(id,ExceptionMessage.ARTICLEIDNOTNULLPARAMETERIZATION);
        return reactiveCommentSortingRepository.getCommentById(id).flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.ARTICLEIDNOTEXISTPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<Comment>> addComment(Comment comment) {
        return reactiveCommentSortingRepository.save(comment)
                .flatMap(comment1 -> reactiveCommentSortingRepository.findById(comment1.getId()))
                .flatMap(HttpResponseMessageUtils::insertSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.insertFailedCommonResponse());
    }

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'id_' + #comment.getId().toString()")
    @Override
    public Mono<ResponseInfo<Comment>> updateComment(Comment comment) {
        return reactiveCommentSortingRepository.findById(comment.getId())
                .flatMap(comment1 -> {
                    if(!StrUtil.equals(comment1.getCommentContent(),comment.getCommentContent())){
                        comment1.setCommentContent(comment.getCommentContent());
                    }
                 return reactiveCommentSortingRepository.save(comment1)
                         .flatMap(HttpResponseMessageUtils::updateSuccessResponse);
                })
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse());
    }



    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'id_' + #id.toString()")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteCommentById(Integer id) {

        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);

        return reactiveCommentSortingRepository.findById(id)
                .flatMap(comment1 -> reactiveCommentSortingRepository.deleteById(comment1.getId()))
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }

    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'username_' + #userName")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteCommentByUserName(String userName) {

        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);

        return reactiveCommentSortingRepository.deleteCommentByUserName(userName)
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }



    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'id_' + id.toString()")
    @Override
    public Mono<ResponseInfo<Boolean>> setCommentStar(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);

        return reactiveCommentSortingRepository.findById(id).flatMap(comment -> {
            comment.setCommentStars(comment.getCommentStars()+1);
            return reactiveCommentSortingRepository.save(comment);
        })
                .flatMap(comment -> HttpResponseMessageUtils.updateSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }



}
