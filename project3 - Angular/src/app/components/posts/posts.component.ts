import { HttpErrorResponse } from '@angular/common/http';
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
  constructor(private postServ: PostService) { }

  ngOnInit(): void {
    this.getPosts();
  }

  getPosts():void {
    console.log("this is getPosts");

    this.postServ.getAllPosts().subscribe(
      (response: IPost[]) =>{
        console.log(response);
        this.posts = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    )
  }

}
