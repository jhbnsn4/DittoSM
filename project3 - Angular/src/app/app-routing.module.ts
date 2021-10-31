import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GlobalFeedComponent } from './components/global-feed/global-feed.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterAccountComponent } from './components/register-account/register-account.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { PostsComponent } from './components/posts/posts.component';
import { PasswordLandingComponent } from './components/password-landing/password-landing.component';
import { PasswordResetComponent } from './components/password-reset/password-reset.component';

const routes: Routes = [
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterAccountComponent},
      {path: 'profile', component: UserProfileComponent},
      {path: 'global', component: GlobalFeedComponent},
      {path: 'post', component: PostsComponent},
      {path: 'passlanding', component: PasswordLandingComponent},
      {path: 'reset/:id', component: PasswordResetComponent},
      {path: '**', redirectTo: 'login'}

];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
