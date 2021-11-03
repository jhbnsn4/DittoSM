import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { IUserAccount } from 'src/app/models/useraccount';
import { SessionAjaxService } from 'src/app/services/session-ajax.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
 
  loginFailed: boolean =false;

  loginName: string = ""

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

  constructor(private myAjax: SessionAjaxService, private myRouter: Router, private userService: UserService) { }

  ngOnInit(): void {
    this.myAjax.logoutRequest().subscribe(data => {})
  }
  
  /**
   * Returns true if given string is an email using a regular expression test
   * @param email the string that will be tested
   */
  userNameOrEmail(email: string){
    const regularExpression = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regularExpression.test(String(email).toLowerCase());
  }


  /**
   * Sends the login form data to be validated by the server
   */
  login(){
    // Are we sending a username or an email?
    if(this.userNameOrEmail(this.loginName)){
      this.userAccount.userEmail=this.loginName;
    } else {
      this.userAccount.username=this.loginName;
    }

    // Send the request
    this.myAjax.loginRequest(this.userAccount).subscribe(data => {
      // If the server refused to log user in
      if(data.message==="Unsuccessfull login"){
        // Reload login page
        this.myRouter.navigate(['/login']);

        // Zero out form
        this.loginName=""
        this.userAccount.password=""
        this.loginFailed=true;

      } 
      // On successful login
      else{
        // Set local storage vars
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userId", data.otherPossibleInformation);

        // Update search bar 
        this.userService.updateSearchBar();

        // Navigate to user's profile
        this.myRouter.navigate(['/profile']);
      }
    });
  }



}
