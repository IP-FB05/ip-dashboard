import { Component, OnInit } from '@angular/core';
import { ProcessesComponent } from '../process/processes/processes.component';
import { Process } from '../process/process';
import { Subs } from '../subs/subs';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SubsService } from '../subs/subs.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('dashboard:dashboardPW') })
};

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {

  constructor(private pc: ProcessesComponent, private http: HttpClient, private subsService: SubsService) { }

  processes: Process[];
  subscribedProcesses: Process[];
  subscribedProcessInstances: Process[];
  runningProcesses: Process[];

  private processUrl = 'http://localhost:9090/processes';

  ngOnInit() {

    this.getProcesses().subscribe(process => this.processes = process);

    this.subsService.getMySuscribedProcesses("aa1234s")
      .subscribe(process => this.subscribedProcesses = process);

    this.subsService.getMySuscribedProcessInstances("aa1234s")
      .subscribe(process => this.subscribedProcessInstances = process);

    this.subsService.getRunningProcesses()
      .subscribe(process => this.runningProcesses = process);


  }

  public getProcesses() {
    return this.http.get<Process[]>(this.processUrl, httpOptions);
  }





}