package com.tianling.controller;

import com.tianling.entities.Article;
import com.tianling.entities.Pagination;
import com.tianling.entities.ResponseInfo;
import com.tianling.service.IArticleService;
import com.tianling.validator.article.ArticleAddValidator;
import com.tianling.validator.article.ArticleUpdateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 * 文章信息 前端控制器
 * </p>
 *
 * @author Tian Ling
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {
    @Resource
    IArticleService iArticleService;



    @GetMapping("/getAllArticle")
    public Mono<ResponseInfo<Article>> getAllArticle() {
        return iArticleService.getAllArticle();
    }

    @GetMapping("/getAllArticleTotal")
    public Mono<ResponseInfo<Integer>> getAllArticleTotal() {
        return iArticleService.getAllArticleTotal();
    }

    @GetMapping("/getPageArticle")
    public Mono<ResponseInfo<Article>> getPageArticle(Pagination pagination) {
        return iArticleService.getPagination(pagination);
    }

    @GetMapping("/getArticlesTotalByCategoryName/{categoryName}")
    public Mono<ResponseInfo<Integer>> getArticlesTotalByCategoryName(@PathVariable String categoryName) {
        return iArticleService.getArticlesTotalByCategoryName(categoryName);
    }

    @GetMapping("/getPageArticleByCategoryName/{categoryName}")
    public Mono<ResponseInfo<Article>> getPageArticleByCategoryName(Pagination pagination,@PathVariable String categoryName) {
        return iArticleService.getPageArticleByCategoryName(pagination,categoryName);
    }


    @GetMapping("/getCommandArticle/{limit}")
    public Mono<ResponseInfo<Article>> getCommandArticle(@PathVariable Integer limit) {
        return iArticleService.getCommandArticle(limit);
    }

    @GetMapping("/getArticleByUserName/{userName}")
    public Mono<ResponseInfo<Article>> getArticleByUserName(@PathVariable String userName) {
        return iArticleService.getArticleByUserName(userName);
    }
    @GetMapping("/getArticleById/{id}")
    public Mono<ResponseInfo<Article>> getArticleById(@PathVariable Integer id) {
        return iArticleService.getArticleByUserId(id);
    }

    @GetMapping("/updateArticleStar/{id}" )
    public Mono<ResponseInfo<Boolean>> setCommentStar(@PathVariable Integer id){
        return iArticleService.setArticleStar(id);
    }

    @PostMapping(value = "/addArticle",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Article>> insertArticle( @Validated(ArticleAddValidator.class) @RequestBody Article article) {
        return iArticleService.insertArticle(article);
    }

    @PostMapping(value = "/updateArticle",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Article>> updateArticle( @Validated(ArticleUpdateValidator.class) @RequestBody Article article) {
        return iArticleService.updateArticle(article);
    }

    @DeleteMapping(value = "/deleteArticleById/{id}")
    public Mono<ResponseInfo<Boolean>> deleteArticleById(@PathVariable Integer id) {
        return  iArticleService.deleteArticleById(id);
    }

    @DeleteMapping(value = "/deleteArticleByUserName/{userName}")
    public Mono<ResponseInfo<Boolean>> deleteArticleByUserName(@PathVariable String userName) {
        return  iArticleService.deleteArticleByUserName(userName);
    }


}
