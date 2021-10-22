package ditto;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainDriver {
	public static ApplicationContext appContext=
			new ClassPathXmlApplicationContext("dittoContext.xml");

	public static void main(String[] args) {


	}

}
