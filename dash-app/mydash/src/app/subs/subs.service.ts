import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Process } from '../process/process';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW') })
  };

@Injectable({
    providedIn: 'root'
})
export class SubsService {

    constructor(private http: HttpClient) { }

    private subsUrl = 'http://localhost:9090/subs';

    public getMySuscribedProcesses(user: string) {
        return this.http.get<Process[]>(this.subsUrl + "/mysubscribedProcesses?user=" + user, httpOptions);
    }

    public getMySuscribedProcessInstances(user: string) {
        return this.http.get<Process[]>(this.subsUrl + "/mysubscribedProcessInstances?user=" + user, httpOptions);
    }

    public getRunningProcesses() {
        return this.http.get<Process[]>(this.subsUrl + "/runningProcesses", httpOptions);
    }
}