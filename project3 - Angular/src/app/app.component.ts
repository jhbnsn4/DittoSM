import { Component } from '@angular/core';
import { IUserAccount } from './models/useraccount';
import { UserService } from './services/user.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Ditto Social Media';
  
 
  constructor(private currentUserService: UserService, private router: Router) { }

  filterTerm!: string 

  items:  IUserAccount[] =[];
  selected = [];
  ngOnInit(): void {
    this.getUsers();
    
  }
  logout(){
    console.log("logout")
    
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
