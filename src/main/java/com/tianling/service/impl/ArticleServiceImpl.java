package com.tianling.service.impl;

import cn.hutool.core.lang.Assert;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheEvict;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCachePut;
import com.hanqunfeng.reactive.redis.cache.aop.ReactiveRedisCacheable;
import com.tianling.common.ExceptionMessage;
import com.tianling.entities.Article;
import com.tianling.entities.Pagination;
import com.tianling.entities.ResponseInfo;
import com.tianling.repository.ReactiveArticleSortingRepository;
import com.tianling.service.IArticleService;
import com.tianling.utils.HttpResponseMessageUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 * 文章信息 服务实现类
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    @Resource
    ReactiveArticleSortingRepository reactiveArticleSortingRepository;


    private static final String BASENAME = "article_cache_article_";

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "all")
    @Override
    public Mono<ResponseInfo<Article>> getAllArticle() {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveArticleSortingRepository.findAll());
    }

    @Override
    public Mono<ResponseInfo<Article>> getCommandArticle(Integer limit) {
        return  HttpResponseMessageUtils.querySuccessResponse( reactiveArticleSortingRepository.findAll().sort((article1,article2)-> article2.getArticleStars() - article1.getArticleStars())
                .take(limit));
    }


    @Override
    public Mono<ResponseInfo<Article>> getPagination(Pagination page) {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveArticleSortingRepository.findAll().skip((long) (page.getPage() - 1) * page.getPageShowNumber()).take(page.getPageShowNumber()));
    }

    @ReactiveRedisCacheable(cacheName = BASENAME,key = "'username_' + #userName")
    @Override
    public Mono<ResponseInfo<Article>> getArticleByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);
        return HttpResponseMessageUtils.querySuccessResponse(reactiveArticleSortingRepository.getArticleByUserName(userName));
    }

    @ReactiveRedisCacheable(cacheName = BASENAME,key = "'id_' + #id.toString()")
    @Override
    public Mono<ResponseInfo<Article>> getArticleByUserId(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveArticleSortingRepository.findById(id)
                .flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<Article>> insertArticle(Article article){
        return reactiveArticleSortingRepository.save(article)
                .flatMap(article1 -> reactiveArticleSortingRepository.findById(article1.getId()))
                .flatMap(HttpResponseMessageUtils::insertSuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.insertFailedCommonResponse());
    }

    @ReactiveRedisCachePut(cacheName = BASENAME,key = "'id_' +#article.getId().toString()")
    @Override
    public Mono<ResponseInfo<Article>> updateArticle(Article article) {
        return updateDataArticle(article.getId(),article);
    }

    private Mono<ResponseInfo<Article>> updateDataArticle(Integer id, Article article){

        return reactiveArticleSortingRepository.findById(id).flatMap(oldArticle->{
            if (article.getArticleDate() != null){
                oldArticle.setArticleDate(article.getArticleDate());
            }
            if (article.getArticleStars() != null){
                oldArticle.setArticleStars(oldArticle.getArticleStars()+article.getArticleStars());
            }
            if(article.getArticleContent() != null){
                oldArticle.setArticleContent(article.getArticleContent());
            }
            if (article.getArticleAddress() != null) {
                oldArticle.setArticleAddress(article.getArticleAddress());
            }
           return reactiveArticleSortingRepository.save(oldArticle);
        }).flatMap(HttpResponseMessageUtils::updateFailedResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }



    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'id_' + #article.getId().toString()")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteArticleById(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveArticleSortingRepository.findById(id)
                .flatMap(article1 -> reactiveArticleSortingRepository.deleteById(article1.getId()))
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }

    @ReactiveRedisCacheEvict(cacheName = BASENAME,key = "'username_' + #userName")
    @Override
    public Mono<ResponseInfo<Boolean>> deleteArticleByUserName(String userName) {
        Assert.notBlank(userName,ExceptionMessage.USERNAMENULLPARAMETERIZATION);
        return reactiveArticleSortingRepository.deleteArticleByUserName(userName)
                .then(HttpResponseMessageUtils.deleteSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.deleteFailedCommonResponse(ExceptionMessage.USERNAMENOTEXISTPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<Boolean>> setArticleStar(Integer id) {
        Assert.notNull(id,ExceptionMessage.IDNULLPARAMETERIZATION);
        return reactiveArticleSortingRepository.findById(id).flatMap(article -> {
            article.setArticleStars(article.getArticleStars()+1);
            return reactiveArticleSortingRepository.save(article);
        }).flatMap(article1 -> HttpResponseMessageUtils.updateSuccessResponse(Boolean.TRUE))
                .defaultIfEmpty(HttpResponseMessageUtils.updateFailedCommonResponse(ExceptionMessage.IDNOTEXSITPARAMETERIZATION));
    }

    @Override
    public Mono<ResponseInfo<Integer>> getAllArticleTotal() {
        return reactiveArticleSortingRepository.articleTotal().flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.SERVERERROREXCEPTION));
    }


    @Override
    public Mono<ResponseInfo<Integer>> getArticlesTotalByCategoryName(String categoryName) {
        return reactiveArticleSortingRepository.articleTotalByCategoryName(categoryName).flatMap(HttpResponseMessageUtils::querySuccessResponse)
                .defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse(ExceptionMessage.SERVERERROREXCEPTION));
    }

    @Override
    public Mono<ResponseInfo<Article>> getPageArticleByCategoryName(Pagination pagination, String categoryName) {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveArticleSortingRepository.getArticlesByCategoryName(categoryName).skip((long) (pagination.getPage() - 1) * pagination.getPageShowNumber()).take(pagination.getPageShowNumber()));

    }

    @Override
    public Mono<ResponseInfo<Article>> getArticlesByUserId(Integer userId) {
        return HttpResponseMessageUtils.querySuccessResponse(reactiveArticleSortingRepository.getArticlesByUserId(userId));
    }
}
