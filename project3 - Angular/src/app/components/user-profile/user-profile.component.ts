import { ValueConverter } from '@angular/compiler/src/render3/view/template';
import { Component, OnInit, Input, assertPlatform } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { BehaviorSubject, Subject } from 'rxjs';
import { IImageMap } from 'src/app/models/imagemap';
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
  _targetId: number = 0;
  private _profileImage: string | ArrayBuffer | null = "";
  profileImageForm: FormGroup;

  // test multi-file & text
  postFormImages: string[] = [];
  postFormFiles: File[] = [];
  postForm: FormGroup = new FormGroup({
    postText: new FormControl('', [Validators.required, Validators.minLength(3)]),
    postFile: new FormControl(''),
    fileSource: new FormControl('')
  });

  onTestPostFileChange(event:Event) {
    let inputElement = event.target as HTMLInputElement;
    let numFiles = inputElement.files.length;

    // Is our file too large?
    let sizeOK = this.checkFileSize(inputElement, numFiles);
    if (!sizeOK) {
      // Zero out the vars tracking our form
      this.postForm.patchValue({fileSource: []});
      this.postFormImages = [];
      this.postFormFiles = [];
      return;
    }

    // Remember add these to our list of selected files
    for (let i = 0; i < numFiles; i++) {
      this.postFormFiles.push(inputElement.files[i]);
    }
    for (let i = 0; i < numFiles; i++) {
      this.postForm.patchValue({
        fileSource: inputElement.files
      });

      let reader = new FileReader();
      reader.onload = (event) => {
        this.postFormImages.push(event.target.result as string);
        
        console.log("patching value");
      }
      reader.readAsDataURL(inputElement.files[i]);
    }
  }

  // Prevent user from uploading file(s) above a certain size
  checkFileSize(inputElement: HTMLInputElement, numFiles: number): boolean {
    // Count the total size of all files
    let totalSize = 0;

    // Count new files
    for (let i = 0; i < numFiles; i++) {
      totalSize += inputElement.files[i].size;
    }
    // Count other selected files
    for (let i = 0; i < this.postFormFiles.length; i++) {
      totalSize += this.postFormFiles[i].size;
    }
    console.log("size:",totalSize);
    //                3MB
    if (totalSize > 3145728) {
      alert("File is too big!");
      // Clear file form (Sadly, removing only the most recent elemnt is impossible)
      inputElement.value = "";
      return false;
    }

    return true;
  }

  onTestPostSubmit() {
    console.log("submitted test form");
    console.log(this.postForm.value);

    const formData = new FormData();
    formData.append('postText', this.postForm.get('postText').value);
    formData.append('postFile', this.postForm.get('postFile').value);

    // Add all of our selected files under the SAME name (necessary for receiving server)
    for (let i = 0; i < this.postFormImages.length; i++) {
      // old way: 'fileSource', this.postForm.get('fileSource').value[i]
      formData.append('fileSource', this.postFormFiles[i]);
    }
    this.userService.testSendMultipleImages(formData).subscribe(
      data => {}
    );
  }

  public targetUser: IUserAccountPackaged = {
    userId: 0,
    firstName: '',
    lastName: '',
    birthday: '',
    statusText: '',
    profilePicture: '',
  };
  public editing: boolean = false;
  eventsSubject: BehaviorSubject<number> = new BehaviorSubject<number>(0);



  constructor(private userService: UserService, private postService: PostService, 
    private formBuilder: FormBuilder, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {

    // Set up profile image form
    this.profileImageForm = this.formBuilder.group({
      imageFile: ['']
    });

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
          // set user's profile picture
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
          console.log(this.targetUser.userId + " inside retreieveUserinfo else stmt");
          this.eventsSubject.next(this.targetUser.userId);

          // set user's profile picture
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

    // If we have an image to send (if formData != {})
    if (!(Object.keys(formData).length === 0)) {
      this.userService.addProfilePicture(formData).subscribe(
        data => { console.log("image stored") }
      );

    }
  }


}
