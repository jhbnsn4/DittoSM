import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IUserAccount } from 'src/app/models/useraccount';
import { SessionAjaxService } from 'src/app/services/session-ajax.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
 
  loginFailed: boolean =false;

  userAccount: IUserAccount= 
  {
    'userId':0,
    'username':'', 
    'password':'',
    'userEmail':'',
    'firstName':'',
    'lastName':'',
    'birthday': '',
    'statusText': '',
    'profilePicture': null, //{} as IImageMap,
    'dittoFollowerList': null, // {} as IUserAccount[],
    'dittoFollowingList': null,//{} as IUserAccount[],
    'postList': null //{} as IPost[]
    };

  constructor(private myAjax: SessionAjaxService, private myRouter: Router) { }

  ngOnInit(): void {
    this.myAjax.logoutRequest().subscribe(data => {console.log(data)})
  }

  login(){


    console.log('login clicked');

    this.myAjax.loginRequest(this.userAccount).subscribe(data => {
      console.log(data)
      if(data.message==="Unsuccessfull login"){
        this.myRouter.navigate(['/login']);
        this.userAccount.username=""
        this.userAccount.password=""
        this.loginFailed=true;

      } else{
        this.myRouter.navigate(['/profile']);
      }
    
    });

    
    
  }



}
