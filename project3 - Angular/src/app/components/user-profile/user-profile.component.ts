import { ValueConverter } from '@angular/compiler/src/render3/view/template';
import { Component, OnInit, Input } from '@angular/core';
import { IImageMap } from 'src/app/models/imagemap';
import { IUserAccount } from 'src/app/models/useraccount';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

 
  private targetId: number = 1;
  

  public targetUser: IUserAccount = {
    userId: 0,
    username: '',
    password: '',
    userEmail: '',
    firstName: '',
    lastName: '',
    birthday: '',
    statusText: '',
    profilePicture: {imageId: 0, imageFile: '', postFK: null, profileFK: null},
    postList: [],
    dittoFollowerList: [],
    dittoFollowingList: []
  };
  public editing: boolean = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    //check for nav bar data.
    this.userService.currentMessage.subscribe(message => this.targetId = message)

    // Retrieve user from database server (hardcoded for now)
    let response = this.userService.getUserById(this.targetId).subscribe(
      (data: IUserAccount) => {
        this.targetUser = data;
      }
    );
 
  }

  /////////// GETTERS & SETTERS
  get firstName() {
    return this.targetUser.firstName;
  }
  set firstName(firstName: string) {
      this.targetUser.firstName = firstName;
  }
  get lastName() {
    return this.targetUser.lastName;
  }
  set lastName(lastName: string) {
      this.targetUser.lastName = lastName;
  }

  setBirthDate(event: Event) {
    if (this.targetUser) {
      this.targetUser.birthday = (event.target as HTMLInputElement).value;
    }
  }
  parseDate(): String {
    console.log(parseInt(this.targetUser.birthday));
    return new Date(parseInt(this.targetUser.birthday)).toISOString().split('T')[0];
  }
  get statusText() {
    return this.targetUser.statusText;
  }
  set statusText(statusText: string) {
      this.targetUser.statusText = statusText;
  }
  // TOOD: Add getter/setter for profile picture

  //////////// OTHER METHODS

  onClickEdit(event: Event) {
    (document.getElementById("profileFieldset") as HTMLInputElement).disabled = this.editing;
    this.editing = !this.editing;
  }

  onClickUpdateProfile() {
    let response = this.userService.updateUser(this.targetUser as IUserAccount).subscribe(
      (data: string) => {
        console.log("update data: " + data);
      }
    );
  }

  


}
