import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IMyCustomMessage } from '../models/mycustommessage';
import { IUserAccount } from '../models/useraccount';

@Injectable({
  providedIn: 'root'
})
export class SessionAjaxService {

  private url=environment.dittoUrl;

  constructor(private myHttpCli: HttpClient) { }


  loginRequest(myUser: IUserAccount): Observable<IMyCustomMessage>{

    return this.myHttpCli.post<IMyCustomMessage>(`${this.url}/userAccount/login`, myUser,
      {withCredentials: true});
  }

  logoutRequest(): Observable<IMyCustomMessage> {

    return this.myHttpCli.post<IMyCustomMessage>(`${this.url}/userAccount/logout`, {},
      {withCredentials: true});

  }



}
