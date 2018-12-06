import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Subs } from '../subs/subs';
import { Process } from '../process/process';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
    providedIn: 'root'
})
export class SubsService {

    constructor(private http: HttpClient) { }

    private subsUrl = 'http://localhost:8080/subs';

    public getMySubbedProcesses(user: string) {
        return this.http.get<Process[]>(this.subsUrl + "/mysubs?user=" + user);
    }
}