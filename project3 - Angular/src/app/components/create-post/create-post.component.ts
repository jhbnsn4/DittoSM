import { Component, OnInit } from '@angular/core';
import { IImageMap } from 'src/app/models/images';
import { IPost } from 'src/app/models/post';
import { IUser } from 'src/app/models/user';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  postInsert: IPost = {'postId': 0, 'postText':'', 'likeNum': 0, 'createdTime': new Date, 'author': {} as IUser, 'imageList': {} as IImageMap[], 'likes': {} as IUser[]  };

  constructor(private postOb: PostService) { }

  ngOnInit(): void {
  }

  createPost() {
    console.log("this is a test");

    this.postOb.addPost(this.postInsert, 1).subscribe(
      data => {
        console.log(data);
      })
  }

  


}
