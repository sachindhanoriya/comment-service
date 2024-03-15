package com.commentsService.commentService.core;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.commentsService.commentService.entities.Comment;
import com.commentsService.commentService.repositories.ICommentDAO;
import com.commentsService.commentService.utils.Constants.EntityTypeEnum;
import com.commentsService.commentService.utils.Constants.ReactionTypeEnum;

@Component
public class SocialMedia {

    private ICommentDAO commentDAO;

    public SocialMedia(ICommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public Page<Comment> getComments(Integer page, Integer size, Integer postId) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getComments'");

        return commentDAO.getAllComments(page, size, postId);
    }

    public Page<Comment> getReplies(Integer page, Integer size, Integer commentId) {
        return commentDAO.getReplies(page, size, commentId);
    }

    public Map<String, List<String>> getReactionUsers(Integer id, EntityTypeEnum type) {
        return commentDAO.getUserReactions(id, type);
    }

    public Comment addComment(Comment comment, EntityTypeEnum type) {
        return commentDAO.addComment(comment, type);
    }

    public Integer addReaction(Integer commentId, Integer userId, ReactionTypeEnum type) {
        return commentDAO.addReaction(commentId, userId, type);
    }

    public Comment updateComment(Comment comment) {
        return commentDAO.updateComment(comment);
    }

    public Integer deleteComment(Integer commentId) {
        return commentDAO.deleteComment(commentId);
    }

    public Integer removeReaction(Integer commentId, Integer userId, ReactionTypeEnum type) {
        return commentDAO.removeReaction(commentId, userId, type);
    }

    public Page<Comment> testFindAll() {
        return commentDAO.testFindAll();
    }

}
