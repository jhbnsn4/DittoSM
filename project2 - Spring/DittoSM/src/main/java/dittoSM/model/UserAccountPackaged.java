package dittoSM.model;

import java.sql.Timestamp;
import java.util.Objects;

public class UserAccountPackaged {

	private int userId;
	private String firstName;
	private String lastName;
	private String statusText;
	private Timestamp birthday;
	private String profilePicture;
	
//////////////////////////// CONSTRUCTORS
	public UserAccountPackaged() {
	}
	public UserAccountPackaged(UserAccount account) {
		super();
		this.userId = account.getUserId()	;
		this.firstName = account.getFirstName();
		this.lastName = account.getLastName();
		this.statusText = account.getStatusText();
		this.birthday = account.getBirthday();
		this.profilePicture = account.getProfilePicture();
	}
	public UserAccountPackaged(int userId, String firstName, String lastName, String statusText, Timestamp birthday,
			String profilePicture) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.statusText = statusText;
		this.birthday = birthday;
		this.profilePicture = profilePicture;
	}
	
/////////////////////////////// SETTERS & GETTERS
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	@Override
	public String toString() {
		return "UserAccountPackaged [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", statusText=" + statusText + ", birthday=" + birthday + ", profilePicture=" + profilePicture + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(birthday, firstName, lastName, profilePicture, statusText, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccountPackaged other = (UserAccountPackaged) obj;
		return Objects.equals(birthday, other.birthday) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(profilePicture, other.profilePicture)
				&& Objects.equals(statusText, other.statusText) && userId == other.userId;
	}
	

}
