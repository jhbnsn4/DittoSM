import {
  Component, Input, OnInit
} from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { IPost } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css'],
})
export class PostsComponent implements OnInit {
  private eventsSubscription: Subscription;

  @Input() events: Observable<number>;
  posts: IPost[] = [];
  userid: number = 0;
  isPostEmpty: number;
  noPost: string;

  url=environment.dittoUrl;

  constructor(private postServ: PostService) { }

  ngOnInit(): void {

    this.postServ.theOberv.subscribe((payload: string) => {
      if (payload == 'get list') {
        // this.posts = this.postServ.posts;/
        this.postServ.triggerBehaveSubj('');
        this.noPost = '';
        this.getPosts();
      }
    });

    if (this.events == undefined) {
      this.getPosts();

    } else {
      this.eventsSubscription = this.events.subscribe((response) => {
        if (response != 0) {
          this.userid = response;
          this.getPosts();
        }
      });
    }
  }


  likeButtonClicked(number) {
    let response = this.postServ.addLike(this.posts[number] as IPost).subscribe(
      (data: string) => {
        this.ngOnInit();
      }
    );


  }


  getPosts(): void {

    this.postServ
      .getPostsByUserId(this.userid)
      .subscribe((response: IPost[]) => {
        if (response!==null) {
          this.posts = response;
          this.isPostEmpty = this.posts.length;
        } 
        if(this.isPostEmpty==0) {
          this.noPost = "There are no posts!";
        } 
      });
  }
}
