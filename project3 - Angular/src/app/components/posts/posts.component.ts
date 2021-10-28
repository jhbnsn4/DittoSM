import { Component, OnInit, Input } from '@angular/core';
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

  constructor(private postServ: PostService, private userServ: UserService) { }

  ngOnInit(): void {
    console.log(this.userid);
    this.getPosts(this.userid);
  }

  getPosts(userid: number):void {
    console.log("this is getPosts");
    // console.log(this.userAcc.userAccount.username);

    this.postServ.getPostsByUserId(userid).subscribe(
      (response: IPost[]) =>{
        // console.log(response);
        this.posts = response;
      }
    )
  }

  

}
