package dittoSM.service;

import java.util.List;

import dittoSM.model.Post;
import dittoSM.model.UserAccount;

public interface PostService {
	
	public boolean addNewPost(Post post, int userid);
	
	public List<Post> findAllPosts();
	public List<Post> findAllPostsById(int userid);


	void updatePost(Post post, UserAccount user);

}
