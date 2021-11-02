package dittoSM.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dittoSM.EmailService;
import dittoSM.model.UserAccount;
import dittoSM.service.ImageService;
import dittoSM.service.UserAccountService;


@ExtendWith(MockitoExtension.class)
public class UserAccountControllerTest {
	
	UserAccountController userAccountController;
	
	@Mock
	UserAccountService userAccountService;
	
	@Mock
	EmailService emailService;
	
	@Mock
	ImageService imageService;
	
	@BeforeEach
	void setUp() {
		userAccountController = new UserAccountController(userAccountService, emailService, imageService);
	}
	
	@Test
	void getAllUsers() {
		//Arrange
		List<UserAccount> mockUserArray = new ArrayList<>();
		mockUserArray.add(new UserAccount(1, "User1", "password", "user1@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null));
		mockUserArray.add(new UserAccount(3, "User2", "password2", "user2@ditto.com", "Squall", "Lionheart", new Timestamp(2000),"Leave me alone", "", null, null, null));
		mockUserArray.add(new UserAccount(4, "User3", "password3", "user3@ditto.com", "Noctis", "Callum", new Timestamp(3000),"Where did Prompto go?", "", null, null, null));
		
		
		List<UserAccount> expectedUserArray = new ArrayList<>();
		expectedUserArray.add(new UserAccount(1, "User1", "password", "user1@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null));
		expectedUserArray.add(new UserAccount(3, "User2", "password2", "user2@ditto.com", "Squall", "Lionheart", new Timestamp(2000),"Leave me alone", "", null, null, null));
		expectedUserArray.add(new UserAccount(4, "User3", "password3", "user3@ditto.com", "Noctis", "Callum", new Timestamp(3000),"Where did Prompto go?", "", null, null, null));
		
		
		when(userAccountService.getAllUsers()).thenReturn(mockUserArray);
		
		//Act
		List<UserAccount> actualUserArray = userAccountController.getAllUsers();
		
		//Assert
		verify(userAccountService, times(1)).getAllUsers();
		assertEquals(expectedUserArray, actualUserArray);
		
	}
}
