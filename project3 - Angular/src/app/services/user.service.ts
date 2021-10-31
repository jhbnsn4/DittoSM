import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IImageMap } from "../models/imagemap";
import { ITestImageContainer } from "../models/testImageContainer";
import { IMyCustomMessage } from "../models/mycustommessage";
import { IUserAccount } from "../models/useraccount";
import { IUserAccountPackaged } from "../models/useraccount.packaged";



@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url=environment.dittoUrl;

  private messageSource = new BehaviorSubject<number>(0);
  currentMessage = this.messageSource.asObservable();
 
  constructor(private myHttpCli: HttpClient) {

  }

 // Calls next on behavior to change value
changeMessage(message: number){
this.messageSource.next(message)

}

  // GET USER BY ID
  getUserById(id: number): Observable<IUserAccountPackaged> {
    return this.myHttpCli.get<IUserAccountPackaged>(`${this.url}/users/getUserById?id=${id}`);
  }

  // GET CURRENT USER
  getCurrentUser(): Observable<IUserAccountPackaged> {
    return this.myHttpCli.get<IUserAccountPackaged>(`${this.url}/users/getCurrentUser`, {withCredentials: true});
  }

  // GET USER ID BY USERNAME
  getUserIdByUsername(username: number): Observable<number> {
    return this.myHttpCli.get<number>(`${this.url}/users/getUserId?username=${username}`);
  }

  //SEND EMAIL
  postResetPassword(userEmail: string): Observable<IMyCustomMessage>{
    return this.myHttpCli.post<IMyCustomMessage>(`${this.url}/users/resetPassword`, userEmail, {});
  }

  // UPDATE USER
  updateUser(user: IUserAccountPackaged): Observable<IMyCustomMessage> {
    console.log("updating user: " + user.firstName);
    const httpPost = {withCredentials: true, 'Content-Type': 'application/json'}

    return this.myHttpCli.put<IMyCustomMessage>(`${this.url}/users/updateUser`,
      user, httpPost);
      
  }

  //UPDATE PASSWORD
  updatePassword(user: IUserAccount): Observable<IMyCustomMessage> {
    console.log("updating user: " + user.firstName);
    const httpPost = {withCredentials: true, 'Content-Type': 'application/json'}

    return this.myHttpCli.put<IMyCustomMessage>(`${this.url}/users/updateUserPassword`,
      user, httpPost);
  }

  //LOGIN COMPONENT
  userRequest(primaryKey: number): Observable<IUserAccount> {
    //retrieves the user information from the database
    return this.myHttpCli.get<IUserAccount>(`${this.url}/users/getUserById?id={primaryKey}`);
  }

  //GET ALL USERS
  allUsersRequest(): Observable<IUserAccount[]> {
    return this.myHttpCli.get<IUserAccount[]>(`${this.url}/users/getAllUsers`);
  }


  //REGISTER USER COMPONENT
  addUserRequest(newUser: IUserAccount): Observable<string> {
    const httpPost = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    return this.myHttpCli.post<string>(`${this.url}/users/addUser`, newUser, httpPost)

  }

  // ADD/UPDATE PROFILE PICTURE
  addProfilePicture(profileImage: string | ArrayBuffer | null): Observable<string> {
    let image: ITestImageContainer = {imageId: 0, imageFile: profileImage as string};

    // let image: IImageMap = {imageId: 0, imageFile: profileImage, postFK: null, profileFK: null}
    // const httpPost = {withCredentials: true, 'Content-Type': 'application/json'}
    // const httpPost = { headers: new HttpHeaders({'Content-Type': 'application/json'})};

    console.log("sending image");
    return this.myHttpCli.post<string>(`${this.url}/users/addProfilePicture`, image);
  }
}