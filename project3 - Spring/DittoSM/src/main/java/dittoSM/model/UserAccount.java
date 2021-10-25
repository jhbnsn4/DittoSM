package dittoSM.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_account")
public class UserAccount {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "birthday", nullable = false)
	private Timestamp birthday;

	@Column(name = "statusText")
	private String statusText;

	@OneToOne
	@JoinColumn(name = "picture_FK")
	private ImageMap profilePicture;

	@OneToMany(mappedBy = "authorFK")
	private List<Post> postList = new ArrayList<>();

	@ManyToMany(mappedBy = "dittoFollowingList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserAccount> dittoFollowerList;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserAccount> dittoFollowingList;

////////////////////////////// CONSTRUCTORS
	public UserAccount() {
	}

	public UserAccount(int userId, String username, String password, String firstName, String lastName,
			Timestamp birthday, String statusText, ImageMap profilePicture, List<Post> postList,
			List<UserAccount> dittoFollowerList, List<UserAccount> dittoFollowingList) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.statusText = statusText;
		this.profilePicture = profilePicture;
		this.postList = postList;
		this.dittoFollowerList = dittoFollowerList;
		this.dittoFollowingList = dittoFollowingList;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public ImageMap getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(ImageMap profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	public List<UserAccount> getDittoFollowerList() {
		return dittoFollowerList;
	}

	public void setDittoFollowerList(List<UserAccount> dittoFollowerList) {
		this.dittoFollowerList = dittoFollowerList;
	}

	public List<UserAccount> getDittoFollowingList() {
		return dittoFollowingList;
	}

	public void setDittoFollowingList(List<UserAccount> dittoFollowingList) {
		this.dittoFollowingList = dittoFollowingList;
	}

	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", birthday=" + birthday + ", statusText=" + statusText
				+ ", profilePicture=" + profilePicture + ", postList=" + postList + ", dittoFollowerList="
				+ dittoFollowerList + ", dittoFollowingList=" + dittoFollowingList + "]";
	}

}
