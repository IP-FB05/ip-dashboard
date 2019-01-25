import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivateChild } from '@angular/router';
import { Observable } from 'rxjs';

// Import Models
// Import Components

// Import Services
/*import { AuthorizationService } from './authorization.service';


@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate, CanActivateChild {

    constructor(private authorizationService: AuthorizationService, private router: Router) { }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        const allowedRoles = next.data.allowedRoles;
        const isAuthorized = this.authorizationService.isAuthorized(allowedRoles);

        if (!isAuthorized) {
            this.router.navigate(['login']);
        }

        return isAuthorized;
    }

    canActivateChild(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        const allowedRoles = next.data.allowedRoles;
        const isAuthorized = this.authorizationService.isAuthorized(allowedRoles);

        if (!isAuthorized) {
            this.router.navigate(['login']);
        }

        return isAuthorized
    }
}*/