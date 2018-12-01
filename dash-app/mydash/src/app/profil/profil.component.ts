import { Component, OnInit } from '@angular/core';
import { ProcessesComponent } from '../process/processes/processes.component';
import { Process } from '../process/process';

import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {

  constructor(private pc: ProcessesComponent, private http:HttpClient) { }

  processes: Process[];
  private processUrl = 'http://localhost:8080/processes';

  ngOnInit() {
    this.getProcesses()
      .subscribe(process => this.processes = process);
  }

  public getProcesses() {
    return this.http.get<Process[]>(this.processUrl);
  }



}