import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { PasswordLandingComponent } from './components/password-landing/password-landing.component';
import { PasswordResetComponent } from './components/password-reset/password-reset.component';
import { LoginCheck } from './services/logincheck';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';


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
    PasswordLandingComponent,
    PasswordResetComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
    ReactiveFormsModule,
    HttpClientModule,
    NgSelectModule,
  ],
  providers: [UserService, PostService, PostsComponent, LoginCheck, {provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
