package com.commentsService.commentService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commentsService.commentService.entities.Comment;
import com.commentsService.commentService.services.ICommentsService;
import com.commentsService.commentService.utils.Constants.EntityTypeEnum;
import com.commentsService.commentService.utils.Constants.ReactionTypeEnum;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.*;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class CommentsController {

    private ICommentsService commentService;

    public CommentsController() {
    }

    @Autowired
    public CommentsController(ICommentsService commentService) {
        this.commentService = commentService;
    }

    @PostConstruct
    public void initializeCommentsRESTController() {
        System.out.println("REST Controller Initialized!");
    }

    @PreDestroy
    public void finalizeCommentsRESTController() {
        System.out.println("REST Controller Stopped!");
    }

    @GetMapping("/")
    public ResponseEntity<Page<Comment>> hello() {
        return new ResponseEntity<>(commentService.testFindAll(), HttpStatus.OK);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<Page<Comment>> getComments(@PathVariable Integer postId, @RequestParam Integer page,
            @RequestParam Integer size) {
        return new ResponseEntity<>(commentService.getComments(page, size, postId), HttpStatus.OK);
    }

    @GetMapping("/comment/replies/{commentId}")
    public ResponseEntity<Page<Comment>> getReplies(@PathVariable Integer commentId, @RequestParam Integer page,
            @RequestParam Integer size) {
        return new ResponseEntity<>(commentService.getReplies(page, size, commentId), HttpStatus.OK);
    }

    @GetMapping("/comment/users/likesDislikes/{commentId}")
    public ResponseEntity<Map<String, List<String>>> getReactionUsers(@PathVariable Integer commentId) {
        return new ResponseEntity<>(commentService.getReactionUsers(commentId, EntityTypeEnum.COMMENT), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.addComment(comment, EntityTypeEnum.COMMENT), HttpStatus.CREATED);
    }

    @PatchMapping("/comment/like/{commentId}")
    public ResponseEntity<Integer> addLike(@PathVariable Integer commentId, @RequestParam Integer userId) {
        return new ResponseEntity<>(commentService.addReaction(commentId, userId, ReactionTypeEnum.LIKE),
                HttpStatus.CREATED);
    }

    @PatchMapping("/comment/dislike/{commentId}")
    public ResponseEntity<Integer> addDislike(@PathVariable Integer commentId, @RequestParam Integer userId) {
        return new ResponseEntity<>(commentService.addReaction(commentId, userId, ReactionTypeEnum.DISLIKE),
                HttpStatus.CREATED);
    }

    @PatchMapping("/comment")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.updateComment(comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Integer> deleteComment(@PathVariable Integer commentId) {
        return new ResponseEntity<>(commentService.deleteComment(commentId), HttpStatus.OK);
    }

    @DeleteMapping("/comment/like/{commentId}")
    public ResponseEntity<Integer> removeLike(@PathVariable Integer commentId, @RequestParam Integer userId) {
        return new ResponseEntity<>(commentService.removeReaction(commentId, userId, ReactionTypeEnum.LIKE),
                HttpStatus.OK);
    }

    @DeleteMapping("/comment/dislike/{commentId}")
    public ResponseEntity<Integer> removeDislike(@PathVariable Integer commentId, @RequestParam Integer userId) {
        return new ResponseEntity<>(commentService.removeReaction(commentId, userId, ReactionTypeEnum.DISLIKE),
                HttpStatus.OK);
    }

}
