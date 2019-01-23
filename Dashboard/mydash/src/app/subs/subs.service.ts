import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

// Import Models
import { Process } from '../process/process';
import { Subscription } from './subscription';
import { Notification } from './notification';

// Import Components

// Import Services

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('dashboard:dashboardPW') })
};

@Injectable({
    providedIn: 'root'
})
export class SubsService {

    constructor(private http: HttpClient) { }

    private subsUrl = 'http://149.201.176.231:9090/subs';

    public getMySuscribedProcesses(user: string) {
        return this.http.get<Process[]>(this.subsUrl + "/mysubscribedProcesses?user=" + user, httpOptions);
    }

    public getMySuscribedProcessInstances(user: string) {
        return this.http.get<Process[]>(this.subsUrl + "/mysubscribedProcessInstances?user=" + user, httpOptions);
    }

    public getRunningProcesses() {
        return this.http.get<Process[]>(this.subsUrl + "/runningProcesses", httpOptions);
    }

    public addUserToNotification(notification) {
        return this.http.post<Notification>("http://149.201.176.231:9090/notification/add", notification, httpOptions);
    }

    public deleteUserFromNotification(username: string) {
        return this.http.delete<Notification>("http://149.201.176.231:9090/notification/delete"+ "?username=" + username, httpOptions);
    }
 

    public addSubscribedProcess(sub) {
        return this.http.post<Subscription>(this.subsUrl + '/addSub', sub, httpOptions);
    }

    public addSubscribedRunningProcess(sub) {
        return this.http.post<Subscription>(this.subsUrl + '/addRunningSub', sub, httpOptions);
    }

    public deleteSubscribedProcess(process: Process | number, username: string) {
        const id = typeof process === 'number' ? process : process.processID;
        const url = `http://149.201.176.231:9090/subs/deleteSubscribedProcess/${id}`;
        console.log(id);

        return this.http.delete<Process>(url + "?username=" + username, httpOptions);
    }

    public deleteSubscribedRunningProcess(process: Process | number, username: string) {
        const id = typeof process === 'number' ? process : process.processID;
        const url = `http://149.201.176.231:9090/subs/deleteSubscribedRunningProcess/${id}`;
        console.log(id);

        return this.http.delete<Process>(url + "?username=" + username, httpOptions);
    }
}