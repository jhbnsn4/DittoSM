package dittoSM.model;

import java.util.Arrays;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(imageFile);
		result = prime * result + Objects.hash(imageId, imageName);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageMap other = (ImageMap) obj;
		return Arrays.equals(imageFile, other.imageFile) && imageId == other.imageId
				&& Objects.equals(imageName, other.imageName);
	}
	
	
	
	
}
