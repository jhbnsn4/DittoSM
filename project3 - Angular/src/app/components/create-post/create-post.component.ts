import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { IPost } from 'src/app/models/post';
import { IPostResponse } from 'src/app/models/post-text';
import { PostService } from 'src/app/services/post.service';
import { PostsComponent } from '../posts/posts.component';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  posts: IPost;

  constructor(private postServ: PostService) { }

  ngOnInit(): void {
  }

  public addPost(addPostForm: NgForm): void {
    this.posts = addPostForm.value
    console.log(addPostForm.value)
    this.postServ.profanityFilter(this.posts.text).subscribe(
      (data: IPostResponse) =>{
        console.log(data);
        addPostForm.setValue({
          'text': data.result
        })
        console.log(addPostForm.value)
        this.postServ.addPost(addPostForm.value).subscribe(
          (response: IPost) =>{
            // console.log(response);
            // console.log(addPostForm.value);
            this.postServ.posts.unshift(addPostForm.value);
            this.postServ.triggerBehaveSubj('get list');
            addPostForm.reset();
          });
      });
  }
}
