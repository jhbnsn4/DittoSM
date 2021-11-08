import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { IPost } from 'src/app/models/post';
import { IPostResponse } from 'src/app/models/post-text';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  posts: IPost;
  postFormImages: string[] = [];
  postFormFiles: File[] = [];
  postForm: FormGroup = new FormGroup({
    postText: new FormControl('', [Validators.required, Validators.minLength(3)]),
    postFile: new FormControl(''),
    fileSource: new FormControl('')
  });

  constructor(private postServ: PostService) { }

  ngOnInit(): void {
  }

  onPostFileChange(event: Event) {
    let inputElement = event.target as HTMLInputElement;
    let numFiles = inputElement.files.length;

    // Is our file too large?
    let sizeOK = this.checkFileSize(inputElement, numFiles);
    if (!sizeOK) {
      // Zero out the vars tracking our form
      this.postForm.patchValue({ fileSource: [] });
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

      // Read and save the file
      let reader = new FileReader();
      reader.onload = (event) => {
        this.postFormImages.push(event.target.result as string);
      }
      // Trigger onload
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
    //                3MB
    if (totalSize > 3145728) {
      alert("File is too big!");
      // Clear file form (Sadly, removing only the most recent elemnt is impossible)
      inputElement.value = "";
      return false;
    }

    // File(s) was small enough
    return true;
  }


  // Add our new post
  onPostFormSubmit(event: Event) {

    // Filter out profanity
    this.postServ.profanityFilter(this.postForm.get("postText").value).subscribe(
      (data: IPostResponse) => {
        this.postForm.get("postText").setValue(data.result);

        // Send post to server
        this.sendPostForm();
      });

  }

  // Build form and send to server
  sendPostForm() {
    // Build our formData object
    const formData = new FormData();
    formData.append('postText', this.postForm.get('postText').value);

    // Only add this value if we have images
    if (this.postFormImages.length)
      formData.append('postFile', this.postForm.get('postFile').value);

    // Add all of our selected files under the SAME name (necessary for receiving endpoint)
    for (let i = 0; i < this.postFormImages.length; i++) {
      formData.append('fileSource', this.postFormFiles[i]);
    }

    // Send it out to the server
    // Hit different endpoints depending on whether we have image files or not
    this.postServ.addPost(formData, (this.postFormImages.length > 0)).subscribe(
      (response: HttpResponse<IPost>) => {
        this.postServ.triggerBehaveSubj('get list'); // updates post list
      }
    );

    // Empty form
    this.postForm.reset();
    this.postFormImages = [];
    this.postFormFiles = [];
  }

}
