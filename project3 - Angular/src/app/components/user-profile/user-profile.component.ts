import { Component, OnInit } from '@angular/core';
import { IImageMap } from 'src/app/models/imagemap';
import { IUserAccount } from 'src/app/models/useraccount';
import { AjaxService } from 'src/app/services/ajax.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  private targetUser: IUserAccount = {
    userId: 0,
    username: '',
    password: '',
    userEmail: '',
    firstName: '',
    lastName: '',
    birthday: new Date(),
    statusText: '',
    profilePicture: {imageId: 0, imageFile: '', postFK: null, profileFK: null},
    postList: [],
    dittoFollowerList: [],
    dittoFollowingList: []
  };
  public editing: boolean = false;

  constructor(private ajaxService: AjaxService) { }

  ngOnInit(): void {

    // Retrieve user from database server (hardcoded for now)
    let response = this.ajaxService.getUserById(1).subscribe(
      data => {
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
      this.targetUser.birthday = new Date((event.target as HTMLInputElement).value);
    }
  }
  parseDate(): String {
    return new Date(this.targetUser.birthday).toISOString().split('T')[0];
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
    let response = this.ajaxService.updateUser(this.targetUser as IUserAccount).subscribe(
      data => {
        console.log("update data: " + data);
      }
    );
  }

  


}
