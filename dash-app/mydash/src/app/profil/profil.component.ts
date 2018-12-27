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
              private processService: ProcessService) { 
  }

  processes: Process[];
  subscribedProcesses: Process[];
  subscribedProcessInstances: Process[];
  runningProcesses: Process[];

  formProcessSub: FormGroup;
  formRunningProcessSub: FormGroup;
  formNotification: FormGroup;

  sub: Subscription;
  

  private processUrl = 'http://localhost:9090/processes';

  ngOnInit() {

    

    this.formProcessSub = new FormGroup({
      processSubControl: new FormControl()
    });

    this.formRunningProcessSub = new FormGroup({
      processRunningSubControl: new FormControl()
    });

    this.formNotification = new FormGroup({
      notifyControl: new FormControl()
    })

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

  subscribeProcess() {
    var curProcessID = this.formProcessSub.controls.processSubControl.value.trim();
    var curUsername = this.authService.currentUser.id.trim();
    if( !curProcessID || !curUsername) {
      return;
    }
    this.sub = new Subscription(curProcessID,curUsername);
    this.subsService.addSubscribedProcess(this.sub)
    .subscribe( data => {
      alert("Process subscribed successfully.");
    });
  }



  subscribeRunningProcess() {
    console.log('ProcessID: '+ this.formProcessSub.controls.processSubControl.value + ' User: ' + this.authService.currentUser.id);
    this.subsService.addSubscribedRunningProcess(this.formProcessSub.controls.processRunningSubControl.value, this.authService.currentUser.id);
  }

  getNotification() {
    this.subsService.checkUserNotification(this.authService.currentUser.id);
  }

  deleteSubscribedProcess(process: Process){
    this.subscribedProcesses = this.subscribedProcesses.filter(p => p !== process);
    this.subsService.deleteSubscribedProcess(process, this.authService.currentUser.id).subscribe();
  }



}