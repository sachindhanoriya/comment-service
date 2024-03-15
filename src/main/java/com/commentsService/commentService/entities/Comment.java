package com.commentsService.commentService.entities;

import com.commentsService.commentService.utils.Constants.ReactionTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    Integer commentId;

    @Column(name = "parent_comment_id")
    Integer parentCommentId;

    @Column(name = "post_id")
    Integer postId;

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "comment_text")
    String commentText;

    @Column(name = "users_liked")
    String usersLiked;

    @Column(name = "users_disliked")
    String usersDisliked;

    @Column(name = "timestamp")
    String timestamp;

    public Comment() {
    }

    public Comment(Integer userId, Integer parentCommentId, Integer postId, String commentText, String usersLiked,
            String usersDisliked, String timestamp) {
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.postId = postId;
        this.commentText = commentText;
        this.usersLiked = usersLiked;
        this.usersDisliked = usersDisliked;
        this.timestamp = timestamp;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUsersLiked() {
        return usersLiked;
    }

    public void setUsersLiked(String usersLiked) {
        this.usersLiked = usersLiked;
    }

    public String getUsersDisliked() {
        return usersDisliked;
    }

    public void setUsersDisliked(String usersDisliked) {
        this.usersDisliked = usersDisliked;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void addUserReaction(Integer id, ReactionTypeEnum type) {
        switch (type) {
            case LIKE -> {
                if (this.usersLiked == null)
                    usersLiked = "";
                if (!usersLiked.contains(id.toString()))
                    this.usersLiked += id + ",";
                if (this.usersDisliked != null)
                    this.usersDisliked = this.usersDisliked.replace(id + ",", "");
            }
            case DISLIKE -> {
                if (this.usersDisliked == null)
                    usersDisliked = "";
                if (!usersDisliked.contains(id.toString()))
                    this.usersDisliked += id + ",";
                if (this.usersLiked != null)
                    this.usersLiked = this.usersLiked.replace(id + ",", "");

            }
        }
    }

    public void removeUserReaction(Integer id, ReactionTypeEnum type) {
        switch (type) {
            case LIKE -> {
                if (usersLiked != null && usersLiked.contains(id.toString()))
                    this.usersLiked = this.usersLiked.replace(id + ",", "");
            }
            case DISLIKE -> {
                if (usersDisliked != null && usersDisliked.contains(id.toString()))
                    this.usersDisliked = this.usersDisliked.replace(id + ",", "");
            }
        }
    }

    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", parentCommentId=" + parentCommentId + ", postId=" + postId
                + ", userId=" + userId + ", commentText=" + commentText + ", usersLiked=" + usersLiked
                + ", usersDisliked=" + usersDisliked + ", timestamp=" + timestamp + "]";
    }

}
