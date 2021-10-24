package ditto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserAccounts")
public class UserAccounts {
	
	@Id
	@Column(name="ditto_user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	
	@Column(name="ditto_username")
	private String userName;
	
	@Column(name="ditto_password")
	private String password;
	
	@Column(name="ditto_email")
	private String email;
	
	@Column(name="ditto_first_name")
	private String firstName;
	
	@Column(name="ditto_family_name")
	private String familyName;
	
	//CONSTRUCTORS
	public UserAccounts() {/*No Args*/}
	
	
	public UserAccounts(int userId, String userName, String password, String email, String firstName,
			String familyName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.familyName = familyName;
	}

	//Not neccessary due to the @GeneratedValue
	public UserAccounts(String userName, String password, String email, String firstName, String familyName) {
		//All args minus ID
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.familyName = familyName;
	}


	//GETTERS AND SETTERS
	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getFamilyName() {
		return familyName;
	}


	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	

	@Override
	public String toString() {
		return "\n\tUserAccounts [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email="
				+ email + ", firstName=" + firstName + ", familyName=" + familyName + "]";
	}
	
	
	
	
	
	
	

}
