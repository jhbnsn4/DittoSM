package dittoSM.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dittoSM.dao.ImageDao;
import dittoSM.model.ImageMap;
import dittoSM.utils.MyLogger;

/**
 * 
 * @author Ryan Moss
 * @author Jae Kyoung Lee (LJ)
 *
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {

	private ImageDao imageDao;

	/**
	 * Adds Image
	 */
	@Override
	public void addImage(ImageMap image) {
		imageDao.insertImage(image);
	}
	
	/**
	 * Inserts image into imageList
	 */
	@Override
	public void addImages(List<ImageMap> imageList) {
		imageDao.insertImages(imageList);
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
	
	/**
	 * Deletes image.
	 */
	@Override
	public void deleteImage(ImageMap image) {
		imageDao.deleteImage(image);
	}

	/**
	 * Deletes image by name.
	 */
	@Override
	public void deleteImageByName(String imageName) {
		imageDao.deleteImageByName(imageName);
	}
	
	/**
	 * REturns image by name.
	 */
	@Override
	public ImageMap getImageByName(String imageName) {
		return imageDao.selectImageByName(imageName);
	}
	
	/**
	 * Returns first image or "default" image.
	 */
	@Override
	public ImageMap getDefaultImage() {
		return imageDao.selectFirstImage();
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
