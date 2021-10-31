import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IPost } from '../models/post';
import { IPostResponse } from '../models/post-text';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private url=environment.dittoUrl;

  _posts: IPost[] = [];
  
  private myBehavioralSubject = new BehaviorSubject<string>('');
  private castMyBehaviorSubjectToObservable = this.myBehavioralSubject.asObservable(); //used to subscribe

  
  constructor(private postHttpCli: HttpClient) { }

  addPost(newPost: IPost): Observable<IPost> {
    return this.postHttpCli.post<IPost>(`${this.url}/posts/newPost/`, newPost , {withCredentials: true});
  }

  getPostsByUserId(userid:number): Observable<IPost[]>{
    // console.log(userid + " this is from service");
    if (userid==0) {
      // console.log("this is not supposed to happen unless....");
      return this.postHttpCli.get<IPost[]>(`${this.url}/posts/getPosts`, {withCredentials: true});
    } 
    // console.log(`${this.url}/posts/getPosts/${userid}`);
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

  
  profanityFilter(message:string){
    let myList: string='fridge';
    let httpOptions= { 
      headers: new HttpHeaders({ 
      'Content-Type': 'text/plain',
      })

      , params: new HttpParams().set('text', message)
                              .set('add', myList)

    };
    return this.postHttpCli.get<IPostResponse>("https://www.purgomalum.com/service/json", httpOptions)
  }

}
