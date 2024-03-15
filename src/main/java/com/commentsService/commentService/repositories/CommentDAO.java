package com.commentsService.commentService.repositories;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.commentsService.commentService.entities.Comment;
import com.commentsService.commentService.entities.User;
import com.commentsService.commentService.exceptions.CommentNotfoundException;
import com.commentsService.commentService.exceptions.UserNotfoundException;
import com.commentsService.commentService.repositories.paginationUtil.PaginationHelperRepository;
import com.commentsService.commentService.utils.Constants.EntityTypeEnum;
import com.commentsService.commentService.utils.Constants.ReactionTypeEnum;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class CommentDAO implements ICommentDAO {
    // public class CommentDAO implements ICommentDAO {

    private EntityManager entityManager;
    private PaginationHelperRepository paginationHelperRepository;

    public CommentDAO() {
    }

    @Autowired
    public CommentDAO(EntityManager entityManager, PaginationHelperRepository paginationHelperRepository) {
        this.entityManager = entityManager;
        this.paginationHelperRepository = paginationHelperRepository;
    }

    @Override
    public Page<Comment> getAllComments(Integer page, Integer size, Integer postId) {
        // TypedQuery<Comment> query = entityManager
        // .createQuery("FROM Comment WHERE postId=:param", Comment.class)
        // .setParameter("param", postId);
        // return query.getResultList();

        Pageable paging = PageRequest.of(page, size, Sort.by("timestamp").ascending());
        Page<Comment> commentsPage = paginationHelperRepository.findAllByPostId(postId, paging);
        if (!commentsPage.hasContent())
            throw new CommentNotfoundException("No comments found for PostID:" + postId);
        return commentsPage;
    }

    @Override
    public Page<Comment> getReplies(Integer page, Integer size, Integer commentId) {
        // String jql = "FROM Comment WHERE parentCommentId=:param";
        // TypedQuery<Comment> query = entityManager
        // .createQuery(jql, Comment.class)
        // .setParameter("param", commentId);
        // return query.getResultList();

        Pageable paging = PageRequest.of(page, size, Sort.by("timestamp").ascending());
        Page<Comment> commentsPage = paginationHelperRepository.findAllByParentCommentId(commentId, paging);
        if (!commentsPage.hasContent())
            throw new CommentNotfoundException("No comments found for CommentID:" + commentId);
        return commentsPage;
    }

    @Override
    public Map<String, List<String>> getUserReactions(Integer commentId, EntityTypeEnum type) {
        Comment comment = entityManager.find(Comment.class, commentId);
        if (comment == null)
            throw new CommentNotfoundException("No comments found for CommentID:" + commentId);
        Map<String, List<String>> res = new HashMap<>();
        List<String> usersLiked = null;
        List<String> usersDisliked = null;

        if (comment.getUsersLiked() != null && comment.getUsersLiked().length() > 0) {
            List<Integer> idsLiked = Arrays.stream(comment.getUsersLiked().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            TypedQuery<User> userLikedJQL = entityManager
                    .createQuery("FROM User WHERE userId IN :ids", User.class)
                    .setParameter("ids", idsLiked);
            usersLiked = userLikedJQL.getResultList()
                    .stream()
                    .map(user -> user.getFullName())
                    .collect(Collectors.toList());
        }

        if (comment.getUsersDisliked() != null && comment.getUsersDisliked().length() > 0) {
            List<Integer> idsDisliked = Arrays.stream(comment.getUsersDisliked().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            TypedQuery<User> userDislikedJQL = entityManager
                    .createQuery("FROM User WHERE userId IN :ids", User.class)
                    .setParameter("ids", idsDisliked);
            usersDisliked = userDislikedJQL.getResultList()
                    .stream()
                    .map(user -> user.getFullName())
                    .collect(Collectors.toList());
        }

        // get user's fullname from user table
        res.put("likes", usersLiked);
        res.put("dislikes", usersDisliked);

        return res;
    }

    @Override
    public Comment addComment(Comment comment, EntityTypeEnum type) {
        try {
            entityManager.persist(comment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            // throw new CommentNotfoundException(
            //         "No parent found with id:" + (comment.getPostId() != null ? "PostID:"+comment.getPostId()
            //                 : "ParentCommentID:"+comment.getParentCommentId()));
            throw e;
        }
        return comment;
    }

    @Override
    public Integer addReaction(Integer commentId, Integer userId, ReactionTypeEnum type) {
        if (entityManager.find(User.class, userId) == null)
            throw new UserNotfoundException("No User found with UserId:" + userId);
        Comment comment = entityManager.find(Comment.class, commentId);
        if (comment == null)
            throw new CommentNotfoundException("No comment found with `commentId`:" + commentId);
        comment.addUserReaction(userId, type);
        entityManager.merge(comment);
        return 0;
    }

    @Override
    public Comment updateComment(Comment comment) {
        if (entityManager.find(Comment.class, comment.getCommentId()) == null)
            throw new CommentNotfoundException("No comment found with CommentId:" + comment.getCommentId());
        return entityManager.merge(comment);
    }

    @Override
    public Integer deleteComment(Integer commentId) {
        Comment comment = entityManager.find(Comment.class, commentId);
        if (comment == null)
            throw new CommentNotfoundException("No comment found with `commentId`:" + commentId);
        entityManager.remove(comment);
        return 0;
    }

    @Override
    public Integer removeReaction(Integer commentId, Integer userId, ReactionTypeEnum type) {
        if (entityManager.find(User.class, userId) == null)
            throw new UserNotfoundException("No User found with UserId:" + userId);
        Comment comment = entityManager.find(Comment.class, commentId);
        if (comment == null)
            throw new CommentNotfoundException("No comment found with `commentId`:" + commentId);
        comment.removeUserReaction(userId, type);
        entityManager.persist(comment);
        return 0;
    }

    @Override
    public Page<Comment> testFindAll() {
        Pageable paging = PageRequest.of(0, 5, Sort.by("commentId").ascending());
        Page<Comment> commentsPage = paginationHelperRepository.findAllByParentCommentIdJPQLPositional(3, paging);
        // Page<Comment> commentsPage =
        // paginationHelperRepository.findAllByParentCommentIdJPQLNamed(3, paging);

        // Pageable paging = PageRequest.of(0, 5, Sort.by("comment_id").ascending());
        // Page<Comment> commentsPage =
        // paginationHelperRepository.findAllByParentCommentIdNativePositional(3,
        // paging);
        // Page<Comment> commentsPage =
        // paginationHelperRepository.findAllByParentCommentIdNativeNamed(3, paging);

        return commentsPage;
    }

}
