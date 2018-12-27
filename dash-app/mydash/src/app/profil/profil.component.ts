import { Component, OnInit } from '@angular/core';
import { ProcessesComponent } from '../process/processes/processes.component';
import { Process } from '../process/process';
import { Subs } from '../subs/subs';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SubsService } from '../subs/subs.service';

import { AuthService } from '../login/auth.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';


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
              private authService: AuthService) { 
  }

  processes: Process[];
  subscribedProcesses: Process[];
  subscribedProcessInstances: Process[];
  runningProcesses: Process[];

  formProcessSub: FormGroup;
  formRunningProcessSub: FormGroup;
  formNotification: FormGroup;
  

  private processUrl = 'http://localhost:9090/processes';

  ngOnInit() {

    

    this.formProcessSub = new FormGroup({
      processSubControl: new FormControl()
    });

    this.formRunningProcessSub = new FormGroup({
      processRunningSubControl: new FormControl()
    });

    this.formNotification = new FormGroup({
      notifyControl: new FormControl(true)
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
    console.log('ProcessID: '+ this.formProcessSub.controls.processSubControl.value + ' User: ' + this.authService.currentUser.id);
    this.subsService.addSubscribedProcess(this.formProcessSub.controls.processSubControl.value, this.authService.currentUser.id);
    this.checkNotification();
  }

  subscribeRunningProcess() {
    console.log('ProcessID: '+ this.formProcessSub.controls.processSubControl.value + ' User: ' + this.authService.currentUser.id);
    this.subsService.addSubscribedRunningProcess(this.formProcessSub.controls.processRunningSubControl.value, this.authService.currentUser.id);
    this.checkNotification();
  }

  checkNotification() {
    this.subsService.checkUserNotification(this.formNotification.controls.notifyControl.value, this.authService.currentUser.id);
  }

  deleteSubscribedProcess(process: Process){
    this.subscribedProcesses = this.subscribedProcesses.filter(p => p !== process);
    this.subsService.deleteSubscribedProcess(process, this.authService.currentUser.id).subscribe();
  }



}