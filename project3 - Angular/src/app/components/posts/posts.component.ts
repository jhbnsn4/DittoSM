import {
  Component, Input, OnInit
} from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { IPost } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css'],
})
export class PostsComponent implements OnInit {
  private eventsSubscription: Subscription;

  @Input() events: Observable<number>;

  posts: IPost[] = [  ];

  userid: number = 0;

  constructor(private postServ: PostService) { }

  ngOnInit(): void {
    
    this.postServ.theOberv.subscribe((payload: string) => {
      if (payload == 'get list') {
        // this.posts = this.postServ.posts;
        this.getPosts();
      }
    });


    if (this.events == undefined) {
      this.getPosts();

    } else {
      this.eventsSubscription = this.events.subscribe((response) => {
        if (response != 0) {
          console.log(response + ' inside eventssuc');
          this.userid = response;
          this.getPosts();
        }
      });
    }
  }

    
  likeButtonClicked(number){
    console.log("before like");
    console.log(number);
  
    let response = this.postServ.addLike(this.posts[number] as IPost).subscribe(
      (data: string) => {
    //     let updateLikeNumber = Number(document.getElementById("likeNumber").innerHTML) +1;
    // document.getElementById("likeNumber").innerHTML = updateLikeNumber.toString();    
    // console.log("after like");
    this.ngOnInit();
      }
    );
    

  }


  getPosts(): void {
    console.log('this is getPosts');

    this.postServ
      .getPostsByUserId(this.userid)
      .subscribe((response: IPost[]) => {
        this.posts = response;
        // this.postServ.posts = this.posts;
      });
  }
}
