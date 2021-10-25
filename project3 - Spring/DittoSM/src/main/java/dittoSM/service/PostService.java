package dittoSM.service;

import java.util.List;

import dittoSM.model.Post;
import dittoSM.model.UserAccount;

public interface PostService {
	
	public boolean addNewPost(Post post);
	
	public List<Post> findAllPosts();
	public List<Post> findAllPostsById(UserAccount user);

}
