import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IPost } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

  
  posts: IPost[] = [];  

  @Input() userid: number = 0;

  constructor(private postServ: PostService) { }

  ngOnInit(): void {
    console.log(this.userid);
    this.getPosts();
    this.postServ.theOberv.subscribe((payload:string)=>{
      if(payload=='get list') {
        this.posts = this.postServ.posts;
        this.getPosts();

      }
    });
  }

  getPosts():void {
    console.log("this is getPosts");

    this.postServ.getPostsByUserId(this.userid).subscribe(
      (response: IPost[]) =>{
        this.posts = response;
        this.postServ.posts = this.posts;
      }
    )
  }

  

}
