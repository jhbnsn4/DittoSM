package dittoSM.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import dittoSM.model.UserAccount;

public class UserAccountDaoTest {
	
	public static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	UserAccountDao userDao = appContext.getBean("userAccountDao", UserAccountDao.class);
	
	@Test
	@Transactional
	void selectByUserId() {
		//Arrange
		UserAccount mockUser = new UserAccount(1, "Hero", "password", "omnislash@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		UserAccount expectedUser = new UserAccount(1, "Hero", "password", "omnislash@ditto.com", "Cloud", "Strife", new Timestamp(1000),"Whatever...", "", null, null, null);
		
		userDao.insertAccount(mockUser);
		
		//Act
		UserAccount actualUser = userDao.selectUserById(expectedUser.getUserId());
		
		//Assert
		assertEquals(expectedUser, actualUser);
		
	}
	

}
