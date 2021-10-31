import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IPost } from '../models/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private url=environment.dittoUrl;

  _posts: IPost[] = [];  
  private myBehavioralSubject = new BehaviorSubject<string>('');
  private castMyBehaviorSubjectToObservable = this.myBehavioralSubject.asObservable(); //used to subscribe

  
  constructor(private postHttpCli: HttpClient) { }

  addPost_old(newPost: IPost): Observable<IPost> {
    console.log("newpost value:", newPost);
    return this.postHttpCli.post<IPost>(`${this.url}/posts/newPost/`, newPost , {withCredentials: true});
  }

  addPost(postForm: FormData, withImages:boolean): Observable<HttpResponse<object>> {

    // Hit endpoint that only receives text
    if (!withImages) {
      return this.postHttpCli.post(`${this.url}/posts/newPost`, postForm, 
      {observe: 'response', responseType: 'json', withCredentials: true});
    }
    // Hit endpoint that receives text and images
    else
      return this.postHttpCli.post(`${this.url}/posts/newPostWithImages`, postForm,
      {observe: 'response', responseType: 'json', withCredentials: true});
  }

  getPostsByUserId(userid:number): Observable<IPost[]>{
    console.log(userid + " this is from service");
    if (userid==0 || undefined) {
      console.log(userid + " this is inside getpostbyuserid if stmt");
      return this.postHttpCli.get<IPost[]>(`${this.url}/posts/getPosts`, {withCredentials: true});
    } 
    console.log(`${this.url}/posts/getPosts/${userid}`);
    return this.postHttpCli.get<IPost[]>(`${this.url}/posts/getPosts/${userid}`, {withCredentials: true});
  }

  triggerBehaveSubj(newPostList: string){
    this.myBehavioralSubject.next(newPostList);
  }
 
  get theOberv(){
    return this.castMyBehaviorSubjectToObservable;
  }

  set posts(_posts: IPost[]) {
    this._posts = _posts;
  }

  get posts() {
    return this._posts;
  }


}
