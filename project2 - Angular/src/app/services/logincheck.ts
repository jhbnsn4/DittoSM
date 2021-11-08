import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { SessionAjaxService } from "./session-ajax.service";

@Injectable()
export class LoginCheck implements CanActivate {

    constructor(private router: Router, private sesService: SessionAjaxService) {

    }
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): 
    boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        // If user is not logged in
        if (localStorage.getItem("isLoggedIn") != "true") {
            // Reroute to login
            this.router.navigate(['login']);
        }
        
        return true;
    }
}