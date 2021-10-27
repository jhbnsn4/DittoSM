import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { IUserAccount } from "../models/useraccount";

@Injectable({
    providedIn: 'root'
})
export class AjaxService {
    
    constructor(private myHttpCli:HttpClient) {

    }

    getUserById(id: number): Observable<IUserAccount> {
        return this.myHttpCli.get<IUserAccount>(`http://localhost:9015/DittoSM/api/users/getUserById?id=${id}`);
    }

    updateUser(user: IUserAccount): Observable<string> {
        console.log("updating user: " + user.username);
        const httpPost = {
          headers: new HttpHeaders({
            'Content-Type':  'application/json',
          })
        };
    
        return this.myHttpCli.put<string>('http://localhost:9015/DittoSM/api/users/updateUser', 
        user, httpPost);
      }
}