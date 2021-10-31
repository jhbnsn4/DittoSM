package dittoSM.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dittoSM.model.ImageMap;
import dittoSM.model.ImagePath;
import dittoSM.model.Post;
import dittoSM.model.UserAccount;
import dittoSM.service.ImageService;
import dittoSM.service.PostService;
import dittoSM.utils.MyLogger;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PostController {

	private PostService postServ;
	private ImageService imageServ;

	@PostMapping(value = "/newPost")
	public ResponseEntity<Post> addPostNoImages(HttpSession currentSes, @RequestParam("postText") String postText) {

		System.out.println("received post: " + postText);

		UserAccount currentUser = (UserAccount) currentSes.getAttribute("currentUser");
		System.out.println("currentUser is not null: " + (currentUser != null));

		// Create Post object and send to database
		Post newPost = new Post(postText);
		postServ.addNewPost(newPost, currentUser.getUserId());

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(newPost);
	}

	@PostMapping(value = "/newPostWithImages")
	public ResponseEntity<Post> addPostWithImages(HttpSession currentSes, @RequestParam("postText") String postText,
			@RequestParam("postFile") String postFile, @RequestParam("fileSource") MultipartFile[] fileSource) {

		System.out.println("received post: " + postText);
		System.out.println("received post: " + postFile);
		System.out.println("received post: " + fileSource.length);

		// Create ImageMap(s)
		List<ImageMap> images = new ArrayList<>();
		List<ImagePath> imagePaths = new ArrayList<>();
		for (int i = 0; i < fileSource.length; i++) {

			// read image file
			byte[] byteFile = {};
			try {
				byteFile = fileSource[i].getBytes();
			} catch (IOException e) {
				Logger log = MyLogger.getLoggerForClass(this);
				log.error("Exception when reading profile image file", e);
				e.printStackTrace();
				return null;
			}
			// Add image to the list
			String fileName = fileSource[i].getOriginalFilename();
			images.add(new ImageMap(byteFile, fileName));
			imagePaths.add(new ImagePath(fileName));
		}

		// Get current user
		UserAccount currentUser = (UserAccount) currentSes.getAttribute("currentUser");
		System.out.println("currentUser is not null: " + (currentUser != null));

		// Persist images
		imageServ.addImages(images);

		// Create and save our post object
		Post newPost = new Post(postText, imagePaths);
		postServ.addNewPost(newPost, currentUser.getUserId());

		// Respond with our post object
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(newPost);
	}

	@GetMapping(value = "/getPosts")
	public List<Post> getAllUsers() {
		System.out.println(postServ.findAllPosts());
		return postServ.findAllPosts();
	}

	@GetMapping(value = "/getPosts/{userid}")
	public @ResponseBody List<Post> getPostById(@PathVariable("userid") Integer userid) {
		System.out.println(userid);
		if (userid == 0 || userid.equals(null)) {
//			System.out.println(userid + " this is if it's 0 or null");
			return postServ.findAllPosts();
		} else {
//			System.out.println(userid + " this is if it's not 0 or not null");
			return postServ.findAllPostsById(userid);
		}
	}

	@GetMapping(value = "/getPostImages", params = "imageName")
	public ResponseEntity<byte[]> getImageFromPost(@RequestParam("imageName") String imageName) {
		System.out.println("in getPostImages");
		
		// Get image 
		ImageMap image = imageServ.getImageByName(imageName);
		
		if (image == null) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Exception when reading post image file");
			return ResponseEntity.badRequest().contentType(MediaType.IMAGE_JPEG).body(null);
		}
		
		// Respond with image file
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.getImageFile());
	}

	////////////// Constructor
	public PostController() {
	}

	@Autowired
	public PostController(PostService postServ, ImageService imageServ) {
		super();
		this.postServ = postServ;
		this.imageServ = imageServ;
	}

}
