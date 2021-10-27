import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { IImageMap } from 'src/app/models/imagemap';
import { IPost } from 'src/app/models/post';
import { IUserAccount } from 'src/app/models/useraccount';
import { SessionAjaxService } from 'src/app/service/session-ajax.service';
import { UserAjaxService } from 'src/app/service/user-ajax.service';

@Component({
  selector: 'app-register-account',
  templateUrl: './register-account.component.html',
  styleUrls: ['./register-account.component.css']
})
export class RegisterAccountComponent implements OnInit {

  constructor(private myAjax: UserAjaxService) { }

  userAccount: IUserAccount= 
  {
  userId:0,
  username:'', 
  password:'',
  firstName:'',
  lastName:'',
  'birthday': 0,
  'statusText': '',
  'profilePicture': {} as IImageMap,
  'dittoFollowerList': {} as IUserAccount[],
  'dittoFollowingList': {} as IUserAccount[],
  'postList': {} as IPost[]
  }


  ngOnInit(): void {
  }

  registerButton(){
    console.log("register button clicked");

    this.myAjax.addUserRequest(this.userAccount).subscribe(data => {console.log(data)});
  }


}
