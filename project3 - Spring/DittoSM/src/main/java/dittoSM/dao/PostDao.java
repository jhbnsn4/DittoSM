package dittoSM.dao;

import java.util.List;

import dittoSM.model.Post;

public interface PostDao {
	
	public void insertNewPost(Post post);
	
	public List<Post> selectAllPosts();
	public List<Post> selectPostsById(int userid);
	
}
