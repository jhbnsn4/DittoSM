package dittoSM.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dittoSM.dao.ImageDao;
import dittoSM.model.ImageMap;
import dittoSM.model.UserAccount;
import dittoSM.utils.MyLogger;

@Service("imageService")
public class ImageServiceImpl implements ImageService {

	private ImageDao imageDao;

	@Override
	public void addImage(ImageMap image) {
		imageDao.insertImage(image);
	}

	/**
	 * Sends our profile image to the DAO layer to be persisted, but also checks the user's original
	 * profile image and returns false if they are equivalent. If they are different, the original will be deleted.
	 */
	@Override
	public boolean addProfilePicture(MultipartFile imageFile, String originalProfileImage) {

		// Does this user already have a profile picture?
		ImageMap originalImage = getImageByName(originalProfileImage);
		if (originalImage != null) {
			// Are we trying to upload an existing image?
			if (originalImage.getImageName().equals(imageFile.getOriginalFilename())) {
				// Do nothing
				return false;
			}
			else {
				deleteImage(originalImage);
			}
		}

		// Read the new image file
		byte[] imageBytes = {};
		try {
			imageBytes = imageFile.getBytes();
		} catch (IOException e) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Exception when reading profile image file", e);
			e.printStackTrace();
			return false;
		}
		
		addImage(new ImageMap(imageBytes, imageFile.getOriginalFilename()));
		return true;
	}

	@Override
	public void deleteImage(ImageMap image) {
		imageDao.deleteImage(image);
	}

	@Override
	public void deleteImageByName(String imageName) {
		imageDao.deleteImageByName(imageName);
	}

	@Override
	public ImageMap getImageByName(String imageName) {
		return imageDao.selectImageByName(imageName);
	}

////////////////// CONSTRUCTORS
	public ImageServiceImpl() {
	}

	@Autowired
	public ImageServiceImpl(ImageDao imageDao) {
		super();
		this.imageDao = imageDao;
	}

}
