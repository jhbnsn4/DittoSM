import { Component, OnInit } from '@angular/core';
import { IUserAccount } from './models/useraccount';
import { UserService } from './services/user.service';
import { Router } from '@angular/router';
import { SessionAjaxService } from './services/session-ajax.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Ditto Social Media';


  filterTerm!: string 
  
  
  
 
  constructor(private currentUserService: UserService, private router: Router) { }

  message: number;
  items:  IUserAccount[] =[];
  selected: number;


  ngOnInit(): void {
    this.getUsers();
    this.currentUserService.currentMessage.subscribe(message => this.message = message)
    
  }

  logout(){
    this.router.navigateByUrl('/login');
  }
  
  changeFn(val: any) {
    this.selected = null;
    console.log("Dropdown selection:", val.userId);
    this.currentUserService.changeMessage(val.userId);
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigateByUrl('/profile')
}
clearFn(){
  this.selected = null;
console.log("clear")
}
profileLoad(){
  this.router.navigateByUrl('/profile');

}
  profileRoute(){
    this.router.navigateByUrl('/profile');
    }

    globalRoute(){
      this.router.navigateByUrl('/global');
      }
  
  getUsers(): void {
    this.currentUserService.allUsersRequest()
    .subscribe(data => this.items = data);
    console.log("items: ")
    console.log(this.items);
  }
  

}
