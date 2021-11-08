import { Component, OnInit } from '@angular/core';
import { IUserAccount } from './models/useraccount';
import { UserService } from './services/user.service';
import { Router, Event as RouterEvent, NavigationStart, NavigationEnd } from '@angular/router';
import { SessionAjaxService } from './services/session-ajax.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Ditto Social Media';
  hideDiv: number;
  showOverlay = true;
  filterTerm!: string

  constructor(private currentUserService: UserService, private sesService: SessionAjaxService, private router: Router) {
    router.events.subscribe((event: RouterEvent) => {
      this.navigationInterceptor(event)
    })
   }
  navigationInterceptor(event: RouterEvent): void {
    if (event instanceof NavigationStart) {
      this.showOverlay = true;
    }
    if (event instanceof NavigationEnd) {
      this.showOverlay = false;
    }
    
  }

  message: number;
  items: IUserAccount[] = [];
  selected = [];
  load: number;


  ngOnInit(): void {
    this.getUsers();
    this.currentUserService.currentMessage.subscribe(message => this.message = message);
    this.currentUserService.searchUpdateObs.subscribe(()=>{this.getUsers()});
  }

  logout() {
    this.currentUserService.changeMessage(0);
    localStorage.setItem("isLoggedIn", "false");
    localStorage.removeItem("userId");
    this.router.navigateByUrl('/login');
  }

  changeFn(val: any) {
    this.selected = null;
    this.currentUserService.changeMessage(val.userId);
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigateByUrl('/profile')
  }


  profileRoute() {
    this.currentUserService.changeMessage(0);
    this.router.navigateByUrl('/profile');
  }

  globalRoute() {
    this.router.navigateByUrl('/global');
  }

  getUsers(): void {
    this.currentUserService.allUsersRequest()
      .subscribe(data => this.items = data);
  }

  clickProfile() {
    // Send message setting profile id to 0
    this.currentUserService.changeMessage(0);
  }

  checkLoggedIn(): boolean {
    return (localStorage.getItem("isLoggedIn") == "true");
  }

  customSearchFn(term: string, item: any) {
    term = term.toLowerCase();

    // Creating and array of space saperated term and removinf the empty values using filter
    let splitTerm = term.split(' ').filter(t => t);

    let isWordThere = [];

    // Pushing True/False if match is found
    splitTerm.forEach(arr_term => {
    let mySearchString = item['firstName'] + item['lastName'];
  
    let search = mySearchString.toLowerCase();
    isWordThere.push(search.indexOf(arr_term) != -1);
    

    });

    const all_words = (this_word) => this_word;
    // Every method will return true if all values are true in isWordThere.
    return isWordThere.every(all_words);
  }


}
