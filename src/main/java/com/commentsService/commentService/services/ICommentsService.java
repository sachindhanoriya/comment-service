package com.commentsService.commentService.services;

import java.util.*;

import org.springframework.data.domain.Page;

import com.commentsService.commentService.entities.Comment;
import com.commentsService.commentService.utils.Constants.*;

public interface ICommentsService {
    public Page<Comment> getComments(Integer page, Integer size, Integer postId);

    public Page<Comment> getReplies(Integer page, Integer size, Integer commentId);

    public Map<String, List<String>> getReactionUsers(Integer commentId, EntityTypeEnum type);

    public Comment addComment(Comment comment, EntityTypeEnum type);

    public Integer addReaction(Integer commentId, Integer userId, ReactionTypeEnum type);

    public Comment updateComment(Comment comment);

    public Integer deleteComment(Integer commentId);

    public Integer removeReaction(Integer commentId, Integer userId, ReactionTypeEnum like);

    public Page<Comment> testFindAll();
}
