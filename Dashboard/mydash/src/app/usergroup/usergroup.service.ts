import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


// Import Models
import { Usergroup } from './usergroup';

// Import Components
// Import Services

const httpOptions = {
    //headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('dashboard:dashboardPW') })
    headers: new HttpHeaders({ 'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class UsergroupService {

    constructor(private http: HttpClient) { }

    private usergroupUrl = 'http://localhost:9090/usergroups';

    public getUsergroups() {
        return this.http.get<Usergroup[]>(this.usergroupUrl + '/all', httpOptions);
    }
}