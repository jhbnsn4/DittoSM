import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IImageMap } from "../models/imagemap";
import { IMyCustomMessage } from "../models/mycustommessage";
import { IPost } from "../models/post";
import { IUserAccount } from "../models/useraccount";
import { IUserAccountPackaged } from "../models/useraccount.packaged";



@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url = environment.dittoUrl;

  private messageSource = new BehaviorSubject<number>(0);
  currentMessage = this.messageSource.asObservable();

  // BehaviorSubject to update the search bar
  private searchUpdateSource = new BehaviorSubject<any>('');
  searchUpdateObs = this.searchUpdateSource.asObservable();

  constructor(private myHttpCli: HttpClient) {

  }

  // Calls next on behavior to change value
  changeMessage(message: number) {
    this.messageSource.next(message)
  }

  // Calls update to search bar
  updateSearchBar() {
    this.searchUpdateSource.next('');
  }

  // GET USER BY ID
  getUserById(id: number): Observable<IUserAccountPackaged> {
    return this.myHttpCli.get<IUserAccountPackaged>(`${this.url}/users/getUserById?id=${id}`, { withCredentials: true });
  }

  getUserByIdPassword(id: number): Observable<IUserAccount> {
    return this.myHttpCli.get<IUserAccount>(`${this.url}/users/getUserByIdPassword?id=${id}`, { withCredentials: true });
  }

  // GET CURRENT USER
  getCurrentUser(): Observable<IUserAccountPackaged> {
    return this.myHttpCli.get<IUserAccountPackaged>(`${this.url}/users/getCurrentUser`, { withCredentials: true });
  }

  // GET USER ID BY USERNAME
  getUserIdByUsername(username: number): Observable<number> {
    return this.myHttpCli.get<number>(`${this.url}/users/getUserId?username=${username}`, { withCredentials: true });
  }

  //SEND EMAIL
  postResetPassword(userEmail: string): Observable<IMyCustomMessage> {
    return this.myHttpCli.post<IMyCustomMessage>(`${this.url}/users/resetPassword`, userEmail, {});
  }

  // UPDATE USER
  updateUser(user: IUserAccountPackaged): Observable<IMyCustomMessage> {
    const httpPost = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      withCredentials: true
    };

    return this.myHttpCli.put<IMyCustomMessage>(`${this.url}/users/updateUser`,
      user, httpPost);

  }

  //UPDATE PASSWORD
  updatePassword(user: IUserAccount): Observable<IMyCustomMessage> {
    const httpPost = { withCredentials: true, 'Content-Type': 'application/json' }

    return this.myHttpCli.put<IMyCustomMessage>(`${this.url}/users/updateUserPassword`,
      user, httpPost);
  }

  //GET ALL USERS
  allUsersRequest(): Observable<IUserAccount[]> {
    return this.myHttpCli.get<IUserAccount[]>(`${this.url}/users/getAllUsers`, { withCredentials: true });
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
  addProfilePicture(profileForm: FormData): Observable<string> {

    return this.myHttpCli.post<string>(`${this.url}/users/addProfilePicture`, profileForm, { withCredentials: true });
  }

  // GET PROFILE PICTURE
  getProfilePicture(userId: number): Observable<Blob> {
    //${userId}
    return this.myHttpCli.get(`${this.url}/users/getProfileImage?userId=${userId}`, { withCredentials: true, responseType: 'blob' });
  }
}