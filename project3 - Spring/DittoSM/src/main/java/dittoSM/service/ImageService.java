package dittoSM.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import dittoSM.model.ImageMap;

public interface ImageService {

	public void addImage(ImageMap image);
	public void addImages(List<ImageMap> imageList);
	public boolean addProfilePicture(MultipartFile imageFile, String originalProfileImage);
	public void deleteImage(ImageMap image);
	
	public void deleteImageByName(String imageName);
	public ImageMap getImageByName(String imageName);
	public ImageMap getDefaultImage();
}
