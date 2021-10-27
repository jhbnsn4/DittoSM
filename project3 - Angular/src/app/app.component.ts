import { Component } from '@angular/core';
import { UserAjaxServiceService } from './user-ajax-service.service';
import { IUserAccount } from './user';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
 
  constructor(private UserAjaxServiceService: UserAjaxServiceService) { }

  filterTerm!: string 

  items:  IUserAccount[] =[];
  selected = [
  
  ];

  searchUserInput(){
    console.log("logout")
  }
   
  getUsers(): void {
    this.UserAjaxServiceService.fetchUsers()
    .subscribe(data => this.items = data);
  }



}