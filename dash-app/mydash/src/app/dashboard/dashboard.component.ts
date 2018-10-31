import { Component, OnInit } from '@angular/core';
import { Process } from '../process';
import { ProcessService } from '../process.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  processes: Process[] = [];

  constructor(private processService: ProcessService) { }

  ngOnInit() {
    this.getProcesses();
  }

  getProcesses(): void {
    this.processService.getProcesses()
      .subscribe(processes => this.processes = processes);
  }
}