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

	public ImageMap(int imageId, byte[] imageFile, Post postFK) {
		super();
		this.imageId = imageId;
		this.imageFile = imageFile;
		this.postFK = postFK;
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

	@Override
	public String toString() {
		return "ImageMap [imageId=" + imageId + ", imageFile=" + Arrays.toString(imageFile) + ", postFK=" + postFK
				+ "]";
	}

	
	
}
