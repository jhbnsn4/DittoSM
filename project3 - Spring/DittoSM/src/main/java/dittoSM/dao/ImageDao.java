package dittoSM.dao;

import java.util.List;

import dittoSM.model.ImageMap;

public interface ImageDao {

	public void insertImage(ImageMap image);
	public void insertImages(List<ImageMap> imageList);
	public void deleteImage(ImageMap image);
	public void deleteImageByName(String imageName);
	public ImageMap selectImageByName(String imageName);
	public ImageMap selectFirstImage();
}
