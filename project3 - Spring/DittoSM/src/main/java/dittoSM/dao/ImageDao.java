package dittoSM.dao;

import dittoSM.model.ImageMap;

public interface ImageDao {

	public void insertImage(ImageMap image);
	public void deleteImage(ImageMap image);
	
	public void deleteImageByName(String imageName);
	public ImageMap selectImageByName(String imageName);
}
