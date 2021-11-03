import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IUserAccount } from 'src/app/models/useraccount';
import { UserService } from 'src/app/services/user.service';
import { LoadingHandler } from 'src/app/util/loading-handler';


@Component({
  selector: 'app-password-landing',
  templateUrl: './password-landing.component.html',
  styleUrls: ['./password-landing.component.css']
})
export class PasswordLandingComponent implements OnInit {
  
  userEmail: string =""

  emailFailed: boolean = false

  loadingHandler = new LoadingHandler();

  constructor(private myAjax: UserService, private myRouter: Router) { }

  ngOnInit(): void {

  }

  sendEmail(){
    this.loadingHandler.start();
    
    this.emailFailed = false;
    this.myAjax.postResetPassword(this.userEmail).subscribe(data => {
      if(data.message==="Invalid Email Address"){
        this.emailFailed = true;
        this.loadingHandler.finish();
      } else{
        this.loadingHandler.finish();
        this.myRouter.navigate(['/login']);
      }
    })



  }

}
