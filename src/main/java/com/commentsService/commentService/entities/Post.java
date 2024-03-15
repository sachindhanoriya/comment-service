package com.commentsService.commentService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    Integer postId;

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "post_text")
    String postText;

    @Column(name = "post_media_url")
    String postMediaURL;

    @Column(name = "users_liked")
    String usersLiked;

    @Column(name = "users_disliked")
    String usersDisliked;

    @Column(name = "timestamp")
    String timestamp;

    public Post() {
    }

    public Post(Integer userId, String postText, String postMediaURL, String usersLiked, String usersDisliked,
            String timestamp) {
        this.userId = userId;
        this.postText = postText;
        this.postMediaURL = postMediaURL;
        this.usersLiked = usersLiked;
        this.usersDisliked = usersDisliked;
        this.timestamp = timestamp;
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

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostMediaURL() {
        return postMediaURL;
    }

    public void setPostMediaURL(String postMediaURL) {
        this.postMediaURL = postMediaURL;
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

    @Override
    public String toString() {
        return "Post [postId=" + postId + ", userId=" + userId + ", postText=" + postText + ", postMediaURL="
                + postMediaURL + ", usersLiked=" + usersLiked + ", usersDisliked=" + usersDisliked + ", timestamp="
                + timestamp + "]";
    }

}
