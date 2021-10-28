package dittoSM.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dittoSM.model.Post;
import dittoSM.model.UserAccount;
import dittoSM.service.PostService;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
public class PostController {
	
	private PostService postServ;

	@PostMapping(value="/newPost")
	public boolean addPost(@RequestBody Post post, HttpSession currentSes) {
		UserAccount currentUser = (UserAccount) currentSes.getAttribute("currentUser");
		postServ.addNewPost(post, currentUser.getUserId());
		return true;
	}
	
	@GetMapping(value="/getAllPosts")
	public List<Post> getAllUsers(){
		return postServ.findAllPosts();
	}
	
	@GetMapping(value="/getPosts/{userid}") 
	public @ResponseBody List<Post> getPostById(@PathVariable("userid") int userid) {
		System.out.println(userid);
		return postServ.findAllPostsById(userid);
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
