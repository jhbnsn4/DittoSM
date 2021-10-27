import { Component, OnInit } from '@angular/core';
import { IPost } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  posts: IPost[] = [];
  constructor(private postObs: PostService) { }

  ngOnInit(): void {
  }

  getPosts() {
    console.log("this is getPosts");

    this.postObs.getAllPosts().subscribe(
      data=>{
        console.log(data);

      }
    )
  }

}
