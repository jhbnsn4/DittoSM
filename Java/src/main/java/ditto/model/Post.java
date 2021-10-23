package ditto.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ditto_post")
public class Post {
	
	private int postId;
	private String post_text;

}
