import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { IPost } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  constructor(private postServ: PostService) { }

  ngOnInit(): void {
  }
  public addPost(addPostForm: NgForm): void {
    this.postServ.addPost(addPostForm.value).subscribe(
      (response: IPost) =>{
        console.log(response);
        addPostForm.reset();
      }
    );
  }

  

}
