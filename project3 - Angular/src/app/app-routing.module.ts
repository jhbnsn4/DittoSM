import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterAccountComponent } from './components/register-account/register-account.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';

const routes: Routes = [
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterAccountComponent},
      {path: 'profile', component: UserProfileComponent},

];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }