package dittoSM.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dittoSM.model.ImagePath;
import dittoSM.model.Post;
import dittoSM.model.UserAccount;
import dittoSM.service.ImageService;
import dittoSM.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
	
	PostController postController;
	
	@Mock
	PostService postService;
	
	@Mock
	ImageService imageService;
	
	@Mock
	HttpSession mockSession;
	
	@BeforeEach
	void setUp() {
		postController = new PostController(postService, imageService);
	}
	
	@Test
	void addLikeTest() {
		//Arrange
		List<UserAccount> mockUserArray = new ArrayList<>();
		mockUserArray.add(new UserAccount(1, "User1", "password", "user1@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null));
		mockUserArray.add(new UserAccount(3, "User2", "password2", "user2@ditto.com", "Squall", "Lionheart", new Timestamp(2000),"Leave me alone", "", null, null, null));
		List<ImagePath> mockImageList = new ArrayList<>();
		
		UserAccount mockUser = new UserAccount(4, "User3", "password3", "user3@ditto.com", "Noctis", "Callum", new Timestamp(3000),"Where did Prompto go?", "", null, null, null);
		
		when(mockSession.getAttribute("currentUser")).thenReturn(mockUser);
		
		Post mockPost = new Post(1, "post text", 3, new Timestamp(1000), mockUserArray.get(1),
				mockImageList, mockUserArray);
		
		//Act
		postController.addLike(mockSession, mockPost);
		
		//Assert
		verify(postService, times(1)).updatePost(mockPost, mockUser);
	}
	
	@Test
	void addLikeTestWithInvalidPost() {
		//Arrange
		UserAccount mockUser = new UserAccount(4, "User3", "password3", "user3@ditto.com", "Noctis", "Callum", new Timestamp(3000),"Where did Prompto go?", "", null, null, null);
		
		when(mockSession.getAttribute("currentUser")).thenReturn(null);
		
		Post mockPost = new Post();
		
		//Act
		postController.addLike(mockSession, mockPost);
		
		//Assert
		verify(postService, never()).updatePost(mockPost, mockUser);
	}
	
	@Test
	void getAllPostsTest() {
		//Arrange
		UserAccount mockUser = new UserAccount(4, "User3", "password3", "user3@ditto.com", "Noctis", "Callum", new Timestamp(3000),"Where did Prompto go?", "", null, null, null);
		List<UserAccount> mockUserArray = new ArrayList<>();
		mockUserArray.add(new UserAccount(1, "User1", "password", "user1@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null));
		mockUserArray.add(new UserAccount(3, "User2", "password2", "user2@ditto.com", "Squall", "Lionheart", new Timestamp(2000),"Leave me alone", "", null, null, null));
		List<ImagePath> mockImageList = new ArrayList<>();
		
		List<Post> mockPostArray = new ArrayList<>();
		mockPostArray.add(new Post(1, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		mockPostArray.add(new Post(2, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		mockPostArray.add(new Post(3, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		
		
		List<Post> expectedPostArray = new ArrayList<>();
		expectedPostArray.add(new Post(1, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		expectedPostArray.add(new Post(2, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		expectedPostArray.add(new Post(3, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		
		when(postService.findAllPosts()).thenReturn(mockPostArray);
		
		//Act
		List<Post> actualPostArray = postController.getAllUsers();
		
		//Assert
		verify(postService, times(1)).findAllPosts();
		assertEquals(expectedPostArray, actualPostArray);
		
	}
	
	@Test
	void getPostByIdTestWithValidId() {
		//Arrange
		int testId = 1;
		UserAccount mockUser = new UserAccount(testId, "User3", "password3", "user3@ditto.com", "Noctis", "Callum", new Timestamp(3000),"Where did Prompto go?", "", null, null, null);
		List<UserAccount> mockUserArray = new ArrayList<>();
		mockUserArray.add(new UserAccount(1, "User1", "password", "user1@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null));
		mockUserArray.add(new UserAccount(3, "User2", "password2", "user2@ditto.com", "Squall", "Lionheart", new Timestamp(2000),"Leave me alone", "", null, null, null));
		List<ImagePath> mockImageList = new ArrayList<>();
		
		List<Post> mockPostArray = new ArrayList<>();
		mockPostArray.add(new Post(1, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		mockPostArray.add(new Post(2, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		mockPostArray.add(new Post(3, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		
		
		List<Post> expectedPostArray = new ArrayList<>();
		expectedPostArray.add(new Post(1, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		expectedPostArray.add(new Post(2, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		expectedPostArray.add(new Post(3, "post text", 3, new Timestamp(1000), mockUser,
				mockImageList, mockUserArray));
		
		when(postService.findAllPosts()).thenReturn(mockPostArray);
		
		//Act
		List<Post> actualPostArray = postController.getAllUsers();
		
		//Assert
		verify(postService, times(1)).findAllPosts();
		assertEquals(expectedPostArray, actualPostArray);
		
	}

}
