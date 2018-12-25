import { CookieService } from 'ngx-cookie-service';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

import * as jsonwebtoken from 'jsonwebtoken';
import { User } from './user';

import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'/*,'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW' )*/ })
};

@Injectable({
  providedIn: 'root',
})

export class AuthService {

  private authUrl = 'http://ec2-18-185-50-159.eu-central-1.compute.amazonaws.com:8080/engine-rest';
  private localUrl = 'http://localhost:8080/engine-rest';
  isLoggedin = false;
  currentUser: User;

  constructor(
    private http: HttpClient,
    private cookieService: CookieService
  ) {

    this.currentUser = new User();

  }

  login(username: string, password: string) {
    //var jwt = require('jsonwebtoken');          
    return this.http.post<any>(this.authUrl + '/identity/verify', { username: username, password: password }, httpOptions)
      .pipe(map(user => {
        if (user && user.authenticatedUser == username && user.authenticated) {
          this.currentUser.id = user.authenticatedUser;
          this.currentUser.authenticated = user.authenticated;
          //this.setUserdetails();
          //this.setGroups();
          this.setTestData();
          this.setToken();
          this.cookieService.set('userID', this.currentUser.id);
          this.cookieService.set('userRole', this.currentUser.role);
          /*this.cookieService.set('authUser', user.authenticatedUser +'|'
                                  + 'admin' + '|' + jsonwebtoken.sign({
                                                      user: user.authenticatedUser,
                                                      code: '31',
                                                      pw: 'DIP'
                                                    }, 'secret', { expiresIn: '1h' })
                                );*/
          this.isLoggedin = true;
          //this.cookieService.set( 'UserGroup', user.groups);
        }

        return this.currentUser;
      }));
  }

  logout() {
    this.cookieService.set('userID', 'NULL');
    this.cookieService.set('userRole', 'NULL');
    this.isLoggedin = false;
  }

  setTestData() {
    this.currentUser.firstName = 'Anton';
    this.currentUser.lastName = 'Adenauer';
    this.currentUser.email = 'a.adenauer@bauer.de';
    this.currentUser.role = 'Student';
  }


  private setUserdetails() {
    return this.http.get<any>(this.authUrl + '/user/' + this.currentUser.id + '/profile', httpOptions)
      .pipe(map(userdetail => {
        this.currentUser.firstName = userdetail.firstName;
        this.currentUser.lastName = userdetail.lastName;
        this.currentUser.email = userdetail.email;
      }));
  }

  private setGroups() {
    return this.http.get<any>(this.authUrl + '/identity/groups?userId=' + this.currentUser.id, httpOptions)
      .pipe(map(groupdetails => {
        this.currentUser.groups = groupdetails.groups;
      }));
  }

  private setToken() {
    this.currentUser.token = this.generateToken(
      this.currentUser.id,
      this.currentUser.role
    );
  }

  private generateToken(id: string, role: string) {
    return jsonwebtoken.sign({
      user: id,
      role: role,
      code: '31',
      pw: 'DIP'
    }, 'secret', { expiresIn: '1h' });
  }

  validateToken(): boolean {
    return this.currentUser.token === this.generateToken(this.cookieService.get('userID'),this.cookieService.get('userRole'));
  }


  /*
  {
              "authenticatedUser": "demo",
              "groups": null,
              "tenants": null,
              "authenticated": true
  }
  */


}
