import { ValueConverter } from '@angular/compiler/src/render3/view/template';
import { Component, OnInit, Input } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { IImageMap } from 'src/app/models/imagemap';
import { IUserAccount } from 'src/app/models/useraccount';
import { IUserAccountPackaged } from 'src/app/models/useraccount.packaged';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  // When this is zero, target will be the user in the current session
  _targetId: number = 0;
  private _profileImage: string | ArrayBuffer | null = "";

  public targetUser: IUserAccountPackaged = {
    userId: 0,
    firstName: '',
    lastName: '',
    birthday: '',
    statusText: '',
    profilePicture: { imageId: 0, imageFile: '', postFK: null, profileFK: null },
  };
  public editing: boolean = false;
  eventsSubject: BehaviorSubject<number> = new BehaviorSubject<number>(0);


  constructor(private userService: UserService, private postService: PostService) { }

  ngOnInit(): void {
    //check for nav bar data.
    this.userService.currentMessage.subscribe(message => this._targetId = message)

    this.retrieveUserInformation();
  }

  /////////// GETTERS & SETTERS
  // For posts, target Id is the user's id
  get targetId() {
    return this.targetUser ? this.targetUser.userId : 0;
  }

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

  // Event bound to birthday date form. Sets user's birthdate with the form's value.
  setBirthDate(event: Event) {
    if (this.targetUser) {
      this.targetUser.birthday = (event.target as HTMLInputElement).value;
    }
  }
  /* Bound to the birthday date form's value attribute. 
     Converts user's birthdate to an ISO string.
  */
  parseDate(): String {
    return (this.targetUser?.birthday) ?
      (new Date(this.targetUser.birthday)).toISOString().split('T')[0] : "";
  }
  get statusText() {
    return this.targetUser?.statusText;
  }
  set statusText(statusText: string) {
    this.targetUser.statusText = statusText;
  }

  get profileImage() {
    return this._profileImage;
  }

  //////////// OTHER METHODS

  // Retrieve user from database server 
  retrieveUserInformation() {
    // Retrieve by id if we were given one
    if (this._targetId) {
      let response = this.userService.getUserById(this._targetId).subscribe(
        (data: IUserAccountPackaged) => {
          this.targetUser = data;
          console.log(this.targetUser.userId + " inside retreieveUserinfo if stmt");
          this.eventsSubject.next(this.targetUser.userId);
          // ok to get user info
        }
      );
    }
    // Otherwise, retrieve user from current session
    else {
      let response = this.userService.getCurrentUser().subscribe(
        (data: IUserAccountPackaged) => {
          this.targetUser = data;
          console.log(this.targetUser.userId + " inside retreieveUserinfo else stmt");
          this.eventsSubject.next(this.targetUser.userId);

        }
      );
    }
  }

  // Enable edit profile
  onClickEdit() {
    (document.getElementById("profileFieldset") as HTMLInputElement).disabled = this.editing;
    this.editing = !this.editing;
  }

  // Send updated profile to database
  onClickUpdateProfile() {
    // Act as if we toggled the edit button
    this.onClickEdit();

    // Update our user
    let updateResponse = this.userService.updateUser(this.targetUser as IUserAccount).subscribe(
      (data: string) => {
        console.log("onclickupdate");
        this.postService.triggerBehaveSubj('get list');
      });

    // Add/Update our profile picture
    if (this._profileImage) {
      let imageResponse = this.userService.addProfilePicture(this._profileImage).subscribe(
        (data: string) => {
        });
    }
  }

  // Load an image from our file HTML element
  onImageLoad(event: Event) {
    let targetFile = (event.target as HTMLInputElement).files;
    if (targetFile && targetFile.length) {

      const reader = new FileReader();
      reader.readAsDataURL(targetFile[0]);
      reader.onload = (event)=>{
        this._profileImage = reader.result;
      }

    }
  }




}
