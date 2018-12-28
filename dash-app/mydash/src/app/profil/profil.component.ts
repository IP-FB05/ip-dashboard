import { Component, OnInit } from '@angular/core';
import { ProcessesComponent } from '../process/processes/processes.component';
import { Process } from '../process/process';
import { Subs } from '../subs/subs';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SubsService } from '../subs/subs.service';

import { AuthService } from '../login/auth/auth.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Subscription } from '../subs/subscription';
import { ProcessService } from '../process/process.service';
import { MatSnackBar } from '@angular/material';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('dashboard:dashboardPW') })
};

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {

  constructor(private pc: ProcessesComponent,
    private http: HttpClient,
    private subsService: SubsService,
    private authService: AuthService,
    private processService: ProcessService,
    public snackBar: MatSnackBar) {
  }

  processes: Process[];
  subscribedProcesses: Process[];
  subscribedProcessInstances: Process[];
  runningProcesses: Process[];

  formProcessSub: FormGroup;
  formRunningProcessSub: FormGroup;
  formNotification: FormGroup;

  sub: Subscription;
  notification: Notification;


  private processUrl = 'http://localhost:9090/processes';

  ngOnInit() {

    this.formProcessSub = new FormGroup({
      processSubControl: new FormControl()
    });

    this.formRunningProcessSub = new FormGroup({
      processRunningSubControl: new FormControl()
    });

    this.getProcesses().subscribe(process => this.processes = process);

    this.subsService.getMySuscribedProcesses(this.authService.currentUser.id)
      .subscribe(process => this.subscribedProcesses = process);

    this.subsService.getMySuscribedProcessInstances(this.authService.currentUser.id)
      .subscribe(process => this.subscribedProcessInstances = process);

    this.subsService.getRunningProcesses()
      .subscribe(process => this.runningProcesses = process);
  }

  public getProcesses() {
    return this.http.get<Process[]>(this.processUrl, httpOptions);
  }

  public getProcessById(id: number) {
    return this.http.get<Process>(this.processUrl + "/" + id);
  }

  subscribeProcess() {
    var curProcessID = this.formProcessSub.controls.processSubControl.value.trim();
    var curUsername = this.authService.currentUser.id.trim();
    if (!curProcessID || !curUsername) {
      return;
    }
    this.sub = new Subscription(curProcessID, curUsername);
    this.subsService.addSubscribedProcess(this.sub)
      .subscribe(
        data => {
          this.openSnackBar("Prozess erfolgreich abonniert");
        },
        error => {
          this.openSnackBar("Prozess konnte nicht abonniert werden");
        });
  }

  subscribeRunningProcess() {
    var curProcessID = this.formRunningProcessSub.controls.processRunningSubControl.value.trim();
    var curUsername = this.authService.currentUser.id.trim();
    if (!curProcessID || !curUsername) {
      return;
    }
    this.sub = new Subscription(curProcessID, curUsername);
    this.subsService.addSubscribedRunningProcess(this.sub)
      .subscribe(
        data => {
          this.openSnackBar("Prozess erfolgreich abonniert");
        },
        error => {
          this.openSnackBar("Prozess konnte nicht abonniert werden");
          console.log(error);
        });
  }

  deleteSubscribedProcess(process: Process) {
    this.subscribedProcesses = this.subscribedProcesses.filter(p => p !== process);
    var curUsername = this.authService.currentUser.id.trim();
    if (!process.processID || !curUsername) {
      return;
    }
    this.subsService.deleteSubscribedProcess(process, curUsername).subscribe(
      data => {
        this.openSnackBar("Prozess erfolgreich deabonniert");
      },
      error => {
        this.openSnackBar("Prozess konnte nicht deabonniert werden");
      });
  }

  deleteSubscribedRunningProcess(process: Process) {
    this.subscribedProcessInstances = this.subscribedProcessInstances.filter(p => p !== process);
    var curUsername = this.authService.currentUser.id.trim();
    if (!process.processID || !curUsername) {
      return;
    }
    this.subsService.deleteSubscribedRunningProcess(process, curUsername).subscribe(
      data => {
        this.openSnackBar("Prozess erfolgreich deabonniert");
      },
      error => {
        this.openSnackBar("Prozess konnte nicht deabonniert werden");
      });
  }

  eventNotification(e) {
    var curUsername = this.authService.currentUser.id.trim();
    this.notification = new Notification(curUsername);
    if (e.target.checked) {
      console.log(curUsername);
      this.subsService.deleteUserFromNotification(curUsername).subscribe(
        data => {
          this.openSnackBar("Benachrichtigungen aktiviert");
        }, 
        error => {
          console.log(error);
          this.openSnackBar("Benachrichtigungen konnten nicht aktiviert werden");
        });
    }
    else {
      console.log(curUsername);
      this.subsService.addUserToNotification(this.notification).subscribe(
        
        data => {
          this.openSnackBar("Benachrichtigungen deaktiviert");
        }, 
        error => {
          console.log(error);
          this.openSnackBar("Benachrichtigungen konnten nicht deaktiviert werden");
        });
    }
  }

  openSnackBar(text: string) {
    this.snackBar.open(text, '', {
      duration: 2000,
    });
  }
}