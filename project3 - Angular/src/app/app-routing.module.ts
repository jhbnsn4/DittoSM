import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GlobalFeedComponent } from './components/global-feed/global-feed.component';
import { ProfileComponent } from './components/profile/profile.component';

const routes: Routes = [
  {path: 'global', component: GlobalFeedComponent},
  {path: 'profile', component: ProfileComponent},
  {path: '', redirectTo: 'global', pathMatch: 'full'},
  {path: '**', redirectTo: 'global'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
