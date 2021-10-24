package ditto.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ditto_post")
public class Post {
	
	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int postId;
	
	@Column(name="post_text")
	private String post_text;
	@Column(name="post_created_time")
	private Timestamp postCreatedTime;
	@Column(name="post_picture_id")
	private int postPictureId;
	
	@ManyToOne 
	@JoinColumn(name="fk_post_user")
	private User postUserId;
	
	
	public Post() {
	}
	public Post(int postId, String post_text, Timestamp postCreatedTime, int postPictureId, User postUserId) {
		super();
		this.postId = postId;
		this.post_text = post_text;
		this.postCreatedTime = postCreatedTime;
		this.postPictureId = postPictureId;
		this.postUserId = postUserId;
	}
	public Post(String post_text, Timestamp postCreatedTime, int postPictureId, User postUserId) {
		super();
		this.post_text = post_text;
		this.postCreatedTime = postCreatedTime;
		this.postPictureId = postPictureId;
		this.postUserId = postUserId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPost_text() {
		return post_text;
	}
	public void setPost_text(String post_text) {
		this.post_text = post_text;
	}
	public Timestamp getPostCreatedTime() {
		return postCreatedTime;
	}
	public void setPostCreatedTime(Timestamp postCreatedTime) {
		this.postCreatedTime = postCreatedTime;
	}
	public int getPostPictureId() {
		return postPictureId;
	}
	public void setPostPictureId(int postPictureId) {
		this.postPictureId = postPictureId;
	}
	public User getPostUserId() {
		return postUserId;
	}
	public void setPostUserId(User postUserId) {
		this.postUserId = postUserId;
	}
	@Override
	public String toString() {
		return "\n\tPost [postId=" + postId + ", post_text=" + post_text + ", postCreatedTime=" + postCreatedTime
				+ ", postPictureId=" + postPictureId + ", postUserId=" + postUserId + "]";
	}
	
	
}
