import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { IUserAccount } from './user';


@Injectable({
  providedIn: 'root'
})
export class UserAjaxServiceService {

  constructor(private myHttpCli: HttpClient) { }


  
  fetchUsers(): Observable<IUserAccount[]> {
    return this.myHttpCli.get<IUserAccount[]>(`http://localhost:4200/DittoSM/api/users/getAllUsers`);
  }
 
}
