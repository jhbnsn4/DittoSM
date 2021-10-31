import { Component, OnInit } from '@angular/core';
import { Router,  ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';
import { IMyCustomMessage } from 'src/app/models/mycustommessage';
import { IUserAccount } from 'src/app/models/useraccount';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  targetId: number = 0;

  public targetUser: IUserAccount = {
    userId: 0,
    firstName: '',
    lastName: '',
    birthday: '',
    statusText: '',
    profilePicture: { imageId: 0, imageFile: '', postFK: null, profileFK: null },
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
    if (this.targetId) {
      let response = this.userService.getUserById(this.targetId).subscribe(
        (data: IUserAccount) => {
          this.targetUser = data;
          // ok to get user info
        }
        );
    }

  }

  onClickUpdatePassword() {

    // Update our user
    let response = this.userService.updatePassword(this.targetUser as IUserAccount).subscribe(
      (data: IMyCustomMessage) => {
        console.log(data)
        if(data.message==="Password Succefully Updated"){
          console.log(this.targetUser)
          // this.myRouter.navigate(['/login']);
        }
      }
    );
    
  }

}
