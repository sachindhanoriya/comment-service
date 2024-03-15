package com.commentsService.commentService.repositories.paginationUtil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commentsService.commentService.entities.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaginationHelperRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.postId=?1")
    Page<Comment> findAllByPostId(Integer postId, Pageable page);

    @Query("SELECT c FROM Comment c WHERE c.parentCommentId=?1")
    Page<Comment> findAllByParentCommentId(Integer commentId, Pageable page);

    /////////////////////////////////////////

    @Query("SELECT c FROM Comment c WHERE c.parentCommentId=?1")
    Page<Comment> findAllByParentCommentIdJPQLPositional(Integer commentId, Pageable page);

    @Query("SELECT c FROM Comment c WHERE parentCommentId=:id")
    Page<Comment> findAllByParentCommentIdJPQLNamed(@Param("id") Integer commentId, Pageable page);

    @Query(value = "SELECT * FROM comments as c WHERE c.parent_comment_id=?1", nativeQuery = true)
    Page<Comment> findAllByParentCommentIdNativePositional(Integer commentId, Pageable page);

    @Query(value = "SELECT * FROM comments as c WHERE c.parent_comment_id=:id", nativeQuery = true)
    Page<Comment> findAllByParentCommentIdNativeNamed(@Param("id") Integer commentId, Pageable page);
}