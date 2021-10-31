package dittoSM.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="image_path")
public class ImagePath {

	@Id
	@Column(name="image_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int imageId;
	
	@Column(name="image_name", nullable=false)
	private String imageName;
	
	// Initializer block to set base image path
	{
		// Our hardcoded path
		imageName = "http://localhost:9009/DittoSM/api/posts/getPostImages?imageName=";
	}
	
	public ImagePath() {
	}

	public ImagePath(String imageName) {
		super();
		this.imageName += imageName;
	}
	public ImagePath(int imageId, String imageName) {
		super();
		this.imageId = imageId;
		this.imageName += imageName;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "ImagePath [imageId=" + imageId + ", imageName=" + imageName + "]";
	}
	
}
