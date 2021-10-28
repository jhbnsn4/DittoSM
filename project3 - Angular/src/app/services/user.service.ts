import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IUserAccount } from "../models/useraccount";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url=environment.dittoUrl;

  constructor(private myHttpCli: HttpClient) {

  }

  // GET USER BY ID
  getUserById(id: number): Observable<IUserAccount> {
    return this.myHttpCli.get<IUserAccount>(`${this.url}/users/getUserById?id=${id}`);
  }

  // UPDATE USER
  updateUser(user: IUserAccount): Observable<string> {
    console.log("updating user: " + user.username);
    const httpPost = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    };

    return this.myHttpCli.put<string>(`${this.url}/api/users/updateUser`,
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

    console.log(newUser);

    return this.myHttpCli.post<string>(`${this.url}/users/addUser`, newUser, httpPost)

  }
}