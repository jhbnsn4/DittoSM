import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IMyCustomMessage } from '../models/mycustommessage';
import { IUserAccount } from '../models/useraccount';

@Injectable({
  providedIn: 'root'
})
export class SessionAjaxService {

  constructor(private myHttpCli: HttpClient) { }

  loginRequest(myUser: IUserAccount): Observable<IMyCustomMessage>{

    return this.myHttpCli.post<IMyCustomMessage>('http://localhost:9015/DittoSM/api/userAccount/login', myUser,
      {withCredentials: true});
  }

  logoutRequest(): Observable<IMyCustomMessage> {

    return this.myHttpCli.post<IMyCustomMessage>('http://localhost:9015/DittoSM/api/userAccount/logout', {},
      {withCredentials: true});

  }



}
