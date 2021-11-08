package dittoSM.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dittoSM.model.MyCustomMessage;
import dittoSM.model.UserAccount;
import dittoSM.service.UserAccountService;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

	SessionController sessionController;
	
	@Mock
	UserAccountService userAccountService;
	
	@Mock
	HttpSession mockSession;
	
	@BeforeEach
	void setUp() {
		sessionController = new SessionController(userAccountService);
	}
	
	@Test
	void loginWithCurrentUser() {
		//Arrange
		String targetUsername = "User1";
		String targetEmail = "user1@ditto.com";
		UserAccount mockUser = new UserAccount(1, targetUsername, "password", targetEmail, "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		MyCustomMessage expectedMessage = new MyCustomMessage("You have successfully logged IN", "1");
		when(userAccountService.getUserByUsername(targetUsername, targetEmail)).thenReturn(mockUser);
		
		//Act
		MyCustomMessage actualMessage = sessionController.login(mockSession, mockUser);
		
		//Assert
		verify(userAccountService, times(1)).getUserByUsername(targetUsername, targetEmail);
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void loginWithEmptyUser() {
		//Arrange
		String targetUsername = "";
		String targetEmail = "";
		UserAccount mockUser = new UserAccount(0,"","","","","",null,"","",null,null,null);
		MyCustomMessage expectedMessage = new MyCustomMessage("Unsuccessfull login", "User Not Found");
//		when(userAccountService.getUserByUsername(targetUsername, targetEmail)).thenReturn(mockUser);
		
		//Act
		MyCustomMessage actualMessage = sessionController.login(mockSession, mockUser);
		
		//Assert
		verify(userAccountService, times(1)).getUserByUsername(targetUsername, targetEmail);
		assertEquals(actualMessage, expectedMessage);
	}
	
	@Test
	void logoutTest() {
		//Arrange
		MyCustomMessage expectedMessage = new MyCustomMessage("You have successfully logged OUT", "");
	
		//Act
		MyCustomMessage actualMessage = sessionController.logout(mockSession);
	
		//Assert
		assertEquals(actualMessage, expectedMessage);
	}
}
