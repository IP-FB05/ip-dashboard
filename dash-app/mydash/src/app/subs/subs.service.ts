import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Process } from '../process/process';
import { Subscription } from './subscription';

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

    public getUserNotification(username: string) {
        return this.http.get<any>('http://localhost:9090/notification/get?username=' + username, httpOptions);
    }

    public switchUserNotification(username: string) {
        return this.http.post<any>('http://localhost:9090/notification/switch?username=' + username, httpOptions);
    }

    public addSubscribedProcess(sub) {
        return this.http.post<Subscription>(this.subsUrl + '/addSub', sub, httpOptions);
    } 

    public addSubscribedRunningProcess(processId: number, username: string) {
        return this.http.post<any>(this.subsUrl + "/addRunningSub?processID=" + processId + "&username=" + username, httpOptions);
    }

    public deleteSubscribedProcess(process: Process | number, username: string) {
        const id = typeof process === 'number' ? process : process.processID;
        const url = `http://localhost:9090/deleteSubscribedProcess/${id}`;
    
        return this.http.delete<Process>(url + "?username=" + username, httpOptions);
    }
}