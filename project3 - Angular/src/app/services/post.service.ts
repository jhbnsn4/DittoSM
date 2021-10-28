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

  addPost(newPost: IPost): Observable<IPost> {
    return this.postHttpCli.post<IPost>(`${this.url}/posts/newPost/`, newPost , {withCredentials: true});
  }

  getPostsByUserId(userid:number): Observable<IPost[]>{
    console.log(userid + " this is from service");
    if (userid==0) {
      console.log("this is not supposed to happen unless....");
      return this.postHttpCli.get<IPost[]>(`${this.url}/posts/getPosts`, {withCredentials: true});
    } 
    console.log(`${this.url}/posts/getPosts/${userid}`);
    return this.postHttpCli.get<IPost[]>(`${this.url}/posts/getPosts/${userid}`, {withCredentials: true});
  }


}
