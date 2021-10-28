import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterAccountComponent } from './components/register-account/register-account.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { UserService } from './services/user.service';
import { FeedComponent } from './components/feed/feed.component';
import { PostsComponent } from './components/posts/posts.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { GlobalFeedComponent } from './components/global-feed/global-feed.component';
import { PostService } from './services/post.service';
import { NgSelectModule } from '@ng-select/ng-select';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterAccountComponent,
    UserProfileComponent,
    FeedComponent,
    PostsComponent,
    CreatePostComponent,
    GlobalFeedComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
    HttpClientModule,
    NgSelectModule,
    
  ],
  providers: [UserService, PostService, PostsComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
