import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IPost } from '../models/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private url=environment.dittoUrl;

  constructor(private postHttpCli: HttpClient) { }

  addPost(newPost: IPost, userid: number): Observable<string> {
    // const httpPost  = {
    //   headers: new HttpHeaders({
    //     'Content-Type': 'application/json'
    //   })
    // }

    console.log(newPost);

    return this.postHttpCli.post<string>(`${this.url}/posts/newPost/${userid}`, newPost);
    // return this.postHttpCli.post<string>(`localhost:9009/DittoSM/api/posts/newPost/${userid}`, newPost, httpPost);

  }

  getAllPosts(): Observable<IPost[]> {
    return this.postHttpCli.get<IPost[]>(`${this.url}/posts/getAllPosts`, {withCredentials: true});
  }

  getPostsByUserId(userid:number): Observable<IPost[]> {
    return this.postHttpCli.get<IPost[]>(`${this.url}/posts/getPosts/${userid}`, {withCredentials: true});
  }

}