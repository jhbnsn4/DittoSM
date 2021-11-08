package dittoSM.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
		mockUserArray.add(new UserAccount(4, "User3", "password3", "user3@ditto.com", "Noctis", "Callum", new Timestamp(3000),"Where did Prompto go?", "", null, null, null));
		
		List<ImagePath> mockImageList = new ArrayList<>();
		
		mockSession.setAttribute("currentUser", mockUserArray.get(0));
		
		Post mockPost = new Post(1, "post text", 3, new Timestamp(1000), mockUserArray.get(1),
				mockImageList, mockUserArray);
		
		
		//Act
		
		
		//Assert
		verify(postService, times(1)).updatePost(mockPost, mockUserArray.get(0));
		
	}

}
