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
    if (userid === undefined) {
      return this.postHttpCli.get<IPost[]>(`${this.url}/posts/getPosts`, {withCredentials: true});
    } 
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
    const httpPost = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      withCredentials: true
    };

    return this.postHttpCli.put<string>(`${this.url}/posts/addLike`, post, httpPost);
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
