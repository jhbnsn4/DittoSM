package dittoSM.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dittoSM.model.ImageMap;
import dittoSM.utils.MyLogger;

@Transactional
@Repository("imageDao")
public class ImageDaoImpl implements ImageDao {

	private SessionFactory sesFact;
	
	@Override
	public void insertImage(ImageMap image) {
		sesFact.getCurrentSession().save(image);
	}
	
	@Override
	public void deleteImage(ImageMap image) {
		sesFact.getCurrentSession().delete(image);
	}

	@Override
	public void deleteImageByName(String imageName) {
		String hql = "delete from ImageMap where imageName=:imageName";
		// Delete from table
		sesFact.getCurrentSession().createQuery(hql).setParameter("imageName", imageName)
		.executeUpdate();
	}

	@Override
	public ImageMap selectImageByName(String imageName) {
		ImageMap image = sesFact.getCurrentSession().createQuery("from ImageMap where imageName=:imageName", ImageMap.class)
				.setParameter("imageName", imageName).uniqueResult();
		
		return image;
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
