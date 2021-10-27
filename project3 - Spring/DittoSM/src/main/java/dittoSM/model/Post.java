package dittoSM.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="post")
public class Post {

	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int postId;
	
	@Column(name="post_text")
	private String text;
	
	@Column(name="like_num")
	private int numLikes;
	
	@Column(name="created_time", nullable=false)
	private Timestamp createdTime;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="author_FK", nullable=false)
	private UserAccount authorFK;
	
	@OneToMany(mappedBy="postFK")
	private List<ImageMap> imageList = new ArrayList<>();
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<UserAccount> likes;
	
	public Post() {
	}

	public Post(int postId, String text, int numLikes, Timestamp createdTime, UserAccount authorFK,
			List<ImageMap> imageList, List<UserAccount> likes) {
		super();
		this.postId = postId;
		this.text = text;
		this.numLikes = numLikes;
		this.createdTime = createdTime;
		this.authorFK = authorFK;
		this.imageList = imageList;
		this.likes = likes;
	}
	public Post(String text, int numLikes, Timestamp createdTime, UserAccount authorFK,
			List<ImageMap> imageList, List<UserAccount> likes) {
		super();
		this.text = text;
		this.numLikes = numLikes;
		this.createdTime = createdTime;
		this.authorFK = authorFK;
		this.imageList = imageList;
		this.likes = likes;
	}
	public Post(String text, int numLikes, Timestamp createdTime, UserAccount authorFK) {
		super();
		this.text = text;
		this.numLikes = numLikes;
		this.createdTime = createdTime;
		this.authorFK = authorFK;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	
	@JsonBackReference
	public UserAccount getAuthorFK() {
		return authorFK;
	}

	@JsonBackReference
	public void setAuthorFK(UserAccount authorFK) {
		this.authorFK = authorFK;
	}

	public List<ImageMap> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageMap> imageList) {
		this.imageList = imageList;
	}

	public List<UserAccount> getLikes() {
		return likes;
	}

	public void setLikes(List<UserAccount> likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", text=" + text + ", numLikes=" + numLikes + ", createdTime=" + createdTime
				+ ", authorFK=" + authorFK + ", imageList=" + imageList + ", likes=" + likes + "]";
	}

}
