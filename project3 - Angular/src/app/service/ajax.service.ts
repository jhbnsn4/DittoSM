import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { IUserAccount } from "../model/useraccount";

@Injectable({
    providedIn: 'root'
})
export class AjaxService {
    
    constructor(private myHttpCli:HttpClient) {

    }

    getUserById(id: number): Observable<IUserAccount> {
        return this.myHttpCli.get<IUserAccount>(`http://localhost:9015/DittoSM/api/users/getUserById?id=${id}`);
    }
}