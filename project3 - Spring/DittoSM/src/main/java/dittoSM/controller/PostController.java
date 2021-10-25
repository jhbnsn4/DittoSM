package dittoSM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dittoSM.model.Post;
import dittoSM.service.PostService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
public class PostController {
	
	private PostService postServ;

	@PostMapping(value="/newPost")
	public boolean addPost(@RequestBody Post post) {
		postServ.addNewPost(post);
		return true;
	}
	
	@GetMapping(value="/getAllPosts")
	public List<Post> getAllUsers(){
		return postServ.findAllPosts();
	}
	
	
	
	
	
	
	
	//////////////Constructor
	public PostController() {
	}	
	
	@Autowired
	public PostController(PostService postServ) {
		super();
		this.postServ = postServ;
	}
	
}
