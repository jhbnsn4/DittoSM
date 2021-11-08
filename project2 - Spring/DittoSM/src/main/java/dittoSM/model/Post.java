package dittoSM.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

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
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dittoSM.utils.CustomListSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dittoSM.utils.CustomListSerializer;
@JsonIgnoreProperties({"likes"})
@Entity
@Table(name="post")
public class Post {

	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int postId;
	
	@Column(name="post_text", columnDefinition="TEXT")
	private String text;
	
	@Column(name="like_num")
	private int numLikes;
	
	@Column(name="created_time", nullable=false)
	private Timestamp createdTime;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="author_FK", nullable=false)
    @JsonSerialize(using = CustomListSerializer.class)
	private UserAccount authorFK;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ImagePath> imageList;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<UserAccount> likes;
	
	public Post() {
	}
	
	public Post(String text) {
		super();
		this.text = text;
	}
	
	public Post(String text, List<ImagePath> imageList) {
		super();
		this.text = text;
		this.imageList = imageList;
	}

	public Post(int postId, String text, int numLikes, Timestamp createdTime, UserAccount authorFK,
			List<ImagePath> imageList, List<UserAccount> likes) {
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
			List<ImagePath> imageList, List<UserAccount> likes) {
		super();
		this.text = text;
		this.numLikes = numLikes;
		this.createdTime = createdTime;
		this.authorFK = authorFK;
		this.imageList = imageList;
		this.likes = likes;
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
	
//	@JsonBackReference
	public UserAccount getAuthorFK() {
		return authorFK;
	}

//	@JsonBackReference
	public void setAuthorFK(UserAccount authorFK) {
		this.authorFK = authorFK;
	}

	public List<ImagePath> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImagePath> imageList) {
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
		return "\nPost [postId=" + postId + ", text=" + text + ", numLikes=" + numLikes + ", createdTime=" + createdTime
				+ ", authorFK=" + authorFK + ", imageList=" + imageList + ", likes=" + likes + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorFK, createdTime, imageList, likes, numLikes, postId, text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(authorFK, other.authorFK) && Objects.equals(createdTime, other.createdTime)
				&& Objects.equals(imageList, other.imageList) && Objects.equals(likes, other.likes)
				&& numLikes == other.numLikes && postId == other.postId && Objects.equals(text, other.text);
	}

	
}
