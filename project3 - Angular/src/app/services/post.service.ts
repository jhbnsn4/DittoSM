import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
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
    if (!userid) {
      console.log(userid + " this is inside getpostbyuserid if stmt");
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

  //adding a like
  addLike(post: IPost): Observable<string> {
    console.log("updating like: ");
    const httpPost = {withCredentials: true, 'Content-Type': 'application/json'}

    return this.postHttpCli.put<string>(`http://localhost:9009/DittoSM/api/posts/addLike`, post, httpPost);
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
