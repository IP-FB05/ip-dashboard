import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from '../user';
import { Group } from '../group';

const httpOptions = {
    //headers: new HttpHeaders({ 'Content-Type': 'application/json'/*,'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW' )*/ })
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class AuthService {

    private authUrl = 'http://ec2-18-185-50-159.eu-central-1.compute.amazonaws.com:8080/engine-rest';
    //private localUrl = 'http://localhost:8080/engine-rest';

    isLoggedin = false;
    currentUser: User = new User();

    constructor(
        private http: HttpClient,
        private cookieService: CookieService
    ) { }

    login(username: string, password: string) {
        return this.http.post<any>(this.authUrl + '/identity/verify', { username: username, password: password }, httpOptions)
            .pipe(map(user => {
                // login successful if there's a user in the response
                if (user && user.authenticatedUser == username && user.authenticated) {

                    this.currentUser.id = user.authenticatedUser;
                    this.currentUser.authenticated = user.authenticated;

                    return this.http.get<any>(this.authUrl + '/identity/groups?userId=' + this.currentUser.id, httpOptions)
                        .subscribe(data => {
                            this.currentUser.groups = data.groups;
                            console.log('User groups successfully fetched: ' + JSON.stringify(this.currentUser.groups));
                            return this.http.get<any>(this.authUrl + '/user/' + this.currentUser.id + '/profile', httpOptions)
                                .subscribe(data => {
                                    this.currentUser.firstName = data.firstName;
                                    this.currentUser.lastName = data.lastName;
                                    this.currentUser.email = data.email;
                                    console.log('User details successfully fetched: ' + JSON.stringify(data));
                                    
                                    // store user details and basic auth credentials in session storage 
                                    // to keep user logged in between page refreshes
                                    this.setAuthData(username, password);
                                    sessionStorage.setItem('currentUser', JSON.stringify(this.currentUser));
                                    this.isLoggedin = true;
                                },
                                    err => {
                                        console.log("Error occured. (GET User Details)")
                                    }
                                );
                        },
                            err => {
                                console.log("Error occured. (GET Groups)")
                            }
                        );

                    //this.getUserDetails();
                    //this.getUserGroups();

                    //this.cookieService.set('userID', this.currentUser.id);
                    //this.cookieService.set( 'userGroups', JSON.stringify(this.currentUser.groups));

                }

                return this.currentUser;
            }));
    }

    logout() {
        // remove user from session storage to log user out
        sessionStorage.removeItem('currentUser');
        this.isLoggedin = false;
    }

    private setAuthData(username: string, password: string) {
        // Basic Auth Token
        this.currentUser.authdata = window.btoa(username + ':' + password);
    }

}



