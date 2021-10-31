import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IUserAccount } from 'src/app/models/useraccount';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-password-landing',
  templateUrl: './password-landing.component.html',
  styleUrls: ['./password-landing.component.css']
})
export class PasswordLandingComponent implements OnInit {
  
  userEmail: string =""

  emailFailed: boolean = false

  constructor(private myAjax: UserService, private myRouter: Router) { }

  ngOnInit(): void {

  }

  sendEmail(){
    console.log('Send Email Clicked');

    
    this.myAjax.postResetPassword(this.userEmail).subscribe(data => {
      console.log(data)
      if(data.message==="Invalid Email Address"){
        this.emailFailed = true;
        
      } else{
        this.emailFailed = false;
        this.myRouter.navigate(['/login']);
      }
    })



  }

}
