import { ValueConverter } from '@angular/compiler/src/render3/view/template';
import { Component, OnInit, Input, assertPlatform, Directive } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { BehaviorSubject, Subject } from 'rxjs';
import { IImageMap } from 'src/app/models/imagemap';
import { IMyCustomMessage } from 'src/app/models/mycustommessage';
import { IUserAccount } from 'src/app/models/useraccount';
import { IUserAccountPackaged } from 'src/app/models/useraccount.packaged';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';



@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  // When this is zero, target will be the user in the current session
  _targetId: number;
  actualId: number; 
  private _profileImage: string | ArrayBuffer | null = "";
  profileImageForm: FormGroup;
  @Input() toggleModal: string;

  public targetUser: IUserAccountPackaged = {
    userId: 0,
    firstName: '',
    lastName: '',
    birthday: '',
    statusText: '',
    profilePicture: '',
  };
  // Booleans for editing profile info & editing image
  public editing: boolean = false;
  public editingImage: boolean = false;
  public mouseOverProfile: boolean = false;
  public createCondition: boolean = false; 

  public editable: boolean;
  
  eventsSubject: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  
  constructor(private userService: UserService, private postService: PostService, 
    private formBuilder: FormBuilder, private sanitizer: DomSanitizer) { }
    
    ngOnInit(): void {
      // Set up profile image form
      this.profileImageForm = this.formBuilder.group({
        imageFile: ['']
      });
      
      //check for nav bar data.
      this.userService.currentMessage.subscribe(message => {
        this._targetId = message; 
        if(this._targetId==0){
          this._targetId=parseInt(localStorage.getItem("userId"));
        }
      });

      
      this.retrieveUserInformation();
      
      this.actualId= parseInt(localStorage.getItem("userId"));
      this.editable = (this.actualId==this._targetId);
      
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
    if (this._targetId > 0) {
      let response = this.userService.getUserById(this._targetId).subscribe(
        (data: IUserAccountPackaged) => {
          this.targetUser = data;
          this.eventsSubject.next(this.targetUser?.userId);
          // ok to get user info
          // set user's profile picture
          if (this.targetUser)
            this.retrieveProfilePicture(this.targetUser.userId);
        }
      );
    }
    // Otherwise, retrieve user from current session
    else {
      let response = this.userService.getCurrentUser().subscribe(
        (data: IUserAccountPackaged) => {
          // set user's profile information
          this.targetUser = data;
          this.eventsSubject.next(this.targetUser?.userId);

          // set user's profile picture
          if (this.targetUser)
            this.retrieveProfilePicture(this.targetUser.userId);
        }
      );
    }
    
  }
  
  retrieveProfilePicture(userId: number) {
    // Set our img element's src to our profile image's url
    this._profileImage = `${environment.dittoUrl}/users/getProfileImage?userId=${userId}`;

  }

  // Enable edit profile
  onClickEdit() {
    (document.getElementById("profileFieldset") as HTMLInputElement).disabled = this.editing;
    this.editing = !this.editing;
    this.retrieveUserInformation();
  }

  // Send updated profile to database
  onClickUpdateProfile() {
    // Act as if we toggled the edit button
    (document.getElementById("profileFieldset") as HTMLInputElement).disabled = this.editing;
    this.editing = !this.editing;
    
    // Update our user
    let updateResponse = this.userService.updateUser(this.targetUser as IUserAccount).subscribe(
      (data: IMyCustomMessage) => {
        // Reload posts (to show correct name)
        this.postService.triggerBehaveSubj('get list');

        // Update search bar
        this.userService.updateSearchBar();
      });

  }

  // Load an image from our file HTML element
  onImageLoad(event: Event) {
    let targetFile = (event.target as HTMLInputElement).files;

    // Set the image in our form
    this.profileImageForm.get('imageFile').setValue(targetFile[0]);

    // Read the image file
    if (targetFile && targetFile.length) {

      const reader = new FileReader();
      reader.readAsDataURL(targetFile[0]);
      reader.onload = (event) => {
        // Display the image in our img element
        this._profileImage = reader.result;
      }

    }
  }

  onProfileImageSubmit() {
    const formData = new FormData();
    formData.append('imageFile', this.profileImageForm.get('imageFile').value);

    // If we have an image to send 
    if (this.profileImageForm.get("imageFile").value) {
      this.userService.addProfilePicture(formData).subscribe(
        data => { }
      );
    }
    else {
      console.log("attempting to submit empty profile pic");
    }

    this.editingImage = false;

  }
  onProfileImageCancel() {
    this.editingImage = false;
    this.retrieveUserInformation();
  }

  // Toggle profile picture editing
  onProfilImageClick() {
      this.editingImage = !this.editingImage;
  }

  onProfileMouseOver(event: Event) {
    this.mouseOverProfile = true;
  }
  onProfileMouseOut() {
    this.mouseOverProfile = false;
  }

  onCreateBtnClick() {
    this.createCondition = !this.createCondition;
  }


}
