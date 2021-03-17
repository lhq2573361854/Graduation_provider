package com.tianling.controller;


import com.tianling.entities.Comment;
import com.tianling.entities.Pagination;
import com.tianling.entities.ResponseInfo;
import com.tianling.service.ICommentService;
import com.tianling.validator.comment.CommentAddValidator;
import com.tianling.validator.comment.CommentUpdateValidator;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author Tian Ling
 * @since 2021-01-12
 */
@RestController
@RequestMapping("/comment")
public class CommentController{

    @Resource
    ICommentService commentService;

    @GetMapping("/getAllComment")
    public Mono<ResponseInfo<Comment>> getAllComment(){
        return commentService.getAllComment();
    }

    @GetMapping("/getCommentByUserName/{userName}")
    public Mono<ResponseInfo<Comment>> getCommentByUserName(@PathVariable String userName){
        return commentService.getCommentByUserName(userName);
    }

    @GetMapping("/getPageCommentByArticleId/{articleId}")
    public Mono<ResponseInfo<Comment>> getPageCommentByArticleId(Pagination pagination ,@PathVariable Integer articleId) {
        return commentService.getPaginationByArticleId(articleId,pagination);
    }

    @GetMapping("/getCommentTotalByArticleId/{articleId}")
    public Mono<ResponseInfo<Integer>> getCommentTotalByArticleId(@PathVariable Integer articleId) {
        return commentService.getCommentTotalByArticleId(articleId);
    }

    @GetMapping("/getCommentById/{id}")
    public Mono<ResponseInfo<Comment>> getCommentById(@PathVariable Integer id){
        return commentService.getCommentById(id);
    }

    @GetMapping("/getCommentByArticleId/{articleId}")
    public Mono<ResponseInfo<Comment>> getCommentByArticleId(@PathVariable Integer articleId){
        return commentService.getCommentByArticleId(articleId);
    }

    @PostMapping(value = "/addComment" , produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Comment>> addComment(@Validated(CommentAddValidator.class) @RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @PostMapping(value = "/updateComment" , produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseInfo<Comment>> updateComment(@Validated(CommentUpdateValidator.class) @RequestBody Comment comment){
        return commentService.updateComment(comment);
    }

    @DeleteMapping(value = "/deleteCommentById/{id}")
    public Mono<ResponseInfo<Boolean>> deleteComment(@PathVariable Integer id) {
        return  commentService.deleteCommentById(id);
    }

    @DeleteMapping(value = "/deleteCommentByUserName/{userName}")
    public Mono<ResponseInfo<Boolean>> deleteCommentByUserName(@PathVariable String userName) {
        return  commentService.deleteCommentByUserName(userName);
    }

    @GetMapping("/updateCommentStar/{id}")
    public Mono<ResponseInfo<Boolean>> setCommentStar(@PathVariable Integer id){
        return commentService.setCommentStar(id);
    }


}
