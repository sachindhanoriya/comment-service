package com.commentsService.commentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.commentsService.commentService.entities.Comment;
import com.commentsService.commentService.exceptions.CommentNotfoundException;
import com.commentsService.commentService.exceptions.UserNotfoundException;
import com.commentsService.commentService.repositories.ICommentDAO;
import com.commentsService.commentService.utils.Constants.EntityTypeEnum;
import com.commentsService.commentService.utils.Constants.ReactionTypeEnum;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource("/application.properties")
@Transactional
public class CommentDAOTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private ICommentDAO commentDAO;

    private Comment mockAddComment;

    @BeforeEach
    public void beforeEach() {
        this.mockAddComment = new Comment(
                1,
                1,
                1,
                "null",
                "",
                "",
                "2016-04-04 22:30:00");
        jdbc.execute("""
                INSERT IGNORE INTO `comment_service`.`users`
                (`user_id`, `username`, `password`, `full_name`)
                VALUES ('-1000', 'test0', 'test00', 'test0 test00');""");
        jdbc.execute("""
                INSERT IGNORE INTO `comment_service`.`users`
                (`user_id`, `username`, `password`, `full_name`)
                VALUES ('-1001', 'test1', 'test11', 'test1 test11');""");
        jdbc.execute("""
                INSERT IGNORE INTO `comment_service`.`users`
                (`user_id`, `username`, `password`, `full_name`)
                VALUES ('-1002', 'test2', 'test22', 'test2 test22');""");

        jdbc.execute("""
                INSERT IGNORE INTO `comment_service`.`posts`
                (`post_id`, `user_id`, `post_text`, `post_media_url`, `users_liked`, `users_disliked`, `timestamp`)
                VALUES ('-1000', '-1000', 'test', 'www.test.com', '-1001', '-1002', '1000-01-01 00:00:00');""");

        jdbc.execute("""
                INSERT IGNORE INTO `comment_service`.`comments`
                (`comment_id`, `post_id`, `user_id`, `comment_text`, `users_liked`, `users_disliked`, `timestamp`)
                VALUES ('-1000', '-1000', '-1001', 'test', '-1001', '-1002', '2018-08-10 12:10:20');""");
        jdbc.execute(
                """
                        INSERT IGNORE INTO `comment_service`.`comments`
                        (`comment_id`, `parent_comment_id`, `user_id`, `comment_text`, `users_liked`, `users_disliked`, `timestamp`)
                        VALUES ('-1001', '-1000', '-1002', 'test', '-1001', '-1002', '2018-08-10 12:10:20');""");

    }

    @AfterEach
    public void AfterEach() {

        jdbc.execute("DELETE FROM `comment_service`.`users` WHERE `user_id`='-1000';");
        jdbc.execute("DELETE FROM `comment_service`.`users` WHERE `user_id`='-1001';");
        jdbc.execute("DELETE FROM `comment_service`.`users` WHERE `user_id`='-1002';");

        jdbc.execute("DELETE FROM `comment_service`.`posts` WHERE `post_id`='-1000';");

        jdbc.execute("DELETE FROM `comment_service`.`comments` WHERE `comment_id`='-1001';");
        jdbc.execute("DELETE FROM `comment_service`.`comments` WHERE `comment_id`='-1000';");
    }

    @Test
    public void testGetAllComments() {
        assertNotNull(commentDAO.getAllComments(1, 1, 1));
        assertThrows(CommentNotfoundException.class, () -> commentDAO.getAllComments(1, 1, -1100));
    }

    @Test
    public void testGetReplies() {
        assertNotNull(commentDAO.getReplies(1, 1, 1));
        assertThrows(CommentNotfoundException.class, () -> commentDAO.getReplies(1, 1, -1100));
    }

    @Test
    public void testGetUserReactions() {
        assertNotNull(commentDAO.getUserReactions(-1000, EntityTypeEnum.COMMENT));
        assertThrows(CommentNotfoundException.class, () -> commentDAO.getUserReactions(-1100, EntityTypeEnum.COMMENT));
    }

    @Test
    public void testAddComment() {
        assertEquals(mockAddComment, commentDAO.addComment(mockAddComment, EntityTypeEnum.COMMENT));
    }

    @Test
    public void testAddReaction() {
        assertEquals(0, commentDAO.addReaction(-1000, -1000, ReactionTypeEnum.LIKE));
        assertEquals(0, commentDAO.addReaction(-1000, -1000, ReactionTypeEnum.DISLIKE));
        assertThrows(CommentNotfoundException.class,
                () -> commentDAO.addReaction(-1100, -1000, ReactionTypeEnum.DISLIKE));
        assertThrows(UserNotfoundException.class, () -> commentDAO.addReaction(-1000, -1100, ReactionTypeEnum.DISLIKE));
    }

    @Test
    public void testUpdateComment() {
        mockAddComment.setCommentId(-1000);
        assertNotNull(commentDAO.updateComment(mockAddComment));
        mockAddComment.setCommentId(-1100);
        assertThrows(CommentNotfoundException.class, () -> commentDAO.updateComment(mockAddComment));
    }

    @Test
    public void testDeleteComment(){
        commentDAO.addComment(mockAddComment, EntityTypeEnum.COMMENT);
        assertEquals(0, commentDAO.deleteComment(-1000));
    }

    @Test
    public void testRemoveReaction(){
        commentDAO.addReaction(-1000, -1000, ReactionTypeEnum.LIKE);
        assertEquals(0, commentDAO.removeReaction(-1000, -1000, ReactionTypeEnum.DISLIKE));
        assertThrows(UserNotfoundException.class, () -> commentDAO.removeReaction(-1000, -1100, ReactionTypeEnum.DISLIKE));
        assertThrows(CommentNotfoundException.class, () -> commentDAO.removeReaction(-1100, -1000, ReactionTypeEnum.DISLIKE));
    }

}
