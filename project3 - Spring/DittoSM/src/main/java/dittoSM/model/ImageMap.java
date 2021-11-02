package dittoSM.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(name="image_name", nullable=false)
	private String imageName;
	
	public ImageMap() {
	}

	public ImageMap(byte[] imageFile, String imageName) {
		super();
		this.imageFile = imageFile;
		this.imageName = imageName;
	}
	public ImageMap(int imageId, byte[] imageFile, String imageName) {
		super();
		this.imageId = imageId;
		this.imageFile = imageFile;
		this.imageName = imageName;
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

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "ImageMap [imageId=" + imageId + ", imageFile=" + Arrays.toString(imageFile) + ", imageName=" + imageName
				+ "]";
	}
	
	
}
