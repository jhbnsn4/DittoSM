package dittoSM.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="image_map")
public class ImageMap {

	@Id
	@Column(name="image_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int imageId;
	
	@Column(name="image_file", nullable=false)
	private byte[] imageFile;
	
	@ManyToOne
	@JoinColumn(name="post_FK")
	private Post postFK;
	
	@OneToOne(mappedBy="profilePicture")
	private UserAccount profileFK;
	
	public ImageMap() {
	}
	
	// Constructor for user profile
	public ImageMap(byte[] imageFile, UserAccount profileFK) {
		super();
		this.imageFile = imageFile;
		this.postFK = null;
		this.profileFK = profileFK;
	}
	
	// Constructor for post img
	public ImageMap(byte[] imageFile, Post postFK) {
		super();
		this.imageFile = imageFile;
		this.postFK = postFK;
		this.profileFK = null;
	}
	
	public ImageMap(int imageId, byte[] imageFile, Post postFK, UserAccount profileFK) {
		super();
		this.imageId = imageId;
		this.imageFile = imageFile;
		this.postFK = postFK;
		this.profileFK = profileFK;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
	}

	public Post getPostFK() {
		return postFK;
	}

	public void setPostFK(Post postFK) {
		this.postFK = postFK;
	}
	
	@JsonBackReference
	public UserAccount getProfileFK() {
		return profileFK;
	}

	@JsonBackReference
	public void setProfileFK(UserAccount profileFK) {
		this.profileFK = profileFK;
	}

	@Override
	public String toString() {
		return "ImageMap [imageId=" + imageId + ", imageFile=" + Arrays.toString(imageFile) + ", postFK=" + postFK
				+ "]";
	}

	
}
