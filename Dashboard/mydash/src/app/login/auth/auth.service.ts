import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';

import * as jwt from 'jsonwebtoken';

// Import Models
import { User } from '../user';
import { Authorization } from './authorization';

// Import Components
// Import Services


const httpOptions = {
    //headers: new HttpHeaders({ 'Content-Type': 'application/json'/*,'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW' )*/ })
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({ providedIn: 'root' })
export class AuthService {

    private authUrl = 'http://localhost:8080/engine-rest';

    isLoggedin = false;
    currentUser: User = new User();
    authdata: Authorization = new Authorization();
    allowedGroups: String = "";

    constructor(
        private http: HttpClient,
        private cookieService: CookieService,
        private router: Router,
        public snackBar: MatSnackBar
    ) { }

    login(username: string, password: string) {
        const mockLogin = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json', "Authorization": 'Basic ' + btoa(username + ':' + password) })
        };
        return this.http.post<any>(this.authUrl + '/identity/verify', { username: username, password: password }, mockLogin)
            .pipe(map(user => {
                // login successful if there's a user in the response
                if (user && user.authenticatedUser == username && user.authenticated) {

                    this.currentUser.id = user.authenticatedUser;
                    this.currentUser.authenticated = user.authenticated;
                    this.openSnackBar("Erfolgreich angemeldet");


                    return this.http.get<any>(this.authUrl + '/identity/groups?userId=' + this.currentUser.id, mockLogin)
                        .subscribe(data => {
                            this.currentUser.groups = data.groups;
                            console.log('User groups successfully fetched: ' + JSON.stringify(this.currentUser.groups));
                            return this.http.get<any>(this.authUrl + '/user/' + this.currentUser.id + '/profile', mockLogin)
                                .subscribe(data => {
                                    this.currentUser.firstName = data.firstName;
                                    this.currentUser.lastName = data.lastName;
                                    this.currentUser.email = data.email;
                                    console.log('User details successfully fetched: ' + JSON.stringify(data));

                                    this.setUserRole();

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

                } else {
                    this.openSnackBar("Benutzer konnte nicht angemeldet werden");
                }

                return this.currentUser;
            }));
    }

    logout() {
        // remove user from session storage to log user out
        sessionStorage.removeItem('currentUser');
        if (this.isLoggedin) {
            this.isLoggedin = false;
            this.openSnackBar("Benutzer erfolgreich abgemeldet.");
        } else {
            this.isLoggedin = false;
        }

        this.router.navigate(['/login']);

    }

    private setAuthData(username: string, password: string) {
        // Set Basic Auth Token in authdata.baseAuth
        this.authdata.baseAuth = window.btoa(username + ':' + password);
        // Set roleAuth in authdata.roleAuth to control the Authorization
        //var token = jwt.sign({ id: this.currentUser.id, groups: JSON.stringify(this.currentUser.groups) }, 'DIP31');
        this.authdata.roleAuth = this.generateRoleAuthToken(this.currentUser.id, this.currentUser.role);
        this.currentUser.authdata = this.authdata;
        console.log('Authorization tokens successfully declared.');
    }

    private generateRoleAuthToken(id: string, role: string): string {
        return jwt.sign({ id: id, role: role }, 'DIP31');
    }

    private setUserRole() {
        for (var i = 0; i < this.currentUser.groups.length; i++) {
            if (this.currentUser.groups[i].id == 'camunda-admin') {
                this.currentUser.role = 'admin';
                return;
            } else if (this.currentUser.groups[i].id == 'pruefungsamt') {
                this.currentUser.role = 'pruefungsamt';
                return;
            } else if (this.currentUser.groups[i].id == 'professor') {
                this.currentUser.role = 'professor';
                return;
            } else if (this.currentUser.groups[i].id == 'mitarbeiter') {
                this.currentUser.role = 'mitarbeiter';
                return;
            } else if (this.currentUser.groups[i].id == 'student') {
                this.currentUser.role = 'student'
                return;
            }

        }
        this.currentUser.role = 'gast';
    }

    getUsergroups(processID: number){
       return this.http.get<any>("http://localhost:9090/getUserGroups?pid=" + processID , httpOptions).subscribe(data => {
        this.allowedGroups = data.allowedGroups;
        console.log(this.allowedGroups);
        });
    }


    openSnackBar(text: string) {
        this.snackBar.open(text, '', {
            duration: 2000,
        });
    }

}



