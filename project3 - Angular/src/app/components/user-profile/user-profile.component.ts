import { Component, OnInit } from '@angular/core';
import { IImageMap } from 'src/app/model/imagemap';
import { IUserAccount } from 'src/app/model/useraccount';
import { AjaxService } from 'src/app/service/ajax.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  private targetUser: IUserAccount | undefined;
  public editing: boolean = false;

  constructor(private ajaxService: AjaxService) { }

  ngOnInit(): void {


    let response = this.ajaxService.getUserById(1).subscribe(
      data => {
        this.targetUser = data;
        //console.log(this.firstName);
      }
    );
  }

  /////////// GETTERS & SETTERS
  get firstName() {
    return (this.targetUser) ? this.targetUser.firstName : "";
  }
  set firstName(firstName: string) {
    if (this.targetUser)
      this.targetUser.firstName = firstName;
  }
  get lastName() {
    return (this.targetUser) ? this.targetUser.lastName : "";
  }
  set lastName(lastName: string) {
    if (this.targetUser)
      this.targetUser.lastName = lastName;
  }
  get birthday() {
    return (this.targetUser) ? this.targetUser.birthday : new Date();
  }
  set birthday(birthday: Date) {
    if (this.targetUser) {
      this.targetUser.birthday = birthday;
      // console.log(this.targetUser.birthday.toDateString());
    }
  }
  parseDate(): String {
    return (this.targetUser) ? new Date(this.targetUser.birthday).toISOString().split('T')[0] : "";
  }
  get statusText() {
    return (this.targetUser) ? this.targetUser.statusText : "";
  }
  set statusText(statusText: string) {
    if (this.targetUser)
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
