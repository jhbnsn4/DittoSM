package ditto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ditto_user")
public class User {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	
	@Column(name="user_username", unique=true, nullable=false)
	private String userName;
	
	@Column(name="user_password", nullable=false)
	private String userPassword;
	
	@Column(name="user_email", unique=true, nullable=false)
	private String userEmail;
	
	@Column(name="user_first_name", nullable=false)
	private String userFName;
	
	@Column(name="user_last_name", nullable=false)
	private String userLName;
	
	@Column(name="user_dob", nullable=false)
	private String dob;
	
	public User() {
	}

	public User(int userId, String userName, String userPassword, String userEmail, String userFName, String userLName,
			String dob) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userFName = userFName;
		this.userLName = userLName;
		this.dob = dob;
	}
	public User(String userName, String userPassword, String userEmail, String userFName, String userLName,
			String dob) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userFName = userFName;
		this.userLName = userLName;
		this.dob = dob;
	}

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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserFName() {
		return userFName;
	}

	public void setUserFName(String userFName) {
		this.userFName = userFName;
	}

	public String getUserLName() {
		return userLName;
	}

	public void setUserLName(String userLName) {
		this.userLName = userLName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "\nUser [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userEmail="
				+ userEmail + ", userFName=" + userFName + ", userLName=" + userLName + ", dob=" + dob + "]";
	}
	
	

}
