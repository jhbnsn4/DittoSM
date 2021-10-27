import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http'
import { IUserAccount } from '../models/useraccount';

@Injectable({
  providedIn: 'root'
})
export class UserAjaxService {

  constructor(private myHttpCli: HttpClient) {}

  //LOGIN COMPONENT
  userRequest(primaryKey: number): Observable<IUserAccount>{
    //retrieves the user information from the database
    return this.myHttpCli.get<IUserAccount>(`http://localhost:9015/DittoSM/api/users/getUserById?id={primaryKey}`);
  }

  //GET ALL USERS
  allUsersRequest(): Observable<IUserAccount[]>{
    return this.myHttpCli.get<IUserAccount[]>(`http://localhost:9015/DittoSM/api/users/getAllUsers`);
  }


  //REGISTER USER COMPONENT
  addUserRequest(newUser: IUserAccount): Observable<string>{
    const httpPost = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    console.log(newUser);

    return this.myHttpCli.post<string>(`http://localhost:9015/DittoSM/api/users/addUser`, newUser, httpPost)

  }

}
