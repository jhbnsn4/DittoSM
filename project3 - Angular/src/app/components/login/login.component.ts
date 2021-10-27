import { Component, OnInit } from '@angular/core';
import { IImageMap } from 'src/app/models/imagemap';
import { IPost } from 'src/app/models/post';
import { IUserAccount } from 'src/app/models/useraccount';
import { SessionAjaxService } from 'src/app/service/session-ajax.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userAccount: IUserAccount= 
  {
    'userId':0,
    'username':'', 
    'password':'',
    'firstName':'',
    'lastName':'',
    'birthday': 0,
    'statusText': '',
    'profilePicture': null, //{} as IImageMap,
    'dittoFollowerList': null, // {} as IUserAccount[],
    'dittoFollowingList': null,//{} as IUserAccount[],
    'postList': null //{} as IPost[]
    }

  constructor(private myAjax: SessionAjaxService) { }

  ngOnInit(): void {
  }

  login(){


    console.log('login clicked');

    this.myAjax.loginRequest(this.userAccount).subscribe(data => {console.log(data)});
    
  
  }



}
