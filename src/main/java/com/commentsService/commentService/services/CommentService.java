package com.commentsService.commentService.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.commentsService.commentService.core.SocialMedia;
import com.commentsService.commentService.entities.Comment;
import com.commentsService.commentService.utils.Constants.*;

import jakarta.transaction.Transactional;

@Service
public class CommentService implements ICommentsService {

    private SocialMedia sys;

    public CommentService() {
    }

    @Autowired
    public CommentService(SocialMedia sys) {
        this.sys = sys;
    }

    @Override
    public Page<Comment> getComments(Integer page, Integer size, Integer postId) {
        // TODO Auto-generated method stub
        return sys.getComments(page, size, postId);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getComments'");
    }

    @Override
    public Page<Comment> getReplies(Integer page, Integer size, Integer commentId) {
        return sys.getReplies(page, size, commentId);
    }

    @Override
    public Map<String, List<String>> getReactionUsers(Integer commentId, EntityTypeEnum type) {
        return sys.getReactionUsers(commentId, type);
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment, EntityTypeEnum type) {
        return sys.addComment(comment, type);
    }

    @Override
    @Transactional
    public Integer addReaction(Integer commentId, Integer userId, ReactionTypeEnum type) {
        return sys.addReaction(commentId, userId, type);
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) {
        return sys.updateComment(comment);
    }

    @Override
    @Transactional
    public Integer deleteComment(Integer commentId) {
        return sys.deleteComment(commentId);
    }

    @Override
    @Transactional
    public Integer removeReaction(Integer commentId, Integer userId, ReactionTypeEnum type) {
        return sys.removeReaction(commentId, userId, type);
    }

    @Override
    public Page<Comment> testFindAll() {
        return sys.testFindAll();
    }

}
