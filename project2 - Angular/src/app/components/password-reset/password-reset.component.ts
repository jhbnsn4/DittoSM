import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';
import { IMyCustomMessage } from 'src/app/models/mycustommessage';
import { IUserAccount } from 'src/app/models/useraccount';
import { UserService } from 'src/app/services/user.service';
import { LoadingHandler } from 'src/app/util/loading-handler';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  targetId: number;
  loadingHandler = new LoadingHandler();

  public targetUser: IUserAccount = {
    userId: 0,
    firstName: '',
    lastName: '',
    birthday: '',
    statusText: '',
    profilePicture: '',
    username: '',
    password: '',
    userEmail: '',
    postList: [],
    dittoFollowerList: [],
    dittoFollowingList: []
  };


  constructor(private userService: UserService, private route: ActivatedRoute, private myRouter: Router) { }

  ngOnInit(): void {
    this.targetId = parseInt(this.route.snapshot.paramMap.get('id'));

    this.retrieveUserInformation();
  }

  /////////// GETTERS & SETTERS
  get firstName() {
    return this.targetUser ? this.targetUser?.firstName : "Something went wrong. Cannot find user.";
  }
  set firstName(firstName: string) {
    this.targetUser.firstName = firstName;
  }
  get lastName() {
    return this.targetUser?.lastName;
  }
  set lastName(lastName: string) {
    this.targetUser.lastName = lastName;
  }

  //////////// OTHER METHODS

  // Retrieve user from database server 
  retrieveUserInformation() {
    // Retrieve by id if we were given one

    this.userService.getUserByIdPassword(this.targetId).subscribe(
      (data: IUserAccount) => {
        this.targetUser = data;
        this.targetUser.password='';
      } 

    )

    // Otherwise, retrieve user from current session
    

  }

  onClickUpdatePassword() {
    this.loadingHandler.start();
    let response = this.userService.updatePassword(this.targetUser as IUserAccount).subscribe(
      (data: IMyCustomMessage) => {
        if (data.message === "Password Succefully Updated") {
          this.loadingHandler.finish();
          this.myRouter.navigate(['/login']);
        }
      }
    );


  }

}
