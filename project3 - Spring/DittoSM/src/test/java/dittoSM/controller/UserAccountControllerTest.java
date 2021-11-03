package dittoSM.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import dittoSM.EmailService;
import dittoSM.model.MyCustomMessage;
import dittoSM.model.UserAccount;
import dittoSM.model.UserAccountPackaged;
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
	
	@Mock
	HttpSession mockSession;
	
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
	
	@Test
	void getUserByIdTestWithIntendedInput() {
		//Arrange
		int targetId = 2;
		UserAccount mockUser = new UserAccount(targetId,"User1", "password", "user1@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		UserAccountPackaged expectedUser = new UserAccountPackaged(targetId, "Cloud", "Strife", "Whatever...", new Timestamp(1000),"");
		when (userAccountService.getUserById(targetId)).thenReturn(mockUser);
		
		//Act
		UserAccountPackaged actualUser = userAccountController.getUserById(targetId);
		
		//Assert
		verify(userAccountService, times(1)).getUserById(targetId); //Object instead of target id
		assertEquals(expectedUser, actualUser);
		
	}
	
	@Test
	void getUserByIdTestWithEmptyInput() {
		//Arrange
		int targetId = -1;
		
		//Act
		UserAccountPackaged actualUser = userAccountController.getUserById(targetId);
		
		//Assert
		verify(userAccountService, times(1)).getUserById(targetId);
		assertNull(actualUser);
	}
	
	@Test
	void getUserByyUsernameTestWithOnlyUsername() {
		//Arrange
		String targetUsername = "Hero";
		String targetEmail = null;
		UserAccount mockUser = new UserAccount(1, targetUsername, "password", "user1@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		UserAccount expectedUser = new UserAccount(1, targetUsername, "password", "user1@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		when(userAccountService.getUserByUsername(targetUsername, targetEmail)).thenReturn(mockUser);
		
		//Act
		UserAccount actualUser = userAccountController.getUserByUsername(targetUsername, targetEmail);
		
		//Assert
		verify(userAccountService, times(1)).getUserByUsername(targetUsername, targetEmail);
		assertEquals(expectedUser, actualUser);
		
	}
	
	@Test
	void getUserByyUsernameTestWithOnlyEmail() {
		//Arrange
		String targetUsername = null;
		String targetEmail = "user1@ditto.com";
		UserAccount mockUser = new UserAccount(1, "Hero", "password", targetEmail, "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		UserAccount expectedUser = new UserAccount(1, "Hero", "password", targetEmail, "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		when(userAccountService.getUserByUsername(targetUsername, targetEmail)).thenReturn(mockUser);
		
		//Act
		UserAccount actualUser = userAccountController.getUserByUsername(targetUsername, targetEmail);
		
		//Assert
		verify(userAccountService, times(1)).getUserByUsername(targetUsername, targetEmail);
		assertEquals(expectedUser, actualUser);
		
	}
	
	@Test
	void getUserByyUsernameTestWithNull() {
		//Arrange
		String targetUsername = null;
		String targetEmail = null;
		
		//Act
		UserAccount actualUser = userAccountController.getUserByUsername(targetUsername, targetEmail);
		
		//Assert
		verify(userAccountService, times(1)).getUserByUsername(targetUsername, targetEmail);
		assertNull(actualUser);
		
	}
	
	@Test
	void addUserTestWithExpectedUser() {
		//Arrange
		UserAccount mockUser = new UserAccount(1, "Hero", "password", "omnislash@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		
		//Act
		userAccountController.addUser(mockUser);
				
		//Assert
		verify(userAccountService, times(1)).addAccount(mockUser);
	}
	
	@Test
	void addUserTestWithNullUser() {
		//Arrange
		UserAccount mockUser = new UserAccount();
		
		//Act
		userAccountController.addUser(mockUser);
				
		//Assert
		verify(userAccountService, times(1)).addAccount(mockUser);
	}
	
	@Test
	void updateUserTestWithValidUser() {
		//Arrange
		int targetId = 1;
		UserAccountPackaged mockUserPackaged = new UserAccountPackaged(targetId, "Cloud", "Strife", "Whatever...", new Timestamp(1000),"");
		UserAccount mockUser = new UserAccount(targetId, "Hero", "password", "omnislash@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		when(userAccountService.getUserById(targetId)).thenReturn(mockUser);
		
		//Act
		userAccountController.updateUser(mockSession, mockUserPackaged);
		
		//Assert
		verify(userAccountService, times(1)).updateAccount(mockUser);
		
	}
	
	@Test
	void updateUserTestWithInvalidUser() {
		//Arrange
		int targetId = 2;
		UserAccountPackaged mockUserPackaged = new UserAccountPackaged(targetId, "Cloud", "Strife", "Whatever...", new Timestamp(1000),"");
		UserAccount mockUser = new UserAccount(1, "Hero", "password", "omnislash@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		when(userAccountService.getUserById(targetId)).thenReturn(null);
		
		//Act
		userAccountController.updateUser(mockSession, mockUserPackaged);
		
		//Assert
		verify(userAccountService, never()).updateAccount(mockUser);
	}
	
	@Test
	void updateUserPasswordTestWithValidInput() {
		//Arrange
		UserAccount mockUser = new UserAccount(1, "Hero", "password", "omnislash@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		MyCustomMessage expectedMessage = new MyCustomMessage("Password Succefully Updated","");
		when(userAccountService.getUserById(mockUser.getUserId())).thenReturn(mockUser);
		
		//Act
		MyCustomMessage actualMessage = userAccountController.updateUserPassword(mockSession, mockUser);
		
		//Assert
		verify(userAccountService, times(1)).updateAccount(mockUser);
		assertEquals(actualMessage, expectedMessage);
	}
	
	@Test
	void updateUserPasswordTestWithInvalidInput() {
		//Arrange
		UserAccount mockUser = new UserAccount();
		MyCustomMessage expectedMessage = new MyCustomMessage("Invalid Account","");
		when(userAccountService.getUserById(mockUser.getUserId())).thenReturn(null);
		
		//Act
		MyCustomMessage actualMessage = userAccountController.updateUserPassword(mockSession, mockUser);
		
		//Assert
		verify(userAccountService, never()).updateAccount(mockUser);
		assertEquals(actualMessage, expectedMessage);
	}
	
	@Test
	void resetPasswordTestWithValidInput() {
		//Arrange
		String mockEmail = "omnislash@ditto.com";
		String mockUrl = System.getenv("DITTO_ANGULAR_IP_AND_PORT")+"/#/reset/";
		UserAccount mockUser = new UserAccount(1, "Hero", "password", mockEmail, "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		String mockMessage = "Click the following link to reset your password: "+mockUrl+mockUser.getUserId();
		MyCustomMessage expectedMessage = new MyCustomMessage("Message has been sent to:", mockEmail);
		when(userAccountService.getUserByUsername("",mockEmail)).thenReturn(mockUser);
		
		//Act
		MyCustomMessage actualMessage = userAccountController.resetPassword(mockSession, mockEmail);
		
		//Assert
		verify(emailService, times(1)).sendMail(mockEmail, "Password Reset", mockMessage);
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void resetPasswordTestWithInvalidInput() {
		//Arrange
		String mockEmail = "omnislash@ditto.com";
		String mockUrl = System.getenv("DITTO_ANGULAR_IP_AND_PORT")+"/#/reset/";
		String mockMessage = "Click the following link to reset your password: "+mockUrl+null;
		MyCustomMessage expectedMessage = new MyCustomMessage("Invalid Email Address", "Please Try Again");
		when(userAccountService.getUserByUsername("",mockEmail)).thenReturn(null);
		
		//Act
		MyCustomMessage actualMessage = userAccountController.resetPassword(mockSession, mockEmail);
		
		//Assert
		verify(emailService, never()).sendMail(mockEmail, "Password Reset", mockMessage);
		assertEquals(actualMessage, expectedMessage);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
