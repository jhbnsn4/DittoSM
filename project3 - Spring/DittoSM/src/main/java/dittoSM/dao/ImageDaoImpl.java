package dittoSM.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dittoSM.model.ImageMap;
/**
 * 
 * @author Synergy Development Team
 *
 */
@Transactional
@Repository("imageDao")
public class ImageDaoImpl implements ImageDao {

	private SessionFactory sesFact;
	
	/**
	 * Saves an image via the current session.
	 */
	@Override
	public void insertImage(ImageMap image) {
		sesFact.getCurrentSession().save(image);
	}
	
	/**
	 * Saves an image by adding an image to a list of images.
	 */
	@Override
	public void insertImages(List<ImageMap> imageList) {
		Session curSes = sesFact.getCurrentSession();
		for (ImageMap image : imageList) {
			curSes.save(image);
		}
	}
	
	/**
	 * Deletes an Image via the current session.
	 */
	@Override
	public void deleteImage(ImageMap image) {
		sesFact.getCurrentSession().delete(image);
	}

	/**
	 * Utilizes HQL to delete specific image from ImageMap by name.
	 */
	@Override
	public void deleteImageByName(String imageName) {
		String hql = "delete from ImageMap where imageName=:imageName";
		// Delete from table
		sesFact.getCurrentSession().createQuery(hql).setParameter("imageName", imageName)
		.executeUpdate();
	}

	/**
	 * Utilizes HQL to retrieve specific image from ImageMap by name.
	 */
	@Override
	public ImageMap selectImageByName(String imageName) {
		// Get a list of all images with that name
		List<ImageMap> imageList = sesFact.getCurrentSession().createQuery("from ImageMap where imageName=:imageName", ImageMap.class)
				.setParameter("imageName", imageName).getResultList();
		
		// Don't access elements if list is empty, just return null 
		if (imageList.isEmpty())
			return null;
		
		return imageList.get(0);
	}
	
	/**
	 * Utilizes SQL Query to acquire first image (default image , imageId =1).
	 */
	@Override
	public ImageMap selectFirstImage() {
		return sesFact.getCurrentSession().createQuery("from ImageMap where imageId=1", ImageMap.class)
				.uniqueResult();
	}

//////////////// CONSTRUCTORS
	public ImageDaoImpl() {
	}
	
	@Autowired
	public ImageDaoImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}

	
	
}
