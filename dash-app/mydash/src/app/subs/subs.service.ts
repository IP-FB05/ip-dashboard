import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Process } from '../process/process';
import { Subscription } from './subscription';
import { Notification } from './notification';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('dashboard:dashboardPW') })
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

    public addUserToNotification(notification) {
        return this.http.post<Notification>("http://localhost:9090/notification/add", notification, httpOptions);
    }

    public deleteUserFromNotification(notification) {
        return this.http.delete("http://localhost:9090/notification/delete/"+ notification.username, httpOptions);
    }


    /*
    public addSubscribedProcess(processId: number, username: string) {
        console.log('Request wird geschickt.' + processId + username);
        return this.http.post<any>(this.subsUrl + "/addSub", { "processId": "processId", "username": "username" }, httpOptions).subscribe(
            data => {
                console.log("POST Request is successful ", data);
            },
            error => {
                console.log("Error", error);
            }
        );
    }
    */
  

    public addSubscribedProcess(sub) {
        return this.http.post<Subscription>(this.subsUrl + '/addSub', sub, httpOptions);
    }

    public addSubscribedRunningProcess(sub) {
        return this.http.post<Subscription>(this.subsUrl + '/addRunningSub', sub, httpOptions);
    }

    public deleteSubscribedProcess(process: Process | number, username: string) {
        const id = typeof process === 'number' ? process : process.processID;
        const url = `http://localhost:9090/subs/deleteSubscribedProcess/${id}`;
        console.log(id);

        return this.http.delete<Process>(url + "?username=" + username, httpOptions);
    }

    public deleteSubscribedRunningProcess(process: Process | number, username: string) {
        const id = typeof process === 'number' ? process : process.processID;
        const url = `http://localhost:9090/subs/deleteSubscribedRunningProcess/${id}`;
        console.log(id);

        return this.http.delete<Process>(url + "?username=" + username, httpOptions);
    }
}