package com.commentsService.commentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.commentsService.commentService.core.SocialMedia;
import com.commentsService.commentService.entities.Comment;
import com.commentsService.commentService.services.CommentService;
import com.commentsService.commentService.utils.Constants.EntityTypeEnum;
import com.commentsService.commentService.utils.Constants.ReactionTypeEnum;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;

@SpringBootTest
class CommentServiceTest {

	// @Mock ---(1)-
	@MockBean
	private SocialMedia mockSocialMedia;

	// @Mock ---(1)-
	@MockBean
	private Page<Comment> mockPage;
	private Map<String, List<String>> mockUserReactions;
	private Comment mockComment, mockReturnComment;

	// @InjectMocks ---(1)-
	@Autowired
	private CommentService commentService;

	@SuppressWarnings("null")
	@BeforeEach
	public void BeforeEach() {
		this.mockPage = new Page<Comment>() {

			@Override
			public int getNumber() {
				return 1;
			}

			@Override
			public int getSize() {
				return 1;
			}

			@Override
			public int getNumberOfElements() {
				return 1;
			}

			@Override
			public List<Comment> getContent() {
				return null;
			}

			@Override
			public boolean hasContent() {
				return true;
			}

			@Override
			public Sort getSort() {
				return null;
			}

			@Override
			public boolean isFirst() {
				return true;
			}

			@Override
			public boolean isLast() {
				return true;
			}

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public boolean hasPrevious() {
				return true;
			}

			@Override
			public Pageable nextPageable() {
				return null;
			}

			@Override
			public Pageable previousPageable() {
				return null;
			}

			@Override
			public Iterator<Comment> iterator() {
				return null;
			}

			@Override
			public int getTotalPages() {
				return 1;
			}

			@Override
			public long getTotalElements() {
				return 1;
			}

			@Override
			public <U> Page<U> map(Function<? super Comment, ? extends U> converter) {
				return null;
			}

		};
		this.mockUserReactions = new HashMap<>();
		this.mockComment = new Comment(
				1,
				1,
				1,
				"null",
				"",
				"",
				"2016-04-04 22:30:00");
		this.mockReturnComment = new Comment(
				1,
				1,
				1,
				"null",
				"",
				"",
				"2016-04-04 22:30:00");
		mockReturnComment.setCommentId(1);
	}

	@Test
	void testGetComments() {
		when(mockSocialMedia.getComments(1, 1, 1)).thenReturn(mockPage);
		assertEquals(mockPage, commentService.getComments(1, 1, 1));
		verify(mockSocialMedia, times(1)).getComments(1, 1, 1);
	}

	@Test
	void testGetReplies() {
		when(mockSocialMedia.getReplies(1, 1, 1)).thenReturn(mockPage);
		assertEquals(mockPage, commentService.getReplies(1, 1, 1));
		verify(mockSocialMedia, times(1)).getReplies(1, 1, 1);
	}

	@Test
	void testGetReactionUsers() {
		when(mockSocialMedia.getReactionUsers(1, EntityTypeEnum.COMMENT)).thenReturn(mockUserReactions);
		assertEquals(mockUserReactions, commentService.getReactionUsers(1, EntityTypeEnum.COMMENT));
		verify(mockSocialMedia, times(1)).getReactionUsers(1, EntityTypeEnum.COMMENT);
	}

	@Test
	void testAddComment() {
		when(mockSocialMedia.addComment(mockComment, EntityTypeEnum.COMMENT)).thenReturn(mockReturnComment);
		assertEquals(mockReturnComment, commentService.addComment(mockComment, EntityTypeEnum.COMMENT));
		verify(mockSocialMedia, times(1)).addComment(mockComment, EntityTypeEnum.COMMENT);
	}

	@Test
	void testAddReaction() {
		when(mockSocialMedia.addReaction(1, 1, ReactionTypeEnum.LIKE)).thenReturn(1);
		assertEquals(1, commentService.addReaction(1, 1, ReactionTypeEnum.LIKE));
		verify(mockSocialMedia, times(1)).addReaction(1, 1, ReactionTypeEnum.LIKE);
	}

	@Test
	void testUpdateComment() {
		when(mockSocialMedia.updateComment(mockReturnComment)).thenReturn(mockReturnComment);
		assertEquals(mockReturnComment, commentService.updateComment(mockReturnComment));
		verify(mockSocialMedia, times(1)).updateComment(mockReturnComment);
	}

	@Test
	void testDeleteComment() {
		when(mockSocialMedia.deleteComment(1)).thenReturn(1);
		assertEquals(1, commentService.deleteComment(1));
		verify(mockSocialMedia, times(1)).deleteComment(1);
	}

	@Test
	void testRemoveReaction() {
		when(mockSocialMedia.removeReaction(1, 1, ReactionTypeEnum.LIKE)).thenReturn(1);
		assertEquals(1, commentService.removeReaction(1, 1, ReactionTypeEnum.LIKE));
		verify(mockSocialMedia, times(1)).removeReaction(1, 1, ReactionTypeEnum.LIKE);
	}
}
