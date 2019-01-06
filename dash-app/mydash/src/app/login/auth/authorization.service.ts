import { Injectable } from '@angular/core';
import { User } from '../user';
import * as jwt from 'jsonwebtoken';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  constructor(private authService: AuthService) {
  }

  currentUser: User = new User();

  isAuthorized(allowedRoles: string[]): boolean {

    // check if the list of allowed roles is empty, if empty, authorize the user to access the page
    if (allowedRoles == null || allowedRoles.length === 0) {
      return true;
    }

    // get token from session storage
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    const token = this.currentUser.authdata.roleAuth;


    try {

      var decodedToken = jwt.verify(token, 'DIP31');

      // check if it was decoded successfully, if not the token is not valid, deny access
      if (!decodedToken) {
        console.log('Invalid token');
        return false;
      }

      // check if the user roles is in the list of allowed roles, return true if allowed and false if not allowed
      return allowedRoles.includes(decodedToken['role']);

    } catch (err) {

      console.log(err);
      return false;

    }
  }

  public hasRole(allowedRoles: string[]): boolean {
    return allowedRoles.includes(this.authService.currentUser.role);
  } 
}
