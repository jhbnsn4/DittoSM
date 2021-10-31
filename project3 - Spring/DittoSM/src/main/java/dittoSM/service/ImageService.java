package dittoSM.service;

import org.springframework.web.multipart.MultipartFile;

import dittoSM.model.ImageMap;
import dittoSM.model.UserAccount;

public interface ImageService {

	public void addImage(ImageMap image);
	public boolean addProfilePicture(MultipartFile imageFile, String originalProfileImage);
	public void deleteImage(ImageMap image);
	
	public void deleteImageByName(String imageName);
	public ImageMap getImageByName(String imageName);
}
