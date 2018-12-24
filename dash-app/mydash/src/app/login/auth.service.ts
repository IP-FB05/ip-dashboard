import { CookieService } from 'ngx-cookie-service';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

import { HttpClient, HttpHeaders } from '@angular/common/http';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'/*,'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW' )*/})
};

@Injectable({
  providedIn: 'root',
})

export class AuthService {
  
  private authUrl = 'http://ec2-18-185-50-159.eu-central-1.compute.amazonaws.com:8080/engine-rest/identity/verify';
  private localUrl = 'http://localhost:8080/engine-rest/identity/verify';
  isLoggedin = false;

  constructor (
    private http: HttpClient,
    private cookieService: CookieService
  ){ } 

  login(username: string, password: string) {
      return this.http.post<any>(this.authUrl, { username: username, password: password }, httpOptions)
          .pipe(map(user => {
              if (user && user.authenticatedUser == username && user.authenticated) {
                this.cookieService.set( 'UserName', user.authenticatedUser);
                this.isLoggedin = true;
                //this.cookieService.set( 'UserGroup', user.groups);
              }

              return user;
          }));
  }

  logout() {
      this.cookieService.set('UserName', 'NULL');
      this.isLoggedin = false;  
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
