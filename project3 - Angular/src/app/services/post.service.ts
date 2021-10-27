import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IPost } from '../models/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private postHttpCli: HttpClient) { }

  addPost(newPost: IPost, userid: number): Observable<string> {
    const httpPost  = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }

    console.log(newPost);

    return this.postHttpCli.post<string>(`localhost:9009/DittoSM/api/posts/newPost/${userid}`, newPost, httpPost);

  }

  getAllPosts(): Observable<IPost[]> {
    return this.postHttpCli.get<IPost[]>(`localhost:9009/DittoSM/api/posts/getAllPosts`, {withCredentials: true});
  }

}
