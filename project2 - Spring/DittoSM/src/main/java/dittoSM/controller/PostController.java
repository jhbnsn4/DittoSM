package dittoSM.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

/**
 * @author Jae Kyoung Lee (LJ)
 * @author Ryan Moss
 * @author John Benson
 */

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "#{environment.DITTO_ANGULAR_IP_AND_PORT}", allowCredentials = "true")
public class PostController {

	private PostService postServ;
	private ImageService imageServ;

	
/**
 * End-point to update an existing post with a user-unique like.
 * @param currentSes acquires session to retrieve the current user
 * @param post is retrieved in the request body and is updated with a "like" from a unique user
 */
	@PutMapping(value = "/addLike")
	public void addLike(HttpSession currentSes, @RequestBody Post post) {


		UserAccount currentUserForLike = (UserAccount) currentSes.getAttribute("currentUser");

		postServ.updatePost(post, currentUserForLike);
	}
	
	/**
	 * End-point to add a post that contains only text.
	 * @param currentSes acquires user from session and is tested for a null value.
	 * @param postText is received to add new text to a post without an image
	 * @return
	 */

	@PostMapping(value = "/newPost")
	public ResponseEntity<Post> addPostNoImages(HttpSession currentSes, @RequestParam("postText") String postText) {

		UserAccount currentUser = (UserAccount) currentSes.getAttribute("currentUser");
		System.out.println("currentUser is not null: " + (currentUser != null));

		// Create Post object and send to database
		Post newPost = new Post(postText);
		postServ.addNewPost(newPost, currentUser.getUserId());

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(newPost);
	}
	
	/**
	 * End-point to create a post that includes an image.
	 * @param currentSes is used to acquire the current user
	 * @param postText is the text submitted for the post
	 * @param postFile is the image file path.  Used to add image to image list.
	 * @return returns a completed post with an image
	 */

	@PostMapping(value = "/newPostWithImages")
	public ResponseEntity<Post> addPostWithImages(HttpSession currentSes, @RequestParam("postText") String postText,
			@RequestParam("postFile") String postFile, @RequestParam("fileSource") MultipartFile[] fileSource) {

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
	/**
	 * End-point to retrieve a complete list of posts
	 * @return returns a list of posts
	 */
	@GetMapping(value = "/getPosts")
	public List<Post> getAllUsers() {
	return postServ.findAllPosts();
	}
	/**
	 * Returns a list of posts specific to a userId.
	 * @param userid the current user.
	 * @return returns a complete list of user-specific posts.
	 */
	@GetMapping(value = "/getPosts/{userid}")
	public @ResponseBody List<Post> getPostById(@PathVariable("userid") Integer userid) {
		if (userid == 0 || userid.equals(null)) {
			return postServ.findAllPosts();
		} else {
			return postServ.findAllPostsById(userid);
		}

	}
	/**
	 * Returns a byte arrays which is converted to JPEG image, if the image passed
	 * "imageName" is not null.
	 * @param imageName is the image passed from the post.
	 * @return the return statement returns JPEG image file
	 */

	@GetMapping(value = "/getPostImages", params = "imageName")
	public ResponseEntity<byte[]> getImageFromPost(@RequestParam("imageName") String imageName) {

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
/**
 * @Autowired Constructor for PostController.
 */
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
