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

  // postInsert: IPost = {'postId': 0, 'text':'', 'numLikes': 0, 'createdTime': new Date, 'authorFK': {} as IUser, 'imageList': {} as IImageMap[], 'likes': {} as IUser[]  };

  constructor(private postServ: PostService) { }

  ngOnInit(): void {
  }



}
