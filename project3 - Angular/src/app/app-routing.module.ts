import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterAccountComponent } from './components/register-account/register-account.component';

const routes: Routes = [
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterAccountComponent},
      {path: '**', redirectTo: 'login'}

];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }