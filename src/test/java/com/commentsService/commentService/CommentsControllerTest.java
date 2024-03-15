package com.commentsService.commentService;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.commentsService.commentService.entities.Comment;
import com.commentsService.commentService.repositories.CommentDAO;
import com.commentsService.commentService.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentsControllerTest {

    private static final MediaType applicationType_json_utf8 = MediaType.APPLICATION_JSON;

    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    @MockBean
    private CommentService mockCommentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private Comment comment;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeAll
    public static void beforeAll(){
        request = new MockHttpServletRequest();
        request.setParameter("", "");
    }
    @BeforeEach
    public void beforeEach() {
    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    public void testGetAllComments() {

    }

}
